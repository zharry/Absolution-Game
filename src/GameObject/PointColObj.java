package GameObject;

import java.awt.Graphics;

public class PointColObj extends GameObject {

	private static final long serialVersionUID = -4795857450456537774L;

	public PointColObj(int gridLockX, int gridLockY) {
		super(gridLockX, gridLockY, 1, 1, 0, 0);
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void tick() {
	}

	@Override
	public void onCollide() {
	}

}
