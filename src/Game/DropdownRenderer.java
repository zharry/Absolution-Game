package Game;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

@SuppressWarnings({ "rawtypes", "serial" })
public class DropdownRenderer extends JPanel implements ListCellRenderer {

	private JLabel labelItem = new JLabel();

	public DropdownRenderer() {
		setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(1, 2, 1, 2);
        labelItem.setOpaque(true);
        labelItem.setHorizontalAlignment(JLabel.LEFT);
        add(labelItem, constraints);
        setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		// Create Element
		DropdownItem item = (DropdownItem) value;
		labelItem.setText(item.getDesc());
		labelItem.setIcon(new ImageIcon(item.getIcon()));

		if (isSelected) {
			labelItem.setBackground(Color.GRAY);
			labelItem.setForeground(Color.WHITE);
		} else {
			labelItem.setForeground(Color.BLACK);
			labelItem.setBackground(Color.LIGHT_GRAY);
		}

		return this;
	}

}
