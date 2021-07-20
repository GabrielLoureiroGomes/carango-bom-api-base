package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.config.database.PostgreConfiguration;
import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.caelum.carangobom.mappers.BrandMappers.mapToBrand;
import static br.com.caelum.carangobom.mappers.BrandMappers.mapToListBrands;

@Log4j2
@Repository
public class BrandRepositoryImpl implements BrandRepository {

    @Override
    public List<Brand> findAll() {
        String findAllQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM BRANDS";

        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(findAllQuery)) {
            ResultSet rs = ps.executeQuery();
            return mapToListBrands(rs);

        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        return List.of();
    }

    @Override
    public Optional<Brand> findById(Long id) {
        String findByIdQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM BRANDS WHERE ID = ?";

        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(findByIdQuery)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            return mapToBrand(rs);
        } catch (SQLException e) {
            log.debug(e.getMessage());
            throw new BusinessException();
        }
    }

    @Override
    public Optional<Brand> findByName(String name) {
        String findByNameQuery = "SELECT ID, NAME, CREATED_AT, UPDATED_AT FROM BRANDS WHERE NAME = ?";

        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(findByNameQuery)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            return mapToBrand(rs);
        } catch (SQLException e) {
            log.debug(e.getMessage());
            throw new BusinessException();
        }
    }

    @Override
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM BRANDS WHERE ID = ?";
        findById(id);
        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(deleteQuery)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    public Optional<Brand> create(String brandName) {
        String insertQuery = "INSERT INTO BRANDS(NAME, CREATED_AT) VALUES(?, ?) RETURNING ID";
        long id = 0;
        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, brandName);
            ps.setDate(2, Date.valueOf(LocalDate.now()));

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                id = checkIfInsertIsSuccessed(ps);
            }
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        return findById(id);
    }

    @Override
    public Optional<Brand> update(Long id, String brandName) {
        String updateQuery = "UPDATE BRANDS SET NAME = ?, UPDATED_AT = ? WHERE ID = ?";
        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(updateQuery)) {
            ps.setString(1, brandName);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setLong(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        return findById(id);
    }

    private Long checkIfInsertIsSuccessed(PreparedStatement ps) {
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
