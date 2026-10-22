# E2E-Saucedemo

UI end-to-end tests for [saucedemo.com](https://www.saucedemo.com), written with Serenity BDD (Screenplay pattern) and Cucumber.

[![E2E](https://github.com/MrMeowdyRowdy/E2E-Saucedemo/actions/workflows/e2e.yml/badge.svg)](https://github.com/MrMeowdyRowdy/E2E-Saucedemo/actions/workflows/e2e.yml)
[Latest report](https://mrmeowdyrowdy.github.io/E2E-Saucedemo/)

## What it tests

Core flows of the Sauce Demo shop: login, cart, sorting, checkout and logout. Failures assert the exact error text; sorting asserts the whole displayed order; checkout asserts that the overview adds up (sum of item prices = item total, item total + tax = total).

| Feature | Scenarios | Tags |
|---|---|---|
| `purchase_flow` : log in, add two items, check out, see the confirmation | 1 | `@smoke` `@regression` |
| `login` : locked-out user | 1 | `@smoke` `@regression` |
| `login` : wrong password, empty username, empty password | 3 | `@regression` |
| `cart` : add items, badge count, cart contents, remove, badge hides | 3 | `@regression` |
| `sorting` : price low to high, name Z to A | 2 | `@regression` |
| `checkout` : missing first name, missing postal code, price arithmetic | 3 | `@regression` |
| `logout` : back to the login page | 1 | `@regression` |
| `known_issues` : `problem_user` bugs (below) | 3 | `@known-issue` |

Totals: 2 `@smoke`, 14 `@regression`, 3 `@known-issue` (not part of the default run).

## Design choices

- **Screenplay instead of page objects.** Tests describe what an actor does (tasks) and what the actor checks (questions); the page details live in separate UI targets. Steps stay readable and tasks are reusable.
- **Layers** (all under `src/test/java/org/example/`):
  - `ui/` : targets (locators) per page.
  - `tasks/` : `LoginWithCredentials`, `AddProductToCart`, `RemoveProductFromCart`, `SortProducts`, `OpenCart`, `EnterCheckoutDetails`, `CompleteCheckout`, `Logout`.
  - `questions/` : `ShownError`, `CartBadge`, `CartContents`, `ProductList`, `OrderSummary`, `PurchaseSuccess` (and `Money`, which parses prices with `BigDecimal`, never floating point).
  - `steps/` : Cucumber step definitions (`SauceDemoSteps`) that only call tasks and questions.
  - `runners/` : `CucumberTestSuite`, the JUnit runner.
- **Features** are in `src/test/resources/features/`, one file per area.
- **Tags.** `@smoke` is the fast core (a valid purchase and one login failure). `@regression` is everything that must stay green. `@known-issue` is kept apart on purpose.
- **Known-issue policy.** The `problem_user` account has planted bugs. Those scenarios assert the buggy behaviour as it is today, so they pass while the bug exists and fail the day the site is fixed. The runner excludes `@known-issue` by default, so a deliberate site bug never turns the main build red.
  - Every product shows the same image.
  - Sorting by name (Z to A) does not reorder the list.
  - The last name cannot be entered at checkout, so the form fails with "Last Name is required".
- **Browser config lives in `serenity.conf`**, not in the pom or the workflow, so local runs and CI behave the same: headless Chrome at 1440x900, `--no-sandbox --disable-dev-shm-usage` for CI containers, and Chrome prefs plus `--disable-features=PasswordLeakDetection` that stop the leaked-password popup (the shared demo password would otherwise block clicks). The `headed` environment turns headless off for debugging.
- **Reports** come from the Serenity Maven plugin, with a screenshot per action. Tests do not stop the build on their own: the report is aggregated first, then `serenity:check` fails the build, so a red run still has a report.

## Run it

Prerequisites: Java 17 or newer, Google Chrome, internet access. Maven is not needed; the wrapper downloads it.

```bash
./mvnw verify                                   # default run: everything except @known-issue, headless
./mvnw verify -Dcucumber.filter.tags="@smoke"   # fast core only
./mvnw verify -Dcucumber.filter.tags="@known-issue"   # the problem_user bugs
./mvnw verify -Denvironment=headed              # visible browser for debugging
```

On Windows use `mvnw.cmd`. The report is written to `target/site/serenity/index.html`.

## CI

`.github/workflows/e2e.yml` runs `./mvnw -B verify` on GitHub Actions on every push and pull request to `master`, nightly at 05:00 UTC, and on demand (`workflow_dispatch`). The Serenity report is always uploaded as a build artifact. On `master` it is also published to GitHub Pages, even when tests failed: [mrmeowdyrowdy.github.io/E2E-Saucedemo](https://mrmeowdyrowdy.github.io/E2E-Saucedemo/).

## Findings

- Saucedemo's shared demo password triggers Chrome's leaked-password popup, which can block clicks. The Chrome prefs in `serenity.conf` disable it.
- Serenity 4.x needs the matching `serenity-cucumber` and `serenity-maven-plugin` versions; they all use the single `serenity.version` property in `pom.xml`.
