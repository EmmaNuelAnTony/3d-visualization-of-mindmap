package lk.ac.pdn.ce.mm3d.DataStructure;

/**
 * Position in 3d SPACE
 * @author Pasindu Pavithra Ariyarathna
 *
 */
public class Position {
	private float X;
	private float Y;
	private float Z;
	
	public Position(){
		super();
	}
	
	public Position(float x, float y, float z) {
		super();
		X = x;
		Y = y;
		Z = z;
	}
	
	public float getX() {
		return X;
	}
	public void setX(float x) {
		X = x;
	}
	public float getY() {
		return Y;
	}
	public void setY(float y) {
		Y = y;
	}
	public float getZ() {
		return Z;
	}
	public void setZ(float z) {
		Z = z;
	}
	
}
