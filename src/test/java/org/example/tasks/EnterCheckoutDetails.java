package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.example.ui.CartPage;
import org.example.ui.CheckoutInfoPage;

/** From the cart: start checkout, fill the customer form and press Continue. */
public class EnterCheckoutDetails implements Task {
    private final String firstName, lastName, postalCode;

    public EnterCheckoutDetails(String firstName, String lastName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public static EnterCheckoutDetails with(String first, String last, String postal) {
        return Tasks.instrumented(EnterCheckoutDetails.class, first, last, postal);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(CartPage.CHECKOUT_BUTTON),
                Enter.theValue(firstName).into(CheckoutInfoPage.FIRST_NAME),
                Enter.theValue(lastName).into(CheckoutInfoPage.LAST_NAME),
                Enter.theValue(postalCode).into(CheckoutInfoPage.POSTAL_CODE),
                Click.on(CheckoutInfoPage.CONTINUE_BUTTON)
        );
    }
}
