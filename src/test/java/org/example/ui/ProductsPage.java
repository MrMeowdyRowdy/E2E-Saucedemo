package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ProductsPage {

    public static final Target CART_LINK = Target.the("shopping cart link")
            .located(By.className("shopping_cart_link"));

    public static Target addButtonFor(String productName) {
        String id = "add-to-cart-" + productName
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]", "")
                .replace(" ", "-");

        return Target.the("Add to cart button for " + productName)
                .located(By.id(id));
    }
}
