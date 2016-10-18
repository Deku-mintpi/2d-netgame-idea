package baseSystems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import baseComponents.*;

public class Mappers {
//	public static TextureAtlas atlas;
    public static final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<SpawnerComponent> spawn_m = ComponentMapper.getFor(SpawnerComponent.class);
    public static final ComponentMapper<ImageComponent> img_m = ComponentMapper.getFor(ImageComponent.class);
}