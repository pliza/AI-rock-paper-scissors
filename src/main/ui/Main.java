package main.ui;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import main.model.Move;
import main.model.Paper;
import main.model.Rock;
import main.model.Scissors;

// deals with introducing user and displaying the menu
public class Main {

    private static Scanner scanner;
    private static User user;
    
    public static void main(String[] args){

        scanner = new Scanner(System.in);

        startUp();
    }

    public static void startUp() { 
        System.out.println("\n Welcome to a game of AI Rock Paper Scissors.");
        System.out.println("\n The AI will learn how you play and try to defeat you.");
        System.out.println("\n Begin by entering a username: \n");

        String name = scanner.nextLine();
        user = new User(name);

        boolean running = true;

        while(running) { 
            displayMenu();
            int option = scanner.nextInt(); 

            switch (option) { 
                case 1:  play();
                case 2 : user.viewHistory();
                case 3 : user.leaderboard();
                case 4 : running = false;
                default: System.out.println("\nNot a valid option");
            }
        }
    }

    public static void displayMenu() { 
        System.out.println("\n🌸 🌸 🌸 Main Menu 🌸 🌸 🌸");
        System.out.println("1. Play a game");
        System.out.println("2. View History");
        System.out.println("3. Leaderboard");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void play() { 
        // first move played is random
        Move firstMove; 
        System.out.println("\n Enter your move. rock, paper, or scissors");
            String moveName = scanner.nextLine().toLowerCase(); 

            switch(moveName) { 
                case "rock" :  firstMove = new Rock(moveName); 
                case "paper" :  firstMove = new Paper(moveName); 
                case "scissors" :  firstMove = new Scissors(moveName); 
                default : System.out.println("not a valid move. Default move is rock");
                            firstMove = new Rock("rock");
            }

            int randomNum = ThreadLocalRandom.current().nextInt(1, 4);

            Move gameMove; 
            switch(randomNum) { 
                case 1 :  { gameMove = new Rock(moveName); 
                            System.out.println("Ai plays back: " + gameMove.getName());
                }   
                case 2 :  { gameMove = new Paper(moveName); 
                            System.out.println("Ai plays back: " + gameMove.getName());
                }   
                case 3 :  { gameMove = new Scissors(moveName); 
                            System.out.println("Ai plays back: " + gameMove.getName());
                }    
            }

            user.play(scanner, firstMove);
        
    }


}
