package blockchain;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

public class Blockchain implements Serializable {
    private static final long serialVersionUID = 1L;
    LinkedList<Block> listOfBlocks;
    Block headBlock;
    transient BlockDirector director;

    Blockchain(){
        this.listOfBlocks = new LinkedList<>();
        this.director = new BlockDirector();
    }

    public void addBlock(){

        if (listOfBlocks.size() == 0) {
            Block newBlock = director.makeBlock(null);
            listOfBlocks.add(newBlock);
            headBlock = newBlock;
        }
        else{

            Block newBlock = director.makeBlock(headBlock);
            listOfBlocks.add(newBlock);
            headBlock = newBlock;
        }

    }

    public boolean validateChain(){

        boolean isValid = true;
        for (int blockNumber = listOfBlocks.size()-1; blockNumber > 0; blockNumber-- ){
            if(!validatePreviousBlock(listOfBlocks.get(blockNumber),listOfBlocks.get(blockNumber-1))){
                isValid = false;
            }
        }
        return isValid;
    }

    boolean validatePreviousBlock(Block currentBlock, Block previousBlock){

        if(currentBlock.getHashPreviousBlock().equals(HashUtil.applySha256(previousBlock.toString()))){
            return true;
        }
        else return false;
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

    public BlockchainState getState(){
        return new BlockchainState(listOfBlocks, headBlock);
    }

    public void setState(BlockchainState state) {
        this.listOfBlocks = new LinkedList<>(state.listOfBlocks);
        this.headBlock = state.headBlock;
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
