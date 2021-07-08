package br.com.caelum.carangobom.mappers;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.utils.Utils;
import lombok.extern.log4j.Log4j2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class BrandMappers {

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_CREATED_AT = "CREATED_AT";
    private static final String COLUMN_UPDATED_AT = "UPDATED_AT";

    private BrandMappers() {
    }

    public static Brand mapToBrand(ResultSet rs) {
        Brand brand = new Brand();
        try {
            while (rs.next()) {
                brand.setId(rs.getLong(COLUMN_ID));
                brand.setName(rs.getString(COLUMN_NAME));
                brand.setCreatedAt(Utils.toLocalDate(rs.getDate(COLUMN_CREATED_AT).toString()));
                brand.setUpdatedAt(rs.getDate(COLUMN_UPDATED_AT) != null ? Utils.toLocalDate(rs.getDate(COLUMN_UPDATED_AT).toString()) : null);
            }
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        return brand;
    }

    public static List<Brand> mapToListBrands(ResultSet rs) {
        List<Brand> brands = new ArrayList<>();
        try {
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getLong(COLUMN_ID));
                brand.setName(rs.getString(COLUMN_NAME));
                brand.setCreatedAt(Utils.toLocalDate(rs.getDate(COLUMN_CREATED_AT).toString()));
                brand.setUpdatedAt(rs.getDate(COLUMN_UPDATED_AT) != null ? Utils.toLocalDate(rs.getDate(COLUMN_UPDATED_AT).toString()) : null);
                brands.add(brand);
            }
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        return brands;
    }

}
