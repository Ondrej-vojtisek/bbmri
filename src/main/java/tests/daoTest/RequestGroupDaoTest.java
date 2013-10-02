package tests.daoTest;

import bbmri.dao.BiobankDao;
import bbmri.dao.ProjectDao;
import bbmri.dao.RequestGroupDao;
import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.RequestGroup;
import bbmri.entities.RequestState;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public class RequestGroupDaoTest extends AbstractDaoTest {

    @Autowired
    RequestGroupDao requestGroupDao;


    @Autowired
    BiobankDao biobankDao;


    @Test
    public void getAllByBiobankAndState() {
        Biobank biobank1 = createTestBiobank(1);
        biobankDao.create(biobank1);

        Biobank biobank2 = createTestBiobank(2);
        biobankDao.create(biobank2);

        RequestGroup rqg1 = createRequestGroup(1);
        rqg1.setBiobank(biobank1);
        requestGroupDao.create(rqg1);

        RequestGroup rqg2 = createRequestGroup(2);
        rqg2.setBiobank(biobank2);
        requestGroupDao.create(rqg2);

        rqg1.setRequestState(RequestState.APPROVED);

        assertEquals(true, requestGroupDao.getByBiobankAndState(biobank1, RequestState.NEW).isEmpty());
        assertEquals(false, requestGroupDao.getByBiobankAndState(biobank1, RequestState.APPROVED).isEmpty());
        assertEquals(1, requestGroupDao.getByBiobankAndState(biobank1, RequestState.APPROVED).size());
        assertEquals(1, requestGroupDao.getByBiobankAndState(biobank2, RequestState.NEW).size());

    }
}
