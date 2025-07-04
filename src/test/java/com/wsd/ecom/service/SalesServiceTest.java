package com.wsd.ecom.service;

import com.wsd.ecom.dto.MaxSaleDayDto;
import com.wsd.ecom.dto.SalesTodayDto;
import com.wsd.ecom.repository.SalesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @Test
    void shouldReturnMaxSalesDayWhenGivenDateTimeRange() {
        // Given
        LocalDate expectedMaxSalesDay = LocalDate.now().minusDays(3);

        LocalDateTime from = LocalDateTime.now().minusDays(5);
        LocalDateTime to = LocalDateTime.now();

        when(salesRepository.findDayWithMaxSales(eq(from), eq(to)))
                .thenReturn(expectedMaxSalesDay);

        // When
        MaxSaleDayDto actual = salesService.getMaxSalesDay(from, to);

        // Then
        assertEquals(actual.getMaxSalesDay(), expectedMaxSalesDay);
    }

    @Test
    void shouldReturnNullMaxSalesDayWhenGivenDateTimeRange() {
        // Given

        LocalDateTime from = LocalDateTime.now().minusDays(5);
        LocalDateTime to = LocalDateTime.now();

        when(salesRepository.findDayWithMaxSales(eq(from), eq(to)))
                .thenReturn(null);
        // When
        MaxSaleDayDto actual = salesService.getMaxSalesDay(from, to);

        // Then
        assertThat(actual.getMaxSalesDay()).isNull();
    }

}
