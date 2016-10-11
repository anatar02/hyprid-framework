<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="" lang="">
<head>
  <title>Test Results Report - ${testName}</title>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <link href="reportng.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="reportng.js"></script>
</head>
<body>
<script type="text/javascript">
	function showErrorPopup(id1,id2){
		var x=document.getElementById(id1);
		var y=document.getElementById(id2);
		if(x.style.display == 'none'){
			x.style.display = 'block';
			y.title="hide details.";
		}else{
			x.style.display = 'none';
			y.title="show details.";
		}
	}
</script>


<h1>${testName}</h1>
<p>
  <!-- Test duration: -->
</p>


 <table border="1" class="resultsTable">
 <tr>
 <th width="85%" class="header">Test</th>
 <th width="5%" class="header">Status</th>
 <th width="10%" class="header">Duration ms</th>
 </tr>
 <#assign i = 1>
 <#list testCases as testCase>
 
 <#if testCase.testcaseStatus == "Fail">
	<tr class="testFailIndicator" id="test-${i}">
		<td width="85%"><a href="#${testCase.testcaseName}${i}">${testCase.testcaseName}</a></td>
		<td width="5%"><span title="test failed.">&#x2718;</span></td>
		<td width="10%">${testCase.testcaseDuration}</td>
	</tr>
 <#elseif testCase.testcaseStatus == "Pass">
	<tr class="testSuccessIndicator" id="test-${i}">
		<td width="85%"><a href="#${testCase.testcaseName}${i}">${testCase.testcaseName}</a></td>
		<td width="5%"><span title="test passed.">&#x2714;</span></td>
		<td width="10%">${testCase.testcaseDuration}</td>
	</tr>
 <#else>
	<tr class="testSkipIndicator" id="test-${i}">
		<td width="85%"><a href="#${testCase.testcaseName}${i}">${testCase.testcaseName}</a></td>
		<td width="5%"><span title="test skipped.">&#x2702;</span></td>
		<td width="10%">${testCase.testcaseDuration}</td>
	</tr>
 </#if>
 
 <#assign i = i + 1>
 </#list>
 </table>
 
 <br>
 <br>
 <br>
 
 <#assign j = 1>
 <#list testCases as testCase>
 <br>
 <br>
 <p class="header"><b>Test: </b><a name="${testCase.testcaseName}${j}"><b>${testCase.testcaseName}</b></a>
	<b>Duration: ${testCase.testcaseDuration} ms.</b>
 </p>
 <table border="1" class="resultsTable">
 <tr>
 <th width="70%" class="header">Step</th>
 <th width="5%" class="header">Status</th>
 <th width="20%" class="header">Error</th>
 <th width="5%" class="header">Screenshot</th>
 </tr>
 
 <#assign k = 1>
 <#list testCase.testSteps as testStep>
 <#if testStep.stepStatus == "Fail">
	<tr class="stepFailIndicator">
		<td width="70%">${testStep.stepName}</td>
		<td width="5%"><span title="Fail.">&#x2718;</span></td>
		<td width="20%"><div id="error-${testStep.errorCounter}" style="display:none;">${testStep.stepFullError}</div>${testStep.stepError} <span id="span-${testStep.errorCounter}" title="show details." class="errorDetails" onclick="showErrorPopup('error-${testStep.errorCounter}','span-${testStep.errorCounter}');">..</span></td>
		<td width="5%"><a href="${testStep.stepScreenshot}">&#x279C;</a></td>
	</tr>
 <#elseif testStep.stepStatus == "Pass">
	<tr class="stepSuccessIndicator">
		<td width="70%">${testStep.stepName}</td>
		<td width="5%"><span title="Pass.">&#x2714;</span></td>
		<td width="20%"></td>
		<td width="5%"></td>
	</tr>
 <#else>
	<tr class="stepWarnIndicator">
		<td width="70%">${testStep.stepName}</td>
		<td width="5%"><span title="Timeout.">&#x2707;</span></td>
		<td width="20%"><div id="error-${testStep.errorCounter}" style="display:none;">${testStep.stepFullError}</div>${testStep.stepError} <span id="span-${testStep.errorCounter}" title="show details." class="errorDetails" onclick="showErrorPopup('error-${testStep.errorCounter}','span-${testStep.errorCounter}');">..</span></td>
		<td width="5%"><a href="${testStep.stepScreenshot}">&#x279C;</a></td>
	</tr>
 </#if>
 <#assign k = k + 1>
 </#list>
 </table>
 <#assign j = j + 1>
 </#list>
</body>
</html>
