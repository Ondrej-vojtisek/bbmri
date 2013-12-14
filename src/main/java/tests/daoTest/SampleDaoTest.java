package tests.daoTest;

import cz.bbmri.dao.SampleDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 22:54
 * To change this template use File | Settings | File Templates.
 */
public class SampleDaoTest extends AbstractDaoTest {


    @Autowired
    SampleDao sampleDao;

    /*
    @Test
    public void getSelectedTest(){
    }  */

    @Test
       public void notImplementedYet(){
           assertEquals(1,1);
       }
}
