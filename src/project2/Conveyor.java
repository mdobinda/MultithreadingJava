package project2;

import java.util.concurrent.locks.ReentrantLock;

public class Conveyor {
    public int ID;

    // Controls synchronization with conveyor
    public ReentrantLock lock = new ReentrantLock();

    public Conveyor(int ID){
        this.ID = ID;
    }

    public void Input(int stationID){
        System.out.printf("Routing Station %d: successfully moves packages into station on input conveyor number C%d%n", stationID, this.ID);
    }

    public void Output(int stationID){
        System.out.printf("Routing Station %d: successfully moves packages out of station on output conveyor number C%d%n", stationID, this.ID);
    }
}