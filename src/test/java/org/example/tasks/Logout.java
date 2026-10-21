package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import org.example.ui.ProductsPage;

public class Logout implements Task {
    public static Logout now() { return Tasks.instrumented(Logout.class); }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(ProductsPage.MENU_BUTTON),
                Click.on(ProductsPage.LOGOUT_LINK)
        );
    }
}
