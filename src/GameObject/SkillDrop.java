package GameObject;

import java.awt.Color;
import java.awt.Graphics;

import Handler.GameObjectAssets;
import Handler.GameObjectRegistry;

public class SkillDrop extends GameObject {

	private static final long serialVersionUID = 1L;
	private int variation;

	public SkillDrop(int x, int y, int variation) {
		super(x, y, 16, 16, 0, 0);
		this.variation = variation;
	}

	public int getVariation() {
		System.out.println("Added skill in slot: " + variation);
		return variation;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(x - 4, y + 10, 24, 8);
		g.drawImage(GameObjectAssets.idToAsset(GameObjectRegistry.DROP_SKILL, variation), x, y, 16, 16, null);
	}

	@Override
	public void onCollide() {
	}

}
