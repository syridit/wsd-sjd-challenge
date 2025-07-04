package com.wsd.ecom.api;

import com.wsd.ecom.dto.WishlistDto;
import com.wsd.ecom.entity.BaseEntity;
import com.wsd.ecom.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest extends BaseControllerTest {

    @MockBean
    private WishlistService wishlistService;

    private static final String GET_WISHLIST_BY_CUSTOMER_ID = "/api/customers/{customerId}/wishlist";

    @Autowired
    public CustomerControllerTest(MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    void shouldReturnWishlistForCustomer() throws Exception {
        Long customerId = 1L;

        List<WishlistDto> mockWishlist = List.of(
                new WishlistDto(1L, "iPhone 14",
                        new BigDecimal("1299.99"), 11L, LocalDateTime.now()),
                new WishlistDto(1L, "MacBook Air",
                        new BigDecimal("999.99"), 12L, LocalDateTime.now().minusDays(1))
        );

        when(wishlistService.getWishlistForCustomer(eq(customerId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(mockWishlist));

        ResultActions resultActions = get(GET_WISHLIST_BY_CUSTOMER_ID, null, customerId);

        assertHttpStatus(resultActions, HttpStatus.OK);
    }


    @Test
    void shouldThrowExceptionWhenCustomerIdIsNotNumericWhileGettingWishlistForCustomer() throws Exception {
        String customerId = "abc";

        ResultActions resultActions = get(GET_WISHLIST_BY_CUSTOMER_ID, null, customerId);

        assertHttpStatus(resultActions, HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldInvokeServiceWithSortedPageableWhileGettingWishlistForCustomer() throws Exception {
        Long customerId = 1L;
        Pageable expectedPageable = PageRequest.of(0, 20)
                .withSort(Sort.by(Sort.Direction.DESC, BaseEntity.Fields.createdAt));

        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("page", "0");
        queryParams.put("size", "20");
        queryParams.put("sort", "createdAt,desc");

        get(GET_WISHLIST_BY_CUSTOMER_ID, queryParams, customerId);

        verify(wishlistService).getWishlistForCustomer(eq(customerId), eq(expectedPageable));
    }


}
