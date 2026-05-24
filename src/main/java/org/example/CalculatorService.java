package org.example;

public class CalculatorService {
    private int errorCounter = 0;

    /**
     * Вычисляет значение по формуле: (-v0 + sqrt(v0^2 + a * r)) / a
     */
    public double calculate(double v0, double r, double a) {
        if (a == 0) {
            throw new ArithmeticException("Деление на ноль: параметр 'a' не должен быть равен 0.");
        }
        double discriminant = (v0 * v0) + (a * r);
        if (discriminant < 0) {
            throw new IllegalArgumentException("Выражение под корнем меньше нуля.");
        }
        return (-v0 + Math.sqrt(discriminant)) / a;
    }

    /**
     * Фиксирует ошибку парсинга. Возвращает true, если достигнут лимит (2 или более ошибок).
     */
    public boolean incrementErrorsAndCheckLimit() {
        errorCounter++;
        return errorCounter >= 2;
    }

    public void resetErrorCounter() {
        this.errorCounter = 0;
    }

    public int getErrorCounter() {
        return this.errorCounter;
    }
}