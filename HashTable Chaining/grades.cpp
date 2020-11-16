// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CSCI 455 PA5
// Fall 2020

/*
 * grades.cpp
 * A program to test the Table class.
 * How to run it:
 *      grades [hashSize]
 * 
 * the optional argument hashSize is the size of hash table to use.
 * if it's not given, the program uses default size (Table::HASH_SIZE)
 *
 */

#include "Table.h"

// cstdlib needed for call to atoi
#include <cstdlib>

using namespace std;

void getInput(Table * grades);
void insert(Table * grades);
void change(Table * grades);
void lookup(Table * grades);
void remove(Table * grades);
void printHelp();
void checkMoreCommands(Table * grades, string word);

int main(int argc, char * argv[]) {

   // gets the hash table size from the command line

   int hashSize = Table::HASH_SIZE;

   Table * grades;  // Table is dynamically allocated below, so we can call
   // different constructors depending on input from the user.

   if (argc > 1) {
      hashSize = atoi(argv[1]);  // atoi converts c-string to int

      if (hashSize < 1) {
         cout << "Command line argument (hashSize) must be a positive number" 
              << endl;
         return 1;
      }

      grades = new Table(hashSize);

   }
   else {   // no command line args given -- use default table size
      grades = new Table();
   }
   grades->hashStats(cout);

   // add more code here
   // Reminder: use -> when calling Table methods, since grades is type Table*
   getInput(grades);
   return 0;
   }

   // Gets commands from the user and calls corresponding functions for each command
   // @param grades - Table object that will contain students' scores and corresponding functions
   void getInput(Table * grades){
      string word = "";
      while (true){
         cout << "cmd> ";
         cin >> word;
         if (word == "quit"){
            delete grades;
            return;
         }
         else if (word == "insert"){
            insert(grades);
         }
         else if (word == "change"){
            change(grades);
         }
         else if (word == "lookup"){
            lookup(grades);
         }
         else if (word == "remove"){
            remove(grades);
         }
         else{
            checkMoreCommands(grades, word);
         }
   }
}

// Checks additional commands other than the ones above
// @param grades - Table object that will contain students' scores and corresponding functions
// @param word - the word entered by the user
void checkMoreCommands(Table * grades, string word){
   if (word == "print"){
      grades->printAll();
   }
   else if (word == "size"){
      cout << grades->numEntries() << endl;
   }
   else if (word == "stats"){
      grades->hashStats(cout);
   }
   else if (word == "help"){
      printHelp();
   }
   else{
      cout << "ERROR: invalid command" << endl;
   }
}

// Helper function to print available commands when user enters help command
void printHelp(){
   cout << "List of valid commands are:" << endl;
   cout << "[insert, change, lookup, remove, print, size, stats, help, quit]" << endl;
   cout << "[insert] - " << "inserts the name and score in the grade table" << endl;
   cout << "[change] - " << "changes the score for given name" << endl;
   cout << "[lookup] - " << "prints the score of the given student" << endl;
   cout << "[remove] - " << "removes the student from the grade table" << endl;
   cout << "[print] - " << "prints out all names and scores in the table" << endl;
   cout << "[size] - " << "prints the number of entries in the table" << endl;
   cout << "[stats] - " << "prints out statistics about the hash table" << endl;
   cout << "[help] - " << "prints a brief command summary" << endl;
   cout << "[quit] - " << "exits the program" << endl; 
}

// Performs insert operation into the grades table. Prints a message if
// the student's name is not present
// @param grades - The table object that contains students' scores
void insert(Table * grades){
   string key;
   int value;
   cin >> key;
   cin >> value;
   if (!grades->insert(key, (int)value)){
      cout << "This student's name is already present" << endl;
   }
}

// Performs change operation on the grades table. Prints a message if
// the student's name is not present
// @param grades - The table object that contains students' scores
void change(Table * grades){
   string key;
   int value;
   cin >> key;
   cin >> value;
   int* val = grades->lookup(key);
   if (val == NULL){
      cout << "This student's name is not present" << endl;
   }
   else{
      *val = value;
   }
}

// Performs lookup of a student's score and prints it. Prints a message if
// the student's name is not present
// @param grades - The table object that contains students' scores
void lookup(Table * grades){
   string key;
   cin >> key;
   int* val = grades->lookup(key);
   if (val == NULL){
      cout << key << " is not present in the table" << endl;
   }
   else{
      cout << key << "'s score is: " << *val << endl;
   }
}

// Removes a student's name and score from the table. Prints a message
// if the name is not present
// @param grades - The table object that contains students' scores
void remove(Table * grades){
   string key;
   cin >> key;
   if (!grades->remove(key)){
      cout << key << " is not in the grade table" << endl;
   }
}
