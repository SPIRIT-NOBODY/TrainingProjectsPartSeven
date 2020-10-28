<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	/**
	Задачу 5.2 реализовать на JSP+JSTL.
	*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task 03</title>
</head>
<body>
	<c:catch var="catchException">
		<sql:setDataSource driver="com.mysql.cj.jdbc.Driver"
			url="jdbc:mysql://127.0.0.1/java_test_db?serverTimezone=Europe/Moscow&useSSL=false&autoReconnect=true"
			user="root" password="" />

		<c:if test="${param.delete == 'Y' && !empty paramValues.Id}">
	 	<sql:update var="count">
			delete from `students` where `Id` in ('${fn:join(paramValues.Id,'\',\'')}')
		</sql:update>
        <c:if test="${count>=1}">
            <font size="5" color='green'>Delete ${count}
					student(s)</font>                 
        </c:if>
		</c:if>
		<c:if test="${param.insert == 'Y'}">
			<sql:update var="count">
			insert into `students` (`Name`,`Group`,`MarkMath`,`MarkLiterature`,`MarkEnglish`,`MarkProgramming`) 
			values ('${param.Name}','${param.Group}','${param.MarkMath}' ,'${param.MarkLiterature}','${param.MarkEnglish}','${param.MarkProgramming}')
		</sql:update>
			<c:if test="${count>=1}">
            <font size="5" color='green'>Inserted ${count}
					student(s)</font>                 
        </c:if>
		</c:if>

		<sql:query var="students">
	  	select * from students where id >  ?
 		<sql:param value="0" />
		</sql:query>

		<form method="post">
			<input type="hidden" name="delete" value="Y">
			<table border="2">
				<tr>
					<c:forEach var="col" items="${students.columnNames}">
						<th>${col}</th>
					</c:forEach>
				</tr>
				<c:set var="stp" value="1"></c:set>
				<c:forEach var="index" begin="1" items="${students.rowsByIndex}">
					<tr>
						<c:forEach var="student" items="${index}" varStatus="status">
							<c:set var="curr" value="${status.index}" scope="page"></c:set>
							<td><c:if test="${students.columnNames[curr] == 'Id' }">
									<input name="Id" value="${index[curr]}" type="checkbox"
										multiple="multiple">
								</c:if> ${student}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
			<div>
				<button type="submit">Delete selected</button>
			</div>
		</form>
		<br>
		<div>
			<form method="post">
				<input type="hidden" name="insert" value="Y">
				<div>
					Name: <input name="Name" value="Гриша Пырков">
				</div>
				<div>
					Group: <input name="Group" value="123">
				</div>
				<div>
					MarkMath: <input name="MarkMath" value="5">
				</div>
				<div>
					MarkLiterature: <input name="MarkLiterature" value="6">
				</div>
				<div>
					MarkEnglish: <input name="MarkEnglish" value="9">
				</div>
				<div>
					MarkProgramming: <input name="MarkProgramming" value="8">
				</div>
				<div>
					<button type="submit">Insert</button>
				</div>
			</form>
		</div>
	</c:catch>
	<c:if test="${!empty catchException}">
		<div>
			<p>Error:</p>
			<p style="color: red;">${catchException.message}</p>
		</div>
	</c:if>
</body>
</html>