package bd_monstres;
import java.io.File;

import application.Application;
public class Runes extends Application{



	public Runes(File imported) {
		super(imported);
		// TODO Auto-generated constructor stub
	}

	public static String effectTypes(long a) {
		String [] effectTypes = new String[13];
		effectTypes[0] = "";
		effectTypes[1] = "HP flat";
		effectTypes[2] = "HP%";
		effectTypes[3] = "ATK flat";
		effectTypes[4] = "ATK%";
		effectTypes[5] = "DEF flat";
		effectTypes[6] = "DEF%";
		effectTypes[8] = "SPD";
		effectTypes[9] = "CRate";
		effectTypes[10] = "CDmg";
		effectTypes[11] = "RES";
		effectTypes[12] = "ACC";
		return effectTypes[(int) a];
	}
	
	public String sets(int a) {
		String [] sets = new String[100];
		sets[1] = "Energy";
		sets[2] = "Guard";
		sets[3] = "Swift";
		sets[4] = "Blade";
		sets[5] = "Rage";
		sets[6] = "Focus";
		sets[7] = "Endure";
		sets[8] = "Fatal";
		sets[10] = "Despair";
		sets[11] = "Vampire";
		sets[13] = "Violent";
		sets[14] = "Nemesis";
		sets[15] = "Will";
		sets[16] = "Shield";
		sets[17] = "Revenge";
		sets[18] = "Destroy";
		sets[19] = "Fight";
		sets[20] = "Determination";
		sets[21] = "Enhance";
		sets[22] = "Accuracy";
		sets[23] = "Tolerance";
		sets[99] = "Immemorial";
		return sets[a];
    }
	
    public String class_runes(int a) {
    	String [] class_rune = new String[5];
    	class_rune[0]="Common";
    	class_rune[1]="Magic";
    	class_rune[2]="Rare";
    	class_rune[3]="Hero";
    	class_rune[4]="Legendary";
    	return class_rune[a];
    }
    public void quality() {
//      1: 'Common',
//      2: 'Magic',
//      3: 'Rare',
//      4: 'Hero',
//      5: 'Legend',
//      // ancient rune qualities
//      11: 'Common',
//      12: 'Magic',
//      13: 'Rare',
//      14: 'Hero',
//      15: 'Legend',
    }
    public void EffSwift() {
    	
    }

}