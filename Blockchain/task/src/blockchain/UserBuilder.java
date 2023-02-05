package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class UserBuilder {

    private User user;
    int idCounter = 1;

    public UserBuilder() {
        this.reset();
    }

    public void setBlockchain() {
        user.blockchain = Blockchain.getInstance();
        user.blockchain.userBC.put(user.ID,user);

    }



    public void setID() {
        user.ID = idCounter;
        idCounter++;
    }

    public void setKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
        PublicPrivateKeys keys = new PublicPrivateKeys(1024);
        keys.createKeys();
        user.keys = keys;
    }



    public void reset() {
        user = new User();
    }

    public User getUser() {
        return user;
    }
}
