package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Handler.GameObjectAssets;

public class Player extends GameObject {

	private static final long serialVersionUID = 3038088647945748368L;

	// Movement
	boolean goUp = false, goDown = false, goLeft = false, goRight = false;
	int moveDist = 4;

	// Animation
	int curFrame = 0, frameTimer = 0, ticksPerFrame = 8;
	int lastDir = 0;

	public Player(int x, int y) {
		super(x, y, 13, 5, 9, 41);
	}

	@Override
	public void tick() {
		if (this.goUp)
			lastDir = 2;
			this.velY = -this.moveDist;
		if (this.goDown)
			lastDir = 0;
			this.velY = this.moveDist;
		if (this.goLeft)
			lastDir = 3;
			this.velX = -this.moveDist;
		if (this.goRight)
			lastDir = 1;
			this.velX = this.moveDist;
		if (this.goUp && this.goDown)
			this.velY = 0;
		if (this.goLeft && this.goRight) {
			this.velX = 0;
		}
		this.x += this.velX;
		this.y += this.velY;

		frameTimer++;
		if (frameTimer > ticksPerFrame) {
			frameTimer = 0;
			curFrame = (curFrame + 1) % 4;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(GameObjectAssets.sprPlayer[lastDir][curFrame], x, y, null);
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub

	}

}
