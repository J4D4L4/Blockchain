package blockchain;

import java.time.Instant;
import java.util.LinkedList;

public class Block {
    String uniqueID;
    LinkedList<Transaction> transactionList;
    long timestamp;
    String hashPreviousBlock;


    Block previousBlock;
    String hashBlock;

    //Block parameters are set through the ConcreteBlockBuilder
    Block(){
        transactionList = new LinkedList<>();
    }


    //used to generate Hash of Block
    public String toString(){

        String paramtersAsString = uniqueID+transactionList.toString()+timestamp+hashPreviousBlock;
        return paramtersAsString;

    }

    //Prints out block info for the Test requirement of HyperSkill
    public void printBlock(){
        System.out.println("Block:");
        System.out.printf("Id: %s%n",getUniqueID());
        System.out.printf("Hash of the previous block:%n%s%n",getHashPreviousBlock());
        System.out.printf("Hash of the block:%n%s%n",getHashBlock());
    }

    //Getter and Setter
    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public LinkedList<Transaction> getTransactionList() {
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



}
