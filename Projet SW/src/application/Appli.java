package application;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Appli extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JComboBox<String> choixmonster;
	static JFrame frame = new JFrame("Appli SW");
	GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public Appli() {
			if (device.isFullScreenSupported()) {
				device.setFullScreenWindow(frame);
			} else {
				System.err.println("Le mode plein ecran n'est pas disponible");
	        }
			JPanel haut = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JPanel principal = new JPanel();
			principal.setLayout(new BorderLayout());
			Vector<String> monstretrouver = new Vector<String>();
			String[] tab = Application.Monster();
			for(String toPrint : tab ) {
				if (toPrint != null)
					monstretrouver.add(toPrint);
			}
			choixmonster = new JComboBox<String>(monstretrouver);
			haut.add(choixmonster);
			principal.add(haut, BorderLayout.NORTH);
			
			//listener
			
			listener.Modifprofil modifprof = new listener.Modifprofil();
	        choixmonster.addActionListener(modifprof);
	        
	        
	        // affichage fenetre
			frame.getContentPane().add(principal);
			frame.setPreferredSize(new Dimension(600, 300));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
	}
	
	public static String donnerMonstreSelectionne() {
    	return (String)choixmonster.getSelectedItem();
    }
	public static void fermetureFenetre() {
		frame.dispose();
	}

	public static void main(String[] args) {
		new Appli();
	}
}
