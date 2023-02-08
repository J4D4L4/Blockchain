package blockchain;

import blockchain.Block;
import blockchain.ConcreteBlockBuilder;
import blockchain.HashUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteBlockBuilderTest {
    ConcreteBlockBuilder builder = new ConcreteBlockBuilder();
    @Test
    void buildTimeStamp() {
        Block block = builder.getBlock();
        long timeStamp = new Date().getTime();
        boolean assertionB = (timeStamp>block.getTimestamp());
        Assertions.assertTrue(assertionB);
    }

    @Test
    void buildListOfTransactions() {
        Block block = builder.getBlock();
        Assertions.assertEquals(0, block.getTransactionList().size());
    }

    @Test
    void buildHashOfPreviousBlock() {
    }

    @Test
    void getBlock() {
    }

    @Test
    void buildHash() {
        builder.buildHash(1);
        Block block = builder.getBlock();

        Assertions.assertEquals(block.getHashBlock(), HashUtil.applySha256(block.toString()));
    }

    @Test
    void reset() {

    }

    @Test
    void buildID() {
    }

    @Test
    void buildPreviousBlock() {
    }
}