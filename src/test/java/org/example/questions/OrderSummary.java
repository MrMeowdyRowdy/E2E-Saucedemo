package org.example.questions;

import java.math.BigDecimal;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.example.ui.CheckoutOverviewPage;

/** Amounts on the checkout overview page. */
public class OrderSummary {

    public static Question<BigDecimal> itemPricesSum() {
        return actor -> Text.ofEach(CheckoutOverviewPage.ITEM_PRICES).answeredBy(actor).stream()
                .map(Money::parse)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Question<BigDecimal> itemTotal() {
        return actor -> Money.parse(Text.of(CheckoutOverviewPage.SUBTOTAL).answeredBy(actor));
    }

    public static Question<BigDecimal> tax() {
        return actor -> Money.parse(Text.of(CheckoutOverviewPage.TAX).answeredBy(actor));
    }

    public static Question<BigDecimal> total() {
        return actor -> Money.parse(Text.of(CheckoutOverviewPage.TOTAL).answeredBy(actor));
    }
}
