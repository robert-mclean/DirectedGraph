package directedGraph;

public class Edge {
	Vertex source, destination;
	long weight;
	long idNum;
	String label;
	
	public Edge(String labelIn, long idIn, Vertex sourceIn, Vertex destinationIn, long weightIn){
		label = labelIn;
		idNum = idIn;
		source = sourceIn;
		destination = destinationIn;
		weight = weightIn;
	}

}
