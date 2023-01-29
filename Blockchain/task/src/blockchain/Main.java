package blockchain;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Blockchain blockchain = new Blockchain();
        int amountOfBlocksToCreate = 5;
        System.out.print("Enter how many zeros the hash must start with: ");
        int nrOfZeroes = scanner.nextInt();


        for(int blockNr = 0; blockNr < amountOfBlocksToCreate; blockNr++) {
            blockchain.addBlock(nrOfZeroes);
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
