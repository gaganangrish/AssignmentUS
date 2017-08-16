/**
 * 
 */
package com.UniSuper.Cucumber;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

/**
 * Runner for parallel thread 1
 * 
 * @author gagan_000
 *
 */
@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/report1/cucumber1.json", 
//		retryCount = 1, 
		detailedReport = true, detailedAggregatedReport = true, overviewReport = true,
		coverageReport = true,
		jsonUsageReport = "target/report1/cucumber-usage.json", usageReport = true, toPDF = true, excludeCoverageTags = {
				"@flaky" }, includeCoverageTags = { "@passed" }, outputFolder = "target/report1")
@CucumberOptions(plugin = { "html:target/report1/cucumber-html-report",
        "json:target/report1/cucumber1.json", "pretty:target/report1/cucumber-pretty.txt",
        "usage:target/report1/cucumber-usage.json", "junit:target/report1/cucumber-results.xml" }, features = { "src/test/resources/features/" }, 
        format = {"pretty", "json:target/report1/cucumber1.json", "html:target/report1/html/" }, 
        glue = { "com.UniSuper.StepDefs/" },
        tags = {("@Thread1")}
//		tags = {("@Test1")}
)
public class CucumberRunner1 {

}
