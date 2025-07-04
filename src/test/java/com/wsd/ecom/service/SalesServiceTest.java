package com.wsd.ecom.service;

import com.wsd.ecom.dto.SalesTodayDto;
import com.wsd.ecom.repository.SalesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author Md. Sadman Yasar Ridit
 * @email syridit.prof@gmail.com
 * @since 04 July, 2025
 */

@ExtendWith(MockitoExtension.class)
public class SalesServiceTest {

    @Mock
    private SalesRepository salesRepository;

    @InjectMocks
    private SalesService salesService;

    @Test
    void shouldReturnTotalSalesOfToday() {
        // Given
        BigDecimal expectedSales = new BigDecimal("1200.00");

        LocalDate today = LocalDate.now();

        when(salesRepository.sumSalesAmountBetween(eq(today.atStartOfDay()),
                eq(today.plusDays(1).atStartOfDay())))
                .thenReturn(expectedSales);

        // When
        SalesTodayDto actual = salesService.getTotalSalesAmountOfToday();

        // Then
        assertEquals(actual.getTotalSales(), expectedSales);
    }

}
