<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="" lang="">
<head>
  <title>Test Results Report - Overview</title>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <link href="reportng.css" rel="stylesheet" type="text/css" />
  </head>
<body>
<div id="meta">
  Generated at xxx IST on Thursday dd Month yyyy
  <br /><span id="systemInfo">systename&nbsp;/&nbsp;Java version (Oracle Corporation)&nbsp;/&nbsp;OS name and version</span>
</div>

<h1>Test Results Report</h1>

<table class="overviewTable">
    <tr>
    <th colspan="6" class="header suite">
      <div class="suiteLinks">
                                      </div>
      ${suiteName}
    </th>
  </tr>
  <tr class="columnHeadings">
    <td>Tests</td>
    <th>Duration</th>
    <th>Passed</th>
    <th>Skipped</th>
    <th>Failed</th>
    <th>Pass Rate</th>
  </tr>
	<#assign i = 1>
	<#list xmlTests as xmlTest>
    <tr class="test">
    <td class="test">
      <a href="${xmlTest.testName}.html">${xmlTest.testName}</a>
    </td>
    <td class="duration">
      
    </td>

        <td class="passed number">${xmlTest.testPassCnt}</td>
    
        <td class="skipped number">${xmlTest.testSkipCnt}</td>
    
        <td class="failed number">${xmlTest.testFailCnt}</td>
    
    <td class="passRate">
            ${xmlTest.testPassPercent}%
          </td>
	</tr>
    </#list>
    <tr class="suite">
    <td colspan="2" class="totalLabel">Total</td>

        <td class="passed number">${suitePassCnt}</td>
    
        <td class="skipped number">${suiteSkipCnt}</td>
    
        <td class="failed number">${suiteFailCnt}</td>
    
    <td class="passRate suite">
            ${suitePassPercent}%
          </td>

  </tr>
</table>

<span id="${acceptanceTest}"/>
<#if acceptanceTest == "1">


<table class="overviewTable">
    <tr>
    <th colspan="6" class="header suite">
      <div class="suiteLinks">
                                      </div>
      Acceptance Tests
    </th>
  </tr>
  <tr class="columnHeadings">
    <td>Features</td>
    <th>Duration</th>
    <th>Passed</th>
    <th>Skipped</th>
    <th>Failed</th>
    <th>Pass Rate</th>
  </tr>
	<#assign i = 1>
	<#list xmlFeatures as xmlFeature>
    <tr class="test">
    <td class="test">
      <a href="${xmlFeature.featureName}.html">${xmlFeature.featureName}</a>
    </td>
    <td class="duration">
      
    </td>

        <td class="passed number">${xmlFeature.scenarioPassCnt}</td>
    
        <td class="skipped number">${xmlFeature.scenarioSkipCnt}</td>
    
        <td class="failed number">${xmlFeature.scenarioFailCnt}</td>
    
    <td class="passRate">
            ${xmlFeature.scenarioPassPercent}%
          </td>
	</tr>
    </#list>
    <tr class="suite">
    <td colspan="2" class="totalLabel">Total</td>

        <td class="passed number">${totalScenarioPassCnt}</td>
    
        <td class="skipped number">${totalScenarioSkipCnt}</td>
    
        <td class="failed number">${totalScenarioFailCnt}</td>
    
    <td class="passRate suite">
            ${totalScenarioPassPercent}%
          </td>

  </tr>
</table>
</#if>

</body>
</html>
