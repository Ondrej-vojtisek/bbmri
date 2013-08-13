package bbmri.action;

import bbmri.entities.User;
import bbmri.io.ExcelImport;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@PermitAll
@UrlBinding("/user")
public class UserActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String ALL = "/user_all.jsp";
    private static final String CREATE = "/user_create.jsp";

    @ValidateNestedProperties(value = {
                    @Validate(on = {"create"},
                            field = "name",
                            required = true),
                    @Validate(on = {"create"},
                            field = "surname",
                            required = true),
                    @Validate(on = {"create"},
                            field = "password",
                            required = true)
            })
    private User user;
    private Long id;

    private List<User> users;

    public List<User> getUsers() {
        return userService.getAll();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @DontValidate
    @DefaultHandler
    public Resolution display() {
        users = userService.getAll();
        return new ForwardResolution(ALL);
    }
    @DontValidate
    @HandlesEvent("createUser")
    public Resolution createUser(){
        return new ForwardResolution(CREATE);
    }
    public Resolution create() {
        logger.debug("User: " + user);

        userService.create(user);
        getContext().getMessages().add(
                new SimpleMessage("User {0} was created", user)
        );
        return new RedirectResolution(this.getClass(), "display");
    }
    @DontValidate
    public Resolution delete() {
        User user = userService.getById(id);
        userService.remove(user);
        getContext().getMessages().add(
                new SimpleMessage("User {0} was created", user)
        );
        return new RedirectResolution(this.getClass(), "display");
    }

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
    }
}


