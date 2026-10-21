package org.example.questions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.example.ui.ProductsPage;

/** What the products page currently shows, in display order. */
public class ProductList {

    public static Question<List<String>> names() {
        return actor -> new ArrayList<>(Text.ofEach(ProductsPage.ITEM_NAMES).answeredBy(actor));
    }

    public static Question<List<BigDecimal>> prices() {
        return actor -> Text.ofEach(ProductsPage.ITEM_PRICES).answeredBy(actor).stream()
                .map(Money::parse)
                .collect(Collectors.toList());
    }

    public static Question<List<String>> imageSources() {
        return actor -> ProductsPage.ITEM_IMAGES.resolveAllFor(actor).stream()
                .map(img -> img.getAttribute("src"))
                .collect(Collectors.toList());
    }
}
