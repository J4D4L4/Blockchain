package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ConcreteBlockBuilder implements BlockBuilder {

    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());
    private Block block;

    public ConcreteBlockBuilder() {
        block = new Block();
    }

    @Override
    public void buildTimeStamp() {
        block.setTimestamp(new Date().getTime());
    }


    @Override
    public void buildListOfTransactions(List<Transaction> transactionList, Transaction minerReward) throws Exception {
        this.block.transactionList = transactionList;

        this.block.transactionList.add(minerReward);
    }


    @Override
    public void buildHashOfPreviousBlock(Block previousBlock) {
        if (previousBlock != null) {
            block.setHashPreviousBlock(previousBlock.getHashBlock());
        } else {
            block.setHashPreviousBlock("0");
        }

    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public void buildHash(int numberOfZero) {
        String prelimHash;

        while (true) {
            block.magicNumber = ThreadLocalRandom.current().nextInt(0, 99999999 + 1);
            prelimHash = HashUtil.applySha256(block.toString());
            int startsWithZeros = prelimHash.length() - prelimHash.replaceAll("^0+", "").length();
            if (startsWithZeros >= numberOfZero) break;
        }

        block.setHashBlock(prelimHash);
    }

    public void reset() {
        block = new Block();
    }

    public void buildID(Block previousBlock) {

        if (previousBlock != null) {
            block.setUniqueID("" + (Integer.parseInt(previousBlock.getUniqueID()) + 1));
        } else {
            block.setUniqueID("" + 1);
        }


    }

    @Override
    public void buildPreviousBlock(Block previousBlock) {
        if (previousBlock == null) {
            block.setHashPreviousBlock("0");
        } else {
            block.setHashPreviousBlock(HashUtil.applySha256(previousBlock.toString()));
        }

        block.setPreviousBlock(previousBlock);
    }

    public void buildDifficulty(int difficulty) {

        block.setDiffcultyWhileCreated(difficulty);


    }

    public void setTimeNeededToCreate(int time) {
        block.timeNeededToCreate = time;
    }

    @Override
    public void buildBlockchain() throws NoSuchAlgorithmException, NoSuchProviderException {
        block.blockchain = Blockchain.getBlockchainInstance();
    }


}
