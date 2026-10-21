package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import org.example.ui.ProductsPage;

public class SortProducts implements Task {

    private final String option;

    public SortProducts(String option) {
        this.option = option;
    }

    public static SortProducts by(String option) {
        return Tasks.instrumented(SortProducts.class, option);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(SelectFromOptions.byVisibleText(option).from(ProductsPage.SORT_DROPDOWN));
    }
}
