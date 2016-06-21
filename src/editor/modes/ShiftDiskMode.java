package editor.modes;

import baseSystems.EditorFrame.Mode;

public class ShiftDiskMode implements Mode {
	public static void setShift(double x, double y) {
		game.shiftGL(-game.xShift, -game.yShift);
		game.shiftGL(x, y);
	}
	
	@Override
	public int modeType() { return 1; }
}
