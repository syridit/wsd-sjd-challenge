package com.wsd.ecom.util.generator;

import com.wsd.ecom.entity.Customer;
import com.wsd.ecom.entity.Product;
import com.wsd.ecom.entity.Wishlist;
import com.wsd.ecom.repository.CustomerRepository;
import com.wsd.ecom.repository.ProductRepository;
import com.wsd.ecom.repository.WishlistRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 03 July, 2025
 */

@Component
@Slf4j
public class WishlistGenerator {

    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final WishlistRepository repo;
    private final Faker faker = new Faker();

    public WishlistGenerator(WishlistRepository repo,
                             CustomerRepository customerRepo,
                             ProductRepository productRepo) {
        this.repo = repo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    @CacheEvict(value = "wishlist", allEntries = true)
    public void generate(int count) {
        List<Customer> customers = customerRepo.findAll();
        List<Product> products = productRepo.findAll();

        Random random = new Random();
        List<Wishlist> wishlists = IntStream.range(0, count)
                .mapToObj(i -> {
                    Customer customer = customers.get(random.nextInt(customers.size()));
                    Product product = products.get(random.nextInt(products.size()));

                    return new Wishlist(customer.getId(), product.getId());
                })
                .toList();

        for (Wishlist wishlist : wishlists) {
            try {
                repo.saveAndFlush(wishlist);
            } catch (DataIntegrityViolationException e) {
                log.warn("Skipping duplicate entry: productId - {} and customerId - {}",
                        wishlist.getProductId(), wishlist.getCustomerId());
            }
        }

        System.out.println("Current total wishlists: " + repo.count());
    }


}
