package blockchain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Blockchain blockchain;

    @BeforeEach
    public void setUp() throws Exception {
        blockchain = Blockchain.getBlockchainInstance();
        Miner testMiner =blockchain.director.makeMiner(blockchain);
        user = blockchain.director.makeUser();
        testMiner.createBlock();
        testMiner.createBlock();
        testMiner.createBlock();
        blockchain.listOfBlocks.get(1).transactionList.add(new Transaction(0,1,0,100));
        blockchain.listOfBlocks.get(1).transactionList.add(new Transaction(0,2,0,100));
        blockchain.listOfBlocks.get(1).transactionList.add(new Transaction(0,3,0,100));



    }

    @Test
    void testCreateTransaction() {
        Transaction transaction = user.createTransaction();
        assertNotNull(transaction);
        assertEquals(3, transaction.from);
        assertEquals(100, transaction.amount);
        assertNotNull(transaction.signature);
        assertTrue(blockchain.transactionList.contains(transaction));
    }

    @Test
    void testRun() throws InterruptedException {
        int transactionsBefore = blockchain.listOfBlocks.get(blockchain.getListOfBlocks().size()-1).transactionList.size();
        user.run();
        TimeUnit.MILLISECONDS.sleep(2000);
        int transactionsAfter = blockchain.getListOfBlocks().size();
        assertTrue(transactionsAfter > transactionsBefore);
    }
}