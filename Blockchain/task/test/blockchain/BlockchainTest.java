package blockchain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockchainTest {
    Blockchain blockchain = new Blockchain();

    @Test
    void addBlock() {

        blockchain.addBlock();
        Assertions.assertEquals(1,blockchain.listOfBlocks.size());
    }

    @Test
    void validateChain() {

        assertEquals(true,blockchain.validateChain());
    }

    @Test
    void validatePreviousBlock() {
        blockchain.addBlock();
        blockchain.addBlock();
        blockchain.addBlock();
        boolean validateTwo = blockchain.validatePreviousBlock(blockchain.listOfBlocks.get(2),blockchain.listOfBlocks.get(1));
        assertEquals(true,validateTwo);
    }
}