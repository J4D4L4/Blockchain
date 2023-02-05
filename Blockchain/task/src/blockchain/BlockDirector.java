package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalTime;

public class BlockDirector {

    ConcreteBlockBuilder blockBuilder;
    MinerBuilder minerBuilder;
    UserBuilder userBulder;
    BlockDirector(){

        blockBuilder = new ConcreteBlockBuilder();
        minerBuilder = new MinerBuilder();
        userBulder = new UserBuilder();
    }

    public Block makeBlock(Block previousBlock, int nrOfZero, Blockchain blockchain){
        LocalTime start = LocalTime.now();
        blockBuilder.reset();
        blockBuilder.buildTimeStamp();
        blockBuilder.buildListOfTransactions(blockchain.transactionList);
        blockchain.resetTransactionList();
        blockBuilder.buildPreviousBlock(previousBlock);
        blockBuilder.buildID(previousBlock);

        blockBuilder.buildHash(nrOfZero);
        LocalTime end = LocalTime.now();
        blockBuilder.setTimeNeededToCreate(end.toSecondOfDay()-start.toSecondOfDay());
        blockBuilder.buildDifficulty(nrOfZero);
        return blockBuilder.getBlock();

    }

    public Miner makeMiner(Blockchain blockchain){
        minerBuilder.reset();
        minerBuilder.setID();
        minerBuilder.setBlockchain();
        minerBuilder.setNotify(blockchain);
        return minerBuilder.getMiner();
    }

    public User makeUser() throws NoSuchAlgorithmException, NoSuchProviderException {
        userBulder.reset();
        userBulder.setID();
        userBulder.setBlockchain();
        userBulder.setKeys();
        return userBulder.getUser();
    }

}
