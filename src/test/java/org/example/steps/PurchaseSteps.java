package org.example.steps;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.annotations.Managed;
import org.example.questions.CartBadge;
import org.example.questions.CartContents;
import org.example.questions.OrderSummary;
import org.example.questions.ProductList;
import org.example.questions.PurchaseSuccess;
import org.example.questions.ShownError;
import org.example.tasks.AddProductToCart;
import org.example.tasks.CheckoutInformation;
import org.example.tasks.EnterCheckoutDetails;
import org.example.tasks.LoginWithCredentials;
import org.example.tasks.Logout;
import org.example.tasks.OpenCart;
import org.example.tasks.RemoveProductFromCart;
import org.example.tasks.SortProducts;
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

    @Given("the user is logged in as {string}")
    public void logged_in_as(String user) {
        dennis.wasAbleTo(Open.url(LoginPage.URL), LoginWithCredentials.with(user, "secret_sauce"));
    }

    @When("the user removes the following items from the cart:")
    public void remove_items(DataTable table) {
        dennis.attemptsTo(RemoveProductFromCart.called(table.asList(String.class)));
    }

    @When("the user sorts the products by {string}")
    public void sort_by(String option) {
        dennis.attemptsTo(SortProducts.by(option));
    }

    @When("the user logs out")
    public void logout() {
        dennis.attemptsTo(Logout.now());
    }

    @When("the user proceeds to checkout with:")
    public void checkout(DataTable table) {
        Map<String, String> row = table.asMaps().get(0);
        dennis.attemptsTo(CheckoutInformation.with(field(row, "firstName"), field(row, "lastName"), field(row, "postalCode")));
    }

    @When("the user enters the checkout details and continues:")
    public void enter_details(DataTable table) {
        Map<String, String> row = table.asMaps().get(0);
        dennis.attemptsTo(EnterCheckoutDetails.with(field(row, "firstName"), field(row, "lastName"), field(row, "postalCode")));
    }

    // Empty table cells arrive as null
    private static String field(Map<String, String> row, String key) {
        return Objects.toString(row.get(key), "");
    }

    @Then("the purchase should be completed successfully")
    public void verify_success() {
        dennis.should(seeThat("Purchase success page is shown", PurchaseSuccess.isDisplayed(), is(true)));
    }

    @Then("the error message should be {string}")
    public void verify_error(String message) {
        dennis.should(seeThat("Error message", ShownError.message(), is(message)));
    }

    @Then("the cart badge should show {int}")
    public void verify_badge(int count) {
        dennis.should(seeThat("Cart badge count", CartBadge.count(), is(count)));
    }

    @Then("the cart should contain exactly:")
    public void verify_cart(DataTable table) {
        dennis.should(seeThat("Cart items", CartContents.itemNames(), contains(table.asList(String.class).toArray())));
    }

    @Then("the product names should be sorted {word}")
    public void verify_names_sorted(String direction) {
        List<String> shown = dennis.asksFor(ProductList.names());
        List<String> expected = new ArrayList<>(shown);
        expected.sort("descending".equals(direction) ? Comparator.reverseOrder() : Comparator.naturalOrder());
        assertThat("Displayed names " + shown, shown, is(expected));
    }

    @Then("the product prices should be sorted {word}")
    public void verify_prices_sorted(String direction) {
        List<BigDecimal> shown = dennis.asksFor(ProductList.prices());
        List<BigDecimal> expected = new ArrayList<>(shown);
        expected.sort("descending".equals(direction) ? Comparator.reverseOrder() : Comparator.naturalOrder());
        assertThat("Displayed prices " + shown, shown, is(expected));
    }

    @Then("the item total should equal the sum of the item prices")
    public void verify_item_total() {
        BigDecimal sum = dennis.asksFor(OrderSummary.itemPricesSum());
        dennis.should(seeThat("Item total", OrderSummary.itemTotal(), comparesEqualTo(sum)));
    }

    @Then("the total should equal the item total plus tax")
    public void verify_total() {
        BigDecimal expected = dennis.asksFor(OrderSummary.itemTotal()).add(dennis.asksFor(OrderSummary.tax()));
        dennis.should(seeThat("Total", OrderSummary.total(), comparesEqualTo(expected)));
    }

    @Then("the login page should be shown")
    public void verify_login_page() {
        dennis.should(seeThat("Login button is displayed", Presence.of(LoginPage.LOGIN_BUTTON), is(true)));
    }

    @Then("every product should show the same image")
    public void verify_same_images() {
        List<String> sources = dennis.asksFor(ProductList.imageSources());
        assertThat("Image sources " + sources, new HashSet<>(sources).size(), is(1));
    }
}
