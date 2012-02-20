/* 
 * Note- Calculations are happens using radions
 */

/**
 * 3D positioning calculations Happens in this class 
 * @author Pasindu Ariyarathan
 *
 */
public class MindMath {
	static int levelSize=10;
	
	//assumption- root node is at (0,0,0)
	public static void calPositions(int nuOfChildren,float spaceAngle,int level,double firstHAngleOfLevel,double firstVAngleOfLevel){
		float devideAngle=spaceAngle/((float) nuOfChildren*2);
		float newWidthAngle =devideAngle;
		
		position[] result = new position[nuOfChildren];
		if(level%2==0){
			//devide vertically
			double currentAngleH=firstHAngleOfLevel+devideAngle;
			double currentAngleV=firstVAngleOfLevel;
			for(int i=0;i<nuOfChildren;i++){
				
				System.out.println("level "+level);
				System.out.println(levelSize*(level+1));
				System.out.println(Math.toDegrees(currentAngleH));
				System.out.println(Math.toDegrees(currentAngleV));
				System.out.println("");
				
				calPositions(4,2*devideAngle,level+1,currentAngleH,currentAngleV-devideAngle);
				currentAngleH=currentAngleH+2*devideAngle;
			}
		}else{
			//devide horizontally
			double currentAngleH=firstHAngleOfLevel;
			double currentAngleV=firstVAngleOfLevel+devideAngle;
			for(int i=0;i<nuOfChildren;i++){
				
				System.out.println("level "+level);
				System.out.println(levelSize*(level+1));
				System.out.println(Math.toDegrees(currentAngleH));
				System.out.println(Math.toDegrees(currentAngleV));
				System.out.println("");
				calPositions(4,2*devideAngle,level+1,currentAngleH-devideAngle,currentAngleV);
				currentAngleV=currentAngleV+2*devideAngle;
			}
		}
	}
	
	public double[] polarToCartesian(int x,int y, int z){
		double r =  Math.sqrt(x*x + y*y + z*z);
		double a2 =  Math.tan((float)y / (float) x);
		double a1 = Math.cos((float) z / (float) r);
		double[] res= {r,a2,a1};
		return res;
	}

	public static void main(String arg[]){
		calPositions(4,(float) (2*Math.PI),0,-(float) (Math.PI),0);
	}
	
}

class position{
	private int x;
	private int y;
	private int z;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	
	
}
