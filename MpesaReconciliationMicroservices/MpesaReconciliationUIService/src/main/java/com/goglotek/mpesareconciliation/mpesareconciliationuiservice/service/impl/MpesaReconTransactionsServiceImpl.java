package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.DiscrepancyType;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFetchedTrans;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaMainTransactions;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconTransactions;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository.MpesaReconTransactionsRepository;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaReconTransactionsService;

@Service
public class MpesaReconTransactionsServiceImpl implements MpesaReconTransactionsService {
	@Autowired
	MpesaReconTransactionsRepository mpesaReconTransactionsRepository;

	@Override
	public Optional<MpesaReconTransactions> findById(Long reconTransId) {
		return mpesaReconTransactionsRepository.findById(reconTransId);
	}

	@Override
	public List<MpesaReconTransactions> findAllPaged(Integer page, Integer limit, String order, String direction) {
		return mpesaReconTransactionsRepository
				.findAll(PageRequest.of(page, limit,
						direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
				.getContent();
	}

	@Override
	public List<MpesaReconTransactions> search(String keywords) {
		return mpesaReconTransactionsRepository.search(keywords, keywords);
	}

	private MpesaReconTransactions createFilterObject(List<FilterModel> filterModel) {
		MpesaFetchedTrans trans = new MpesaFetchedTrans();
		MpesaMainTransactions mainTrans = new MpesaMainTransactions();
		MpesaReconTransactions recon = new MpesaReconTransactions();
		Set<Integer> discType = new HashSet<Integer>();
		for (FilterModel m : filterModel) {
			String filterColumn = m.getColumnField();
			String value = m.getValue();
			switch (filterColumn.toLowerCase()) {
			case "receiptno":
				trans.setReceiptNo(value);
				break;
			case "details":
				trans.setDetails(value);
				break;
			case "paidin":
				trans.setPaidIn(Double.parseDouble(value));
				break;
			case "fileid":
				trans.setFileId(Long.parseLong(value));
				break;
			case "reasontype":
				trans.setReasonType(value);
				break;
			case "transactionstatus":
				trans.setTransactionStatus(value);
			case "otherpartyinfo":
				trans.setOtherPartyInfo(value);
				break;
			case "accountno":
				trans.setAccountNo(value);
				break;
			case "msisdn":
				trans.setMsisdn(value);
				mainTrans.setMsisdn(value);
				break;
			case "shortcode":
				trans.setShortcode(value);
				break;
			case "firstname":
				trans.setFirstname(value);
				mainTrans.setFirstname(value);
				break;
			case "middlename":
				trans.setMiddlename(value);
				mainTrans.setMiddlename(value);
				break;
			case "lastname":
				trans.setLastname(value);
				mainTrans.setLastname(value);
				break;
			case "discrepancytype":
				int type = Integer.parseInt(value);
				recon.setDiscrepancyType(type);
				discType.add(type);
				break;
			case "isvalidtrans":
				recon.setValidTrans(Boolean.parseBoolean(value));
				break;
			case "comments":
				recon.setComments(value);
				break;
			case "action":
				recon.setAction(value);
				break;
			case "isinsertedtomain":
				recon.setInsertedToMain(Boolean.parseBoolean(value));
				break;
			case "isflaggedinmain":
				recon.setFlaggedInMain(Boolean.parseBoolean(value));
				break;
			case "transtype":
				mainTrans.setTransType(value);
				break;
			case "transid":
				mainTrans.setTransId(value);
				break;
			case "transamount":
				mainTrans.setTransAmount(Double.parseDouble(value));
				break;
			case "businessshortcode":
				mainTrans.setBusinessShortcode(value);
				break;
			case "billrefnumber":
				mainTrans.setBillRefNumber(value);
				break;
			case "invoicenumber":
				mainTrans.setInvoiceNumber(value);
			case "orgaccountbalance":
				mainTrans.setOrgAccountBalance(Double.parseDouble(value));
				break;
			case "isfromreconciliation":
				mainTrans.setFromReconciliation(Boolean.parseBoolean(value));
				break;
			}
		}
		if (discType.contains(DiscrepancyType.MISSING.value)) {
			recon.setMpesaFetchedTrans(trans);
		} else if (discType.contains(DiscrepancyType.FRAUDULENT.value)) {
			recon.setMpesaMainTransactions(mainTrans);
		}
		return recon;
	}

	private ExampleMatcher createFilterMatcher(List<FilterModel> filterModel) {
		ExampleMatcher matcher = ExampleMatcher.matchingAll();
		for (FilterModel m : filterModel) {
			switch (m.getOperatorValue().toLowerCase()) {
			case "contains": {
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			case "equals": {
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			case "startswith": {
				matcher = matcher
						.withMatcher(m.getColumnField(),
								ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			case "endswith": {
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			default:
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
		}
		matcher = matcher.withIgnoreNullValues();
		return matcher;
	}

	@Override
	public long countAllFilteredPaged(List<FilterModel> filterModel) {
		Example<MpesaReconTransactions> example = Example.of(createFilterObject(filterModel),
				createFilterMatcher(filterModel));
		return mpesaReconTransactionsRepository.count(example);
	}

	@Override
	public List<MpesaReconTransactions> findAllFilteredPaged(Integer page, Integer limit, String order,
			String direction, List<FilterModel> filterModel) {
		Example<MpesaReconTransactions> example = Example.of(createFilterObject(filterModel),
				createFilterMatcher(filterModel));
		return mpesaReconTransactionsRepository
				.findAll(example, PageRequest.of(page, limit,
						direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
				.getContent();
	}

}
