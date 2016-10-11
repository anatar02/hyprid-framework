package com.stc.acceptancetests;

import com.stc.report.AcceptanceTestReporter;
import com.stc.acceptancetests.GSRuntime;

import org.testng.annotations.Test;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNgReporter;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.CucumberException;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;



@CucumberOptions(format = {"pretty", "html:target/cucumber"},
features = "D:\\sam\\GSEnterprise\\workspace\\GSFramework\\features\\com\\example\\acceptancetests"
)

//public class RunAcceptanceTest extends AbstractTestNGCucumberTests {
public class RunAcceptanceTest extends AbstractTestNGCucumberTests {
	private final Runtime runtime;
	
	public RunAcceptanceTest() {
		// TODO Auto-generated constructor stub
		ClassLoader classLoader = getClass().getClassLoader();
        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        
		 RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(getClass(), new Class[]{CucumberOptions.class});
	     RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();

	     TestNgReporter reporter = new AcceptanceTestReporter(System.out);
	     runtimeOptions.getFormatters().add(reporter);
	     ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
	     runtime = new GSRuntime(resourceLoader, classFinder, classLoader, runtimeOptions);
	     //runtime = new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
	}
	
	@Override
	@Test(groups = "cucumber", description = "Runs Cucumber Features")
	public void run_cukes() {
		// TODO Auto-generated method stub
		runtime.run();
        //runtime.printSummary();
        
		//Not sure if these lines are needed
		if (!runtime.getErrors().isEmpty()) {
            throw new CucumberException(runtime.getErrors().get(0));
        }
	}
}