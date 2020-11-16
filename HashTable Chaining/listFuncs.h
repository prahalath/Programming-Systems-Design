// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CSCI 455 PA5
// Fall 2020


//*************************************************************************
// Node class definition 
// and declarations for functions on ListType

// Note: we don't need Node in Table.h
// because it's used by the Table class; not by any Table client code.

// Note2: it's good practice to not put "using" statement in *header* files.  Thus
// here, things from std libary appear as, for example, std::string

#ifndef LIST_FUNCS_H
#define LIST_FUNCS_H
  

struct Node {
   std::string key;
   int value;

   Node *next;

   Node(const std::string &theKey, int theValue);

   Node(const std::string &theKey, int theValue, Node *n);
};


typedef Node * ListType;

//*************************************************************************
//add function headers (aka, function prototypes) for your functions
//that operate on a list here (i.e., each includes a parameter of type
//ListType or ListType&).  No function definitions go in this file.


// Removes the node containing the given target name as key; also frees the memory of the node.
// Returns true if the target is present in the list, otherwise false. 
bool listRemove(ListType & list, std::string target);

// creates a new node at the end of the list with the given key and value if the key
// isn't already present, otherwise returns false
bool listInsert(ListType & list, std::string key, int value);

// function to lookup a key (name) in the given chain and return a pointer
// the corresponding score. Returns null if the name is not present. 
int* listLookUp(ListType list, std::string target);


// Returns the number of nodes in the given chain. 
int numListEntries(ListType list);


// Prints all the keys and corresponding values from a given list.  
void printListEntries(ListType list);












// keep the following line at the end of the file
#endif
