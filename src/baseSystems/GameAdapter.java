package baseSystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.*;
import baseSystems.Main;

import baseComponents.*;

public class GameAdapter extends ApplicationAdapter {
	private OrthographicCamera camera;
	protected static SpriteBatch batch;
	private RenderSystem renderer;
	float w;
	float h;
	
	@Override
	public void create() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
//		batch.setProjectionMatrix(cam2d.combined);
		renderer = new RenderSystem();
		Main.engine.addSystem(renderer);
		Mappers.atlas = new TextureAtlas(Gdx.files.internal("src/images/textures.atlas"));
		AtlasRegion testImg = Mappers.atlas.findRegion("ButtonTexture");
		Entity testEntity = Main.engine.createEntity();
		ImageComponent<AtlasRegion> testImgComp = Main.engine.createComponent(ImageComponent.class);
		PositionComponent testPosComp = Main.engine.createComponent(PositionComponent.class);
		testImgComp.image = testImg;
		testImgComp.zOrd = 1;
		testImgComp.canRotate = true;
		testPosComp.x = 17;
		testPosComp.y = 29;
		testEntity.add(testImgComp);
		testEntity.add(testPosComp);
		Main.engine.addEntity(testEntity);
		System.out.println(renderer.getFamily().matches(testEntity));
		System.out.println(renderer.getEntities());
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float dt = Gdx.graphics.getDeltaTime();
		batch.begin();
		renderer.update(dt);
		batch.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
}