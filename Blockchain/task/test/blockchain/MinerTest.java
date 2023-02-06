package blockchain;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinerTest {

    private Blockchain blockchain = Blockchain.getInstance();
    private Miner miner = blockchain.director.makeMiner(blockchain);

    public MinerTest() throws NoSuchAlgorithmException, NoSuchProviderException {
    }

    @Before
    public void setUp() throws NoSuchAlgorithmException, NoSuchProviderException {
        blockchain = Blockchain.getInstance();
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
        Assertions.assertEquals(miner.name, transaction.to);
        assertEquals(miner.ID, transaction.userID);
        assertEquals(100, transaction.amount);
    }

    @Test
    public void testAddedMinerReward() throws Exception {
        miner.createBlock();
        miner.createBlock();
        int size = blockchain.listOfBlocks.size();
        int moneyAmt = blockchain.getBalance(miner.ID);
        Assertions.assertTrue(moneyAmt>0);

    }
}
