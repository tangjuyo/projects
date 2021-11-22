package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.Import_appli;

public class Import implements ActionListener {
	private application.Import_appli appli;
	public Import(Import_appli a) {
		this.appli = a;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
