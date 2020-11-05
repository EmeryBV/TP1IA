package csp_etud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Application {

	public static void main(String[] args) throws Exception {

		ThreadMXBean thread = ManagementFactory.getThreadMXBean();

		GenereFicherReine reine = new GenereFicherReine();
		String ReineN = reine.GenereFichierReine(8);
		String fileName= "Zebre"; //nom du fichier
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
		int tempsTotalReel = 0;
		int tempsTotalCpu = 0;
		int tempsTotalSytem = 0;
		int tempsTotalUser = 0;
		for(int i = 0; i< 10;i++){
		long startTime = System.nanoTime();
		long startCpuTime = thread.getCurrentThreadCpuTime();
		long startUserTime = thread.getCurrentThreadUserTime();
		allSolutions = csp.searchAllSolutions();

		System.out.println("Nombre de noeud exploré: "+ csp.cptr);

		long userTime = thread.getCurrentThreadUserTime() - startUserTime ;
		long cpuTime = thread.getCurrentThreadCpuTime() - startCpuTime;
		long sysTime = cpuTime - userTime;
		long realTime = System.nanoTime() - startTime;
		System.out.print("\t\treal: " + realTime/1000000d + "ms");
		System.out.print("\t\tsystem: " + sysTime/1000000d + "ms");
		System.out.print("\t\tcpu: " + cpuTime/1000000d + "ms");
		System.out.println("\t\tuser: " + userTime/1000000d  + "ms");
		tempsTotalReel += realTime/1000000d;
		tempsTotalUser += userTime/1000000d;
		tempsTotalSytem += sysTime/1000000d;
		tempsTotalCpu += cpuTime/1000000d;
		}

		System.out.println("Moyenne des 10 temps reel= " + tempsTotalReel/10 + "ms");
		System.out.println("Moyenne des 10 temps User= " + tempsTotalUser/10+ "ms");
		System.out.println("Moyenne des 10 temps system= " + tempsTotalSytem/10+ "ms");
		System.out.println("Moyenne des 10 temps Cpu= " + tempsTotalCpu/10+ "ms");
	}
	
	
}
