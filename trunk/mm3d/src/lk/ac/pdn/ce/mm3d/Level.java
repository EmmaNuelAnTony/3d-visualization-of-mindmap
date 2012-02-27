package lk.ac.pdn.ce.mm3d;

import min3d.vos.Color4;

public class Level {
	
	private int level;	// level number, 0 being the root level.
	private Color4 color;
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Color4 getColor() {
		return color;
	}

	public void setColor(Color4 color) {
		this.color = color;
	}

	public Level(int l, Color4 c){
		level=l;
		color=c;
	}

}
