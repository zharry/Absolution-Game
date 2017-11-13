package GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

	private static final long serialVersionUID = 5395933748344835733L;

	protected int x, y, velX, velY, rotate; // Rotate is Degrees CW from North
											// (So 90Deg = 0 Deg)
	protected int colLength, colWidth;
	protected boolean noCol;
	protected transient BufferedImage[] sprite; // Array of sprite Rotations per Degree

	public GameObject(int x, int y, BufferedImage[] sprite, int rotate, int colLength, int colWidth) {
		this(sprite, rotate, colLength, colWidth);
		this.x = x;
		this.y = y;
	}

	public GameObject(BufferedImage[] sprite, int rotate, int colLength, int colWidth) {
		x = 0;
		y = 0;
		velX = 0;
		velY = 0;
		this.rotate = rotate;
		this.colLength = colLength;
		this.colWidth = colWidth;
		if (colLength == 0 && colWidth == 0)
			noCol = true;
		else
			noCol = false;
		this.sprite = sprite;
	}

	public abstract void tick();

	public void render(Graphics g) {
		g.drawImage(GameObjectHelper.sprPlayer[this.rotate], this.x, this.y, null);
//		g.drawImage(this.sprite[this.rotate], this.x, this.y, null);
	}

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
