package blockchain;

import java.time.LocalTime;

public class BlockDirector {

    ConcreteBlockBuilder blockBuilder;

    BlockDirector(){
        blockBuilder = new ConcreteBlockBuilder();
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
        return blockBuilder.getBlock();

    }

}
