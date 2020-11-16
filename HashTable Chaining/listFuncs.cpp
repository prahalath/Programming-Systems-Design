// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CSCI 455 PA5
// Fall 2020


#include <iostream>

#include <cassert>

#include "listFuncs.h"

using namespace std;

Node::Node(const string &theKey, int theValue) {
   key = theKey;
   value = theValue;
   next = NULL;
}

Node::Node(const string &theKey, int theValue, Node *n) {
   key = theKey;
   value = theValue;
   next = n;
}




//*************************************************************************
// put the function definitions for your list functions below


bool listInsert(ListType & list, string key, int value){
   ListType p = list;
   if (p == NULL){
      list = new Node(key, value, list);
      return true;
   }

   while (p->next != NULL){ //moves to the last node in the chain
      if (p->key == key){
         return false;
      }
      p = p->next;
   }
   
   if (p->key == key){ // return false if the last node's key matches the entered key
         return false;
   }

   p->next = new Node(key, value);
   return true;
   
}

int* listLookUp(ListType list, string target){ //traverses the list until the end to find the target
   ListType p = list;
   while (p != NULL){
      if (p->key == target){
         return &p->value;
      }
      p = p->next;
   }
   return NULL;
}

bool listRemove(ListType & list, string target){
   ListType p = list;
   if (p == NULL){
      return false;
   }

   if (p->key == target){
      ListType temp = p->next; // stores the reference to the next node before deleting current target node
      delete p;
      list = temp;
      return true;
   }
   ListType prev = p;
   p = p->next;

   while (p != NULL){
      if (p->key == target){
         prev->next = p->next;
         delete p;
         return true;
      }
      else{
         prev = p;
         p = p->next;
      }
   }
   return false;
}

int numListEntries(ListType list){
   int count = 0;
   ListType p = list;
   while (p != NULL){
      count += 1;
      p = p->next;
   }
   return count;
} 


void printListEntries(ListType list){
   ListType p = list;
   while (p != NULL){
      cout << p->key << " " << p->value << endl;
      p = p->next;
   }
}






