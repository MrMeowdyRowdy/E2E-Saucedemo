package org.example.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.example.ui.ProductsPage;

public class CartBadge {

    /** Number on the cart badge; 0 when the badge is not displayed. */
    public static Question<Integer> count() {
        return actor -> ProductsPage.CART_BADGE.resolveAllFor(actor).isEmpty()
                ? 0
                : Integer.parseInt(Text.of(ProductsPage.CART_BADGE).answeredBy(actor).trim());
    }
}
