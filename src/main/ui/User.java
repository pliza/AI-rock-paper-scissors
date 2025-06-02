package main.ui;

import java.util.ArrayList;
import java.util.Scanner;

import main.model.Move;
import main.model.Prob;
import main.model.Tracker;

public class User {

    String name;
    Tracker tracker;
    int playerScore; 
    int AIScore;

    public User(String name) { 
        this.name = name;
        tracker = new Tracker();
    }

    public void play(Scanner scanner, Move prevMove) { 
        Move currMove;

        for(int i = 0; i < 50; i ++) { 

            Move AImove = getAIMove(prevMove);

            // let the user make thier move
            System.out.println("\nEnter your move. rock, paper, or scissors");
            String moveName = scanner.nextLine().toLowerCase(); 
            currMove = new Move(moveName);

            // moves recorded
            tracker.addMove(prevMove, currMove);

            // let AI play 
            System.out.println("AI plays: " + AImove.getName());


            // score recorded and displayed 
            AIScore += getScore(currMove, AImove);
            playerScore += getScore(AImove, currMove);

            System.out.println("your score: " + playerScore + " AI score: " + AIScore);
            prevMove = currMove;

        }

    }

    public Move getAIMove(Move prevMove) { 
        ArrayList<Prob> probs = tracker.getProbTracker().get(prevMove);
            Move mostLikely = new Move("rock");
            if(probs != null) { 
                int max = 0;
                for(Prob p : probs) {
                    if(p.getCount() >= max) {
                        max = p.getCount();
                        mostLikely = p.getMove();
                    }
                }
            }
            
            Move AImove = null;
            System.out.println("most likely next move is : " + mostLikely.getName());
            if(mostLikely.getName().toLowerCase().equals("rock")) { 
                AImove = new Move("paper");
            } else if(mostLikely.getName().toLowerCase().equals("paper")) { 
                AImove = new Move("scissors");
            } else if (mostLikely.getName().toLowerCase().equals("scissors")){ 
                AImove = new Move("rock");
            }
            System.out.println("your score: " + playerScore + " AI score: " + AIScore);
        return AImove;
    }

    public int getScore(Move currMove, Move AImove) { 
        String curr = currMove.getName().toLowerCase(); 
        String AI = AImove.getName().toLowerCase();
        if(curr.equals(AI)) {
            return 0;
        } else if ((curr.equals("rock") && AI.equals("paper")) ||
                    (curr.equals("paper") && AI.equals("scissors")) || 
                    (curr.equals("scissors") && AI.equals("rock"))) { 
                        return 1;
        } else {
            return 0;
        }
    }

    public void viewHistory() { 

    }

    public void leaderboard() { 

    }

    public Tracker getTracker() { 
        return tracker;
    }
    
}
