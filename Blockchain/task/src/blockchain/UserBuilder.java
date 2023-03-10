package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class UserBuilder {

    int idCounter = 1;
    private User user;

    public UserBuilder() {
        this.reset();
    }

    public void setBlockchain() throws NoSuchAlgorithmException, NoSuchProviderException {
        user.blockchain = Blockchain.getBlockchainInstance();
        user.blockchain.entityBC.put(user.ID, user);

    }


    public void setID(int id) {
        user.ID = id;
        user.name = "user" + user.ID;
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
