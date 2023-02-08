package blockchain;

import java.io.Serializable;

public class SerializerHelper implements Serializable {


    // TO Serialize Objects
    Blockchain blockchain;

//write read functions

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

}
