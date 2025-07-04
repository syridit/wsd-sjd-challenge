package com.wsd.ecom.util.generator;

import com.wsd.ecom.entity.Customer;
import com.wsd.ecom.entity.Wishlist;
import com.wsd.ecom.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 03 July, 2025
 */

@Component
@Slf4j
public class CustomerGenerator {

    private final CustomerRepository repo;
    private final Faker faker = new Faker();

    public CustomerGenerator(CustomerRepository repo) {
        this.repo = repo;
    }

    public void generate(int count) {
        List<Customer> customers = IntStream.range(0, count)
                .mapToObj(i -> new Customer(faker.name().fullName(), faker.internet().emailAddress()))
                .toList();

        for (Customer customer : customers) {
            try {
                repo.saveAndFlush(customer);
            } catch (DataIntegrityViolationException e) {
                log.warn("Skipping duplicate entry: name - {} and email - {}",
                        customer.getName(), customer.getEmail());
            }
        }

        System.out.println("Current total customers: " + repo.count());
    }

}
