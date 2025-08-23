package com.goglotek.frauddetector.dataprocessorservice.service.impl;

import com.goglotek.frauddetector.dataprocessorservice.AbstractTests;
import com.goglotek.frauddetector.dataprocessorservice.dto.DiscrepancyType;
import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DataProcessingServiceImplTests extends AbstractTests {
    @Autowired
    private DataProcessingServiceImpl dataProcessingService;

    @BeforeEach
    public void setUp() {
        super.startUp();

    }

    @Test
    public void shouldRetrieveLocalTransactions() {
        List<Transaction> tr = dataStoreService.getLocalTransactions(new FileDto());
        assertTrue(tr != null);
        assertTrue(tr.size() == localTransCount);
    }

    @Test
    public void shouldRetrieveProviderTransactions() {
        List<Transaction> tr = dataStoreService.getProviderTransactions(new FileDto());
        assertTrue(tr != null);
        assertTrue(tr.size() == providerTransCount);
    }

    @Test
    public void shouldDetectFraudulentTransactions() {
        //use reflections to test private method
        Method method = ReflectionUtils.findMethod(DataProcessingServiceImpl.class, "getFraudulentTransactions", List.class, List.class);
        ReflectionUtils.makeAccessible(method);

        Map<DiscrepancyType, List<Transaction>> result = (Map<DiscrepancyType, List<Transaction>>) ReflectionUtils.invokeMethod(method, dataProcessingService, providerTransactions, localTransactions);

        assertTrue(result != null);
        List<Transaction> fraudTrans = result.get(DiscrepancyType.FRAUDULENT);
        assertTrue(fraudTrans != null);
        assertTrue(fraudTrans.size() == localTransCount - providerTransCount);

        //get random transaction and check in the provider transactions to ensure that it's not there
        int index = new Random().nextInt(result.size());
        Transaction t = fraudTrans.get(index);
        Transaction t2 = providerTransactions.stream().filter(s -> s.getId() == t.getId()).findFirst().orElse(null);

        //confirm transaction is not there
        assertTrue(t2 == null);


    }

    @Test
    public void shouldDetectMissingTransactions() {
        //for missing transactions test, swap local and provider transactions
        swapLocalAndProviderTransactions();

        //use reflection to test private method
        Method method = ReflectionUtils.findMethod(DataProcessingServiceImpl.class, "getMissingTransactions", List.class, List.class);
        ReflectionUtils.makeAccessible(method);

        Map<DiscrepancyType, List<Transaction>> result = (Map<DiscrepancyType, List<Transaction>>) ReflectionUtils.invokeMethod(method, dataProcessingService, providerTransactions, localTransactions);

        assertTrue(result != null);
        List<Transaction> missingTrans = result.get(DiscrepancyType.MISSING);
        assertTrue(missingTrans != null);
        assertTrue(missingTrans.size() == localTransCount - providerTransCount);

        //get random transaction and check in the provider transactions to ensure that it's not there
        int index = new Random().nextInt(result.size());
        Transaction t = missingTrans.get(index);
        Transaction t2 = localTransactions.stream().filter(s -> s.getId() == t.getId()).findFirst().orElse(null);

        //confirm transaction is not there
        assertTrue(t2 == null);

        //swap them back
        swapLocalAndProviderTransactions();

    }

    @Test
    public void shouldDetectInvalidAmountInTransactions() {
        //test for invalid amounts in fraudulent transactions check
        //change transaction amount of first transaction of either
        Transaction t = localTransactions.get(0);
        t.setAmount(1000000d);
        localTransactions.set(0, t);

        //use reflections to test private method
        Method method = ReflectionUtils.findMethod(DataProcessingServiceImpl.class, "getFraudulentTransactions", List.class, List.class);
        ReflectionUtils.makeAccessible(method);

        Map<DiscrepancyType, List<Transaction>> result = (Map<DiscrepancyType, List<Transaction>>) ReflectionUtils.invokeMethod(method, dataProcessingService, providerTransactions, localTransactions);
        List<Transaction> invalidAmt = result.get(DiscrepancyType.INVALID_AMOUNT);
        assertTrue(invalidAmt != null);
        assertTrue(invalidAmt.size() == 1);

        //confirm that the transaction flagged as having invalid amount is the one whose amount we changed
        assertTrue(invalidAmt.get(0).getId().equals(t.getId()));

        //test for invalid amounts in missing transactions check
        //swap local and provider transactions first
        swapLocalAndProviderTransactions();

        //change transaction amount of first transaction of either
        Transaction t1 = providerTransactions.get(0);
        t1.setAmount(1000000d);
        providerTransactions.set(0, t1);

        //use reflections to test private method
        Method method1 = ReflectionUtils.findMethod(DataProcessingServiceImpl.class, "getMissingTransactions", List.class, List.class);
        ReflectionUtils.makeAccessible(method1);

        Map<DiscrepancyType, List<Transaction>> result1 = (Map<DiscrepancyType, List<Transaction>>) ReflectionUtils.invokeMethod(method1, dataProcessingService, providerTransactions, localTransactions);
        List<Transaction> invalidAmt1 = result1.get(DiscrepancyType.INVALID_AMOUNT);
        assertTrue(invalidAmt1 != null);
        assertTrue(invalidAmt1.size() == 1);

        //confirm that the transaction flagged as having invalid amount is the one whose amount was changed
        assertTrue(invalidAmt1.get(0).getId().equals(t1.getId()));

        //swap them back
        swapLocalAndProviderTransactions();


    }

    @Test
    public void shouldDetectInvalidClientIdInLocalTransactions() {
        //test for invalid clientId in fraudulent transactions check
        //change transaction clientid of first transaction of either
        Transaction t = localTransactions.get(0);
        t.setClientAccount("asdujshsidfrwerttertertsdf");
        localTransactions.set(0, t);

        //use reflections to test private method
        Method method = ReflectionUtils.findMethod(DataProcessingServiceImpl.class, "getFraudulentTransactions", List.class, List.class);
        ReflectionUtils.makeAccessible(method);

        Map<DiscrepancyType, List<Transaction>> result = (Map<DiscrepancyType, List<Transaction>>) ReflectionUtils.invokeMethod(method, dataProcessingService, providerTransactions, localTransactions);
        List<Transaction> invalidClientAcc = result.get(DiscrepancyType.INVALID_CLIENT_ID);
        assertTrue(invalidClientAcc != null);
        assertTrue(invalidClientAcc.size() == 1);

        //confirm that the transaction flagged as having invalid clientaccount is the one whose clientaccount was changed
        assertTrue(invalidClientAcc.get(0).getId().equals(t.getId()));
    }

    @Test
    public void shouldDetectInvalidTimestampInLocalTransactions() {
        //test for invalid timestamp in fraudulent transactions check
        //change transaction timestamp of first transaction of either
        Transaction t = localTransactions.get(0);
        t.setTransactionTimestamp(new Date());
        localTransactions.set(0, t);

        //use reflections to test private method
        Method method = ReflectionUtils.findMethod(DataProcessingServiceImpl.class, "getFraudulentTransactions", List.class, List.class);
        ReflectionUtils.makeAccessible(method);

        Map<DiscrepancyType, List<Transaction>> result = (Map<DiscrepancyType, List<Transaction>>) ReflectionUtils.invokeMethod(method, dataProcessingService, providerTransactions, localTransactions);
        List<Transaction> invalidTimestamp = result.get(DiscrepancyType.INVALID_TIMESTAMP);
        assertTrue(invalidTimestamp != null);
        assertTrue(invalidTimestamp.size() == 1);

        //confirm that the transaction flagged as having invalid timestamp is the one whose timestamp was changed
        assertTrue(invalidTimestamp.get(0).getId().equals(t.getId()));
    }


    private void swapLocalAndProviderTransactions() {
        List<Transaction> buffer = new ArrayList<>(localTransactions);
        localTransactions.clear();
        localTransactions.addAll(providerTransactions);
        providerTransactions.clear();
        providerTransactions.addAll(buffer);
    }

}
