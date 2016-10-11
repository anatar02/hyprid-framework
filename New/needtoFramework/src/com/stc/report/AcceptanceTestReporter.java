package com.stc.report;


import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;
import cucumber.api.testng.TestNgReporter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.stc.Logger.GSLogger.gsLogger;
import static com.stc.report.XMLReporter.*;

import com.stc.Logger.GSLogger;


public class AcceptanceTestReporter extends TestNgReporter {
	private XMLReporter xmlReporter;

	public AcceptanceTestReporter(Appendable appendable) {
		super(appendable);
		// TODO Auto-generated constructor stub
		xmlReporter=getXMLReporter();
	}
	
	
	public void uri(String uri) {
        // TODO: find an appropriate keyword
		//xmlReporter.createAcceptanceReport(uri, new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
        super.uri(uri);
    }

	public void scenario(Scenario scenario) {
		xmlReporter.writeScenarioReport(scenario.getName(),new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
		super.scenario(scenario);
    }
	
	public void feature(Feature feature){
		xmlReporter.createAcceptanceReport(feature.getName(),new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
		super.feature(feature);
	}
	
	public void step(Step step) {
		super.step(step);
    }
}
