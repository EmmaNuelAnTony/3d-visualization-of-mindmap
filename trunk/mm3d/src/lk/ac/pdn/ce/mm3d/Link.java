package lk.ac.pdn.ce.mm3d;

import min3d.objectPrimitives.HollowCylinder;

public class Link extends HollowCylinder{
	
	public Link(float d){
		super(0.05f,0.04f,d,20);
	}
	
	public Link(float r, float d){	//default segments sest to 20
		super(r,r-0.2f,d,20);
	}
	
	public Link(float r, float d, int s){
		super(r,r-0.01f,d,s);
	}	

}
