package com.wsd.ecom.api;

import com.wsd.ecom.dto.DataGenerationRequest;
import com.wsd.ecom.dto.TopSellingProductByAmountDto;
import com.wsd.ecom.dto.TopSellingProductByQuantityDto;
import com.wsd.ecom.service.SalesService;
import com.wsd.ecom.util.generator.DataGenerator;
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
public class BackdoorController {

    private final DataGenerator dataGenerator;

    @Autowired
    public BackdoorController(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @PostMapping("/generate-data")
    public void generateData(@Valid @RequestBody DataGenerationRequest request) {
        dataGenerator.generateData(request);
    }


}
