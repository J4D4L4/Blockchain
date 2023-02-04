package blockchain;

public class UserBuilder {

    private User user;
    int idCounter = 1;

    public UserBuilder() {
        this.reset();
    }

    public void setBlockchain() {
        user.blockchain = Blockchain.getInstance();

    }



    public void setID() {
        user.ID = idCounter;
        idCounter++;
    }



    public void reset() {
        user = new User();
    }

    public User getUser() {
        return user;
    }
}
