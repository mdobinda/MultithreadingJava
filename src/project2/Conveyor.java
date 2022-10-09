package project2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Conveyor {
    public int ID;
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