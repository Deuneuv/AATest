package version1;

import java.util.*;

import weka.core.*;

public class PFoil {
		
	private ArrayList<Litteral> listeLitteraux;
	private int nbPositifs; // au début de l'apprentissage
	private int nbNegatifs;
	
	public PFoil(){
		listeLitteraux = new ArrayList<Litteral>();
		nbNegatifs = 0;
		nbPositifs = 0;
	}
	
	public int getNbPositifs() {
		return nbPositifs;
	}

	public void setNbPositifs(int nbPositifs) {
		this.nbPositifs = nbPositifs;
	}

	public int getNbNegatifs() {
		return nbNegatifs;
	}

	public void setNbNegatifs(int nbNegatifs) {
		this.nbNegatifs = nbNegatifs;
	}

	
	
	public ArrayList<Litteral> getListeLitteraux() {
		return listeLitteraux;
	}
	public void setListeLitteraux(ArrayList<Litteral> listeLitteraux) {
		this.listeLitteraux = listeLitteraux;
	}
	
	public void buildClassifier(Instances data) throws Exception{
		
		if (!data.classAttribute().isNominal()) {
			throw new Exception("PFoil: seulement une classe nominale.");
		}
		Enumeration<Attribute> enumAtt = data.enumerateAttributes();
		while (enumAtt.hasMoreElements()) {
			Attribute attr = (Attribute) enumAtt.nextElement();
			if (!attr.isNominal()) {
			throw new Exception("PFoil: seulement des attributs nominaux.");
			}
			Enumeration<Instance> enum1 = data.enumerateInstances();
			while (enum1.hasMoreElements()) {
				if(((Instance) enum1.nextElement()).isMissing(attr)) {
					throw new Exception("PFoil: Pas d'exemple sans classe.");
				}
			}
		}
		data = new Instances(data);
		data.deleteWithMissingClass();
		System.out.println(data.toString());
		//makeRules(data);
		
	}
	/**
	 * 
	 * @param dataset le jeu d'exemples
	 * Crée une liste de litteraux 
	 */
	
	public void makeLitteraux(Instances dataset){
		System.out.println("\n---------Génération des litteraux du jeu d'apprentissage-----------\n");
		for(int i=0; i < dataset.numAttributes()-1; ++i){
			for(int j = 0; j < dataset.attribute(i).numValues(); ++j){
				Litteral l = new Litteral(dataset.attribute(i), j);
				listeLitteraux.add(l);
			}
		}
		/*for(Litteral lit : listeLitteraux){
			lit.tostring();
		}*/
	}
	
	/*public void makeRules(Instances data){
		if (data.numInstances() == 0) {
			return;
		}
		// Compute attribute with maximum information gain.
		double[] infoGains = new double[data.numAttributes()];
		Enumeration<Attribute> attEnum = data.enumerateAttributes();
		while (attEnum.hasMoreElements()) {
			Attribute att = (Attribute) attEnum.nextElement();
			infoGains[att.index()] = computeInfoGain(data, att);
		}
		//m_Attribute = data.attribute(Utils.maxIndex(infoGains))
	}*/

	
	/**
	 * @param exples  vecteur d'instance
	 * @param L littéral
	 * @return le nombre d'instance qui satisfont L
	 */
	public int satisfies(Vector<Instance> exples, Litteral L){
		int nbreExple = 0;
		Instance temp;
		for(int i = 0; i< exples.size(); ++i){
			temp = (Instance) exples.elementAt(i);
			if(temp.attribute(L.getIndexAttr()).name() == L.getNameAttr() && temp.stringValue(L.getIndexAttr()) == L.getValueAttr()){
				++nbreExple;
			}
		}
		return nbreExple;
		
	}
	
	public double gain(Litteral litteral, Vector<Instance> pos, Vector<Instance> neg){
		int P = pos.size();
		int N = neg.size();
		/*int P = nbPositifs;
		int N = nbNegatifs;*/
		int p = satisfies(pos, litteral);
		int n = satisfies(neg, litteral);
		System.out.print(", n = "+n); System.out.print(", p = "+p);
		double val1 = P/(P+N);
		double val2 = p/(p+n);
		double logGeneral = Math.log(val1) / Math.log(2.0);
		double logLitteral = Math.log(val2) / Math.log(2.0);
		double val_retour = -1.0; 
		
		if(p == 0){
			//le gain n'est pas calculable
			return val_retour;
		}
		val_retour = p  * (logLitteral - logGeneral);
		
		return val_retour;
	}
	
	/*public static Vector<Instance> foilProp(Vector<Instance> pos, Vector<Instance> neg){
		Vector<Regle> regles = new Vector<Regle>();
		
		while(pos.size()!=0){
			Regle r = new Regle();
			//candidats
			Vector<Instance> neg2 = neg;
			Vector<Instance> pos2 = pos;
			
			while (neg2.size()!=0) {
				
			}
		}
		
		return regles;
		
	}*/
	
	public void splitData(Vector<Instance> pos, Vector<Instance> neg, Instances data, Attribute classAttr){
		
		Enumeration<Instance> instEnum = data.enumerateInstances();
		Instance inst;
		while (instEnum.hasMoreElements()) {
			inst = (Instance) instEnum.nextElement();
			/*on suppose que le premiere valeur est positive */ 
			if(inst.stringValue(classAttr) == classAttr.value(0)){
				pos.addElement(inst);
				++nbPositifs;
			}
			else{
				neg.addElement(inst);
				++nbNegatifs;
			}
		}
		
	}

}

