package blockchain;

public class TransactionCreator implements Runnable{
    Transaction transaction;
    Blockchain blockchain = Blockchain.getInstance();


    @Override
    public void run() {
        synchronized (this) {
            blockchain.addTransaction(new Transaction("message 1"));
            blockchain.addTransaction(new Transaction("message 2"));
            return;
        }

    }
}
