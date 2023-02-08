package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MinerBuilder {

    int idCounter = 1;
    private Miner miner;

    public MinerBuilder() {
        this.reset();
    }

    public void setBlockchain() throws NoSuchAlgorithmException, NoSuchProviderException {
        miner.blockchain = Blockchain.getBlockchainInstance();
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
        miner.name = "miner" + miner.ID;

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
