package lk.ac.pdn.ce.mm3d;

import lk.ac.pdn.ce.mm3d.DataStructure.MMElement;
import android.renderscript.Mesh.Primitive;
import android.util.Log;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;

public class Node extends Object3D{
	
	private Node parent;
	private Link parentLink;
	private int level;
	private MMElement mmElement;
	
	
	public Node(RGBColor color){			// no parent means root node
		super(Primitives.getSphere(5));
		this.setAdditionalColor(color);
		level=0;
	}
	
	public Node(Node p,RGBColor color){
		super(Primitives.getSphere(4));
		this.setAdditionalColor(color);
		parent=p;
		level=parent.getLevel();
	}
	
	public Node(Node p){
		super(Primitives.getSphere(4));
		this.setAdditionalColor(new RGBColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255)));
		parent=p;
		level=parent.getLevel()+1;
	}
	
	
	public int getLevel() {
		return level;
	}

	public Node getParent() {
		return parent;
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

	public void buildParentLink(){
		if (parent==null){
			return;
		}
		
		parentLink=new Link(mmElement);

	}
	
	
	

	
	
}
