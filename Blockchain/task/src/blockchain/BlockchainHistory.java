package blockchain;

import java.util.ArrayDeque;
import java.util.Deque;


//class to implement Memento patter for blockchain
public class BlockchainHistory {

    private final Blockchain blockchain;
    private final Deque<Blockchain.BlockchainState> history = new ArrayDeque<>();

    public BlockchainHistory(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void save() {
        history.push(blockchain.getState());
    }

    public void undo() {
        blockchain.setState(history.pop());
    }
}
