/**
 * 
 */
package com.UniSuper.Cucumber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.UniSuper.baseClass.BaseClass;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;

/**
 * This Cucumber runner file combines the cucumber.json file created by various parallel running threads and 
 * produces a single consolidate report for test results.
 * This runner file should be executed once the build for executing the tests has finished. 
 *
 *
 * @author gagan_000
 */

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
		// retryCount = 1,
		detailedReport = true, detailedAggregatedReport = true, overviewReport = true, coverageReport = true, jsonUsageReport = "target/cucumber-usage.json", usageReport = true, toPDF = true, excludeCoverageTags = {
				"@flaky" }, includeCoverageTags = { "@passed" }, outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report", "json:target/cucumber.json",
		"pretty:target/cucumber-pretty.txt", "usage:target/cucumber-usage.json",
		"junit:target/cucumber-results.xml" }, features = { "src/test/resources/features/" }, format = {
				"pretty", "json:target/cucumber.json",
				"html:target/html/" }, glue = { "com.UniSuper.StepDefs/" },
				tags = { "@Report" })

public class CucumberReportGenerator extends BaseClass {

	@BeforeClass
	public static void beforeClass() throws IOException {

		System.out.println("Report generator before class +++++++++++++++++++++++");
		String userDir = System.getProperty("user.dir");
		moveFiles(userDir + "/target/report1/cucumber1.json", userDir + "/target/cucumber1.json");
		moveFiles(userDir + "/target/report2/cucumber2.json", userDir + "/target/cucumber2.json");

//		moveFiles(userDir+"/src/main/resources/log4j.dtd", userDir+"/target/classes");
//		moveFiles(userDir+"/src/main/resources/log4j.properties", userDir+"/target/classes");
//		moveFiles(userDir+"/src/main/resources/log4j2.xml", userDir+"/target/classes");

	}

	@AfterClass
	public static void afterClass() throws Exception {
		try {
			File reportOutputDirectory = new File("target");
			List<String> jsonFiles = new ArrayList<String>();
			jsonFiles.add("target/cucumber1.json");
			jsonFiles.add("target/cucumber2.json");

			String buildNumber = "1";
			String projectName = "UniSuper Coding Assignment";
			boolean runWithJenkins = false;
			boolean parallelTesting = true;

			Configuration configuration = new Configuration(reportOutputDirectory, projectName);
			// optional configuration
			configuration.setParallelTesting(parallelTesting);
			configuration.setRunWithJenkins(runWithJenkins);
			configuration.setBuildNumber(buildNumber);

			// addidtional metadata presented on main page
			configuration.addClassifications("Platform", "Windows");
			configuration.addClassifications("Browser", "Chrome");
			// configuration.addClassifications("Functional Test URL",
			// BaseURLFunctionalTests);
			// configuration.addClassifications("Fare cache Functional Test URL",
			// BaseURLFunctionalTestsFareCache);
			// configuration.addClassifications("Holidays Functional Test URL",
			// BaseURLFunctionalTestsSearchHolidays);

			ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
			Reportable result = reportBuilder.generateReports();
			// result.
			// and here validate 'result' to decide what to do
			// if report has failed features, undefined steps etc
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
