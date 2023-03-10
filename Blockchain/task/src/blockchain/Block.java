package blockchain;

import java.util.LinkedList;
import java.util.List;

public class Block {
    String uniqueID;
    List<Transaction> transactionList;
    long timestamp;
    String hashPreviousBlock;
    Block previousBlock;
    String hashBlock;
    int magicNumber;
    String createdByMiner;
    int diffcultyWhileCreated;
    int timeNeededToCreate;
    boolean interrupt;
    Blockchain blockchain;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());

    //Block parameters are set through the ConcreteBlockBuilder
    Block() {

        transactionList = new LinkedList<>();

    }


    //used to generate Hash of Block
    public String toString() {

        String paramtersAsString = uniqueID + timestamp + hashPreviousBlock + transactionList.toString() + magicNumber;
        return paramtersAsString;

    }

    //Prints out block info for the Test requirement of HyperSkill
    public void printBlock() {
        System.out.println("Block:");
        System.out.printf("Created by: miner%s%n", getCreatedByMiner());
        System.out.printf("%s gets 100 VC%n", blockchain.entityBC.get(Integer.parseInt(getCreatedByMiner())).name);
        System.out.printf("Id: %s%n", getUniqueID());
        System.out.printf("Timestamp: %s%n", getTimestamp());
        System.out.printf("Magic number: %s%n", getMagicNumber());
        System.out.printf("Hash of the previous block:%n%s%n", getHashPreviousBlock());
        System.out.printf("Hash of the block:%n%s%n", getHashBlock());
        System.out.print("Block data: ");
        if (transactionList.size() == 0)
            System.out.println("No transactions");
        else {
            for (Transaction transaction : transactionList) {
                if (transaction.from != -1) {
                    System.out.printf("%n%s sent %d VC to %s", blockchain.entityBC.get(transaction.from).name, transaction.amount, blockchain.entityBC.get(transaction.to).name);
                }
                //else System.out.printf("Blockreward sent %d VC to %s%n",  transaction.amount, blockchain.entityBC.get(transaction.to));
            }
        }
        System.out.printf("%nBlock was generating for %d seconds%n", getTimeNeededToCreate());
        System.out.printf("N was increased to %s%n", getDiffcultyWhileCreated() + 1);
        System.out.println();
    }

    //Getter and Setter
    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(LinkedList<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHashPreviousBlock() {
        return hashPreviousBlock;
    }

    public void setHashPreviousBlock(String hashPreviousBlock) {
        this.hashPreviousBlock = hashPreviousBlock;
    }

    public String getHashBlock() {
        return hashBlock;
    }

    public void setHashBlock(String hashBlock) {
        this.hashBlock = hashBlock;
    }

    public Block getPreviousBlock() {
        return previousBlock;
    }

    public void setPreviousBlock(Block previousBlock) {
        this.previousBlock = previousBlock;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getTimeNeededToCreate() {
        return timeNeededToCreate;
    }

    public void setTimeNeededToCreate(int timeNeededToCreate) {
        this.timeNeededToCreate = timeNeededToCreate;
    }

    public String getCreatedByMiner() {
        return createdByMiner;
    }

    public void setCreatedByMiner(String createdByMiner) {
        this.createdByMiner = createdByMiner;
    }

    public int getDiffcultyWhileCreated() {
        return diffcultyWhileCreated;
    }

    public void setDiffcultyWhileCreated(int diffcultyWhileCreated) {
        this.diffcultyWhileCreated = diffcultyWhileCreated;
    }


}
