package practice;

/*
another way to implement multithreading is implementing the runnable interface
public class multithreadThing implements Runnable

this will basically do the same thing, but in the main method you have to add
Thread myThread = new Thread(myThing);
myThread.start();
*/
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

    private final int threadNumber;

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

            /*
            Timestamp 5:20 in video
            NOTE: when runnig this loop, it prints out the threads randomly
            i.e output is:
            1 from thread 1
            1 from thread 0
            1 from thread 2
            1 from thread 3
            2 from thread 1
            you would assume since thread 0 is created first, it would be printed out first, but in this
            case it isn't.
            what this output tells us is that when you break into multiple threads there's no guarantee which
            thread is going to doing its task first. they're all running at the same time completely independently.

            another great thing about multi-threading is that if one of the threads blows up with an exception or error, it doesn't
            impact the other threads at all.

             */


         //    example of making an exception error occur:

            if( threadNumber == 3) {
                throw new RuntimeException();
            }



            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // how do we kick off this code in multiple threads? lets go back to the main method!

        }

    }
}
