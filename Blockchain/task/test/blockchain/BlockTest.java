package blockchain;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.Instant;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockTest {
    static Blockchain blockchain;

    static {
        try {
            blockchain = Blockchain.getBlockchainInstance();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    static Miner testMiner;

    static {
        try {
            testMiner = blockchain.director.makeMiner(blockchain);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void init() throws Exception {
        testMiner.createBlock();
        testMiner.createBlock();
    }



    BlockTest() throws NoSuchAlgorithmException, NoSuchProviderException {
    }

    //Block block1 = director.makeBlock(null,1,blockchain,testMiner);
    //Block block2 = director.makeBlock(block1,1,blockchain,testMiner);

    @BeforeEach
    void setUp() throws Exception {

        Block block = blockchain.listOfBlocks.get(0);
    }

    @Test
    void getUniqueID() throws Exception {
        Block block = blockchain.listOfBlocks.get(0);
        assertEquals("1", block.getUniqueID());
    }

    @Test
    void getTransactionList() throws Exception {
        Block block = blockchain.listOfBlocks.get(0);
        LinkedList<Transaction> transactionList = new LinkedList<>();
        block.setTransactionList(transactionList);
        assertEquals(transactionList, block.getTransactionList());
    }

    @Test
    void getTimestamp() throws Exception {
        Block block = blockchain.listOfBlocks.get(0);
        long timestamp = Instant.now().toEpochMilli();
        assertTrue(timestamp > block.getTimestamp());
    }

    @Test
    void getHashPreviousBlock() throws Exception {
        Block block = blockchain.listOfBlocks.get(1);
        assertEquals(block.getHashPreviousBlock(), blockchain.listOfBlocks.get(0).getHashBlock());
    }

    @Test
    void getHashBlock() throws Exception {
        Block block = blockchain.listOfBlocks.get(0);
        String hashBlock = "someHashBlock";
        block.setHashBlock(hashBlock);
        assertEquals(hashBlock, block.getHashBlock());
    }

    @Test
    void getPreviousBlock() throws Exception {
        Block block = blockchain.listOfBlocks.get(1);
        assertEquals(block.getPreviousBlock(), blockchain.listOfBlocks.get(0));
    }

    @Test
    void getMagicNumber() throws Exception {
        Block block = blockchain.listOfBlocks.get(1);
        int magicNumber = 42;
        block.setMagicNumber(magicNumber);
        assertEquals(magicNumber, block.getMagicNumber());
    }

    @Test
    void getTimeNeededToCreate() throws Exception {
        Block block = blockchain.listOfBlocks.get(0);
        assertTrue(0 <= block.getTimeNeededToCreate());
    }

    @Test
    void getCreatedByMiner() throws Exception {
        Block block = blockchain.listOfBlocks.get(0);
        String createdByMiner = "0";
        assertEquals(createdByMiner, block.getCreatedByMiner());
    }

    @Test
    void getDiffcultyWhileCreated() throws Exception {
        Block block = blockchain.listOfBlocks.get(0);
        assertTrue(blockchain.difficulty > block.getDiffcultyWhileCreated());
    }
}