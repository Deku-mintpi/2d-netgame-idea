package baseSystems;

import java.util.Comparator;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import baseSystems.*;

import baseComponents.*;

public class RenderSystem extends SortedIteratingSystem {

	public RenderSystem() {
		super(Family.all(ImageComponent.class, PositionComponent.class).get(), new ZComparator());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// TODO Auto-generated method stub
		GameAdapter.batch.draw((TextureRegion) Mappers.img_m.get(entity).image, (float) Mappers.pm.get(entity).x, (float) Mappers.pm.get(entity).y);
	}
	
	private static class ZComparator implements Comparator<Entity> {
		@Override
		public int compare(Entity e1, Entity e2) {
			// TODO Auto-generated method stub
			return (int)Math.signum(Mappers.img_m.get(e1).zOrd - Mappers.img_m.get(e2).zOrd);
		}
		
	}
	
}
