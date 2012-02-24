package lk.ac.pdn.ce.mindmap3d.DataStructure;

import static org.junit.Assert.*;

import org.junit.Test;

public class test1 {

	@Test
	public void saveTest() {
		System.out.println("**********************************");
		MapData m1=new MapData("mind map1 save test ");
	
		m1.saveXML("test.xml");
	}

}
