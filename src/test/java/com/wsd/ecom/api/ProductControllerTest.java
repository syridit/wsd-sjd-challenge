package com.wsd.ecom.api;

import com.wsd.ecom.dto.TopSellingProductByAmountDto;
import com.wsd.ecom.dto.TopSellingProductByQuantityDto;
import com.wsd.ecom.service.SalesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest extends BaseControllerTest {

    @MockBean
    private SalesService salesService;

    private static final String GET_TOP_SELLING_ALL_TIME = "/api/products/top-selling/all-time/by-amount";
    private static final String GET_TOP_SELLING_LAST_MONTH = "/api/products/top-selling/last-month/by-quantity";

    @Autowired
    public ProductControllerTest(MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    void shouldReturnTop5SellingProductsAllTime() throws Exception {
        List<TopSellingProductByAmountDto> expectedList = List.of(
                TopSellingProductByAmountDto.builder().productId(1L).totalSales(new BigDecimal("1200.00")).build(),
                TopSellingProductByAmountDto.builder().productId(2L).totalSales(new BigDecimal("1100.00")).build(),
                TopSellingProductByAmountDto.builder().productId(3L).totalSales(new BigDecimal("1000.00")).build(),
                TopSellingProductByAmountDto.builder().productId(4L).totalSales(new BigDecimal("900.00")).build(),
                TopSellingProductByAmountDto.builder().productId(5L).totalSales(new BigDecimal("800.00")).build()
        );

        when(salesService.getAllTimeTopFiveProductsBySalesAmount())
                .thenReturn(expectedList);


        ResultActions resultActions = get(GET_TOP_SELLING_ALL_TIME, null);

        assertHttpStatus(resultActions, HttpStatus.OK);
    }

    @Test
    void shouldInvokeServiceWhileGettingTopSellingAllTime() throws Exception {
        get(GET_TOP_SELLING_ALL_TIME, null);
        verify(salesService).getAllTimeTopFiveProductsBySalesAmount();
    }

    @Test
    void shouldReturnTop5SellingProductsLastMonthByQuantity() throws Exception {
        List<TopSellingProductByQuantityDto> expectedList = List.of(
                TopSellingProductByQuantityDto.builder().productId(1L).totalCount(200L).build(),
                TopSellingProductByQuantityDto.builder().productId(2L).totalCount(180L).build(),
                TopSellingProductByQuantityDto.builder().productId(3L).totalCount(150L).build(),
                TopSellingProductByQuantityDto.builder().productId(4L).totalCount(120L).build(),
                TopSellingProductByQuantityDto.builder().productId(5L).totalCount(100L).build()
        );

        when(salesService.getLastMonthTopFiveProductsByQuantity())
                .thenReturn(expectedList);


        ResultActions resultActions = get(GET_TOP_SELLING_LAST_MONTH, null);

        assertHttpStatus(resultActions, HttpStatus.OK);
    }

    @Test
    void shouldInvokeServiceWhileGettingTopSellingLastMonth() throws Exception {
        get(GET_TOP_SELLING_LAST_MONTH, null);
        verify(salesService).getLastMonthTopFiveProductsByQuantity();
    }

}
