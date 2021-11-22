package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.Appli;
import application.Appli_profil;


public class Modifprofil implements ActionListener{
	
	public Modifprofil() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new Appli_profil("Profil :" + Appli.donnerMonstreSelectionne());
		application.Appli.fermetureFenetre();
	}
}
