package lk.ac.pdn.ce.mm3d.DataStructure;


/**
 * represent a mindmap. starting from root node
 * @author Pasindu Pavithra Ariyarathna
 *	
 */
public class MapData {
	
	private MMElement root;
	
	/**
	 * Initialize data Structure
	 * @param mapName name for the root element
	 */
	public MapData(String mapName){
		root=new MMElement();
		root.setName(mapName);
	}
	
	/**
	 * @return root element of the MindMap
	 */
	public MMElement getRoot(){
		return root;
	}
	
	/**
	 * introduce new Element to mindmap 
	 * @param nElement new element
	 * @param parent parent element
	 */
	public synchronized void addElement(MMElement nElement,MMElement parent){
		parent.addChild(nElement);
		nElement.setParent(parent);
	}
	
	/**
	 * To remove existing element from the map
	 * @param cElement
	 */
	public synchronized void removeElement(MMElement cElement){
		//remove connections
		cElement.getParent().removeChild(cElement);
		cElement.setParent(null);
	}
	
}
