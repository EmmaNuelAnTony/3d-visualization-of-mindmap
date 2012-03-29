package lk.ac.pdn.ce.mm3d;

import android.util.Log;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;

public class Node extends Object3D{
	
	private Node parent;
	private Link parentLink;
	private int level;
	
	public Node(RGBColor color){			// no parent means root node
		super(Primitives.getSphere(1));
		this.setAdditionalColor(color);
		level=0;
	}
	
	public Node(Node p,RGBColor color){
		super(Primitives.getSphere(1));
		this.setAdditionalColor(color);
		parent=p;
		level=parent.getLevel();
//		buildParentLink();
	}
	
	public Node(Node p){

//		super(0.5f,40,40,
//				p.getLevel()>0 ? new Color4((p.colors().getPropertyR(0)+150) % 255,(p.colors().getPropertyB(0)+200) % 255,(p.colors().getPropertyG(0)+200) % 255,255): new Color4((float) (Math.random() * 255),(float) (Math.random() * 255),(float) (Math.random() * 255),255));
		
		super(Primitives.getSphere(1));
		this.setAdditionalColor(new RGBColor((int) Math.random()*255,(int) Math.random()*255,(int) Math.random()*255));
		
		parent=p;
		level=parent.getLevel()+1;
//		buildParentLink();
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

	public void buildParentLink(){
		if (parent==null){
			return;
		}
		
		float thisx,thisy,thisz,parentx,parenty,parentz;
		SimpleVector origin=this.getOrigin();
		thisx=origin.x;
		thisy=origin.y;
		thisz=origin.z;
		SimpleVector parentOrigin=parent.getOrigin();
		parentx=parentOrigin.x;
		parenty=parentOrigin.y;
		parentz=parentOrigin.z;
//		thisx=this.position().x;
//		thisy=this.position().y;
//		thisz=this.position().z;
//		parentx=parent.position().x;
//		parenty=parent.position().y;
//		parentz=parent.position().z;
		
		// calculating the distance of the link
		float d=(float) Math.sqrt(((Math.pow(thisx-parentx, 2))+
				(Math.pow(thisy-parenty, 2))+
				(Math.pow(thisz-parentz, 2))));
		
		
		/* following rotation should be done prior to the position change because
		 * rotation is calculated with respect to the origin
		 */
		float ry,rz,rx;	//rotation amount, in degrees
		ry=getAngle(parentx-thisx,parentz-thisz);
		rz=getAngle(parenty-thisy,parentx-thisx);
		rx=getAngle(parenty-thisy,parentz-thisz);
		
//		Log.v("position", thisx+","+thisy+","+thisz);
//		Log.v("position", thisx+","+thisy+","+thisz+","+parentx+","+parenty+","+parentz);
//		Log.v("angle", rx+","+ry+","+rz);
//		Log.v("z angle",","+rx);
		
		parentLink=new Link(d);
//		parentLink.rotateX(-rx);
//		parentLink.rotateY(ry);
		parentLink.rotateZ(rz);
//		parentLink.rotation().setAll(-rx, ry, 0f);
//		parentLink.rotation().add(new Number3d(45,45,45));
//		parentLink.rotation().x+=-rx;
//		parentLink.rotation().y+=ry;
//		parentLink.rotation().z-=rz;


		
		float x,y,z;	//calculating center position for the link
		x=(thisx+parentx)/2f;
		y=(thisy+parenty)/2f;
		z=(thisz+parentz)/2f;
		
		parentLink.setOrigin(new SimpleVector(x,y,z));
//		parentLink.position().setAll(x,y,z);

	}
	
	private float getAngle(float x, float y){ // theta=atan(x/y) where x=x2-x1 and y=y2-y1
		if(y==0){
			if(x==0){
				return 0;
			} else if (x>0){
				return 90;
			} else if (x<0){
				return -90;
			}
		}
		return (float) ((float) Math.atan((x)/(y))/Math.PI/2*360);
	}
	

	
	
}
