/*
NAME
CLASS - SEMESTER
HOMEWORK #
 */
//Drayson Sanders CIST 1450 Fall Semester HOMEWORK 2

import java.text.DecimalFormat;
import java.util.Scanner;

public class FlightCostCalculator {

    /*
    Do not change any of the code in main.
    The user should be able to book multiple flights before stopping the program.
     */
    public static void main(String args[]) {
        Scanner inputDevice = new Scanner(System.in);
        System.out.println("Welcome to your Flight Cost Calculator! Let's begin!");
        do {
            bookFlight(inputDevice);
        } while (purchaseAnotherTicket(inputDevice));
    }

    /*
    You may modify this method if you choose.
    This gives a rough outline of the steps you need to do to complete the flight cost calculator program.
     */
    public static void bookFlight(Scanner inputDevice) {
        Flyer flyer = createFlyer(inputDevice);
        System.out.println(flyer.getName()+" Do You have any Frequent Flyer Miles for the airlines below?");
        for (int i=0;i<Ticket.AIRLINES.length;i++) {
            System.out.println((i+1)+". "+ Ticket.AIRLINES[i]);
        }

        System.out.println("Please Enter Selection here (y/n):");
        flyer.setFrequentFlyer(inputDevice.nextLine().charAt(0));

        if (flyer.getFrequentFlyer()=='y')
        {
            System.out.println("How many Miles do you have?");
            flyer.setMiles(Integer.parseInt(inputDevice.nextLine()));
        }
        else{
            flyer.setMiles(0);
        }





        // Set frequent flyer
        // Set miles
        Ticket ticket = pickAirline(inputDevice, flyer);
        getDestination(ticket,inputDevice);
        setBasePrice(flyer,ticket, inputDevice);
        mileParameters(flyer,ticket,inputDevice);
        upgrade(flyer,ticket,inputDevice);
        baggage(flyer,ticket,inputDevice);
        insurance(flyer,ticket,inputDevice);

        // Pick destination
        // Calculate ticket cost

    }



    /*
    Do not modify this method.
    This method asks users if they would like to purchase another ticket to rerun the program all over again.
     */
    public static boolean purchaseAnotherTicket(Scanner inputDevice) {
        String purchaseAnotherTicket;
        do {
            System.out.print("Would you like to purchase another ticket? (y/n) ");
            purchaseAnotherTicket = inputDevice.nextLine();
            if (!purchaseAnotherTicket.equals("y") && !purchaseAnotherTicket.equals("n"))
                printErrorMessage();
        } while(!purchaseAnotherTicket.equals("y") && !purchaseAnotherTicket.equals("n"));

        return purchaseAnotherTicket.equals("y");
    }

    /*
    Do not modify this method.
    This is the generic error message you'll print when a user does not give proper user input.
    Look to purchaseAnotherTicket method for example.
     */
    public static void printErrorMessage() {
        System.out.println("ERROR: Incorrect selection.");
    }

    /*
    You may modify this method if you choose.
    This is a starter method to create a new Flyer object.
     */
    public static Flyer createFlyer(Scanner inputDevice) {
        Flyer flyer = new Flyer();
        System.out.print("What is the passenger's name? ");
        flyer.setName(inputDevice.nextLine());
        return flyer;
    }

    /*
    You may modify this method if you choose.
    This method uses the AIRLINES array in the Ticket class.
    This method is an example of data verification you must do for all user input.
     */
    public static Ticket pickAirline(Scanner inputDevice, Flyer flyer) {
        int airlineSelection = 0;
        Ticket ticket = new Ticket();

        while (airlineSelection < 1 || airlineSelection > Ticket.AIRLINES.length) {
            if (flyer.getFrequentFlyer() == 'y')
                System.out.print("Which airline are you a frequent flyer for? ");

            else
                System.out.print("Which airline would you like to ride with? ");
            airlineSelection = Integer.parseInt(inputDevice.nextLine());
            if (airlineSelection < 1 || airlineSelection > Ticket.AIRLINES.length)
                printErrorMessage();
            else
                ticket.setAirlineIndex(airlineSelection - 1);
        }

        return ticket;
    }
    public static void getDestination(Ticket ticket, Scanner inputDevice) {
        int destinationSelection = -1;
        while (destinationSelection < 1 || destinationSelection > Ticket.DESTINATIONS[ticket.getAirlineIndex()].length) {
            System.out.println("Where would you like to go?");
            for (int i = 0; i < Ticket.DESTINATIONS[ticket.getAirlineIndex()].length; i++) {
                System.out.println((i + 1) + ". " + Ticket.DESTINATIONS[ticket.getAirlineIndex()][i]);

            }
            destinationSelection = Integer.parseInt(inputDevice.nextLine());
            if (destinationSelection < 1 || destinationSelection > Ticket.AIRLINES.length)
                printErrorMessage();
            else
                ticket.setDestinationIndex(destinationSelection - 1);
        }
    }

    //set base price
    public static void setBasePrice(Flyer flyer, Ticket ticket, Scanner inputDevice) {
    DecimalFormat df2 = new DecimalFormat("#.##");
    double basePrice = Ticket.PRICES[ticket.getAirlineIndex()][ticket.getDestinationIndex()];
    System.out.println(flyer.getName()+" Your Base Price is: $"+df2.format(+basePrice));
    ticket.setBasePrice(basePrice);
    }
//parameters for miles
    public static Flyer mileParameters(Flyer flyer, Ticket ticket, Scanner inputDevice) {
        int milesUsed=-1;
        DecimalFormat df2 = new DecimalFormat("#.##");
        do {


            if (flyer.getMiles() >=1) {
                System.out.println("How many of your " + flyer.getMiles() + " Miles would you like to use toward your purchase today?");
                milesUsed = Integer.parseInt(inputDevice.nextLine());
                if (milesUsed>flyer.getMiles())
                {
                    printErrorMessage();
                }
                if(milesUsed>=1 && milesUsed <= flyer.getMiles()) {
                    double damnPemdas = milesUsed / 100;

                    double milesMinusPrice = ticket.getBasePrice() - damnPemdas;//creating a variable to store calculation?
                    if(milesMinusPrice>=0)
                    {
                        System.out.println("Your ticket price is $" + df2.format(milesMinusPrice) + " after using miles.");
                        ticket.setBasePrice(milesMinusPrice);
                    }
                    if (milesMinusPrice<=0)
                    {
                        double tooManyMiles=ticket.getBasePrice()-ticket.getBasePrice();
                        System.out.println("Your ticket price is $" + df2.format(tooManyMiles));
                        ticket.setBasePrice(tooManyMiles);
                    }
                }

            }


        } while ((milesUsed < 1 || milesUsed > flyer.getMiles()) && flyer.getFrequentFlyer()=='y');

        return flyer;
    }

    //upgrade?
    public static void upgrade (Flyer flyer, Ticket ticket,Scanner inputDevice)
    {
        int upgrade=0;
        DecimalFormat df2 = new DecimalFormat("#.##");
        if(flyer.getFrequentFlyer()=='y') {
            System.out.println("Since you are a frequent flyer, you may upgrade to business class for free. ");
        }

        System.out.println("Would you like to upgrade to Business or First class?" + "\n1.Business Class" + "\n2.First Class" + "\n3.No Upgrade");
        upgrade = Integer.parseInt(inputDevice.nextLine());


        if(upgrade==1 && flyer.getFrequentFlyer()=='n') {//business
            double firstClass;
            firstClass = ticket.getBasePrice() * 1.3;//price is set to zero still if miles is greater than ticket price
            System.out.println("Your ticket price is $" + df2.format(firstClass) + " after upgrade.");
            ticket.setBasePrice(firstClass);
        }
        if(upgrade==2) {//firstclass
            double classChoice;
            classChoice = ticket.getBasePrice() * 1.7;//price is set to zero still if miles is greater than ticket price
            System.out.println("Your ticket price is $" + df2.format(classChoice) + " after upgrade.");
            ticket.setBasePrice(classChoice);
        }

        if(upgrade==3) {//no upgrade
            System.out.println("Your ticket price is $" + df2.format(ticket.getBasePrice()));
        }

    }

    //baggage
    public static void baggage (Flyer flyer, Ticket ticket, Scanner inputDevice)
    {
        double baggageChoice=0;
        DecimalFormat df2 = new DecimalFormat("#.##");
        do {
            if (flyer.getFrequentFlyer() == 'y') {
                System.out.println("Since you are a frequent flyer, you may take one carry on and your first checked bag is free. ");
                System.out.println("Would you like to purchase additional checked bags? Your first checked bag is $50, every other checked bag is +$100.Enter the number of bags you would like to check (0-10)");
                baggageChoice = Integer.parseInt(inputDevice.nextLine());
            }

            if (flyer.getFrequentFlyer() == 'n') {
                System.out.println("You may take a carry on for free.");
                System.out.println("Would you like to purchase additional checked bags? Your first checked bag is +$50, every other checked bag is +$100. Enter the number of bags you would like to check (0-10)");
                baggageChoice = Integer.parseInt(inputDevice.nextLine());
            }
            if (baggageChoice == 1) {
                double bagSelection;
                bagSelection = ticket.getBasePrice() + 50;
                System.out.println("Your new ticket price is $" + df2.format(bagSelection) + " after checked bag.");
                ticket.setBasePrice(bagSelection);
                break;
            }

            if (baggageChoice > 1 && baggageChoice <= 10) {
                double bagSelection;
                bagSelection = ticket.getBasePrice() + baggageChoice * 100;
                System.out.println("Your ticket price is $" + df2.format(bagSelection) + " after checked bags.");
                ticket.setBasePrice(bagSelection);
                break;
            }
            if (baggageChoice == 0) {
                double bagSelection = ticket.getBasePrice() + baggageChoice * 100;
                System.out.println("Your ticket price is $" + df2.format(bagSelection) + " after checked bags.");
                break;
            }
            if (baggageChoice <= 0 || baggageChoice >= 10)
            {
                printErrorMessage();

            }
        } while(baggageChoice >= 0 || baggageChoice <=10 );
        {

        }
    }

    public static void insurance(Flyer flyer, Ticket ticket, Scanner inputDevice)
    {
        char insurance;
        char youSure='n';
        DecimalFormat df2 = new DecimalFormat("#.##");
        System.out.println("Would you like to purchase travel insurance?(y/n)");
        insurance=inputDevice.nextLine().charAt(0);
        if (insurance=='y') {
            double yessir;
            yessir = ticket.getBasePrice() + 14.99;
            System.out.println("Your Final ticket price is $" + df2.format(yessir) + " after travel insurance.");
            ticket.setBasePrice(yessir);
        }
        if(insurance=='n') {
            double noInsurance;

            System.out.println("Are you sure? This will protect your belongings. You will not be given the chance to purchase again. Buy travel insurance (y/n): ");
            youSure = inputDevice.nextLine().charAt(0);
            noInsurance= ticket.getBasePrice()-0;
            System.out.println("Your Final ticket price is $"+df2.format(noInsurance));
            ticket.setBasePrice(noInsurance);

        }
        if (youSure=='y') {
            double yessir;
            yessir = ticket.getBasePrice() + 14.99;
            System.out.println("Your Final ticket price is $" + df2.format(ticket.getBasePrice() + 14.99) + " after travel insurance.");
            ticket.setBasePrice(yessir);
        }
    }


}
