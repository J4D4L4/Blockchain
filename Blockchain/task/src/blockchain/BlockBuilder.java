package blockchain;

public interface BlockBuilder {

    public void buildTimeStamp();
    public void buildListOfTransactions();
    public void buildHashOfPreviousBlock(Block previousBlock);
    public Block getBlock();
    public void buildHash(int numberOfZero);
    public void buildID(Block previousBlock);
    public void buildPreviousBlock(Block previousBlock);
    public void setTimeNeededToCreate(int time);

}
