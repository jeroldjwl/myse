package org.sina.data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sina.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Jerold on 2016/11/15.
 */
public class XmlDataExtractor implements Extractor {
    private ArrayList<User> users;

    public XmlDataExtractor() {

    }

    @Override
    public void extract(InputStream in) throws IOException {
        Workbook wb = new XSSFWorkbook(in);
        Sheet sheet = wb.getSheet("bak");
        Iterator rows = sheet.rowIterator();
        int count = 0;
        while (rows.hasNext()) {
            count++;
            Row row = (Row) rows.next();
            Iterator iterator = row.cellIterator();
            while (iterator.hasNext()) {
                Cell cell = (Cell) iterator.next();
                int i = cell.getCellType();
                switch (i) {
                    case 0:
                        System.out.printf("%d\t", (long) cell.getNumericCellValue());
                        break;
                    case 2:
                        System.out.printf("%s\t", cell.getCellFormula());
                        break;
                    case 4:
                        System.out.printf("%s\t", cell.getBooleanCellValue());
                        break;
                    default:
                        System.out.printf("%s\t", cell.getStringCellValue());
                }
            }
            System.out.println();
        }
        System.out.println(count);
    }
}
