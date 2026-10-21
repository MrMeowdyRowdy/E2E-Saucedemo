package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ProductsPage {

    public static final Target CART_LINK = Target.the("shopping cart link")
            .located(By.className("shopping_cart_link"));

    public static final Target ITEM_NAMES = Target.the("product names")
            .located(By.className("inventory_item_name"));

    public static final Target ITEM_PRICES = Target.the("product prices")
            .located(By.className("inventory_item_price"));

    public static final Target ITEM_IMAGES = Target.the("product images")
            .located(By.cssSelector(".inventory_item_img img"));

    public static final Target CART_BADGE = Target.the("cart badge")
            .located(By.className("shopping_cart_badge"));

    public static final Target SORT_DROPDOWN = Target.the("sort dropdown")
            .located(By.cssSelector("[data-test='product-sort-container']"));

    public static final Target MENU_BUTTON = Target.the("menu button")
            .located(By.id("react-burger-menu-btn"));

    public static final Target LOGOUT_LINK = Target.the("logout link")
            .located(By.id("logout_sidebar_link"));

    public static Target removeButtonFor(String productName) {
        return Target.the("Remove button for " + productName)
                .located(By.id("remove-" + slug(productName)));
    }

    public static Target addButtonFor(String productName) {
        return Target.the("Add to cart button for " + productName)
                .located(By.id("add-to-cart-" + slug(productName)));
    }

    private static String slug(String productName) {
        return productName
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-");
    }
}
