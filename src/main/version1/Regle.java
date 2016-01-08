package version1;

import weka.core.Attribute;
import java.util.*;
import weka.core.Instance;

public class Regle {
	protected Attribute conclusion; //la classe
	protected Vector<Attribute> conditions; //vecteur de litteraux

	public Regle(){
		conditions = new Vector<Attribute>();
	}
	/**
	 * Crée une règle avec des conditions vides
	 * et initialise la classe
	 */
	public Regle(Attribute classifieur){
		conclusion = classifieur;
		conditions = new Vector<Attribute>();
	}
	/**
	 * Retourne vrai si la règle couvre l'exemple
	 * @param exple
	 * @return
	 */
	public boolean covers(Instance exple){
		boolean val_retour = false;
		
		return val_retour;
	}
	
	public Attribute getConclusion() {
		return conclusion;
	}
	public void setConclusion(Attribute conclusion) {
		this.conclusion = conclusion;
	}
	
	public void addCondition(Attribute c){
		conditions.addElement(c);
	}
	
}
