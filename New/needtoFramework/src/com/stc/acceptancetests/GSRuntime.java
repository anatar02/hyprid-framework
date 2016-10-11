package com.stc.acceptancetests;

import com.stc.Logger.GSLogger;
import com.stc.report.XMLReporter;
import com.stc.result.StepDefinitionResult;
import static com.stc.globals.Constants.*;
import static com.stc.report.XMLReporter.*;
import static com.stc.Logger.GSLogger.gsLogger;

import gherkin.formatter.Reporter;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import gherkin.I18n;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;



import java.util.Iterator;
import java.util.Set;

public class GSRuntime extends Runtime{
	private StepDefinitionResult stepDefinitionResult=null;
	private XMLReporter xmlReporter;

	public GSRuntime(ResourceLoader resourceLoader, ClassFinder classFinder,
			ClassLoader classLoader, RuntimeOptions runtimeOptions) {

		super(resourceLoader,classFinder,classLoader,runtimeOptions);
		
	}
	
	
	 public void runStep(String featurePath, Step step, Reporter reporter, I18n i18n) {
		 stepDefinitionResult=StepDefinitionResult.newInstance();
		 stepDefinitionResult.setStepDefiniton(step.getName());
		 stepDefinitionResult.setStepDefinitonKeyword(step.getKeyword());
		 //stepDefinitionResult.setTestStatus(PASS);
		 long start=System.currentTimeMillis();
		 
		 super.runStep(featurePath,step,reporter,i18n);
		 
		 long end=System.currentTimeMillis();
		 
		 stepDefinitionResult.setStepEllapsedTime(end-start);
		 xmlReporter=getXMLReporter();
		 xmlReporter.writeStepDefinitionReport(stepDefinitionResult);
	 }
	 

}
