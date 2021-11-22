package application;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Appli_profil extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Appli_profil(String titre) {
		JFrame frame = new JFrame(titre);
		String url = "img_monstre/"+ Appli.donnerMonstreSelectionne() + " (" +Application.getAttribute(Appli.donnerMonstreSelectionne()) + ")" + "/" +Appli.donnerMonstreSelectionne()+".png";
		System.out.println(url);
		ImageIcon icone = new ImageIcon(url);
		JLabel img = new JLabel(icone, JLabel.CENTER);
		frame.getContentPane().add(img);
		frame.validate();
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setLocationRelativeTo(null);
	      frame.setPreferredSize(new Dimension(400, 300));
	      frame.getContentPane().setBackground(Color.white);
	      frame.pack();
	      frame.setVisible(true);
	}

}
