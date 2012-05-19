package lk.ac.pdn.ce.mm3d;

import lk.ac.pdn.ce.mm3d.DataStructure.MMElement;
import lk.ac.pdn.ce.mm3d.DataStructure.Position;

import android.graphics.Color;

import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureInfo;
import com.threed.jpct.TextureManager;

public class Link2 extends Object3D {

	public Link2(MMElement child) {
		super(10000);
		buildLink(child);
	}
	
	/**
	 * Create a cylinder connection between child and it's parent
	 * using a given number of faces
	 * @param child The element which needs to be connected
	 * @param faces Number of faces for the connecting cylinder
	 */
//	public Link2(MMElement child,int faces){
//		super(faces*2+2);
//		buildLink(child);
//	}
	
	private void buildLink(MMElement child){
		Position childPosition=child.getPosition();
		Position parentPosition=child.getParent().getPosition();
	    SimpleVector point1=new SimpleVector(parentPosition.getX(),parentPosition.getY(),parentPosition.getZ());
	    SimpleVector point2=new SimpleVector(childPosition.getX(),childPosition.getY(),childPosition.getZ());
	    

	    
	    SimpleVector prev1c=new SimpleVector(point1.x,point1.y+0.5,point1.z);
	    SimpleVector prev2c=new SimpleVector(point2.x,point2.y+0.5,point2.z);
	    
//	    ysing, zcos
	    for(double i=0; i<=Math.PI*2f; i+=Math.PI/20){
	    	SimpleVector p1c=new SimpleVector(point1.x,point1.y+Math.sin(i+0.5)/2f,point1.z+Math.cos(i+0.5)/2f);
	    	SimpleVector p2c=new SimpleVector(point2.x,point2.y+Math.sin(i)/2f,point2.z+Math.cos(i)/2f);
	    	
	    	this.addTriangle(prev1c,prev2c,p1c);
	    	this.addTriangle(p1c,p2c,prev2c);
	    	
	    	SimpleVector p1cx=new SimpleVector(point1.x,point1.y+Math.sin(i)/2f,point1.z+Math.cos(i)/2f);
	    	SimpleVector p2cx=new SimpleVector(point2.x,point2.y+Math.sin(i)/2f,point2.z+Math.cos(i)/2f);

	    	prev1c=p1cx;
	    	prev2c=p2cx;
	    }
	    
	    this.addTriangle(prev1c, prev2c,new SimpleVector(point1.x,point1.y+0.5,point1.z));
	    this.build();
	    this.compile();
	    this.strip();
	    this.setAdditionalColor(new RGBColor(0, 0, 0));
	   
	    
	}

}
