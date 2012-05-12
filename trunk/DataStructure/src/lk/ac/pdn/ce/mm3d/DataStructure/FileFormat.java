package lk.ac.pdn.ce.mm3d.DataStructure;

public interface FileFormat {
	
	/**
	 * To read the specific format
	 * @param content content String content
	 * @param map map object of the map
	 * @throws Exception 
	 */
	public void readFormat(String content,MapData map) throws Exception;
	
	/**
	 * To write a map into specific format
	 * @param map map object of the map	 
	 * @return the string output of the format
	 * @throws Exception 
	 */
	public String writeFormat(MapData map) throws Exception;

}
