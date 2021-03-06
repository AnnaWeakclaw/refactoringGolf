package hole2;

import java.util.Arrays;
import java.util.List;

class TakeHomeCalculator {

    private final int percent;

    TakeHomeCalculator(int percent) {
        this.percent = percent;
    }

    Money netAmount(Money first, Money... rest) {

        List<Money> monies = Arrays.asList(rest);

        Money total = first;

        total = plus(monies, total);

        Double amount = total.value * (percent / 100d);
        Money tax = new Money(amount.intValue(), first.currency);

        if (!total.currency.equals(tax.currency)) {
            throw new Incalculable();
        }
        return new Money(total.value - tax.value, first.currency);
    }

    private Money plus(List<Money> monies, Money total) {
        for (Money next : monies) {
            total = plus(total, next);
        }

        return total;
    }

    private Money plus(Money total, Money next) {
        if (!next.currency.equals(total.currency)) {
            throw new Incalculable();
        }
        total = new Money(total.value + next.value, next.currency);
        return total;
    }

    static class Money {
        final Integer value;
        final String currency;

        Money(Integer value, String currency) {
            this.value = value;
            this.currency = currency;
        }

    }
}
