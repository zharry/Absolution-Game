package Game;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxEditor;

public class DropdownEditor extends BasicComboBoxEditor {
	private JPanel panel = new JPanel();
	private JLabel labelItem = new JLabel();
	private DropdownItem selectedValue;

	public DropdownEditor() {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(2, 5, 2, 2);

		labelItem.setOpaque(false);
		labelItem.setHorizontalAlignment(JLabel.LEFT);
		labelItem.setForeground(Color.BLACK);

		panel.add(labelItem, constraints);
		panel.setBackground(new Color(0xD0D0D0));
	}

	public Component getEditorComponent() {
		return this.panel;
	}

	public Object getItem() {
		return this.selectedValue;
	}

	public void setItem(Object obj) {
		if (obj == null) {
			return;
		}
		DropdownItem item = (DropdownItem) obj;
		selectedValue = item;
		labelItem.setText(selectedValue.getDesc());
		labelItem.setIcon(new ImageIcon(item.getIcon()));
	}
}
