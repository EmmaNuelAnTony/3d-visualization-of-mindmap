package lk.ac.pdn.ce.mindmap3d.DataStructure;

/**
 * Represent single element in mindmap
 * @author Pasindu Pavithra Ariyarathna
 *
 */
public class MMElement {
	private String id;
	private String name;
	private Position position;
	private MMElement parent;
	private MMElement[] children;
}
