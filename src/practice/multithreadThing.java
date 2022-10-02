package practice;

public class multithreadThing extends Thread {
    /*
    It doesn't matter what this class is called but you must extend thread.
    Now that it extends thread, the only other thing you have to do in this class
    to make it multi-threadable (is that a word?) is to override the thread classes
    run method. just type :
     */


    /* 4:46 timestamp on video
    lets create a new constructor for the looped multithread

    */

    private int threadNumber;

    public multithreadThing (int threadNumber ) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {

        // lets try something simple, like count up the #s from 1-5

        for(int i = 1; i <= 5; i++) {
            System.out.println(i + " from thread " + threadNumber);
            // lets make it sleep for 1 sec between each num so we can watch it
            // note the try catch method automatically was created
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // how do we kick off this code in multiple threads? lets go back to the main method!

        }

    }
}
