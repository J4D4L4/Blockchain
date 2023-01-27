package blockchain;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args){

        Blockchain blockchain = new Blockchain();
        int amountOfBlocksToCreate = 5;
        for(int blockNr = 0; blockNr < amountOfBlocksToCreate; blockNr++) {
            blockchain.addBlock();
        }

        print(blockchain.listOfBlocks);

        System.out.println(blockchain.validateChain());
    }

    public static void print(LinkedList<Block> blockList){

        for (Block block : blockList){

            block.printBlock();
        }
    }



}
