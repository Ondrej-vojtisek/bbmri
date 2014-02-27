package tests.daoTest;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.entities.Biobank;
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
public class SampleQuestionDaoTest extends AbstractDaoTest {

    @Autowired
    SampleQuestionDao sampleQuestionDao;

    @Autowired
    BiobankDao biobankDao;

    @Test
    public void getAllByBiobankAndProcessed() {

//        Biobank biobank1 = createTestBiobank(1);
//        biobankDao.create(biobank1);
//
//        Biobank biobank2 = createTestBiobank(2);
//        biobankDao.create(biobank2);
//
//        SampleRequest sq1 = createTestSampleQuestion(1);
//        sq1.setBiobank(biobank1);
//        sampleQuestionDao.create(sq1);
//
//        SampleRequest sq2 = createTestSampleQuestion(2);
//        sq2.setBiobank(biobank1);
//        sampleQuestionDao.create(sq2);
//
//        SampleRequest sq3 = createTestSampleQuestion(3);
//        sq3.setBiobank(biobank2);
//        sampleQuestionDao.create(sq3);
//
//        sampleQuestionDao.update(sq1);

//        assertEquals(true, sampleRequestDao.getByBiobankAndProcessed(biobank1, true).contains(sq1));
//        assertEquals(true, sampleRequestDao.getByBiobankAndProcessed(biobank1, false).contains(sq2));
//        assertEquals(true, sampleRequestDao.getByBiobankAndProcessed(biobank2, false).contains(sq3));
    }
}
