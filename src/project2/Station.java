package project2;

import java.util.Random;

/* Restriction #5: Your station threads must implement the Runnable interface and not extend the Thread
class in order to utilize the ExecutorService object mentioned in 4 above.*/

public class Station implements Runnable {

    //define all the RoutingStation attributes
    //protected
    protected int workLoad = 0;
    protected int stationID;
    protected Conveyor input;
    protected Conveyor output;

    public Station(int ID){
        this.stationID = ID;
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
        /* Output specification #10*/
        System.out.println("\n* * Routing Station " + stationID + ": * * CURRENTLY HARD AT WORK MOVING PACKAGES.\n");
        //decrement workload counter
        this.workLoad--;

        this.input.Input(this.stationID);
        this.output.Output(this.stationID);

        /* Output specification #11*/
        System.out.printf("Routing Station %d: has %d package groups left to move.%n%n%n", this.stationID, workLoad);
        /* Output specification #9*/

        //loop ends - station is done - print out message
        if (workLoad == 0) {
            System.out.printf("# # Routing Station %d: Workload successfully completed. * *  Station %d preparing to go offline. # # %n%n", stationID, stationID);
        }
        //hold the conveyors for a random period of time to simulate work flow, i.e. sleep the thread
        goToSleep();
    }

    /* Output specification #1 */
    public void Input(Conveyor c){
        System.out.printf("Routing Station %d: Input conveyor set to conveyor number C%d.%n", this.stationID, c.conveyorID);
        this.input = c;
    }

    /* Output specification #2 */
    public void Output(Conveyor c){
        System.out.printf("Routing Station %d: Output conveyor set to conveyor number C%d.%n", this.stationID, c.conveyorID);
        this.output = c;
    }

    /* Output specification #3 */
    public void setWorkload(int work){
        System.out.printf("Routing Station %d: Workload set. %n", this.stationID);
        this.workLoad = work;
    }

    public void setOnline(){
        System.out.printf("Routing Station %d: Now Online %n%n", this.stationID);
    }

    //the run() method - this is what a station does
    @Override
    public void run() {
        //dump out the conveyor assignments and workload settings for the station - simulation output criteria
        System.out.println("% % % % % ROUTING STATION " + stationID + " Coming Online - Initializing Conveyors % % % % % \n");

        //run the simulation on the station for its entire workload
        while(workLoad != 0){

            System.out.printf("Routing Station %d: Entering Lock Aquisition Phase\n", stationID);
            /* Output specification #4 */
            //get input lock
            if(input.lock.tryLock()) {
                System.out.printf("Routing Station %d: holds lock on input conveyor C%d.%n", stationID, input.conveyorID);
                /* Output specification #5 */
                //attempt to get output lock
                if(output.lock.tryLock()) {
                    System.out.printf("Routing Station %d: holds lock on output conveyor C%d.%n", stationID, output.conveyorID);
                    //if both locks acquired - then go to work
                    doWork();
                } else {
                    //if not - release input lock and sleep for a while
                   /* Output specification #8 */
                    System.out.printf("Routing Station %d: unable to lock output conveyor C%d.%n", stationID, output.conveyorID);
                    System.out.printf("SYNCHRONIZATION ISSUE: Station %d currently holds the lock on output conveyor %d – releasing lock on input conveyor C%d.%n", stationID, output.conveyorID, input.conveyorID);

                    input.lock.unlock();
                }

            }
            /* Output specification #6 */
            if(input.lock.isHeldByCurrentThread()){
                System.out.printf("Routing Station %d: unlocks/releases input conveyor C%d.%n", stationID, input.conveyorID);
                input.lock.unlock();
            }
            /* Output specification #7 */
            if(output.lock.isHeldByCurrentThread()){
                System.out.printf("Routing Station %d: unlocks/releases output conveyor C%d.%n", stationID, output.conveyorID);
                output.lock.unlock();
            }
            goToSleep();
        }

    }

}
