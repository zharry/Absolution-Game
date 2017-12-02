package GameObject;

import java.awt.Graphics;

public class Player extends GameObject {

	public Player(int x, int y, int colLength, int colWidth) {
		super(x, y, GameObjectAssets.sprPlayer,  colLength, colWidth);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		super.render(g);
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub

	}

}
