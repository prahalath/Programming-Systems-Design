// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CSCI 455 PA5
// Fall 2020

// Table.cpp  Table class implementation


#include "Table.h"

#include <iostream>
#include <string>
#include <cassert>

using namespace std;


// listFuncs.h has the definition of Node and its methods.  -- when
// you complete it it will also have the function prototypes for your
// list functions.  With this #include, you can use Node type (and
// Node*, and ListType), and call those list functions from inside
// your Table methods, below.

#include "listFuncs.h"


//*************************************************************************

// representation invariant: The scores and names in the Table object are always stored in 
// an array of type Node *. Each array entry is NULL if no name is present in the 
// corresponding array entry. 

Table::Table() {
   hashTable = new ListType[HASH_SIZE]();
   hashSize = HASH_SIZE;
}


Table::Table(unsigned int hSize) {
   hashTable = new ListType[hSize]();
   hashSize = hSize;
}


int * Table::lookup(const string &key) { 
   return listLookUp(hashTable[hashCode(key)], key);
}

bool Table::remove(const string &key) {
   return listRemove(hashTable[hashCode(key)], key);
}

bool Table::insert(const string &key, int value) {
   return listInsert(hashTable[hashCode(key)], key, value);
}

int Table::numEntries() const {
   int count = 0;
   for (int i = 0; i < hashSize; i++){
      count += numListEntries(hashTable[i]);
   }
   return count;
}

void Table::printAll() const {
   for (int i = 0; i < hashSize; i++){
      printListEntries(hashTable[i]);
   }

}

void Table::hashStats(ostream &out) const {
   int entries = 0;
   int nonEmptyBuckets = 0;
   int longestChain = 0;
   int currLength = 0;

   for (int i = 0; i < hashSize; i++){
      if (hashTable[i] != NULL){
         nonEmptyBuckets += 1;

         currLength = numListEntries(hashTable[i]);
         entries += currLength;
         if (currLength > longestChain){
            longestChain = currLength;
         }
      }
      
   }

   out << "number of buckets: " << hashSize << endl;
   out << "number of entries: " << entries << endl;
   out << "number of non-empty buckets: " << nonEmptyBuckets << endl;
   out << "longest chain: " << longestChain << endl;
  
}


// add definitions for your private methods here
