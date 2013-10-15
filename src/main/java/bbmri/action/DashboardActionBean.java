package bbmri.action;

import bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 8.10.13
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
@UrlBinding("/dashboard")
public class DashboardActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    BiobankFacade biobankFacade;

    public int cisloA = 1;
    public int cisloB = 2;

    public int getCisloAA() {
        return cisloA;
    }

    public int getCisloBB() {
        return cisloB;
    }

    private BiobankFacade getFacade(){
        return biobankFacade;
    }

    private static final String DEFAULT = "/dashboard.jsp";


    @DontValidate
    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(DEFAULT);
    }

    //OK
    @HandlesEvent("iamsecure")
    @RolesAllowed({"user"})
    public Resolution iamsecure() {
        return new ForwardResolution("/WEB-INF/jsp/some.jsp");
    }

    //OK
    @HandlesEvent("iamsecure2")
    @RolesAllowed({"administrator"})
    public Resolution iamsecure2() {
        return new ForwardResolution("/WEB-INF/jsp/some.jsp");
    }

    //OK
    @HandlesEvent("iamsecure3")
    @RolesAllowed({"user if ${1 == 1}"})
    public Resolution iamsecure3() {
        return new ForwardResolution("/WEB-INF/jsp/some.jsp");
    }

    //OK
    @HandlesEvent("iamsecure4")
    @RolesAllowed({"user if ${1 == 2}"})
    public Resolution iamsecure4() {
        return new ForwardResolution("/WEB-INF/jsp/some.jsp");
    }

    //OK
    @HandlesEvent("iamsecure5")
    @RolesAllowed({"user if ${cisloA == cisloB}"})
    public Resolution iamsecure5() {
        return new ForwardResolution("/WEB-INF/jsp/some.jsp");
    }

    // wrong
    @HandlesEvent("iamsecure6")
    @RolesAllowed({"user if ${getCisloAA == getCisloBB}"})
    public Resolution iamsecure6() {
        return new ForwardResolution("/WEB-INF/jsp/some.jsp");
    }

    //OK
    @HandlesEvent("iamsecure7")
    @RolesAllowed({"user if ${cisloAA == cisloBB}"})
    public Resolution iamsecure7() {
        return new ForwardResolution("/WEB-INF/jsp/some.jsp");
    }

    @HandlesEvent("iamsecure8")
    @RolesAllowed({"user if ${context.myId == 2}"})
    public Resolution iamsecure8() {
        return new ForwardResolution("/WEB-INF/jsp/some.jsp");
    }

    @HandlesEvent("iamsecure10")
    @RolesAllowed({"user if ${facade.true}"})
    public Resolution iamsecure10() {
        return new ForwardResolution("/WEB-INF/jsp/some.jsp");
    }



}
