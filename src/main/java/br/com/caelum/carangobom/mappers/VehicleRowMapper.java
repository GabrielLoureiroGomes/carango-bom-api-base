package br.com.caelum.carangobom.mappers;

import br.com.caelum.carangobom.domain.Vehicle;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class VehicleRowMapper implements RowMapper<Vehicle> {

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_BRAND_ID = "BRAND_ID";
    private static final String COLUMN_BRAND_NAME = "NAME";
    private static final String COLUMN_MODEL = "MODEL";
    private static final String COLUMN_YEAR = "YEAR";
    private static final String COLUMN_PRICE = "PRICE";
    private static final String COLUMN_CREATED_AT = "CREATED_AT";
    private static final String COLUMN_UPDATED_AT = "UPDATED_AT";

    @Override
    public Vehicle mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final Vehicle vehicle = new Vehicle();

        vehicle.setId(resultSet.getLong(COLUMN_ID));
        vehicle.setBrandId(resultSet.getLong(COLUMN_BRAND_ID));
        vehicle.setBrandName(resultSet.getString(COLUMN_BRAND_NAME));
        vehicle.setModel(resultSet.getString(COLUMN_MODEL));
        vehicle.setYear(resultSet.getString(COLUMN_YEAR));
        vehicle.setPrice(resultSet.getInt(COLUMN_PRICE));
        vehicle.setCreatedAt(resultSet.getDate(COLUMN_CREATED_AT).toLocalDate());
        vehicle.setUpdatedAt(resultSet.getDate(COLUMN_UPDATED_AT) != null ? resultSet.getDate(COLUMN_UPDATED_AT).toLocalDate() : null);

        return vehicle;
    }
}
