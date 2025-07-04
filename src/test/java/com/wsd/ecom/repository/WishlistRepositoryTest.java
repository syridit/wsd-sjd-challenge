package com.wsd.ecom.repository;

import com.wsd.ecom.dto.WishlistDto;
import com.wsd.ecom.entity.BaseEntity;
import com.wsd.ecom.entity.Customer;
import com.wsd.ecom.entity.Product;
import com.wsd.ecom.entity.Wishlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

public class WishlistRepositoryTest extends DatabaseIntegrationTest {

    private final WishlistRepository wishlistRepository;

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishlistRepositoryTest(WishlistRepository wishlistRepository,
                                  CustomerRepository customerRepository,
                                  ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Test
    void shouldFindWishlistByCustomerId() {
        // Given
        Customer alice = new Customer("Alice", "alice@example.com");
        Customer bob = new Customer("Bob", "bob@example.com");
        customerRepository.save(alice);
        customerRepository.save(bob);

        Product p1 = new Product("Keyboard", new BigDecimal("50"));
        Product p2 = new Product("Monitor", new BigDecimal("150"));
        Product p3 = new Product("Mouse", new BigDecimal("150"));
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);


        wishlistRepository.save(new Wishlist(alice.getId(), p1.getId()));
        wishlistRepository.save(new Wishlist(alice.getId(), p2.getId()));
        wishlistRepository.save(new Wishlist(bob.getId(), p3.getId()));

        // When
        Pageable pageable = PageRequest.of(0, 10);
        Page<WishlistDto> wishlist = wishlistRepository.findWishlistByCustomerId(alice.getId(), pageable);

        // Then
        assertThat(wishlist).hasSize(2);
    }

    @Test
    void shouldReturnSortedWishlistByCustomerIdIfSortParameterSentInPageable() {
        // Given
        Customer alice = new Customer("Alice", "alice@example.com");
        Customer bob = new Customer("Bob", "bob@example.com");
        customerRepository.save(alice);
        customerRepository.save(bob);

        Product p1 = new Product("Keyboard", new BigDecimal("50"));
        Product p2 = new Product("Monitor", new BigDecimal("150"));
        Product p3 = new Product("Mouse", new BigDecimal("150"));
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);


        wishlistRepository.save(new Wishlist(alice.getId(), p1.getId()));
        wishlistRepository.save(new Wishlist(alice.getId(), p2.getId()));
        wishlistRepository.save(new Wishlist(bob.getId(), p3.getId()));

        // When
        Pageable pageable = PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, BaseEntity.Fields.createdAt));
        Page<WishlistDto> wishlist = wishlistRepository.findWishlistByCustomerId(alice.getId(), pageable);

        // Then
        assertThat(wishlist).hasSize(2);
        assertThat(wishlist.getContent().get(0).getProductId()).isEqualTo(p2.getId());
        assertThat(wishlist.getContent().get(1).getProductId()).isEqualTo(p1.getId());
    }

    @Test
    void shouldReturnEmptyWishlistByUnknownCustomerId() {
        // Given
        Customer alice = new Customer("Alice", "alice@example.com");
        customerRepository.save(alice);

        Product p1 = new Product("Keyboard", new BigDecimal("50"));
        Product p2 = new Product("Monitor", new BigDecimal("150"));
        productRepository.save(p1);
        productRepository.save(p2);


        wishlistRepository.save(new Wishlist(alice.getId(), p1.getId()));
        wishlistRepository.save(new Wishlist(alice.getId(), p2.getId()));

        // When
        Pageable pageable = PageRequest.of(0, 10);
        Page<WishlistDto> wishlist = wishlistRepository
                .findWishlistByCustomerId(alice.getId() + 1, pageable);

        // Then
        assertThat(wishlist).isEmpty();
    }

}
