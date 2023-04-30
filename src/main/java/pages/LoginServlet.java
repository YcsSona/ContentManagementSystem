package pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.IUserDao;
import dao.UserDaoImpl;
import entity.User;
import utility.DBUtils;

@WebServlet(value = "/authenticate", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserDao userDao;

	public void init() throws ServletException {
		try {
			userDao = new UserDaoImpl();
		} catch (Exception e) {
			// re-throw the exception back to WC, so that it doesn't continue the life
			// cycle.
			// to inform WC that init() has failed
			throw new ServletException("Error in init() " + getClass().getName(), e);
		}
	}

	public void destroy() {
		try {
			userDao.cleanUp();
			DBUtils.closeConnection();
		} catch (Exception e) {
//			e.printStackTrace();
			// how to inform WC that destroy() has failed?
			throw new RuntimeException("Error in destory() " + getClass().getName(), e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		try (PrintWriter writer = response.getWriter()) {
			String email = request.getParameter("email");
			String password = request.getParameter("pass");

			User user = userDao.validateUser(email, password);

			if (user == null) {
				writer.println("<h2> Invalid login. Please retry <a href='login.html'>Retry</a></h2>");
			} else {
//				writer.println("<h2> Login successful, user details : " + user + "</h2>");

				// create session object from WC
				HttpSession session = request.getSession();
				System.out.println("From login page session is new : " + session.isNew());
				System.out.println(session.getId());

				// save user details, under session scope
				session.setAttribute("user_info", user);

				// WC: sends temporary redirect response
				// RESPONSE : SC 302 | header : location = topics, setCookie = user_info | body
				// : EMPTY
				// Web browser : Sends a NEW request
				response.sendRedirect("topics");
			}

		} catch (Exception e) {
			throw new ServletException("Error in doPost()" + getClass(), e);
		}

	}

}
