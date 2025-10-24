# Serenity BDD + Screenplay Saucedemo Tests

## Project Overview

This project is a **Screenplay-style Serenity BDD test suite** for [saucedemo.com](https://www.saucedemo.com).
It tests an end-to-end scenario:

1. User logs in with valid credentials.
2. User adds one or more products to the shopping cart.
3. User proceeds to checkout and fills in checkout information.
4. User completes the purchase and verifies the **Purchase Success** page.

---

## Prerequisites

* Java 17
* Maven 3.9.X
* Chrome browser installed
* Internet connection
* IDE IntelliJ IDEA

---

## Project Structure

```
src
├── main
│   
├── test
│   ├── java/org/example/ui/                # Screenplay UI targets (Products, Cart, Checkout, etc.)
│   ├── java/org/example/tasks/             # Screenplay tasks (Login, AddProducts, Checkout)
│   ├── java/org/example/questions/         # Screenplay questions (PurchaseSuccess)
│   ├── java/org/example/stepdefinitions/   # Cucumber step definitions
│   └── java/org/example/runners/           # CucumberTestSuite.java (test runner)
└── resources/features/                     # Cucumber .feature files
```

---

## Setup & Dependencies

The project uses **Maven** to manage dependencies.
Key dependencies in `pom.xml`:

* `serenity-core`
* `serenity-screenplay`
* `serenity-screenplay-webdriver`
* `serenity-cucumber6` (or `serenity-cucumber7` depending on your Cucumber version)
* `selenium-java`

> Ensure the dependencies match your Serenity version.

---

## How to Run the Tests

### 1️⃣ Open Terminal / Command Prompt

Navigate to the **project root directory** (where `pom.xml` is located):

```bash
cd C:\Users\YOUR_USER\Desktop\E2E-test\untitled
```
Or in case you are working with intelliJ IDEA, you can open the terminal directly in the IDE. Or use the IDE's built-in Maven support to run goals.

---

### 2️⃣ Run all tests

```bash
mvn clean verify
```

* Compiles the project
* Executes Cucumber scenarios via Serenity
* Generates test reports
* Default browser: Chrome

In some cases the browser used may detect the password used is leaked and advise to change it. This is a browser feature and in some cases interrupt the test excecution. To avoid this, you can use a different browser such as Firefox by specifying it in the command line:

```bash
mvn clean verify -Dwebdriver.driver=firefox
```

---

## Test Results & Reports

Serenity generates HTML reports automatically in:

```
<project_root>\target\site\serenity\index.html
```

Though it is prefferable to open the report using the provided link when the tests finish executing in the terminal.

**Example (Windows):**

```
file:///C:/Users/YOUR_USER/path-to/e2e-saucedemo/target/site/serenity/index.html
```

* Open `index.html` in a browser to view detailed interactive reports
* Shows scenario steps, and failures and screenshots.



