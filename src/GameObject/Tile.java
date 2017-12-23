package GameObject;

import java.awt.Graphics;

import Handler.GameObjectAssets;

public class Tile extends GameObject {

	private static final long serialVersionUID = 8584770631071877315L;
	public int type, variation;

	public Tile(int x, int y, int type, int variation) {
		super(x, y, 32, 32, 0, 0);
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
	}

	@Override
	public void onCollide() {
	}

}
