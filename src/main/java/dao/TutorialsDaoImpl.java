package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Tutorial;
import utility.DBUtils;

public class TutorialsDaoImpl implements ITutorialDao {

	private Connection connection;
	private PreparedStatement pst1, pst2, pst3, pst4;

	public TutorialsDaoImpl() throws SQLException {
		connection = DBUtils.openConnection();
		pst1 = connection.prepareStatement("select name from tutorials where topic_id=? order by visits desc");
		pst2 = connection.prepareStatement("select * from tutorials where name=?");
		pst3 = connection.prepareStatement("update tutorials set visits=visits+1 where id=?");
		pst4 = connection.prepareStatement(
				"insert into tutorials(name, author, publish_date, visits, contents, topic_id) values(?,?,?,?,?,?)");
		System.out.println("tutorials dao created");
	}

	@Override
	public List<String> getTutorialsByTopicId(int topicId) throws SQLException {

		List<String> topicNames = new ArrayList<>();

		// set IN parameters
		pst1.setInt(1, topicId);

		try (ResultSet resultSet = pst1.executeQuery()) {
			while (resultSet.next()) {
				topicNames.add(resultSet.getString(1));
			}
		}
		return topicNames;
	}

	@Override
	public Tutorial getTutorialDetails(String tutName) throws SQLException {
		// set IN parameters
		pst2.setString(1, tutName);

		try (ResultSet rst = pst2.executeQuery()) {
			if (rst.next()) {
				return new Tutorial(rst.getInt(1), tutName, rst.getString(3), rst.getDate(4), rst.getInt(5),
						rst.getString(6), rst.getInt(7));
			}
		}
		return null;
	}

	@Override
	public String updateVisits(int tutorialId) throws SQLException {
		// set IN parameters
		pst3.setInt(1, tutorialId);

		int result = pst3.executeUpdate();

		return result == 1 ? "Updated visit count is : " + result : "Could not update the visit count";
	}

	@Override
	public String addTutorial(Tutorial tutorial) throws SQLException {
		// set IN parameters
		pst4.setString(1, tutorial.getTutorialName());
		pst4.setString(2, tutorial.getAuthor());
		pst4.setDate(3, tutorial.getPublishDate());
		pst4.setInt(4, tutorial.getVisits());
		pst4.setString(5, tutorial.getContents());
		pst4.setInt(6, tutorial.getTopicId());

		int result = pst4.executeUpdate();

		if (result == 1) {
			return tutorial.getTutorialName() + " inserted successfully";
		}
		return "Could not insert the record";

	}

	@Override
	public void cleanUp() throws SQLException {
		if (pst1 != null)
			pst1.close();
		if (pst2 != null)
			pst2.close();
		if (pst3 != null)
			pst3.close();
		System.out.println("tutorials dao cleaned up!");

	}

}
