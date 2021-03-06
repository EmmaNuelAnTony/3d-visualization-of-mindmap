package lk.ac.pdn.ce.mm3d;

import lk.ac.pdn.ce.mm3d.DataStructure.MMElement;
import lk.ac.pdn.ce.mm3d.DataStructure.MapData;

import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

public class AndObj {
	private Node rootNode;// 3D node object
	private RGBColor black = new RGBColor(0, 0, 0, 255);

	public AndObj(MapData map, World world) {
		addAllNodes(map.getRoot(), null, world, black);
	}

	public Node getRootNode() {
		return rootNode;
	}

	public void setRootNode(Node rootNode) {
		this.rootNode = rootNode;
	}

	public void addAllNodesToMap(MapData map, World world){
		addAllNodes(map.getRoot(), null, world, black);
	}
	
	
	private void addAllNodes(MMElement e1, Node parent, World world, RGBColor col) {
		Node n1 = addNewNode(e1, parent, world, col);
		RGBColor nc = new RGBColor((int) (Math.random() * 255),
				(int) (Math.random() * 255), (int) (Math.random() * 255));
		for (MMElement c1 : e1.getChildren()) {
			addAllNodes(c1, n1, world, nc);
		}
	}

	private Node addNewNode(MMElement e1, Node parent, World world,
			RGBColor color) {
		if (parent == null) {
			rootNode = new Node(color);
			rootNode.setOrigin(new SimpleVector(0, 0, 0));
			rootNode.setMmElement(e1);
			rootNode.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
			rootNode.strip();
			rootNode.build();
			world.addObject(rootNode);
			return rootNode;
		} else {
			Node n1 = new Node(parent, color);
			n1.setOrigin(new SimpleVector((float) e1.getPosition().getX(),
					(float) e1.getPosition().getY(), (float) e1.getPosition()
							.getZ()));
			n1.setMmElement(e1);
			n1.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
			n1.strip();
			world.addObject(n1);
			n1.buildParentLink();
			world.addObject(n1.getParentLink());
			return n1;
		}
	}

}
