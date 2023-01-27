package blockchain;

public interface BlockBuilder {

    public void buildTimeStamp();
    public void buildListOfTransactions();
    public void buildHashOfPreviousBlock();
    public Block getBlock();
    public void buildHash();

}
