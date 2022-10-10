package project2;

/*
Name: Magdalena Dobinda
Course: CNT 4714 Fall 2022
Assignment title: Project 2 – Multi-threaded programming in Java
Date: October 8th, 2022
Class: Enterprise Computing
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/*Restriction 4: You must use an ExecutorService object to manage a FixedThreadPool(MAX),
where MAX is the upper limit on the number of stations which we’ll set to be 10 (see below
under Input Specification).*/

public class PackageManagementFacilitySimulator {

    static Conveyor[] conveyors;
    static RoutingStation[] stations;
    static int[] workloads;
    static int numStations;
    static int MAX = 10;


    public static void main(String[] args) throws FileNotFoundException {



        //read in config.txt file
        File file = new File("src/project2/config.txt");
        Scanner read = new Scanner(file);

        //redirects all terminal console activity to a file for grading

        File output = new File("src/project2/sample.txt");
        PrintStream stream = new PrintStream(output);
        System.setOut(stream);

        System.out.println("\n* * * * * * * * * * PACKAGE MANAGEMENT FACILITY SIMULATION BEGINS * * * * * * * * * * \n");

        //save the first integer in the config.txt file as number of routing stations in the simulation run
        numStations = read.nextInt();

        //credate arrays
        workloads = new int[numStations];
        stations = new RoutingStation[numStations];

        //create thread pool of MAX size
        ExecutorService pool = Executors.newFixedThreadPool(MAX);

        //create an array of conveyor objects
        conveyors = new Conveyor[numStations];
        for(int i = 0; i < numStations; i++){
            conveyors[i] = new Conveyor(i);
        }


        //create the routing stations for this simulation run
        //loop through each routing station in the simulation
        System.out.println("The parameters for this simulation run are: \n");
        for(int i = 0; i < numStations; i++){
            workloads[i] = read.nextInt();
            System.out.printf("Routing Station %d has a total workload of %d%n", i, workloads[i]);
            stations[i] = new RoutingStation(i);
        }
        System.out.println("\n* * * * * * * * * * Routing Stations are being assigned workloads * * * * * * * * * * \n");

        // Give the workload each station can handle.

        for(int i = 0; i < numStations; i++){
            stations[i].Input((i == 0) ? conveyors[0] : conveyors[i - 1]);
            stations[i].Output((i == 0) ? conveyors[numStations - 1] : conveyors[i]);
            stations[i].setWorkload(workloads[i]);
           // stations[i].setOnline();
        }

        System.out.printf("%n%n%n");

        //start threads executing using the ExecutorService object i.e executes pooled threads
        for(int i = 0; i < numStations; i++){
            try {
                pool.execute(stations[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //application shutdown - different techniques for shutting down the ExecutorService are shown below
        pool.shutdown();

        //print statements that checks if thread pool is fully terminanted and declares simulation is finished using the executor interface
        //Returns true if all tasks have completed following shut down. Note that isTerminated is never true unless either shutdown or shutdownNow was called first.

        while(true) {
            if (pool.isTerminated()) {
                System.out.println("* * * * * * * * * * ALL WORKLOADS COMPLETE * * * PACKAGE MANAGEMENT FACILITY SIMULATION TERMINATES * * * * * * * * * *\n\n");
                System.out.println("* % * % * % SIMULATION ENDS % * % * % *");
                break;
            }
        }

    }
}

