package GameObject;

import java.awt.Graphics;

public class CheckPoint extends GameObject {

	private static final long serialVersionUID = -3234320293018133982L;

	// True = Start
	// False = End Point
	private boolean state;

	public CheckPoint(int x, int y, boolean start) {
		super(x, y, 0, 0, 0, 0);
		state = start;
	}

	public boolean isStart() {
		return state;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void onCollide() {
	}

}
