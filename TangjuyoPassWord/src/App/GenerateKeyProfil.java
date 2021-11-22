package App;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GenerateKeyProfil extends JFrame  {
	

	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Creation De la Clé");
	private JPanel principal = new JPanel(new BorderLayout());
	private JPanel center = new JPanel(new GridLayout(2,2));
	private JPanel north = new JPanel();
	private JPanel south = new JPanel();
	private JPanel choix_avatar = new JPanel();
	private JLabel text = new JLabel("Création de sa Clé de sécurité ?");
	private static JButton generateKey;
	private Border border = BorderFactory.createLineBorder(Color.black, 5);
	static int compteur_nbre_profil =0;
	
	public GenerateKeyProfil() {


			generateKey = new JButton("Generate a Key");
			generateKey.setBackground(Color.black);
			generateKey.setPreferredSize(new Dimension(263,300));
			generateKey.setFont(new Font("Serif",Font.BOLD,75));
			generateKey.setForeground(Color.white);
			generateKey.setBorder(border);
			generateKey.addActionListener((ActionListener) new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String key = Crypting.generateKey();
					new GenerateKeyProfil(key);
					frame.dispose();
				}
				
			});
			choix_avatar.setBackground(Color.black);
			JLabel img_txt = new JLabel("Recommended :   ");
			img_txt.setFont(new Font("Serif",Font.BOLD,75));
			img_txt.setForeground(Color.white);
			center.add(img_txt);
			center.add(generateKey);
			choix_avatar.setBackground(Color.black);
						
			
			
			//choix pseudo
			JLabel choix_pseudo = new JLabel("Not Recommended (only 16 Character) :  ");
			JTextField txtfield = new JTextField();
			txtfield.setBackground(Color.black);
			txtfield.setForeground(Color.white);
			txtfield.setPreferredSize(new Dimension(100,100));
			txtfield.setFont(new Font("Serif",Font.BOLD,100));
			choix_pseudo.setFont(new Font("Serif",Font.BOLD,50));
			choix_pseudo.setForeground(Color.white);
			center.add(choix_pseudo);
			center.add(txtfield);
			center.setBackground(Color.BLACK);
			

		
			north.setBackground(Color.black);
			text.setFont(new Font("Serif",Font.BOLD,100));
			text.setForeground(Color.white);
			north.add(text);
			
			
			
			principal.add(north,BorderLayout.NORTH);
			principal.add(center,BorderLayout.CENTER);
			principal.add(south,BorderLayout.SOUTH);

		
		// affichage fenetre
		frame.getContentPane().add(principal).setBackground(Color.black);;
		frame.setPreferredSize(new Dimension(1000,1500));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		
	}
	
public GenerateKeyProfil(String key) {
	// affichage fenetre
			JFrame newFrame = new JFrame();
			JPanel newPanel = new JPanel(new GridLayout(3,1));
			JLabel txt = new JLabel();
			txt.setText("You're Key is :           "+key+"");
			txt.setBackground(Color.black);
			txt.setFont(new Font("Serif",Font.BOLD,50));
			txt.setForeground(Color.white);
			JLabel txt2 = new JLabel();
			txt2.setText("(THIS IS THE ONLY TIME YOU WILL SEE HER, PLEASE WRITE IT DOWN ON A POST-IT)");
			txt2.setBackground(Color.black);
			txt2.setFont(new Font("Serif",Font.BOLD,30));
			txt2.setForeground(Color.white);
			newPanel.add(txt);
			newPanel.add(txt2);
			JButton leave = new JButton("Go on PassWord Manager");
			leave.setBackground(Color.black);
			leave.setFont(new Font("Serif",Font.BOLD,30));
			leave.setForeground(Color.white);
			leave.addActionListener((ActionListener) new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			newPanel.add(leave);
			
			
			newFrame.getContentPane().add(newPanel).setBackground(Color.black);;
			newFrame.setPreferredSize(new Dimension(1000,1500));
			newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			newFrame.pack();
			newFrame.setVisible(true);
			JFrame.setDefaultLookAndFeelDecorated(true);
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	}

public static void main(String[] args) {
	new GenerateKeyProfil();
}
}
