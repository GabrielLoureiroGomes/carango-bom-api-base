package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.config.database.PostgreConfiguration;
import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.utils.Utils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BrandRepositoryImpl implements BrandRepository {

    Utils date = new Utils();

    @Override
    public List<Brand> findAll() {
        String findAllQuery = "SELECT NAME, CREATED_AT, UPDATED_AT FROM BRANDS";
        List<Brand> brands = new ArrayList<>();

        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(findAllQuery)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                var brand = new Brand();
                brand.setName(rs.getString("NAME"));
                brand.setCreatedAt(date.toLocalDate(rs.getDate("CREATED_AT").toString()));
                brand.setCreatedAt(date.toLocalDate(rs.getDate("UPDATED_AT").toString()));
                brands.add(brand);
            }

        } catch (SQLException e) {
            e.getCause();
        }
        return brands;
    }

    @Override
    public Brand findById(Long id) {
        String findByIdQuery = "SELECT NAME, CREATED_AT, UPDATED_AT FROM BRANDS WHERE ID = ?";
        var brand = new Brand();

        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement(findByIdQuery)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                brand.setName(rs.getString("NAME"));
                brand.setCreatedAt(date.toLocalDate(rs.getDate("CREATED_AT").toString()));
                brand.setCreatedAt(date.toLocalDate(rs.getDate("UPDATED_AT").toString()));
            }

        } catch (SQLException e) {
            e.getCause();
        }
        return brand;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Brand create(Brand brand) {
        return null;
    }

    @Override
    public Brand update(String name) {
        return null;
    }
}