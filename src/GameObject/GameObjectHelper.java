package GameObject;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Handler.Helper;

public class GameObjectHelper {

	public static final String ASSETSDIR = "Assets/";

	// Game Assets
	public static BufferedImage[] sprPlayer = new BufferedImage[361];

	public static void initSprites() {
		// Load Sprites
		try {
			sprPlayer[0] = ImageIO.read(new File(ASSETSDIR + "Player.png"));
		} catch (IOException e) {
			Helper.displayError("Failed to load Game Assets, please re-download the game and try again!");
			System.exit(-1);
		}
		// Calculate Rotations
		AffineTransformOp op;
		for (int i = 1; i < 361; i++) {
			op = new AffineTransformOp(AffineTransform.getRotateInstance(Math.toRadians(i), sprPlayer[0].getWidth() / 2,
					sprPlayer[0].getHeight() / 2), AffineTransformOp.TYPE_BILINEAR);
			sprPlayer[i] = op.filter(sprPlayer[0], null);
		}
	}

}
