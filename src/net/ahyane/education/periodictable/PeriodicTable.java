package net.ahyane.education.periodictable;

import java.util.ArrayList;

import net.ahyane.renderbase.object.Animation3D;

public class PeriodicTable {
	static final int[] periodicTableData = {
		 1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  2,
		 3,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  5,  6,  7,  8,  9, 10,
		11, 12,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 13, 14, 15, 16, 17, 18,
		19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36,
		37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54,
		55, 56, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86,
		87, 88,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,
	};

	static final int[] lanthanTableData = {
		57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71,		
	};

	static final int[] actiniumTableData = {
		89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99,100,101,102,103,		
	};

	static final String[] atomSigns = {
		 "H", "He",
		"Li", "Be",  "B",  "C",  "N",  "O",  "F", "Ne",
		"Na", "Mg", "Al", "Si",  "P",  "S", "Cl", "Ar",
		 "K", "Ca", "Sc", "Ti",  "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr",
		"Rb", "Sr",  "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te",  "I", "Xe",
		"Cs", "Ba",
		            "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb",
		            "Lu", "Hf", "Ta",  "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn",
		"Fr", "Ra",
		            "Ac", "Th", "Pa",  "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No",
		            "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Cn","Uut", "Fl","Uup", "Lv","Uus","Uuo",
	};
	
	static final String[] atomWeights = {
		  "1.0008",  "4.0026",
		    "6.94",  "9.0122",   "10.81",  "12.011",  "14.007",  "15.999",  "18.998",  "20.180",
		  "22.990",  "24.305",  "26.982",  "28.085",  "30.974",   "32.06",   "35.45",  "39.948",
		  "39.098",  "40.078",  "44.956",  "47.867",  "50.942",  "51.996",  "54.938",  "55.845",  "58.933",  "58.693",  "63.546",   "65.38",  "69.723",   "72.63",  "74.922",   "78.96",  "79.904",  "83.798",
		  "85.468",   "87.62",  "88.906",  "91.224",  "92.906",   "95.96", "[97.91]",  "101.07",  "102.91",  "106.42",  "107.87",  "112.41",  "114.82",  "118.71",  "121.76",  "127.60",  "126.90",  "131.29",
		  "132.91",  "137.33",
		                        "138.91",  "140.12",  "140.91",  "144.24","[144.91]",  "150.36",  "151.96",  "157.25",  "158.93",  "162.50",  "164.93",  "167.26",  "168.93",  "173.05",
		                        "174.97",  "178.49",  "180.95",  "183.84",  "186.21",  "190.23",  "192.22",  "195.08",  "196.97",  "200.59",  "204.38",   "207.2",  "208.98","[208.98]","[209.99]","[222.02]",
		"[223.02]","[226.03]",
		                      "[227.03]","[232.04]","[231.04]",  "238.03","[237.05]","[244.06]","[243.06]","[247.07]","[247.07]","[251.08]","[252.08]","[257.10]","[258.10]","[259.10]",
		                      "[262.11]","[265.12]","[268.13]","[271.13]",   "[270]","[277.15]","[276.15]","[281.16]","[280.16]","[285.17]","[284.18]","[289.19]","[288.19]",   "[293]",   "[294]",   "[294]",
	};
	
	static final String[] atomNames = {
		"¼ö¼Ò", "Çï·ý",
		"¸®Æ¬", "º£¸±·ý", "ºØ¼Ò", "Åº¼Ò", "Áú¼Ò", "»ê¼Ò", "ÇÃ·ç¿À¸£", "³×¿Â",
		"³ªÆ®·ý", "¸¶±×³×½·", "¾Ë·ç¹Ì´½", "±Ô¼Ò", "ÀÎ", "È²", "¿°¼Ò", "¾Æ¸£°ï",
		"Ä®·ý", "Ä®½·", "½ºÄ­µã", "Æ¼Å¸´½", "¹Ù³ªµã", "Å©·Ò", "¸Á°£", "Ã¶", "ÄÚ¹ßÆ®", "´ÏÄÌ", "±¸¸®", "¾Æ¿¬", "°¥·ý", "°Ô¸£¸¶´½", "ºñ¼Ò", "¼¿·¹´½", "ºê·Ò", "Å©¸³Åæ",
		"D","D","D","D","D","D","D","D","D","D","D","D","D","D","D","D","D","D","D","D","D","D","D",
		 "H", "He",
		"Li", "Be",  "B",  "C",  "N",  "O",  "F", "Ne",
		"Na", "Mg", "Al", "Si",  "P",  "S", "Cl", "Ar",
		 "K", "Ca", "Sc", "Ti",  "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr",
		"Rb", "Sr",  "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te",  "I", "Xe",
		"Cs", "Ba",
		            "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb",
		            "Lu", "Hf", "Ta",  "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn",
		"Fr", "Ra",
		            "Ac", "Th", "Pa",  "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No",
		            "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Cn","Uut", "Fl","Uup", "Lv","Uus","Uuo",
	};
	
//	public static final int A = 0x0000;//air
//	public static final int L = 0x0001;//liquid
//	public static final int M = 0x0002;//metal
	
	public static final int NM = 0x0000;//ºñ±Ý¼Ó
	public static final int AM = 0x0010;//¾ËÄ®¸®±Ý¼Ó
	public static final int AEM = 0x0020;//¾ËÄ®¸®Åä±Ý¼Ó
	public static final int TM = 0x0030;//ÀüÀÌ±Ý¼Ó
	public static final int M = 0x0040;//ÀÏ¹Ý±Ý¼Ó
	public static final int ML = 0x0050;//¸ÞÅ»·ÎÀÌµå
	public static final int HA = 0x0060;//ÇÒ·Î°Õ
	public static final int NG = 0x0070;//³ëºí°¡½º

	public static final int G1 = AM;
	public static final int G2 = AEM;
	public static final int GTM = TM;
	public static final int GM = M;
	public static final int GML = ML;
	public static final int GNM = NM;
	public static final int GH = HA;
	public static final int G18 = NG;

	static final int[] atomAttributes = {
		 GNM,  G18,
		  G1,   G2,  GML,  GNM,  GNM,  GNM,   GH,  G18,
		  G1,   G2,   GM,  GML,  GNM,  GNM,   GH,  G18,
		  G1,   G2,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,   GM,  GML,  GML,  GNM,   GH,  G18,
		  G1,   G2,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,   GM,   GM,  GML,  GML,   GH,  G18,
		  G1,   G2,
		             GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,
		             GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,   GM,   GM,   GM,   GM,  GML,  G18,
		  G1,   G2,
		             GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,
		             GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,  GTM,   GM,   GM,   GM,   GM,   GM,  G18,
	};
	
	public static class Atom{
		int no;
		String sign;
		String name;
		String weight;
		int attribute;
		int group[];
		
		Animation3D animation3d;
		
		public Atom() {
			super();
			animation3d = new Animation3D();
		}

		public Animation3D getAnimation3d(){
			return animation3d;
		}
	}
	
	ArrayList<Atom> atoms = null;
	
	public void initialize(){
		atoms = new ArrayList<Atom>();
		
		for(int i = 0; i < 118; i++){
			Atom atom = new Atom();
			atom.no = i + 1;
			atom.sign = atomSigns[i];
			atom.name = atomNames[i];
			atom.weight = atomWeights[i];
			atom.attribute = atomAttributes[i];
			atom.group = null;
			atoms.add(atom);
		}
		
		atoms.get(57).group = lanthanTableData;
		atoms.get(89).group = actiniumTableData;
		
	}
	
	
}
