import java.awt.Graphics;

public class Player extends GameObject {

	public Player(int x, int y, int rotate, int colLength, int colWidth) {
		super(x, y, AbsolutionGame.sprPlayer, rotate, colLength, colWidth);
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
