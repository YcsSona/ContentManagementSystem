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
import entity.Tutorial;

@WebServlet("/tutorial_details")
public class TutorialDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		try (PrintWriter writer = response.getWriter()) {
			String tutName = request.getParameter("tut_name");

			HttpSession session = request.getSession();
			System.out.println("From tutorials page session new : " + session.isNew());
			System.out.println(session.getId());

			ITutorialDao tutorialDao = (TutorialsDaoImpl) session.getAttribute("tutorial_dao");

			Tutorial tutorial = tutorialDao.getTutorialDetails(tutName);

			System.out.println(tutorialDao.updateVisits(tutorial.getTopicId()));

			writer.println("<h2>" + tutName + " tutorial </h2>");
			writer.println("<h2> Author : " + tutorial.getAuthor() + "</h2>");
			writer.println("<h2> Published on : " + tutorial.getPublishDate() + "</h2>");
			writer.println("<h2> Visits : " + tutorial.getVisits() + "</h2>");
			writer.println("<h2> Contents : " + tutorial.getContents() + "</h2>");

			// send back link to client
			writer.println("<h2> <a href='tutorials?topic_id=" + tutorial.getTopicId() + "'>Back</a> </h2>");

			// send logout link to client
			writer.println("<h2> <a href='logout'>Log me out.</a> </h2>");

		} catch (Exception e) {
			throw new ServletException("Error in doGet() of " + getClass(), e);
		}
	}

}
