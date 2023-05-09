package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ITutorialDao;
import dao.TutorialsDaoImpl;
import entity.Tutorial;

@WebServlet("/processform")
public class ProcessFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		http://localhost:8080/CMS/processform?topic_id=3&name=fads&author=afa&date=2017-01-23&contents=Testing
		response.setContentType("text/html");

		try (PrintWriter writer = response.getWriter()) {
			int topic_id = Integer.parseInt(request.getParameter("topic_id"));
			String name = request.getParameter("name");
			String author = request.getParameter("author");
			String contents = request.getParameter("contents");
			Date date = Date.valueOf(request.getParameter("date"));

			LocalDate publishDate = date.toLocalDate();

			LocalDate minus6Months = LocalDate.now().minusMonths(6);

			if (contents.length() < 255 && !publishDate.isBefore(minus6Months)) {

				HttpSession session = request.getSession();

				ITutorialDao tutorialDao = (TutorialsDaoImpl) session.getAttribute("tutorial_dao");
				tutorialDao.addTutorial(new Tutorial(name, author, date, 1, contents, topic_id));

				writer.println("<h2>Tutorial added successfully under topic id: " + topic_id + "</h2>");

				// send logout link to client
				writer.println("<h2> <a href='logout'>Log me out.</a> </h2>");

			} else {
				writer.println("<h2 style='color:red'>Validation error.</h2>");
				// send back link to client
				writer.println("<h2> <a href='showform'>Back</a> </h2>");

			}

		} catch (Exception e) {
			throw new ServletException("Error in doGet() of" + getClass(), e);
		}
	}

}
