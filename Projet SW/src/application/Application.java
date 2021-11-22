package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import bd_monstres.BD_monstre;
import bd_monstres.Runes;



public class Application{
	static int i=0;
	
	static File test;
	
	public Application(File imported) {
		Application.test = imported;
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
	
	public static long getMana() {
		JSONObject jsonO =  getParse(test);
		JSONObject obj =  (JSONObject) jsonO.get("wizard_info");
		long mana = (long) obj.get("wizard_mana");
		return mana;
	}
	
	public static long getCrystal() {
		
		JSONObject jsonO = getParse(test);
		JSONObject obj =  (JSONObject) jsonO.get("wizard_info");
		long crystal = (long) obj.get("wizard_crystal");
		return crystal;

	}
	
	public static String[] Monster() {
		long tab[] = new long[10000];
		String [] strValues = new String[10000];
		String [] Final = new String[10000];
		JSONObject jsonO = getParse(test);
	    JSONArray fields = (JSONArray) jsonO.get("unit_list");
	    for(Object field: fields) {
	    	JSONObject jsonObj = (JSONObject) field;
	    	long monstre = (long) jsonObj.get("unit_master_id");
	    	tab[i] = monstre;
	    	String s = String.valueOf(tab[i]);
	    	strValues[i] = s;
	    	if (BD_monstre.GetMonster(strValues[i]) == "" || BD_monstre.GetMonster(strValues[i]) == null) {
	    	} 
	    	else {
	    		Final[i] = BD_monstre.GetMonster(strValues[i]);
	    	}
	    	i++;
	    }
	    return Final;
	}
	public static String Attribute(long a) {
		String[] tab = new String[6];
		tab[1] = "water";
		tab[2] = "fire";
		tab[3] = "wind";
		tab[4] = "light";
		tab[5] = "dark";
		return tab[(int) a];
	}
	public static String getAttribute(String monstre) {
		long tab[] = new long[10000];
		String [] strValues = new String[10000];
		JSONObject jsonO = getParse(test);
	    JSONArray fields = (JSONArray) jsonO.get("unit_list");
	    for(Object field: fields) {
	    	JSONObject jsonObj = (JSONObject) field;
	    	long unit = (long) jsonObj.get("unit_master_id");
	    	tab[i] = unit;
	    	String s = String.valueOf(tab[i]);
	    	strValues[i] = s;
	    	if (BD_monstre.GetMonster(strValues[i]).equals(monstre)) {
	    		long attribute = (long) jsonObj.get("attribute");
	    		
	    		return Attribute(attribute);
	    	}
	    	i++;
	    }
	    return "Monstres non trouvée, ou, attribut non répertoriée";
	}
	
	public static void getNumberOfRune_Artifacts(String monster) {
		long nom[] = new long[1000];
		long rune[] = new long[1000];
		long artefact[] = new long[1000];
		long compteur_rune = 0;
		long compteur_arte = 0;
		String [] strValues = new String[1000];
		JSONObject jsonO = getParse(test);
	    JSONArray fields = (JSONArray) jsonO.get("unit_list");
	    for(Object field: fields) {
	    	JSONObject jsonObj = (JSONObject) field;
	    	long monstre = (long) jsonObj.get("unit_master_id");
	    	
	    	long unit = (long) jsonObj.get("unit_id");
	    	nom[i] = monstre;
	    	String s = String.valueOf(nom[i]);
	    	strValues[i] = s;
	    	if(monster.equals(BD_monstre.GetMonster(strValues[i]))) {
	    		//runes
		    	JSONArray fields2 = (JSONArray) jsonObj.get("runes");
		    	for (Object field2: fields2) {
		    		JSONObject jsonOb = (JSONObject) field2;
		    		long runes = (long) jsonOb.get("occupied_id");
		    		rune[i] = runes;
		    		if(rune[i] == unit)
		    			compteur_rune++;
		    	}
		    	System.out.println("Le nombre de runes équipées sur "+ BD_monstre.GetMonster(strValues[i]) +" est : " + compteur_rune);
		    	compteur_rune=0;
		    	
		    	//artifacts
		    	JSONArray fields3 = (JSONArray) jsonObj.get("artifacts");
		    	for (Object field3: fields3) {
		    		JSONObject jsonOb = (JSONObject) field3;
		    		long artifact = (long) jsonOb.get("occupied_id");
		    		artefact[i] = artifact;
		    		if(artefact[i] == unit)
		    			compteur_arte++;
		    	}
		    	System.out.println("Le nombre d'artifacts équipées sur "+ BD_monstre.GetMonster(strValues[i]) +" est : " + compteur_arte);
		    	compteur_arte=0;
	    	}
	    	i++;
	    }
	}
	
	
	public static void getRunes(String monster,long pos_runes) {
		long nom[] = new long[1000];
		String [] strValues = new String[1000];
		JSONObject jsonO = getParse(test);
	    JSONArray fields = (JSONArray) jsonO.get("unit_list");
	    for(Object field: fields) {
	    	JSONObject jsonObj = (JSONObject) field;
	    	long monstre = (long) jsonObj.get("unit_master_id");
	    	nom[i] = monstre;
	    	String s = String.valueOf(nom[i]);
	    	strValues[i] = s;
	    	if(monster.equals(BD_monstre.GetMonster(strValues[i]))) {
		    	JSONArray fields2 = (JSONArray) jsonObj.get("runes");
		    	for (Object field2: fields2) {
		    		JSONObject jsonOb = (JSONObject) field2;
		    		long slot_runes = (long) jsonOb.get("slot_no");
		    		if (slot_runes == pos_runes) {
		    			JSONArray fields3 = (JSONArray) jsonOb.get("pri_eff");
		    			System.out.println(	Runes.effectTypes((long) fields3.get(0)));
		    			JSONArray fields4 = (JSONArray) jsonOb.get("sec_eff");
		    				for (Object j : fields4) {
		    					for (long i=0;i< fields4.size();i++) {
		    						if(i== 0)
		    							System.out.println(Runes.effectTypes((long) ((ArrayList<?>) j).get(0)));
		    						if(i == 1)
		    							System.out.println((long) ((ArrayList<?>) j).get(1) + (long) ((ArrayList<?>) j).get(3));
		    					}
		    				}
		    			}
		    		}
		    	}
	    	}
	    }
   public static void main(String args[]) {


   }
}