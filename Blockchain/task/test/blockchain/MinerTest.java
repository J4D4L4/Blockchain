package blockchain;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinerTest {

    private Blockchain blockchain = Blockchain.getBlockchainInstance();
    private Miner miner = blockchain.director.makeMiner(blockchain);

    public MinerTest() throws NoSuchAlgorithmException, NoSuchProviderException {
    }

    @Before
    public void setUp() throws NoSuchAlgorithmException, NoSuchProviderException {
        blockchain = Blockchain.getBlockchainInstance();
        miner = blockchain.director.makeMiner(blockchain);
        miner.blockchain = blockchain;
    }

    @Test
    public void testCreateBlock() throws Exception {
        Block block = miner.createBlock();
        Assertions.assertNotNull(block);
        assertEquals(miner.ID, Integer.parseInt(block.createdByMiner));
    }

    @Test
    public void testCreateMinerReward() throws Exception {
        Transaction transaction = miner.createMinerReward();
        Assertions.assertNotNull(transaction);
        Assertions.assertTrue(blockchain.verifySignature(transaction,miner));
        Assertions.assertEquals(-1, transaction.from);
        Assertions.assertEquals(miner.ID, transaction.to);
        assertEquals(miner.ID, transaction.userID);
        assertEquals(100, transaction.amount);
    }

    @Test
    public void testAddedMinerReward() throws Exception {
        miner.createBlock();
        miner.createBlock();
        int moneyAmt = blockchain.getBalance(miner.ID);
        Assertions.assertTrue(moneyAmt>0);

    }







    @Test
    public void testCreateTransaction() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Transaction transaction = miner.createTransaction();
        assertNotNull(transaction);
    }
}
