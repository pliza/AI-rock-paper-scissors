package main.ui;

import java.util.Scanner;

import main.model.Move;
import main.model.Paper;
import main.model.Rock;
import main.model.Scissors;
import main.model.Tracker;

public class User {

    String name;
    Tracker tracker;

    public User(String name) { 
        this.name = name;
        tracker = new Tracker();
    }

    public void play(Scanner scanner, Move firstMove) { 
        Move currMove; 

        for(int i = 0; i < 6; i ++) { 

            // let the user make thier move
            System.out.println("\n Enter your move. rock, paper, or scissors");
            String moveName = scanner.nextLine().toLowerCase(); 
            switch(moveName) { 
                case "rock" :  currMove = new Rock(moveName); 
                case "paper" :  currMove = new Paper(moveName); 
                case "scissors" :  currMove = new Scissors(moveName); 
                default : System.out.println("not a valid move. Default move is rock");
                            currMove = new Rock("rock");
            }

            // moves recorded and move made back beased on tracker
            tracker.addMove(firstMove, currMove);

            // score recorded and displayed 


        }

    }

    public void viewHistory() { 

    }

    public void leaderboard() { 

    }
    
}
