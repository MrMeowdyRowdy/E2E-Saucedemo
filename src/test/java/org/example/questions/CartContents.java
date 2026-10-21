package org.example.questions;

import java.util.ArrayList;
import java.util.List;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.example.ui.CartPage;

public class CartContents {

    public static Question<List<String>> itemNames() {
        return actor -> new ArrayList<>(Text.ofEach(CartPage.ITEM_NAMES).answeredBy(actor));
    }
}
