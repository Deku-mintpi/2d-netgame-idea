package graphics;

import org.la4j.vector.dense.BasicVector;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.*;
import com.badlogic.gdx.graphics.glutils.*;

import hypmath.HyperCoord;

public class RenderSystem extends EntitySystem {
	private ImmutableArray<Entity> Entities;
	
	public RenderSystem() {}
	
	public BasicVector toPoincare(HyperCoord pos) {
		BasicVector pcoord = null;
		pcoord.set(0, pos.x()/(1-pos.z()));
		pcoord.set(1, pos.y()/(1-pos.z()));
		return pcoord;
	}
	
	
	
}