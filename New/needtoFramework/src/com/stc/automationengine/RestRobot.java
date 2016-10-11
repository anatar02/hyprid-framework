package com.stc.automationengine;

import static com.jayway.restassured.RestAssured.*;
import static com.stc.Logger.GSLogger.gsLogger;
import static com.stc.globals.Constants.FAIL;
import static com.stc.globals.Constants.FUNCTIONAL_TEST;
import static com.stc.globals.Constants.PASS;
import static com.stc.result.StepDefinitionResult.geStepDefinitionResult;
import static com.stc.result.TestCaseResult.geTestCaseResult;

import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.config.RestAssuredConfig.newConfig;
import static com.jayway.restassured.config.DecoderConfig.decoderConfig;
import static com.jayway.restassured.config.EncoderConfig.encoderConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.crypto.RuntimeCryptoException;
import org.w3c.dom.Node;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.path.xml.config.XmlPathConfig;

import com.stc.globals.TestType;
import com.stc.result.StepDefinitionResult;
import com.stc.result.TestCaseResult;
import com.stc.result.TestStepResult;

public class RestRobot {
	//private ResponseSpecification responseSpecification=null;
	//private RequestSpecification requestSpecification=null;
	private Map<String,String> headers=null;
	private Map<String,String> parameters=null;
	//private Response response=null;
	private TestStepResult stepResult;
	private TestCaseResult testcaseResult;
	private StepDefinitionResult stepDefinitionResult;
	public static RestRobot restRobot= new RestRobot();
	private String response=null;
	private String xmlBody=null;
	
	private RestRobot(){
		testcaseResult=geTestCaseResult();
		stepDefinitionResult=geStepDefinitionResult();
		
		//RestAssured.config=newConfig().decoderConfig(decoderConfig().defaultContentCharset("UTF-8")).and().newConfig().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
		RestAssured.config=newConfig().decoderConfig(decoderConfig().defaultContentCharset("UTF-8"));
		XmlPath.config=new XmlPathConfig("UTF-8");
	}
	
	public void newRequest(){
		//requestSpecification=given();
		headers=new HashMap<String,String>();
		parameters=new HashMap<String,String>();
		
		//responseSpecification=expect();
	}
	
	public void setHeader(String key,String value){
		try{
			headers.put(key, value);
			setStepPass("Request Header "+key+" = "+ value);
		}catch(Exception e){
			setStepFail("Request Header "+key+" = "+ value,e);
			gsLogger.writeERROR("Exception occured-> RestRobot -> setHeader() -> ", e);
		}finally{
			if(TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}
	
	public void setParameter(String key,String value){
		try{
			parameters.put(key,value);
			setStepPass("Request Parameter "+key+" = "+ value);
		}catch(Exception e){
			setStepFail("Request Parameter "+key+" = "+ value,e);
			gsLogger.writeERROR("Exception occured-> RestRobot -> setParameter() -> ", e);
		}finally{
			if(TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}
	
	public void setXMLBody(String xmlFimeName){
		try{
			File fileDir = new File(System.getProperty("user.dir")+"/resource/postdata/"+xmlFimeName);
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			
			xmlBody="";
			String str;
			 
			while ((str = in.readLine()) != null) {
				xmlBody=xmlBody+str;
			}
	 
	        in.close();
			
			setStepPass("Request Body = "+ xmlBody);
		}catch(Exception e){
			setStepFail("Request Body = "+ xmlBody,e);
			gsLogger.writeERROR("Exception occured-> RestRobot -> setXMLBody() -> ", e);
		}finally{
			if(TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}
	
	/*public void setExpectedResponseIgnoringCase(String key, String value){
		responseSpecification=responseSpecification.body(key, equalToIgnoringCase(value));
		setStep("Expect response "+key+" has "+ value);
	}
	
	public void setExpectedResponse(String key, String value){
		responseSpecification=responseSpecification.body(key, equalTo(value));
		setStep("Expect response "+key+" = "+ value);
	}*/
	
	public String getAsXML(String uri){	
		String xml=null;
		
		try{
			if((headers.size() > 0) &&(parameters.size() > 0)){
				xml=given().headers(headers).params(parameters).when().get(uri).andReturn().asString();
			}else if(headers.size() > 0){
				xml=given().headers(headers).when().get(uri).andReturn().asString();
			}else if(parameters.size() > 0){
				xml=given().params(parameters).when().get(uri).andReturn().asString();
			}
			setStepPass("Get "+uri);
		}catch(Exception e){
			xml=null;
			setStepFail("Get ("+uri+")",e);
			gsLogger.writeERROR("Exception occured-> RestRobot -> getAsXML() -> ", e);
		}finally{
			if(TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
		response=xml;
		return xml;
	}
	
	public String postAsXML(String uri){
		String xml=null;
		
		try{
			if((headers.size() > 0) &&(parameters.size() > 0)){
				xml=given().headers(headers).queryParameters(parameters).body(xmlBody).when().post(uri).asString();
			}else if(headers.size() > 0){
				xml=given().headers(headers).body(xmlBody).when().post(uri).asString();
			}else if(parameters.size() > 0){
				xml=given().queryParameters(parameters).body(xmlBody).when().post(uri).asString();
			}
			setStepPass("Post "+uri);
		}catch(Exception e){
			xml=null;
			setStepFail("Post ("+uri+")",e);
			gsLogger.writeERROR("Exception occured-> RestRobot -> postAsXML() -> ", e);
		}finally{
			if(TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
		response=xml;
		return xml;
	}
	
	public void verifyNumberOfXmlNodesInResponse(String xPath,String expectedValue,String passMessage,String failMessage){
		try{
			XmlPath xmlPath=new  XmlPath(response);
			List<Node> nodeList=xmlPath.getList("response.products.product");
			
			
			int actualValue=nodeList.size();
			if(new Integer(expectedValue).intValue() == actualValue){
				setStepPass("VerifyNumberOfXmlNodesInResponse ("+xPath+","+expectedValue+") ["+passMessage+"]");
			}
			else{
				throw new Exception("Expected: "+expectedValue+" Got: "+actualValue);
			}
		}catch(Exception e){
			setStepFail("VerifyNumberOfXmlNodesInResponse ("+xPath+","+expectedValue+") ["+failMessage+"]",e);
			gsLogger.writeERROR("Exception occured-> RestRobot -> verifyNumberOfXmlNodesInResponse() -> ", e);
		}finally{
			if(TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}
		
	public void assertNumberOfXmlNodesInResponse(String xPath,String expectedValue,String passMessage,String failMessage){
		try{
			XmlPath xmlPath=new  XmlPath(response);
			List<Node> nodeList=xmlPath.getList("response.products.product");
			
			
			int actualValue=nodeList.size();
			if(new Integer(expectedValue).intValue() == actualValue){
				setStepPass("AssertNumberOfXmlNodesInResponse ("+xPath+","+expectedValue+") ["+passMessage+"]");
			}
			else{
				throw new RuntimeException("Expected: "+expectedValue+" Got: "+actualValue);
			}
		}catch(Exception e){
			setStepFail("AssertNumberOfXmlNodesInResponse ("+xPath+","+expectedValue+") ["+failMessage+"]",e);
			gsLogger.writeERROR("Exception occured-> RestRobot -> assertNumberOfXmlNodesInResponse() -> ", e);
			
			throw new RuntimeException(e.getMessage());
		}finally{
			if(TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}
	
	public void verifyXMLResponse(String xPath,String expectedValue,String passMessage,String failMessage){
		try{
			XmlPath xmlPath=new  XmlPath(response);
			String actualValue=xmlPath.getString(xPath);
			
			if(actualValue.equals(expectedValue)){
				setStepPass("VerifyXMLResponse ("+xPath+","+expectedValue+") ["+passMessage+"]");
			}
			else{
				throw new Exception("Expected: "+expectedValue+" Got: "+actualValue);
			}
				
		}catch(Exception e){
			setStepFail("VerifyXMLResponse ("+xPath+","+expectedValue+") ["+failMessage+"]",e);
			gsLogger.writeERROR("Exception occured-> RestRobot -> verifyXMLResponse() -> ", e);
		}finally{
			if(TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}
	
	public void assertXMLResponse(String xPath,String expectedValue,String passMessage,String failMessage){
		try{
			XmlPath xmlPath=new  XmlPath(response);
			String actualValue=xmlPath.getString(xPath);
			
			if(actualValue.equals(expectedValue)){
				setStepPass("AssertXMLResponse ("+xPath+","+expectedValue+") ["+passMessage+"]");
			}
			else{
				throw new RuntimeException("Expected: "+expectedValue+" Got: "+actualValue);
			}
				
		}catch(Exception e){
			setStepFail("AssertXMLResponse ("+xPath+","+expectedValue+") ["+failMessage+"]",e);
			gsLogger.writeERROR("Exception occured-> RestRobot -> assertXMLResponse() -> ", e);
			
			throw new RuntimeException(e.getMessage());
		}finally{
			if(TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}
	
	private void setStepFail(String step,Exception e){
		stepResult=new TestStepResult();
		
		stepResult.setStep(step);
		stepResult.setStatus(FAIL);
		stepResult.setErrMsg(e.getMessage());
		
		StringWriter stacktrace=new StringWriter();
		e.printStackTrace(new PrintWriter(stacktrace));
		stepResult.setStackTrace(stacktrace.toString());
	}
	
	private void setStepPass(String step){
		stepResult=new TestStepResult();
		
		stepResult.setStep(step);
		stepResult.setStatus(PASS);
	}
}
