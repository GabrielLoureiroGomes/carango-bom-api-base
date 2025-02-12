package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.mappers.UserFullRowMapper;
import br.com.caelum.carangobom.mappers.UserRowMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public Optional<User> findByUsername(String name) {
        try {
            String findByNameQuery = "SELECT ID, NAME, PASSWORD, CREATED_AT, UPDATED_AT FROM USERS WHERE NAME = ?";

            return Optional.ofNullable(jdbcTemplate.queryForObject(findByNameQuery, new UserFullRowMapper(), name));
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void changePassword(Long id, String password) {
        try {
            String updateQuery = "UPDATE USERS SET PASSWORD = ? WHERE ID = ?";

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(updateQuery);
                ps.setString(1, passwordEncoder.encode(password));
                ps.setLong(2, id);

                return ps;
            });

        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    public Optional<User> create(User user) {
        String insertQuery = "INSERT INTO USERS(NAME, PASSWORD, CREATED_AT) VALUES(?, ?, ?) RETURNING ID";

        try {
            KeyHolder key = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername().trim());
                ps.setString(2, passwordEncoder.encode(user.getPassword().trim()));
                ps.setDate(3, Date.valueOf(LocalDate.now()));

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
