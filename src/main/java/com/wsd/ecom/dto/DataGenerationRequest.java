package com.wsd.ecom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataGenerationRequest {

    @NotNull
    @Schema(
            description = "Number of customers to generate",
            example = "50",
            minimum = "1"
    )
    int customerCount;

    @NotNull
    @Schema(
            description = "Number of products to generate",
            example = "100",
            minimum = "1"
    )
    int productCount;

    @NotNull
    @NotNull
    @Schema(
            description = "Number of sales records to generate",
            example = "500",
            minimum = "1"
    )
    int salesCount;

    @NotNull
    @NotNull
    @Schema(
            description = "Number of wishlist entries to generate",
            example = "200",
            minimum = "1"
    )
    int wishlistCount;

}
