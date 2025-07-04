package com.wsd.ecom.api;

import com.wsd.ecom.dto.WishlistDto;
import com.wsd.ecom.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@RestController
@RequestMapping(value = "/api/customers")
@Tag(name = "Customer", description = "Operations related to customers and their data")
public class CustomerController {

    private final WishlistService wishlistService;

    @Autowired
    public CustomerController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


    @Operation(
            summary = "Get Customer Wishlist",
            description = "Returns a paginated list of wishlist items for the given customer."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved wishlist"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}/wishlist")
    public Page<WishlistDto> getCustomerWishlist(
            @Parameter(description = "The ID of the customer", required = true, example = "123")
            @PathVariable("id") Long customerId,

            @Parameter(description = "Pagination information (page, size, sort)")
            Pageable pageable) {
        return wishlistService.getWishlistForCustomer(customerId, pageable);
    }

}
