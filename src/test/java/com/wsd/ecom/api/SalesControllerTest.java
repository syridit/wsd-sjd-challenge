package com.wsd.ecom.api;

import com.wsd.ecom.dto.MaxSaleDayDto;
import com.wsd.ecom.dto.SalesTodayDto;
import com.wsd.ecom.service.SalesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@WebMvcTest(controllers = SalesController.class)
public class SalesControllerTest extends BaseControllerTest {

    @MockBean
    private SalesService salesService;

    private static final String GET_TOTAL_SALES_OF_TODAY = "/api/sales/today/total";
    private static final String GET_MAX_SALE_DAY = "/api/sales//max-sale-day";

    @Autowired
    public SalesControllerTest(MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    void shouldReturnTotalSalesOfToday() throws Exception {
        SalesTodayDto expected = SalesTodayDto.builder()
                .totalSales(new BigDecimal("1000.00"))
                .build();

        when(salesService.getTotalSalesAmountOfToday())
                .thenReturn(expected);

        String expectedResponseJson = """
                {
                    "totalSales": 1000.00
                }
                """;

        ResultActions resultActions = get(GET_TOTAL_SALES_OF_TODAY, null);

        assertHttpStatus(resultActions, HttpStatus.OK);
        assertJsonResponseBody(resultActions, expectedResponseJson);
    }

    @Test
    void shouldInvokeServiceWhileGettingTotalSalesForToday() throws Exception {
        get(GET_TOTAL_SALES_OF_TODAY, null);
        verify(salesService).getTotalSalesAmountOfToday();
    }


    @Test
    void shouldReturnMaxSaleDayFromGivenRange() throws Exception {
        LocalDateTime from = LocalDateTime.now().minusDays(5);
        LocalDateTime to = LocalDateTime.now();
        MaxSaleDayDto expected = MaxSaleDayDto.builder()
                .maxSalesDay(LocalDate.now().minusDays(3))
                .build();
        String expectedJsonResponse = """
                {
                    "from": null,
                    "maxSalesDay": "2025-07-01",
                    "to": null
                }
                """;
        when(salesService.getMaxSalesDay(eq(from), eq(to)))
                .thenReturn(expected);

        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("from", from.toString());
        queryParams.put("to", to.toString());

        ResultActions resultActions = get(GET_MAX_SALE_DAY, queryParams);

        assertHttpStatus(resultActions, HttpStatus.OK);
        assertJsonResponseBody(resultActions, expectedJsonResponse);
    }

    @Test
    void shouldInvokeServiceWhileGettingMaxSaleDay() throws Exception {
        LocalDateTime from = LocalDateTime.now().minusDays(5);
        LocalDateTime to = LocalDateTime.now();
        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("from", from.toString());
        queryParams.put("to", to.toString());

        get(GET_MAX_SALE_DAY, queryParams);
        verify(salesService).getMaxSalesDay(eq(from), eq(to));
    }


    @Test
    void shouldThrowExceptionWhenGettingMaxSaleDayFromGivenRangeAndParameterMissing() throws Exception {
        LocalDateTime from = LocalDateTime.now().minusDays(5);

        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("from", from.toString());

        ResultActions resultActions = get(GET_MAX_SALE_DAY, queryParams);

        assertHttpStatus(resultActions, HttpStatus.BAD_REQUEST);
    }

}
