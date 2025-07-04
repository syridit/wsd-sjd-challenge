package com.wsd.ecom.api;

import com.wsd.ecom.dto.TopSellingProductByAmountDto;
import com.wsd.ecom.dto.TopSellingProductByQuantityDto;
import com.wsd.ecom.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product Analytics", description = "Endpoints for top-selling product analytics")
public class ProductController {

    private final SalesService salesService;

    @Autowired
    public ProductController(SalesService salesService) {
        this.salesService = salesService;
    }

    @Operation(
            summary = "Get All-Time Top 5 Products by Sales Amount",
            description = "Returns the top 5 selling products of all time based on total sales revenue."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top-selling products retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/top-selling/all-time/by-amount")
    public List<TopSellingProductByAmountDto> getTopFiveProductsOfAllTimeByAmount() {
        return salesService.getAllTimeTopFiveProductsBySalesAmount();
    }

    @Operation(
            summary = "Get Last Month's Top 5 Products by Quantity Sold",
            description = "Returns the top 5 products with the highest quantity sold in the last calendar month."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top-selling products retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/top-selling/last-month/by-quantity")
    public List<TopSellingProductByQuantityDto> getTopFiveProductsOfLastMonthByQuantity() {
        return salesService.getLastMonthTopFiveProductsByQuantity();
    }

}
