package blockchain;


import java.util.concurrent.Callable;

public class Miner implements EventListener, Runnable {

    public Blockchain blockchain;
    public int ID;
    boolean interrupt;

    @Override
    public void update() {
        return;
    }

    public Block createBlock() {
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

                Block newBlock = blockchain.director.makeBlock(Blockchain.headBlock, blockchain.difficulty,blockchain);
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

        Block returnBlock = createBlock();
        if (returnBlock != null) {

        }




    }


}
