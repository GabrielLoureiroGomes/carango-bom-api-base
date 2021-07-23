package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.mappers.UserRowMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Repository
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAll() {
        String findAllQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM USERS";

        return jdbcTemplate.query(findAllQuery, new UserRowMapper());
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            String findByIdQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM USERS WHERE ID = ?";

            return Optional.ofNullable(jdbcTemplate.queryForObject(findByIdQuery, new UserRowMapper(), id));
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void changePassword(Long id, String password) {
        try {
            String updateQuery = "UPDATE USERS SET PASSWORD = crypt(?, gen_salt('bf') WHERE ID = ?";

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(updateQuery);
                ps.setString(1, password);
                ps.setLong(2, id);

                return ps;
            });

        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    public Optional<User> create(User user) {
        String insertQuery = "INSERT INTO USERS(NAME, PASSWORD, CREATED_AT) VALUES(?, crypt(?, gen_salt('bf'), ?) RETURNING ID";

        try {
            KeyHolder key = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName().trim());
                ps.setString(2, user.getPassword().trim());
                ps.setDate(2, Date.valueOf(LocalDate.now()));

                return ps;
            }, key);

            return findById(Objects.requireNonNull(key.getKey()).longValue());
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM USERS WHERE ID = ?";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(deleteQuery);
                ps.setLong(1, id);

                return ps;
            });
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }
}
