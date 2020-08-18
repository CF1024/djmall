package com.dj.mall.model.util;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class POIUtil {

	/**
	 * 
	 * @param file 待解析的Excel
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Workbook getExcelWorkBook(InputStream file, String fileName) throws Exception {
		// 获取一个工作簿
		Workbook workbook = null;
		// 判断excel的版本 2007以前版本需要使用HSSFWorkbook
		if(fileName.endsWith(".xls")){
			workbook = new HSSFWorkbook(file);
		}else if(fileName.endsWith(".xlsx")){
			workbook = new XSSFWorkbook(file);
		}
		return workbook;
	}
	
	/**
	 * 数据转换
	 * @param cell
	 * @return
	 */
	public static String getStringCellValue(Cell cell){
		String strCell = "";  
        if(cell==null) return strCell;  
        
        switch (cell.getCellTypeEnum()) {  
            case STRING: // get String data   
                strCell = cell.getRichStringCellValue().getString().trim();  
                break;  
            case NUMERIC:    // get date or number data   
                if (DateUtil.isCellDateFormatted(cell)) {
                	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    strCell = simpleDateFormat.format(cell.getDateCellValue());
                } else {  
//                    strCell = String.valueOf(cell.getNumericCellValue());
                	// 返回数值类型的值
                    Object inputValue = null;// 单元格值
                    Long longVal = Math.round(cell.getNumericCellValue());
                    Double doubleVal = cell.getNumericCellValue();
                    if(Double.parseDouble(longVal + ".0") == doubleVal){   //判断是否含有小数位.0
                        inputValue = longVal;
                    }
                    else{
                        inputValue = doubleVal;
                    }
                    DecimalFormat df = new DecimalFormat("#.####");  
                    strCell = String.valueOf(df.format(inputValue));
                }  
                break;  
            case BOOLEAN:    // get boolean data   
                strCell = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case FORMULA:    // get expression data   
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();  
//                evaluator.evaluateFormulaCell(cell);
                CellValue cellValue = evaluator.evaluate(cell);  
                strCell = String.valueOf(cellValue.getNumberValue()) ;  
                break;  
            default:  
                strCell = "";  
        }  
        return strCell;  
	}
}
