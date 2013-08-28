package tests.daoAndServiceTest;

import bbmri.dao.BiobankDao;
import bbmri.entities.Biobank;
import bbmri.entities.User;
import bbmri.service.BiobankService;
import bbmri.service.UserService;
import bbmri.serviceImpl.BiobankServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 28.8.13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class BiobankComplexTest extends AbstractTest {

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private UserService userService;

    private User createTestUser() {
        User user = new User();
        user.setName("Pokusny");
        user.setSurname("Uzivatel");
        return user;
    }

    private Biobank createTestBiobank() {
        Biobank biobank = new Biobank();
        biobank.setName("Biobank");
        biobank.setAddress("Address");
        return biobank;
    }

    @Test
    public void createBiobankTest(){
        /* Relationship is usable from both sides */

        Biobank biobank = createTestBiobank();
        User user = createTestUser();
        userService.create(user);
        biobankService.create(biobank,user.getId());
        assertEquals(biobank, user.getBiobank());
        assertEquals(user, biobank.getAdministrators().get(0));
    }

    @Test
    public void createBiobankTest2(){
        /* User not present in DB */
        User user = createTestUser();
        Biobank biobank = createTestBiobank();
        userService.create(user);
        biobankService.create(biobank, new Long(5));
        assertEquals(user.getBiobank(), null);
        assertEquals(true, biobank.getAdministrators().isEmpty());
    }

    @Test
    public void removeBiobankTest(){
           /* Test if remove involves second side of relationship */

           Biobank biobank = createTestBiobank();
           User user = createTestUser();
           userService.create(user);
           biobankService.create(biobank,user.getId());
           biobankService.remove(biobank.getId());
           assertEquals(biobankService.get(biobank.getId()), null);
           assertEquals(user, userService.get(user.getId()));
           user = userService.get(user.getId());
           assertEquals(null, user.getBiobank());
    }

    @Test
      public void removeBiobankTest2(){
             /* Test if remove involves second side of relationship */

             Biobank biobank = createTestBiobank();
             User user = createTestUser();
             userService.create(user);
             biobankService.create(biobank,user.getId());
             userService.remove(user.getId());
             assertEquals(biobankService.get(biobank.getId()), biobank);
             assertEquals(userService.get(user.getId()), null);
             biobank = biobankService.get(biobank.getId());
             assertEquals(true, biobank.getAdministrators().isEmpty());
      }



}
