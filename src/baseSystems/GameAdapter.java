package baseSystems;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Vector3;

import types.*;

//TODO: Writing a proper vertex shader or finding a better render system will be necessary.

public class GameAdapter extends ApplicationAdapter {
	private OrthographicCamera camera;
//	private Vector<VecPath> shapes; //java.utils vector, not la4j vector!
	private Vector<Complex[]> shapes;
	private Vector<Complex> positions;
	private Complex origin;
	ShaderProgram hyperShade;
	Mesh fullquad;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 2, 2);
		origin = new Complex(0, 0);
		hyperShade = new ShaderProgram(Gdx.files.internal("baseSystems/hypervertex.glsl"), Gdx.files.internal("baseSystems/hyperfrag.glsl"));
		shapes = new Vector<Complex[]> (5);
		shapes.setSize(1);
		shapes.set(0, new Complex[] {new Complex(0.5, 0.5), new Complex(0.0, 1.0), new Complex(-0.5, 0.5), new Complex(-0.5, -0.5), new Complex(0.5, -0.5)});
		fullquad = createFullScreenQuad();
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
		Gdx.gl.glClearColor(0.05f, 0.1f, 0.1f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    for (int i = 0; i < shapes.size(); i++) {
			shapeDraw(shapes.get(i), Color.RED);
		}
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
//	public void updateShapes(Vector<VecPath> paths, Vector<Complex> positions) {
//		shapes = paths;
//		this.positions = positions;
//	}
	
//	public void shapeDraw(VecPath path, Complex handle) {
////		Vector3[] lines = new Vector3[path.vertices.size()];
//		hyperShade.begin();
//		for (int i = 1; i < path.vertices.size(); i++) {
//			Complex p = handle.mobius(path.vertices.get(i-1));
//			Complex q = handle.mobius(path.vertices.get(i));
//			
//			//convert to Klein model
//			Complex np = new Complex(2*p.re / (1 + p.re*p.re + p.im*p.im), 2*p.im / (1 + p.re*p.re + p.im*p.im));
//			Complex nq = new Complex(2*q.re / (1 + q.re*q.re + q.im*q.im), 2*q.im / (1 + q.re*q.re + q.im*q.im));
//			
//			float a = (float) (2.0*(np.im - nq.im));
//			float b = (float) (2.0*(nq.re - np.re));
//			float c = (float) (np.im * nq.re - np.re * nq.im);
//			
//			hyperShade.setUniformf("arcs["+(i-1)+"]", new Vector3(a, b, c));
////			lines[i-1] 
//		}
//		hyperShade.setUniformf("color", path.fillType);
//		hyperShade.setUniformi("numArcs", path.vertices.size());
//		createFullScreenQuad().render(hyperShade, GL20.GL_TRIANGLE_STRIP);
//		hyperShade.end();
//	}
	
	public void shapeDraw(Complex[] vertices, Color fillType) {
		//	Vector3[] lines = new Vector3[path.vertices.size()];
		System.out.println("drawing shape");
		hyperShade.begin();
		for (int i = 0; i < vertices.length; i++) {
			Complex p = vertices[i];
			Complex q = vertices[(i+1)%vertices.length];

			//convert to Klein model
			Complex np = new Complex(2*p.re / (1 + p.re*p.re + p.im*p.im), 2*p.im / (1 + p.re*p.re + p.im*p.im));
			Complex nq = new Complex(2*q.re / (1 + q.re*q.re + q.im*q.im), 2*q.im / (1 + q.re*q.re + q.im*q.im));

			float a = (float) (2.0*(np.im - nq.im));
			float b = (float) (2.0*(nq.re - np.re));
			float c = (float) (np.im * nq.re - np.re * nq.im);

			hyperShade.setUniformf("arcs["+i+"]", new Vector3(a, b, c));
			//		lines[i-1] 
		}
		hyperShade.setUniformf("color", fillType);
		hyperShade.setUniformi("numArcs", vertices.length);
		fullquad.render(hyperShade, GL20.GL_TRIANGLE_STRIP);
		hyperShade.end();
	}
	
	//copied super ugly thing from libgdx documentation
	public Mesh createFullScreenQuad() {

		float[] verts = new float[12];
		int i = 0;

		verts[i++] = -1; // x1
		verts[i++] = -1; // y1
		verts[i++] = 0;

		verts[i++] = 1f; // x2
		verts[i++] = -1; // y2
		verts[i++] = 0;

		verts[i++] = -1; // x4
		verts[i++] = 1f; // y4
		verts[i++] = 0;

		verts[i++] = 1f; // x3
		verts[i++] = 1f; // y2
		verts[i++] = 0;

		Mesh mesh = new Mesh(true, 4, 0,  // static mesh with 4 vertices and no indices
				new VertexAttribute(Usage.Position, 3, "pos"));

		mesh.setVertices( verts );
		return mesh;
	}
	// original code by kalle_h

}
