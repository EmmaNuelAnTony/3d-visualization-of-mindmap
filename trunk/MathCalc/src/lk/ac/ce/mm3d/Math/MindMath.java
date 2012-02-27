package lk.ac.ce.mm3d.Math;

import lk.ac.pdn.ce.mm3d.DataStructure.*;

/* 
 * Note- Calculations are happens using radions
 */

/**
 * 3D positioning calculations Happens in this class 
 * @author Pasindu Pavithra Ariyarathana
 *
 */
public class MindMath {
	
		
	private int levelSize=10;
	
	/**
	 * Initialize MindMath
	 * @param levelSize maximum size for a level can go
	 */
	public MindMath(int levelSize){
		this.levelSize=levelSize;
	}

	/**
	 * Generate positions of the map 
	 * @param root root node of the map
	 */
	public void positionGenerate(MMElement root){
		setPositionAsCartician(root,0,0,0);
		calPositions(root,(float) (2*Math.PI),0,(float) (Math.PI),(float) (Math.PI)/2);
	}
	
	private void setPositionAsCartician(MMElement element,double r, double a,double b){
		double x = Math.round(r* Math.sin(b)*Math.cos(a));
		double y = Math.round(r* Math.sin(b)*Math.sin(a));
		double z = Math.round(r* Math.cos(b));
		//System.out.println(element.getName());
		//System.out.println(x+","+y+","+z);
		element.setPosition(new Position(x,y,z));
	}
	
	//assumption- root node is at (0,0,0)
	private void calPositions(MMElement parent,float spaceAngle,int level,double firstHAngleOfLevel,double firstVAngleOfLevel){
		int nuOfChildren=parent.getChildren().size();
		float devideAngle=spaceAngle/((float) nuOfChildren*2);
		float newWidthAngle =devideAngle;
		
		
		if(level%2==0){
			//devide vertically
			double currentAngleH=firstHAngleOfLevel+devideAngle;
			double currentAngleV=firstVAngleOfLevel;
			for(int i=0;i<nuOfChildren;i++){
				
//				System.out.println("level "+level);
//				System.out.println(levelSize*(level+1));
//				System.out.println(Math.toDegrees(currentAngleH));
//				System.out.println(Math.toDegrees(currentAngleV));
//				System.out.println("");
				MMElement child=parent.getChildren().get(i);
				setPositionAsCartician(child,levelSize*(level+1),currentAngleH,currentAngleV);
				calPositions(child,2*devideAngle,level+1,currentAngleH,currentAngleV-devideAngle);
				currentAngleH=currentAngleH+2*devideAngle;
			}
		}else{
			//devide horizontally
			double currentAngleH=firstHAngleOfLevel;
			double currentAngleV=firstVAngleOfLevel+devideAngle;
			for(int i=0;i<nuOfChildren;i++){
				
//				System.out.println("level "+level);
//				System.out.println(levelSize*(level+1));
//				System.out.println(Math.toDegrees(currentAngleH));
//				System.out.println(Math.toDegrees(currentAngleV));
//				System.out.println("");
				MMElement child=parent.getChildren().get(i);
				setPositionAsCartician(child,levelSize*(level+1),currentAngleH,currentAngleV);
				calPositions(child,2*devideAngle,level+1,currentAngleH-devideAngle,currentAngleV);
				currentAngleV=currentAngleV+2*devideAngle;
			}
		}
	}
	
}
