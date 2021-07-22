package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.mappers.BrandRowMapper;
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
public class BrandRepositoryImpl implements BrandRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Brand> findAll() {
        String findAllQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM BRANDS";

        return jdbcTemplate.query(findAllQuery, new BrandRowMapper());
    }

    @Override
    public Optional<Brand> findById(Long id) {
        try {

            String findByIdQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM BRANDS WHERE ID = ?";

            return Optional.ofNullable(jdbcTemplate.queryForObject(findByIdQuery, new BrandRowMapper(), id));

        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Brand> findByName(String name) {
        String findByNameQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM BRANDS WHERE NAME = ?";

        try {

            return Optional.ofNullable(jdbcTemplate.queryForObject(findByNameQuery, new BrandRowMapper(), name));

        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM BRANDS WHERE ID = ?";

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

    @Override
    public Optional<Brand> create(String name) {
        String insertQuery = "INSERT INTO BRANDS(NAME, CREATED_AT) VALUES(?, ?) RETURNING ID";

        try {
            KeyHolder key = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name.trim());
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
    public Optional<Brand> update(Long id, String brandName) {
        try {
            String updateQuery = "UPDATE BRANDS SET NAME = ?, UPDATED_AT = ? WHERE ID = ?";

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(updateQuery);
                ps.setString(1, brandName.trim());
                ps.setDate(2, Date.valueOf(LocalDate.now()));
                ps.setLong(3, id);

                return ps;
            });

            return findById(id);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return Optional.empty();

    }


}
