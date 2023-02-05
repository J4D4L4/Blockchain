package blockchain;


import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Miner implements EventListener, Runnable {

    public Blockchain blockchain;
    public int ID;
    boolean interrupt;
    java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());

    @Override
    public void update() {
        return;
    }

    public Block createBlock() throws InterruptedException {

        while (true) {
            if (blockchain.listOfBlocks.size() == 0) {
                Block newBlock = blockchain.director.makeBlock(null, blockchain.difficulty,blockchain);
                if (HashUtil.startsWithXZero(newBlock.hashBlock) >= blockchain.difficulty) {
                    newBlock.createdByMiner = "" + ID;
                    if (blockchain.addBlock(newBlock)) {
                        return newBlock;
                    } else return null;
                }
            } else {
                TimeUnit.MILLISECONDS.sleep(100);
                Block newBlock = blockchain.director.makeBlock(blockchain.headBlock, blockchain.difficulty,blockchain);

                if (HashUtil.startsWithXZero(newBlock.hashBlock) >= blockchain.difficulty) {

                    newBlock.createdByMiner = "" + ID;
                    if (blockchain.addBlock(newBlock)) {
                        return newBlock;
                    } else return null;
                }

            }
        }
    }


    //@Override
    public void run() {

        Block returnBlock = null;
        try {
            returnBlock = createBlock();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (returnBlock != null) {

        }




    }


}
