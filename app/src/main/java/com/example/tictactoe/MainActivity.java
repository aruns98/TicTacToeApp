package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defining all cell buttons.
        final Button cellZero = findViewById(R.id.cellZeroBtn);
        final Button cellOne = findViewById(R.id.cellOneBtn);
        final Button cellTwo = findViewById(R.id.cellTwoBtn);
        final Button cellThree = findViewById(R.id.cellThreeBtn);
        final Button cellFour = findViewById(R.id.cellFourBtn);
        final Button cellFive = findViewById(R.id.cellFiveBtn);
        final Button cellSix = findViewById(R.id.cellSixBtn);
        final Button cellSeven = findViewById(R.id.cellSevenBtn);
        final Button cellEight = findViewById(R.id.cellEightBtn);

        // Defining all toggle buttons.
        final ToggleButton randomMoverBtn = findViewById(R.id.randomMoverBtn);
        final ToggleButton perfectMoverBtn = findViewById(R.id.perfectMoverBtn);
        final ToggleButton userStartsBtn = findViewById(R.id.userStartsBtn);
        final ToggleButton computerStartsBtn = findViewById(R.id.computerStartsBtn);

        // This toggle button can never be turned off by clicking on it. It can only be turned off
        // by clicking the other toggle button. It is paired with perfectMoverBtn.
        randomMoverBtn.setOnClickListener(v -> {
            if (!randomMoverBtn.isChecked()) {
                randomMoverBtn.setChecked(true);
                modifyToggle(randomMoverBtn,true);
                perfectMoverBtn.setChecked(false);
                modifyToggle(perfectMoverBtn,false);
            }
            else{
                randomMoverBtn.setChecked(true);
                modifyToggle(randomMoverBtn,true);
                perfectMoverBtn.setChecked(false);
                modifyToggle(perfectMoverBtn,false);
            }
        });

        // This toggle button can never be turned off by clicking on it. It can only be turned off
        // by clicking the other toggle button. It is paired with randomMoverBtn.
        perfectMoverBtn.setOnClickListener(v -> {
            if (!perfectMoverBtn.isChecked()) {
                perfectMoverBtn.setChecked(true);
                modifyToggle(perfectMoverBtn,true);
                randomMoverBtn.setChecked(false);
                modifyToggle(randomMoverBtn,false);
            }
            else{
                perfectMoverBtn.setChecked(true);
                modifyToggle(perfectMoverBtn,true);
                randomMoverBtn.setChecked(false);
                modifyToggle(randomMoverBtn,false);
            }
        });

        // This toggle button can never be turned off by clicking on it. It can only be turned off
        // by clicking the other toggle button. It is paired with computerStartsBtn.
        userStartsBtn.setOnClickListener(v -> {
            if (!userStartsBtn.isChecked()) {
                userStartsBtn.setChecked(true);
                modifyToggle(userStartsBtn,true);
                computerStartsBtn.setChecked(false);
                modifyToggle(computerStartsBtn,false);
            }
            else{
                userStartsBtn.setChecked(true);
                modifyToggle(userStartsBtn,true);
                computerStartsBtn.setChecked(false);
                modifyToggle(computerStartsBtn,false);
            }
        });

        // This toggle button can never be turned off by clicking on it. It can only be turned off
        // by clicking the other toggle button. It is paired with userStartsBtn.
        computerStartsBtn.setOnClickListener(v -> {
            if (!computerStartsBtn.isChecked()) {
                computerStartsBtn.setChecked(true);
                modifyToggle(computerStartsBtn,true);
                userStartsBtn.setChecked(false);
                modifyToggle(userStartsBtn,false);
            }
            else{
                computerStartsBtn.setChecked(true);
                modifyToggle(computerStartsBtn,true);
                userStartsBtn.setChecked(false);
                modifyToggle(userStartsBtn,false);
            }
        });

        class Cell{
            String pegPlaced = " ";
            int position;
            int score=0;


            Cell(int pos){
                this.position=pos;
            }

            void putPeg(String peg){
                this.pegPlaced=peg;
            }
        }

        class Board{
            private String userPeg;
            private String computerPeg;
            private boolean gameOver=false;
            private Cell[] allCells = new Cell[9];


            private void initializeCells(){
                for (int i=0;i<9;i++) {
                    allCells[i] = new Cell(i);
                }
            }


            @RequiresApi(api = Build.VERSION_CODES.M)
            private Board(){
                setAllCellsEmpty();
                setAllTextWhite();
                getPegs();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            private void setAllTextWhite(){
                cellZero.setTextColor(getColor(R.color.white));
                cellOne.setTextColor(getColor(R.color.white));
                cellTwo.setTextColor(getColor(R.color.white));
                cellThree.setTextColor(getColor(R.color.white));
                cellFour.setTextColor(getColor(R.color.white));
                cellFive.setTextColor(getColor(R.color.white));
                cellSix.setTextColor(getColor(R.color.white));
                cellSeven.setTextColor(getColor(R.color.white));
                cellEight.setTextColor(getColor(R.color.white));
            }

            private void setAllCellsEmpty(){
                cellZero.setText(" ");
                cellOne.setText(" ");
                cellTwo.setText(" ");
                cellThree.setText(" ");
                cellFour.setText(" ");
                cellFive.setText(" ");
                cellSix.setText(" ");
                cellSeven.setText(" ");
                cellEight.setText(" ");
            }

            private void getPegs(){
                if (userStartsBtn.isChecked()){
                    userPeg = "O";
                    computerPeg = "X";
                }
                else{
                    userPeg = "X";
                    computerPeg = "O";
                }
            }

            // Returns an integer array containing the positions of all vacant cells.
            private int[] getVacantCells(){
                int[] allVacantCells = new int[0];
                String cellZeroPeg = cellZero.getText().toString();
                String cellOnePeg = cellOne.getText().toString();
                String cellTwoPeg = cellTwo.getText().toString();
                String cellThreePeg = cellThree.getText().toString();
                String cellFourPeg = cellFour.getText().toString();
                String cellFivePeg = cellFive.getText().toString();
                String cellSixPeg = cellSix.getText().toString();
                String cellSevenPeg = cellSeven.getText().toString();
                String cellEightPeg = cellEight.getText().toString();

                if (cellZeroPeg.equals(" ")){
                    allVacantCells = appendToArray(allVacantCells,0);
                }

                if (cellOnePeg.equals(" ")){
                    allVacantCells = appendToArray(allVacantCells,1);
                }

                if (cellTwoPeg.equals(" ")){
                    allVacantCells = appendToArray(allVacantCells,2);
                }

                if (cellThreePeg.equals(" ")){
                    allVacantCells = appendToArray(allVacantCells,3);
                }

                if (cellFourPeg.equals(" ")){
                    allVacantCells = appendToArray(allVacantCells,4);
                }

                if (cellFivePeg.equals(" ")){
                    allVacantCells = appendToArray(allVacantCells,5);
                }

                if (cellSixPeg.equals(" ")){
                    allVacantCells = appendToArray(allVacantCells,6);
                }

                if (cellSevenPeg.equals(" ")){
                    allVacantCells = appendToArray(allVacantCells,7);
                }

                if (cellEightPeg.equals(" ")){
                    allVacantCells = appendToArray(allVacantCells,8);
                }

                return(allVacantCells);
            }

            // Placed a peg in the input position.
            private void placePeg(int position, String peg){
                if (position==0){
                    cellZero.setText(peg);
                }

                else if (position==1){
                    cellOne.setText(peg);
                }

                else if (position==2){
                    cellTwo.setText(peg);
                }

                else if (position==3){
                    cellThree.setText(peg);
                }

                else if (position==4){
                    cellFour.setText(peg);
                }

                else if (position==5){
                    cellFive.setText(peg);
                }

                else if (position==6){
                    cellSix.setText(peg);
                }

                else if (position==7){
                    cellSeven.setText(peg);
                }

                else if (position==8){
                    cellEight.setText(peg);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            private void resolveRows() {
                String cellZeroPeg = cellZero.getText().toString();
                String cellOnePeg = cellOne.getText().toString();
                String cellTwoPeg = cellTwo.getText().toString();
                String cellThreePeg = cellThree.getText().toString();
                String cellFourPeg = cellFour.getText().toString();
                String cellFivePeg = cellFive.getText().toString();
                String cellSixPeg = cellSix.getText().toString();
                String cellSevenPeg = cellSeven.getText().toString();
                String cellEightPeg = cellEight.getText().toString();

                if(cellZeroPeg.equals(cellOnePeg) && cellZeroPeg.equals(cellTwoPeg) && !cellZeroPeg.equals(" ")){
                    gameOver=true;
                    disableButtons();
                    (new Handler()).postDelayed(this::makeCell0Red, 500);
                    (new Handler()).postDelayed(this::makeCell1Red, 1000);
                    (new Handler()).postDelayed(this::makeCell2Red, 1500);
                }

                else if(cellThreePeg.equals(cellFourPeg) && cellThreePeg.equals(cellFivePeg) && !cellThreePeg.equals(" ")){
                    gameOver=true;
                    disableButtons();
                    (new Handler()).postDelayed(this::makeCell3Red, 500);
                    (new Handler()).postDelayed(this::makeCell4Red, 1000);
                    (new Handler()).postDelayed(this::makeCell5Red, 1500);
                }

                else if(cellSixPeg.equals(cellSevenPeg) && cellSixPeg.equals(cellEightPeg) && !cellSixPeg.equals(" ")){
                    gameOver=true;
                    disableButtons();
                    (new Handler()).postDelayed(this::makeCell6Red, 500);
                    (new Handler()).postDelayed(this::makeCell7Red, 1000);
                    (new Handler()).postDelayed(this::makeCell8Red, 1500);
                }
            }

            private int checkRows(){
                String peg0 = allCells[0].pegPlaced;
                String peg1 = allCells[1].pegPlaced;
                String peg2 = allCells[2].pegPlaced;
                String peg3 = allCells[3].pegPlaced;
                String peg4 = allCells[4].pegPlaced;
                String peg5 = allCells[5].pegPlaced;
                String peg6 = allCells[6].pegPlaced;
                String peg7 = allCells[7].pegPlaced;
                String peg8 = allCells[8].pegPlaced;

                if (peg0.equals(peg1)&&peg0.equals(peg2)&&!peg0.equals(" ")){
                    if (peg0.equals("O")){
                        return 100;
                    }
                    else{
                        return -100;
                    }
                }

                else if (peg3.equals(peg4)&&peg3.equals(peg5)&&!peg3.equals(" ")){
                    if (peg3.equals("O")){
                        return 100;
                    }
                    else{
                        return -100;
                    }
                }

                else if (peg6.equals(peg7)&&peg6.equals(peg8)&&!peg6.equals(" ")){
                    if (peg6.equals("O")){
                        return 100;
                    }
                    else{
                        return -100;
                    }
                }

                else{
                    return 0;
                }
            }



            @RequiresApi(api = Build.VERSION_CODES.M)
            private void resolveColumns() {
                String cellZeroPeg = cellZero.getText().toString();
                String cellOnePeg = cellOne.getText().toString();
                String cellTwoPeg = cellTwo.getText().toString();
                String cellThreePeg = cellThree.getText().toString();
                String cellFourPeg = cellFour.getText().toString();
                String cellFivePeg = cellFive.getText().toString();
                String cellSixPeg = cellSix.getText().toString();
                String cellSevenPeg = cellSeven.getText().toString();
                String cellEightPeg = cellEight.getText().toString();

                if(cellZeroPeg.equals(cellThreePeg) && cellZeroPeg.equals(cellSixPeg) && !cellZeroPeg.equals(" ")){
                    gameOver=true;
                    disableButtons();
                    (new Handler()).postDelayed(this::makeCell0Red, 500);
                    (new Handler()).postDelayed(this::makeCell3Red, 1000);
                    (new Handler()).postDelayed(this::makeCell6Red, 1500);
                }

                else if(cellOnePeg.equals(cellFourPeg) && cellOnePeg.equals(cellSevenPeg) && !cellOnePeg.equals(" ")){
                    gameOver=true;
                    disableButtons();
                    (new Handler()).postDelayed(this::makeCell1Red, 500);
                    (new Handler()).postDelayed(this::makeCell4Red, 1000);
                    (new Handler()).postDelayed(this::makeCell7Red, 1500);
                }

                else if(cellTwoPeg.equals(cellFivePeg) && cellTwoPeg.equals(cellEightPeg) && !cellTwoPeg.equals(" ")){
                    gameOver=true;
                    disableButtons();
                    (new Handler()).postDelayed(this::makeCell2Red, 500);
                    (new Handler()).postDelayed(this::makeCell5Red, 1000);
                    (new Handler()).postDelayed(this::makeCell8Red, 1500);
                }
            }

            private int checkColumns(){
                String peg0 = allCells[0].pegPlaced;
                String peg1 = allCells[1].pegPlaced;
                String peg2 = allCells[2].pegPlaced;
                String peg3 = allCells[3].pegPlaced;
                String peg4 = allCells[4].pegPlaced;
                String peg5 = allCells[5].pegPlaced;
                String peg6 = allCells[6].pegPlaced;
                String peg7 = allCells[7].pegPlaced;
                String peg8 = allCells[8].pegPlaced;

                if (peg0.equals(peg3)&&peg0.equals(peg6)&&!peg0.equals(" ")){
                    if (peg0.equals("O")){
                        return 100;
                    }
                    else{
                        return -100;
                    }
                }

                else if (peg1.equals(peg4)&&peg1.equals(peg7)&&!peg1.equals(" ")){
                    if (peg1.equals("O")){
                        return 100;
                    }
                    else{
                        return -100;
                    }
                }

                else if (peg2.equals(peg5)&&peg2.equals(peg8)&&!peg2.equals(" ")){
                    if (peg2.equals("O")){
                        return 100;
                    }
                    else{
                        return -100;
                    }
                }

                else{
                    return 0;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            private void resolveDiagonals() {
                String cellZeroPeg = cellZero.getText().toString();
                String cellTwoPeg = cellTwo.getText().toString();
                String cellFourPeg = cellFour.getText().toString();
                String cellSixPeg = cellSix.getText().toString();
                String cellEightPeg = cellEight.getText().toString();

                if(cellZeroPeg.equals(cellFourPeg) && cellZeroPeg.equals(cellEightPeg) && !cellZeroPeg.equals(" ")){
                    gameOver=true;
                    disableButtons();
                    (new Handler()).postDelayed(this::makeCell0Red, 500);
                    (new Handler()).postDelayed(this::makeCell4Red, 1000);
                    (new Handler()).postDelayed(this::makeCell8Red, 1500);
                }

                else if(cellTwoPeg.equals(cellFourPeg) && cellTwoPeg.equals(cellSixPeg) && !cellTwoPeg.equals(" ")){
                    gameOver=true;
                    disableButtons();
                    (new Handler()).postDelayed(this::makeCell2Red, 500);
                    (new Handler()).postDelayed(this::makeCell4Red, 1000);
                    (new Handler()).postDelayed(this::makeCell6Red, 1500);
                }
            }

            private int checkDiagonals(){
                String peg0 = allCells[0].pegPlaced;
                String peg2 = allCells[2].pegPlaced;
                String peg4 = allCells[4].pegPlaced;
                String peg6 = allCells[6].pegPlaced;
                String peg8 = allCells[8].pegPlaced;

                if (peg0.equals(peg4)&&peg0.equals(peg8)&&!peg0.equals(" ")){
                    if (peg0.equals("O")){
                        return 100;
                    }
                    else{
                        return -100;
                    }
                }

                else if (peg2.equals(peg4)&&peg2.equals(peg6)&&!peg2.equals(" ")){
                    if (peg2.equals("O")){
                        return 100;
                    }
                    else{
                        return -100;
                    }
                }

                else{
                    return 0;
                }
            }

            private boolean boardFull(){
                boolean full;
                full= getVacantCells().length == 0;
                return full;
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            private void resolveBoard() {
                resolveDiagonals();
                resolveRows();
                resolveColumns();
                if (boardFull() && !gameOver){
                    gameOver=true;
                    disableButtons();
                    (new Handler()).postDelayed(this::makeCell4Red, 500);
                    (new Handler()).postDelayed(this::makeInnerStarRed, 1000);
                    (new Handler()).postDelayed(this::makeOuterStartRed, 1500);
                }
            }

            private int calcScore(){
                int winner;
                winner = checkDiagonals();
                if (winner==0){
                    winner=checkRows();
                }
                if (winner==0){
                    winner=checkColumns();
                }
                return winner;
            }

            // Plays a random legal move.
            private void randomMove(){
                int[] allVacantCells = getVacantCells();
                if (allVacantCells.length>1) {
                    Random rand = new Random();
                    int upperBound = allVacantCells.length;
                    int randomPosition = rand.nextInt(upperBound);
                    placePeg(allVacantCells[randomPosition], computerPeg);
                }
                else{
                    placePeg(allVacantCells[0], computerPeg);
                }

            }

            private void disableButtons(){
                cellZero.setClickable(false);
                cellOne.setClickable(false);
                cellTwo.setClickable(false);
                cellThree.setClickable(false);
                cellFour.setClickable(false);
                cellFive.setClickable(false);
                cellSix.setClickable(false);
                cellSeven.setClickable(false);
                cellEight.setClickable(false);
            }

            private void enableButtons(){
                cellZero.setClickable(true);
                cellOne.setClickable(true);
                cellTwo.setClickable(true);
                cellThree.setClickable(true);
                cellFour.setClickable(true);
                cellFive.setClickable(true);
                cellSix.setClickable(true);
                cellSeven.setClickable(true);
                cellEight.setClickable(true);

            }

            private void makeCell0Red(){
                cellZero.setTextColor(getColor(R.color.red));
            }
            private void makeCell1Red(){
                cellOne.setTextColor(getColor(R.color.red));
            }
            private void makeCell2Red(){
                cellTwo.setTextColor(getColor(R.color.red));
            }
            private void makeCell3Red(){
                cellThree.setTextColor(getColor(R.color.red));
            }
            private void makeCell4Red(){
                cellFour.setTextColor(getColor(R.color.red));
            }
            private void makeCell5Red(){
                cellFive.setTextColor(getColor(R.color.red));
            }
            private void makeCell6Red(){
                cellSix.setTextColor(getColor(R.color.red));
            }
            private void makeCell7Red(){
                cellSeven.setTextColor(getColor(R.color.red));
            }
            private void makeCell8Red(){
                cellEight.setTextColor(getColor(R.color.red));
            }
            private void makeInnerStarRed(){
                makeCell1Red();
                makeCell3Red();
                makeCell5Red();
                makeCell7Red();
            }
            private void makeOuterStartRed(){
                makeCell0Red();
                makeCell2Red();
                makeCell6Red();
                makeCell8Red();
            }

            private void newGame(){
                enableButtons();
                setAllTextWhite();
                setAllCellsEmpty();
                getPegs();
                gameOver=false;
                boolean userStarts;
                userStarts= userPeg.equals("O");
                if (!userStarts){
                    randomMove();
                }
            }

            private void resetGame(){
                (new Handler()).postDelayed(this::newGame, 2000);
            }

            private int[] getVacantPos(){
                int[] vacantPosList = new int[0];

                for (Cell allCell : allCells) {
                    if (allCell.pegPlaced.equals(" ")) {
                        vacantPosList = appendToArray(vacantPosList, allCell.position);
                    }
                }

                return vacantPosList;
            }

            private void retrieveCellPegs(){
                allCells[0].pegPlaced=cellZero.getText().toString();
                allCells[1].pegPlaced=cellOne.getText().toString();
                allCells[2].pegPlaced=cellTwo.getText().toString();
                allCells[3].pegPlaced=cellThree.getText().toString();
                allCells[4].pegPlaced=cellFour.getText().toString();
                allCells[5].pegPlaced=cellFive.getText().toString();
                allCells[6].pegPlaced=cellSix.getText().toString();
                allCells[7].pegPlaced=cellSeven.getText().toString();
                allCells[8].pegPlaced=cellEight.getText().toString();
            }


            private int[] minimizer(int depth, int alpha, int beta){
                int[] allVacantCells = getVacantPos();
                int posForMinimizer=0;
                int minScore=1000;
                for (int pos : allVacantCells) {
                    allCells[pos].putPeg("X");
                    int score = calcScore();
                    if (score != 0) {
                        allCells[pos].score = score + depth;
                    } else if (allVacantCells.length == 1) {
                        allCells[pos].score = score + depth;
                    } else {
                        int[] minList = maximizer(depth + 1, alpha, beta);
                        allCells[pos].score = minList[0];
                    }

                    if (allCells[pos].score < minScore) {
                        minScore = allCells[pos].score;
                        posForMinimizer = allCells[pos].position;
                    }

                    beta = Math.min(beta, allCells[pos].score);
                    if (beta <= alpha) {
                        allCells[pos].putPeg(" ");
                        allCells[pos].score = 0;
                        break;
                    }

                    allCells[pos].putPeg(" ");
                    allCells[pos].score = 0;
                }
                int[] returnList = new int[2];
                returnList[0]=minScore;
                returnList[1]=posForMinimizer;
                return returnList;
            }

            private int[] maximizer(int depth, int alpha, int beta){
                int[] allVacantCells = getVacantPos();
                int posForMaximizer=0;
                int maxScore=-1000;
                for (int pos : allVacantCells) {
                    allCells[pos].putPeg("O");
                    int score = calcScore();
                    if (score != 0) {
                        allCells[pos].score = score - depth;
                    } else if (allVacantCells.length == 1) {
                        allCells[pos].score = score - depth;
                    } else {
                        int[] maxList = minimizer(depth + 1, alpha, beta);
                        allCells[pos].score = maxList[0];
                    }

                    if (allCells[pos].score > maxScore) {
                        maxScore = allCells[pos].score;
                        posForMaximizer = allCells[pos].position;
                    }

                    alpha = Math.max(alpha, allCells[pos].score);
                    if (beta <= alpha) {
                        allCells[pos].putPeg(" ");
                        allCells[pos].score = 0;
                        break;
                    }

                    allCells[pos].putPeg(" ");
                    allCells[pos].score = 0;
                }
                int[] returnList = new int[2];
                returnList[0]=maxScore;
                returnList[1]=posForMaximizer;
                return returnList;
            }

            private void perfectMove(){
                retrieveCellPegs();
                if (computerPeg.equals("O")){
                    int[] listVal = maximizer(0,-1000,1000);
                    placePeg(listVal[1],computerPeg);
                }
                else{
                    int[] listVal = minimizer(0,-1000, 1000);
                    placePeg(listVal[1],computerPeg);
                }
            }

            private void computerMove(){
                disableButtons();
                if (randomMoverBtn.isChecked()){
                    randomMove();
                }
                else if (perfectMoverBtn.isChecked()){
                    perfectMove();
                }
                enableButtons();
            }
        }

        final Board newBoard = new Board();
        newBoard.initializeCells();
        newBoard.newGame();

        cellZero.setOnClickListener(v -> {
            if (elementInArray(newBoard.getVacantCells(),0)){
                cellZero.setText(newBoard.userPeg);
                newBoard.resolveBoard();
                if (!newBoard.gameOver) {
                    newBoard.computerMove();
                    newBoard.resolveBoard();
                    if (newBoard.gameOver){
                        newBoard.resetGame();
                    }
                }
                else{
                    newBoard.resetGame();
                }
            }
        });

        cellOne.setOnClickListener(v -> {
            if (elementInArray(newBoard.getVacantCells(),1)){
                cellOne.setText(newBoard.userPeg);
                newBoard.resolveBoard();
                if (!newBoard.gameOver) {
                    newBoard.computerMove();
                    newBoard.resolveBoard();
                    if (newBoard.gameOver){
                        newBoard.resetGame();
                    }
                }
                else{
                    newBoard.resetGame();
                }
            }
        });

        cellTwo.setOnClickListener(v -> {
            if (elementInArray(newBoard.getVacantCells(),2)){
                cellTwo.setText(newBoard.userPeg);
                newBoard.resolveBoard();
                if (!newBoard.gameOver) {
                    newBoard.computerMove();
                    newBoard.resolveBoard();
                    if (newBoard.gameOver){
                        newBoard.resetGame();
                    }
                }
                else{
                    newBoard.resetGame();
                }
            }
        });

        cellThree.setOnClickListener(v -> {
            if (elementInArray(newBoard.getVacantCells(),3)){
                cellThree.setText(newBoard.userPeg);
                newBoard.resolveBoard();
                if (!newBoard.gameOver) {
                    newBoard.computerMove();
                    newBoard.resolveBoard();
                    if (newBoard.gameOver){
                        newBoard.resetGame();
                    }
                }
                else{
                    newBoard.resetGame();
                }
            }
        });

        cellFour.setOnClickListener(v -> {
            if (elementInArray(newBoard.getVacantCells(),4)){
                cellFour.setText(newBoard.userPeg);
                newBoard.resolveBoard();
                if (!newBoard.gameOver) {
                    newBoard.computerMove();
                    newBoard.resolveBoard();
                    if (newBoard.gameOver){
                        newBoard.resetGame();
                    }
                }
                else{
                    newBoard.resetGame();
                }
            }
        });

        cellFive.setOnClickListener(v -> {
            if (elementInArray(newBoard.getVacantCells(),5)){
                cellFive.setText(newBoard.userPeg);
                newBoard.resolveBoard();
                if (!newBoard.gameOver) {
                    newBoard.computerMove();
                    newBoard.resolveBoard();
                    if (newBoard.gameOver){
                        newBoard.resetGame();
                    }
                }
                else{
                    newBoard.resetGame();
                }
            }
        });

        cellSix.setOnClickListener(v -> {
            if (elementInArray(newBoard.getVacantCells(),6)){
                cellSix.setText(newBoard.userPeg);
                newBoard.resolveBoard();
                if (!newBoard.gameOver) {
                    newBoard.computerMove();
                    newBoard.resolveBoard();
                    if (newBoard.gameOver){
                        newBoard.resetGame();
                    }
                }
                else{
                    newBoard.resetGame();
                }
            }
        });

        cellSeven.setOnClickListener(v -> {
            if (elementInArray(newBoard.getVacantCells(),7)){
                cellSeven.setText(newBoard.userPeg);
                newBoard.resolveBoard();
                if (!newBoard.gameOver) {
                    newBoard.computerMove();
                    newBoard.resolveBoard();
                    if (newBoard.gameOver){
                        newBoard.resetGame();
                    }
                }
                else{
                    newBoard.resetGame();
                }
            }
        });

        cellEight.setOnClickListener(v -> {
            if (elementInArray(newBoard.getVacantCells(),8)){
                cellEight.setText(newBoard.userPeg);
                newBoard.resolveBoard();
                if (!newBoard.gameOver) {
                    newBoard.computerMove();
                    newBoard.resolveBoard();
                    if (newBoard.gameOver){
                        newBoard.resetGame();
                    }
                }
                else{
                    newBoard.resetGame();
                }
            }
        });

    }

    // This function takes in a toggleButton and a boolean. It modifies the text color accordingly.
    @SuppressLint("ResourceAsColor")
    public void modifyToggle(ToggleButton toggleButton, boolean toggleChecked){
        if (toggleChecked){
            toggleButton.setTextColor(getResources().getColor(R.color.white));
        }
        else{
            toggleButton.setTextColor(getResources().getColor(R.color.fadedWhite));
        }
    }

    // This function appends an element to an array.
    public int[] appendToArray(int[] oldArray, int newElement){
        int oldArrayLength = oldArray.length;
        int[] newArray = new int[oldArrayLength+1];

        if (oldArrayLength>0) {
            // Copies the elements of the old array into the new array.
            System.arraycopy(oldArray, 0, newArray, 0, oldArrayLength);
        }

        // Assigns new element to the last position of the new array.
        newArray[oldArrayLength]=newElement;
        return(newArray);
    }

    public boolean elementInArray(int[] oldArray, int element){
        boolean isPresent = false;
        for (int i: oldArray){
            if (i==element){
                isPresent=true;
                break;
            }
        }
        return(isPresent);
    }



}
