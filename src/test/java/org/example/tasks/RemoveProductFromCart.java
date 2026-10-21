package org.example.tasks;

import java.util.List;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import org.example.ui.ProductsPage;

public class RemoveProductFromCart implements Task {

    private final List<String> products;

    public RemoveProductFromCart(List<String> products) {
        this.products = products;
    }

    public static RemoveProductFromCart called(List<String> products) {
        return Tasks.instrumented(RemoveProductFromCart.class, products);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        for (String product : products) {
            actor.attemptsTo(Click.on(ProductsPage.removeButtonFor(product)));
        }
    }
}
