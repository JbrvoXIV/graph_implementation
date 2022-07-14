import java.util.*;

public class Graph {
    private HashMap<Vertex, LinkedList<Vertex>> adjVertices = new HashMap<>();

    static class Vertex {
        String label;
        Vertex(String label) {
            this.label = label;
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

    public void addVertex(String label) {
        adjVertices.putIfAbsent(new Vertex(label), new LinkedList<Vertex>());
    }

    public boolean removeVertex(String label) {
        Vertex rmv = new Vertex(label);
        if(adjVertices.containsKey(rmv)) {
            adjVertices.remove(rmv);
            return true;
        }
        return false;
    }

    public boolean addEdge(String start, String destination) {
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

    public boolean removeEdge(String start, String destination) {
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

    Set<String> depthFirstTraversal(String root) {
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

    Set<String> breadthFirstTraversal(String root) {
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