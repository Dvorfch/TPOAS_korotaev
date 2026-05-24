package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServiceTest {
    private CalculatorService service;

    @BeforeEach
    public void setUp() {
        service = new CalculatorService();
    }

    @Test
    public void testCalculatePositiveValues() {
        // v0=2, r=6, a=2 -> (-2 + sqrt(4 + 12)) / 2 = (-2 + 4) / 2 = 1.0
        double result = service.calculate(2.0, 6.0, 2.0);
        assertEquals(1.0, result, 0.001, "Стандартный расчет с положительными числами неверный");
    }

    @Test
    public void testCalculateZeroV0() {
        // v0=0, r=4, a=1 -> (-0 + sqrt(0 + 4)) / 1 = 2.0
        double result = service.calculate(0.0, 4.0, 1.0);
        assertEquals(2.0, result, 0.001);
    }

    @Test
    public void testCalculateNegativeV0() {
        // v0=-2, r=12, a=1 -> (-(-2) + sqrt(4 + 12)) / 1 = (2 + 4) / 1 = 6.0
        double result = service.calculate(-2.0, 12.0, 1.0);
        assertEquals(6.0, result, 0.001);
    }

    @Test
    public void testCalculateThrowsExceptionOnDivisionByZero() {
        // Предел 'a' равен 0 -> Ожидаем ArithmeticException
        assertThrows(ArithmeticException.class, () -> {
            service.calculate(2.0, 4.0, 0.0);
        });
    }

    @Test
    public void testCalculateThrowsExceptionOnNegativeDiscriminant() {
        // Под корнем: v0^2 + a*r -> 1 + (2 * -5) = -9 -> Ожидаем IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            service.calculate(1.0, -5.0, 2.0);
        });
    }

    @Test
    public void testErrorCounterInitialState() {
        assertEquals(0, service.getErrorCounter(), "Начальное состояние счетчика должно быть 0");
    }

    @Test
    public void testIncrementErrorsFirstTime() {
        // Первая ошибка — лимит в 2 попытки еще не достигнут (должно вернуться false)
        boolean limitReached = service.incrementErrorsAndCheckLimit();
        assertFalse(limitReached);
        assertEquals(1, service.getErrorCounter());
    }

    @Test
    public void testIncrementErrorsSecondsTimeReachesLimit() {
        service.incrementErrorsAndCheckLimit(); // Первая ошибка
        boolean limitReached = service.incrementErrorsAndCheckLimit(); // Вторая ошибка

        assertTrue(limitReached, "При двух ошибках метод должен сообщить о достижении лимита");
        assertEquals(2, service.getErrorCounter());
    }

    @Test
    public void testResetErrorCounter() {
        service.incrementErrorsAndCheckLimit();
        service.incrementErrorsAndCheckLimit();

        service.resetErrorCounter();
        assertEquals(0, service.getErrorCounter(), "Счетчик ошибок не сбросился в 0");
    }

    @Test
    public void testResetOnSuccessExecutionFlow() {
        service.incrementErrorsAndCheckLimit();
        assertEquals(1, service.getErrorCounter());

        // Моделируем успешный расчет параметров
        double result = service.calculate(2.0, 0.0, 2.0); // (-2 + 2) / 2 = 0
        service.resetErrorCounter();

        assertEquals(0.0, result, 0.001);
        assertEquals(0, service.getErrorCounter(), "Счетчик ошибок должен обнуляться при успешном вводе");
    }
}