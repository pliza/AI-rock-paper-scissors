package Main;
import java.util.ArrayList;
import java.util.HashMap;

public class Tracker {

    public HashMap<Move,ArrayList<Prob>> probTracker;


    public Tracker() { 
        this.probTracker = new HashMap<>();
    }

    // EFFECTS: add the move if ROCK, PAPER or SCISSOR          
    public void addMove(Move prev, Move curr) { 
        // current and previous moves are valid
        if (isValid(prev.getName()) && isValid(curr.getName())) { 
            // if we already have the previous move as a key
            if(probTracker.containsKey(prev)) { 
                // get the ArrayList of probs for that key
                for(Prob p : probTracker.get(prev)) { 
                    //if curr move is already stored as a prob
                    if(p.getMove().equals(curr)) { 
                        p.addCount();
                        System.out.println(p.getCount());
                        return;
                    }
                }
                // prev move is there but curr move is not
                System.out.println("shouldnt run");
                probTracker.get(prev).add(new Prob(curr, 1));
            } else { 
                // put key as prev move and value as new ArrayList w prob of current move and count 1
                ArrayList<Prob> toPut = new ArrayList<>(); 
                toPut.add(new Prob(curr, 1));
                probTracker.put(prev, toPut);
            }
        }
    }

    public boolean isValid(String name) { 
        name = name.toLowerCase();
        if(name.equals("rock") || name.equals("paper") || name.equals("scissors")) { 
            return true;
        } else { 
            return false;
        }
    }

    //getters 
    public HashMap<Move,ArrayList<Prob>> getProbTracker() { 
        return probTracker;
    }
    

}
