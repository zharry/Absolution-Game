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
			this.velY = -this.moveDist;
		if (this.goDown)
			this.velY = this.moveDist;
		if (this.goLeft)
			this.velX = -this.moveDist;
		if (this.goRight)
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
		BufferedImage toDraw = GameObjectAssets.playerToAsset(lastDir, curFrame);
		if (this.goUp)
			toDraw = GameObjectAssets.playerToAsset(lastDir = 0, curFrame);
		if (this.goDown)
			toDraw = GameObjectAssets.playerToAsset(lastDir = 2, curFrame);
		if (this.goLeft)
			toDraw = GameObjectAssets.playerToAsset(lastDir = 3, curFrame);
		if (this.goRight)
			toDraw = GameObjectAssets.playerToAsset(lastDir = 1, curFrame);
		g.drawImage(toDraw, x, y, null);
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub

	}

}
