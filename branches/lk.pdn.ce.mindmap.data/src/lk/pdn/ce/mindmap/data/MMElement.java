package lk.pdn.ce.mindmap.data;

public class MMElement {
	
	private String id;
	private String name;
	private String[] position;
	private MMElement parent;
	private MMElement[] child;
	
	public MMElement(){
		
	}
	
	public MMElement(String id, String name, String[] position,
			MMElement parent, MMElement[] child) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.parent = parent;
		this.child = child;
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
	public String[] getPosition() {
		return position;
	}
	public void setPosition(String[] position) {
		this.position = position;
	}
	public MMElement getParent() {
		return parent;
	}
	public void setParent(MMElement parent) {
		this.parent = parent;
	}
	public MMElement[] getChild() {
		return child;
	}
	public void setChild(MMElement[] child) {
		this.child = child;
	}
	
	

}
