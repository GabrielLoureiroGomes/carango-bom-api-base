package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.config.database.PostgreConfiguration;
import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.utils.DateFormatter;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BrandRepositoryImpl implements BrandRepository {

    DateFormatter date = new DateFormatter();

    @Override
    public List<Brand> findAll() {
        List<Brand> brands = new ArrayList<>();

        try (PreparedStatement ps = PostgreConfiguration.getDatabaseConnection().prepareStatement("SELECT NAME, CREATED_AT, UPDATED_AT FROM BRANDS")) {
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
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Brand create(Brand brand) {
        return null;
    }

    @Override
    public Brand update(Brand brand) {
        return null;
    }
}