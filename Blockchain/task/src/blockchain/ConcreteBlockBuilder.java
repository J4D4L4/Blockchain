package blockchain;

import java.util.Date;
import java.util.LinkedList;

public class ConcreteBlockBuilder implements BlockBuilder {

    private Block block;

    ConcreteBlockBuilder(){
        block = new Block();
    }

    @Override
    public void buildTimeStamp() {
        block.setTimestamp(new Date().getTime());
    }

    @Override
    public void buildListOfTransactions() {


    }

    @Override
    public void buildHashOfPreviousBlock() {

    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public void buildHash() {
        block.setHashBlock(HashUtil.applySha256(block.toString()));
    }

    public void reset(){
        block = new Block();
    }
}
