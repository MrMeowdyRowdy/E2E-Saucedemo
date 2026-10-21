package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CheckOutOverviewPage {

    public static final Target ITEM_PRICES = Target.the("overview item prices")
            .located(By.className("inventory_item_price"));

    public static final Target SUBTOTAL = Target.the("item total")
            .located(By.className("summary_subtotal_label"));

    public static final Target TAX = Target.the("tax")
            .located(By.className("summary_tax_label"));

    public static final Target TOTAL = Target.the("total")
            .located(By.className("summary_total_label"));

    public static final Target FINISH_BUTTON = Target.the("finish button")
            .located(By.id("finish"));
}
