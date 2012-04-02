package lk.ac.pdn.ce.mm3d;

import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;

public class Link extends Object3D{
	
	public Link(float d){
		super(Primitives.getCylinder(10, 0.5f, d));
		this.setOrigin(new SimpleVector(0,0,0));
	}

}
