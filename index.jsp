<!--
Name:  Roshan Sahu
Assignment:  Assignment #3
Date:  August 2nd, 2018

Description: This web application allows a user to track homework for their courses.
Page: The index page contains a form that allows a user to enter a new evaluation.
Files: 
listEvals.jsp - lists of all the records in the evaluations table, sorted by due date
AddEvaluations.java - adds a new evaluation to the tracker database
ListEvaluations.java - retrieves a list of all the evaluations
DbListener.java -  manages the dao for the duration of the application
DaoEvaluations.java: Encapsulates all database code 
Evaluation.java: Models evaluation object
Course.java: Models course object
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- setDataSource element -->
<sql:setDataSource var="courses" driver="com.mysql.cj.jdbc.Driver"
	url="jdbc:mysql://localhost:3306/tracker" user="admin"
	password="math127" />

<!-- query: SELECT * FROM courses -->
<sql:query var="results" dataSource="${courses}"
	sql="SELECT * FROM courses;" />

<title>Add a New Evaluation</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
	<%-- Displaying the list of errors, if it exists--%>
	<div style="color: red">${errorMessage}</div>
	

	<%-- Link to the list evaluations Servlet --%>
	<a href="ListEvaluations">List All Evaluations</a>

	<%-- The form to add a course --%>
	<form action="AddEvaluations" method="post">
		Course:
		<%-- DropDown populated with list of courses from the SQL database --%>
		
		<select name="listCourses">
			<c:forEach var="row" items="${results.rows}">
				<option value="${row.code}">${row.title}</option>
			</c:forEach>
		</select> <br> Evaluation: <input type="text" name="evaluation"> <br>Due
		Date (yyyy/mm/dd): Year: <input type="text" name="year">

		<%-- DropDown with 12 months of the year --%>
		Month: <select name="selectMonth">
			<c:forEach var="month" begin="1" end="12" step="1">
				<option value="${month}">${month}</option>
			</c:forEach>

			<%-- DropDown with 31 days of the month --%>
		</select> Day: <select name="selectDay">
			<c:forEach var="day" begin="1" end="31" step="1">
				<option value="${day}">${day}</option>
			</c:forEach>
		</select> <br>
		<div>
			<input type="radio" name="submitStatus" value="true">
			Submitted<input type="radio" name="submitStatus" value="false"
				checked> Not Submitted<br>
			<%--Sending the information to the AddEvaluation.java servlet --%>
		</div>
		<button type="submit" id="submit">Add</button>
		<button type="reset" value="Reset">Clear</button>
	</form>

</body>
</html>