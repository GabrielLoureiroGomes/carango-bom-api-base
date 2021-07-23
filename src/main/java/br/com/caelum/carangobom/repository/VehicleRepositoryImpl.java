package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.domain.Dashboard;
import br.com.caelum.carangobom.domain.Vehicle;
import br.com.caelum.carangobom.mappers.DashboardRowMapper;
import br.com.caelum.carangobom.mappers.VehicleRowMapper;
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
public class VehicleRepositoryImpl implements VehicleRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Vehicle> findAll() {
        String findAllQuery = "SELECT ID, BRAND_ID, MODEL, YEAR, PRICE, CREATED_AT, UPDATED_AT FROM VEHICLES";

        return jdbcTemplate.query(findAllQuery, new VehicleRowMapper());
    }

    @Override
    public List<Dashboard> fetchDashboard() {
        String fetchDashboardQuery = "select \n" +
                "b.\"name\" as BRAND, \n" +
                "SUM(v.price) as TOTAL_PRICE, \n" +
                "count(v.model) as MODELS_AVAILABLE \n" +
                "from brands b \n" +
                "inner join vehicles v on v.brand_id = b.id\n" +
                "group by v.model, b.\"name\" ";

        return jdbcTemplate.query(fetchDashboardQuery, new DashboardRowMapper());
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        try {
            String findByIdQuery = "SELECT ID, BRAND_ID, MODEL, YEAR, PRICE, CREATED_AT, UPDATED_AT FROM VEHICLES WHERE ID = ?";

            return Optional.ofNullable(jdbcTemplate.queryForObject(findByIdQuery, new VehicleRowMapper(), id));
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Vehicle> create(Vehicle vehicle) {
        String insertQuery = "INSERT INTO VEHICLES(BRAND_ID, MODEL, YEAR, PRICE, CREATED_AT) VALUES(?, ?, ?, ?, ?) RETURNING ID";

        try {
            KeyHolder key = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, vehicle.getBrandId());
                ps.setString(2, vehicle.getModel());
                ps.setString(3, vehicle.getYear());
                ps.setInt(4, vehicle.getPrice());
                ps.setDate(5, Date.valueOf(LocalDate.now()));

                return ps;
            }, key);

            return findById(Objects.requireNonNull(key.getKey()).longValue());

        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Vehicle> update(Long id, Vehicle vehicle) {
        try {
            String updateQuery = "UPDATE VEHICLES SET BRAND_ID = ?, MODEL = ?, YEAR = ?, PRICE = ?, UPDATED_AT = ? WHERE ID = ?";

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(updateQuery);
                ps.setLong(1, vehicle.getBrandId());
                ps.setString(2, vehicle.getModel());
                ps.setString(3, vehicle.getYear());
                ps.setInt(4, vehicle.getPrice());
                ps.setDate(5, Date.valueOf(LocalDate.now()));
                ps.setLong(6, id);

                return ps;
            });

            return findById(id);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return Optional.empty();

    }

    @Override
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM VEHICLES WHERE ID = ?";

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
