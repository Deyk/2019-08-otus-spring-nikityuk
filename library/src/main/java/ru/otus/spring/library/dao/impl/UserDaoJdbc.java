package ru.otus.spring.library.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.library.dao.UserDao;
import ru.otus.spring.library.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDaoJdbc implements UserDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    public UserDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("login", login);
        params.put("password", password);
        return jdbcOperations.queryForObject("SELECT * FROM user WHERE login = :login AND password = :password", params, new UserMapper());
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            return new User(id, login, password);
        }
    }
}