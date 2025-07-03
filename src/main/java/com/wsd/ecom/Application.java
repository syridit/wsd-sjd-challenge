package com.wsd.ecom;

import com.wsd.ecom.util.generator.CustomerGenerator;
import com.wsd.ecom.util.generator.ProductGenerator;
import com.wsd.ecom.util.generator.SalesGenerator;
import com.wsd.ecom.util.generator.WishlistGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private final CustomerGenerator customerGenerator;
    private final ProductGenerator productGenerator;
    private final SalesGenerator salesGenerator;
    private final WishlistGenerator wishlistGenerator;

    public Application(CustomerGenerator customerGenerator,
                       ProductGenerator productGenerator,
                       SalesGenerator salesGenerator,
                       WishlistGenerator wishlistGenerator) {
        this.customerGenerator = customerGenerator;
        this.productGenerator = productGenerator;
        this.salesGenerator = salesGenerator;
        this.wishlistGenerator = wishlistGenerator;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    void generateData() {
        int customerCount = 200;
        int productCount = 300;
        int salesCount = 1500;
        int wishListCount = 2200;

//        customerGenerator.generate(customerCount);
//        salesGenerator.generate(salesCount);
//        wishlistGenerator.generate(wishListCount);

    }

}
