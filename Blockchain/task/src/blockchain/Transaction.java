package blockchain;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.Signature;

public class Transaction {
    public int userID;
    public int from;
    public int to;
    public int amount;
    int id;
    String message;
    byte[] signature;

    Transaction(String s, User user) {
        message = s;
        this.userID = user.ID;


    }

    Transaction(int from, int to, int signerID, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.userID = signerID;
    }

    public void sign(PrivateKey keys) throws Exception {
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(keys);
        rsa.update(this.toString().getBytes());

        signature = rsa.sign();
    }

    public String toString() {
        return "" + from + to + amount;
    }


}
