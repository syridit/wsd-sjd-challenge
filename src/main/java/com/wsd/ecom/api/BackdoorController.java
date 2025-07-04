package com.wsd.ecom.api;

import com.wsd.ecom.dto.DataGenerationRequest;
import com.wsd.ecom.dto.TopSellingProductByAmountDto;
import com.wsd.ecom.dto.TopSellingProductByQuantityDto;
import com.wsd.ecom.service.SalesService;
import com.wsd.ecom.util.generator.DataGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@RestController
@RequestMapping(value = "/api/backdoor")
@Tag(name = "Backdoor Utilities", description = "Endpoints used for internal testing, development, or data seeding")
public class BackdoorController {

    private final DataGenerator dataGenerator;

    @Autowired
    public BackdoorController(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Operation(
            summary = "Generate Mock Data",
            description = "Generates random test data (e.g., sales, customers, products) for development or testing purposes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test data generation started successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data for generation request"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error during data generation")
    })
    @PostMapping("/generate-data")
    public void generateData(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body defining data generation counts and limits",
                    required = true
            )
            @Valid @RequestBody DataGenerationRequest request) {
        dataGenerator.generateData(request);
    }


}
