package com.wsd.ecom.util.generator;

import com.wsd.ecom.dto.DataGenerationRequest;
import org.springframework.stereotype.Component;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@Component
public class DataGenerator {

    private final CustomerGenerator customerGenerator;
    private final ProductGenerator productGenerator;
    private final SalesGenerator salesGenerator;
    private final WishlistGenerator wishlistGenerator;

    public DataGenerator(CustomerGenerator customerGenerator,
                         ProductGenerator productGenerator,
                         SalesGenerator salesGenerator,
                         WishlistGenerator wishlistGenerator) {
        this.customerGenerator = customerGenerator;
        this.productGenerator = productGenerator;
        this.salesGenerator = salesGenerator;
        this.wishlistGenerator = wishlistGenerator;
    }

    public void generateData(DataGenerationRequest request) {

        customerGenerator.generate(request.getCustomerCount());
        productGenerator.generate(request.getProductCount());
        salesGenerator.generate(request.getSalesCount());
        wishlistGenerator.generate(request.getWishlistCount());

    }
}
