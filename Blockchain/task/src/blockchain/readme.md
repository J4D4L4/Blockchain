Hyperskill Project: Blockchain

Blockchain has a simple interpretation: it's just a chain of blocks. It represents a sequence of data that you can't
break in the middle; you can only append new data at the end of it. All the blocks in the blockchain are chained
together.

- Stage 1: In the first stage, you need to implement such a blockchain. In addition to storing the hash of the previous
  block, every block should also have a unique identifier. The chain starts with a block whose id = 1. Also, every block
  should contain a timestamp representing the time the block was created. You can use the following code to get such a
  timestamp. This represents the number of milliseconds since 1 January 1970.
- Stage 2: Implement proof of work. In this stage, you need to improve the blockchain. It should generate new blocks
  only with hashes that start with N zeros. The number N should be input from the keyboard.
- Stage 3: In this stage, you should create a lot of threads with miners, and every one of them should contain the same
  blockchain. The miners should mine new blocks and the blockchain should regulate the number N. The blockchain should
  check the validity of the incoming block (ensure that the previous hash equals the hash of the last block of the
  blockchain and the hash of this new block starts with N zeros). At the start, the number N equals 0 and should be
  increased by 1 / decreased by 1 / stays the same after the creation of the new block based on the time of its
  creation.
    - Used Singleton for Blockchain and Observer for miners. Thread managment of miners with Write Lock on Blockchain
- Stage 4:  In this stage, you need to upgrade the blockchain. A block should contain messages that the blockchain received during the creation of the previous block. When the block was created, all new messages should become a part of the new block, and all the miners should start to search for a magic number for this block. New messages, which were sent after this moment, shouldn't be included in this new block. Don't forget about thread synchronization as there is a lot of shared data.
- Stage 5: In this stage, you need to upgrade the messages. The message should include the text of the message, the signature of this message, a unique identifier (remember to include a unique identifier when creating a signature), and a public key so everyone can check that this message is valid. Don't forget to check every message when checking that the blockchain is valid! The blockchain should reject the messages with identifier less than the maximum identifier in the block in which miners looking for the magic number. Also, when validating the blockchain you should check that every message has an identifier greater than the maximum identifier of the previous block.
  - Implemented Public -Private key for Users