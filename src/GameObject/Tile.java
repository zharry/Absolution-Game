package GameObject;

import java.awt.Graphics;

public class Tile extends GameObject {

	private static final long serialVersionUID = 8584770631071877315L;
	private int type, variation;

	public Tile(int x, int y, int type, int variation) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.variation = variation;
	}

	public void incVar() {
		variation++;
	}

	public void decVar() {
		variation--;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(GameObjectAssets.idToAsset(type, variation), this.x, this.y, null);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub

	}

}
