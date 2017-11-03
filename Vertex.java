package directedGraph;

import java.util.HashMap;

public class Vertex {
	long idNum;
	String label;
	HashMap<String, Edge> outEdge;
	HashMap<String, Edge> inEdge;
	int inDegree;
	boolean known;
	long dist;
	Vertex path;
	
	public Vertex(String newLabel, long newID){
		label = newLabel;
		idNum = newID;
		outEdge = new HashMap<String, Edge>();
		inEdge = new HashMap<String, Edge>();
		inDegree = 0;//Maybe -1?
	}
	
}
