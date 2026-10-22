package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import org.example.ui.CheckoutOverviewPage;

/** From the cart: fill the customer form and place the order. */
public class CompleteCheckout implements Task {
    private final String firstName, lastName, postalCode;

    public CompleteCheckout(String firstName, String lastName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public static CompleteCheckout with(String first, String last, String postal) {
        return Tasks.instrumented(CompleteCheckout.class, first, last, postal);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnterCheckoutDetails.with(firstName, lastName, postalCode),
                Click.on(CheckoutOverviewPage.FINISH_BUTTON)
        );
    }
}
