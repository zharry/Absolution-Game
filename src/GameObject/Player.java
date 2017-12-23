package GameObject;

import java.awt.Graphics;

import Handler.GameObjectAssets;

public class Player extends GameObject {

	private static final long serialVersionUID = 3038088647945748368L;

	// Movement
	public boolean goUp = false, goDown = false, goLeft = false, goRight = false;
	int moveDist = 1;

	// Animation
	public int curFrame = 0, frameTimer = 0, lastDir = 0;
	final int ticksPerFrame = 14;
	boolean freezeFrame;

	// Moveback
	public boolean moveBack = false;
	
	// Inventory
	public int[] skillCount;

	public Player(int x, int y) {
		super(x, y, 16, 5, 8, 38);
		skillCount = new int[7];
	}

	@Override
	public void tick() {
		// Previous tick collision response
		if (moveBack) {
			this.x -= velX;
			this.y -= velY;
		}

		// Check Keys
		this.velX = 0;
		this.velY = 0;
		if (this.goUp) {
			lastDir = 0;
			this.velY = -this.moveDist;
		}
		if (this.goDown) {
			lastDir = 2;
			this.velY = this.moveDist;
		}
		if (this.goLeft) {
			lastDir = 3;
			this.velX = -this.moveDist;
		}
		if (this.goRight) {
			lastDir = 1;
			this.velX = this.moveDist;
		}
		if (this.goUp && this.goDown) {
			this.velY = 0;
		}
		if (this.goLeft && this.goRight) {
			this.velX = 0;
		}

		// Set New Position
		this.x += this.velX;
		this.y += this.velY;

		if (!goUp && !goDown && !goLeft && !goRight) {
			freezeFrame = true;
			curFrame = 0;
			frameTimer = 0;
		} else
			freezeFrame = false;
		if (!freezeFrame) {
			frameTimer++;
			if (frameTimer > ticksPerFrame) {
				frameTimer = 0;
				curFrame = (curFrame + 1) % 4;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(GameObjectAssets.sprPlayer[lastDir][curFrame], x, y, null);
	}

	@Override
	public void onCollide() {
	}

}
