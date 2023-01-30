package blockchain;

import java.time.LocalTime;

public class BlockDirector {

    ConcreteBlockBuilder blockBuilder;
    MinerBuilder minerBuilder;

    BlockDirector(){

        blockBuilder = new ConcreteBlockBuilder();
        minerBuilder = new MinerBuilder();
    }

    public Block makeBlock(Block previousBlock, int nrOfZero){
        LocalTime start = LocalTime.now();
        blockBuilder.reset();
        blockBuilder.buildTimeStamp();
        blockBuilder.buildListOfTransactions();
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

}
