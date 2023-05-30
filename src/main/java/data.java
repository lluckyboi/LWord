import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class data {
    public Object[][] Data;

    public data() {
        String filePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath() + "/words.xls";
        System.out.println("路径: " + filePath);

        try (FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new HSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum() + 1;
            int columnCount = sheet.getRow(0).getLastCellNum();

            // 创建一个二维数组来保存数据
            Data = new Object[rowCount][columnCount];

            // 跳过第零行的标题
            for (int row = 1; row < rowCount; row++) {
                Row sheetRow = sheet.getRow(row);
                for (int column = 0; column < columnCount; column++) {
                    Cell cell = sheetRow.getCell(column);
                    Data[row][column] = getCellValue(cell);
                }
            }

            // 打印数组中的数据
//            for (Object[] rowData : Data) {
//                for (Object cellData : rowData) {
//                    System.out.print(cellData + "\t");
//                }
//                System.out.println();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
}

    private static Object getCellValue(Cell cell) {
        Object value = null;
        if (cell != null) {
            value = switch (cell.getCellType()) {
                case NUMERIC -> cell.getNumericCellValue();
                case STRING  -> cell.getStringCellValue();
                default -> "";
            };
        }
        return value;
    }
}
