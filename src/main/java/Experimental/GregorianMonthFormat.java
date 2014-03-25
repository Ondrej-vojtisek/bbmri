package experimental;

import org.apache.axis2.databinding.types.soapencoding.GMonth;
import org.apache.axis2.databinding.types.soapencoding.GYear;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 22.3.14
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public class GregorianMonthFormat {

    public static void main(String[] args) {
        GYear year = GYear.Factory.fromString("1980", "http://www.w3.org/2001/XMLSchema");

        GMonth month = GMonth.Factory.fromString("--02--", "http://www.w3.org/2001/XMLSchema");

        GYear year2 = GYear.Factory.fromString("1980", "http://www.w3.org/2001/XMLSchema");

        GMonth month2 = GMonth.Factory.fromString("--03--", "http://www.w3.org/2001/XMLSchema");

        String birthMonth = "--03";

        System.out.println("Year1: " + year);
        System.out.println("Month1: " + month.toString().substring(2,4));
        System.out.println(Integer.parseInt(month.toString().substring(2,4)));
        System.out.println("Year2: " + year2);
        System.out.println("Month3: " + Integer.parseInt(birthMonth.substring(2,4)));

//
//        Calendar calendar = Calendar.getInstance();
//
//        if (birthMonth != null) {
//            month = birthMonth;
//        }
//
//        if (birthYear != null) {
//            year = birthYear;
//        }
//
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//               /* calendar month is zero based - January is 0, February 1 etc.*/
//        int monthNow = cal.get(Calendar.MONTH) - 1;
//        int yearNow = cal.get(Calendar.YEAR);
//
//        Integer age = yearNow - year;
//        if (monthNow < month) age = age - 1;


    }
}
