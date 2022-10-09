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
        /* Output specification #10*/
        System.out.println("\n* * Routing Station " + ID + ": * * CURRENTLY HARD AT WORK MOVING PACKAGES.\n");
        //decrement workload counter
        this.work--;

        this.input.Input(this.ID);
        this.output.Output(this.ID);

        /* Output specification #11*/
        System.out.printf("Routing Station %d: has %d package groups left to move.%n%n%n", this.ID, work);
        /* Output specification #9*/

        if (work == 0) {
            System.out.printf("# # Routing Station %d: Workload successfully completed. * *  Station %d preparing to go offline. # # %n%n", ID, ID);
        }
        //hold the conveyors for a random period of time to simulate work flow, i.e. sleep the thread
        goToSleep();
    }


    /* Output specification #1 */
    public void Input(Conveyor c){
        System.out.printf("Routing Station %d: Input conveyor set to conveyor number C%d.%n", this.ID, c.ID);
        this.input = c;
    }

    /* Output specification #2 */
    public void Output(Conveyor c){
        System.out.printf("Routing Station %d: Output conveyor set to conveyor number C%d.%n", this.ID, c.ID);
        this.output = c;
    }

    /* Output specification #3 */
    // Routing Station X Has Total Workload of n Package Groups.

    public void setWorkload(int work){
        System.out.printf("Routing Station %d: Workload set. %nRouting Station %d has a total workload of %d package groups.%n", this.ID, this. ID, this.work);
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
            /* Output specification #4 */
            if(input.lock.tryLock()){
                System.out.printf("Routing Station %d: holds lock on input conveyor C%d.%n", this.ID, input.ID);
                /* Output specification #5 */
                if(output.lock.tryLock()){
                    System.out.printf("Routing Station %d: holds lock on output conveyor C%d.%n", this.ID, output.ID);
                    doWork();
                } else {

                   /* Output specification #8 */
                    System.out.printf("Routing Station %d: unable to lock output conveyor C%d.%n", this.ID, output.ID);
                    System.out.printf("SYNCHRONIZATION ISSUE: Station %d currently holds the lock on output conveyor %d – releasing lock on input conveyor C%d.%n", this.ID, output.ID, input.ID);

                    input.lock.unlock();
                }

            }
            /* Output specification #6 */
            if(input.lock.isHeldByCurrentThread()){
                System.out.printf("Routing Station %d: unlocks/releases input conveyor C%d.%n", this.ID, input.ID);
                input.lock.unlock();
            }
            /* Output specification #7 */
            if(output.lock.isHeldByCurrentThread()){
                System.out.printf("Routing Station %d: unlocks/releases output conveyor C%d.%n", this.ID, output.ID);
                output.lock.unlock();
            }

            goToSleep();
        }

        System.out.printf("# # # # Routing Station %d: Workload successfully completed! * * * Routing Station %d preparing to go offline. # # # # %n%n", ID, ID);

    }



}
