package com.goglotek.frauddetector.datastoreservice.controller;

import java.util.Date;
import java.util.List;

import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.model.LocalTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.dto.LocalTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.service.LocalTransactionsService;

@RequestMapping("localtransactions")
@RestController
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'MACHINE_USER')")
public class LocalTransactionsController {
    @Autowired
    private Config config;

    @Autowired
    private LocalTransactionsService localTransactionsService;

    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody LocalTransactionsDto filteredLocalTransactions(
            @RequestParam(name = "page", required = true) Integer page,
            @RequestParam(name = "limit", required = true) Integer limit,
            @RequestParam(name = "order_by", required = true) String order,
            @RequestParam(name = "direction", required = true) String direction,
            @RequestBody List<FilterModel> filterModel) {
        LocalTransactionsDto dto = new LocalTransactionsDto();
        dto.setCount(localTransactionsService.countAllFilteredPaged(filterModel));
        dto.setLocalTransactions(
                localTransactionsService.findAllFilteredPaged(page, limit, order, direction, filterModel));
        return dto;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<LocalTransactions> getLocalTransactionsByPeriod(
            @RequestParam(value = "from") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date from,
            @RequestParam(value = "to") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date to

    ) {

        return localTransactionsService.findByTimePeriod(from, to);
    }

}
