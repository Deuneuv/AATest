package version1;
import java.io.*;
import java.util.*;

import weka.associations.tertius.Literal;
import weka.core.*;

import weka.core.Instances;

public class Princi {

	public static void main(String[] args) throws IOException {
		
		BufferedReader datafile = null;
		if(args.length < 1){
			System.err.println("----Donner un fichier .arff en paramètre-----");
		}
		else{
			try {
				datafile = new BufferedReader(new FileReader(args[0]));
			} catch (FileNotFoundException ex) {
				System.err.println("----File not found: " + args[0]+ "-----");
			}		 
			Instances jeuExple = new Instances(datafile);
			jeuExple.setClassIndex(jeuExple.numAttributes() - 1);
			PFoil testpfoil = new PFoil();
			try{
				testpfoil.buildClassifier(jeuExple);

			}catch(Exception e){
				System.err.println(e.getMessage());
			}
			
			Vector<Instance> positifs = new Vector<Instance>();
			Vector<Instance> negatifs = new Vector<Instance>();
			testpfoil.splitData(positifs, negatifs, jeuExple, jeuExple.classAttribute());
			System.out.println("-----\nExemples positifs : " + testpfoil.getNbPositifs()+ "------\n");
			for(Instance p : positifs){
				System.out.println(p.toString());
			}
			System.out.println("----\nExemples negatifs : " + testpfoil.getNbNegatifs()+"------\n");
			for(Instance n : negatifs){
				System.out.println(n.toString());
			}
			/*calcul le gain de chaque litteral*/
			testpfoil.makeLitteraux(jeuExple);
			double maximum = 0.0;
			Litteral maxLitt = new Litteral();
			System.out.println("----Les litteraux avec leur gain au début de l'apprentissage----\n");
			ArrayList<Litteral> tmpLit = testpfoil.getListeLitteraux();
			for(Litteral l : tmpLit){
				System.out.print(l.tostring());
				l.setGain(testpfoil.gain(l, positifs, negatifs));
				System.out.println(" ==> Gain : " +l.getGain());
				if(l.getGain() > maximum){
					maximum = l.getGain();
					maxLitt = l;
				}
			}
			/* Affichage du litteral au gain maximum*/
			System.out.println("----\nLe litteral avec le plus grand gain----\n");
			System.out.println(maxLitt.tostring());
			
			testpfoil.foilProp(positifs, negatifs);
		}
	}

}
