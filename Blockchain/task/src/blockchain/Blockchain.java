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
    static Block headBlock = null;
    //singleton instance
    private static Blockchain instance;
    public LinkedList<Block> listOfBlocks;
    public transient BlockDirector director;
    public int difficulty = 0;
    Observer minerObservers;


    ReadWriteLock lock = new ReentrantReadWriteLock();
    List<Transaction> transactionList;
    Map<Integer, Entity> entityBC;
    int transactionCounter;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());

    Blockchain() throws NoSuchAlgorithmException, NoSuchProviderException {
        this.listOfBlocks = new LinkedList<>();
        this.director = new BlockDirector();
        this.minerObservers = new Observer();
        this.transactionList = new ArrayList<>();
        this.entityBC = new HashMap<>();
        this.transactionCounter = 0;
        difficulty = -10;
        instance = this;
    }

    //singleton functions
    public static Blockchain getBlockchainInstance() throws NoSuchAlgorithmException, NoSuchProviderException {
        if (instance == null) {
            instance = new Blockchain();
        }
        return instance;
    }

    private boolean checkAllTransactions(Block block) throws Exception {
        for (Transaction transaction : block.transactionList) {
            if (!verifySignature(transaction, entityBC.get(transaction.userID)))
                return false;
            if (transaction.from != -1)
                if (!checkTransactionAmt(transaction.from, transaction.amount))
                    return false;
        }
        return true;
    }

    public synchronized boolean addBlock(Block block) {
        try {

            if (listOfBlocks.size() > 0) {

                return createBlockIfHashAndTransactionsLegalAndNotFirstBlock(block);

            } else return createGenesisBlockWithLegalChecks(block);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private boolean createBlockIfHashAndTransactionsLegalAndNotFirstBlock(Block block) throws Exception {
        lock.writeLock().lock();
        if (HashUtil.startsWithXZero(block.hashBlock) >= difficulty && block.getHashPreviousBlock().equals(headBlock.getHashBlock())) {
            if (!checkTransactionsOfListIfLegal(block)) {
                lock.writeLock().unlock();
                return false;
            }

            addBlockToBlockList(block);
            lock.writeLock().unlock();
            return true;
        }
        lock.writeLock().unlock();
        return false;
    }

    private boolean createGenesisBlockWithLegalChecks(Block block) throws Exception {
        lock.writeLock().lock();
        if (HashUtil.startsWithXZero(block.hashBlock) >= difficulty && headBlock == null) {

            addBlockToBlockList(block);
            lock.writeLock().unlock();
            return true;
        }
        lock.writeLock().unlock();
        return false;
    }


    private void addBlockToBlockList(Block block) {

        listOfBlocks.add(block);
        headBlock = block;
        minerObservers.notify(Event.NEWBLOCK);
        if (difficulty == block.diffcultyWhileCreated) determineNewDifficulty(block);

    }

    private boolean checkTransactionsOfListIfLegal(Block block) throws Exception {

        if (!checkAllTransactions(block)) {

            lock.writeLock().unlock();
            return false;

        }
        if (!allTransactionCountersHigher(block)) {

            lock.writeLock().unlock();
            return false;

        } else return true;


    }

    public synchronized void addTransaction(Transaction transaction) {
        lock.writeLock().lock();
        this.transactionList.add(transaction);
        lock.writeLock().unlock();
    }

    public void resetTransactionList() {
        transactionList = new ArrayList<>();
    }

    boolean validateChain() {

        boolean isValid = true;
        for (int blockNumber = listOfBlocks.size() - 1; blockNumber > 0; blockNumber--) {
            if (!validatePreviousBlock(listOfBlocks.get(blockNumber), listOfBlocks.get(blockNumber - 1))) {
                isValid = false;
            }
        }
        return isValid;
    }

    private boolean validatePreviousBlock(Block currentBlock, Block previousBlock) {

        return currentBlock.getHashPreviousBlock().equals(HashUtil.applySha256(previousBlock.toString()));
    }

    private void determineNewDifficulty(Block block) {
        if (block.timeNeededToCreate < 5) {
            difficulty += 1;
        } else if (difficulty == 1) {
        } else difficulty -= 1;

    }

    public int getBalance(int entityID) {

        int received = this.listOfBlocks.stream().map(block -> block.transactionList).flatMap(Collection::stream).filter(transaction -> transaction.to == (entityID)).map(transaction -> transaction.amount).reduce(0, Integer::sum);
        int send = this.listOfBlocks.stream().map(block -> block.transactionList).flatMap(Collection::stream).filter(transaction -> transaction.from == (entityID)).map(transaction -> transaction.amount).reduce(0, Integer::sum);
        int sumBalance = received - send;
        return sumBalance;
    }

    // verify user signature
    public boolean verifySignature(Transaction data, Entity entity) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(entity.keys.getPublicKey());
        sig.update(data.toString().getBytes());
        boolean returnVal = sig.verify(data.signature);

        if (!returnVal) {
        }
        return returnVal;
    }

    public int getTransactionCounter() {
        transactionCounter++;
        return transactionCounter;
    }

    private boolean allTransactionCountersHigher(Block block) {

        if (block.transactionList.size() != 0) {
            return block.transactionList.stream().map(transaction -> transaction.id).min(Integer::compare).get() > headBlock.transactionList.stream().map(transaction -> transaction.id).max(Integer::compare).get();
        } else return true;

    }

    public LinkedList<Block> getListOfBlocks() {
        return listOfBlocks;
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

    public BlockchainState getState() {
        return new BlockchainState(listOfBlocks, headBlock);
    }

    public void setState(BlockchainState state) {
        this.listOfBlocks = new LinkedList<>(state.listOfBlocks);
        headBlock = state.headBlock;
    }

    public boolean checkTransactionAmt(int from, int amt) {
        return getBalance(from) >= amt;
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
