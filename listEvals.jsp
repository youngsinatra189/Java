<!--
Name:  Roshan Sahu
Assignment:  Assignment #3
Date:  August 2nd, 2018

Description: The purpose of this page to display the evaluation tracker to the user 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evaluation Tracker</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
	<h2>Evaluation Tracker</h2>
	<c:set var="evals" value="${sessionScope.listEvaluations}"
		scope="session" />
	<table class="center">
		<tr>
			<th>Course</th>
			<th>Evaluation</th>
			<th>Due Date</th>
			<th>Submitted?</th>
		</tr>
		<c:forEach items="${evals}" var="current">
			<tr>
				<td><c:out value="${current.getCourse()}" />
				<td><c:out value="${current.getEvalName()}" /></td>
				<td><c:out value="${current.getDueDate()}" /></td>
				<td><c:out value="${current.isSubmitted()}" /></td>
			</tr>
		</c:forEach>
		<tr>
	</table>
</body>
</html>