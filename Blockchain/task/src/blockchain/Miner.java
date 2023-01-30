package blockchain;


public class Miner implements EventListener, Runnable {

    public Blockchain blockchain;
    public int ID;
    boolean interrupt;
    @Override
    public void update() {
        System.out.println("notified");
    }

    public Block createBlock() {
        while (true) {
            if (blockchain.listOfBlocks.size() == 0) {
                Block newBlock = blockchain.director.makeBlock(null, blockchain.difficulty);
                if (HashUtil.startsWithXZero(newBlock.hashBlock) >= blockchain.difficulty) {
                    newBlock.createdByMiner = "" + ID;
                    if(blockchain.addBlock(newBlock)) {
                        return newBlock;
                    }
                    else return null;
                }
            } else {

                Block newBlock = blockchain.director.makeBlock(blockchain.headBlock, blockchain.difficulty);
                if (HashUtil.startsWithXZero(newBlock.hashBlock) >= blockchain.difficulty) {
                    newBlock.createdByMiner = "" + ID;
                    if(blockchain.addBlock(newBlock)) {
                        return newBlock;
                    }
                    else return null;
                }

            }
        }
    }


    @Override
    public void run() {

        boolean run = true;
        //while (run) {
        synchronized (this) {
            Block returnBlock = createBlock();
            if (returnBlock != null) return;
        }

    }
}
