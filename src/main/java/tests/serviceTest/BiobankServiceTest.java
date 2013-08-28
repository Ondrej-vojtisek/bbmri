package tests.serviceTest;

import bbmri.dao.BiobankDao;
import bbmri.dao.UserDao;
import bbmri.entities.Biobank;
import bbmri.entities.User;
import bbmri.service.BiobankService;
import bbmri.serviceImpl.BiobankServiceImpl;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tests.AbstractServiceTest;

import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 14.8.13
 * Time: 15:53
 * To change this template use File | Settings | File Templates.
 */
public class BiobankServiceTest extends AbstractServiceTest {

    @Mock
    private BiobankDao biobankDao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private BiobankService biobankService = new BiobankServiceImpl();

    @Test
    public void updateBiobankTest() {
        /* ********* GIVEN ********** */
        Biobank biobank = new Biobank();
        biobank.setName("Biobank");
        biobank.setAddress("Address");
        biobank.setId(new Long(1));
        when(biobankDao.get(new Long(1)))
                       .thenReturn(biobank);
         /* ********* WHEN ********** */

          Biobank biobank2 = new Biobank();
          biobank2.setName("BioName");
          biobank2.setId(new Long(1));
          Biobank result = biobankService.update(biobank2);

         /* ********* THEN ********** */

        assertEquals(result.getId(), biobank2.getId());
        assertEquals(result.getName(), biobank2.getName());
        assertEquals(result.getAddress(), biobank.getAddress());
    }

    @Test
       public void removeAdministratorTest() {
           /* ********* GIVEN ********** */
           User user = new User();
           user.setName("Tester");
           user.setId(new Long(1));

           Biobank biobank = new Biobank();
           biobank.setName("Biobank");
           biobank.setAddress("Address");
           biobank.setId(new Long(1));
           biobank.getAdministrators().add(user);
           user.setBiobank(biobank);

           when(biobankDao.get(new Long(1)))
                          .thenReturn(biobank);
           when(userDao.get(new Long(1)))
                                 .thenReturn(user);
            /* ********* WHEN ********** */

             User result = biobankService.removeAdministratorFromBiobank(user.getId(), biobank.getId());

            /* ********* THEN ********** */

           assertEquals(result.getBiobank(), null);
       }

    @Test(expected=IllegalArgumentException.class)
    public void exceptionTest(){
            biobankService.create(null,null);
    }


}
