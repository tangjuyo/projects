package App;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Loading_Profil {
	static JFrame frame = new JFrame("Gestionnaire de Mot de Passe");
	private JPanel principal = new JPanel(new BorderLayout());

	public Loading_Profil() {
		
		JLabel test = new JLabel("Entrez la clé :");
		JTextField jt = new JTextField("tu est la");
		principal.setMaximumSize(new Dimension(150,30));
		principal.add(test);
		principal.add(jt);
		frame.getContentPane().add(principal).setBackground(Color.black);;

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	public static void main(String[] args) {
		new Loading_Profil();
	}
}
