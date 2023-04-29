package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ITopicDao;
import dao.TopicDaoImpl;
import entity.Topic;
import entity.User;

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

			// Get the session object from WC (provided cookies are enabled)
			HttpSession session = request.getSession();
			System.out.println("From topics page session new : " + session.isNew());
			System.out.println(session.getId());

			// retrieve the user details from session scope
			User user = (User) session.getAttribute("user_info");

			if (user != null) {
				writer.println("<h2> Retrieved user details from HttpSession " + user + "</h2>");

				List<Topic> topics = topicDao.getAllTopics();
				topics.forEach(t -> {
					writer.println("<h2>" + t.getTopicName() + "</h2>");
				});

			} else {
				writer.println("<h2> Session tracking failed : no cookies found!!!</h2>");
			}

			// send logout link to client
			writer.println("<h2> <a href='logout'>Log me out.</a> </h2>");

		} catch (Exception e) {
			throw new ServletException("Error in doGet() of " + getClass(), e);
		}
	}

}