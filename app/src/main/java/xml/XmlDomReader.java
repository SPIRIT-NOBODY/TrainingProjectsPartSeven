package xml;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import xmltag.Student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlDomReader {

	private Document document;
	private XmlWriter<Student> xmlWriter;
	private List<Student> studentsList = new ArrayList<>();
	private NodeList studentElements;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private String url;
	private File file;

	public XmlDomReader(String url) throws ParserConfigurationException, SAXException, IOException {
		this.url = url;
		file = new File(this.url);

		parse();
		studentElements = document.getElementsByTagName("student");
		fillXmlWriter();
	}

	private void parse() throws ParserConfigurationException, SAXException, IOException {
		factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setExpandEntityReferences(false);

		builder = factory.newDocumentBuilder();

		builder.setErrorHandler(new ErrorHandler() {
			@Override
			public void warning(SAXParseException exception) throws SAXException {
				throw exception;
			}

			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				throw exception;
			}

			@Override
			public void error(SAXParseException exception) throws SAXException {
				throw exception;
			}
		});
		document = builder.parse(file);
	}

	private void fillXmlWriter() {
		xmlWriter = new XmlWriter<>();
		xmlWriter.setCaptionName("Студенты");
		for (int i = 0; i < studentElements.getLength(); i++) {
			Student student = new Student();
			Node item = studentElements.item(i);
			student.setId(item.getAttributes().getNamedItem("id").getNodeValue());
			student.setFirstName(getChildNodeByTag(item, "firstname").getTextContent());
			student.setLastName(getChildNodeByTag(item, "lastname").getTextContent());
			student.setGroup(getChildNodeByTag(item, "group").getTextContent());

			Node phone = getChildNodeByTag(item, "phone");
			student.setGsm(phone.getAttributes().getNamedItem("gsm").getNodeValue());
			student.setPhone(phone.getTextContent());
			Float progress = 0.0f;
			try {
				progress = Float.parseFloat(getChildNodeByTag(item, "progress").getTextContent());
			} catch (Exception e) {
			}
			student.setProgress(progress);
			xmlWriter.addForecast(student);
		}
	}

	private Node getChildNodeByTag(Node item, String name) {
		Node child = null;
		NodeList children = item.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node tmpChild = children.item(i);
			if (tmpChild.getNodeName().equals(name)) {
				child = tmpChild;
				break;
			}
		}
		return child;
	}

	public String removeNode(String id)
			throws TransformerException, ParserConfigurationException, SAXException, IOException {

		String xmlId = "id_" + id;
		String result = "Student deleted";
		try {
			Node item = document.getElementById(xmlId);
			item.getParentNode().removeChild(item);
			document.normalize();

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMImplementation domImpl = document.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("DOCTYPE", "students", "students.dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			DOMSource source = new DOMSource(document);
			StreamResult fileXml = new StreamResult(new File(url));
			transformer.transform(source, fileXml);
		} catch (Exception e) {
			result = "Error delete student with id=" + id;
		}
		parse();
		studentElements = document.getElementsByTagName("student");
		fillXmlWriter();
		return result;
	}

	public String insertNode(HttpServletRequest request)
			throws ParserConfigurationException, SAXException, IOException {

		String result = "Students added";
		Student student = new Student();
		student.setId(request.getParameter("id"));
		student.setFirstName(request.getParameter("firstname"));
		student.setLastName(request.getParameter("lastname"));
		student.setGroup(request.getParameter("group"));
		student.setGsm(request.getParameter("gsm"));
		student.setPhone(request.getParameter("phone"));
		student.setProgress(request.getParameter("progress"));
		student.checkErrors();
		if (!student.getErrorsString().isEmpty()) {
			result = student.getErrorsString();
		} else {
			String id = "id_" + student.getId();
			try {
				if (document.getElementById(id) != null) {
					throw new Exception("element with id =  " + id + " is exist");
				}

				Element studentTag = document.createElement("student");
				studentTag.setAttribute("id", id);
				Element firstNameTag = document.createElement("firstname");
				firstNameTag.setTextContent(student.getFirstName());
				Element lastNameTag = document.createElement("lastname");
				lastNameTag.setTextContent(student.getLastName());
				Element groupTag = document.createElement("group");
				groupTag.setTextContent(student.getGroup());
				Element phoneTag = document.createElement("phone");
				phoneTag.setAttribute("gsm", student.getGsm());
				phoneTag.setTextContent(student.getPhone());
				Element progressTag = document.createElement("progress");
				progressTag.setTextContent("" + student.getProgress());

				studentTag.appendChild(lastNameTag);
				studentTag.appendChild(firstNameTag);
				studentTag.appendChild(groupTag);
				studentTag.appendChild(phoneTag);
				studentTag.appendChild(progressTag);

				document.getElementsByTagName("students").item(0).appendChild(studentTag);
				document.normalize();

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMImplementation domImpl = document.getImplementation();
				DocumentType doctype = domImpl.createDocumentType("DOCTYPE", "students", "students.dtd");
				transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
				DOMSource source = new DOMSource(document);
				StreamResult fileXml = new StreamResult(new File(url));
				transformer.transform(source, fileXml);
			} catch (Exception e) {
				result = "Error insert student";
				e.printStackTrace();
			}
			parse();
			studentElements = document.getElementsByTagName("student");
			fillXmlWriter();
		}
		return result;

	}

	public XmlWriter<Student> getXmlWriter() {
		return xmlWriter;
	}
}
