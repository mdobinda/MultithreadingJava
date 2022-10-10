package project2;

/*
Name: Magdalena Dobinda
Course: CNT 4714 Fall 2022
Assignment title: Project 2 – Multi-threaded programming in Java
Date: October 8th, 2022
Class: Enterprise Computing
*/

import java.util.concurrent.locks.ReentrantLock;

public class Conveyor {

    public int conveyorID;


    /* Resctriction #2: You must use a the java.util.concurrent.locks.ReentrantLock interface. In
other words, do not create your own locking system nor implement a Boolean semaphore-like
system to control the locking */

    // Controls synchronization with conveyor
    public ReentrantLock lock = new ReentrantLock();

    //constructor method - simply assign the conveyor its number
    public Conveyor(int ID){
        this.conveyorID = ID;
    }

    //method for routing stations to acquire a conveyor lock
   // public boolean lockConveyor(){
        //use tryLock()
        //tryLock() returns true if the lock request is granted by the Lock Manager
        //i.e. the lock was free and was granted to the requesting thread - otherwise return is false
   // }

    //method for routing stations to release a conveyor lock
    // public void unlockConveyor(){
        //simply call unluck() on the theLock
   // }

    public void Input(int stationID){
        System.out.printf("Routing Station %d: successfully moves packages into station on input conveyor number C%d%n", stationID, conveyorID);
    }

    public void Output(int stationID){
        System.out.printf("Routing Station %d: successfully moves packages out of station on output conveyor number C%d%n", stationID, conveyorID);
    }
}