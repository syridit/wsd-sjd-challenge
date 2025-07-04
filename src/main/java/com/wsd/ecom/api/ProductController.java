package com.wsd.ecom.api;

import com.wsd.ecom.dto.TopSellingProductByAmountDto;
import com.wsd.ecom.dto.TopSellingProductByQuantityDto;
import com.wsd.ecom.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private final SalesService salesService;

    @Autowired
    public ProductController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/top-selling/all-time/by-amount")
    public List<TopSellingProductByAmountDto> getTopFiveProductsOfAllTimeByAmount() {
        return salesService.getAllTimeTopFiveProductsBySalesAmount();
    }

    @GetMapping("/top-selling/last-month/by-quantity")
    public List<TopSellingProductByQuantityDto> getTopFiveProductsOfLastMonthByQuantity() {
        return salesService.getLastMonthTopFiveProductsByQuantity();
    }

}
