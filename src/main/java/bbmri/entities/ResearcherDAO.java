package bbmri.entities;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public interface ResearcherDAO {

        public void addResearcher(Researcher researcher);

        public void removeResearcher(Researcher researcher);

        public void removeResearcher(long id);

        public void updateResearcher(Researcher researcher);

        public List<Researcher> getAllResearchers();

        public Researcher getResearcher(long id);

        // temporal prosthesis
        public boolean verifyPassword(String password, long id);
}
