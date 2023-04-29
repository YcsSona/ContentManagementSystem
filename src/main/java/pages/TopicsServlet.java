package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ITopicDao;
import dao.TopicDaoImpl;
import entity.Topic;

@WebServlet("/topics")
public class TopicsServlet extends HttpServlet {

	ITopicDao topicDao;

	@Override
	public void init() throws ServletException {
		try {
			topicDao = new TopicDaoImpl();
		} catch (SQLException e) {
			throw new ServletException("Error in init() of " + getClass(), e);
		}
	}

	@Override
	public void destroy() {
		try {
			topicDao.cleanUp();
		} catch (SQLException e) {
			throw new RuntimeException("Error in destroy() of " + getClass(), e);
		}
	}

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		try (PrintWriter writer = response.getWriter()) {
			writer.println("<h2> Login successful from topics page.</h2>");

			// get cookies from the request
			Cookie[] cookies = request.getCookies();

			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals("user_info")) {
						writer.println("<h2> Validated user details retrieved from a cookie " + c.getValue() + "</h2>");
						break;
					}
				}

				List<Topic> topics = topicDao.getAllTopics();
				topics.forEach(t -> {
					writer.println("<h2>" + t.getTopicName() + "</h2>");
				});

			} else {
				writer.println("<h2> Session tracking failed : no cookies found!!!</h2>");
			}

		} catch (Exception e) {
			throw new ServletException("Error in doGet() of " + getClass(), e);
		}
	}

}