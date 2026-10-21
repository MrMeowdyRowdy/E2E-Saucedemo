package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ErrorBanner {

    public static final Target MESSAGE = Target.the("error message")
            .located(By.cssSelector("[data-test='error']"));
}
