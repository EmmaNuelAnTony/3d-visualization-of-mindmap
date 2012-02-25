package lk.ac.ce.mindmap3d.Math;
import static org.junit.Assert.*;
import lk.ac.pdn.ce.mindmap3d.DataStructure.MMElement;
import lk.ac.pdn.ce.mindmap3d.DataStructure.MapData;

import org.junit.Test;

/**
 * test class
 * @author Pasindu Pavithra Ariyarathana
 *
 */
public class test1 {

	@Test
	public void test() {
		MapData m1=new MapData("test map");
		MMElement root= m1.getRoot();
		
		MMElement n1=new MMElement();
		n1.setName("n1");
		m1.addElement(n1, root);
		
		MMElement n2=new MMElement();
		n2.setName("n2");
		m1.addElement(n2, root);
		
		MMElement n3=new MMElement();
		n3.setName("n3");
		m1.addElement(n3, root);
		
		MMElement n21=new MMElement();
		n21.setName("n21");
		m1.addElement(n21, n2);
		
		MindMath mm1=new MindMath(10);
		mm1.positionGenerate(root);
		
		printAllCoordinates(root);
	}
	
	private void printAllCoordinates(MMElement e1){
		System.out.println(e1.getName());
		System.out.println(e1.getPosition().getX()+","+e1.getPosition().getY()+","+e1.getPosition().getZ()+"\n");
		for(MMElement c1:e1.getChildren()){
			printAllCoordinates(c1);
		}
	}

}
