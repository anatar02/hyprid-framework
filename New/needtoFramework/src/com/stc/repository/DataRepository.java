package com.stc.repository;

import static com.stc.Logger.GSLogger.gsLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import au.com.bytecode.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import static com.stc.globals.Constants.*;

public class DataRepository {
	private static ArrayList<Object[]> values=new ArrayList<Object[]>();

	/*public static Iterator<Object[]>createNewData(ITestContext testContext){
		testContext.getCurrentXmlTest().getAllParameters();
	}*/

	@DataProvider(name="create")
	public static Object[][] createData(Method testMethod){		

		String testClassName = getTestClassName(testMethod);
		if (testClassName.contains(".")){
			testClassName=testClassName.substring(testClassName.lastIndexOf(".")+1);
		}
		//System.out.println("testMethod are : "+testMethod.getName());
		List<String> parameters=getTestParameters(testMethod);

		/*for(String s : parameters){
			System.out.println("Parameters are : "+s);
		}*/

		//Read test specific csv
		//List<String> parameterValues=readCSV(System.getProperty("user.dir")+"/resource/data/"+testMethod.getName()+".csv",parameters);
		//System.out.println("CSV file location :: "+System.getProperty("user.dir")+"/resource/data/"+testMethod.getName()+".csv");

		//readCSV(System.getProperty("user.dir")+"/resource/data/"+testMethod.getName()+".csv",parameters);
		//readExcel(System.getProperty("user.dir")+"/resource/data/"+testMethod.getName()+".xlsx",parameters);
		readExcel(System.getProperty("user.dir")+"/resource/data/"+testClassName+".xlsx",testMethod.getName(),parameters);

		//values[0]=parameterValues.toArray();

		//return values.iterator();
		//return values.iterator();

		//Read Global csv if required
		//readCSV("../resource/data/GlobalVariables.csv");
		Object returnValues[][]=new Object[values.size()][];

		int i=0;
		for (Object[] obj : values){
			returnValues[i]=obj;
			i++;
		}

		return returnValues;
	}

	private static void readCSV(String fileName, List<String> parameters){

		try{
			//TODO - Delete this later, try for csvReader API.
			/*CsvReader reader = null;
			int noOfRecords=0;

			//System.out.println("Charset.defaultCharset()"+Charset.defaultCharset());
			reader=new CsvReader(fileName);
			//reader=new CsvReader(fileName,',',Charset.defaultCharset());			
			//reader=new CsvReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"),',');

			//Read the first record of data as column headers.
			reader.readHeaders();

			//columnHeaders=readColumnHeaders(reader);
			//return readData(reader);

			//clear old values
			values.clear();
			while(reader.readRecord()){
				List<String> parameterValues=new ArrayList<String>();
				noOfRecords++;

				for(String column : parameters){
					System.out.println("Reading column "+column);
					String columnValue = reader.get(column);
					System.out.println("got column value "+columnValue);
					parameterValues.add(columnValue);
				}

				values.add(parameterValues.toArray());
			}
			//return parameterValues;*/

			//TODO - Delete this later, try for OpenCSV API
			CSVReader reader = new CSVReader(new FileReader(fileName));
			String [] headerLine = reader.readNext(); 
			//System.out.println("Headers are : " +headerLine[0]+" "+headerLine[1]+" "+headerLine[2]);
			int i = 0;
			List<String> parameterValues=new ArrayList<String>();
			values.clear();

			String [] nextLine;
			while((nextLine = reader.readNext())!= null){

				//System.out.println("Cloumns are : " +nextLine[0]+" "+nextLine[1]+" "+nextLine[2]);
				for(String parameter : parameters){
					//System.out.println("Parameter Value : " + parameter );	
					for(String hl : headerLine){
						i = Arrays.asList(headerLine).indexOf(hl);
						//System.out.println("Header Value : " + hl );
						if(parameter.equals(hl)){
							//String [] nextLine = reader.readNext();
							//System.out.println("Column value is :" + nextLine[i] +"& i :" + i);
							parameterValues.add(nextLine[i]);
						}
						//TODO - Implement break once header matches with parameter
						//break;
					}
				}
				values.add(parameterValues.toArray());
				parameterValues.clear();
			}
			i++;		

		}catch(Exception e){
			gsLogger.writeERROR("Exception occured-> DataRepository -> readCSV() -> ", e);
		}
		//return null;
	}

	private static void readExcel(String fileName,String methodName, List<String> parameters){

		try{
			//TODO - Keep bellow try for Apache POI APIs

			FileInputStream file = new FileInputStream(new File(fileName));	

			//Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first sheet from the workbook
			//XSSFSheet sheet = workbook.getSheetAt(0);
			//System.out.println("Reading Sheet : "+methodName);
			XSSFSheet sheet = workbook.getSheet(methodName);

			//Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			List<String> parameterValues=new ArrayList<String>();
			values.clear();

			List<String> headerLine = new ArrayList<String>();
			int rowNumber,i=0;

			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();		
				//For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();

				rowNumber = row.getRowNum();
				//System.out.println("Row number : " + rowNumber);

				//Read first line as a Headers/parameters of data 	        	
				if(rowNumber == 0){
					while(cellIterator.hasNext()) {		             
						Cell cell = cellIterator.next();
						int cellType = cell.getCellType();
						switch(cellType){
						case Cell.CELL_TYPE_NUMERIC: {
							double numericValue = cell.getNumericCellValue();
							String numericString = null;
							numericString = new DecimalFormat(DECIMALFORMATPATERN).format(numericValue);
							//String dString = String.format("%f", d);		
							//String dString = String.format("%d", d);			
							//String dString = String.format("#", d);
							headerLine.add(numericString);
							break;
						}
						case Cell.CELL_TYPE_STRING:{
							headerLine.add(cell.getStringCellValue());
							break;
						}
						case Cell.CELL_TYPE_FORMULA:{
							headerLine.add(cell.getCellFormula());
							break;
						}
						case Cell.CELL_TYPE_BLANK:{
							headerLine.add("");
							break;
						}
						case Cell.CELL_TYPE_BOOLEAN:{
							headerLine.add(Boolean.toString(cell.getBooleanCellValue()));
							break;
						}
						case Cell.CELL_TYPE_ERROR:{
							headerLine.add(Byte.toString(cell.getErrorCellValue()));
							break;
						}
						}	
					}        	
				}

				//Read all other lines as data of required parameters
				List<String> nextLine = new ArrayList<String>();
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int cellType = cell.getCellType();
					switch(cellType){
						case Cell.CELL_TYPE_NUMERIC: {
							double numericValue = cell.getNumericCellValue();
							String numericString = null;
							numericString = new DecimalFormat(DECIMALFORMATPATERN).format(numericValue);
							//String dString = String.format("%f", d);		
							//String dString = String.format("%d", d);			
							//String dString = String.format("#", d);
							nextLine.add(numericString);
							break;
						}
						case Cell.CELL_TYPE_STRING:{
							nextLine.add(cell.getStringCellValue());		          
							break;
						}
						case Cell.CELL_TYPE_FORMULA:{
							nextLine.add(cell.getCellFormula());
							break;
						}
	
						case Cell.CELL_TYPE_BOOLEAN:{
							nextLine.add(Boolean.toString(cell.getBooleanCellValue()));
							break;
						}
						case Cell.CELL_TYPE_ERROR:{
							nextLine.add(Byte.toString(cell.getErrorCellValue()));
							break;
						}
	
						case Cell.CELL_TYPE_BLANK:
						default: {
							nextLine.add("");
							break;
						}
					}	
				}

				if(nextLine.size() !=  0){
					for(String parameter : parameters){
						//System.out.println("Parameter Value : " + parameter );	
						for(String hl : headerLine){
							i = headerLine.indexOf(hl);
							//System.out.println("Header Value : " + hl + " with index i :" + i);
							if(parameter.equals(hl)){				    			
								//System.out.println("Data from column is :" + nextLine.get(i) +" & i :" + i);
								parameterValues.add(nextLine.get(i));
							}
							//TODO - Implement break once header matches with parameter
							//break;
						}
					}
					values.add(parameterValues.toArray());
					parameterValues.clear();
				}		        
			}
			i++;	

		}catch(Exception e){
			gsLogger.writeERROR("Exception occured-> DataRepository -> readExcel() -> ", e);
		}		
	}

	private static List<String> getTestParameters(Method testMethod){
		Annotation[] a= testMethod.getAnnotations();
		List<String> parameters=new ArrayList<String>();

		if(a.length!=0){
			for(Annotation val : a){

				if(val instanceof Parameters){
					//Test method has parameters

					//Get parameter names of test method
					//Need better way of doing it
					String temp=val.toString();


					int index1=temp.indexOf('[');
					int index2=temp.indexOf(']');
					temp=temp.substring(index1+1,index2);


					StringTokenizer st=new StringTokenizer(temp, ",");
					while(st.hasMoreTokens()){
						//Add parameter names List
						parameters.add(st.nextToken().trim());
					}
				}
			}
		}
		return parameters;
	}	

	private static String getTestClassName(Method testMethod){
		return testMethod.getDeclaringClass().getSimpleName();
		/*System.out.println("testMethod is : "+testMethod);
		String testMethodName = testMethod.toString().replaceAll("(java.lang.String)", "");
		System.out.println("TestMethod is : "+testMethodName);
		String testClassName = testMethodName.substring(29, testMethodName.lastIndexOf("."));
		System.out.println("TestClassName is : "+testClassName);
		return testClassName;*/
	}
}
