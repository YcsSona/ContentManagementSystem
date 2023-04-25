package dao;

import java.sql.SQLException;

import entity.User;

public interface IUserDao {
	User validateUser(String email, String pwd) throws SQLException;

	void cleanUp() throws SQLException;
}
