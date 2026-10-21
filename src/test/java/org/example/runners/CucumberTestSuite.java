package org.example.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

// Default run skips @known-issue; override with -Dcucumber.filter.tags="..."
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example.steps",
        tags = "not @known-issue",
        plugin = {"pretty"}
)
public class CucumberTestSuite {}
