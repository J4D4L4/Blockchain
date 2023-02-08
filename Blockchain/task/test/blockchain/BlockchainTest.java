package blockchain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static org.junit.jupiter.api.Assertions.*;

class BlockchainTest {
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

    BlockchainTest() throws NoSuchAlgorithmException, NoSuchProviderException {
    }

    @BeforeAll
    static void init() throws Exception {
        testMiner.createBlock();
        testMiner.createBlock();

    }

    @Test
    void addBlock() throws Exception {


        Assertions.assertEquals(2,blockchain.listOfBlocks.size());
    }

    @Test
    void validateChain() {

        assertEquals(true,blockchain.validateChain());
    }



    @Test
    void validateGetBalance(){
        Assertions.assertEquals(200, blockchain.getBalance(0));

    }



        @Test
        public void testGetBlockchainInstance() throws NoSuchAlgorithmException, NoSuchProviderException {

            assertNotNull(blockchain);
            Blockchain blockchain2 = Blockchain.getBlockchainInstance();
            assertEquals(blockchain, blockchain2);
        }

        @Test
        public void testAddBlock() throws Exception {

            Block block = testMiner.createBlock();
            assertEquals(blockchain.headBlock, block);
        }

        @Test
        public void testAddTransaction() throws NoSuchAlgorithmException, NoSuchProviderException {

            Transaction transaction = new Transaction(-1, 0, 0, 100);
            blockchain.addTransaction(transaction);
            assertEquals(1, blockchain.transactionList.size());
        }

        @Test
        public void testResetTransactionList() throws NoSuchAlgorithmException, NoSuchProviderException {

            Transaction transaction = new Transaction(-1, 0, 0, 100);
            blockchain.addTransaction(transaction);
            blockchain.resetTransactionList();
            assertEquals(0, blockchain.transactionList.size());
        }

}