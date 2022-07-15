import java.lang.reflect.Array;
import java.util.*;

public class Graph {
    private HashMap<Vertex, LinkedList<Vertex>> adjVertices = new HashMap<>();
    private HashMap<Vertex, LinkedList<Vertex>> directedVertices = new HashMap<>();

    static class Vertex {
        String label;
        int indegree;
        Vertex(String label) {
            this.label = label;
            indegree = 0;
        }

        @Override
        public boolean equals(Object o) {
            if(o == this)
                return true;
            if(o == null)
                return false;
            if(getClass() != o.getClass())
                return false;
            final Vertex other = (Vertex)o;
            return this.label.equals(other.label);
        }

        @Override
        public int hashCode() {
            return label.hashCode();
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public void addVertexUndirected(Vertex v) {
        adjVertices.putIfAbsent(v, new LinkedList<Vertex>());
    }

    public void addVertexDirected(Vertex v) {
        directedVertices.putIfAbsent(v, new LinkedList<>());
    }

    public boolean removeVertex(Vertex v) {
        if(adjVertices.containsKey(v)) {
            adjVertices.remove(v);
            return true;
        }
        return false;
    }

    public boolean addEdgeUndirected(String start, String destination) {
        Vertex v1 = new Vertex(start);
        Vertex v2 = new Vertex(destination);
        if(adjVertices.containsKey(v1) && adjVertices.containsKey(v2)) {
            if(!adjVertices.get(v1).contains(v2) && !adjVertices.get(v2).contains(v1)) {
                adjVertices.get(v1).add(v2);
                adjVertices.get(v2).add(v1);
                return true;
            }
        }

        return false;
    }

    public void addEdgeDirected(Vertex start, Vertex destination) {
        if(!directedVertices.containsKey(start))
            addVertexDirected(start);
        directedVertices.get(start).add(destination);
        destination.indegree++;
    }

    public boolean removeEdgeUndirected(String start, String destination) {
        Vertex v1 = new Vertex(start);
        Vertex v2 = new Vertex(destination);
        if(adjVertices.containsKey(v1) && adjVertices.containsKey(v2)) {
            if(adjVertices.get(v1).contains(v2) && adjVertices.get(v2).contains(v1)) {
                adjVertices.get(v1).remove(v2);
                adjVertices.get(v2).remove(v1);
                return true;
            }
        }
        return false;
    }

    public Set<String> depthFirstTraversalUG(String root) {
        if(!adjVertices.containsKey(new Vertex(root))) {
            return Collections.emptySet();
        }

        Set<String> visited = new LinkedHashSet<>();
        Stack<String> stack = new Stack<>();

        stack.push(root);
        while(!stack.isEmpty()) {
            String vertex = stack.pop();
            if(!visited.contains(vertex)) {
                visited.add(vertex);
                for(Vertex v : adjVertices.get(new Vertex(vertex))) {
                    stack.push(v.label);
                }
            }
        }
        return visited;
    }

    public Set<String> breadthFirstTraversalUG(String root) {
        if(!adjVertices.containsKey(new Vertex(root))) {
            return Collections.emptySet();
        }

        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root);

        while(!queue.isEmpty()) {
            String vertex = queue.poll();
            for(Vertex v : adjVertices.get(new Vertex(vertex))) {
                if(!visited.contains(v.label)) {
                    visited.add(v.label);
                    queue.add(v.label);
                }
            }
        }
        return visited;
    }

    public Set<String> depthFirstTraversalDG(Vertex root) {
        if(!directedVertices.containsKey(root)) {
            return Collections.emptySet();
        }

        Set<String> visited = new LinkedHashSet<>();
        Stack<String> stack = new Stack<>();

        stack.push(root.label);
        while(!stack.isEmpty()) {
            String vertex = stack.pop();
            if(!visited.contains(vertex)) {
                visited.add(vertex);
                for(Vertex v : directedVertices.get(root)) {
                    stack.push(v.label);
                }
            }
        }
        return visited;
    }

    public Set<String> breadthFirstTraversalDG(Vertex root) {
        if(!directedVertices.containsKey(root)) {
            return Collections.emptySet();
        }

        Set<String> visited = new LinkedHashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root.label);

        while(!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            for(Vertex v : directedVertices.get(vertex)) {
                if(!visited.contains(v.label)) {
                    visited.add(v.label);
                    queue.add(vertex);
                }
            }
        }
        return visited;
    }


    public Set<Vertex> topologicalSortDAG() {
        Stack<Vertex> queue = new Stack<>();
        Set<Vertex> sorted = new LinkedHashSet<>();

        for(Vertex v : directedVertices.keySet()) {
            if(v.indegree == 0)
                queue.add(v);
        }

        while(!queue.isEmpty()) {
            Vertex curr = queue.pop();
            sorted.add(curr);
            if(curr.indegree == 0)
                continue;
            for(Vertex neighbor : directedVertices.get(curr)) {
                neighbor.indegree--;
                if(neighbor.indegree == 0)
                    queue.add(neighbor);
            }
        }
        return sorted;
    }

    @Override
    public String toString() {
        StringBuilder graph = new StringBuilder("");
        for(Vertex v : adjVertices.keySet()) {
            if(adjVertices.get(v).size() == 0)
                graph.append(String.format("%s: None", v));
            else
                graph.append(String.format("%s: %s", v, adjVertices.get(v).getFirst()));
            for(Vertex adjV : adjVertices.get(v)) {
                if(adjV.equals(adjVertices.get(v).getFirst()))
                    continue;
                graph.append(String.format(" -> %s", adjV));
            }
            graph.append("\n");
        }
        return graph.toString();
    }
}