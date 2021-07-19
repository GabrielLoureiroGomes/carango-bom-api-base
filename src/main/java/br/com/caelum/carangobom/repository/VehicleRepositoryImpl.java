package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.config.database.PostgreConfiguration;
import br.com.caelum.carangobom.domain.Vehicle;
import br.com.caelum.carangobom.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.caelum.carangobom.mappers.VehicleMappers.mapToVehicle;
import static br.com.caelum.carangobom.mappers.VehicleMappers.mapToListVehicles;

@Log4j2
@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

    @Override
    public List<Vehicle> findAll() {
        String findAllQuery = "SELECT ID, BRAND_ID, MODEL, YEAR, PRICE, CREATED_AT, UPDATED_AT FROM VEHICLES";

        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(findAllQuery)) {
            ResultSet rs = ps.executeQuery();
            return mapToListVehicles(rs);

        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        return List.of();
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        String findByIdQuery = "SELECT ID, BRAND_ID, MODEL, YEAR, PRICE, CREATED_AT, UPDATED_AT FROM VEHICLES WHERE ID = ?";

        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(findByIdQuery)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            return mapToVehicle(rs);
        } catch (SQLException e) {
            log.debug(e.getMessage());
            throw new BusinessException();
        }
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        String insertQuery = "INSERT INTO VEHICLES(BRAND_ID, MODEL, YEAR, PRICE, CREATED_AT) VALUES(?, ?, ?, ?, ?) RETURNING ID";
        long id = 0;
        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            LocalDate now = LocalDate.now();
            ps.setLong(1, vehicle.getBrandId());
            ps.setString(2, vehicle.getModel());
            ps.setString(3, vehicle.getYear());
            ps.setInt(4, vehicle.getPrice());
            ps.setDate(5, Date.valueOf(now));
            vehicle.setCreatedAt(now);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                id = checkIfInsertIsSucceed(ps);
            }
            vehicle.setId(id);
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }

        return vehicle;
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        String updateQuery = "UPDATE VEHICLES SET BRAND_ID = ?, MODEL = ?, YEAR = ?, PRICE = ?, UPDATED_AT = ? WHERE ID = ?";
        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(updateQuery)) {
            LocalDate now = LocalDate.now();
            ps.setLong(1, vehicle.getBrandId());
            ps.setString(2, vehicle.getModel());
            ps.setInt(3, vehicle.getPrice());
            ps.setDate(4, Date.valueOf(now));
            ps.setLong(5, vehicle.getId());
            ps.executeUpdate();

            vehicle.setUpdatedAt(now);
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        return vehicle;
    }

    @Override
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM VEHICLES WHERE ID = ?";
        findById(id);
        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(deleteQuery)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
    }

    private Long checkIfInsertIsSucceed(PreparedStatement ps) {
        long count = 0;
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                count = rs.getLong(1);
            }
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        return count;
    }
}
