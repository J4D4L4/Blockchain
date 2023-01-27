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
    public void buildHashOfPreviousBlock(Block previousBlock) {
        if(previousBlock != null) {
            block.setHashPreviousBlock(previousBlock.getHashBlock());
        }
        else {
            block.setHashPreviousBlock("0");
        }

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

    public void buildID(Block previousBlock) {

        if(previousBlock != null) {
            block.setUniqueID(""+Integer.parseInt(previousBlock.getUniqueID()+1));
        }
        else {
            block.setUniqueID(""+1);
        }

        ;


    }

    @Override
    public void buildPreviousBlock(Block previousBlock) {
        if(previousBlock == null){
            block.setHashPreviousBlock("0");
        }
        else {
            block.setHashPreviousBlock(HashUtil.applySha256(previousBlock.toString()));
        }

        block.setPreviousBlock(previousBlock);
    }
}
