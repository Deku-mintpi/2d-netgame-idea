package baseSystems;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;

import types.*;

//TODO: Writing a proper vertex shader or finding a better render system will be necessary.

public class GameAdapter extends ApplicationAdapter {
	private OrthographicCamera camera;
	private ShapeRenderer renderer;
	private Vector<VecPath> shapes; //java.utils vector, not la4j vector!
	private Vector<Complex> positions;
	private Complex origin;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);
		origin = new Complex(0, 0);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public void updateShapes(Vector<VecPath> paths, Vector<Complex> positions) {
		shapes = paths;
		this.positions = positions;
	}
	
	public void shapeDefine(VecPath path, Complex handle) {
		for (int i = 1; i < path.vertices.size(); i++) {
			renderer.begin(ShapeType.Filled);
			renderer.setColor(path.fillType);
			Complex p = handle.mobius(path.vertices.get(i-1));
			Complex q = handle.mobius(path.vertices.get(i));
			if (p.equals(origin) || q.equals(origin)) {
				
			}
			
			Complex np = p.times(1/p.absSq());
			
			double eSlope = (q.im - p.im) / (q.re - p.re);
			double aSlope = (np.im - q.im) / (np.re - q.re);
			
			double centerX = (eSlope * aSlope * (np.im - p.im) + eSlope * (q.re + np.re) - aSlope * (p.re + q.re)) / (2*(eSlope - aSlope));
			double centerY = (p.im + q.im) / 2 - (centerX - (p.re + q.re) / 2) / eSlope;
			
			
		}
	}

}
