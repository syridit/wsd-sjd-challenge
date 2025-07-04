package com.wsd.ecom.repository;

import com.wsd.ecom.entity.Customer;
import com.wsd.ecom.entity.Product;
import com.wsd.ecom.entity.Sales;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

public class SalesRepositoryTest extends DatabaseIntegrationTest {

    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public SalesRepositoryTest(SalesRepository salesRepository,
                               CustomerRepository customerRepository,
                               ProductRepository productRepository) {
        this.salesRepository = salesRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Test
    void shouldReturnTotalSalesOfToday() {
        // Given
        Customer alice = new Customer("Alice", "alice@example.com");
        Customer bob = new Customer("Bob", "bob@example.com");
        customerRepository.save(alice);
        customerRepository.save(bob);

        Product p1 = new Product("Keyboard", new BigDecimal("50"));
        Product p2 = new Product("Monitor", new BigDecimal("150"));
        Product p3 = new Product("Mouse", new BigDecimal("250"));
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);


        salesRepository.save(new Sales(p1.getId(), alice.getId(), 10L));
        salesRepository.save(new Sales(p1.getId(), bob.getId(), 20L));
        salesRepository.save(new Sales(p2.getId(), alice.getId(), 5L));
        salesRepository.save(new Sales(p3.getId(), bob.getId(), 1L));

        LocalDate today = LocalDate.now();
        BigDecimal expectedPrice = new BigDecimal("2500.00");

        // When
        BigDecimal totalPrice = salesRepository.sumSalesAmountBetween(today.atStartOfDay(),
                today.plusDays(1).atStartOfDay());

        // Then
        assertThat(totalPrice).isEqualTo(expectedPrice);
    }

    @Test
    void shouldReturnTotalSalesOfTodayWhenDataHasDifferentDays() {
        // Given
        LocalDateTime today = LocalDateTime.now();

        Customer alice = new Customer("Alice", "alice@example.com");
        Customer bob = new Customer("Bob", "bob@example.com");
        customerRepository.save(alice);
        customerRepository.save(bob);

        Product p1 = new Product("Keyboard", new BigDecimal("50"));
        Product p2 = new Product("Monitor", new BigDecimal("150"));
        Product p3 = new Product("Mouse", new BigDecimal("250"));
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);

        Sales s1 = new Sales(p1.getId(), alice.getId(), 10L);
        Sales s2 = new Sales(p1.getId(), bob.getId(), 20L);
        Sales s3 = new Sales(p2.getId(), alice.getId(), 5L);
        Sales s4 = new Sales(p3.getId(), bob.getId(), 1L);

        s1.setCreatedAt(today.minusDays(2));
        s3.setCreatedAt(today.minusDays(1));

        salesRepository.save(s1);
        salesRepository.save(s2);
        salesRepository.save(s3);
        salesRepository.save(s4);

        s1.setCreatedAt(today.minusDays(2));
        s3.setCreatedAt(today.minusDays(1));

        salesRepository.save(s1);
        salesRepository.save(s3);

        BigDecimal expectedPrice = new BigDecimal("1250.00");

        // When
        BigDecimal totalPrice = salesRepository.sumSalesAmountBetween(today.toLocalDate().atStartOfDay(),
                today.plusDays(1).toLocalDate().atStartOfDay());

        // Then
        assertThat(totalPrice).isEqualTo(expectedPrice);
    }

}
