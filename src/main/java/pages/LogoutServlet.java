package pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		try (PrintWriter writer = response.getWriter()) {
			// Get the session object from WC (provided cookies are enabled)
			HttpSession session = request.getSession();
			System.out.println("From login page session is new : " + session.isNew());
			System.out.println(session.getId());

			User user = (User) session.getAttribute("user_info");

			if (user != null) {
				writer.println("<h2> Hello " + user.getName() + ", you have logged out successfully.</h2>");

			} else {
				writer.println("<h2> Session tracking failed : no cookies found!!!</h2>");
			}
			// If cookies are disabled then WC will create session object for every request,
			// which has to be discarded as it's a blank object
			session.invalidate();

			writer.println("<h2> <a href='login.html'>Visit again</a> </h2>");
		}
	}

}
