package blockchain;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.Signature;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//singleton
public class Blockchain implements Serializable {
    private static final long serialVersionUID = 1L;
    LinkedList<Block> listOfBlocks;
    static Block headBlock = null;
    transient BlockDirector director;
    int difficulty = 0;
    //singleton instance
    private static Blockchain instance = new Blockchain();
    Observer minerObservers;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    List<Transaction> transactionList;
    Map<Integer, User> userBC;
    int transactionCounter;


    private Blockchain(){
        this.listOfBlocks = new LinkedList<>();
        this.director = new BlockDirector();
        this.minerObservers = new Observer();
        this.transactionList = new ArrayList<>();
        this.userBC = new HashMap<>();
        this.transactionCounter = 0;
        difficulty = 0;
    }

    //singleton functions
    public static Blockchain getInstance() {
        if (instance == null) {
            instance = new Blockchain();
        }
        return instance;
    }

    public boolean checkAllTransactions(Block block) throws Exception {
        for (Transaction transaction : block.transactionList){
            if(verifySignature(transaction, userBC.get(transaction.userID)))
                return false;
        }
        return true;
    }

    public synchronized boolean addBlock(Block block){
        try {
            lock.writeLock().lock();
            if (listOfBlocks.size() > 0) {
                if (HashUtil.startsWithXZero(block.hashBlock) >= difficulty && block.getHashPreviousBlock().equals(headBlock.getHashBlock())) {
                    //block.transactionList = transactionList;
                    //resetTransactionList();
                   if(!checkAllTransactions(block)  ) {

                           lock.writeLock().unlock();
                           return false;

                   }
                   if(!allTransactionCountersHigher(block)) {

                       lock.writeLock().unlock();
                       return false;

                   }
                    listOfBlocks.add(block);
                    headBlock = block;
                    minerObservers.notify(Event.NEWBLOCK);
                    if(difficulty == block.diffcultyWhileCreated) determineNewDifficulty(block);
                    lock.writeLock().unlock();
                    //minerObservers.notify(Event.NEWBLOCK);
                    return true;
                }

            } else if (HashUtil.startsWithXZero(block.hashBlock) >= difficulty && headBlock == null) {

                listOfBlocks.add(block);
                headBlock = block;
                if(difficulty == block.diffcultyWhileCreated) determineNewDifficulty(block);
                lock.writeLock().unlock();
                minerObservers.notify(Event.NEWBLOCK);
                return true;
            }
            lock.writeLock().unlock();
        }
        catch (NullPointerException r){
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public synchronized void addTransaction(Transaction transaction){
        lock.writeLock().lock();
        this.transactionList.add(transaction);
        lock.writeLock().unlock();
    }
    public void resetTransactionList() {
        transactionList = new ArrayList<>();
    }

    public boolean validateChain(){

        boolean isValid = true;
        for (int blockNumber = listOfBlocks.size()-1; blockNumber > 0; blockNumber-- ){
            if(!validatePreviousBlock(listOfBlocks.get(blockNumber),listOfBlocks.get(blockNumber-1))){
                isValid = false;
            }
        }
        return isValid;
    }

    boolean validatePreviousBlock(Block currentBlock, Block previousBlock){

        if(currentBlock.getHashPreviousBlock().equals(HashUtil.applySha256(previousBlock.toString()))){
            return true;
        }
        else return false;
    }

    public void determineNewDifficulty(Block block){
        if(block.timeNeededToCreate<5){
            difficulty += 1;
        }
        else if (difficulty == 1) return;
        else difficulty -=1;

    }

    // verify user signature
    private boolean verifySignature(Transaction data, User user) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(user.keys.getPublicKey());
        sig.update(data.toString().getBytes());
        boolean returnVal = sig.verify(data.signature);
        return  returnVal;
    }

    public int getTransactionCounter() {
        transactionCounter++;
        return transactionCounter;
    }

    public boolean allTransactionCountersHigher(Block block) {

       if ( block.transactionList.stream().map( transaction -> transaction.id).min(Integer::compare).get() > headBlock.transactionList.stream().map(transaction -> transaction.id).max(Integer::compare).get())
           return true;
       else return false;

    }

    //Serialization Functions

    private void writeObject(ObjectOutputStream oos) throws Exception {
        oos.defaultWriteObject();

    }

    private void readObject(ObjectInputStream ois) throws Exception {
        ois.defaultReadObject();
        this.director = new BlockDirector();
    }
    //Memento functions set and get state

    public BlockchainState getState(){
        return new BlockchainState(listOfBlocks, headBlock);
    }

    public void setState(BlockchainState state) {
        this.listOfBlocks = new LinkedList<>(state.listOfBlocks);
        this.headBlock = state.headBlock;
    }



    //Memento Functions: State Object
    class BlockchainState {
        private final LinkedList<Block> listOfBlocks;
        private final Block headBlock;

        BlockchainState(LinkedList<Block> listOfBlocks, Block headBlock) {
            this.listOfBlocks = listOfBlocks;
            this.headBlock = headBlock;
        }
    }


}
