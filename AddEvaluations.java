package ca.sheridancollege.java3.servlets;

/**
 * Name:  Roshan Sahu
 * Assignment:  Assignment #3
 * Program: Computer Programmer
 * 
 * Description: The purpose of this Servlet is to add a new evaluation to the tracker database
 */
import ca.sheridancollege.java3.bus.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.sheridancollege.java3.db.DaoEvaluations;

@WebServlet("/AddEvaluations")
public class AddEvaluations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddEvaluations() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<String> errors = new ArrayList<String>();

		try {

			ServletContext ctx = request.getServletContext();
			DaoEvaluations daoEvaluation = (DaoEvaluations) ctx.getAttribute("daoEvaluation");

			// Passing in the parameters from index.jsp
			String courseCode = request.getParameter("listCourses");
			String evaluationName = request.getParameter("evaluation");
			String dayStr = request.getParameter("selectDay");
			String submitStatus = request.getParameter("submitStatus");
			int monthInt = (int) Integer.parseInt(request.getParameter("selectMonth"));
			int dayInt = (int) Integer.parseInt(request.getParameter("selectDay"));

			// Year is the only field that the user could enter the wrong data type
			String yearStr = request.getParameter("year");

			// Validating the year
			int yearInt = 0;
			try {
				yearInt = (int) Integer.parseInt(yearStr);
			} catch (Exception e) {
				errors.add("The year must be a valid number");
			}

			if (evaluationName.equals(null) || evaluationName.equals("")) {
				errors.add("Evaluation name can't be empty");
			}

			// Logic Path 1 -> If there were no errors, continue using the dao to add the
			// evaluation to the database
			if (errors.isEmpty() || errors.equals(null)) {
				LocalDate date = LocalDate.of(yearInt, monthInt, dayInt);
				boolean submitted;
				if (submitStatus.equals("true")) {
					submitted = true;
				} else {
					submitted = false;
				}

				// Creating a course object via the dao
				Course course = daoEvaluation.getCourse(courseCode);
				// Create an evaluation object with the newly created course object
				boolean addStatus = daoEvaluation.getEvaluation(course, evaluationName, date, submitted);

				// Forward to the List Evaluations Servlet to start the listing process
				RequestDispatcher rd = request.getRequestDispatcher("ListEvaluations");
				rd.forward(request, response);

				// Logic Path 2-> If there were errors, send them to the original index.jsp page
			} else {
				request.setAttribute("errorMessage", errors.toString());
				String url = "/index.jsp";
				getServletContext().getRequestDispatcher(url).forward(request, response);
			}

			// If there were any others errors other than user input, send them to index.jsp
			// to be displayed
		} catch (Exception e) {
			errors.add(e.toString());
			request.setAttribute("errorMessage", errors.toString());
			String url = "/index.jsp";
			getServletContext().getRequestDispatcher(url).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
