package roadgraph;

import geography.GeographicPoint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MapNode implements Comparable<MapNode>{
    /** The list of edges out of this node */
    private HashSet<MapEdges> edges;

    /** the latitude and longitude of this node */
    private GeographicPoint location;
    private HashMap<MapNode, Double> distances;


    /**
     * Create a new MapNode at a given Geographic location
     * @param loc the location of this node
     */
    MapNode(GeographicPoint loc)
    {
        location = loc;
        edges = new HashSet<MapEdges>();
        distances = new HashMap<>();
    }

    /**
     * Add an edge that is outgoing from this node in the graph
     * @param edge The edge to be added
     */
    void addEdge(MapEdges edge)
    {
        edges.add(edge);
    }

    /**
     * Return the neighbors of this MapNode
     * @return a set containing all the neighbors of this node
     */
    Set<MapNode> getNeighbors()
    {
        Set<MapNode> neighbors = new HashSet<MapNode>();
        for (MapEdges edge : edges) {
            neighbors.add(edge.getOtherNode(this));
        }
        return neighbors;
    }

    public double getDistance(MapNode o) {
        for (MapEdges edge : edges){
            distances.put(edge.getOtherNode(this), edge.getLength());
        }
        return distances.get(o);
    }

    public void setDistances(MapNode node, Double length){
        if (distances.containsKey(node)){
            distances.remove(node);
            distances.put(node, length);
        }
    }

    /**
     * Get the geographic location that this node represents
     * @return the geographic location of this node
     */
    GeographicPoint getLocation()
    {
        return location;
    }

    /**
     * return the edges out of this node
     * @return a set contianing all the edges out of this node.
     */
    Set<MapEdges> getEdges()
    {
        return edges;
    }

    /** Returns whether two nodes are equal.
     * Nodes are considered equal if their locations are the same,
     * even if their street list is different.
     * @param o the node to compare to
     * @return true if these nodes are at the same location, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof MapNode) || (o == null)) {
            return false;
        }
        MapNode node = (MapNode)o;
        return node.location.equals(this.location);
    }

    /** Because we compare nodes using their location, we also
     * may use their location for HashCode.
     * @return The HashCode for this node, which is the HashCode for the
     * underlying point
     */
    @Override
    public int hashCode()
    {
        return location.hashCode();
    }

    /** ToString to print out a MapNode object
     *  @return the string representation of a MapNode
     */
    @Override
    public String toString()
    {
        String toReturn = "[NODE at location (" + location + ")";
        toReturn += " intersects streets: ";
        for (MapEdges e: edges) {
            toReturn += e.getRoadName() + ", ";
        }
        toReturn += "]";
        return toReturn;
    }

    // For debugging, output roadNames as a String.
    public String roadNamesAsString()
    {
        String toReturn = "(";
        for (MapEdges e: edges) {
            toReturn += e.getRoadName() + ", ";
        }
        toReturn += ")";
        return toReturn;
    }

    @Override
    public int compareTo(MapNode o) {
        for (MapEdges i : this.edges){
            for (MapEdges j : o.edges){
                if ((i.getOtherNode(this)).equals(j.getOtherNode(o))){
                    return Double.compare(i.getLength(), j.getLength());
                }
            }
        }

        return 0;
    }
}