package org.example.steps;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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
import org.example.tasks.CompleteCheckout;
import org.example.tasks.EnterCheckoutDetails;
import org.example.tasks.LoginWithCredentials;
import org.example.tasks.Logout;
import org.example.tasks.OpenCart;
import org.example.tasks.RemoveProductFromCart;
import org.example.tasks.SortProducts;
import org.example.ui.LoginPage;
import org.openqa.selenium.WebDriver;

public class SauceDemoSteps {

    private static final String PASSWORD = "secret_sauce";

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
        dennis.wasAbleTo(Open.relativeUrl("/"));
    }

    @Given("the user is logged in as {string}")
    public void logged_in_as(String user) {
        dennis.wasAbleTo(Open.relativeUrl("/"), LoginWithCredentials.with(user, PASSWORD));
    }

    @When("the user logs in with username {string} and password {string}")
    public void login(String user, String pass) {
        dennis.attemptsTo(LoginWithCredentials.with(user, pass));
    }

    @When("the user adds the following items to the cart:")
    public void add_items(DataTable table) {
        dennis.attemptsTo(AddProductToCart.called(table.asList(String.class)));
    }

    @When("the user removes the following items from the cart:")
    public void remove_items(DataTable table) {
        dennis.attemptsTo(RemoveProductFromCart.called(table.asList(String.class)));
    }

    @When("the user opens the cart")
    public void open_cart() {
        dennis.attemptsTo(OpenCart.now());
    }

    @When("the user sorts the products by {string}")
    public void sort_by(String option) {
        dennis.attemptsTo(SortProducts.by(option));
    }

    @When("the user logs out")
    public void logout() {
        dennis.attemptsTo(Logout.now());
    }

    @When("the user completes checkout with:")
    public void complete_checkout(DataTable table) {
        Map<String, String> row = table.asMaps().get(0);
        dennis.attemptsTo(CompleteCheckout.with(field(row, "firstName"), field(row, "lastName"), field(row, "postalCode")));
    }

    @When("the user enters the checkout details and continues:")
    public void enter_details(DataTable table) {
        Map<String, String> row = table.asMaps().get(0);
        dennis.attemptsTo(EnterCheckoutDetails.with(field(row, "firstName"), field(row, "lastName"), field(row, "postalCode")));
    }

    @Then("the purchase should be completed successfully")
    public void verify_success() {
        dennis.should(seeThat("Confirmation header", PurchaseSuccess.header(), is("Thank you for your order!")));
    }

    @Then("the error message should be {string}")
    public void verify_error(String message) {
        dennis.should(seeThat("Error message", ShownError.message(), is(message)));
    }

    @Then("the cart badge should show {int}")
    public void verify_badge(int count) {
        dennis.should(seeThat("Cart badge count", CartBadge.count(), is(count)));
    }

    @Then("the cart badge should not be shown")
    public void verify_no_badge() {
        dennis.should(seeThat("Cart badge is shown", CartBadge.isShown(), is(false)));
    }

    @Then("the cart should contain exactly:")
    public void verify_cart(DataTable table) {
        dennis.should(seeThat("Cart items", CartContents.itemNames(), contains(table.asList(String.class).toArray())));
    }

    @Then("the product names should be sorted {word}")
    public void verify_names_sorted(String direction) {
        assertSorted("names", dennis.asksFor(ProductList.names()), direction);
    }

    @Then("the product prices should be sorted {word}")
    public void verify_prices_sorted(String direction) {
        assertSorted("prices", dennis.asksFor(ProductList.prices()), direction);
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

    // Empty table cells arrive as null
    private static String field(Map<String, String> row, String key) {
        return Objects.toString(row.get(key), "");
    }

    private static <T extends Comparable<? super T>> void assertSorted(String what, List<T> shown, String direction) {
        Comparator<T> order = switch (direction) {
            case "ascending" -> Comparator.naturalOrder();
            case "descending" -> Comparator.reverseOrder();
            default -> throw new IllegalArgumentException("Unknown sort direction: " + direction);
        };
        // an empty list is trivially sorted, so the page must actually show products
        assertThat("Displayed " + what, shown, is(not(empty())));
        List<T> expected = new ArrayList<>(shown);
        expected.sort(order);
        assertThat("Displayed " + what + " " + shown, shown, is(expected));
    }
}
