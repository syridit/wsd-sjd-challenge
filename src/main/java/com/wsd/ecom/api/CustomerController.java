package com.wsd.ecom.api;

import com.wsd.ecom.dto.WishlistDto;
import com.wsd.ecom.service.WishlistService;
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
public class CustomerController {

    private final WishlistService wishlistService;

    @Autowired
    public CustomerController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


    @GetMapping(value = "/{id}/wishlist")
    public Page<WishlistDto> getCustomerWishlist(@PathVariable("id") Long customerId,
                                                 Pageable pageable) {
        return wishlistService.getWishlistForCustomer(customerId, pageable);
    }

}
