package GameObject;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

	private static final long serialVersionUID = 5395933748344835733L;

	protected int x, y, velX, velY;

	protected int colLength, colWidth, colXOffset, colYOffset;
	protected boolean noCol;

	public GameObject(int x, int y, int colWidth, int colLength, int colXOffset, int colYOffset) {
		this(colWidth, colLength, colXOffset, colYOffset);
		this.x = x;
		this.y = y;
	}

	public GameObject(int colWidth, int colLength, int colXOffset, int colYOffset) {
		x = 0;
		y = 0;
		velX = 0;
		velY = 0;
		this.colLength = colLength;
		this.colWidth = colWidth;
		if (colLength == 0 && colWidth == 0)
			noCol = true;
		else
			noCol = false;
	}

	public GameObject() {
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract void onCollide();

	public boolean isCollide(GameObject other) {
		if (noCol)
			return false;
		if ((this.x < other.x + other.colLength && this.x + this.colLength > other.x
				&& this.y < other.y + other.colWidth && this.y + this.colWidth > other.y))
			return true;
		return false;
	}

	// All but one sprite is 32x32, so collision can be done by checking numbers
	// The one exception is meant to be treated as though it is a 32x32 anyways
	public boolean isSprCollide(GameObject other) {
		if ((this.x < other.x + 32 && this.x + 32 > other.x && this.y < other.y + 32 && this.y + 32 > other.y))
			return true;
		return false;
	}

	public void move() {
		this.x += this.velX;
		this.y += this.velY;
	}

}
