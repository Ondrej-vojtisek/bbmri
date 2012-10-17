package bbmri.testing;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("booksManager")
public class BooksManagerImplSpring implements BooksManager {

    private JdbcTemplate jdbc;
    private SimpleJdbcInsert insertBook;

    /**
     * Setter volany automaticky Springem, z nazvu metody si odvodi vazbu na bean id="dataSource".
     *
     * @param dataSource zdroj pripojeni na databazi
     */
    @Resource
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
        this.insertBook = new SimpleJdbcInsert(dataSource)
                .withTableName("BOOKS")
                .usingColumns("name", "author")
                .usingGeneratedKeyColumns("id");

        // tohle je pro pripad databazi, ktere nemaji poradne JDBC3 ovladace, jako Derby a PostgreSQL
        // viz http://jira.springframework.org/browse/SPR-5306
        try {
            Connection c = dataSource.getConnection();


            this.supportsGetGeneratedKeys = c.getMetaData().supportsGetGeneratedKeys();
            this.databaseProductName = c.getMetaData().getDatabaseProductName();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //kvuli zjistovani vygenerovanych klicu
    private boolean supportsGetGeneratedKeys;
    private String databaseProductName;


    //zpracovani resultsetu
    private static final ParameterizedRowMapper<Book> BOOK_MAPPER = new ParameterizedRowMapper<Book>() {
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            return new Book(rs.getInt("id"), rs.getString("name"), rs.getString("author"));
        }
    };

    public int getNumberOfBooks() {
        return jdbc.queryForInt("SELECT count(*) FROM BOOKS");
    }

    public List<Book> getAllBooks() {
        return jdbc.query("SELECT * FROM BOOKS", BOOK_MAPPER);
    }

    public Book getBookById(int id) {
        return jdbc.queryForObject("SELECT * FROM BOOKS WHERE id=?", BOOK_MAPPER, id);
    }

    public List<Book> findBooksWithAuthor(String authorNameBeginsWith) {
        //vsimnete si toho %, ktere v SQL prestavuje jakekoliv znaky
        return jdbc.query("SELECT * FROM BOOKS where author like ?", BOOK_MAPPER, authorNameBeginsWith + "%");
    }

    public void updateBook(Book book) {
        jdbc.update("UPDATE BOOKS SET name=?,author=? WHERE id=?", book.getName(), book.getAuthor(), book.getId());
    }

    public void removeBook(int bookId) {
        jdbc.update("DELETE FROM BOOKS WHERE id=?", bookId);
    }


    /**
     * Vytvoreni noveho zaznamu v databazi se ziskanim vygenerovaneho id.
     *
     * @param book nova kniha s id=0
     */
    public void addBook(final Book book) {
        // u databazi s korektnim JDBC3 ovladacem (MySQL,Oracle,PostgreSQL 8.4) staci jeden radek
        if (supportsGetGeneratedKeys) {
            System.out.println("Umi getGeneratedKeys()");
            book.setId(insertBook.executeAndReturnKey(new BeanPropertySqlParameterSource(book)).intValue());
        // Derby umi vracet klice, ale metadata to nehlasi
        } else if (databaseProductName.equals("Apache Derby")) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps =
                                    connection.prepareStatement("INSERT INTO BOOKS (name,author) VALUES (?,?)", new String[]{"id"});
                            ps.setString(1, book.getName());
                            ps.setString(2, book.getAuthor());
                            return ps;
                        }
                    },
                    keyHolder);
            book.setId(keyHolder.getKey().intValue());

        // PostgreSQL od verze 8.3 ma nestandardni klicove slovo RETURNING
        // PostgreSQL od verze 8.4 umi getGeneratedKeys
        } else if (databaseProductName.equals("PostgreSQL")) {

            System.err.println("Je to PostgreSQL");
            int id = jdbc.queryForInt("INSERT INTO BOOKS (name,author) VALUES (?,?) RETURNING id", book.getName(), book.getAuthor());
            book.setId(id);
        // v ostatnich pripadech tezko rict
        } else {
            throw new RuntimeException("nepodporuje getGeneratedKeys() a neni to Postgres ani Derby");
        }
    }

}

