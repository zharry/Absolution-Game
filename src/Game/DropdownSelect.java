package Game;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

@SuppressWarnings({ "serial", "rawtypes" })
public class DropdownSelect extends JComboBox {

	private DefaultComboBoxModel<DropdownItem> model;

	@SuppressWarnings("unchecked")
	public DropdownSelect() {
		model = new DefaultComboBoxModel<DropdownItem>();
		setModel(model);
		setRenderer(new DropdownRenderer());
		setEditor(new DropdownEditor());
	}

	public void addItems(DropdownItem[] items) {
		for (DropdownItem i : items) {
			model.addElement(i);
		}
	}

}
