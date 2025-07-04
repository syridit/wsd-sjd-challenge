package com.wsd.ecom.api;

import com.wsd.ecom.dto.TopSellingProductInfo;
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

    private static final String GET_TOP_SELLING_ALL_TIME = "/api/products/top-selling/all-time";

    @Autowired
    public ProductControllerTest(MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    void shouldReturnTop5SellingProductsAllTime() throws Exception {
        List<TopSellingProductInfo> expectedList = List.of(
                TopSellingProductInfo.builder().productId(1L).totalSales(new BigDecimal("1200.00")).build(),
                TopSellingProductInfo.builder().productId(2L).totalSales(new BigDecimal("1100.00")).build(),
                TopSellingProductInfo.builder().productId(3L).totalSales(new BigDecimal("1000.00")).build(),
                TopSellingProductInfo.builder().productId(4L).totalSales(new BigDecimal("900.00")).build(),
                TopSellingProductInfo.builder().productId(5L).totalSales(new BigDecimal("800.00")).build()
        );

        when(salesService.getAllTimeTopFiveProductsBySalesAmount())
                .thenReturn(expectedList);


        ResultActions resultActions = get(GET_TOP_SELLING_ALL_TIME, null);

        assertHttpStatus(resultActions, HttpStatus.OK);
    }

    @Test
    void shouldInvokeServiceWhileGettingTotalSalesForToday() throws Exception {
        get(GET_TOP_SELLING_ALL_TIME, null);
        verify(salesService).getAllTimeTopFiveProductsBySalesAmount();
    }

}
