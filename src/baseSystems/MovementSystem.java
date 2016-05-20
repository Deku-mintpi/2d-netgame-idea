package baseSystems;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.*;

import baseComponents.*;
import types.Complex;

public class MovementSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	
	public MovementSystem() {}
	
	public void applyVelocity(Entity thing) {
		Complex coords = Mappers.position.get(thing).coords;
		Basic2DMatrix transform = Mappers.velocity.get(thing).currentTransform;
		Complex oldCoords = new Complex(coords.re, coords.im);
		if (transform != null) {
			Mappers.position.get(thing).coords = coords.mobius(transform);
		} else {
			
		}
		
	}

}
