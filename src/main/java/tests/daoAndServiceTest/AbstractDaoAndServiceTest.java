package tests.daoAndServiceTest;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import tests.testUtils.TestUtils;


/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.9.13
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
@Ignore
public class AbstractDaoAndServiceTest extends TestUtils {

}
