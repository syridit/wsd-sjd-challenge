package com.wsd.ecom.service;

import com.wsd.ecom.dto.WishlistDto;
import com.wsd.ecom.entity.Product;
import com.wsd.ecom.entity.Wishlist;
import com.wsd.ecom.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 03 July, 2025
 */

@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @Test
    void shouldReturnWishlistForCustomer() {
        // Given
        Long customerId = 123L;
        List<WishlistDto> expectedWishlist = List.of(
                WishlistDto.builder().customerId(customerId).productId(456L).build()
        );

        Page<WishlistDto> pageResponse = new PageImpl<>(expectedWishlist);

        when(wishlistRepository.findWishlistByCustomerId(eq(customerId), any(Pageable.class)))
                .thenReturn(pageResponse);

        // When
        Page<WishlistDto> actual = wishlistService.getWishlistForCustomer(customerId,
                PageRequest.of(0, 10));

        // Then
        assertFalse(actual.isEmpty());
        assertEquals(expectedWishlist.size(), actual.getNumberOfElements());
        assertEquals(expectedWishlist.getFirst().getCustomerId(), customerId);
    }

}
