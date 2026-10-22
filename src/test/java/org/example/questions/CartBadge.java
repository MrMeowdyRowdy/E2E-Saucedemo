package org.example.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.questions.Text;
import org.example.ui.ProductsPage;

public class CartBadge {

    public static Question<Integer> count() {
        return actor -> Integer.parseInt(Text.of(ProductsPage.CART_BADGE).answeredBy(actor).trim());
    }

    public static Question<Boolean> isShown() {
        return Presence.of(ProductsPage.CART_BADGE);
    }
}
