package blockchain;

import java.time.Instant;
import java.util.LinkedList;

public class Block {
    String uniqueID;
    LinkedList<Transaction> transactionList;
    long timestamp;
    String hashPreviousBlock;
    String hashBlock;

    //Block parameters are set through the ConcreteBlockBuilder
    Block(){
        transactionList = new LinkedList<>();
    }

    public String toString(){

        String paramtersAsString = uniqueID+transactionList.toString()+timestamp+hashPreviousBlock;
        return paramtersAsString;

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
}
