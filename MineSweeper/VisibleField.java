// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CS 455 PA3
// Fall 2020


/**
  VisibleField class
  This is the data that's being displayed at any one point in the game (i.e., visible field, because it's what the
  user can see about the minefield), Client can call getStatus(row, col) for any square.
  It actually has data about the whole current state of the game, including  
  the underlying mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), isGameOver().
  It also has mutators related to actions the player could do (resetGameDisplay(), cycleGuess(), uncover()),
  and changes the game state accordingly.
  
  It, along with the MineField (accessible in mineField instance variable), forms
  the Model for the game application, whereas GameBoardPanel is the View and Controller, in the MVC design pattern.
  It contains the MineField that it's partially displaying.  That MineField can be accessed (or modified) from 
  outside this class via the getMineField accessor.  
 */
public class VisibleField {
   // ----------------------------------------------------------   
   // The following public constants (plus numbers mentioned in comments below) are the possible states of one
   // location (a "square") in the visible field (all are values that can be returned by public method 
   // getStatus(row, col)).
   
   // Covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // Uncovered states (all non-negative values):
   
   // values in the range [0,8] corresponds to number of mines adjacent to this square
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
   // ----------------------------------------------------------   
  
   // <put instance variables here>
   private MineField visibleMineField;
   private int[][] squares;
   private int numGuesses;
   private static final int ZERO = 0; // variable to represent the integer 0
   

   /**
      Create a visible field that has the given underlying mineField.
      The initial state will have all the mines covered up, no mines guessed, and the game
      not over.
      @param mineField  the minefield to use for for this VisibleField
    */
   public VisibleField(MineField mineField) {
      visibleMineField = mineField;
      squares = new int[visibleMineField.numRows()][visibleMineField.numCols()];
      resetGameDisplay();
   }
   
   
   /**
      Reset the object to its initial state (see constructor comments), using the same underlying
      MineField. 
   */     
   public void resetGameDisplay() {
      for (int i = 0; i < squares.length; i++){
         for (int j = 0; j < squares[0].length; j++){
            squares[i][j] = COVERED;
         }
      }
      numGuesses = 0;
   }
  
   
   /**
      Returns a reference to the mineField that this VisibleField "covers"
      @return the minefield
    */
   public MineField getMineField() {

      return visibleMineField;       // DUMMY CODE so skeleton compiles
   }
   
   
   /**
      Returns the visible status of the square indicated.
      @param row  row of the square
      @param col  col of the square
      @return the status of the square at location (row, col).  See the public constants at the beginning of the class
      for the possible values that may be returned, and their meanings.
      PRE: getMineField().inRange(row, col)
    */
   public int getStatus(int row, int col) {

      return squares[row][col];       // DUMMY CODE so skeleton compiles
   }

   
   /**
      Returns the the number of mines left to guess.  This has nothing to do with whether the mines guessed are correct
      or not.  Just gives the user an indication of how many more mines the user might want to guess.  This value can
      be negative, if they have guessed more than the number of mines in the minefield.     
      @return the number of mines left to guess.
    */
   public int numMinesLeft() {
      return visibleMineField.numMines() - numGuesses;       // DUMMY CODE so skeleton compiles

   }
 
   
   /**
      Cycles through covered states for a square, updating number of guesses as necessary.  Call on a COVERED square
      changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to QUESTION;  call on a QUESTION square
      changes it to COVERED again; call on an uncovered square has no effect.  
      @param row  row of the square
      @param col  col of the square
      PRE: getMineField().inRange(row, col)
    */
   public void cycleGuess(int row, int col) {
      if (getStatus(row, col) == COVERED){
         squares[row][col] = MINE_GUESS;
         numGuesses += 1;
      }
      else if (getStatus(row, col) == MINE_GUESS){
         squares[row][col] = QUESTION;
         numGuesses -= 1;
      }
      else if (getStatus(row, col) == QUESTION){
         squares[row][col] = COVERED;
      }
      
   }

   
   /**
      Uncovers this square and returns false iff you uncover a mine here.
      If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in 
      the neighboring area that are also not next to any mines, possibly uncovering a large region.
      Any mine-adjacent squares you reach will also be uncovered, and form 
      (possibly along with parts of the edge of the whole field) the boundary of this region.
      Does not uncover, or keep searching through, squares that have the status MINE_GUESS. 
      Note: this action may cause the game to end: either in a win (opened all the non-mine squares)
      or a loss (opened a mine).
      @param row  of the square
      @param col  of the square
      @return false   iff you uncover a mine at (row, col)
      PRE: getMineField().inRange(row, col)
    */
   public boolean uncover(int row, int col) {

      if (visibleMineField.hasMine(row, col)){
         squares[row][col] = EXPLODED_MINE;
         return false;
      }
      else{
         uncoverHelper(row, col); // call recursive helper function to uncover more squares with given constraints
         return true;
      }
   }
 
   
   /**
      Returns whether the game is over.
      (Note: This is not a mutator.)
      @return whether game over
    */
   public boolean isGameOver() {
      int noMineLocations = (visibleMineField.numRows() * visibleMineField.numCols()) - visibleMineField.numMines();
      for (int row = 0; row < visibleMineField.numRows(); row++){
         for (int col = 0; col < visibleMineField.numCols(); col++){
            if (squares[row][col] == EXPLODED_MINE){
               updateLosingSquares(); // call method to update window with losing characteristics
               return true;
            }
            else if (isUncovered(row, col)){
               noMineLocations -= 1; // decrement the number of no mines locations each time a no mine location is uncovered
            }
         }
      }
      if (noMineLocations == ZERO){  // if all the no mine locations are uncovered the game has been won
         updateWinningSquares(); // call method to update window with winning characteristics
         return true;
      }
      return false;
   }

   /**
      Returns whether this square has been uncovered.  (i.e., is in any one of the uncovered states, 
      vs. any one of the covered states).
      @param row of the square
      @param col of the square
      @return whether the square is uncovered
      PRE: getMineField().inRange(row, col)
    */
   public boolean isUncovered(int row, int col) {
      if (squares[row][col] > COVERED){
         return true;
      }
      return false;       // DUMMY CODE so skeleton compiles
   }
   
 
   // <put private methods here>

   /**
    * Recursively opens squares that are not near adjacent mines and assigns number of adjacent mine values.
    * Returns without doing anything if the (row, col) value is out of range or the status is MINE_GUESS or is already opened.
    * Also opens square with adjacent mines (but does not call recursive function on surrounding squares) which form the
    * boundary of the area opened by this recursive call.
    * @param row
    * @param col
    */
   private void uncoverHelper(int row, int col){
      if (!(visibleMineField.inRange(row, col)) || squares[row][col] == MINE_GUESS || isUncovered(row, col)){
         return;
      }
      else if (visibleMineField.numAdjacentMines(row, col) > 0){
         squares[row][col] = visibleMineField.numAdjacentMines(row, col);
         return;
      }
      else{
         squares[row][col] = visibleMineField.numAdjacentMines(row, col);
         uncoverHelper(row-1, col-1);
         uncoverHelper(row-1, col);
         uncoverHelper(row, col+1);
         uncoverHelper(row-1, col+1);
         uncoverHelper(row+1, col-1);
         uncoverHelper(row+1, col);
         uncoverHelper(row, col-1);
         uncoverHelper(row+1, col+1);
      }

   }

   /**
    * Updates squares after the game has been lost. Squares having MINE_GUESS but don't contain mines are marked
    * with INCORRECT_GUESS, and squares with mines but not marked as MINE_GUESS are marked as MINE.
    */
   private void updateLosingSquares(){
      for (int row = 0; row < visibleMineField.numRows(); row++){
         for (int col = 0; col < visibleMineField.numCols(); col++){
            if (!visibleMineField.hasMine(row, col) && squares[row][col] == MINE_GUESS){
               squares[row][col] = INCORRECT_GUESS;
            }
            else if(visibleMineField.hasMine(row, col) && squares[row][col] != MINE_GUESS){
               squares[row][col] = MINE;
            }
         }
      }
   }

   /**
    * Updates squares after the game has been won. Squares having mines are marked as MINE_GUESS.
    */
   private void updateWinningSquares(){
      for (int row = 0; row < visibleMineField.numRows(); row++){
         for (int col = 0; col < visibleMineField.numCols(); col++){
            if (!isUncovered(row, col) && visibleMineField.hasMine(row, col)){
               squares[row][col] = MINE_GUESS;
            }
         }
      }
   }
   
}
