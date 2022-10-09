package project2;

import java.util.Random;

public class Station implements Runnable {

    //define all the RoutingStation attributes
    //protected
    private int work;
    public int ID;
    private Conveyor input;
    private Conveyor output;

    public Station(int ID){
        this.ID = ID;
        this.work = 0;
    }

    //method for threads to go to sleep
    //note: a sleeping thread in java maintains all resources allocated to it. Including locks
    //Locks are not relinquished during a sleep cycle
    private void goToSleep(){
        try {
            Random gen = new Random();
            Thread.sleep(1000 * gen.nextInt(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //method for simulating Routing Station work - i.e. some time period during which the station is moving packages
    private void doWork(){
        //print simulation output messages
        System.out.println("\n* * Routing Station " + ID + ": * * CURRENTLY HARD AT WORK MOVING PACKAGES.\n");
        //decrement workload counter
        this.work--;

        this.input.Input(this.ID);
        this.output.Output(this.ID);

        System.out.printf("Routing Station %d: has %d package groups left to move.%n%n%n", this.ID, work);

        if (work == 0) {
       //     System.out.println(" GOING OFFLINE TEST TEST TEST WORK COMPLETED");
            System.out.printf("* * Routing Station %d: going offline - work completed! * *%n%n", this.ID);
        }
        //hold the conveyors for a random period of time to simulate work flow, i.e. sleep the thread
        goToSleep();
        //check if workload has reached 0 - if so, print out message indicating station is done
//        if(work == 0) {
//            System.out.printf("* * Station %d: Workload successfully completed. * *%n%n", ID);
//        }
    }

    public void Input(Conveyor c){
        System.out.printf("Routing Station %d: input connection is set to conveyor number C%d%n", this.ID, c.ID);
        this.input = c;
    }

    public void Output(Conveyor c){
        System.out.printf("Routing Station %d: output connection is set to conveyor number C%d%n", this.ID, c.ID);
        this.output = c;
    }

    public void setWorkload(int work){
        System.out.printf("Routing Station %d: Workload set. Station %d has a total of %d package groups to move.%n", this.ID, this. ID, this.work);
        this.work = work;
    }

    public void setOnline(){
        System.out.printf("Routing Station %d: Now Online %n%n", this.ID);
    }


    //the run() method - this is what a station does
    @Override
    public void run(){
        //dump out the conveyor assignments and workload settings for the station - simulation output criteria
        System.out.println("% % % % % ROUTING STATION " + ID + " Coming Online - Initializing Conveyors % % % % % \n");

        //run the simulation on the station for its entire workload
        while(this.work > 0){
            System.out.printf("Routing Station %d: Entering Lock Aquisition Phase\n", this.ID);
            if(input.lock.tryLock()){
                System.out.printf("Routing Station %d: holds lock on input conveyor number C%d%n", this.ID, input.ID);

                if(output.lock.tryLock()){
                    System.out.printf("Routing Station %d: holds lock on output conveyor number C%d%n", this.ID, output.ID);
                    doWork();
                } else {
                    System.out.printf("Routing Station %d: unable to lock output conveyor – releasing lock on input conveyor number C%d.%n", this.ID, output.ID);
                    input.lock.unlock();
                }

            }

            if(input.lock.isHeldByCurrentThread()){
                System.out.printf("Routing Station %d: unlocks input conveyor number C%d%n", this.ID, input.ID);
                input.lock.unlock();
            }

            if(output.lock.isHeldByCurrentThread()){
                System.out.printf("Routing Station %d: unlocks output conveyor number C%d%n", this.ID, output.ID);
                output.lock.unlock();
            }

            goToSleep();
        }

        System.out.printf("# # # # Routing Station %d: Workload successfully completed! * * * Routing Station %d preparing to go offline. # # # # %n%n", ID, ID);

    }





}
