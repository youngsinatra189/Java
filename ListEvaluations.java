package ca.sheridancollege.java3.servlets;

import ca.sheridancollege.java3.bus.*;
/**
 * Name:  Roshan Sahu
 * Assignment:  Assignment #3
 * Program: Computer Programmer
 * 
 * Description: The purpose of this Servlet is to list all of the evaluations from the tracker database.
 */
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.sheridancollege.java3.db.DaoEvaluations;

@WebServlet("/ListEvaluations")
public class ListEvaluations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ListEvaluations() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession();

		try {
			ServletContext ctx = request.getServletContext();
			DaoEvaluations daoEvaluation = (DaoEvaluations) ctx.getAttribute("daoEvaluation");
			ArrayList<Evaluation> listEvaluations = daoEvaluation.showEvaluations();

			// Storing the list of evaluations as a session variable for listEvals.jsp
			session.setAttribute("listEvaluations", listEvaluations);
			String url = "/listEvals.jsp";
			getServletContext().getRequestDispatcher(url).forward(request, response);

		} catch (Exception e) {
			errors.add(e.toString());
			session.setAttribute("errorMessage", errors.toString());
			String url = "/index.jsp";
			getServletContext().getRequestDispatcher(url).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
