package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.*;

import main.model.Move;
import main.model.Prob;
import main.model.Tracker;

public class TrackerTest {

    public Tracker testTrack; 
    public Move paper; 
    public Move rock; 
    public Move scissors; 


    @BeforeEach
    public void setUp() { 
        this.testTrack = new Tracker();
        this.paper = new Move("paper");
        this.rock = new Move("rock");
        this.scissors = new Move("scissors");
    }

    @Test           
    public void testAddFromRock() { 
        // go from rock to rock 
        testTrack.addMove(rock, rock);
        HashMap<Move,ArrayList<Prob>> testProbTracker = testTrack.getProbTracker();
        ArrayList<Prob> probs = testProbTracker.get(rock);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("rock")) {
                assertEquals(1 ,p.getCount());
            }
        }

        // go from rock to paper 
        testTrack.addMove(rock, paper);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("paper")) {
                assertEquals(1 ,p.getCount());
            }
        }

        // go from rock to scissors 
        testTrack.addMove(rock, scissors);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("scissors")) {
                assertEquals(1 ,p.getCount());
            }
        }

        // go from rock to something that should not work
        testTrack.addMove(rock, new Move("eggo"));
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("eggo")) {
                fail();
            }
        }
    }


    @Test           
    public void testAddFromPaper() { 
        // go from paper to rock 
        testTrack.addMove(paper, rock);
        HashMap<Move,ArrayList<Prob>> testProbTracker = testTrack.getProbTracker();
        ArrayList<Prob> probs = testProbTracker.get(paper);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("rock")) {
                assertEquals(1 ,p.getCount());
            }
        }

        // go from paper to paper 
        testTrack.addMove(paper, paper);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("paper")) {
                assertEquals(1 ,p.getCount());
            }
        }

        // go from paper to scissors 
        testTrack.addMove(paper, scissors);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("scissors")) {
                assertEquals(1 ,p.getCount());
            }
        }
    }

    @Test           
    public void testAddFromScissor() { 

        // go from scissors to rock 
        testTrack.addMove(scissors, rock);
        HashMap<Move,ArrayList<Prob>> testProbTracker = testTrack.getProbTracker();
        ArrayList<Prob> probs = testProbTracker.get(scissors);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("rock")) {
                assertEquals(1 ,p.getCount());
            }
        }

        // go from scissors to paper 
        testTrack.addMove(scissors, paper);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("paper")) {
                assertEquals(1 ,p.getCount());
            }
        }

        // go from scissors to scissors 
        testTrack.addMove(scissors, scissors);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("scissors")) {
                assertEquals(1 ,p.getCount());
            }
        }

    }

    @Test           
    public void testAddMoreThanOne() { 

        // go from scissors to rock 
        testTrack.addMove(scissors, rock);
        HashMap<Move,ArrayList<Prob>> testProbTracker = testTrack.getProbTracker();
        ArrayList<Prob> probs = testProbTracker.get(scissors);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("rock")) {
                assertEquals(1 ,p.getCount());
            }
        }

        // go from scissors to paper 
        testTrack.addMove(scissors, paper);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("paper")) {
                assertEquals(1 ,p.getCount());
            }
        }

        // go from scissors to scissors 
        testTrack.addMove(scissors, scissors);
        for (Prob p : probs) { 
            if(p.getMove().getName().equals("scissors")) {
                assertEquals(1 ,p.getCount());
            }
        }

        // go from scissors to rock again 
        testTrack.addMove(scissors, rock);
        for (Prob p : probs) { 
            if(p.getMove().equals(rock)) {
                assertEquals(2 ,p.getCount());
            } else { 
                assertEquals(1, p.getCount());
            }
        }

    }

    

    
    
}
