package lk.ac.pdn.ce.mm3d;

import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;

public class Link extends Object3D{
	
	public Link(float d){
		super(Primitives.getCylinder(10, 0.5f, d));
		this.setOrigin(new SimpleVector(0,0,0));
	}
	
//	public Link(float r, float d){	//default segments sest to 20
//		super(r,r-0.2f,d,20);
//	}
//	
//	public Link(float r, float d, int s){
//		super(r,r-0.01f,d,s);
//	}	

}
