package directedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;


public class DiGraph implements DiGraph_Interface {
	int numNodes, numEdges;
	HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
	Set<Long> vertexId = new HashSet<Long>();
	Set<Long> edgeId = new HashSet<Long>();

	public DiGraph ( ) {
		numNodes = 0;
		numEdges = 0;
	}

	public boolean addNode(long idNum, String label) {
		if(idNum < 0 || vertices.containsKey(label) || vertexId.contains(idNum)){
			return false;
		}
		vertices.put(label, new Vertex(label, idNum));
		vertexId.add(idNum);
		numNodes++;
		return true;
	}

	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		if(idNum < 0 || edgeId.contains(idNum)){
			return false;
		}
		Vertex sourceNode = vertices.get(sLabel);
		Vertex destinationNode = vertices.get(dLabel);
		if(sourceNode == null || destinationNode == null){
			return false;
		}
		if(destinationNode.inDegree > 0){
			for(Edge outEdge: sourceNode.outEdge.values()){
				if(outEdge.destination.label.equals(dLabel)){
					return false;
				}
			}
		}
		Edge toAdd = new Edge(eLabel, idNum, sourceNode, destinationNode, weight);
		edgeId.add(idNum);
		sourceNode.outEdge.put(dLabel, toAdd);
		destinationNode.inEdge.put(sLabel, toAdd);
		destinationNode.inDegree++;
		numEdges++;
		return true;
	}

	public boolean delNode(String label) {
		Vertex toDelete = vertices.get(label);
		if(numNodes < 1 || toDelete == null){
			return false;
		}
		for(Edge out: toDelete.outEdge.values()){
			delEdge(label, out.destination.label);
		}
		for(Edge in: toDelete.inEdge.values()){
			delEdge(in.destination.label, label);
		}
		vertices.remove(label);
		vertexId.remove(toDelete.idNum);
		toDelete = null;
		numNodes--;
		return true;
	}

	public boolean delEdge(String sLabel, String dLabel) {
		Vertex source = vertices.get(sLabel);
		Vertex destination = vertices.get(dLabel);
		if(source == null || destination == null || !(source.outEdge.containsKey(dLabel))){
			return false;
		}
		edgeId.remove(source.outEdge.get(dLabel).idNum);
		source.outEdge.remove(dLabel);
		destination.inEdge.remove(sLabel);
		destination.inDegree--;
		numEdges--;
		return true;
	}

	public long numNodes() {
		return numNodes;
	}

	public long numEdges() {
		return numEdges;
	}

	public String[] topoSort() {
		String[] toReturn = new String[numNodes];
		Queue<Vertex> queue = new LinkedList<Vertex>();
		int i = 0;
		
		for(Vertex v: vertices.values()){
			if(v.inDegree == 0){
				queue.add(v);
			}
		}
				
		while(!(queue.isEmpty())){
			Vertex v = queue.poll();
			toReturn[i] = v.label;
			i++;
			for(String s: v.outEdge.keySet()){
				Vertex aV = vertices.get(s);
				if(--aV.inDegree == 0){
					queue.add(aV);
				}
			}
		}
		if(i != this.numNodes || i == 0){
			return null;
		}
		return toReturn;
	}
	
	/*
	 * Dijkstra's algorithm
	 */
	public ShortestPathInfo[] shortestPath(String label) {
		Vertex s = vertices.get(label);
		
		for(Vertex v: vertices.values()){
			v.dist = Long.MAX_VALUE;
			v.known = false;
		}
		
		s.dist = 0;
		
		MinBinHeap queue = new MinBinHeap();
		queue.insert(new EntryPair(s, 0));
		
		while(queue.size() > 0){
			Vertex n = queue.getMin().value;
			queue.delMin();
			
			if(!n.known){
				n.known = true;
				for(Edge aEdge: n.outEdge.values()){
					Vertex a = aEdge.destination;
					if(!a.known){
						if(a.dist > n.dist + aEdge.weight){
							a.dist = n.dist + aEdge.weight;
							queue.insert(new EntryPair(a, a.dist));
						}
					}
				}
			}
		}
		
		int i = 0;
		ShortestPathInfo[] toPrint = new ShortestPathInfo[numNodes];
		for(Vertex p: vertices.values()){
			if(p.dist == Long.MAX_VALUE) p.dist = -1;
			toPrint[i] = new ShortestPathInfo(p.label, p.dist);
			i++;
		}	
		return toPrint;
	}
	
	
}