import util.GraphAlgorithms;
import util.Vertex;
import util.Edge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void addEdge(HashMap<Vertex, Set<Vertex>> graph, Vertex from, Vertex to) {
        if(!graph.containsKey(from))
            graph.put(from, new HashSet<>());
        graph.get(from).add(to);
        to.inDegree++;
    }

    public static void main(String[] args) {
        HashMap<Vertex, Set<Vertex>> graph = new HashMap<>();
        HashMap<Vertex, Set<Vertex>> testGraph = new HashMap<>(); // ONLY USED FOR TOPOLOGICAL SORTING
        Vertex[] testVertices = new Vertex[]{
                new Vertex("A"),
                new Vertex("B"),
                new Vertex("C"),
                new Vertex("D"),
                new Vertex("E"),
                new Vertex("F")
        }; // ONLY USED FOR TOPOLOGICAL SORTING
        Vertex[] vertices = new Vertex[]{
                new Vertex("v1"),
                new Vertex("v2"),
                new Vertex("v3"),
                new Vertex("v4"),
                new Vertex("v5"),
                new Vertex("v6"),
                new Vertex("v7")
        };

        for(Vertex v : vertices) // add all vertices to graph with no outdegrees/indegrees
            graph.put(v, new HashSet<>());

        for(Vertex v : testVertices) // ONLY USED FOR TOPOLOGICAL SORTING
            testGraph.put(v, new HashSet<>());

        // ONLY USED FOR TOPOLOGICAL SORTING
        addEdge(testGraph, testVertices[0], testVertices[1]); // A -> B
        addEdge(testGraph, testVertices[0], testVertices[2]); // A -> C
        addEdge(testGraph, testVertices[1], testVertices[3]); // B -> D
        addEdge(testGraph, testVertices[1], testVertices[4]); // B -> E
        addEdge(testGraph, testVertices[2], testVertices[5]); // C -> F
        addEdge(testGraph, testVertices[2], testVertices[3]); // C -> D
        addEdge(testGraph, testVertices[3], testVertices[4]); // D -> E
        addEdge(testGraph, testVertices[3], testVertices[5]); // D -> F

        addEdge(graph, vertices[2], vertices[0]);// v3->v1
        addEdge(graph, vertices[2], vertices[5]);// v3->v6
        addEdge(graph, vertices[0], vertices[1]);// v1->v2
        addEdge(graph, vertices[0], vertices[3]);// v1->v4
        addEdge(graph, vertices[1], vertices[4]);// v2->v5
        addEdge(graph, vertices[1], vertices[3]);// v2->v4
        addEdge(graph, vertices[4], vertices[6]);// v5->v7
        addEdge(graph, vertices[6], vertices[5]);// v7->v6
        addEdge(graph, vertices[3], vertices[4]);// v4->v5
        addEdge(graph, vertices[3], vertices[6]);// v4->v7
        addEdge(graph, vertices[3], vertices[5]);// v4->v6
        addEdge(graph, vertices[3], vertices[2]);// v4->v3

        System.out.println("Running BFS iteratively on unweighted graph with 7 vertices\n");
        GraphAlgorithms.bfsIterative(graph, vertices[2], vertices);

        System.out.println("\n\nRunning DFS iteratively on unweighted graph with 7 vertices\n");
        GraphAlgorithms.dfsIterative(graph, vertices[2], vertices);

        System.out.println("\n\nRunning DFS recursively on unweighted graph with 7 vertices\n");
        GraphAlgorithms.dfsIterative(graph, vertices[2], vertices);

        System.out.println("\n\nRunning Topological Sort on unweighted graph with 7 vertices\n");
        Set<Vertex> sorted = GraphAlgorithms.topologicalSort(testGraph, testVertices); // will not work if no node of inDegree 0
        for(Vertex v : sorted) {
            System.out.println(v + ":  " + v.topNum);
        } // had to test using different graph as regular graph has no inDegree of 0

        System.out.println("\nRunning BFS solution on an unweighted graph with 7 vertices\n");
        GraphAlgorithms.bfsSolution(graph, vertices[2], vertices);
        GraphAlgorithms.printSolution(vertices[2], vertices);

        HashMap<Vertex, HashMap<Vertex, Integer>> weightedGraph = new HashMap<>();
        for(Vertex v : vertices)
            weightedGraph.put(v, new HashMap<>());

        weightedGraph.get(vertices[2]).put(vertices[0], 4);// v3->v1, 4
        weightedGraph.get(vertices[2]).put(vertices[5], 5);// v3->v6, 5
        weightedGraph.get(vertices[0]).put(vertices[1], 2);// v1->v2, 2
        weightedGraph.get(vertices[0]).put(vertices[3], 1);// v1->v4, 1
        weightedGraph.get(vertices[1]).put(vertices[4], 10);// v2->v5, 10
        weightedGraph.get(vertices[1]).put(vertices[3], 3);// v2->v4, 3
        weightedGraph.get(vertices[4]).put(vertices[6], 6);// v5->v7, 6
        weightedGraph.get(vertices[6]).put(vertices[5], 1);// v7->v6, 1
        weightedGraph.get(vertices[3]).put(vertices[4], 2);// v4->v5, 2
        weightedGraph.get(vertices[3]).put(vertices[6], 4);// v4->v7, 4
        weightedGraph.get(vertices[3]).put(vertices[5], 8);// v4->v6, 8
        weightedGraph.get(vertices[3]).put(vertices[2], 2);// v4->v3, 2

        System.out.println("\nCalling Kruskal's Algorithm for Minimum Spanning Tree on graph of 7 weighted vertices\n");
        Edge[] minimumSpanningTree = GraphAlgorithms.kruskalsAlgorithm(weightedGraph, vertices);
        for(Edge e : minimumSpanningTree)
            System.out.println(e);

        System.out.println("Running Dijkstra's on a weighted graph with 7 vertices\n");
        GraphAlgorithms.dijkstraSolution(weightedGraph, vertices[0], vertices);
        GraphAlgorithms.printSolution(vertices[0], vertices);

        System.out.println("\nRunning Bellman-Ford's on a weighted graph with 7 vertices\n");
        GraphAlgorithms.dijkstraSolution(weightedGraph, vertices[0], vertices);
        GraphAlgorithms.printSolution(vertices[0], vertices);

        System.out.println("\nChanging weight of edge (v7, v6) from 1 to -4 and (v3, v6) from 5 to 1...\n");
        weightedGraph.get(vertices[6]).put(vertices[5], -4);
        weightedGraph.get(vertices[2]).put(vertices[5], 1);

        try {
            GraphAlgorithms.bellmanFordSolution(weightedGraph, vertices[0], vertices);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        GraphAlgorithms.printSolution(vertices[0], vertices);

        System.out.println("\nChanging weight of edge (v4, v3) from 2 to -6 to create a negative cycle v1 -> v4 -> v3 -> v1\n");
        weightedGraph.get(vertices[3]).put(vertices[2], -6);

        try {
            GraphAlgorithms.bellmanFordSolution(weightedGraph, vertices[0], vertices);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}