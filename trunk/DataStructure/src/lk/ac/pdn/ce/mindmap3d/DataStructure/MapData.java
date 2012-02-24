package lk.ac.pdn.ce.mindmap3d.DataStructure;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * represent a mindmap. starting from root node
 * @author Pasindu Pavithra Ariyarathna
 *	
 */
public class MapData {
	
	MMElement root;
	
	public MapData(String mapName){
		root=new MMElement();
		root.setName(mapName);
	}
	
	public MMElement getRoot(){
		return root;
	}
	
	public synchronized void addElement(MMElement nElement,MMElement parent){
		parent.addChild(nElement);
		nElement.setParent(parent);
	}
	
	public synchronized void removeElement(MMElement cElement){
		//remove connections
		cElement.getParent().removeChild(cElement);
		cElement.setParent(null);
	}
	
	public synchronized void loadXML(String fileName){
		XMLFile.retriveXML(fileName, root);
	}
	
	public synchronized void saveXML(String fileName){
		try {
			XMLFile.saveXML(fileName, root);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
