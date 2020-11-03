package csp_etud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExperimentationLauncher {

    private static final boolean printAll = true;
    private static final DecimalFormat df = new DecimalFormat("0.000");


    private static void delMinMax(List<Double> list) {
        double min = list.get(0);
        double max = list.get(0);

        for (Double d : list) {
            if (d < min) min = d;
            if (d > max) max = d;
        }

    }

    private static double getAverage(List<Double> list) {
        double total = 0;

        for (Double d : list) {
            total += d;
        }

        return total / list.size();
    }

    public static void main(String[] args) throws Exception {

        int hardnessIncrements = 12;
        int networksPerHardnessIncrement = 10;

        double totalRealTime = 0;
        double totalSystemTime = 0;
        double totalCpuTime = 0;
        double totalUserTime = 0;

        for (int i = 0; i < hardnessIncrements; i++) {

            double hardnessTotalRealTime = 0;
            double hardnessTotalSystemTime = 0;
            double hardnessTotalCpuTime = 0;
            double hardnessTotalUserTime = 0;

            for (int j = 0; j < networksPerHardnessIncrement; j++) {

                String filename = "durete" + (i + 27) + "_reseau" + (j + 1) + ".txt";
                BufferedReader reader = new BufferedReader(new FileReader("Reseaux_experimentations/" + filename));

                Network network = new Network(reader);

                reader.close();

                CSP csp = new CSP(network);

                List<Double> realTimes = new ArrayList<>();
                List<Double> systemTimes = new ArrayList<>();
                List<Double> cpuTimes = new ArrayList<>();
                List<Double> userTimes = new ArrayList<>();

                boolean hasSolution = false;

                for (int testIndex = 0; testIndex < 5; testIndex++) {
                    ThreadMXBean thread = ManagementFactory.getThreadMXBean();

                    long startTime = System.nanoTime();
                    long startCpuTime = thread.getCurrentThreadCpuTime();
                    long startUserTime = thread.getCurrentThreadUserTime();

                    Assignment solution = csp.searchSolution();
                    if (solution != null) {
                        hasSolution = true;
                    }

                    long userTime = thread.getCurrentThreadUserTime() - startUserTime;
                    long cpuTime = thread.getCurrentThreadCpuTime() - startCpuTime;
                    long sysTime = cpuTime - userTime;
                    long realTime = System.nanoTime() - startTime;

                    realTimes.add(realTime / 1000000d);
                    systemTimes.add(sysTime / 1000000d);
                    cpuTimes.add(cpuTime / 1000000d);
                    userTimes.add(userTime / 1000000d);
                }

                delMinMax(realTimes);
                delMinMax(systemTimes);
                delMinMax(cpuTimes);
                delMinMax(userTimes);

                double realTimeAverage = getAverage(realTimes);
                double systemTimeAverage = getAverage(systemTimes);
                double cpuTimeAverage = getAverage(cpuTimes);
                double userTimeAverage = getAverage(userTimes);

                if (printAll) {
                    System.out.print(filename);
                    System.out.print("\t\tsol: " + hasSolution);
                    System.out.print("\t\treal: " + df.format(realTimeAverage) + "ms");
                    System.out.print("\t\tsystem: " + df.format(systemTimeAverage) + "ms");
                    System.out.print("\t\tcpu: " + df.format(cpuTimeAverage) + "ms");
                    System.out.println("\t\tuser: " + df.format(userTimeAverage) + "ms");
                }

                hardnessTotalRealTime += realTimeAverage;
                hardnessTotalSystemTime += systemTimeAverage;
                hardnessTotalCpuTime += cpuTimeAverage;
                hardnessTotalUserTime += userTimeAverage;
            }

            double hardnessAverageRealTime = hardnessTotalRealTime / networksPerHardnessIncrement;
            double hardnessAverageSystemTime = hardnessTotalSystemTime / networksPerHardnessIncrement;
            double hardnessAverageCpuTime = hardnessTotalCpuTime / networksPerHardnessIncrement;
            double hardnessAverageUserTime = hardnessTotalUserTime / networksPerHardnessIncrement;

            System.out.print("Moyenne pour durete " + (i + 27) + "%\t\t\t\t");
            System.out.print("\t\treal: " + df.format(hardnessAverageRealTime) + "ms");
            System.out.print("\t\tsystem: " + df.format(hardnessAverageSystemTime) + "ms");
            System.out.print("\t\tcpu: " + df.format(hardnessAverageCpuTime) + "ms");
            System.out.println("\t\tuser: " + df.format(hardnessAverageUserTime) + "ms");
            if (printAll) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------");
            }

            totalRealTime += hardnessAverageRealTime;
            totalSystemTime += hardnessAverageSystemTime;
            totalCpuTime += hardnessAverageCpuTime;
            totalUserTime += hardnessAverageUserTime;
        }

        double averageRealTime = totalRealTime / hardnessIncrements;
        double averageSystemTime = totalSystemTime / hardnessIncrements;
        double averageCpuTime = totalCpuTime / hardnessIncrements;
        double averageUserTime = totalUserTime / hardnessIncrements;

        System.out.print("Moyenne finale\t\t\t\t\t\t");
        System.out.print("\t\treal: " + df.format(averageRealTime) + "ms");
        System.out.print("\t\tsystem: " + df.format(averageSystemTime) + "ms");
        System.out.print("\t\tcpu: " + df.format(averageCpuTime) + "ms");
        System.out.println("\t\tuser: " + df.format(averageUserTime) + "ms");
    }

}
