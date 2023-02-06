package blockchain;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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
    private static Blockchain instance;

    static {
        try {
            instance = new Blockchain();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    Observer minerObservers;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    List<Transaction> transactionList;
    Map<Integer, Entity> entityBC;
    int transactionCounter;
    java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());

    Blockchain() throws NoSuchAlgorithmException, NoSuchProviderException {
        this.listOfBlocks = new LinkedList<>();
        this.director = new BlockDirector();
        this.minerObservers = new Observer();
        this.transactionList = new ArrayList<>();
        this.entityBC = new HashMap<>();
        this.transactionCounter = 0;
        difficulty = -10;
    }

    //singleton functions
    public static Blockchain getInstance() throws NoSuchAlgorithmException, NoSuchProviderException {
        if (instance == null) {
            instance = new Blockchain();
        }
        return instance;
    }

    public boolean checkAllTransactions(Block block) throws Exception {
        for (Transaction transaction : block.transactionList){
            if(!verifySignature(transaction, entityBC.get(transaction.userID)))
                return false;
            if(transaction.from != -1)
                if(!checkTransactionAmt(transaction.from,transaction.amount))
                    return false;
        }
        return true;
    }

    public synchronized boolean addBlock(Block block){
        try {
            lock.writeLock().lock();
            if (listOfBlocks.size() > 0) {
                if (HashUtil.startsWithXZero(block.hashBlock) >= difficulty && block.getHashPreviousBlock().equals(headBlock.getHashBlock())) { //
                   if(!checkAllTransactions(block)  ) {

                           lock.writeLock().unlock();
                           return false;

                   }
                    //logger.info("TChecking ID");
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
                //else return false;

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

    public int getBalance(int entityID) {

        int received =this.listOfBlocks.stream().map(block -> block.transactionList).flatMap(Collection::stream).filter(transaction -> transaction.to == (entityID)).map(transaction -> transaction.amount).reduce(0,Integer::sum);
        int send =  this.listOfBlocks.stream().map(block -> block.transactionList).flatMap(Collection::stream).filter(transaction -> transaction.from == (entityID)).map(transaction -> transaction.amount).reduce(0,Integer::sum);
        int sumBalance = received - send;
        return sumBalance;
    }

    // verify user signature
    boolean verifySignature(Transaction data, Entity entity) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(entity.keys.getPublicKey());
        sig.update(data.toString().getBytes());
        boolean returnVal = sig.verify(data.signature);

        if(!returnVal){
        }
        return  returnVal;
    }

    public int getTransactionCounter() {
        transactionCounter++;
        return transactionCounter;
    }

    public boolean allTransactionCountersHigher(Block block) {

        if(block.transactionList.size() != 0) {
            if (block.transactionList.stream().map(transaction -> transaction.id).min(Integer::compare).get() > headBlock.transactionList.stream().map(transaction -> transaction.id).max(Integer::compare).get())
                return true;
            else return false;
        }
        else return true;

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
    public boolean checkTransactionAmt(int from, int amt) {
        if(getBalance(from) >= amt)
            return true;
        else return false;
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
