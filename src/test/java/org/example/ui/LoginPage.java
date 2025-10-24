package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginPage {
    public static final String URL = "https://www.saucedemo.com/";

    public static final Target USERNAME = Target.the("username field")
            .located(By.id("user-name"));

    public static final Target PASSWORD = Target.the("password field")
            .located(By.id("password"));

    public static final Target LOGIN_BUTTON = Target.the("login button")
            .located(By.id("login-button"));
}
