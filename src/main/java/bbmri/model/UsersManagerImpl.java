package bbmri.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * user: Ori
 * Date: 3.9.12
 * Time: 23:02
 * To change this template use File | Settings | File Templates.
 */
@Repository("usersManager")
public class UsersManagerImpl implements UsersManager {

         private JdbcTemplate jdbc;
         private SimpleJdbcInsert insertUser;

          /**
     * Setter volany automaticky Springem, z nazvu metody si odvodi vazbu na bean id="dataSource".
     *
     * @param dataSource zdroj pripojeni na databazi
     */
    @Resource
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingColumns("name", "surname", "admin", "leader")
                .usingGeneratedKeyColumns("id");
        try {
            Connection c = dataSource.getConnection();
            this.supportsGetGeneratedKeys = c.getMetaData().supportsGetGeneratedKeys();
            this.databaseProductName = c.getMetaData().getDatabaseProductName();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean supportsGetGeneratedKeys;
    private String databaseProductName;



    public void addUser(final User user) {
        if (supportsGetGeneratedKeys) {
            user.setId(insertUser.executeAndReturnKey(new BeanPropertySqlParameterSource(user)).intValue());
        }else {
            throw new RuntimeException("Doesn't support getGeneratedKeys()");
        }
    }
      /*
      private static final ParameterizedRowMapper<User> USER_MAPPER = new ParameterizedRowMapper<User>() {
        public User mapRow(ResultSet rs, int i) throws SQLException {
            return new User(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getBoolean("admin"),
                    rs.getBoolean("leader"));
        }
    };

    public List<User> getAllUsersFromDB() {
        return jdbc.query("SELECT * FROM USERS;", USER_MAPPER);
    }  */
}

