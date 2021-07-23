package br.com.caelum.carangobom.mappers;

import br.com.caelum.carangobom.domain.Dashboard;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class DashboardRowMapper implements RowMapper<Dashboard> {

    private static final String COLUMN_BRAND_NAME = "BRAND";
    private static final String COLUMN_TOTAL_PRICE = "TOTAL_PRICE";
    private static final String COLUMN_MODELS_AVAILABLE = "MODELS_AVAILABLE";

    @Override
    public Dashboard mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final Dashboard dashboard = new Dashboard();

        dashboard.setBrandName(resultSet.getString(COLUMN_BRAND_NAME));
        dashboard.setTotalPrice(resultSet.getInt(COLUMN_TOTAL_PRICE));
        dashboard.setModelsAvailable(resultSet.getInt(COLUMN_MODELS_AVAILABLE));
        return dashboard;
    }
}
