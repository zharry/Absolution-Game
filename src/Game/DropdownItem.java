package Game;

import java.awt.image.BufferedImage;

public class DropdownItem {

	private int val;
	private String desc;
	private BufferedImage icon;

	public DropdownItem(int val, String desc, BufferedImage icon) {
		this.val = val;
		this.desc = desc;
		this.icon = icon;
	}

	public String toString() {
		return val + ": " + desc;
	}

	public int getVal() {
		return val;
	}
	
	public String getDesc() {
		return desc;
	}

	public BufferedImage getIcon() {
		return icon;
	}

}
