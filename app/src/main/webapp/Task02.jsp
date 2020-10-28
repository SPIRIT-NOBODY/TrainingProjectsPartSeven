<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="xml.XmlDomReader" import="xml.XmlWriter"
	import="xmltag.Student" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	/**
	В задаче 5.2 вместо базы данных “students” 
	использовать файл “students.xml”. 
	Разбор файла производить DOM-парсером.
	*/
	String fileName = "xml/students.xml";
	XmlDomReader domReader = new XmlDomReader(getServletContext().getRealPath(fileName));
	String actionDelete = request.getParameter("delete") != null ? (String) request.getParameter("delete")
			: "N";
	String actionInsert = request.getParameter("insert") != null ? (String) request.getParameter("insert")
			: "N";
	String result = "";
	if (actionDelete.equals("Y") && request.getParameter("id") != null) {
		result = domReader.removeNode((String) request.getParameter("id"));
	}
	if (actionInsert.equals("Y")) {
		result = domReader.insertNode(request);
	}
	XmlWriter<Student> xmlWriter = domReader.getXmlWriter();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task 02</title>
</head>
<body>
	<%
		if (xmlWriter.getCaptionName() != null) {
	%>
	<h2>
		<%=xmlWriter.getCaptionName()%></h2>
	<%
		}
	%>
	<div><%=result%></div>
	<table border="2">
		<tr>
			<th>Id</th>
			<th>имя</th>
			<th>Фамилия</th>
			<th>Группа</th>
			<th>Телефон</th>
			<th>Успеваемость</th>
		</tr>
		<%
			for (Student student : xmlWriter.getForecastList()) {
		%>
		<tr>
			<td><%=student.getId()%></td>
			<td><%=student.getFirstName()%></td>
			<td><%=student.getLastName()%></td>
			<td><%=student.getGroup()%></td>
			<td><%=student.getPhone()%></td>
			<td><%=student.getProgress()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<br>
	<div>
		<form method="post">
			<input type="hidden" name="delete" value="Y"> <select
				name="id">
				<%
					for (Student student : xmlWriter.getForecastList()) {
				%>
				<option value="<%=student.getId()%>"><%=student.getFirstName()%>
					<%=student.getLastName()%></option>
				<%
					}
				%>
			</select>
			<button type="submit">Delete selected</button>
		</form>
	</div>
	<br>
	<div>
		<form method="post">
			<input type="hidden" name="insert" value="Y">
			<div>
				id: <input name="id" type="text" placeholder="enter id" value="12">
			</div>
			<div>
				First name: <input name="firstname" type="text"
					placeholder="enter First name" value="Миша">
			</div>
			<div>
				Last name: <input name="lastname" type="text"
					placeholder="enter Last name" value="Махадлв">
			</div>
			<div>
				group: <input name="group" type="text" placeholder="enter group"
					value="23">
			</div>
			<div>
				phone gsm: <input name="gsm" type="text"
					placeholder="enter phone gsm" value="025">
			</div>
			<div>
				phone number: <input name="phone" type="text"
					placeholder="enter First name" value="123-15-45">
			</div>
			<div>
				progress: <input name="progress" type="number" step="0.01"
					placeholder="enter progress" value="6.9">
			</div>
			<div>
				<button type="submit">Insert</button>
			</div>
		</form>
	</div>
</body>
</html>