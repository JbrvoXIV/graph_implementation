package util;

public class Vertex implements Comparable<Vertex> {
    public String name;
    public int dist;
    public Vertex path; // predecessor
    public boolean known;
    public int inDegree;
    public int topNum;

    public Vertex(String name) {
        dist = Integer.MAX_VALUE;
        this.name = name;
        known = false;
        inDegree = 0;
        topNum = 0;
    }

    @Override
    public int compareTo(Vertex another) {
        return dist - another.dist;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(o.getClass() != this.getClass())
            return false;
        final Vertex another = (Vertex)o;
        return this.name.equals(another.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}