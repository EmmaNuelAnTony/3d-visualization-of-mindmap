package lk.ac.pdn.ce.mindmap3d.DataStructure;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Unit test
 * @author QBTNMEV
 *
 */
public class test1 {

	@Test
	public void Test() {
		MapData m1=new MapData("mind map1 save test ");
		try {
			m1.loadXML("test.xml");
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			m1.saveXML("test1.xml");
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//compare documents
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setCoalescing(true);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setIgnoringComments(true);
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc1 = db.parse(new File("test.xml"));
			doc1.normalizeDocument();
			Document doc2 = db.parse(new File("test1.xml"));
			doc2.normalizeDocument();
			assertTrue("Error",doc1.isEqualNode(doc2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
	}

}
