Hyperskill Project: Blockchain

Blockchain has a simple interpretation: it's just a chain of blocks. It represents a sequence of data that you can't break in the middle; you can only append new data at the end of it. All the blocks in the blockchain are chained together.

- Stage 1: In the first stage, you need to implement such a blockchain. In addition to storing the hash of the previous block, every block should also have a unique identifier. The chain starts with a block whose id = 1. Also, every block should contain a timestamp representing the time the block was created. You can use the following code to get such a timestamp. This represents the number of milliseconds since 1 January 1970.
-  Stage 2: Implement proof of work. In this stage, you need to improve the blockchain. It should generate new blocks only with hashes that start with N zeros. The number N should be input from the keyboard.