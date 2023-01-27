package blockchain;

public class BlockDirector {

    ConcreteBlockBuilder blockBuilder;

    BlockDirector(){
        blockBuilder = new ConcreteBlockBuilder();
    }

    public Block makeBlock(Block previousBlock){

        blockBuilder.reset();
        blockBuilder.buildTimeStamp();
        blockBuilder.buildListOfTransactions();
        blockBuilder.buildPreviousBlock(previousBlock);
        blockBuilder.buildID(previousBlock);
        blockBuilder.buildHash();
        return blockBuilder.getBlock();

    }

}
