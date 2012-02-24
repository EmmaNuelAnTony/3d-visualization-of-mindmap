package lk.ac.pdn.ce.mindmap3d.DataStructure;

import java.io.*;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class XMLFile {

	protected static void saveXML(String fileName, MMElement rootElement)
			throws ParserConfigurationException, TransformerException {

		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		// create the root element and add it to the document
		Element root = doc.createElement("node");
		root.setAttribute("name", rootElement.getName());
		doc.appendChild(root);

		appendChildElementsToXML(rootElement, root, doc);

		// set up a transformer
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		// print to file
		File file = new File(fileName);
		Result result = new StreamResult(file);

		// Write the DOM document to the file
		DOMSource source = new DOMSource(doc);
		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(source, result);
	}

	private static void appendChildElementsToXML(MMElement parent,
			Element xmlParent, Document doc) {
		List<MMElement> children = parent.getChildren();
		if (children != null) {
			for (MMElement child : children) {
				Element XMLChild = doc.createElement("node");
				// Add other attributes for save here
				XMLChild.setAttribute("name", child.getName());

				xmlParent.appendChild(XMLChild);

				// recursive call for child
				appendChildElementsToXML(child, XMLChild, doc);
			}
		}
	}

	protected static void retriveXML(String fileName, MMElement rootElement)
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		// remove old mindmap data
		rootElement.getChildren().clear();

		rootElement.setName(doc.getDocumentElement().getNodeName());

		appendChildElementsFromXML(rootElement, doc.getDocumentElement(), doc);
	}

	private static void appendChildElementsFromXML(MMElement parent,
			Node xmlParent, Document doc) {
		NodeList XMLChildren = xmlParent.getChildNodes();
		if (XMLChildren != null) {
			for (int i = 0; i < XMLChildren.getLength(); i++) {
				Node XMLChild =XMLChildren.item(i);
				MMElement child = new MMElement();
				child.setName(XMLChild)
				
					// Add other attributes for save here
					XMLChild.setAttribute("name", child.getName());

					xmlParent.appendChild(XMLChild);

					// recursive call for child
					appendChildElementsFromXML(child, XMLChild, doc);
				}
			}
		}
	}

}
