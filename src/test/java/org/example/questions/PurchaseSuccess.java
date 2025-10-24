package org.example.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.example.ui.CheckoutCompletePage;

public class PurchaseSuccess {

    public static Question<Boolean> isDisplayed() {
        return actor -> Text.of(CheckoutCompletePage.COMPLETE_HEADER)
                .answeredBy(actor)
                .contains("Thank you for your order!");
    }
}
