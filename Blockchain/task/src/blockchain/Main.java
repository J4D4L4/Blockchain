package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException, NoSuchAlgorithmException, NoSuchProviderException {
        java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(Main.class.getName());
        Scanner scanner = new Scanner(System.in);
        Blockchain blockchain = Blockchain.getInstance();
        List<Miner> minerList = new ArrayList<>();



        int amountOfBlocksToCreate = 5;
        int minersToCreate = 5;
        List<Block> createdBlocks = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int amtMiner = 0; amtMiner < minersToCreate; amtMiner++){
            minerList.add(blockchain.director.makeMiner(blockchain));
        }

        //System.out.print("Enter how many zeros the hash must start with: ");
        //int nrOfZeroes = scanner.nextInt();


        while (blockchain.listOfBlocks.size()<amountOfBlocksToCreate) {
            User user = blockchain.director.makeUser();
            executor.submit(user);
            //Miner miner = blockchain.director.makeMiner(blockchain);
            //Miner miner1 = blockchain.director.makeMiner(blockchain);
            Random rand = new Random();
            int randomID = rand.nextInt(minerList.size()-1);
            executor.submit(minerList.get(randomID));
            //executor.submit(miner);
            //executor.awaitTermination(200, TimeUnit.MILLISECONDS);

        }

        //executor.shutdown();

        print(blockchain.listOfBlocks);
        executor.shutdown();
        blockchain = new Blockchain();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        System.exit(0);


    }


    public static void print(LinkedList<Block> blockList){

        for (Block block : blockList){

            block.printBlock();
        }
    }



}