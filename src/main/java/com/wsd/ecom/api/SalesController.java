package com.wsd.ecom.api;

import com.wsd.ecom.dto.MaxSaleDayDto;
import com.wsd.ecom.dto.SalesTodayDto;
import com.wsd.ecom.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@RestController
@RequestMapping(value = "/api/sales")
public class SalesController {

    private final SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }


    @GetMapping(value = "/today/total")
    public SalesTodayDto getCustomerWishlist() {
        return salesService.getTotalSalesAmountOfToday();
    }


    @GetMapping("/max-sale-day")
    public MaxSaleDayDto getMaxSaleDay(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return salesService.getMaxSalesDay(from, to);
    }

}
