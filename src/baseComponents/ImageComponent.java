/**
 * 
 */
package baseComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ImageComponent<T> implements Component, Poolable {
	public String imgname;
	public T image;
	public int frame;
	public int framecount;
	public boolean canRotate;
	public int zOrd;
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
}