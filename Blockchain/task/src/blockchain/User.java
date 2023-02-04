package blockchain;

import java.util.concurrent.TimeUnit;

public class User implements Runnable{
    public Blockchain blockchain;
    public int ID;
    User(){

    }

    public Transaction createTransaction(){
        Transaction transaction = new Transaction(ID+" imessage 1");
        blockchain.addTransaction(transaction);
        return transaction;
    }
    @Override
    public void run() {

            for(int run = 0; run<5; run++) {
                Transaction retunrTransaction = createTransaction();
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }





    }
}
