package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.example.ui.LoginPage;

public class LoginWithCredentials implements Task {
    private final String user;
    private final String pass;

    public LoginWithCredentials(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public static LoginWithCredentials with(String user, String pass) {
        return Tasks.instrumented(LoginWithCredentials.class, user, pass);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(user).into(LoginPage.USERNAME),
                Enter.theValue(pass).into(LoginPage.PASSWORD),
                Click.on(LoginPage.LOGIN_BUTTON)
        );
    }
}
