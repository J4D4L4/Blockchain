package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalTime;

public class BlockDirector {

    ConcreteBlockBuilder blockBuilder;
    MinerBuilder minerBuilder;
    UserBuilder userBulder;
    PublicPrivateKeys keys;
    int entityIdCounter;
    BlockDirector() throws NoSuchAlgorithmException, NoSuchProviderException {

        blockBuilder = new ConcreteBlockBuilder();
        minerBuilder = new MinerBuilder();
        userBulder = new UserBuilder();
        keys = HashUtil.createKeys();
        entityIdCounter = 0;
    }

    public Block makeBlock(Block previousBlock, int nrOfZero, Blockchain blockchain, Miner miner,Transaction minerReward) throws Exception {
        LocalTime start = LocalTime.now();
        blockBuilder.reset();
        blockBuilder.buildTimeStamp();
        blockBuilder.buildBlockchain();
        blockBuilder.buildListOfTransactions(blockchain.transactionList, minerReward);
        blockchain.resetTransactionList();
        //blockBuilder.addMinerRewarf(miner);
        blockBuilder.buildPreviousBlock(blockchain.headBlock);
        blockBuilder.buildID(blockchain.headBlock);
        blockBuilder.buildHash(nrOfZero);
        LocalTime end = LocalTime.now();
        blockBuilder.setTimeNeededToCreate(end.toSecondOfDay()-start.toSecondOfDay());
        blockBuilder.buildDifficulty(nrOfZero);
        return blockBuilder.getBlock();

    }

    public Miner makeMiner(Blockchain blockchain) throws NoSuchAlgorithmException, NoSuchProviderException {
        minerBuilder.reset();
        minerBuilder.setID(entityIdCounter++);

        minerBuilder.setBlockchain();
        minerBuilder.setNotify(blockchain);
        minerBuilder.setKeys();
        return minerBuilder.getMiner();
    }

    public User makeUser() throws NoSuchAlgorithmException, NoSuchProviderException {
        userBulder.reset();
        userBulder.setID(entityIdCounter++);
        userBulder.setBlockchain();
        userBulder.setKeys();
        return userBulder.getUser();
    }

}
