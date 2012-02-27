package lk.ac.pdn.ce.mm3d;


import lk.ac.ce.mm3d.Math.MindMath;
import lk.ac.pdn.ce.mm3d.DataStructure.MMElement;
import lk.ac.pdn.ce.mm3d.DataStructure.MapData;
import min3d.core.RendererActivity;
import min3d.objectPrimitives.*;
import min3d.vos.Color4;
import min3d.vos.Light;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

/**
 * This is the "demo" example.
 * It shows how to add children to Object3dContainers.  
 * 
 * If you're familiar with Flash, this is similar to adding DisplayObjects to the displaylist.
 * 
 * If you're familiar with Papervision3D or Away3D for Flash, this is similar to using 
 * DisplayObject3D's or Object3D's to the Scene.
 * 
 * @author Lee
 */
public class Obj3DView extends RendererActivity
{
	
	private float prevSwipeY,swipeY,prevSwipeX,swipeX,prevSwipeZ,swipeZ;
	private long downTime,actionTime;
			
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}
	
	private Sphere mSphere;
	private HollowCylinder hc;
	private Node[] nodes;
	
	
	Color4 black=new Color4(0,0,0,255);
	Color4 red=new Color4(255,0,0,255);
	Color4 green=new Color4(0,255,0,255);
	Color4 blue=new Color4(0,0,255,255);
	
	public void initScene()
	{
		scene.lights().add(new Light());
		scene.lightingEnabled(true);
		scene.backgroundColor().setAll(new Color4(0,0,255,100));
		
		test();
		


	}
	
	public void updateScene() {
//		if(scene.camera().position.x>10){
//			scene.camera().position.x=0;
//		}
//		if(scene.camera().position.y>10){
//			scene.camera().position.y=0;
//		}
//		scene.camera().position.x+=0.01f;
//		scene.camera().target.x+=0.01f;
//		scene.camera().position.y+=0.01f;
		

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			prevSwipeX=event.getX();
			prevSwipeY=event.getY();
			downTime=System.currentTimeMillis();
			break;
		case MotionEvent.ACTION_UP:
//			downTime=Long.MAX_VALUE;
			break;
		case MotionEvent.ACTION_MOVE:
			actionTime=System.currentTimeMillis()-downTime;
			swipeX=event.getX();
			swipeY=event.getY();
			if(actionTime<3000){
				
				scene.camera().position.x-=(swipeX-prevSwipeX)/40;
				scene.camera().target.x-=(swipeX-prevSwipeX)/40;
				
				
				scene.camera().position.y+=(swipeY-prevSwipeY)/400;
				scene.camera().target.y+=(swipeY-prevSwipeY)/400;
				downTime=System.currentTimeMillis();
			} else {
				scene.camera().position.x-=(swipeX-prevSwipeX);
				scene.camera().position.y+=(swipeY-prevSwipeY);
			}
			prevSwipeX=swipeX;
			prevSwipeY=swipeY;
			
			break;
		}

		
		// Camera pivot point is different 
//		prevSwipeY = event.getX();
		return super.onTouchEvent(event);
	}
	
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
		
		MMElement n4=new MMElement();
		n4.setName("n4");
		m1.addElement(n4, root);
		
		MMElement n5=new MMElement();
		n5.setName("n5");
		m1.addElement(n5, root);
		
		MMElement n6=new MMElement();
		n6.setName("n6");
		m1.addElement(n6, root);
		
		
		//******************** child group 1***************************
		MMElement n61=new MMElement();
		n61.setName("n61");
		m1.addElement(n61, n6);
		
		MMElement n62=new MMElement();
		n62.setName("n62");
		m1.addElement(n62, n6);
		//**************************************************************
		
		//******************* child group 2****************************
		
		MMElement n51=new MMElement();
		n51.setName("n51");
		m1.addElement(n51, n5);
		

		MMElement n52=new MMElement();
		n52.setName("n52");
		m1.addElement(n52, n5);
		
		MMElement n53=new MMElement();
		n53.setName("n53");
		m1.addElement(n53, n5);	
		//*******************************
		
		//******************** child group 3 ****************************
		MMElement n311=new MMElement();
		n311.setName("n311");
		m1.addElement(n311, n61);
		
		MMElement n312=new MMElement();
		n312.setName("n312");
		m1.addElement(n312, n61);
		
		MMElement n313=new MMElement();
		n313.setName("n313");
		m1.addElement(n313, n61);
		
		MMElement n321=new MMElement();
		n321.setName("n321");
		m1.addElement(n321, n62);
		
		MMElement n322=new MMElement();
		n322.setName("n322");
		m1.addElement(n322, n62);
		
		MMElement n323=new MMElement();
		n323.setName("n323");
		m1.addElement(n323, n62);
		
		//***********************************************
		
		//*********************** child group 4 ******************************

		
		MMElement n111=new MMElement();
		n111.setName("n111");
		m1.addElement(n111, n62);
		
		MMElement n112=new MMElement();
		n112.setName("n112");
		m1.addElement(n112, n62);
		//**************************************************************************
		
		//**************** child group 5**************
		
		MMElement n41=new MMElement();
		n41.setName("n41");
		m1.addElement(n41, n4);
		
		MMElement n31=new MMElement();
		n31.setName("n31");
		m1.addElement(n31, n3);
		
		MMElement n32=new MMElement();
		n32.setName("n32");
		m1.addElement(n32, n3);
		
		MMElement n21=new MMElement();
		n21.setName("n21");
		m1.addElement(n21, n2);
		
		MMElement n22=new MMElement();
		n22.setName("n22");
		m1.addElement(n22, n2);
		
		
		MindMath mm1=new MindMath(5);
		mm1.positionGenerate(root);
		
		addAllNodes(root,null);
	}
	
	private void addAllNodes(MMElement e1,Node parent){
		
		Node n1;
		if(parent==null){
			n1=new Node(black);
			scene.addChild(n1);
		} else{
			n1=new Node(parent);
			n1.position().setAll((float) e1.getPosition().getX(), (float) e1.getPosition().getZ(),(float) e1.getPosition().getY());
//			Log.v("coords",e1.getPosition().getX()+","+e1.getPosition().getZ()+","+e1.getPosition().getY());
			n1.buildParentLink();
			scene.addChild(n1);
			scene.addChild(n1.getParentLink());
		}
		
//		System.out.println(e1.getName());
//		System.out.println(e1.getPosition().getX()+","+e1.getPosition().getY()+","+e1.getPosition().getZ()+"\n");
		for(MMElement c1:e1.getChildren()){
			addAllNodes(c1,n1);
		}
	}
	

}
