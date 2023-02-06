package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MinerBuilder {

    private Miner miner;
    int idCounter = 1;

    public MinerBuilder() {
        this.reset();
    }

    public void setBlockchain() throws NoSuchAlgorithmException, NoSuchProviderException {
        miner.blockchain = Blockchain.getInstance();
        miner.blockchain.entityBC.put(miner.ID, miner);

    }

    public void setKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
        miner.keys = HashUtil.createKeys();
    }

    public void setNotify(Blockchain blockchain) {
        blockchain.minerObservers.subscribe(miner, Event.NEWBLOCK);
    }

    public void setID(int id) {
        miner.ID = id;
        miner.name ="Miner "+miner.ID;

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
