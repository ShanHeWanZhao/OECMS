package com.trd.oecms.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * excel读写工具类
 * @author Trd
 * @date 2020-04-05 18:42
 */
public class POIUtil {
	private final static String XLS = "xls";
	private final static String XLSX = "xlsx";
	private final static Logger logger = LoggerFactory.getLogger(POIUtil.class);
	private static POIUtil poiUtil = new POIUtil();

	private POIUtil() {
	}

	/**
	 * 读入excel文件，解析后返回
	 * @param file
	 * @throws IOException
	 */
	public static List<String[]> readExcel(MultipartFile file) throws IOException {
		//检查文件
		poiUtil.checkFile(file);
		//获得Workbook工作薄对象
		Workbook workbook = poiUtil.getWorkBook(file);
		//创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<String[]> list = new ArrayList<>();
		if(workbook != null){
			for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
				//获得当前sheet工作表
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if(sheet == null){
					continue;
				}
				// 获得当前sheet的开始行
				int firstRowNum  = sheet.getFirstRowNum();
				// 获得当前sheet的结束行
				int lastRowNum = sheet.getLastRowNum();
				// 循环除了第一行的所有行
				for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
					//获得当前行
					Row row = sheet.getRow(rowNum);
					if(row == null){
						continue;
					}
					// 获得当前行的开始列
					int firstCellNum = row.getFirstCellNum();
					// firstCellNum为-1时，当前行就不存在了
					if (firstCellNum != -1){
						//获得当前行的列数
						int lastCellNum = row.getPhysicalNumberOfCells();
						String[] cells = new String[lastCellNum];
						//循环当前行
						for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
							Cell cell = row.getCell(cellNum);
							cells[cellNum] = poiUtil.getCellValue(cell);
						}
						list.add(cells);
					}
				}
			}
			workbook.close();
		}
		return list;
	}
	/**
	 * 检查文件
	 * @param file
	 * @throws IOException
	 */
	private  void checkFile(MultipartFile file) throws IOException{
		//判断文件是否存在
		if(null == file){
			logger.error("文件不存在！");
			throw new FileNotFoundException("文件不存在！");
		}
		//获得文件名
		String fileName = file.getOriginalFilename();
		//判断文件是否是excel文件
		if(!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)){
			logger.error(fileName + "不是excel文件");
			throw new IOException(fileName + "不是excel文件");
		}
	}

	/**
	 * 获得当前Excel的Workbook对象
	 * @param file
	 * @return
	 */
	private  Workbook getWorkBook(MultipartFile file) {
		//获得文件名
		String fileName = file.getOriginalFilename();
		//创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			//获取excel文件的io流
			InputStream is = file.getInputStream();
			//根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if(fileName.endsWith(XLS)){
				//2003
				workbook = new HSSFWorkbook(is);
			}else if(fileName.endsWith(XLSX)){
				//2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			logger.warn("获取 Workbook 对象失败:{}",e.getMessage(),e);
		}
		return workbook;
	}

	/**
	 * 获取每个单元格的值，转换为String类型
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell){
		String cellValue = "";
		if(cell == null){
			return cellValue;
		}
		//把数字当成String来读，避免出现1读成1.0的情况
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		//判断数据的类型
		switch (cell.getCellType()){
			//数字
			case Cell.CELL_TYPE_NUMERIC:
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			//字符串
			case Cell.CELL_TYPE_STRING:
				cellValue = String.valueOf(cell.getStringCellValue());
				break;
			//Boolean
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			//公式
			case Cell.CELL_TYPE_FORMULA:
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			//空值
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			//故障
			case Cell.CELL_TYPE_ERROR:
				cellValue = "非法字符";
				break;
			default:
				cellValue = "未知类型";
				break;
		}
		return cellValue;
	}
}

