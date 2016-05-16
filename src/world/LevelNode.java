package world;

import java.util.*;

public class LevelNode<T> implements Iterable<LevelNode<T>> {
	T data;
	LevelNode<T> parent;
	List<LevelNode<T>> nodeChildren;
	
	public LevelNode(T data) {
		this.data = data;
		this.nodeChildren = new LinkedList<LevelNode<T>>();
	}
	
	public LevelNode<T> addChild(T child) {
        LevelNode<T> childNode = new LevelNode<T>(child);
        childNode.parent = this;
        this.nodeChildren.add(childNode);
        return childNode;
    }
	
	@Override
	public Iterator<LevelNode<T>> iterator() {
		// TODO Auto-generated method stub; add an actual iterator thing here
		return null;
	}
}