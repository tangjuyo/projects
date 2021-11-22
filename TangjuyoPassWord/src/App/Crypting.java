package App;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Crypting {
	
	private static String nom_profil;
	private static ArrayList<String> entreprise = new ArrayList<String>();
	private static ArrayList<String> mdp = new ArrayList<String>();
	private static final String ALGORITHM = "AES";
	private static  String contentkey= generateKey();
	private static String content;
	private static File file;
	private static String  newLine = System.getProperty("line.separator");
	
	
	@SuppressWarnings("static-access")
	public Crypting(String nom_profil) {
		this.setNom_profil(nom_profil);
		file = new File("src/Profil_TxT/"+nom_profil+"Password.json");
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
	    	  System.out.println(".");
	      } 
		catch (ParseException e) {
	         System.out.println(".");
		}
		return null;
	}
	
	
	private static String build_stringjson() {

		
		if(entreprise.size() != mdp.size()) {
			return "sa marche po";
		}
		content = "{"+newLine+"\""+getNom_profil()+"\":	["+newLine+"]"+newLine+"}";
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
		return content;
	}

	private static void ajout_stringjson(String noment,String motdepasse) {

		String test = null;
		try {
			JSONObject jsonO = getParse(file);
			test = jsonO.toString();
		}catch (Exception e){
			e.printStackTrace();
			}
			System.gc();
			file.delete();
			String test_ajout = test.substring(0, test.length()-2);
			if(entreprise.size() == 0) {
				entreprise.add(encrypt(noment,contentkey));
				mdp.add(encrypt(motdepasse,contentkey));
				test_ajout+= "{"+newLine+"		\"Entreprise\": \""+encrypt(noment,contentkey)+"\","+newLine+"		\"motdepasse\": \""+encrypt(motdepasse,contentkey)+"\""+newLine+"	}";
			}else {
				entreprise.add(encrypt(noment,contentkey));
				mdp.add(encrypt(motdepasse,contentkey));
				test_ajout+= "	,{"+newLine+"		\"Entreprise\": \""+encrypt(noment,contentkey)+"\","+newLine+"		\"motdepasse\": \""+encrypt(motdepasse,contentkey)+"\""+newLine+"	}";
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
			System.out.println(test_ajout);
	}
	


	public static String getNom_profil() {
		return nom_profil;
	}
	public static void setNom_profil(String nom_profil) {
		Crypting.nom_profil = nom_profil;
	}
	
	private static Key generateKeyFromString(final String secKey) throws Exception {
		final byte[] keyVal = Base64.getDecoder().decode(secKey);
	    final Key key = new SecretKeySpec(keyVal, ALGORITHM);
	    return key;
	}
	
	private static  String encrypt(final String valueEnc, final String secKey) { 

	    String encryptedVal = null;

	    try {
	        final Key key = generateKeyFromString(secKey);
	        final Cipher c = Cipher.getInstance(ALGORITHM);
	        c.init(Cipher.ENCRYPT_MODE, key);
	        final byte[] encValue = c.doFinal(valueEnc.getBytes());
	        encryptedVal = Base64.getEncoder().encodeToString(encValue);
	    } catch(Exception ex) {
	        System.out.println("The Exception is=" + ex);
	    }

	    return encryptedVal;
	}
	
	private static  String decrypt(final String encryptedValue, final String secretKey) {

	    String decryptedValue = null;

	    try {

	        final Key key = generateKeyFromString(secretKey);
	        final Cipher c = Cipher.getInstance(ALGORITHM);
	        c.init(Cipher.DECRYPT_MODE, key);
	        final byte[] decorVal = Base64.getDecoder().decode(encryptedValue);
	        final byte[] decValue = c.doFinal(decorVal);
	        decryptedValue = new String(decValue);
	    } catch(Exception ex) {
	        System.out.println("The Exception is=" + ex);
	    }

	    return decryptedValue;
	}
	
	public static String generateKey() {

		KeyGenerator firstkey = null;
		try {
			firstkey = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		SecretKey key = firstkey.generateKey();
		key = firstkey.generateKey();
		String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
		return encodedKey;
	}

	public static void main(String[] args) throws IOException {
        String profil = "Tangjuyo";
	    new Crypting(profil);
	    build_stringjson();
		ajout_stringjson("ent1","mdp1");
		ajout_stringjson("ent2","mdp2");
		ajout_stringjson("ent3","mdp3");
		
		try {
			JSONObject jsonO = getParse(file);
			JSONArray fields = (JSONArray) jsonO.get("Tangjuyo");
		    for(Object field: fields) {
		    	JSONObject jsonObj = (JSONObject) field;
		    	String entreprise = (String) jsonObj.get("Entreprise");
		    	System.out.println(decrypt(entreprise,contentkey));
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}


