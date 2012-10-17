package bbmri.model;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@UrlBinding("/zaznam/{$event}/{zaznam.id}")
public class ZaznamActionBean implements ActionBean {

    private MujActionBeanContext ctx;
    public void setContext(ActionBeanContext ctx) { this.ctx = (MujActionBeanContext) ctx; }
    public MujActionBeanContext getContext() { return ctx; }

    public List<Zaznam> getZaznamy() {
        return ctx.getZaznamy();
    }

    @ValidateNestedProperties(value = {
                @Validate(on = {"pridej", "uloz"}, field = "jmeno", required = true),
                @Validate(on = {"pridej", "uloz"}, field = "pocet", required = true, minvalue = 1)
            }
    )
    private Zaznam zaznam;
    public Zaznam getZaznam() { return zaznam;  }

    private String pokus;
    public String getPokus() {return pokus;}
    public void setPokus(String pokus) {this.pokus = pokus;}

    public void setZaznam(Zaznam zaznam) { this.zaznam = zaznam; }

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/index.jsp");
    }

    public Resolution pridej() {
        /*
        String s = (String) getContext().getRequest().getAttribute("z2");
        System.out.println("POKUS: " + s + "\n");
                                                  */

         System.out.println("POKUS: " + pokus + "\n");

         EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
          EntityManager em = emf.createEntityManager();
          em.getTransaction().begin();
            em.persist(zaznam);
            em.getTransaction().commit();
            em.close();

        getZaznamy().add(zaznam);
        return new RedirectResolution(this.getClass(), "zobraz");
    }
}

