package cz.bbmri.io;

import cz.bbmri.entities.User;
//import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 17.4.13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class ExcelImport {

    public List<User> parseExcelUserTable(String fileName) throws Exception {
       /* List<User> users = new ArrayList<User>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);

            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator rows = sheet.rowIterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row row = (Row) rows.next();
                Iterator cells = row.cellIterator();

                List data = new ArrayList();
                while (cells.hasNext()) {
                    Cell cell = (Cell) cells.next();
                    data.add(cell);
                }
                if (rowNumber != 0) {
                    users.add(parseUser(data));
                }
                rowNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return users;
              */
        return null;
    }

    private static User parseUser(List rowData) {

       /* User user = new User();
        for (int i = 0; i < rowData.size(); i++) {
            Cell cell = (Cell) rowData.get(i);

            switch (i) {
                case 0:
                    user.setId(((Double) cell.getNumericCellValue()).longValue());
                    break;
                case 1:
                    String admin = cell.getStringCellValue();
                    if (admin.equals("t") || admin.equals("true")) {
                        user.setAdministrator(true);
                    } else {
                        user.setAdministrator(false);
                    }
                    break;
                case 2:
                    user.setName(cell.getStringCellValue());
                    break;
                case 3:
                    String online = cell.getStringCellValue();
                    if (online.equals("t") || online.equals("true")) {
                        user.setOnline(true);
                    } else {
                        user.setOnline(false);
                    }
                    break;
                case 4:
                    user.setPassword(cell.getStringCellValue());
                    break;
                case 5:
                    user.setSurname(cell.getStringCellValue());
                    break;
                case 6:
                    Long biobankId = (((Double) cell.getNumericCellValue()).longValue());
                    Biobank biobank = new Biobank();
                    biobank.setId(biobankId.longValue());
                    user.setBiobank(biobank);
                    break;
            }
        }
        return user;
        */
        return null;
    }
}
