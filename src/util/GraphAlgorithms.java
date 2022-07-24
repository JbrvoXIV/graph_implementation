package util;

import java.util.*;

public final class GraphAlgorithms {

    // search through graph neighbors first iteratively with bfs
    public static void bfsIterative(HashMap<Vertex, Set<Vertex>> graph, Vertex src, Vertex[] vertices) {
        Queue<Vertex> q = new LinkedList<>();
        for(Vertex v : vertices)
            v.known = false;
        q.add(src);
        while(!q.isEmpty()) {
            Vertex cur = q.poll();
            if(!cur.known) {
                System.out.printf("%s ", cur);
            }
            cur.known = true;
            for(Vertex neighbor : graph.get(cur)) {
                if(!neighbor.known)
                    q.add(neighbor);
            }
        }
    }

    // search through graph deeply iteratively with dfs
    public static void dfsIterative(HashMap<Vertex, Set<Vertex>> graph, Vertex src, Vertex[] vertices) {
        Stack<Vertex> s = new Stack<>();
        for(Vertex v : vertices)
            v.known = false;
        s.add(src);
        while(!s.isEmpty()) {
            Vertex cur = s.pop();
            if(!cur.known)
                System.out.printf("%s ", cur);
            cur.known = true;
            for(Vertex neighbor : graph.get(cur)) {
                if(neighbor.known)
                    continue;
                s.push(neighbor);
            }
        }
    }

    // search through graph deeply iteratively with dfs
    public static void dfsRecursive(HashMap<Vertex, Set<Vertex>> graph, Vertex src, Vertex[] vertices) {
        for(Vertex v : vertices)
            v.known = false;
        for(Vertex v : vertices) {
            if(v.known)
                continue;
            dfsHelper(graph, v, vertices);
        }
    }

    private static void dfsHelper(HashMap<Vertex, Set<Vertex>> graph, Vertex src, Vertex[] vertices) {
        System.out.printf("%s ", src);
        for(Vertex neighbor : graph.get(src)) {
            if(!neighbor.known)
                dfsHelper(graph, neighbor, vertices);
        }
    }

    // sort nodes in order starting from indegree 0, requires an indegree of 0!!
    public static Set<Vertex> topologicalSort(HashMap<Vertex, Set<Vertex>> graph, Vertex[] vertices) {
        Queue<Vertex> q = new LinkedList<>();
        Set<Vertex> sorted = new LinkedHashSet<>();
        int counter = 0;

        for(Vertex v : vertices)
            if(v.inDegree == 0)
                q.add(v);

        while(!q.isEmpty()) {
            Vertex cur = q.poll();
            cur.topNum = ++counter;
            sorted.add(cur);
            for(Vertex neighbor : graph.get(cur))
                if(--neighbor.inDegree == 0)
                    q.add(neighbor);
        }
        return sorted;
    }

    // Best solution for shortest path on undirected/directed graphs
    public static void bfsSolution(HashMap<Vertex, Set<Vertex>> graph, Vertex src, Vertex[] list) {
        for(Vertex v : list)
            v.dist = Integer.MAX_VALUE;

        src.dist = 0; // set starting node distance as 0
        Queue<Vertex> q = new LinkedList<Vertex>();
        q.add(src); // add src to queue of visited nodes

        while(!q.isEmpty()) {
            Vertex cur = q.poll();
            if(!graph.containsKey(cur))
                continue;
            for(Vertex neighbor : graph.get(cur)) {
                if(neighbor.dist > cur.dist + 1) {
                    neighbor.dist = cur.dist + 1;
                    neighbor.path = cur;
                    q.add(neighbor);
                }
            }
        }
    }

    // O( |E| + V log V ) Best solution for a weighted graph with no negative weights or cycles
    public static void dijkstraSolution(HashMap<Vertex, HashMap<Vertex, Integer>> weightedGraph,
                                        Vertex src,
                                        Vertex[] list)
    {
        for(Vertex v : list) {
            v.dist = Integer.MAX_VALUE;
            v.known = false;
        }

        src.dist = 0;
        Queue<Vertex> q = new PriorityQueue<>();
        q.add(src);
        while(!q.isEmpty()) {
            Vertex cur = q.poll();
            cur.known = true;
            if(!weightedGraph.containsKey(cur))
                continue;
            for(Vertex neighbor : weightedGraph.get(cur).keySet()) {
                if(neighbor.known) // path from src to neighbor is already known
                    continue;
                int distFromNeighbor = weightedGraph.get(cur).get(neighbor);
                if(neighbor.dist > cur.dist + distFromNeighbor) {
                    neighbor.dist = cur.dist + distFromNeighbor;
                    neighbor.path = cur;
                    q.add(neighbor);
                }
            }
        }
    }

    // O( E * N ) n = number of vertices and e = number of edges, best algorithm for negative edge weights with/without cycle
    public static void bellmanFordSolution(HashMap<Vertex, HashMap<Vertex, Integer>> weightedGraph,
                                           Vertex src,
                                           Vertex[] vertices) throws Exception
    {
        for(Vertex v : vertices)
            v.dist = Integer.MAX_VALUE;
        src.dist = 0;

        for(int rep = 0; rep < vertices.length; rep++) {
            for(Vertex from : weightedGraph.keySet()) {
                for(Vertex to : weightedGraph.get(from).keySet()) {
                    int weight = weightedGraph.get(from).get(to);
                    if(from.dist != Integer.MAX_VALUE && to.dist > from.dist + weight) {
                        if(rep == vertices.length - 1) {
                            throw new Exception("Error: Can't solve the shortest path, graph has negative cycle");
                        }
                        to.dist = from.dist + weight;
                        to.path = from;
                    }
                }
            }
        }
    }

    public static Edge[] kruskalsAlgorithm(HashMap<Vertex, HashMap<Vertex, Integer>> weightedGraph, Vertex[] vertices) {
        ArrayList<Edge> edges = new ArrayList<>();
        Edge[] minimumSpanningTree = new Edge[vertices.length - 1];
        DisjointSet<Vertex> connected = new DisjointSet<>(vertices);

        for(Vertex from : weightedGraph.keySet())
            for(Vertex to : weightedGraph.get(from).keySet())
                edges.add(new Edge(from, to, weightedGraph.get(from).get(to)));
        Collections.sort(edges);

        int cur = 0;
        for(Edge e : edges)
            if(!connected.find(e.from).equals(connected.find(e.to))) {
                minimumSpanningTree[cur++] = e;
                connected.union(e.from, e.to);
            }
        return minimumSpanningTree;
    }

    public static void printSolution(Vertex src, Vertex[] vertices) {
        System.out.println("Source is " + src);
        for(Vertex v : vertices) {
            if(v.equals(src))
                continue;
            System.out.print("Shortest distance/path from " + src + " to " + v + ": " + v.dist + " [");
            printPath(src, v);
            System.out.println("]");
        }
    }

    private static void printPath(Vertex src, Vertex target) {
        if(src.equals(target)) {
            System.out.print(src);
            return;
        }
        printPath(src, target.path);
        System.out.print(" -> " + target);
    }

}
