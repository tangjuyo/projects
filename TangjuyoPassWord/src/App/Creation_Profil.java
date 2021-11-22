package App;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Creation_Profil extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Creation D'un Profil");
	private JPanel principal = new JPanel(new BorderLayout());
	private JPanel center = new JPanel(new GridLayout(2,2));
	private JPanel north = new JPanel();
	private JPanel south = new JPanel();
	private JPanel choix_avatar = new JPanel();
	private JLabel text = new JLabel("Création d'un Profil ?");
	private static JComboBox<Object> choiximg;
	private Border border = BorderFactory.createLineBorder(Color.black, 5);
	static int compteur_nbre_profil =0;
	private static File file = new File("Profil.json");
	private static String content;
	private String current_profil;
	private static String  newLine = System.getProperty("line.separator");
	
	public Creation_Profil() {
		
		if(!file.exists()) {
			build_stringjson();
		}
			//choix avatar
			Object[] items =
	        {
	            new ImageIcon("src/image/luffy.jpg"),
	            new ImageIcon("src/image/chopper.jpg"),
	            new ImageIcon("src/image/brock.jpg"),
	            new ImageIcon("src/image/nami.jpg"),
	            new ImageIcon("src/image/robin.jpg"),
	            new ImageIcon("src/image/sanji.jpg"),
	            new ImageIcon("src/image/franky.jpg")
	        };
			choiximg= new JComboBox<Object>(items);
			choiximg.setBackground(Color.black);
			choiximg.setPreferredSize(new Dimension(263,300));
			choix_avatar.setBackground(Color.black);
			JLabel img_txt = new JLabel("Choix Avatar :   ");
			img_txt.setFont(new Font("Serif",Font.BOLD,75));
			img_txt.setForeground(Color.white);
			center.add(img_txt);
			center.add(choiximg);
			choix_avatar.setBackground(Color.black);
						
			
			
			//choix pseudo
			JLabel choix_pseudo = new JLabel("Choix Pseudonyme :  ");
			JTextField txtfield = new JTextField();
			txtfield.setBackground(Color.black);
			txtfield.setForeground(Color.white);
			txtfield.setPreferredSize(new Dimension(100,100));
			txtfield.setFont(new Font("Serif",Font.BOLD,100));
			choix_pseudo.setFont(new Font("Serif",Font.BOLD,75));
			choix_pseudo.setForeground(Color.white);
			center.add(choix_pseudo);
			center.add(txtfield);
			center.setBackground(Color.BLACK);
			

			//bouton creer
			JButton jb = new JButton("Créer");
			jb.setText("Créer");
			jb.setBackground(Color.black);
			jb.setForeground(Color.white);
			jb.setFont(new Font("Serif",Font.BOLD,100));
			south.add(jb,BorderLayout.EAST);
			south.setBackground(Color.black);
			south.setBorder(border);
			jb.addMouseListener((MouseListener) new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					if(choiximg.getSelectedIndex() == 0) {
						//luffy
						current_profil = "luffy";
					}else if(choiximg.getSelectedIndex() == 1){
						//chopper
						current_profil = "chopper";
					}else if(choiximg.getSelectedIndex() == 2){
						//brock
						current_profil = "brock";
					}else if(choiximg.getSelectedIndex() == 3){
						//nami
						current_profil = "nami";
					}else if(choiximg.getSelectedIndex() == 4){
						//robin
						current_profil = "robin";
					}else if(choiximg.getSelectedIndex() == 5){
						//sanji
						current_profil = "sanji";
					}else if(choiximg.getSelectedIndex() == 6){
						//franky
						current_profil = "franky";
					}

					System.out.println(current_profil);
					ajout_ProfilJson(txtfield.getText(),current_profil);
					new Application();
					frame.dispose();
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				
				
			});
			
			
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
	
	public JLabel image(String nom) {
		JLabel label = new JLabel();
        try {
            Image img = ImageIO.read(new FileInputStream("src/image/"+ nom +".jpg"));
            label.setIcon(new ImageIcon(img));
            label.addMouseListener((MouseListener) new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("le perso clické est :"+nom);
                }
                @Override
                public void mousePressed(MouseEvent e) {
                }
                @Override
                public void mouseExited(MouseEvent e) {
                	border = BorderFactory.createLineBorder(Color.black, 5);
                	label.setBorder(border);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                	border = BorderFactory.createLineBorder(Color.white, 5);
                	label.setBorder(border);
                }
                
                public void mouseReleased(MouseEvent e) {
                     
                }
            });
        } catch (IOException ex) {
        }
        
        label.setBorder(border);
        return label;
	}
	
	public static JSONObject getParse(File test) {
		try {
			JSONParser jsonP = new JSONParser();
			JSONObject jsonO = (JSONObject)jsonP.parse(new FileReader(test));
			return jsonO;
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
		return null;
	}
	
private static String build_stringjson() {

	
		content = "{"+newLine+"\""+"Profil"+"\":	["+newLine+"]"+newLine+"}";
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			 fw = new FileWriter(file,true);
			 bw = new BufferedWriter(fw);
			 bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            try {
                if (bw != null) {
                    bw.close();
                }
               

                if (fw != null) {
                	fw.close();
                }
                    
            } catch (IOException ex) {
                System.err.format("IOException: %s%n", ex);
            }
		}
		System.out.println(content.length());
		return content;
	}

	private static void ajout_ProfilJson(String Pseudo,String img_profil) {

		String test = null;
		try {
			JSONObject jsonO = getParse(file);
			test = jsonO.toString();
			System.gc();
			file.delete();
			String test_ajout = test.substring(0, test.length()-2);
			if(test_ajout.length()<=15) {
				test_ajout+= "{"+newLine+"		\"Pseudo\": \""+Pseudo+"\","+newLine+"		\"Image_de_Profil\": \""+img_profil+"\""+newLine+", 	\"Verif_Key\": \"0\" }";
		    }else {
			    test_ajout+= "	,{"+newLine+"		\"Pseudo\": \""+Pseudo+"\","+newLine+"		\"Image_de_Profil\": \""+img_profil+"\""+newLine+", 	\"Verif_Key\": \"0\" }";
			}
			test_ajout+= "]"+newLine+"}";
			content = test_ajout;
			BufferedWriter bw = null;
			FileWriter fw = null;
			try {
				file.createNewFile();
				fw = new FileWriter(file,true);
				bw = new BufferedWriter(fw);
				bw.write(content);
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();
				} catch (IOException ex) {
					System.err.format("IOException: %s%n", ex);
					}
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Creation_Profil();
	}
}
