package com.wsd.ecom.repository;

import com.wsd.ecom.dto.TopSellingProductInfo;
import com.wsd.ecom.entity.Customer;
import com.wsd.ecom.entity.Product;
import com.wsd.ecom.entity.Sales;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

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




    @Test
    void shouldReturnMaxSalesDayOfCertainDateRange() {
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
        Sales s5 = new Sales(p3.getId(), bob.getId(), 10L);
        Sales s6 = new Sales(p3.getId(), bob.getId(), 2L);
        Sales s7 = new Sales(p2.getId(), bob.getId(), 1L);
        Sales s8 = new Sales(p1.getId(), bob.getId(), 1L);

        salesRepository.save(s1);
        salesRepository.save(s2);
        salesRepository.save(s3);
        salesRepository.save(s4);
        salesRepository.save(s5);
        salesRepository.save(s6);
        salesRepository.save(s7);
        salesRepository.save(s8);

        s1.setCreatedAt(today.minusDays(2));
        s3.setCreatedAt(today.minusDays(1));
        s5.setCreatedAt(today.minusDays(3));
        s8.setCreatedAt(today.minusDays(3));

        salesRepository.save(s1);
        salesRepository.save(s3);
        salesRepository.save(s5);
        salesRepository.save(s8);

        LocalDate expectedDate = LocalDate.now().minusDays(3);

        // When
        LocalDate actual = salesRepository.findDayWithMaxSales(LocalDateTime.now().minusDays(5), LocalDateTime.now());

        // Then
        assertThat(actual).isEqualTo(expectedDate);
    }
    @Test
    void shouldReturnNullWhenCertainDateRangeOutOfExistingData() {
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
        Sales s5 = new Sales(p3.getId(), bob.getId(), 10L);
        Sales s6 = new Sales(p3.getId(), bob.getId(), 2L);
        Sales s7 = new Sales(p2.getId(), bob.getId(), 1L);
        Sales s8 = new Sales(p1.getId(), bob.getId(), 1L);

        salesRepository.save(s1);
        salesRepository.save(s2);
        salesRepository.save(s3);
        salesRepository.save(s4);
        salesRepository.save(s5);
        salesRepository.save(s6);
        salesRepository.save(s7);
        salesRepository.save(s8);

        s1.setCreatedAt(today.minusDays(2));
        s3.setCreatedAt(today.minusDays(1));
        s5.setCreatedAt(today.minusDays(3));
        s8.setCreatedAt(today.minusDays(3));

        salesRepository.save(s1);
        salesRepository.save(s3);
        salesRepository.save(s5);
        salesRepository.save(s8);


        // When
        LocalDate actual = salesRepository.findDayWithMaxSales(LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(7));

        // Then
        assertThat(actual).isNull();
    }

    @Test
    void shouldReturnTop5SellingItem() {
        // Given

        Customer alice = new Customer("Alice", "alice@example.com");
        Customer bob = new Customer("Bob", "bob@example.com");
        customerRepository.save(alice);
        customerRepository.save(bob);

        Product p1 = new Product("Keyboard", new BigDecimal("50"));
        Product p2 = new Product("Monitor", new BigDecimal("150"));
        Product p3 = new Product("Mouse", new BigDecimal("250"));
        Product p4 = new Product("USB", new BigDecimal("20"));
        Product p5 = new Product("HDD", new BigDecimal("550"));
        Product p6 = new Product("SSD", new BigDecimal("750"));
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
        productRepository.save(p5);
        productRepository.save(p6);

        Sales s1 = new Sales(p1.getId(), alice.getId(), 10L);
        Sales s2 = new Sales(p1.getId(), bob.getId(), 20L);
        Sales s3 = new Sales(p2.getId(), alice.getId(), 5L);
        Sales s4 = new Sales(p3.getId(), bob.getId(), 1L);
        Sales s5 = new Sales(p3.getId(), bob.getId(), 10L);
        Sales s6 = new Sales(p4.getId(), bob.getId(), 2L);
        Sales s7 = new Sales(p5.getId(), bob.getId(), 1L);
        Sales s8 = new Sales(p6.getId(), bob.getId(), 1L);

        salesRepository.save(s1);
        salesRepository.save(s2);
        salesRepository.save(s3);
        salesRepository.save(s4);
        salesRepository.save(s5);
        salesRepository.save(s6);
        salesRepository.save(s7);
        salesRepository.save(s8);

        List<Long> expectedProductIdList = List.of(p3.getId(), p1.getId(), p2.getId(), p6.getId(), p5.getId());

        // When
        List<TopSellingProductInfo> actual = salesRepository.findTopSellingProductsByAmount(Pageable.ofSize(5));

        // Then
        assertThat(actual.size()).isEqualTo(5);
        assertThat(actual.get(0).getProductId()).isEqualTo(p3.getId());
        assertThat(actual.stream()
                .filter(x -> expectedProductIdList.contains(x.getProductId()))
                .toList()
                .size()).isEqualTo(5);
    }

    @Test
    void shouldReturnTopSellingItemsEventIfLessThanFive() {
        // Given

        Customer alice = new Customer("Alice", "alice@example.com");
        Customer bob = new Customer("Bob", "bob@example.com");
        customerRepository.save(alice);
        customerRepository.save(bob);

        Product p1 = new Product("Keyboard", new BigDecimal("50"));
        Product p2 = new Product("Monitor", new BigDecimal("150"));
        Product p3 = new Product("Mouse", new BigDecimal("250"));
        Product p4 = new Product("USB", new BigDecimal("20"));
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);

        Sales s1 = new Sales(p1.getId(), alice.getId(), 10L);
        Sales s2 = new Sales(p1.getId(), bob.getId(), 20L);
        Sales s3 = new Sales(p2.getId(), alice.getId(), 5L);
        Sales s4 = new Sales(p3.getId(), bob.getId(), 1L);
        Sales s5 = new Sales(p3.getId(), bob.getId(), 10L);
        Sales s6 = new Sales(p4.getId(), bob.getId(), 2L);

        salesRepository.save(s1);
        salesRepository.save(s2);
        salesRepository.save(s3);
        salesRepository.save(s4);
        salesRepository.save(s5);
        salesRepository.save(s6);

        List<Long> expectedProductIdList = List.of(p3.getId(), p1.getId(), p2.getId(), p4.getId());

        // When
        List<TopSellingProductInfo> actual = salesRepository.findTopSellingProductsByAmount(Pageable.ofSize(5));

        // Then
        assertThat(actual.size()).isEqualTo(4);
        assertThat(actual.get(0).getProductId()).isEqualTo(p3.getId());
        assertThat(actual.stream()
                .filter(x -> expectedProductIdList.contains(x.getProductId()))
                .toList()
                .size()).isEqualTo(4);
    }


}
