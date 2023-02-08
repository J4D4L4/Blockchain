package blockchain;


import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Miner extends Entity implements EventListener, Runnable {

    public Blockchain blockchain;
    public int ID;
    boolean interrupt;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());

    @Override
    public void update() {
    }

    public Block createBlock() throws Exception {

        while (true) {

            if (blockchain.listOfBlocks.size() == 0) {
                return makeGenesisBlock();
            } else {
                return makeBlock();

            }
        }
    }

    private Block makeGenesisBlock() throws Exception {
        Block newBlock = blockchain.director.makeBlock(null, blockchain.difficulty, blockchain, this, createMinerReward());

        if (HashUtil.startsWithXZero(newBlock.hashBlock) >= blockchain.difficulty) {
            newBlock.createdByMiner = "" + ID;
            if (blockchain.addBlock(newBlock)) {
                return newBlock;
            } else return null;
        } else return null;

    }

    private Block makeBlock() throws Exception {
        TimeUnit.MILLISECONDS.sleep(100);
        Block newBlock = blockchain.director.makeBlock(Blockchain.headBlock, blockchain.difficulty, blockchain, this, createMinerReward());

        if (HashUtil.startsWithXZero(newBlock.hashBlock) >= blockchain.difficulty) {

            newBlock.createdByMiner = "" + ID;
            if (blockchain.addBlock(newBlock)) {
                return newBlock;
            } else return null;
        } else return null;
    }

    public Transaction createMinerReward() {
        Transaction transaction = new Transaction(-1, this.ID, this.ID, 100);

        transaction.id = blockchain.getTransactionCounter();
        try {
            transaction.sign(this.keys.getPrivateKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return transaction;
    }

    public Transaction createTransaction() {
        Random rand = new Random();
        int randomID = rand.nextInt(blockchain.entityBC.size() - 1);
        Transaction transaction = new Transaction(this.ID, randomID, this.ID, 100);

        transaction.id = blockchain.getTransactionCounter();
        try {
            transaction.sign(this.keys.getPrivateKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        blockchain.addTransaction(transaction);
        return transaction;
    }


    //@Override
    public void run() {

        Block returnBlock = null;
        try {
            if (blockchain.getBalance(this.ID) > 0) {
                createTransaction();
            }
            returnBlock = createBlock();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (returnBlock != null) {

        }


    }


}
