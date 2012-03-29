package lk.ac.pdn.ce.mm3d;

import com.threed.jpct.RGBColor;

public class Level {
	
	private int level;	// level number, 0 being the root level.
	private RGBColor color;
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public RGBColor getColor() {
		return color;
	}

	public void setColor(RGBColor color) {
		this.color = color;
	}

	public Level(int l, RGBColor c){
		level=l;
		color=c;
	}

}
