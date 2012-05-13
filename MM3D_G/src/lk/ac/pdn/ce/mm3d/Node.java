package lk.ac.pdn.ce.mm3d;

import lk.ac.pdn.ce.mm3d.DataStructure.MMElement;

import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;

public class Node extends Object3D {

	private Node parent;// parent node of the object
	private Link parentLink;// link for object to it's parent
	private int level;// map level
	private MMElement mmElement;// datastructure element

	public Node(RGBColor color) { // no parent means root node
		super(Primitives.getSphere(5));
		this.setAdditionalColor(color);
		level = 0;
	}

	public Node(Node p, RGBColor color) {
		super(Primitives.getSphere(4));
		this.setAdditionalColor(color);
		parent = p;
		level = parent.getLevel();
	}

	public Node(Node p) {
		super(Primitives.getSphere(4));
		this.setAdditionalColor(new RGBColor((int) (Math.random()*255),(int)
		 (Math.random()*255),(int) (Math.random()*255)));
		parent = p;
		level = parent.getLevel() + 1;
//		RGBColor c1 = null;
//		switch(level){
//		case 1:c1 = new RGBColor(255, 0, 0);break;
//		case 2:c1 = new RGBColor(0, 255, 0);break;
//		case 3:c1 = new RGBColor(0, 0, 255);break;
//		default:c1 = new RGBColor(0, 0, 0);
//		}
//		this.setAdditionalColor(c1);
	}

	public int getLevel() {
		return level;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public void setParentLink(Link parentLink) {
		this.parentLink = parentLink;
	}

	public Link getParentLink() {
		return parentLink;
	}

	public MMElement getMmElement() {
		return mmElement;
	}

	public void setMmElement(MMElement mmElement) {
		this.mmElement = mmElement;
	}

	public void buildParentLink() {
		if (parent == null) {
			return;
		}
		parentLink = new Link(mmElement);

	}

}
