import java.util.concurrent.Semaphore;

public class Main {
    // private static Semaphore sem = new Semaphore( 0, true ); Semaphore EXAMPLE

    private static Semaphore Information_Desk = new Semaphore( 1, true ); // Used to get the availability of the Info Desk
    private static Semaphore Information_DeskLine = new Semaphore(0, true ); // So SIGNALS that a CUSTOMER is waiting in LINE
    private static Semaphore Waiting_Area = new Semaphore( 0, true ); // Keeps TRACK of Waiting Area
    private static Semaphore Agent_Line = new Semaphore( 4, true ); // Makes sure that only 4 in Agent LINE
    private static Semaphore Agents = new Semaphore( 2, true ); // Used to get the availability of Agents

    private static Semaphore test = new Semaphore( 0, true );
    private static Semaphore Desk_Numb = new Semaphore( 0, true );
    private static Semaphore CustGrabbed_Num = new Semaphore( 0, true );
    private static int num_to_assign = 1; // Might need a semaphore to guarantee that the number is assigned

    // There are going to be more SEMAPHORES to get the readiness like InfoDesk

    public static void main(String[] args){


        // Create Customers
        // We can just start doing everything one at a time.
        // For instance, we start by creating customers that go through the Information_Desk and then get their number.
        // After that we can add in the Waiting Area etc etc. One at a time. Step by step.
        int num_of_cust = 2;
        Customer[] cust_arr = new Customer[num_of_cust];
        Thread[] cust_threads = new Thread[num_of_cust];
    }

    public static void SetNum_InfoDesk(int num){
        num_to_assign = num;
    }

    public static void IncreNum(){
        num_to_assign++;
    }

    private class Customer implements Runnable{
        private int num_assigned;
        private int cust_num;
        public Customer(int num){
            cust_num = num;
        }

        public void SetNum_Cust(int num){
            num_assigned = num;
        }

        @Override
        public void run() {
            try{
                System.out.println("Customer " + cust_num + " created, enters DMV ");
                //2) Waits in line at Information Desk to get a number
                // So releasing actually increases the number by 1
                // Acquiring reduces the number by 1
                Information_DeskLine.release(); // Stands in line, alerts the InfoDesk that a customer is there

                Information_Desk.acquire(); // Gets Info Desk number, this thread gets InfoDesk or else is blocked -- ensures that only one customer thread gets infoDesk
                // What I need to do is say alert the Customer thread that there is a InfoDesk number that it needs to grab and then allow the Customer thread to do it. This will require a semaphore.
                // Below gets InfoDesk's Number
                CustGrabbed_Num.release();

            }
            catch(InterruptedException e){
            }

        }
    }

    private class InfoDesk implements Runnable{
        @Override
        public void run(){
            try{
                Information_DeskLine.acquire(); // Reduces the DeskLine by one, basically saying hey let me serve a customer
                // The question is how do I want to deal with releasing? I think it would be best to release after there's a number available. NVM!!!



                Information_Desk.release(); //This might actually be conceptually like saying that a number is ready to be grabbed
                CustGrabbed_Num.acquire();
                IncreNum();
            }
            catch(InterruptedException e){
            }
        }
    }

    private class Announcer implements Runnable{
        @Override
        public void run(){

        }
    }

    private class Agent implements Runnable{
        @Override
        public void run(){

        }
    }

}
