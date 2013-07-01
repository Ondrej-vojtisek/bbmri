package Experimental;

import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.entities.User;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 17.4.13
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorld {


    //@SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
       // String filename = "pokus.xlsx";
       // List<User> users = parseUserFile(filename);
      //  System.out.println("Users: " + users);

        System.out.println("Hello");
    //    String filename = "mou_data.xls";
    //    List<Sample> samples = parseSampleFile(filename);
    //    System.out.println("Samples: " + samples);

    }


    public static List<User> parseUserFile(String filename) throws Exception {
        List<User> users = new ArrayList<User>();
        FileInputStream fis = null;
           try {
                      fis = new FileInputStream(filename);

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
    }

    private static User parseUser(List rowData) {
        User user = new User();
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
    }

    public static List<Sample> parseSampleFile(String filename) throws Exception {
           List<Sample> samples = new ArrayList<Sample>();
           FileInputStream fis = null;
              try {
                         fis = new FileInputStream(filename);

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
                                 parseSample(samples, data);
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
           return samples;
       }


    private static void parseSample(List<Sample> samples, List rowData) {

            for (int i = 0; i < rowData.size(); i++) {
                Cell cell = (Cell) rowData.get(i);
                switch (i) {
                    case 0:
                        // Zaznam
                        break;
                    case 1:
                        // Datum - ceho?
                        break;
                    case 2:
                        // Pacient
                        break;
                    case 3:
                        // RC
                        break;
                    case 4:
                        // Chorobopis
                        break;
                    case 5:
                       // Sample sample = new Sample();
                       // String bm = cell.getStringCellValue();
                        //bm.
                       // String record = bm.substring(bm.indexOf('('), bm.indexOf(')'));

                        break;
                }
            }
        }

}
