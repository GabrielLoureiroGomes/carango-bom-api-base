package br.com.caelum.carangobom.mappers;

import br.com.caelum.carangobom.domain.Vehicle;
import lombok.extern.log4j.Log4j2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class VehicleMappers {

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_BRAND_ID = "BRAND_ID";
    private static final String COLUMN_MODEL = "MODEL";
    private static final String COLUMN_YEAR = "YEAR";
    private static final String COLUMN_PRICE = "PRICE";
    private static final String COLUMN_CREATED_AT = "CREATED_AT";
    private static final String COLUMN_UPDATED_AT = "UPDATED_AT";

    private VehicleMappers() {
    }

    public static Optional<Vehicle> mapToVehicle(ResultSet rs) {

        try {
            Vehicle vehicle = new Vehicle();
            rs.next();
            vehicle.setId(rs.getLong(COLUMN_ID));
            vehicle.setBrandId(rs.getLong(COLUMN_BRAND_ID));
            vehicle.setModel(rs.getString(COLUMN_MODEL));
            vehicle.setYear(rs.getString(COLUMN_YEAR));
            vehicle.setPrice(rs.getInt(COLUMN_PRICE));
            vehicle.setCreatedAt(rs.getDate(COLUMN_CREATED_AT).toLocalDate());
            vehicle.setUpdatedAt(rs.getDate(COLUMN_UPDATED_AT) != null ? rs.getDate(COLUMN_UPDATED_AT).toLocalDate() : null);

            return Optional.of(vehicle);

        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        return Optional.empty();
    }

    public static List<Vehicle> mapToListVehicles(ResultSet rs) {
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getLong(COLUMN_ID));
                vehicle.setBrandId(rs.getLong(COLUMN_BRAND_ID));
                vehicle.setModel(rs.getString(COLUMN_MODEL));
                vehicle.setYear(rs.getString(COLUMN_YEAR));
                vehicle.setPrice(rs.getInt(COLUMN_PRICE));
                vehicle.setCreatedAt(rs.getDate(COLUMN_CREATED_AT).toLocalDate());
                vehicle.setUpdatedAt(rs.getDate(COLUMN_UPDATED_AT) != null ? rs.getDate(COLUMN_UPDATED_AT).toLocalDate() : null);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        return vehicles;
    }

}
