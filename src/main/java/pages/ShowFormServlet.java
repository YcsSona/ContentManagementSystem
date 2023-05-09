package pages;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/showform")
public class ShowFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		try (PrintWriter writer = response.getWriter()) {
			HttpSession session = request.getSession();

			ITopicDao topicDao = (TopicDaoImpl) session.getAttribute("topic_dao");

			// Get all topics
			List<Topic> topics = topicDao.getAllTopics();

			writer.println("<form method='get' action='processform'>");
			writer.println("Topic <select name='topic_id'>");
			topics.forEach(t -> {
				writer.println("<option value='" + t.getTopicId() + "'>" + t.getTopicName() + "</option><br>");
			});
			writer.println("</select>");

			writer.println("<br>Name <input type='text' name='name' /><br>");
			writer.println("Author <input type='text' name='author' /><br>");
			writer.println("Publish date <input type='date' name='date' /><br>");
			writer.println("Contents <textarea name='contents'></textarea><br>");
			writer.println("<input type='submit' value='Add Tutorial' /><br>");
			writer.println("</form>");
		} catch (Exception e) {
			throw new ServletException("Error in doGet() of" + getClass(), e);
		}
	}

}
