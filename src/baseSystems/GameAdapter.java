package baseSystems;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch.Config;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Button.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import types.*;

/* 
 * TODO: Figure out anti-aliasing. There's at least one really stupid way to achieve it without editing the shaders,
 * which is to rapidly modulate xShift and yShift to achieve something like MFAA, but...
 * 
 * TODO: Massive code cleanup: place all code except that needed for general input handling, rendering, and enabling
 * communication with the Editor into organized functions; also, probably put some of it into other files...
 * 
 */

public class GameAdapter extends ApplicationAdapter {
	private OrthographicCamera camera;
//	private Vector<VecPath> shapes; //java.utils vector, not la4j vector!
	private Vector<Complex[]> shapes;
	private Vector<Complex> positions;
	private Complex origin;
	ShaderProgram hyperShade;
	Mesh fullquad;
	private Stage stage;
	private Table uiTable;
	
	private EditorFrame editorWindow;
	private boolean editorOn = false;
	
	private int width;
	private int height;
	private double aspect;
	private double scale = 1;
	protected double xShift;
	protected double yShift;
	
	public long tick;
	
	public FreeTypeFontGenerator dejaGen;
	public FreeTypeFontParameter dejaParam;
	public BitmapFont font16;

	private double numSamples = 16;
	
	@Override
	public void create() {
		stage = new Stage(new ScreenViewport());
//		camera = new OrthographicCamera();
//		camera.setToOrtho(false, 2, 2);
		origin = new Complex(0, 0);
		hyperShade = new ShaderProgram(Gdx.files.internal("baseSystems/hypervertex.glsl"), Gdx.files.internal("baseSystems/hyperfrag.glsl"));
		fullquad = createFullScreenQuad();
		
		shapes = new Vector<Complex[]> (5);
		shapes.setSize(2);
		shapes.set(0, new Complex[] {new Complex(0.3, 0.3), new Complex(0.0, 0.5), new Complex(-0.3, 0.3), new Complex(-1.0, 0.0), new Complex(-0.3, -0.3), new Complex(0.3, -0.3)});
		shapes.set(1, new Complex[] {new Complex(0.3, 0.3), new Complex(0.2, 0.4), new Complex(-0.3, 0.3), new Complex(-0.3, -0.3), new Complex(0.3, -0.3)});
		
		// used for initial button to choose test mode.
		dejaGen = new FreeTypeFontGenerator(Gdx.files.internal("DejaVuSans.ttf"));
		dejaParam = new FreeTypeFontParameter();
		font16 = dejaGen.generateFont(dejaParam);
		
		Gdx.input.setInputProcessor(stage);
		setupMainMenu();
	}

	@Override
	public void dispose() {
		stage.dispose();
		editorWindow.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() { 
		tick++;
		stage.act(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0.05f, 0.1f, 0.1f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    Gdx.gl.glEnable(GL20.GL_BLEND);
	    
	    // TODO: Change 2nd argument to GL20.GL_ONE_MINUS_SRC_ALPHA if AA is achieved without rendering each shape multiple times!
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_CONSTANT_ALPHA);
	    for (int i = 0; i < shapes.size(); i++) {
//	    	shapeDraw(shapes.get(i), new Color(1.f, 0.f, 0.f, (float) (1.0/numSamples)));
	    	for (int j = 0; j < numSamples; j++) {
	    		shapeDraw(shapes.get(i), new Color(1.f, 0.f, 0.f, (float) (1.0/numSamples)));
	    	}
		}
	    stage.draw();
	    
	    if (editorOn) {
	    	editorWindow.updateDisplay(yShift, xShift);
	    }
	    
	}

	@Override
	public void resize(int arg0, int arg1) {
		width = arg0;
		height = arg1;
		aspect = width/height;
		System.out.println(aspect);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public void updateShapes(Vector<VecPath> paths, Vector<Complex> positions) {
		shapes.setSize(paths.size());
		for (int i = 0; i < paths.size(); i++) {
			shapes.set(i, paths.get(i).toCoordinates(positions.get(i)));
		}
	}
	
	public void shapeDraw(Complex[] vertices, Color fillType/*, int sampleNum*/) {
		//	Vector3[] lines = new Vector3[path.vertices.size()];
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
		hyperShade.setUniformf("aspect", (float) aspect);
		hyperShade.setUniformf("scale", (float) scale);
		
		// Chord supersampling. This ignores stuff that is out to the corners but it should yield decent results hopefully
		// actually, trying something else in the renderer first.
		
//		Raw Monte Carlo supersample pattern. This tends to flicker on edges. Uses values below .5 as otherwise precision issues lead to 
		hyperShade.setUniformf("xShift", (float) (2 * (xShift + 0.998*Math.random() - 0.499) * aspect / width));
		hyperShade.setUniformf("yShift", (float) (2 * (yShift + 0.998*Math.random() - 0.499) / height));
		
//		Either no supersampling, or the supersampling is in the fragment shader.
//		hyperShade.setUniformf("xShift", (float) (2 * xShift * aspect / width));
//		hyperShade.setUniformf("yShift", (float) (2 * yShift / height));
		
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
	
	public void setupMainMenu() {
		VerticalGroup mainMenuButtons = new VerticalGroup();
		TextButtonStyle menuButtonStyle = new TextButtonStyle();
		menuButtonStyle.font = font16;
		menuButtonStyle.fontColor = Color.BLACK;
		NinePatch buttonPatch = new NinePatch(new Texture(Gdx.files.internal("art/ButtonTexture.png")), 20, 20, 20, 20);
		NinePatchDrawable buttonDrawable = new NinePatchDrawable(buttonPatch);
		menuButtonStyle.up = buttonDrawable.tint(Color.RED);
		menuButtonStyle.down = new NinePatchDrawable(buttonDrawable).tint(Color.BLUE);
		TextButton startEdit = new TextButton("World Editor", menuButtonStyle);
		startEdit.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				startEditor();
				editorOn = true;
			}
		});
		stage.addListener(new InputListener() {
			public boolean mouseMoved(InputEvent event, float x, float y) {
				yShift = y - height / 2;
				return true;
			}
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				xShift = x - width / 2;
				return true;
			}
		});
		uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.add(startEdit);
		stage.addActor(uiTable);
		
	}
	
	protected void startEditor(){
		editorWindow = new EditorFrame(this);
		uiTable.reset();
	}

	public void editorTest() {
		xShift -= 25;
	}
	
}
