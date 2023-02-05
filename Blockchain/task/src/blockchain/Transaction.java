package blockchain;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.Signature;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class Transaction {
    int id;
    String message;
    byte[] signature;
    int userID;
    Transaction(String s, User user){
        message = s;
        this.userID = user.ID;

    }

    public void sign(PrivateKey keys) throws InvalidKeyException, Exception{
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(keys);
        rsa.update(this.toString().getBytes());

        signature = rsa.sign();
    }

    public String toString(){
        return message+signature;
    }


}
