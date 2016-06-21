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
	public Vector<VecPath> shapes; //java.utils vector, not la4j vector!
//	private Vector<Complex[]> shapes;
	private Vector<Complex> positions;
	private Complex origin;
	ShaderProgram hyperShade;
	Mesh diskquad;
	Mesh fillquad;
	private Stage stage;
	private Table uiTable;
	private VecPath blankPath;
	public static InputListener inpListener;
	private static GameAdapter passableGame;
	
	public EditorFrame editorWindow;
	private boolean editorOn = false;
	
	public int width;
	public int height;
	private double aspect;
	private double scale = 1;
	public double xShift;
	public double yShift;
	
	public long tick;
	
	public FreeTypeFontGenerator dejaGen;
	public FreeTypeFontParameter dejaParam;
	public BitmapFont font16;

	public double numSamples = 1;
	
	@Override
	public void create() {
		passableGame = this;
		stage = new Stage(new ScreenViewport());
//		camera = new OrthographicCamera();
//		camera.setToOrtho(false, 2, 2);
		origin = new Complex(0, 0);
		hyperShade = new ShaderProgram(Gdx.files.internal("baseSystems/hypervertex.glsl"), Gdx.files.internal("baseSystems/hyperfrag.glsl"));
		diskquad = createHyperQuad(true);
		fillquad = createHyperQuad(false);
		blankPath = new VecPath(new Complex[] {new Complex(0,0), new Complex(0,-1), new Complex(1,0)}, new Color(0f, 0f, 0f, 1f));
		
		shapes = new Vector<VecPath> (5);
		shapes.setSize(1);
		shapes.set(0, new VecPath(new Complex[] {new Complex(0.3, 0.3), new Complex(0.0, 0.5), new Complex(-0.3, 0.3), new Complex(-1.0, 0.0), new Complex(-0.3, -0.3), new Complex(0.3, -0.3)},Color.RED));
//		shapes.set(1, new Complex[] {new Complex(0.3, 0.3), new Complex(0.2, 0.4), new Complex(-0.3, 0.3), new Complex(-0.3, -0.3), new Complex(0.3, -0.3)});
		
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
		if(editorOn) editorWindow.dispose();
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
//		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_CONSTANT_ALPHA);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		initDraw(blankPath);
		for (int i = 0; i < shapes.size(); i++) {
//			shapeDraw(shapes.get(i), new Color(1.f, 0.f, 0.f, (float) 1.0), 0);
			for (int j = 0; j < numSamples; j++) {
//				shapeDraw(shapes.get(i), new Color(1.f, 0.f, 0.f, (float) (1.0/numSamples)), j);
				shapeDraw(shapes.get(i), j);
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
		aspect = width/(double)height;
		System.out.println(aspect);
		diskquad = createHyperQuad(true);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public void updateShapes(Vector<VecPath> paths, Vector<Complex> positions) {
		shapes.setSize(paths.size());
		for (int i = 0; i < paths.size(); i++) {
			shapes.set(i, paths.get(i));
		}
	}
	
	public void initDraw(VecPath path) {
		//	Vector3[] lines = new Vector3[path.vertices.size()];
		hyperShade.begin();
		for (int i = 0; i < path.vertices.length; i++) {
			Complex p = path.vertices[i];
			Complex q = path.vertices[(i+1)%path.vertices.length];

			//convert to Klein model
			Complex np = new Complex(2*p.re / (1 + p.re*p.re + p.im*p.im), 2*p.im / (1 + p.re*p.re + p.im*p.im));
			Complex nq = new Complex(2*q.re / (1 + q.re*q.re + q.im*q.im), 2*q.im / (1 + q.re*q.re + q.im*q.im));

			float a = (float) (2.0*(np.im - nq.im));
			float b = (float) (2.0*(nq.re - np.re));
			float c = (float) (np.im * nq.re - np.re * nq.im);

			hyperShade.setUniformf("arcs["+i+"]", new Vector3(a, b, c));
			//		lines[i-1] 
		}
		
		hyperShade.setUniformf("color", new Color(0f, 0f, 0f, 0f));
		hyperShade.setUniformi("numArcs", 2);
		hyperShade.setUniformf("aspect", (float) aspect);
		hyperShade.setUniformf("scale", (float) scale);
		hyperShade.setUniformf("xShift", (float) (2 * xShift * aspect / width));
		hyperShade.setUniformf("yShift", (float) (2 * yShift / height));
		
		fillquad.render(hyperShade, GL20.GL_TRIANGLE_STRIP);
		hyperShade.end();
	}
	
	public void shapeDraw(VecPath path, int sampleNum) {
		//	Vector3[] lines = new Vector3[path.vertices.size()];
		hyperShade.begin();
		for (int i = 0; i < path.vertices.length; i++) {
			Complex p = path.vertices[i];
			Complex q = path.vertices[(i+1)%path.vertices.length];

			//convert to Klein model
			Complex np = new Complex(2*p.re / (1 + p.re*p.re + p.im*p.im), 2*p.im / (1 + p.re*p.re + p.im*p.im));
			Complex nq = new Complex(2*q.re / (1 + q.re*q.re + q.im*q.im), 2*q.im / (1 + q.re*q.re + q.im*q.im));

			float a = (float) (2.0*(np.im - nq.im));
			float b = (float) (2.0*(nq.re - np.re));
			float c = (float) (np.im * nq.re - np.re * nq.im);

			hyperShade.setUniformf("arcs["+i+"]", new Vector3(a, b, c));
			//		lines[i-1] 
		}
		hyperShade.setUniformf("color", path.fillType);
		hyperShade.setUniformi("numArcs", path.vertices.length);
		hyperShade.setUniformf("aspect", (float) aspect);
		hyperShade.setUniformf("scale", (float) scale);
//		hyperShade.setUniformf("pixW", (float) (1.0/height));
		
		// Chord supersampling. This ignores stuff that is out to the corners but it should yield decent results hopefully
		// actually, trying something else in the renderer first.
		
//		// Grid sampling
//		hyperShade.setUniformf("xShift", (float) (2 * (xShift + (sampleNum/Math.sqrt(numSamples))%1 - 0.5*(Math.sqrt(numSamples)-1)/Math.sqrt(numSamples)) * aspect / width));
//		hyperShade.setUniformf("yShift", (float) (2 * (yShift + (Math.floor(sampleNum/Math.sqrt(numSamples))/Math.sqrt(numSamples))%1 - 0.5*(Math.sqrt(numSamples)-1)/Math.sqrt(numSamples)) / height));
//		System.out.println("xMod: " + (float) (2 * ((sampleNum/Math.sqrt(numSamples))%1 - 0.5*(Math.sqrt(numSamples)-1)/Math.sqrt(numSamples))) + ";yMod: " +
//				(float) (2 * ((Math.floor(sampleNum/Math.sqrt(numSamples))/Math.sqrt(numSamples))%1 - 0.5*(Math.sqrt(numSamples)-1)/Math.sqrt(numSamples))));

//		//gridded monte carlo
//		double xMod = (sampleNum/Math.sqrt(numSamples))%1 - 0.5*(Math.sqrt(numSamples)-1)/Math.sqrt(numSamples);
//		xMod += Math.random()*((1/Math.sqrt(numSamples))%1) - 0.5 * ((1/Math.sqrt(numSamples))%1);
//		double yMod = (Math.floor(sampleNum/Math.sqrt(numSamples))/Math.sqrt(numSamples))%1 - 0.5*(Math.sqrt(numSamples)-1)/Math.sqrt(numSamples);
//		yMod += Math.random()*((1/Math.sqrt(numSamples))%1) - 0.5 * ((1/Math.sqrt(numSamples))%1);
//		hyperShade.setUniformf("xShift", (float) (2 * (xShift + xMod) * aspect / width));
//		hyperShade.setUniformf("yShift", (float) (2 * (yShift + yMod) / height));
//		System.out.println("xMod: " + 2*xMod + ";yMod: " + 2*yMod);
		
//		// Raw Monte Carlo supersample pattern. This tends to flicker on edges. Uses values below .5 as otherwise precision issues lead to problems...
//		hyperShade.setUniformf("xShift", (float) (2 * (xShift + 0.998*Math.random() - 0.499) * aspect / width));
//		hyperShade.setUniformf("yShift", (float) (2 * (yShift + 0.998*Math.random() - 0.499) / height));
		
		// Either no supersampling, or the supersampling is in the fragment shader.
		hyperShade.setUniformf("xShift", (float) (2 * xShift * aspect / width));
		hyperShade.setUniformf("yShift", (float) (2 * yShift / height));
		
		diskquad.render(hyperShade, GL20.GL_TRIANGLE_STRIP);
		hyperShade.end();
	}
	
	// rewritten version yay.
	public Mesh createHyperQuad(boolean isDisk) {
		// if isDisk is true, generate a square quad where the poincare disk will be rendered.
		// if isDisk is false, generate a full-screen quad for filling in the outside with black.
		
		float[] verts = new float[12];
		float xOff = (float) (2*xShift/width);
		float yOff = (float) (2*yShift/height);
		if (!isDisk) {
			xOff = 0.f;
			yOff = 0.f;
		}
		double laspect = (isDisk)?aspect:1;
		
		for (int i = 0; i < verts.length; i++) {
			if (i%3 == 0) {
				verts[i] = (float) ((2*(i%2)-1)/laspect)+xOff;
			} else if (i%3 == 1) {
				verts[i] = ((i<6)?-1:1) + yOff;
			} else verts[i] = 0;
		}
		
		Mesh mesh = new Mesh(false, 4, 0,  // non-static mesh with 4 vertices and no indices
				new VertexAttribute(Usage.Position, 3, "pos"));

		mesh.setVertices(verts);
		return mesh;
	}
	
	
	public void shiftFullQuad(double x, double y) {
		float[] verts = new float[12];
		diskquad.getVertices(verts);
		for (int i = 0; i < verts.length; i++) {
			if (i%3 == 0) {
				verts[i] += (float) (2*x/width);
			} else if (i%3 == 1) {
				verts[i] += (float) (2*y/height);
			}
		}
		diskquad.setVertices(verts);
	}
	
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
		uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.add(startEdit);
		stage.addActor(uiTable);
	}
	
	protected void startEditor(){
		editorWindow = new EditorFrame(this);
		uiTable.reset();
	}
	
	public void shiftGL(double x, double y){
		xShift += x;
		yShift += y;
		shiftFullQuad(x, y);
	}
	
	public void adjustSamples(int samples) {
		numSamples = (double) samples;
	}
	
	public void setInpMode(int mode) {
		stage.removeListener(inpListener);
		inpListener = ModeActionUpdater.changeInpMode(mode, passableGame);
		stage.addListener(inpListener);
	}
	
}
