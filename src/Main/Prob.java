package Main;
public class Prob {

    public Move move; 
    public int count;

    public Prob(Move move, int count) { 
        this.move = move; 
        this.count = count;
    }

    //getters 
    public Move getMove() { 
        return move;
    }

    public int getCount() { 
        return count;
    }

    public void addCount() {
        count += 1;
    }
    
}
