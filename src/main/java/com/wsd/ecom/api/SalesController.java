package com.wsd.ecom.api;

import com.wsd.ecom.dto.MaxSaleDayDto;
import com.wsd.ecom.dto.SalesTodayDto;
import com.wsd.ecom.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get Today's Total Sales",
            description = "Returns the total sales amount made today across all orders."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total sales amount for today retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/today/total")
    public SalesTodayDto getCustomerWishlist() {
        return salesService.getTotalSalesAmountOfToday();
    }


    @Operation(
            summary = "Get Maximum Sales Day",
            description = "Returns the day with the highest total sales amount within a specified datetime range."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maximum sales day data retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date format or range, or missing parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/max-sale-day")
    public MaxSaleDayDto getMaxSaleDay(
            @Parameter(
                    description = "Start of the date range (ISO format, e.g., 2025-07-01T00:00:00)",
                    required = true,
                    example = "2025-07-01T00:00:00"
            )
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @Parameter(
                    description = "End of the date range (ISO format, e.g., 2025-07-04T23:59:59)",
                    required = true,
                    example = "2025-07-04T23:59:59"
            )
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return salesService.getMaxSalesDay(from, to);
    }

}
