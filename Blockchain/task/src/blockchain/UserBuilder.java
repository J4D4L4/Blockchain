package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class UserBuilder {

    private User user;
    int idCounter = 1;

    public UserBuilder() {
        this.reset();
    }

    public void setBlockchain() throws NoSuchAlgorithmException, NoSuchProviderException {
        user.blockchain = Blockchain.getInstance();
        user.blockchain.entityBC.put(user.ID,user);

    }



    public void setID(int id) {
        user.ID = id;
        user.name = "User "+user.ID;
        //idCounter++;
    }

    public void setKeys() throws NoSuchAlgorithmException, NoSuchProviderException {

        user.keys = HashUtil.createKeys();
    }



    public void reset() {
        user = new User();
    }

    public User getUser() {
        return user;
    }
}
