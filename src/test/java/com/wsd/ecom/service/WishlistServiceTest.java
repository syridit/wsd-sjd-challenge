package com.wsd.ecom.service;

import com.wsd.ecom.dto.WishlistDto;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
        assertEquals(actual.getContent().getFirst().getCustomerId(), customerId);
    }

    @Test
    void shouldReturnNoWishlistForCustomerEmptyResultReturnedFromDb() {
        // Given
        when(wishlistRepository.findWishlistByCustomerId(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        // When
        Page<WishlistDto> actual = wishlistService.getWishlistForCustomer(123L,
                PageRequest.of(0, 10));

        // Then
        assertTrue(actual.isEmpty());
    }

}
