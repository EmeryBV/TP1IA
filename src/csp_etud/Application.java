package csp_etud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class Application {

	public static void main(String[] args) throws Exception {
		Chrono chrono = new Chrono();
		chrono.start(); // démarrage du chrono
		GenereFicherReine reine = new GenereFicherReine();
		String ReineN = reine.GenereFichierReine(6);
		String fileName= "ZebreExp"; //nom du fichier
//		String fileName = ReineN;
		Network networkExt;
		Network networkDif;
		CSP csp;
		Assignment firstSolution;
		List<Assignment> allSolutions;
		BufferedReader readFile= new BufferedReader(new FileReader (fileName));
		networkExt= new Network(readFile);
		networkDif = new Network();
		readFile.close();
		csp = new CSP(networkExt);
//		firstSolution = csp.searchSolution();
		allSolutions = csp.searchAllSolutions();
		chrono.stop();
		System.out.println("Temps d'execution: "+chrono.getDureeMs()+"ms");
		System.out.println("Nombre de noeud exploré: "+ csp.cptr);
	}
	
	
}
