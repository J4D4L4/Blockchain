package blockchain;

import java.util.LinkedList;

public class Blockchain {

    LinkedList<Block> listOfBlocks;
    Block headBlock;
    BlockDirector director;

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


}
