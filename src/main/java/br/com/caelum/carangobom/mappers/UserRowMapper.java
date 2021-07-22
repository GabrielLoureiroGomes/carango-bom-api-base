package br.com.caelum.carangobom.mappers;

import br.com.caelum.carangobom.domain.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class UserRowMapper implements RowMapper<User> {

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_CREATED_AT = "CREATED_AT";
    private static final String COLUMN_UPDATED_AT = "UPDATED_AT";

    @Override
    public User mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final User user = new User();

        user.setId(resultSet.getLong(COLUMN_ID));
        user.setName(resultSet.getString(COLUMN_NAME));
        user.setCreatedAt(resultSet.getDate(COLUMN_CREATED_AT).toLocalDate());
        user.setUpdatedAt(resultSet.getDate(COLUMN_UPDATED_AT) != null ? resultSet.getDate(COLUMN_UPDATED_AT).toLocalDate() : null);

        return user;
    }
}
