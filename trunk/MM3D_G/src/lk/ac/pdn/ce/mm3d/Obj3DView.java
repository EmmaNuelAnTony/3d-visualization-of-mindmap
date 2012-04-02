package lk.ac.pdn.ce.mm3d;

import glfont.*;
import java.lang.reflect.Field;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

import lk.ac.ce.mm3d.Math.MindMath;
import lk.ac.pdn.ce.mm3d.DataStructure.MMElement;
import lk.ac.pdn.ce.mm3d.DataStructure.MapData;
import lk.ac.pdn.ce.mm3d.DataStructure.Position;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Interact2D;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.MemoryHelper;
import com.threed.jpct.Config;

public class Obj3DView extends Activity {

	// Used to handle pause and resume...
	private static Obj3DView master = null;

	private GLSurfaceView mGLView;
	private MyRenderer renderer = null;
	private FrameBuffer fb = null;
	private World world = null;
	private RGBColor back = new RGBColor(50, 50, 100);

	private float touchTurn = 0;
	private float touchTurnUp = 0;

	private float xpos = -1;
	private float ypos = -1;

	private Camera cam;
	private Light sun = null;

	// Used to handle touch operation
	private float prevSwipeY, swipeY, prevSwipeX, swipeX, prevSwipeZ, swipeZ;
	private long downTime, actionTime;

	// Used to handle mind map structure
	private MapData m1;
	private Node rootNode;
	private MindMath mm1;
	private MMElement root;
	private Node touchedNode = null;

	// Basic colors
	RGBColor black = new RGBColor(0, 0, 0, 255);
	RGBColor red = new RGBColor(255, 0, 0, 255);
	RGBColor green = new RGBColor(0, 255, 0, 255);
	RGBColor blue = new RGBColor(0, 0, 255, 255);

	protected void onCreate(Bundle savedInstanceState) {

		Logger.log("onCreate");

		if (master != null) {
			copy(master);
		}

		super.onCreate(savedInstanceState);
		mGLView = new GLSurfaceView(getApplication());

		mGLView.setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() {
			public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
				// Ensure that we get a 16bit framebuffer. Otherwise, we'll fall
				// back to Pixelflinger on some device (read: Samsung I7500)
				int[] attributes = new int[] { EGL10.EGL_DEPTH_SIZE, 16,
						EGL10.EGL_NONE };
				EGLConfig[] configs = new EGLConfig[1];
				int[] result = new int[1];
				egl.eglChooseConfig(display, attributes, configs, 1, result);
				return configs[0];
			}
		});

		renderer = new MyRenderer();
		mGLView.setRenderer(renderer);
		setContentView(mGLView);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGLView.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private void copy(Object src) {
		try {
			Logger.log("Copying data from master Activity!");
			Field[] fs = src.getClass().getDeclaredFields();
			for (Field f : fs) {
				f.setAccessible(true);
				f.set(this, f.get(src));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean onTouchEvent(MotionEvent event) {

		SimpleVector dir = Interact2D.reproject2D3DWS(cam, fb,
				(int) event.getX(), (int) event.getY()-60).normalize();
		Object[] res = world.calcMinDistanceAndObject3D(cam.getPosition(), dir,
				99999);

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			for (Object ob : res) {
				 if(ob!=null && (ob instanceof Node)){
					 Log.v("object", ob.getClass().toString());
					 touchedNode=(Node) ob;
					 world.removeAllObjects();
					 
					 Position temp1=touchedNode.getMmElement().getPosition();
					 Position temp2=root.getPosition();
					 MMElement n1 = new MMElement();
					 n1.setName("generated");
					 root=m1.getRoot();
					 if((temp1.getX()==temp2.getX())&&(temp1.getY()==temp2.getY())&&(temp1.getZ()==temp2.getZ())){
						 m1.addElement(n1, root);
					 } else {
						 m1.addElement(n1,touchedNode.getMmElement());
					 }
					 mm1.positionGenerate(root);
					 renderer.addAllNodes(root, null);
				 }
			}

			prevSwipeX = event.getX();
			prevSwipeY = event.getY();
			downTime = System.currentTimeMillis();
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			actionTime = System.currentTimeMillis() - downTime;

			if (actionTime < 3000) {

			}
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE) {

			swipeX = event.getX();
			swipeY = event.getY();
			float a = swipeY - prevSwipeY;
			float b = swipeX - prevSwipeX;

			actionTime = System.currentTimeMillis() - downTime;

			SimpleVector campos = cam.getPosition();
			if (actionTime < 1000) { // touch and move, move the camera

				if ((swipeX - prevSwipeX) > 5 || (swipeY - prevSwipeY) > 5) {
					cam.setPosition(campos.x - (swipeX - prevSwipeX) / 40,
							campos.y + (swipeY - prevSwipeY) / 400, campos.z);
					downTime = System.currentTimeMillis();
				}
			} else { // touch and hold for 3sec and then move, rotate the camera
				cam.setPosition(campos.x - (swipeX - prevSwipeX), campos.y
						+ (swipeY - prevSwipeY), campos.z);
			}
			cam.lookAt(rootNode.getOrigin());
			prevSwipeX = swipeX;
			prevSwipeY = swipeY;
			return true;
		}

		try {
			Thread.sleep(15);
		} catch (Exception e) {
			// No need for this...
		}
		return super.onTouchEvent(event);
	}

	protected boolean isFullscreenOpaque() {
		return true;
	}

	public Object3D drawSphere(double x, double y, double z, int radious,
			RGBColor color) {
		Object3D cube1 = Primitives.getSphere(radious);
		cube1.strip();
		cube1.build();

		cube1.setOrigin(new SimpleVector(x, y, z));
		cube1.setAdditionalColor(color);
		world.addObject(cube1);
		return cube1;
	}

	class MyRenderer implements GLSurfaceView.Renderer {

		private boolean stop = false;
		private boolean stopNextTime = false;

		private long time = System.currentTimeMillis();

		public synchronized void stopNextTime() {
			stopNextTime = true;
		}

		public synchronized void resume() {
			stop = false;
		}

		public synchronized void stop() {
			stop = true;
		}

		public MyRenderer() {
		}

		public void onSurfaceChanged(GL10 gl, int w, int h) {
			if (fb != null) {
				fb.dispose();
			}
			fb = new FrameBuffer(gl, w, h);

			if (master == null) {
				Config.glDebugLevel=0;
				world = new World();
				world.setAmbientLight(20, 20, 20);

				sun = new Light(world);
				sun.setIntensity(250, 250, 250);

				cam = world.getCamera();
				cam.moveCamera(Camera.CAMERA_MOVEOUT, 200);
				cam.lookAt(new SimpleVector(0,0,0));

				SimpleVector sv1 = new SimpleVector();
				sv1.set(new SimpleVector(0,0,0));
				sv1.y -= 100;
				sv1.z -= 100;
				sun.setPosition(sv1);
				
				//Loding mindmap goes here
				test();
				//finished loading
				MemoryHelper.compact();

				if (master == null) {
					Logger.log("Saving master Activity!");
					master = Obj3DView.this;
				}
			}
		}

		public void test() {
			m1 = new MapData("test map");
			root = m1.getRoot();

			MMElement n1 = new MMElement();
			n1.setName("n1");
			m1.addElement(n1, root);

			MMElement n2 = new MMElement();
			n2.setName("n2");
			m1.addElement(n2, root);

			MMElement n3 = new MMElement();
			n3.setName("n3");
			m1.addElement(n3, root);

			MMElement n4 = new MMElement();
			n4.setName("n4");
			m1.addElement(n4, root);

			MMElement n5 = new MMElement();
			n5.setName("n5");
			m1.addElement(n5, root);

			MMElement n6 = new MMElement();
			n6.setName("n6");
			m1.addElement(n6, root);
			
			MMElement n7 = new MMElement();
			n7.setName("n7");
			m1.addElement(n7, root);
			
			MMElement n8 = new MMElement();
			n8.setName("n8");
			m1.addElement(n8, root);
			// ******************** child group 1***************************
			MMElement n61 = new MMElement();
			n61.setName("n61");
			m1.addElement(n61, n6);

			
			// **************************************************************

			// ******************* child group 2****************************

			MMElement n51 = new MMElement();
			n51.setName("n51");
			m1.addElement(n51, n5);

			MMElement n52 = new MMElement();
			n52.setName("n52");
			m1.addElement(n52, n5);

			MMElement n53 = new MMElement();
			n53.setName("n53");
			m1.addElement(n53, n5);
			// *******************************

			// ******************** child group 3 ****************************
			MMElement n311 = new MMElement();
			n311.setName("n311");
			m1.addElement(n311, n61);

			MMElement n312 = new MMElement();
			n312.setName("n312");
			m1.addElement(n312, n61);

			MMElement n313 = new MMElement();
			n313.setName("n313");
			m1.addElement(n313, n61);



			// **************** child group 5**************

			MMElement n41 = new MMElement();
			n41.setName("n41");
			m1.addElement(n41, n4);

			MMElement n31 = new MMElement();
			n31.setName("n31");
			m1.addElement(n31, n3);

			MMElement n32 = new MMElement();
			n32.setName("n32");
			m1.addElement(n32, n3);

			MMElement n21 = new MMElement();
			n21.setName("n21");
			m1.addElement(n21, n2);

			MMElement n22 = new MMElement();
			n22.setName("n22");
			m1.addElement(n22, n2);

			mm1 = new MindMath(25);
			mm1.positionGenerate(root);

			addAllNodes(root, null);
		}


		public void addAllNodes(MMElement e1, Node parent) {

			Node n1 = addNewNode(e1, parent);

			for (MMElement c1 : e1.getChildren()) {
				addAllNodes(c1, n1);
			}
		}

		private Node addNewNode(MMElement e1, Node parent) {

			if (parent == null) {
				rootNode = new Node(black);
				rootNode.setOrigin(new SimpleVector(0, 0, 0));
				rootNode.setMmElement(e1);
				rootNode.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
				rootNode.strip();
				rootNode.build();
				world.addObject(rootNode);
				return rootNode;
			} else {
				Node n1 = new Node(parent);
				n1.setOrigin(new SimpleVector((float) e1.getPosition().getX(),
						(float) e1.getPosition().getY(), (float) e1
								.getPosition().getZ()));
				n1.setMmElement(e1);
				n1.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
				n1.strip();
				world.addObject(n1);
				n1.buildParentLink();
				world.addObject(n1.getParentLink());
				return n1;
			}

		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		}

		public void onDrawFrame(GL10 gl) {
			fb.freeMemory();
			fb.clear(back);
			try{
				world.renderScene(fb);
			
				world.draw(fb);
			} catch (Exception e){
				Log.v("working", "new node adding still in process");
			}
			// fonts
			// GLFont glFont;
			// Paint paint = new Paint();
			// paint.setAntiAlias(true);
			// paint.setTypeface(Typeface.create((String)null,
			// Typeface.BOLD));

			// paint.setTextSize(16);
			// glFont = new GLFont(paint);

			// paint.setTextSize(50);

			// glFont.blitString(fb, "HI", 5, 15, 10, RGBColor.WHITE);
			// glFont.blitString(fb, "HI", 15, 15, 10, RGBColor.WHITE);
			fb.display();
			try {
				Thread.sleep(30);
			} catch (Exception e) {
				// No need for this...
			}

		}
	}
}
