package com.wsd.ecom.util.generator;

import com.wsd.ecom.entity.Product;
import com.wsd.ecom.repository.ProductRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 03 July, 2025
 */

@Component
public class ProductGenerator {

    private final ProductRepository repo;
    private final Faker faker = new Faker();

    public ProductGenerator(ProductRepository repo) {
        this.repo = repo;
    }

    public void generate(int count) {
        List<Product> productList = IntStream.range(0, count)
                .mapToObj(i -> new Product(faker.commerce().productName(), new BigDecimal(faker.commerce().price())))
                .toList();

        repo.saveAllAndFlush(productList);

        System.out.println(repo.findAll());
        System.out.println("Current total products: " + repo.count());
    }

}
