package org.sina.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sina.EsFactory;
import org.sina.adapter.EsAdapter;
import org.sina.entity.Location;
import org.sina.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jerold on 2016/11/15.
 */
public class ExcelDataExtractor implements Extractor {
    private EsAdapter adapter;

    public ExcelDataExtractor() {
        adapter = EsFactory.getEsAdapter(1);
    }

    @Override
    public void extract(InputStream in) throws IOException {
        Workbook wb = new XSSFWorkbook(in);
        Sheet sheet = wb.getSheet("bak");
        Iterator rows = sheet.rowIterator();
        rows.next();
        int count = 0;
        List<User> users = new ArrayList<User>();
        try {
            while (rows.hasNext()) {
                Row row = (Row) rows.next();
                Object[] cellVal = new Object[13];
                int column = 0;
                for (int i = 0; i < 13; i++) {
                    Cell cell = row.getCell(i);
                    int k = cell != null ? cell.getCellType() : 1;
                    switch (k) {
                        case 0:
                            cellVal[column] = (long) cell.getNumericCellValue();
                            break;
                        case 2:
                            cellVal[column] = cell.getCellFormula();
                            break;
                        case 4:
                            cellVal[column] = cell.getBooleanCellValue();
                            break;
                        default:
                            cellVal[column] = cell != null ? cell.getStringCellValue() : "";
                    }
                    column++;
                }
                User user = new User();
                user.setId((long) cellVal[0]);
                user.setNickName(String.valueOf(cellVal[1]));
                String loc = String.valueOf(cellVal[2]);
                String[] address = loc.trim().split(" ");
                Location location = new Location();
                location.setProvince(address.length > 0 ? address[0] : "其他");
                if (address.length > 1) {
                    location.setCity(address[1]);
                }
                user.setLocation(location);
                user.setDescription(String.valueOf(cellVal[3]));
                user.setPicSrc(String.valueOf(cellVal[4]));
                user.setGender(String.valueOf(cellVal[5]));
                String fans = String.valueOf(cellVal[6]);
                user.setFans((fans != null && (!"".equals(fans))) ? Integer.valueOf(fans) : 0);
                String follow = String.valueOf(cellVal[7]);
                user.setFollow((follow != null && (!"".equals(follow))) ? Integer.valueOf(follow) : 0);
                String weibo = String.valueOf(cellVal[8]);
                user.setWeiboNum((weibo != null && (!"".equals(weibo))) ? Integer.valueOf(weibo) : 0);
                String collect = String.valueOf(cellVal[9]);
                user.setCollect((collect != null && (!"".equals(collect))) ? Integer.valueOf(collect) : 0);
                String d = String.valueOf(cellVal[10]);
                Date date = null;
                if (d != null && !"".equals(d)) {
                    date = new Date(d);
                }
                user.setCreateDate(date);
                user.setCertification(String.valueOf(cellVal[11]));
                String friends = String.valueOf(cellVal[12]);
                user.setFriends((friends != null && (!"".equals(friends))) ? Integer.valueOf(friends) : 0);
                users.add(user);
                count++;
                if (count >= 200) {
                    //push to ES
                    indexBatch(users);
                    count = 0;
                    users.clear();
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //push the left
            indexBatch(users);
        }

    }

    private void indexBatch(List<User> users) {
        List<String> usersJson = new ArrayList<>();
        for (User u : users) {
            ObjectMapper om = new ObjectMapper();
            String userJson = null;
            try {
                userJson = om.writeValueAsString(u);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            usersJson.add(userJson);
        }
        adapter.index("sina", "users", usersJson);
    }
}
