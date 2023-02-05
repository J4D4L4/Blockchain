package blockchain;

import java.util.concurrent.TimeUnit;

public class User extends Entity implements Runnable{
    public Blockchain blockchain;
    public int ID;
    PublicPrivateKeys keys;
    User(){

    }

    public Transaction createTransaction(){
        Transaction transaction = new Transaction(ID+" imessage 1",this);
        blockchain.addTransaction(transaction);
        return transaction;
    }
    @Override
    public void run() {

            for(int run = 0; run<5; run++) {
                Transaction retunrTransaction = createTransaction();
                retunrTransaction.id = blockchain.getTransactionCounter();
                try {
                    retunrTransaction.sign(keys.getPrivateKey());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }





    }
}
