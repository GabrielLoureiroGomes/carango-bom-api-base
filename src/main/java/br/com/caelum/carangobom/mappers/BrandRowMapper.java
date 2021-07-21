package br.com.caelum.carangobom.mappers;

import br.com.caelum.carangobom.domain.Brand;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class BrandRowMapper implements RowMapper<Brand> {

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_CREATED_AT = "CREATED_AT";
    private static final String COLUMN_UPDATED_AT = "UPDATED_AT";

    @Override
    public Brand mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final Brand brand = new Brand();

        brand.setId(resultSet.getLong(COLUMN_ID));
        brand.setName(resultSet.getString(COLUMN_NAME));
        brand.setCreatedAt(resultSet.getDate(COLUMN_CREATED_AT).toLocalDate());
        brand.setUpdatedAt(resultSet.getDate(COLUMN_UPDATED_AT) != null ? resultSet.getDate(COLUMN_UPDATED_AT).toLocalDate() : null);
        return brand;
    }
}
