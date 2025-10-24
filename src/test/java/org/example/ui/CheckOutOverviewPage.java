package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CheckOutOverviewPage {

    public static final Target FINISH_BUTTON = Target.the("finish button")
            .located(By.id("finish"));
}
