package cz.bbmri.entities.systemAdministration;

import cz.bbmri.entities.User;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.3.14
 * Time: 11:42
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class UserSetting implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_LOCALE = "cs";

    // This annotation is to avoid identifier of userSetting
    // Identifier is not needed here bcs is oneToOne relationship with user

    @Id
    @GeneratedValue(generator = "customForeignGenerator")
    @org.hibernate.annotations.GenericGenerator(
            name = "customForeignGenerator",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user")
    )
    private Long id;

    @OneToOne(mappedBy = "userSetting")
    @PrimaryKeyJoinColumn
    private User user;

    private int numberOfRecordsPerPage;

    private boolean sendNotificationToEmail;

    private String locale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumberOfRecordsPerPage() {
        return numberOfRecordsPerPage;
    }

    public void setNumberOfRecordsPerPage(int numberOfRecordsPerPage) {
        this.numberOfRecordsPerPage = numberOfRecordsPerPage;
    }

    public boolean isSendNotificationToEmail() {
        return sendNotificationToEmail;
    }

    public void setSendNotificationToEmail(boolean sendNotificationToEmail) {
        this.sendNotificationToEmail = sendNotificationToEmail;
    }

    public Locale getLocale() {
        if (locale == null) {
            return new Locale(DEFAULT_LOCALE);
        } else {
            return new Locale(locale);
        }
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSetting that = (UserSetting) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "UserSetting{" +
                "id=" + id +
                ", user=" + user +
                ", numberOfRecordsPerPage=" + numberOfRecordsPerPage +
                '}';
    }
}
