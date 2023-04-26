package pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/topics")
public class TopicsServlet extends HttpServlet {
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
						writer.println(
								"<h2> Validated user details retrieved from a cookie " + c.getValue() + "</h2>");
						break;
					}
				}
			} else {
				writer.println("<h2> Session tracking failed : no cookies found!!!</h2>");
			}

		}
	}

}
