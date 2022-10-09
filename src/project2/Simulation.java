package project2;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Simulation {

    static int numStations;
    static int MAX = 10;
    static Station[] stations;
    static int[] stationWorkload;
    static Conveyor[] conveyors;

    public static void main(String[] args) throws FileNotFoundException {


        System.out.println("\n* * * * * * * * * * PACKAGE MANAGEMENT FACILITY SIMULATION BEGINS * * * * * * * * * * \n");

        //read in config.txt file
        File file = new File("src/project2/config.txt");
        Scanner configFile = new Scanner(file);

        //save the first integer in the config.txt file as number of routing stations in the simulation run
        numStations = configFile.nextInt();
        stationWorkload = new int[numStations];
        stations = new Station[numStations];

        //create thread pool of MAX size
        ExecutorService pool = Executors.newFixedThreadPool(MAX);

        //create an array of conveyor objects
        conveyors = new Conveyor[numStations];
        for(int i = 0; i < numStations; i++){
            conveyors[i] = new Conveyor(i);
        }


        //create the routing stations for this simulation run
        //loop through each routing station in the simulation
        for(int i = 0; i < numStations; i++){
            stationWorkload[i] = configFile.nextInt();
            System.out.printf("Routing Station %d has a total workload of %d%n", i, stationWorkload[i]);
            stations[i] = new Station(i);
        }

        System.out.printf("%n%n");

        // what does this do?
        for(int i = 0; i < numStations; i++){
            stations[i].Input((i == 0) ? conveyors[0] : conveyors[i - 1]);
            stations[i].Output((i == 0) ? conveyors[numStations - 1] : conveyors[i]);
            stations[i].setWorkload(stationWorkload[i]);
        }

        System.out.printf("%n%n");

        //start threads executing using the ExecutorService object
        for(int i = 0; i < numStations; i++){
            try {
                pool.execute(stations[i]);
            } catch (Exception e) {
                System.out.printf("Error processing station %d ('%s')%n", i, e.getMessage());
            }
        }

        //application shutdown - different techniques for shutting down the ExecutorService are shown below
        pool.shutdown();

    }
}

