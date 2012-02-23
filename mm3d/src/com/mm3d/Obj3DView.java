package com.mm3d;


import min3d.core.RendererActivity;
import min3d.objectPrimitives.*;
import min3d.vos.Color4;
import min3d.vos.Light;
import android.os.Bundle;

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
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}
	
	private Sphere mSphere;
	private HollowCylinder hc;
	
	public void initScene()
	{
		scene.lights().add(new Light());
		scene.backgroundColor().setAll(new Color4(255,15,35,100));
		

		
		mSphere=new Sphere(0.5f,10,10,new Color4(255,0,0,100));
		scene.addChild(mSphere);
		
		Sphere sp2=new Sphere(0.5f,10,10,new Color4(255,15,35,100));
		sp2.position().x=-1.5f;
		scene.addChild(sp2);
		
		Sphere sp3=new Sphere(0.5f,10,10,new Color4(255,15,35,100));
		sp3.position().x=1.5f;
		scene.addChild(sp3);
		
		Sphere sp4=new Sphere(0.5f,10,10,new Color4(255,15,35,100));
		sp4.position().y=1.5f;
		scene.addChild(sp4);
		
		Sphere sp5=new Sphere(0.5f,10,10,new Color4(255,15,35,100));
		sp5.position().y=-1.5f;
		scene.addChild(sp5);
		
		hc=new HollowCylinder(0.05f,0.04f,3f,40);
		hc.rotation().y=90;
		hc.rotation().x=90;
		scene.addChild(hc);
		
		HollowCylinder hc2=new HollowCylinder(0.05f,0.04f,3f,40);
		hc2.rotation().x=90;
		scene.addChild(hc2);
		
		
	}
	
	public void updateScene() {
		if(scene.camera().position.x>10){
			scene.camera().position.x=0;
		}
		if(scene.camera().position.y>10){
			scene.camera().position.y=0;
		}
		scene.camera().position.x+=0.01f;
		scene.camera().position.y+=0.01f;

	}
}
