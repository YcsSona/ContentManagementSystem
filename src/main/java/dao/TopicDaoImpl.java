package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Topic;
import utility.DBUtils;

public class TopicDaoImpl implements ITopicDao {

	private Connection connection;
	private PreparedStatement pst1;

	public TopicDaoImpl() throws SQLException {
		connection = DBUtils.openConnection();
		pst1 = connection.prepareStatement("select * from topics");
		System.out.println("topics dao created");
	}

	@Override
	public List<Topic> getAllTopics() throws SQLException {
		List<Topic> topics = new ArrayList<Topic>();

		try (ResultSet rs = pst1.executeQuery()) {
			while (rs.next()) {
				topics.add(new Topic(rs.getInt(1), rs.getString(2)));
			}
		}

		return topics;
	}

	@Override
	public void cleanUp() throws SQLException {
		if (pst1 != null)
			pst1.close();
		System.out.println("topics dao cleaned up!");
	}

}
