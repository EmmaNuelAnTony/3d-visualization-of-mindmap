package lk.ac.ce.mm3d.Math;
import static org.junit.Assert.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import lk.ac.pdn.ce.mm3d.DataStructure.MMElement;
import lk.ac.pdn.ce.mm3d.DataStructure.MapData;

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
		
		MMElement n31=new MMElement();
		n31.setName("n31");
		m1.addElement(n31, n3);
		
		MMElement n311=new MMElement();
		n311.setName("n311");
		m1.addElement(n311, n31);
		
		MMElement n312=new MMElement();
		n312.setName("n312");
		m1.addElement(n312, n31);
		
		
		
		MMElement n11=new MMElement();
		n11.setName("n11");
		m1.addElement(n11, n3);
		
		MMElement n4=new MMElement();
		n4.setName("n4");
		m1.addElement(n4, root);
		
		MMElement n5=new MMElement();
		n5.setName("n5");
		m1.addElement(n5, root);
		
//		MMElement n22=new MMElement();
//		n22.setName("n22");
//		m1.addElement(n22, n2);
		
		
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
