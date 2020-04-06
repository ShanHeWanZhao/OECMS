import org.junit.Test;

import java.io.IOException;

/**
 * @author Trd
 * @date 2020-04-05 18:22
 */
public class ExcelTest {

	@Test
	public void fun() throws IOException {
//		List<LoginInfo> temp = new ArrayList<>();
//		FileInputStream fileIn = new FileInputStream("C:/Users/15155/Desktop/java_test.xlsx");
//		//根据指定的文件输入流导入Excel从而产生Workbook对象
//		HSSFWorkbook wb0 = new HSSFWorkbook(fileIn);
//		//获取Excel文档中的第一个表单
//		Sheet sht0 = wb0.getSheetAt(0);
//		//对Sheet中的每一行进行迭代
//		for (Row r : sht0) {
//			//如果当前行的行号（从0开始）未达到2（第三行）则从新循环
//			if (r.getRowNum()<1){
//				continue;
//			}
//			//创建实体类
//			LoginInfo info=new LoginInfo();
//			//取出当前行第1个单元格数据，并封装在info实体stuName属性上
//			info.setUsername(r.getCell(1).getStringCellValue());
//			info.setPassword(r.getCell(1).getStringCellValue());
//			System.out.println(r.getCell(2).getNumericCellValue());
//			System.out.println(r.getCell(3).getNumericCellValue());
//			System.out.println(r.getCell(4).getNumericCellValue());
//			temp.add(info);
//		}
//		fileIn.close();
//		System.out.println(temp);
	}
}