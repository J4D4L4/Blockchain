package blockchain;

public class MinerBuilder {

    private Miner miner;
    int idCounter = 1;

    public MinerBuilder() {
        this.reset();
    }

    public void setBlockchain() {
        miner.blockchain = Blockchain.getInstance();

    }

    public void setNotify(Blockchain blockchain) {
        blockchain.minerObservers.subscribe(miner, Event.NEWBLOCK);
    }

    public void setID() {
        miner.ID = idCounter;
        idCounter++;
    }

    public void setInterrupt() {
        miner.interrupt = false;
    }

    public void reset() {
        miner = new Miner();
    }

    public Miner getMiner() {
        return miner;
    }

}
