package org.example.tasks;

import java.util.List;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import org.example.ui.ProductsPage;

public class AddProductToCart implements Task {

    private final List<String> products;

    public AddProductToCart(List<String> products) {
        this.products = products;
    }

    public static AddProductToCart called(List<String> products) {
        return Tasks.instrumented(AddProductToCart.class, products);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        for (String product : products) {
            actor.attemptsTo(Click.on(ProductsPage.addButtonFor(product)));
        }
    }
}
