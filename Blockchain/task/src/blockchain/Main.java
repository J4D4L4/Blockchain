package blockchain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Scanner scanner = new Scanner(System.in);
        Blockchain blockchain = Blockchain.getInstance();
        List<Miner> minerList = new ArrayList<>();
        //Miner miner = blockchain.director.makeMiner(blockchain);
        int amountOfBlocksToCreate = 5;
        int minersToCreate = 5;
        List<Block> createdBlocks = new ArrayList<>();
        //ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //System.out.print("Enter how many zeros the hash must start with: ");
        //int nrOfZeroes = scanner.nextInt();
/*
        for (int miners =0; miners<minersToCreate ; miners++){
            Miner miner = blockchain.director.makeMiner(blockchain);
            minerList.add(miner);
            executor.submit(miner);
        }*/

        while (blockchain.listOfBlocks.size()<amountOfBlocksToCreate) {
            Miner miner = blockchain.director.makeMiner(blockchain);
            minerList.add(miner);
            executor.submit(miner);
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);

            }



        print(blockchain.listOfBlocks);
        //System.out.println(blockchain.validateChain());

        }


        /*
        for(int blockNr = 0; blockNr < amountOfBlocksToCreate; blockNr++) {
            miner.createBlock();

        }
        */





    public static void print(LinkedList<Block> blockList){

        for (Block block : blockList){

            block.printBlock();
        }
    }



}
