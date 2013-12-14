package experimental;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 3.10.13
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
public class ExperimentalActionBean {

      /*
    private FileBean excelFileBean;

    public FileBean getExcelFileBean() {
        return excelFileBean;
    }

    public void setExcelFileBean(FileBean excelFileBean) {
        this.excelFileBean = excelFileBean;
    }
    @DontValidate
    public Resolution uploadExcel() {
        String filePath = "temp\\" + excelFileBean.getFileName();
        if (excelFileBean == null) {
            getContext().getMessages().add(
                    new SimpleMessage("ExcelFileBean null")
            );
            return new ForwardResolution(this.getClass(), "display");
        }
        File file = new File(filePath);

        try {
            excelFileBean.save(file);

        } catch (IOException e) {
            getContext().getMessages().add(
                    new SimpleMessage("Exception: " + e)
            );
        }

        List<User> users = new ArrayList<User>();

        try {
            ExcelImport excelImport = new ExcelImport();
            users = excelImport.parseExcelUserTable(filePath);

            for(User user : users){
                userService.create(user);
            }

        } catch (Exception e) {
            getContext().getMessages().add(
                    new SimpleMessage("Exception: " + e)
            );
        }
        file.delete();

        return new ForwardResolution(this.getClass(), "display");
    }*/

}
