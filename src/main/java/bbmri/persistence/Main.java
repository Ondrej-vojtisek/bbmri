package bbmri.persistence;

    import java.util.List;
    import javax.persistence.EntityManager;
    import javax.persistence.EntityManagerFactory;
    import javax.persistence.Persistence;
    import javax.persistence.Query;
    /**
     *
     * @author Ori
     */
    public class Main {
          public static void main(String[] args)
        {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
            EntityManager em = emf.createEntityManager();
            Person person = new Person();
            em.getTransaction().begin();

            person.setAddress("Botanicka 68a, Brno");
            person.setFirstName("Paul");
            person.setLastName("Smith");

            em.persist(person);
            em.getTransaction().commit();
            dump(em);

            em.getTransaction().begin();
            person.setAddress("Chvalovice 12");
            person.setLastName("Brown");

            em.getTransaction().commit();
            dump(em);

            em.close();
            emf.close();
        }

        static void dump(EntityManager em)
        {
            Query query = em.createQuery("SELECT p FROM Person p");
            List<Person> results = query.getResultList();
            System.out.println(results.toString());
        }

    }



