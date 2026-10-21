package org.example.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.example.ui.ErrorBanner;

public class ShownError {

    public static Question<String> message() {
        return actor -> Text.of(ErrorBanner.MESSAGE).answeredBy(actor);
    }
}
