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
        List<User> usersList = new ArrayList<>();



        int amountOfBlocksToCreate = 15;
        int minersToCreate = 5;
        int usersToCreate = 4;
        List<Block> createdBlocks = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int amtMiner = 0; amtMiner < minersToCreate; amtMiner++){
            minerList.add(blockchain.director.makeMiner(blockchain));
        }
        for (int amtUser = 0; amtUser < usersToCreate; amtUser++){
            usersList.add(blockchain.director.makeUser());
        }

        //System.out.print("Enter how many zeros the hash must start with: ");
        //int nrOfZeroes = scanner.nextInt();


        while (blockchain.listOfBlocks.size()<amountOfBlocksToCreate) {
            //User user = blockchain.director.makeUser();
            Random rand = new Random();
            int randomID = rand.nextInt(usersList.size()-1);
            executor.submit(usersList.get(randomID));
            //Miner miner = blockchain.director.makeMiner(blockchain);
            //Miner miner1 = blockchain.director.makeMiner(blockchain);

            randomID = rand.nextInt(minerList.size()-1);
            executor.submit(minerList.get(randomID));
            //executor.submit(miner);
            executor.awaitTermination(200, TimeUnit.MILLISECONDS);

        }

        //executor.shutdown();

        print(blockchain.listOfBlocks);
        executor.shutdown();
        blockchain = new Blockchain();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        System.exit(0);


    }


    public static void print(LinkedList<Block> blockList){

        /*for (Block block : blockList){

            block.printBlock();

        for (Iterator<Block> iterator = blockList.iterator(); iterator.hasNext();) {
            Block block = iterator.next();
            block.printBlock();
        } }*/

        for(int blockNr = 0; blockNr < blockList.size(); blockNr++) {

            Block block = blockList.get(blockNr);
            block.printBlock();
        }
    }



}