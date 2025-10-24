package org.example.steps;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.annotations.Managed;
import org.example.questions.PurchaseSuccess;
import org.example.tasks.AddProductToCart;
import org.example.tasks.CheckoutInformation;
import org.example.tasks.LoginWithCredentials;
import org.example.tasks.OpenCart;
import org.example.ui.LoginPage;
import org.openqa.selenium.WebDriver;

public class PurchaseSteps {

    @Managed
    WebDriver browser;

    private Actor dennis;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        dennis = OnStage.theActorCalled("Dennis");
        dennis.can(BrowseTheWeb.with(browser));
    }

    @Given("the user is on the Sauce Demo login page")
    public void open_login_page() {
        dennis.wasAbleTo(Open.url(LoginPage.URL));
    }

    @When("the user logs in with username {string} and password {string}")
    public void login(String user, String pass) {
        dennis.attemptsTo(LoginWithCredentials.with(user, pass));
    }

    @When("the user adds the following items to the cart:")
    public void add_items(DataTable table) {
        List<String> items = table.asList(String.class);
        dennis.attemptsTo(AddProductToCart.called(items));
    }

    @When("the user opens the cart")
    public void open_cart() {
        dennis.attemptsTo(OpenCart.now());
    }

    @When("the user proceeds to checkout with:")
    public void checkout(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        String first;
        String last;
        String postal;
        if (rows.size() >= 2) {
            first = rows.get(1).get(0);
            last = rows.get(1).get(1);
            postal = rows.get(1).get(2);
        } else {
            first = rows.get(0).get(0);
            last = rows.get(0).get(1);
            postal = rows.get(0).get(2);
        }
        dennis.attemptsTo(CheckoutInformation.with(first, last, postal));
    }

    @Then("the purchase should be completed successfully")
    public void verify_success() {
        dennis.should(seeThat("Purchase success page is shown", PurchaseSuccess.isDisplayed(), is(true)));
    }
}
