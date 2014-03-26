package tests.serviceTest;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.impl.BiobankServiceImpl;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
       // biobank.setAddress("Address");
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
       // assertEquals(result.getAddress(), biobank.getAddress());
    }

//    @Test(expected=IllegalArgumentException.class)
//    public void exceptionTest(){
//            biobankService.create(null,null);
//    }


}
