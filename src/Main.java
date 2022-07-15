
public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        Graph.Vertex[] vertices = new Graph.Vertex[]{
                new Graph.Vertex("shirt"), // 0
                new Graph.Vertex("watch"), // 1
                new Graph.Vertex("undershorts"), // 2
                new Graph.Vertex("pants"), // 3
                new Graph.Vertex("belt"), // 4
                new Graph.Vertex("tie"), // 5
                new Graph.Vertex("jacket"), // 6
                new Graph.Vertex("socks"), // 7
                new Graph.Vertex("shoes") // 8
        };


        graph.addEdgeDirected(vertices[7], vertices[8]);
        graph.addEdgeDirected(vertices[2], vertices[8]);
        graph.addEdgeDirected(vertices[2], vertices[3]);
        graph.addEdgeDirected(vertices[3], vertices[8]);
        graph.addEdgeDirected(vertices[3], vertices[4]);
        graph.addEdgeDirected(vertices[0], vertices[4]);
        graph.addEdgeDirected(vertices[0], vertices[5]);
        graph.addEdgeDirected(vertices[5], vertices[6]);
        graph.addEdgeDirected(vertices[4], vertices[6]);

        System.out.println(graph);

        System.out.println(graph.depthFirstTraversalDG(vertices[2]));
        System.out.println(graph.breadthFirstTraversalDG(vertices[2]));
        System.out.println(graph.topologicalSortDAG());
    }
}