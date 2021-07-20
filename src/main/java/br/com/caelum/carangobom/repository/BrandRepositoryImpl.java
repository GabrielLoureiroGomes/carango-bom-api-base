package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.mappers.BrandRowMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Log4j2
@Repository
public class BrandRepositoryImpl implements BrandRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        final SQLErrorCodeSQLExceptionTranslator customSQLErrorCodesTranslation = new SQLErrorCodeSQLExceptionTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodesTranslation);
    }

    @Override
    public List<Brand> findAll() {
        String findAllQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM BRANDS";
        return jdbcTemplate.query(findAllQuery, new BrandRowMapper());
    }

    @Override
    public Optional<Brand> findById(Long id) {
        String findByIdQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM BRANDS WHERE ID = ?";
        return Optional.empty();
    }

    @Override
    public Optional<Brand> findByName(String name) {
        String findByNameQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM BRANDS WHERE NAME = ?";

        return Optional.empty();

    }

    @Override
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM BRANDS WHERE ID = ?";
        findById(id);

    }

    @Override
    public Optional<Brand> create(String brandName) {
        String insertQuery = "INSERT INTO BRANDS(NAME, CREATED_AT) VALUES(?, ?) RETURNING ID";
        return Optional.empty();

    }

    @Override
    public Optional<Brand> update(Long id, String brandName) {
        String updateQuery = "UPDATE BRANDS SET NAME = ?, UPDATED_AT = ? WHERE ID = ?";
        return Optional.empty();

    }


}
