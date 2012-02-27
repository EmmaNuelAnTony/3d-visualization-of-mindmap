package lk.ac.pdn.ce.mm3d.DataStructure;

/**
 * Position in 3d SPACE
 * @author Pasindu Pavithra Ariyarathna
 *
 */
public class Position {
	private double X;
	private double Y;
	private double Z;
	
	public Position(){
		super();
	}
	
	public Position(double x, double y, double z) {
		super();
		X = x;
		Y = y;
		Z = z;
	}
	
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}
	public double getZ() {
		return Z;
	}
	public void setZ(double z) {
		Z = z;
	}
	
}
