package dao;

import java.sql.SQLException;
import java.util.List;

import entity.Tutorial;

public interface ITutorialDao {
	// get all tutorials under specified topic in a sorted as per visits manner
	List<String> getTutorialsByTopicId(int topicId) throws SQLException;

	// get tutorial details
	Tutorial getTutorialDetails(String tutName) throws SQLException;

	// update visits
	String updateVisits(int tutorialId) throws SQLException;

	// add tutorial
	String addTutorial(Tutorial tutorial) throws SQLException;

	void cleanUp() throws SQLException;
}
