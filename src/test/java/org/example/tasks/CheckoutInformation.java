package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import org.example.ui.CheckOutOverviewPage;

public class CheckoutInformation implements Task {
    private final String firstName, lastName, postalCode;

    public CheckoutInformation(String firstName, String lastName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public static CheckoutInformation with(String first, String last, String postal) {
        return Tasks.instrumented(CheckoutInformation.class, first, last, postal);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnterCheckoutDetails.with(firstName, lastName, postalCode),
                Click.on(CheckOutOverviewPage.FINISH_BUTTON)
        );
    }
}
