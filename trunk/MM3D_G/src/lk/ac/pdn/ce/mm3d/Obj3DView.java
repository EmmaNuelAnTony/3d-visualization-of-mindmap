package lk.ac.pdn.ce.mm3d;

import glfont.GLFont;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

import lk.ac.ce.mm3d.Math.MindMath;
import lk.ac.pdn.ce.mm3d.DataStructure.FileFormat;
import lk.ac.pdn.ce.mm3d.DataStructure.MMElement;
import lk.ac.pdn.ce.mm3d.DataStructure.MapData;
import lk.ac.pdn.ce.mm3d.DataStructure.XMLFile;
import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.threed.jpct.Camera;
import com.threed.jpct.Config;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Interact2D;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

enum OpMode {
	ROTATE, ADD, DELETE, ZOOM
}

public class Obj3DView extends Activity {

	private MapData map;// mind map object
	private MindMath mm1;// mind math object. use for calculations
	private AndObj andMap;// android 3D map object
	private Node touchedNode;// selected node

	/* 3d Elements */
	private Camera cam;
	private Light sun = null;
	private GLSurfaceView mGLView;
	private MyRenderer renderer = null;
	private FrameBuffer fb = null;
	private World world = null;

	// Basic colors
	private RGBColor back = new RGBColor(50, 50, 100);
	RGBColor red = new RGBColor(255, 0, 0, 255);
	RGBColor green = new RGBColor(0, 255, 0, 255);
	RGBColor blue = new RGBColor(0, 0, 255, 255);

	public OpMode currentMode = OpMode.ROTATE;// current operation mode

	// Used to handle pause and resume...
	private static Obj3DView master = null;
	// Used to handle touch operation
	private float prevSwipeY, swipeY, prevSwipeX, swipeX;
	private long downTime, actionTime;

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
				(int) event.getX(), (int) event.getY() - 60).normalize();
		Object[] res = world.calcMinDistanceAndObject3D(cam.getPosition(), dir,
				99999);

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			for (Object ob : res) {
				if (ob != null && (ob instanceof Node)) {
					Log.v("object", ob.getClass().toString());
					touchedNode = (Node) ob;
					SimpleVector s1 = touchedNode.getOrigin();
					s1.x += 50;
					s1.y += 50;
					s1.z += 50;
					if (touchedNode != null) {
						cam.lookAt(touchedNode.getOrigin());
					}
					if (currentMode == OpMode.ADD) {
						// cam.moveCamera(touchedNode.getCenter(), 100);
						// Switch to add node window
						setContentView(R.layout.layout1);
						Button b1 = (Button) findViewById(R.id.buttonAdd);
						b1.setOnClickListener(new View.OnClickListener() {

							public void onClick(View v) {
								EditText name = (EditText) findViewById(R.id.textName);
								String tmpName = name.getText().toString();
								MMElement e1 = new MMElement();
								e1.setName(tmpName);
								map.addElement(e1, touchedNode.getMmElement());
								mm1.positionGenerate(map.getRoot());
								world.removeAllObjects();
								andMap = new AndObj(map, world);
								setContentView(mGLView);
								// Back to 3D view
							}
						});
					} else if (currentMode == OpMode.DELETE) {
						if (touchedNode != null
								&& touchedNode!=andMap.getRootNode()) {
							map.removeElement(touchedNode.getMmElement());
							mm1.positionGenerate(map.getRoot());
							world.removeAllObjects();
							andMap = new AndObj(map, world);
							touchedNode = andMap.getRootNode();
						}
					}

				}
			}
			prevSwipeX = event.getX();
			prevSwipeY = event.getY();
			downTime = System.currentTimeMillis();
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			actionTime = System.currentTimeMillis() - downTime;

			if (actionTime < 1000) {
				// cam.setPosition(touchedNode.getOrigin());
			}
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE) {

			swipeX = event.getX();
			swipeY = event.getY();
			actionTime = System.currentTimeMillis() - downTime;
			// if (actionTime < 1000) { // touch and move, move the camera
			//
			// if ((swipeX - prevSwipeX) > 5 || (swipeY - prevSwipeY) > 5) {
			// // cam.setPosition(campos.x - (swipeX - prevSwipeX) / 40,
			// // campos.y + (swipeY - prevSwipeY) / 400, campos.z);
			// downTime = System.currentTimeMillis();
			// }
			// } else { // touch and hold for 3sec and then move, rotate the
			// camera
			if (currentMode == OpMode.ROTATE) {
				if (Math.abs(swipeX - prevSwipeX) > Math.abs(swipeY
						- prevSwipeY)) {
					if (touchedNode != null) {
						cam.setPosition(touchedNode.getOrigin());
						cam.rotateCameraY((swipeX - prevSwipeX) / 100f);
						cam.moveCamera(Camera.CAMERA_MOVEOUT, 100f);
						cam.lookAt(touchedNode.getOrigin());
					}
				} else {
					if (touchedNode != null) {
						cam.setPosition(touchedNode.getOrigin());
						cam.rotateCameraX((swipeY - prevSwipeY) / 100f);
						cam.moveCamera(Camera.CAMERA_MOVEOUT, 100f);
						cam.lookAt(touchedNode.getOrigin());
					}
				}
			} else if (currentMode == OpMode.ZOOM) {
				if (swipeY > prevSwipeY) {
					cam.moveCamera(Camera.CAMERA_MOVEOUT, 1f);
				} else {
					cam.moveCamera(Camera.CAMERA_MOVEIN, 1f);
				}

			}
			// }
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

	/*
	 * 
	 * Render Class
	 */

	class MyRenderer implements GLSurfaceView.Renderer {
		GLFont glFont;
		Paint paint = new Paint();

		public MyRenderer() {
			paint.setTypeface(Typeface.create((String) null, Typeface.BOLD));
			paint.setTextSize(12);
			glFont = new GLFont(paint);
			paint.setTextSize(50);
		}

		public void onSurfaceChanged(GL10 gl, int w, int h) {
			if (fb != null) {
				fb.dispose();
			}
			fb = new FrameBuffer(gl, w, h);

			if (master == null) {
				Config.glDebugLevel = 0;
				world = new World();
				world.setAmbientLight(20, 20, 20);

				sun = new Light(world);
				sun.setIntensity(250, 250, 250);

				cam = world.getCamera();
				cam.moveCamera(Camera.CAMERA_MOVEOUT, 200);
				cam.lookAt(new SimpleVector(0, 0, 0));

				SimpleVector sv1 = new SimpleVector();
				sv1.set(new SimpleVector(0, 0, 0));
				sv1.y -= 100;
				sv1.z -= 100;
				sun.setPosition(sv1);

				/* Loding mindmap goes here */
				test();

				// finished loading
				MemoryHelper.compact();

				if (master == null) {
					Logger.log("Saving master Activity!");
					master = Obj3DView.this;
				}
			}
		}

		private void test() {
			map = new MapData("test map");
			MMElement root = map.getRoot();

			MMElement n1 = new MMElement();
			n1.setName("n1");
			map.addElement(n1, root);

			MMElement n2 = new MMElement();
			n2.setName("n2");
			map.addElement(n2, root);

			MMElement n3 = new MMElement();
			n3.setName("n3");
			map.addElement(n3, root);

			MMElement n4 = new MMElement();
			n4.setName("n4");
			map.addElement(n4, root);

			MMElement n5 = new MMElement();
			n5.setName("n5");
			map.addElement(n5, root);

			MMElement n6 = new MMElement();
			n6.setName("n6");
			map.addElement(n6, root);

			MMElement n7 = new MMElement();
			n7.setName("n7");
			map.addElement(n7, root);

			MMElement n8 = new MMElement();
			n8.setName("n8");
			map.addElement(n8, root);
			// // ******************** child group 1***************************
			MMElement n61 = new MMElement();
			n61.setName("n61");
			map.addElement(n61, n6);

			// // **************************************************************
			//
			// // ******************* child group 2****************************

			MMElement n51 = new MMElement();
			n51.setName("n51");
			map.addElement(n51, n5);

			MMElement n52 = new MMElement();
			n52.setName("n52");
			map.addElement(n52, n5);

			MMElement n53 = new MMElement();
			n53.setName("n53");
			map.addElement(n53, n5);
			// *******************************

			// ******************** child group 3
			// ****************************
			MMElement n311 = new MMElement();
			n311.setName("n311");
			map.addElement(n311, n61);

			MMElement n312 = new MMElement();
			n312.setName("n312");
			map.addElement(n312, n61);

			MMElement n313 = new MMElement();
			n313.setName("n313");
			map.addElement(n313, n61);

			// // **************** child group 5**************

			MMElement n41 = new MMElement();
			n41.setName("n41");
			map.addElement(n41, n4);

			MMElement n31 = new MMElement();
			n31.setName("n31");
			map.addElement(n31, n3);

			MMElement n32 = new MMElement();
			n32.setName("n32");
			map.addElement(n32, n3);

			MMElement n21 = new MMElement();
			n21.setName("n21");
			map.addElement(n21, n2);

			MMElement n22 = new MMElement();
			n22.setName("n22");
			map.addElement(n22, n2);

			mm1 = new MindMath(25);
			mm1.positionGenerate(root);

			andMap = new AndObj(map, world);
			touchedNode = andMap.getRootNode();
			// writeXML();

			// readXML();
			// root = m1.getRoot();
			// mm1 = new MindMath(35);
			// mm1.positionGenerate(root);
			// addAllNodes(root, null);
		}

		/**
		 * Write the mind map to XML file
		 */
		public void writeXML() {
			try {
				FileFormat xmlformat = new XMLFile();
				String xml = xmlformat.writeFormat(map);
				File file = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath(), "mmap.xml");
				FileOutputStream fOut = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(fOut);

				osw.write(xml);

				osw.close();
				osw.flush();
				fOut.close();
				fOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * read map from XML file
		 */
		public void readXML() {
			try {

				File f = new File(Environment.getExternalStorageDirectory()
						+ "/mmap.xml");
				FileInputStream fileIS = new FileInputStream(f);
				BufferedReader buf = new BufferedReader(new InputStreamReader(
						fileIS));
				String readString = new String();
				String xmlString = new String();
				// just reading each line and pass it on the debugger
				while ((readString = buf.readLine()) != null) {
					xmlString += readString;

				}
				FileFormat xmlformat = new XMLFile();
				xmlformat.readFormat(xmlString, map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		}

		public void onDrawFrame(GL10 gl) {
			// this method has to be very fast.Optimize as much as possible
			// fb.freeMemory();
			try {
				fb.clear(back);
				world.renderScene(fb);
				world.draw(fb);
			} catch (Exception e) {
				// Log.v("working", "new node adding still in process");
			}
			// printing text will go here
			displayCaptions();
			fb.display();
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				// No need for this...
			}
		}

		private void displayCaptions() {
			// fonts
			MMElement root = map.getRoot();
			addCaptionToANode(root, glFont);

		}

		private void addCaptionToANode(MMElement e1, GLFont glFont) {

			SimpleVector pos = new SimpleVector(e1.getPosition().getX(), e1
					.getPosition().getY(), e1.getPosition().getZ());
			SimpleVector dir = Interact2D.project3D2D(cam, fb, pos);

			if (dir != null) {
				if (touchedNode != null && touchedNode.getMmElement() == e1) {
					glFont.blitString(fb, e1.getName(), (int) dir.x,
							(int) dir.y, (int) dir.z + 10, RGBColor.GREEN);
				} else {
					glFont.blitString(fb, e1.getName(), (int) dir.x,
							(int) dir.y, (int) dir.z + 10, RGBColor.WHITE);
				}
			}
			for (MMElement c1 : e1.getChildren()) {
				addCaptionToANode(c1, glFont);
			}
		}
	}
}