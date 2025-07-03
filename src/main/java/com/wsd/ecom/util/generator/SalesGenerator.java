package com.wsd.ecom.util.generator;

import com.wsd.ecom.entity.Customer;
import com.wsd.ecom.entity.Product;
import com.wsd.ecom.entity.Sales;
import com.wsd.ecom.repository.CustomerRepository;
import com.wsd.ecom.repository.ProductRepository;
import com.wsd.ecom.repository.SalesRepository;
import net.datafaker.Faker;
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
public class SalesGenerator {

    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final SalesRepository repo;
    private final Faker faker = new Faker();

    public SalesGenerator(SalesRepository repo,
                          CustomerRepository customerRepo,
                          ProductRepository productRepo) {
        this.repo = repo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    public void generate(int count) {
        List<Customer> customers = customerRepo.findAll();
        List<Product> products = productRepo.findAll();

        Random random = new Random();
        List<Sales> sales = IntStream.range(0, count)
                .mapToObj(i -> {
                    Customer customer = customers.get(random.nextInt(customers.size()));
                    Product product = products.get(random.nextInt(products.size()));
                    int quantity = random.nextInt(5) + 1;

                    return new Sales(product.getId(), customer.getId(), (long) quantity);
                })
                .toList();

        repo.saveAllAndFlush(sales);


        System.out.println(repo.findAll());
        System.out.println("Current total sales: " + repo.count());
    }

}
