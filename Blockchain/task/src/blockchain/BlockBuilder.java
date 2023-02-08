package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

public interface BlockBuilder {

    void buildTimeStamp();

    void buildListOfTransactions(List<Transaction> transactionList, Transaction minerReward) throws Exception;

    void buildHashOfPreviousBlock(Block previousBlock);

    Block getBlock();

    void buildHash(int numberOfZero);

    void buildID(Block previousBlock);

    void buildPreviousBlock(Block previousBlock);

    void setTimeNeededToCreate(int time);

    void buildBlockchain() throws NoSuchAlgorithmException, NoSuchProviderException;


}
