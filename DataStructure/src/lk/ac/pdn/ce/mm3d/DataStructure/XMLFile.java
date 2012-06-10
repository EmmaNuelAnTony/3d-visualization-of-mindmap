package lk.ac.pdn.ce.mm3d.DataStructure;

import java.io.*;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


public class XMLFile implements FileFormat{
	
	@Override
	public synchronized void readFormat(String content,MapData map) throws Exception{
		setXML(content, map);
	}

	@Override
	public synchronized String writeFormat(MapData map) throws Exception {
		return getXML(map.getRoot());
	}
	
	/**
	 * To get ming map xml output
	 * @param rootElement
	 * @return XML output
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static String getXML(MMElement rootElement) throws TransformerException, IOException, ParserConfigurationException{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		// create the root element and add it to the document
		Element root = doc.createElement("node");
		root.setAttribute("name", rootElement.getName());
		root.setAttribute("details", rootElement.getDetails());
		doc.appendChild(root);

		appendChildElementsToXML(rootElement, root, doc);

		// set up a transformer
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		// print to file
		//File file = new File(fileName);
		//Result result = new StreamResult(file);

		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);
		
		// Write the DOM document to the file
		DOMSource source = new DOMSource(doc);
		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(source, result);
		writer.close();
		return writer.toString();
		
	}

	
	private static void appendChildElementsToXML(MMElement parent,
			Element xmlParent, Document doc) {
		List<MMElement> children = parent.getChildren();
		if (children != null) {
			for (MMElement child : children) {
				Element XMLChild = doc.createElement("node");
				// Add other attributes for save here
				XMLChild.setAttribute("name", child.getName());
				XMLChild.setAttribute("details", child.getDetails());
				
				xmlParent.appendChild(XMLChild);

				// recursive call for child
				appendChildElementsToXML(child, XMLChild, doc);
			}
		}
	}
	
	/**
	 * to set the nodes using XML string
	 * @param xml
	 * @param map
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static void setXML(String xml,MapData map) throws ParserConfigurationException, SAXException, IOException{
		MMElement rootElement = map.getRoot();

		ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(stream);
		doc.getDocumentElement().normalize();

		// remove old mindmap data
		rootElement.getChildren().clear();
		rootElement.setName(doc.getDocumentElement().getAttribute("name"));
		rootElement.setDetails(doc.getDocumentElement().getAttribute("details"));
		appendChildElementsFromXML(rootElement, doc.getDocumentElement(), map);
	}
	

	private static void appendChildElementsFromXML(MMElement parent,
			Element xmlParent, MapData map) {
		NodeList XMLChildren = xmlParent.getChildNodes();
		if (XMLChildren != null) {
			for (int i = 0; i < XMLChildren.getLength(); i++) {

				if (XMLChildren.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element XMLChild = (Element) XMLChildren.item(i);
					MMElement child = new MMElement();
					// retrive attributes
					child.setName(XMLChild.getAttribute("name"));
					child.setDetails(XMLChild.getAttribute("details"));
					map.addElement(child, parent);

					// recursive call for child
					appendChildElementsFromXML(child, XMLChild, map);
				}
			}
		}
	}

	
}
