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

public class Application extends JFrame  {
	

	private static final long serialVersionUID = 1L;
	static JFrame frame = new JFrame("Gestionnaire de Mot de Passe");
	private JPanel principal = new JPanel(new BorderLayout());
	private JPanel center = new JPanel();
	private JPanel north = new JPanel();
	private JPanel south = new JPanel(new FlowLayout());
	private JLabel text = new JLabel("Qui est-ce ?");
	private Border border = BorderFactory.createLineBorder(Color.black, 5);
	private ArrayList<JPanel> liste_img = new ArrayList<>();;
	private File f = new File("Profil.json");
	
	public Application() {
		
		//code Swing
		File newf = new File("./../../bin/App/Application.class");
		if(newf.exists()) {
			System.out.println("sa marche il est la");
		}
		if(f.exists()){
			System.out.println("coucou");
			try {
				JSONParser jsonP = new JSONParser();
				JSONObject jsonO = (JSONObject)jsonP.parse(new FileReader(new File("Profil.json")));
				JSONArray fields = (JSONArray) jsonO.get("Profil");
			    for(Object field: fields) {
			    	JSONObject jsonObj = (JSONObject) field;
			    	String img_profil= (String) jsonObj.get("Image_de_Profil");
			    	String txt_profil= (String) jsonObj.get("Pseudo");
			    	image(img_profil,txt_profil,(Integer.parseInt((String) jsonObj.get("Verif_Key"))));
			    }
			}
			catch (FileNotFoundException e) {
		    	  System.out.println("fichier non trouvé");
		      } 
			catch (IOException e) {
		    	  System.out.println("io exception");
		      } 
			catch (ParseException e) {
		         System.out.println("parse exception");
			}
		}
		image("+","+",1);

		for(JPanel t : liste_img) {
			center.add(t);
		}
		
		
		JButton refresh = new JButton("Refresh");
		refresh.setBackground(Color.black);
		refresh.setFont(new Font("Serif",Font.BOLD,50));
		refresh.setForeground(Color.white);
		south.setBackground(Color.black);
		south.add(refresh,FlowLayout.LEFT);
		refresh.addActionListener((ActionListener) new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				center.removeAll();
				liste_img.removeAll(liste_img);
				frame.dispose();
				new Application();
			}
			
		});
		center.setBackground(Color.black);
		north.setBackground(Color.black);
		text.setFont(new Font("Serif",Font.BOLD,100));
		text.setForeground(Color.white);
		north.add(text);
		principal.add(north,BorderLayout.NORTH);
		principal.add(center,BorderLayout.CENTER);
		principal.add(south,BorderLayout.SOUTH);
		//Listener
		
		
		// affichage fenetre
		frame.getContentPane().add(principal).setBackground(Color.black);;
		frame.setPreferredSize(new Dimension(1000,1500));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		
	}
	

	public void image(String nom,String pseudo,int verif_key) {
		JPanel imgandtxt = new JPanel();
		JLabel label = new JLabel();
		JLabel img_txt = new JLabel(pseudo);
		img_txt.setLayout(new FlowLayout());
		img_txt.setFont(new Font("Serif",Font.BOLD,50));
		img_txt.setForeground(Color.white);
        try {
            Image img = ImageIO.read(new FileInputStream("src/image/"+ nom +".jpg"));
            label.setIcon(new ImageIcon(img));
            label.addMouseListener((MouseListener) new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("le perso clické est :"+nom);
                    if(nom.equals("+")) {
                    	new Creation_Profil();
                    	frame.dispose();
                    }else if(verif_key==0){
                    	new GenerateKeyProfil();
                    }else {
                    	new Loading_Profil();
                    }
                }
                @Override
                public void mousePressed(MouseEvent e) {
                }
                @Override
                public void mouseExited(MouseEvent e) {

                }
                @Override
                public void mouseEntered(MouseEvent e) {

                }
                
                public void mouseReleased(MouseEvent e) {
                     
                }
            });
        } catch (IOException ex) {
        }
        
        label.setBorder(border);
        imgandtxt.setLayout(new BorderLayout());
        imgandtxt.add(label,BorderLayout.NORTH);
        imgandtxt.add(img_txt,BorderLayout.CENTER);
        imgandtxt.setBackground(Color.black);
        liste_img.add(imgandtxt);
        //center.add(imgandtxt);
	}
	public void supp_center() {
		
	}

	public static void main(String[] args) {
		new Application();
		
		
	}

}
