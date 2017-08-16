/**
 * 
 */
package com.UniSuper.Cucumber;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

/**
 * Runner for parallel thread 2
 * Running all test which are tagged as Thread2
 * 
 * @author gagan_000
 *
 */
@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/report2/cucumber2.json", 
	//Set retry count for rerunning the failed test cases
//		retryCount = 1, 
		detailedReport = true, detailedAggregatedReport = true, overviewReport = true,
		coverageReport = true,
		jsonUsageReport = "target/report2/cucumber-usage.json", usageReport = true, toPDF = true, excludeCoverageTags = {
				"@flaky" }, includeCoverageTags = { "@passed" }, outputFolder = "target/report2")
@CucumberOptions(plugin = { "html:target/report2/cucumber-html-report",
        "json:target/report2/cucumber2.json", "pretty:target/report2/cucumber-pretty.txt",
        "usage:target/report2/cucumber-usage.json", "junit:target/report2/cucumber-results.xml" }, features = { "src/test/resources/features/" }, 
        format = {"pretty", "json:target/report2/cucumber2.json", "html:target/report2/html/" }, 
        glue = { "com.UniSuper.StepDefs/" },
 	tags = {("@Thread2")}
)
public class CucumberRunner2 {

}
