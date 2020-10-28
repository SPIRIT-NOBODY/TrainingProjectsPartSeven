<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.io.*" import="java.util.*"
	import="xmltag.*" import="xml.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	/**
	С использованием JSP отобразить прогноз погоды на ближайшие сутки в Минске.
	Для разбора XML-документа использовать SAX-парсер. XML-документ задан в формате, 
	предоставляемом порталом www.gismeteo.com. В отображаемый прогноз включить  
	температуру, давление, скорость ветра, осадки, облачность на ближайшие 12 часов.	
	(Прогноз погоды для Минска http://www.informer.gismeteo.ru/xml/26850_1.xml )
	<?xml version="1.0"?>
	<MMWEATHER>
	 <REPORT type="frc3">
	  <TOWN index="27612" sname="%CC%EE%F1%EA%E2%E0" latitude="56" longitude="38">
	    <FORECAST day="3" month="5" year="2007" hour="15" tod="2" predict="18" weekday="5">
	     <PHENOMENA cloudiness="2" precipitation="4" rpower="0" spower="0"/>
	     <PRESSURE max="742" min="740"/>
	     <TEMPERATURE max="8" min="6"/>
	     <WIND min="2" max="5" direction="1"/>
	     <RELWET max="49" min="44"/>
	     <HEAT min="6" max="8"/>
	    </FORECAST>
	    <FORECAST day="3" month="5" year="2007" hour="21" tod="3" predict="24" weekday="5">
	     <PHENOMENA cloudiness="2" precipitation="4" rpower="0" spower="0"/>
	     <PRESSURE max="742" min="740"/>
	     <TEMPERATURE max="3" min="1"/>
	     <WIND min="2" max="5" direction="0"/>
	     <RELWET max="67" min="62"/>
	     <HEAT min="1" max="3"/>
	    </FORECAST>
	    <FORECAST day="4" month="5" year="2007" hour="3" tod="0" predict="30" weekday="6">
	     <PHENOMENA cloudiness="3" precipitation="10" rpower="0" spower="0"/>
	     <PRESSURE max="742" min="740"/>
	     <TEMPERATURE max="-1" min="-3"/>
	     <WIND min="2" max="5" direction="6"/>
	     <RELWET max="71" min="66"/>
	     <HEAT min="-3" max="-1"/>
	    </FORECAST>
	    <FORECAST day="4" month="5" year="2007" hour="9" tod="1" predict="36" weekday="6">
	     <PHENOMENA cloudiness="3" precipitation="10" rpower="0" spower="0"/>
	     <PRESSURE max="742" min="740"/>
	     <TEMPERATURE max="7" min="5"/>
	     <WIND min="3" max="6" direction="6"/>
	     <RELWET max="53" min="48"/>
	     <HEAT min="4" max="6"/>
	    </FORECAST>
	  </TOWN>
	 </REPORT>
	</MMWEATHER>
	*/
	String fileName = "xml/pogoda.xml";
	XmlSaxReader xmlReader = new XmlSaxReader(getServletContext().getRealPath(fileName));
	XmlWriter<Forecast> xmlWriter = xmlReader.getXmlWriter();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task 01</title>
</head>
<body>
	<%
		if (xmlWriter.getCaptionName() != null) {
	%>
	<h2>
		Город:
		<%=xmlWriter.getCaptionName()%></h2>
	<%
		}
		for (Forecast forecast : xmlWriter.getForecastList()) {
	%>

	<table border="2">
		<caption><%=forecast.getInfo()%></caption>
		<tr>
			<td>температура воздуха</td>
			<td><%=forecast.getTemperature()%></td>
		</tr>
		<tr>
			<td>атмосферное давление</td>
			<td><%=forecast.getPressure()%></td>
		</tr>
		<tr>
			<td>облачность</td>
			<td><%=forecast.getCloudiness()%></td>
		</tr>
		<tr>
			<td>тип осадков</td>
			<td><%=forecast.getPrecipitation()%></td>
		</tr>
		<tr>
			<td>скорость ветра</td>
			<td><%=forecast.getWind()%></td>
		</tr>
	</table>
	<hr>
	<%
		}
	%>

</body>
</html>