package lk.ac.pdn.ce.mm3d.DataStructure;

import java.util.LinkedList;
import java.util.List;

/**
 * Represent single element in mindmap
 * @author Pasindu Pavithra Ariyarathna
 *
 */
public class MMElement {
	private String id;
	private String name;
	private Position position;
	private MMElement parent;
	private List<MMElement> children;
	
	
	public MMElement(){
		children=new LinkedList<MMElement>();
	}
	

	protected void addChild(MMElement child){
		children.add(child);
	}
	
	protected void removeChild(MMElement child){
		children.remove(child);
	}
	
	public List<MMElement> getChildren(){
		return children;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public MMElement getParent() {
		return parent;
	}
	public void setParent(MMElement parent) {
		this.parent = parent;
	}
	
	
}
