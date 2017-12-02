package MainGame;

import java.awt.image.BufferedImage;

public class DropdownItem {

	public int val;
	public String desc;
	public BufferedImage icon;

	public DropdownItem(int val, String desc, BufferedImage icon) {
		this.val = val;
		this.desc = desc;
		this.icon = icon;
	}
	
	public String toString() {
		return val + ": " + desc;
	}

}
