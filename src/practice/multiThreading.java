package practice;

public class multiThreading {
    public static void main(String[] args) {

        /*
        Multi-threading is the ability to execute multiple different paths of code at the same time.

        In java there are two main ways of creating a new thread.
        The first way is to have a class ~extend~ the thread class.
        Here's how. Let's create a new class (notes continued on other class
        file too.

        */

        // coming back from our other class multithreadThing, lets create an object

    //    multithreadThing myThing = new multithreadThing();
    //    multithreadThing myThing2 = new multithreadThing();
        // writing this ->  myThing.run(); will run the code we wrote in that method but won't do it in a seperate thread
        // instead, we should write this
     //   myThing.start();

        /*
        What's going on here?
        As soon as it gets to this part in the code, line 21, java will branch off a new thread and start running this run method.
        After it kicks off that new thread and lets it run, it will proceed down the main thread that it was executing here.
        */

    //    myThing2.start();

        // if you want to create multiple threads in one loop, you can do it like this
        // we want to assign a number to each thread to see which is printing which number, we can do this by creating
        // a new constructor in the multithreadThing class
        for (int i = 0; i <=3; i++) {
            multithreadThing myThing = new multithreadThing(i);
            myThing.start();

            // now if you put another addition in the print method below, you can print out which thrad it is.
        }





    }
}
