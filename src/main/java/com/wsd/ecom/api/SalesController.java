package com.wsd.ecom.api;

import com.wsd.ecom.dto.SalesTodayDto;
import com.wsd.ecom.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
