package com.bizlogic.tools.functions;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

@SuppressWarnings("all")
public class ExcelTools {

  public static String cellValue(Row row, int column) {
    Cell cell = row.getCell(column);
    String value = null;
    if (cell != null) {
      try {
        CellType cellType = cell.getCellTypeEnum();
        if (cellType.equals(CellType.FORMULA)) {
          cellType = cell.getCachedFormulaResultTypeEnum();
          if (cellType.equals(CellType.NUMERIC)) {
            value = BasicTools.numValue(cell.getNumericCellValue());
          } else {
            value = cell.getStringCellValue();
          }
        } else {
          if (cellType.equals(CellType.NUMERIC)) {
            value = BasicTools.numValue(cell.getNumericCellValue());
          } else {
            value = cell.getStringCellValue();
          }
        }
      } catch (Exception e) {
        // Error
      }
    }
    return value;
  }

  public static Boolean cellNumeric(Row row, int column) {
    Cell cell = row.getCell(column);
    Boolean result = false;
    if (cell != null) {
      try {
        CellType cellType = cell.getCellTypeEnum();
        if (cellType.equals(CellType.NUMERIC)) {
          result = true;
        } else if (cellType.equals(CellType.FORMULA)) {
          result = cell.getCachedFormulaResultTypeEnum().equals(CellType.NUMERIC);
        }
      } catch (Exception e) {
        // Error
      }
    }
    return result;
  }

  public static Integer sheetRecords(Sheet sheet) {
    Iterator<Row> iterator = sheet.iterator();
    String sheetName = sheet.getSheetName();
    Integer records = 0;
    while (iterator.hasNext()) {
      Row row = iterator.next();
      Row nextRow = sheet.getRow(row.getRowNum() + 1);
      Boolean emptyRow = StringUtils.isBlank(cellValue(row, 1));
      Boolean emptyNextRow = nextRow != null && StringUtils.isBlank(cellValue(nextRow, 1));

      if (emptyRow && emptyNextRow) {
        break;
      }

      records++;
    }
    return records;
  }

}
