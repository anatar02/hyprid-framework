<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="" lang="">
<head>
  <title>Test Results Report - Suites</title>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <link href="reportng.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="reportng.js"></script>
</head>
<body style="margin-top: 0;">

<div id="sidebarHeader">
  <h2>Test Results Report</h2>
  <p>
    <a href="overview.html" target="main">Overview</a>
          </p>
</div>
 <table id="suites">
    <thead>
    <tr>
      <th class="header suite" onclick="toggleElement('tests-1', 'table-row-group'); toggle('toggle-1')">
        <span id="toggle-1" class="toggle">&#x25bc;</span>${suiteName}
      </th>
    </tr>
  </thead>
	<tbody id="tests-1" class="tests">
			<#assign i = 1>
			<#list xmlTests as xmlTest>
                <tr>
					<td class="test" >
						<table id="tests${i}">
								<tr>
									<th class="toggle">
										<a href="#"><span id="test-toggle-${i}" class="toggle" onclick="toggleElement('testsCases${i}', 'table-row-group'); toggle('test-toggle-${i}')">&#x25bc;</span></a>
										<#if xmlTest.testStatus == "Fail">
											<span class="failureIndicator" title="Some tests failed.">&#x2718;</span>
										<#else>
											<span class="successIndicator" title="All tests passed.">&#x2714;</span>
										</#if>
										<a href="${xmlTest.testName}.html" target="main">${xmlTest.testName}</a>
									</th>
									<tbody id="testsCases${i}" class="tests">
									<#assign j = 1>
									<#list xmlTest.testCases as testCase>
										<tr>
										<td class="toggle" >
											
											<#if testCase.testcaseStatus == "Fail">
												<span class="failureIndicator" title="test failed.">&#x2718;</span>
											<#elseif testCase.testcaseStatus == "Pass">
												<span class="successIndicator" title="test passed.">&#x2714;</span>
											<#else>
												<span class="skipIndicator" title="test skipped.">&#x2702;</span>
											</#if>
											<a href="${xmlTest.testName}.html#${testCase.testcaseName}${j}" target="main">${testCase.testcaseName}</a>
											
										<#assign j = j + 1>
										</tr>
									</#list>
									</tbody>
								</tr>
						</table>
						<#assign i = i + 1>
					</td>
				</tr>
			</#list>
         </tbody>
  </table>
	
  <span id="${acceptanceTest}"/>
  <#if acceptanceTest == "1">
  
  <table id="features">
    <thead>
    <tr>
      <th class="header suite" onclick="toggleElement('tests-2', 'table-row-group'); toggle('toggle-2')">
        <span id="toggle-2" class="toggle">&#x25bc;</span>Acceptance Tests
      </th>
    </tr>
  </thead>
	<tbody id="tests-2" class="tests">
			<#assign i = 1>
			<#list xmlFeatures as xmlFeature>
                <tr>
					<td class="test" >
						<table id="features${i}">
								<tr>
									<th class="toggle">
										<a href="#"><span id="feature-toggle-${i}" class="toggle" onclick="toggleElement('scenarios${i}', 'table-row-group'); toggle('feature-toggle-${i}')">&#x25bc;</span></a>
										<#if xmlFeature.status == "Fail">
											<span class="failureIndicator" title="Some scenarios failed.">&#x2718;</span>
										<#else>
											<span class="successIndicator" title="All scenarios passed.">&#x2714;</span>
										</#if>
										<a href="${xmlFeature.featureName}.html" target="main">${xmlFeature.featureName}</a>
									</th>
									<tbody id="scenarios${i}" class="tests">
									<#assign j = 1>
									<#list xmlFeature.testScenarios as testScenario>
										<tr>
										<td class="toggle" >
											
											<#if testScenario.scenarioStatus == "Fail">
												<span class="failureIndicator" title="scenario failed.">&#x2718;</span>
											<#elseif testScenario.scenarioStatus == "Pass">
												<span class="successIndicator" title="scenario passed.">&#x2714;</span>
											<#else>
												<span class="skipIndicator" title="scenario skipped.">&#x2702;</span>
											</#if>
											<a href="${xmlFeature.featureName}.html#${testScenario.testScenarioName}${j}" target="main">${testScenario.testScenarioName}</a>
											
										<#assign j = j + 1>
										</tr>
									</#list>
									</tbody>
								</tr>
						</table>
						<#assign i = i + 1>
					</td>
				</tr>
			</#list>
         </tbody>
  </table>
  
  </#if> 
  
 </body>
</html>
