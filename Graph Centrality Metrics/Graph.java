import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
	private ArrayList<Integer> vertices; // to keep vertex names
	private int[][] adjacency; // to keep edges
	private int size;
	private int[] previous;
	private int[] betweenness;
	private int[] closeness;

	public Graph(int size) {
		vertices = new ArrayList<Integer>();
		adjacency = new int[size][size];
		this.size = size;
		previous = new int[size + 1];
		betweenness = new int[size];
		closeness = new int[size];
	}

	public void addEdge(int source, int destination) {

		if (!vertices.contains(source))
			vertices.add(source);
		if (!vertices.contains(destination))
			vertices.add(destination);

		int source_index = vertices.indexOf(source);
		int destination_index = vertices.indexOf(destination);
		adjacency[source_index][destination_index] = 1;
		adjacency[destination_index][source_index] = 1;
	}

	public int size() {
		return this.size;
	}

	public int[][] getAdjacency() {
		return adjacency;
	}

	public ArrayList<Integer> getVertices() {
		return vertices;
	}

	public void BFS(int start, int end) {
		Arrays.fill(previous, -1);
		Queue<Integer> queue = new LinkedList<>();
		int root = vertices.indexOf(start);

		queue.add(start);
		int[] visited = new int[size];
		visited[root] = 1;

		while (!queue.isEmpty()) {
			int current_vertex = queue.poll(); // the top element is selected and removed from queue

			int neighbour_index;
			while ((neighbour_index = unvisitedNeighbour(vertices.indexOf(current_vertex), visited)) != -1) {

				int neighbour = vertices.get(neighbour_index);
				queue.add(neighbour);
				visited[neighbour_index] = 1;

				previous[neighbour] = current_vertex;
				if (neighbour == end) {
					queue.clear();
					break;
				}
			}
		}
		trace_route(end);
	}

	public int unvisitedNeighbour(int index, int[] visited) {
		
		for (int i = 0; i < adjacency.length; i++) {
			if (adjacency[index][i] != 0 && visited[i] == 0)
				return i;
		}
		return -1;
	}

	private void trace_route(int end) {
		int node = end;
		List<Integer> route = new ArrayList<>();
		
		while (node != -1) {
			route.add(node);
			node = previous[node];
		}
		
		Collections.reverse(route);

		//Update Betweenness
		for (int i = 1; i < route.size() - 1; i++) {
			int index = vertices.indexOf(route.get(i));
			betweenness[index] += 1;
		}
		
		//Update Closeness
		int index = vertices.indexOf(route.get(0));
		closeness[index] += (route.size()-1);
	}

	public void betweenness() {
		int maximum_betweenness = -1;
		int max_betweenness_index = -1;

		for (int i = 0; i < size; i++) {
			if (betweenness[i] > maximum_betweenness) {
				maximum_betweenness = betweenness[i];
				max_betweenness_index = i;
			}
		}
		System.out.println(vertices.get(max_betweenness_index) + " - value " + maximum_betweenness);
	}

	public void closeness() {
		double maximum_closeness = -1;
		int max_closeness_index = -1;
		
		for (int i = 0; i < size; i++) {
			if (betweenness[i] > maximum_closeness) {
				maximum_closeness = closeness[i];
				max_closeness_index = i;
			}
		}
		System.out.println(vertices.get(max_closeness_index) + " - value " + 1/maximum_closeness);
	}
}
