package ca.sheridancollege.java3.db;

/**
 * Name:  Roshan Sahu
 * Assignment:  Assignment #3
 * Program: Computer Programmer
 * 
 * Description: The purpose of this dao class is to encapsulate database information
 */
import ca.sheridancollege.java3.bus.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DaoEvaluations {

	private String DB_URL;
	private String dbName;
	private String username;
	private String password;

	public DaoEvaluations() {
	}

	/**
	 * Creates a new instance of DaoEvaluations
	 * 
	 * @param DB_URL   URL of the the database
	 * @param dbName   Name of database
	 * @param username MySQL WorkBench username
	 * @param password Corresponding password for username
	 * @throws Exception
	 */
	public DaoEvaluations(String DB_URL, String dbName, String username, String password) throws Exception {
		this.DB_URL = DB_URL;
		this.dbName = dbName;
		this.username = username;
		this.password = password;
	}

	/**
	 * Creates a connection via the DriverManager
	 * 
	 * @return dbConn The connection object
	 * @throws Exception
	 */
	private Connection getConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		Connection dbConn = DriverManager.getConnection(this.DB_URL + this.dbName, this.username, this.password);
		return dbConn;
	}

	/**
	 * Creates an instance of course object
	 * 
	 * @param courseID The ID of the course to be created
	 * @return Course The newly created course object
	 * @throws Exception
	 */
	public Course getCourse(String courseID) throws Exception {
		String code = courseID;
		String title = null;
		Connection dbConn = getConnection();
		String sql = "SELECT title FROM courses WHERE code = ?;";
		PreparedStatement stmt = dbConn.prepareStatement(sql);
		stmt.setString(1, code);
		ResultSet records = stmt.executeQuery();
		while (records.next()) {
			title = records.getString("title");
		}
		Course course = new Course(code, title);
		dbConn.close();
		return course;

	}

	/**
	 * Adds an evaluation into the evaluations table
	 * 
	 * @param course    The course that the student's evaluation is for
	 * @param name      The name of the evaluation
	 * @param date      The due date of submission
	 * @param submitted Whether or not the evaluation ahs been submitted
	 * @return boolean Status whether or not the evaluation has been successfully
	 *         added
	 * @throws Exception
	 */
	public boolean getEvaluation(Course course, String name, LocalDate date, boolean submitted) throws Exception {
		// Converting the boolean into an int that the sql statement can use
		// Obtain the string value of true of false
		String submitString = String.valueOf(submitted);
		int x = 0;
		// If its false, upload a 0 into the table. Or else, upload a 1 for true
		if (submitString.equals("true")) {
			x = 1;
		}

		Connection dbConn = getConnection();
		Evaluation evaluation = new Evaluation(course, name, date, submitted);
		String sql = "INSERT INTO evaluations (course_code, eval_name, due_date, submitted) " + "VALUES (?, ?, ?, ?)";
		PreparedStatement stmt = dbConn.prepareStatement(sql);

		stmt.setString(1, course.getCode());
		stmt.setString(2, evaluation.getEvalName());
		stmt.setString(3, date.toString());
		stmt.setInt(4, x);
		int success = stmt.executeUpdate();
		dbConn.close();

		// If the update was successful, return true. If unsuccessful, return false.
		if (success > 0)
			return true;
		else
			return false;
	}

	/**
	 * Prepares all of the evaluations to be displayed to the user
	 * 
	 * @return ArraListy<Evaluation> An ArrayList of evaluation objects
	 * @throws Exception
	 */
	public ArrayList<Evaluation> showEvaluations() throws Exception {

		ArrayList<Evaluation> evalArray = new ArrayList<Evaluation>();

		Connection dbConn = getConnection();
		String sql = "SELECT course_code, eval_name, due_date, submitted, title FROM tracker.courses, tracker.evaluations WHERE tracker.courses.code = tracker.evaluations.course_code ORDER BY due_date;";
		PreparedStatement stmt = dbConn.prepareStatement(sql);
		ResultSet records = stmt.executeQuery();

		while (records.next()) {

			Course course = new Course(records.getString("course_code"), records.getString("title"));

			// Converting the boolean number into a string back into a boolean
			boolean submittedBool = false;
			String submittedStr = records.getString("submitted");
			if (submittedStr.equals("1")) {
				submittedBool = true;
			}

			// Splitting up the date string from the database to convert it back into a
			// local date
			String dateString1 = records.getString("due_date");
			String[] dateParts = dateString1.split("-");
			LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]),
					Integer.parseInt(dateParts[2]));

			Evaluation evaluation = new Evaluation(course, records.getString("eval_name"), date, submittedBool);
			evalArray.add(evaluation);

		}
		dbConn.close();
		return evalArray;
	}
}