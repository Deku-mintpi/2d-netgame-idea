package world;

import java.util.*;

/*
 * Some thoughts on how this will probably work:
 * 
 * LevelNodes will have one datatype each.
 * The type associated will determine what sort of content it contains.
 * 
 * Root node: LevelNode<Complex, LevelNode<V, T>>
 * Tree Structural node: LevelNode<Basic2DMatrix, LevelNode<V, T>>
 * Local Map node: LevelNode<V, LevelNode<V, Entity>>
 * 
 * 
 * 
 */

public class LevelNode<V, T> {
	V location;
	T data;
	LevelNode<V, T> parent;
	List<LevelNode<V, T>> nodeChildren;
	
	public LevelNode(V position, T data) {
		this.location = position;
		this.data = data;
		this.nodeChildren = new LinkedList<LevelNode<V, T>>();
	}
	
	public LevelNode<V, T> addChild(V pos, T child) {
        LevelNode<V, T> childNode = new LevelNode<V, T>(pos, child);
        childNode.parent = this;
        this.nodeChildren.add(childNode);
        return childNode;
    }
}