package pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ITutorialDao;
import dao.TutorialsDaoImpl;

@WebServlet("/tutorials")
public class TutorialsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		try (PrintWriter writer = response.getWriter()) {

			if (request.getParameter("topic_id") == null) {
				writer.println("<h2 style='color:red'>You must choose a topic first</h2>");
				writer.println("<h2><a href='topics'>Go back</a></h2>");
			} else {
				int topicId = Integer.parseInt(request.getParameter("topic_id"));
				writer.println("<h2> Tutorials published under topic ID: " + topicId + "</h2>");

				HttpSession session = request.getSession();
				System.out.println("From tutorials page session new : " + session.isNew());
				System.out.println(session.getId());

				ITutorialDao tutorialDao = (TutorialsDaoImpl) session.getAttribute("tutorial_dao");

				tutorialDao.getTutorialsByTopicId(topicId).forEach(tut -> {
					writer.println("<h3> <a href='tutorial_details?tut_name=" + tut + "'>" + tut + "</a></h3>");
				});
			}

		} catch (Exception e) {
			throw new ServletException("Error in doGet() of " + getClass(), e);
		}
	}

}
