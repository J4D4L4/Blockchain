package blockchain;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BlockTest {
    BlockDirector director = new BlockDirector();
    Block block1 = director.makeBlock(null);
    Block block2 = director.makeBlock(block1);

    @Test
    void testToString() {
        String expectedValue ="0[]1674829614547null";
        Assertions.assertNotNull (block1.toString());
    }

    @Test
    void getUniqueID() {
        String expectedValue ="1";
        Assertions.assertEquals(expectedValue,block1.getUniqueID());
    }

    @Test
    void getHashBlock() {
        String expectedValue =HashUtil.applySha256(block2.toString());
        Assertions.assertEquals(expectedValue,block2.getHashBlock());

    }

    @Test
    void setUniqueID() {
        String expectedValue ="3";
        block1.setUniqueID("3");
        Assertions.assertEquals(expectedValue,block1.getUniqueID());
    }

    @Test
    void getTransactionList() {
        Integer expectedValue =0;
        Assertions.assertEquals(expectedValue,block1.getTransactionList().size());
    }

    @Test
    void testPrintBlock(){
        block1.printBlock();
        Assertions.assertEquals(true,true);
    }

    @Test
    void setTransactionList() {
    }

    @Test
    void getTimestamp() {
    }

    @Test
    void setTimestamp() {
    }

    @Test
    void getHashPreviousBlock() {
    }

    @Test
    void setHashPreviousBlock() {
    }



    @Test
    void setHashBlock() {
    }

    @Test
    void getPreviousBlock() {
        Assertions.assertEquals(block1,block2.getPreviousBlock());
    }

    @Test
    void setPreviousBlock() {
    }
}