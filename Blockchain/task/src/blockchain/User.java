package blockchain;

import java.util.concurrent.TimeUnit;

public class User extends Entity implements Runnable{
    public Blockchain blockchain;
    public int ID;
    //PublicPrivateKeys keys;
    User(){

    }

    public Transaction createTransaction(){
        Transaction transaction = new Transaction(this.ID, blockchain.entityBC.keySet().stream().findAny().get(),this.ID,100);

        transaction.id = blockchain.getTransactionCounter();
        try {
            transaction.sign(this.keys.getPrivateKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        blockchain.addTransaction(transaction);
        return transaction;
    }
    @Override
    public void run() {

            for(int run = 0; run<5; run++) {
                if (blockchain.getBalance(this.ID)>0) {
                    createTransaction();
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

    }
}
