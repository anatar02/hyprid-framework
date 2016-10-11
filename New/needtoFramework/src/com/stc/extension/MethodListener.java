package com.stc.extension;


import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.stc.globals.TestType;
import com.stc.report.XMLReporter;
import com.stc.result.TestCaseResult;

import static com.stc.report.XMLReporter.*;
import static com.stc.result.TestCaseResult.*;
import static com.stc.globals.Constants.*;

import static com.stc.Logger.GSLogger.gsLogger;

public class MethodListener implements IInvokedMethodListener2{
	private TestCaseResult testcaseResult;
	private XMLReporter xmlReporter;
	
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult,
			ITestContext testContext) {
		
		int status;
		if(method.isTestMethod() && TestType.getTestType().equals(FUNCTIONAL_TEST)){
			status=testResult.getStatus();
			if(status==ITestResult.SUCCESS){
				testcaseResult.setTestStatus(PASS);
			}else if(status==ITestResult.FAILURE){
				testcaseResult.setTestStatus(FAIL);
			}else if(status==ITestResult.SKIP){
				testcaseResult.setTestStatus(SKIPPED);
			}
			testcaseResult.setTestEllapsedTime(testResult.getEndMillis()-testResult.getStartMillis());
			xmlReporter=getXMLReporter();
			xmlReporter.writeTestCaseReport(testcaseResult);
		}
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult,
			ITestContext testContext) {
		// TODO Auto-generated method stub
		
		if(method.isTestMethod()){
			if(method.toString().startsWith("RunAcceptanceTest.run_cukes")){
				TestType.setTestType(ACCEPTANCE_TEST);
			}else{
				TestType.setTestType(FUNCTIONAL_TEST);
				testcaseResult=newInstance();
				testcaseResult.setTestcaseName(method.getTestMethod().getMethodName());
				testcaseResult.setXMLTestName(testContext.getCurrentXmlTest().getName());
			}
		}
	}
}