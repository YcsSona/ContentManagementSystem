package dao;

import java.sql.SQLException;
import java.util.List;

import entity.Topic;

public interface ITopicDao {
	List<Topic> getAllTopics() throws SQLException;
	
	void cleanUp() throws SQLException;
}
