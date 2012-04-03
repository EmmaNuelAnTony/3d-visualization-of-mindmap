package lk.ac.pdn.ce.mm3d;

import lk.ac.pdn.ce.mm3d.DataStructure.MMElement;
import android.util.Log;

import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;

public class Link extends Object3D{
	
	public Link(MMElement child){
		super(Primitives.getCylinder(10, 0.5f, (float)child.getDistance(child.getParent())));
		//calculate 3d space position
		float thisx,thisy,thisz,parentx,parenty,parentz;
		thisx=(float) child.getPosition().getX();
		thisy=(float) child.getPosition().getY();
		thisz=(float) child.getPosition().getZ();
		parentx=(float) child.getParent().getPosition().getX();
		parenty=(float) child.getParent().getPosition().getY();
		parentz=(float) child.getParent().getPosition().getZ();
		
		
		/* following rotation should be done prior to the position change because
		 * rotation is calculated with respect to the origin
//		 */
		float ry,rz,rx;	//rotation amount, in degrees
		ry=getAngle(parentx-thisx,parentz-thisz);
		rz=getAngle(parentx-thisx,parenty-thisy);
		rx=getAngle(parentz-thisz,parenty-thisy);
		
		Log.v("position", thisx+","+thisy+","+thisz);
		Log.v("position", thisx+","+thisy+","+thisz+","+parentx+","+parenty+","+parentz);
		Log.v("angle", rx+","+ry+","+rz);
		Log.v("z angle",","+rx);
		
		this.rotateX(-rx*0.65f);
//		parentLink.rotateZ(rz);
		this.rotateZ(rz);


		
		float x,y,z;	//calculating center position for the link
		x=(thisx+parentx)/2f;
		y=(thisy+parenty)/2f;
		z=(thisz+parentz)/2f;
		this.setOrigin(new SimpleVector(x,y,z));
	}
	private float getAngle(float x, float y){ // theta=atan(x/y) where x=x2-x1 and y=y2-y1
		if(y==0){
			if(x==0){
				return 0;
			} else if (x>0){
				return (float)Math.PI/2;
			} else if (x<0){
				return -(float)Math.PI/2;
			}
		}
		return (float) Math.toRadians((float) Math.atan((x)/(y))/Math.PI/2*360);
	}

}
