package baseSystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.*;

import baseComponents.*;
import types.Complex;

public class MovementSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	
	public MovementSystem() {}
	
	public void applyVelocity(Entity thing) {
		Complex coords = Mappers.position.get(thing).coords;
		Complex oldCoords = new Complex(coords.re, coords.im);
		Mappers.position.get(thing).heading += Mappers.velocity.get(thing).spin;
		
	}

}
