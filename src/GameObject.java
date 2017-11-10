import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GameObject {

	private double x, y, velX, velY, rotate;
	private double colLength, colWidth;
	private boolean noCol;
	private BufferedImage[] sprite; // Array of sprite Rotations per Degree

	public GameObject(BufferedImage[] sprite, double colLength, double colWidth) {
		x = 0;
		y = 0;
		velX = 0;
		velY = 0;
		rotate = 90;
		if (colLength == 0 && colWidth == 0)
			noCol = true;
		else
			noCol = false;
		this.sprite = sprite;
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

	public void move() {
		this.x += this.velX;
		this.y += this.velY;
	}

	// GameObject Registry
	static final int PLAYER = 0;

}
