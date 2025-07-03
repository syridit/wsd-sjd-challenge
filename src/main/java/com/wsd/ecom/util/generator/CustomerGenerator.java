package com.wsd.ecom.util.generator;

import com.wsd.ecom.entity.Customer;
import com.wsd.ecom.repository.CustomerRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 03 July, 2025
 */

@Component
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

        repo.saveAllAndFlush(customers);

        System.out.println("Current total customers: " + repo.count());
    }

}
