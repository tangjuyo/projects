package application;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Import_appli extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JFrame Import_frame = new JFrame("Import de son fichier");
	private JFileChooser fichier;
	private GraphicsEnvironment mainScreen = GraphicsEnvironment.getLocalGraphicsEnvironment();
	public Import_appli() {
		System.out.println(mainScreen);
		JPanel test = new JPanel();
		fichier = new JFileChooser(new File("."));
		File imported;
		Import_frame.add(test);
		if (fichier.showOpenDialog(Import_frame) == JFileChooser.APPROVE_OPTION) {
			imported = fichier.getSelectedFile();
			new Application(imported);
			new Appli();
		} else {
			imported = null;
			System.out.println("Opération cancel");
			//new Import_appli();
		}
		
		Import_frame.setPreferredSize(new Dimension(600, 600));
		//listener
		
//		listener.Import checkimport = new listener.Import(this);
//        fichier.addActionListener(checkimport);
        
        
//		// affichage fenetre
//		Import_frame.getContentPane().add(principal);
//		Import_frame.setPreferredSize(new Dimension(600, 300));
//		Import_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		Import_frame.pack();
//		Import_frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Import_appli();
	}
}