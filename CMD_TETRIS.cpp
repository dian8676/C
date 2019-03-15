/*151202 Dian Park*/


#include <iostream>
#include <iostream>
#include <conio.h>
#include <cstring>
#include <windows.h>
#include <iomanip>
#include <time.h>

using namespace std;

#define ROW 20
#define COL 14

#define LEFT   75      // Left key
#define RIGHT  77      // Right key
#define UP     72      // Up key
#define DOWN   80      // Down key

int board[ROW][COL] = { 0 };
int block[][4][4][4] = { // Block number, Rotation number, 4*4
	{ { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 } },
	{ { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
	{ { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
	{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } }
};

int cp_x;	//Current position of block
int cp_y;

class Blocks{
private:			//Unable to modify externally
	int shape;
	int rotation;
public:
	void new_block();		//Function that produces blocks randomly
	void draw_block();		//Function of drawing blocks on CMD
	void erase_block();		//Function of erasing function 
	void rotate_block();	//Function that rotates a block
	int check();			//Function to check if a wall has been touched
	void save_block();		//Function to store blocks stacked on board array

};
void showBoard();	//Function that outputs board
void lineCheck();	//Function that checks each block of board array

void gotoxy(int x, int y)
{
	COORD Cur;
	Cur.X = x * 2;
	Cur.Y = y;
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), Cur);
}

//Init Board Array
void initBoard(){
	for (int i = 0; i < ROW; i++){
		for (int j = 0; j < COL; j++){
			if (j == 0 || j == COL - 1 || i == ROW - 1){
				board[i][j] = 3;	//Wall = 3
			}
			else{
				board[i][j] = 0;	//Blank = 0
			}
		}
	}
}
//GAME OVER
int gameOver(){
	gotoxy(COL / 2 - 2, ROW);	//Under the Board
	for (int i = 0; i < COL; i++){
		if (board[1][i] == 1){
			cout << "GAME OVER" << endl;
			return 1;
		}
	}
	return 0;
}

int main(){
	Blocks bl;
	char keytemp;
	int tmp = 0;
	initBoard();	//Init Board Array
	while (1){
		//Block initial positon (x,y)
		cp_x = COL / 2 - 2;
		cp_y = 0;

		showBoard();
		bl.new_block();
		bl.draw_block();
		int k = 1;
		while (k)
		{

			if (_kbhit()){	//if Enter the key
				keytemp = _getch();
				bl.erase_block();
			}
			else{
				keytemp = DOWN;
				Sleep(300);	//Visible for a certain period of time
				bl.erase_block();
			}
			switch (keytemp){
			case UP:				//UP key = rotation
				bl.rotate_block();
				break;
			case LEFT:				//LEFT key = to the left
				cp_x--;
				if (bl.check()){	//if the block touches the wall
					cp_x++;			//don't move									
				}
				break;
			case RIGHT:				//RIGHT key = to the right
				cp_x++;
				if (bl.check()){	//if the block touches the wall
					cp_x--;			//don't move						
				}
				break;
			case DOWN:				//DOWN = go down
				cp_y++;
				if (bl.check()){		//if the block touches another block or the floor
					cp_y--;
					k = 0;			//Stop Repeat(this fuction)
					bl.save_block();//Store the block on Board Array
				}
			}

			bl.draw_block();		//Draw the block in new positon
		}
		lineCheck();				//Check the line is full
		if (gameOver()){			//GAME OVER
			break;
		}
	}
	system("PAUSE");
}

void Blocks::new_block(){
	srand((unsigned)time(NULL));
	shape = rand() % 7;				//Select Appearance as Random
	rotation = rand() % 4;			//Select Rotation to Random
}

void showBoard(){	// draw Board
	for (int i = 0; i < ROW; i++){
		gotoxy(0, i);
		for (int j = 0; j < COL; j++){
			if (board[i][j] == 3){		//if it is the wall
				cout << "¡à";
			}
			else if (board[i][j] == 1){	//if it is the floor
				cout << "¢É";
			}
			else{
				cout << "¡¡";			//blank
			}
		}
	}
}

void Blocks::draw_block(){
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			if (block[shape][rotation][i][j] == 1){		//only 1 (the Block shape), in Block array
				gotoxy(cp_x + i, cp_y + j);
				cout << "¡á";							//draw
			}
		}
		cout << endl;
	}
}

void Blocks::erase_block(){
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			if (block[shape][rotation][i][j] == 1){		//only 1 (the Block shape), in Block array
				gotoxy(cp_x + i, cp_y + j);
				cout << "¡¡";							//erase
			}
		}
		cout << endl;
	}
}

void Blocks::rotate_block(){
	if (rotation == 3){			//The rotation number is only 0 to 3, so it must be changed from 3 to 0.
		rotation = 0;
		if (check())			//if it touches the wall
			rotation = 3;		//don't rotate
	}
	else{
		rotation++;
		if (check())
			rotation--;
	}
}

int Blocks::check(){
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			if (block[shape][rotation][j][i] == 1){			//the number 1 in Block Array
				if (board[i + cp_y][j + cp_x]) return 6;	//touches the wall, then return 6
			}
		}
	}
	return 0;
}

void Blocks::save_block(){
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			if (block[shape][rotation][j][i] == 1){ //the number 1 in Block Array
				board[i + cp_y][j + cp_x] = 1;		//Save the location of the block in the board array
			}
		}
	}
}

//Clear one line and down the block. 
void deleteLine(int num){
	//initial a line
	for (int i = 0; i < COL; i++){
		if (i == 0 || i == COL - 1){
			board[num][i] = 3;
		}
		else{
			board[num][i] = 0;
		}
	}
	//Save each row of boards by lowering the contents of the board.
	for (int i = num; i > 0; i--){
		for (int j = 0; j < COL; j++){
			if (!(j == 0 || j == COL - 1)){
				board[i][j] = board[i - 1][j];
			}
		}
	}
}

void lineCheck(){
	int i, j;
	for (i = ROW-1; i > 0; i--){
		for (j = 1; j < COL; j++)
		{
			if (board[i][j] == 0 || board[i][j] == 3)	//0 : blank 3 : wall
				break;
		}
		if (j == COL - 1){		//the line is full
			deleteLine(i);
			i++;			//The lines below may also be filled.
			showBoard();
			Sleep(300);		//To show one line after another.
		}
	}
}