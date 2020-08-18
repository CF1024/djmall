package com.dj.mall.model.util;

import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    /**
     * 获取工作簿-不用分版本
     * @param inputStream 文件流
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inputStream) throws Exception {
        return WorkbookFactory.create(inputStream);
    }

    /**
     * 读取数据-按行读
     * @param sheet
     * @param start 开始位置
     * @return
     * @throws Exception
     */
    public static List<List<String>> getRowData(Sheet sheet, int start) throws Exception {
        List<List<String>> dataList = new ArrayList<>();
        // 多少行
        int end = sheet.getPhysicalNumberOfRows();
        // 遍历行
        for (int i = start; i < end; i++) {
            Row row = sheet.getRow(i);
            // 多少个单元格
            int cells = row.getPhysicalNumberOfCells();
            List<String> cellDataList = new ArrayList<>();
            for (int j = 0; j < cells; j++) {
                Cell cell = row.getCell(j);
                cellDataList.add(getCellValueToString(cell));
            }
            dataList.add(cellDataList);
        }
        return dataList;
    }

    /**
     * 创建表格数据
     * @param workbook 工作蒲
     * @param sheetName 工作表名
     * @param title 表头信息
     * @param rowList 表格数据
     * @return
     * @throws Exception
     */
    public static void createSheet(Workbook workbook, String sheetName, String[] title, List<List<String>> rowList) throws Exception {
        Sheet sheet = workbook.createSheet(sheetName);
        Row titleRow = sheet.createRow(0);
        // 创建表头
        for (int i = 0; i < title.length; i++) {
            titleRow.createCell(i).setCellValue(title[i]);
        }
        // 表格数据
        for (int i = 0; i < rowList.size(); i++) {
            Row row = sheet.createRow(i + 1);
            List<String> rowData = rowList.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                row.createCell(j).setCellValue(rowData.get(j));
            }
        }
    }

    /**
     * 获取单元格数据
     * @param cell 单元格
     * @return
     */
    public static String getCellValueToString(Cell cell) {
        String strCell = "";
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    LocalDateTime date = cell.getLocalDateTimeCellValue();
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    strCell = dateTimeFormatter.format(date);
                    break;
                }
                // 不是日期格式，则防止当数字过长时以科学计数法显示
                cell.setCellType(CellType.STRING);
                strCell = cell.toString();
                break;
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return strCell;
    }

    /**
     * 将List<User>转化成List<String>
     * @param sourceList
     * @return
     * @throws Exception
     */
    public static List<List<String>> dataToString(List<?> sourceList) throws Exception {
        List<List<String>> dataList = new ArrayList<>();
        sourceList.forEach(data -> {
            // 获取类型
            Class cls = data.getClass();
            // 全部属性
            Field[] fields = cls.getDeclaredFields();
            List<String> dataValues = new ArrayList<>();
            for (Field field : fields) {
                try {
                    // 允许访问私有属性
                    field.setAccessible(true);
                    // 获取属性值
                    dataValues.add(String.valueOf(field.get(data)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            dataList.add(dataValues);
        });
        return dataList;
    }

}
