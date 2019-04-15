/*1301587 박소현
	테트리스*/


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

#define LEFT   75      // 좌측방향키
#define RIGHT  77      // 우측방향키
#define UP     72      // 위쪽방향키
#define DOWN   80      // 아래방향키

int board[ROW][COL] = { 0 };
int block[][4][4][4] = { // 블럭번호, 회전번호, 4*4
	{ { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 } },
	{ { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
	{ { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
	{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } }
};

int cp_x;//블럭의현재위치
int cp_y;

class Blocks{
private:			//외부에서 건드릴 수 없게 함
	int shape;
	int rotation;
public:
	void new_block();		//블럭랜덤생성함수
	void draw_block();		//블럭을 cmd창에 그리기
	void erase_block();		//한칸씩 내려올 때 마다 원래 블럭의 위치 지우기 
	void rotate_block();	//블럭 회전
	int check();			//벽에 닿았는지 체크
	void save_block();		//보드 배열에 쌓인 블럭 저장

};
void showBoard();	//보드출력
void lineCheck();	//쌓인블럭한줄씩체크

void gotoxy(int x, int y)
{
	COORD Cur;
	Cur.X = x * 2;
	Cur.Y = y;
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), Cur);
}

//보드배열초기화
void initBoard(){
	for (int i = 0; i < ROW; i++){
		for (int j = 0; j < COL; j++){
			if (j == 0 || j == COL - 1 || i == ROW - 1){
				board[i][j] = 3;	//벽은 3으로
			}
			else{
				board[i][j] = 0;	//빈칸은 0으로
			}
		}
	}
}
//게임이 끝나면 출력
int gameOver(){
	gotoxy(COL / 2 - 2, ROW);	//보드배열아래에
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
	initBoard();	//보드 배열 초기화
	while (1){
		//블럭이 나타나는 곳 초기값 (x,y)
		cp_x = COL / 2 - 2;
		cp_y = 0;

		showBoard();
		bl.new_block();
		bl.draw_block();
		int k = 1;
		while (k)
		{

			if (_kbhit()){	//키 입력받으면
				keytemp = _getch();
				bl.erase_block();
			}
			else{			//키 입력이 없으면
				keytemp = DOWN;
				Sleep(300);	//일정한시간씩보이며 내려감
				bl.erase_block();
			}
			switch (keytemp){
			case UP:				//상 : 회전
				bl.rotate_block();
				break;
			case LEFT:				//좌 : 왼쪽으로
				cp_x--;
				if (bl.check()){	//벽이면
					cp_x++;			//움직이지 않는다									
				}
				break;
			case RIGHT:				//우 : 오른쪽으로
				cp_x++;
				if (bl.check()){	//벽이면
					cp_x--;			//움직이지 않는다						
				}
				break;
			case DOWN:				//하 : 블럭 내려감
				cp_y++;
				if (bl.check()){		//바닥이나 쌓인 블럭에 닿으면
					cp_y--;
					k = 0;			//반복중지
					bl.save_block();//보드에 블럭저장
				}
			}

			bl.draw_block();		//바뀐 위치에 블럭 그리기
		}
		lineCheck();				//줄이 채워졌는지 확인
		if (gameOver()){			//게임오버
			break;
		}
	}
	system("PAUSE");
}

void Blocks::new_block(){
	srand((unsigned)time(NULL));
	shape = rand() % 7;				//랜덤으로 모양 선택
	rotation = rand() % 4;			//랜덤으로 회전번호 선택
}

void showBoard(){
	for (int i = 0; i < ROW; i++){
		gotoxy(0, i);
		for (int j = 0; j < COL; j++){
			if (board[i][j] == 3){		//벽이면
				cout << "□";
			}
			else if (board[i][j] == 1){	//쌓인 블럭이면
				cout << "▨";
			}
			else{
				cout << "　";			//빈공간
			}
		}
	}
}

void Blocks::draw_block(){
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			if (block[shape][rotation][i][j] == 1){		//블럭배열에서 '1'인부분(블럭모양)만
				gotoxy(cp_x + i, cp_y + j);
				cout << "■";							//출력
			}
		}
		cout << endl;
	}
}

void Blocks::erase_block(){
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			if (block[shape][rotation][i][j] == 1){		//블럭배열에서 '1'인 부분만
				gotoxy(cp_x + i, cp_y + j);
				cout << "　";							//빈칸으로..지움
			}
		}
		cout << endl;
	}
}

void Blocks::rotate_block(){
	if (rotation == 3){			//회전번호는 0~3까지밖에없으므로 3이면 0으로 바뀌어야함
		rotation = 0;
		if (check())			//벽이면
			rotation = 3;		//회전못하게!
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
			if (block[shape][rotation][j][i] == 1){			//블럭에서1인부분이
				if (board[i + cp_y][j + cp_x]) return 6;	//벽에 닿으면 6 리턴 : over
			}
		}
	}
	return 0;
}

void Blocks::save_block(){
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			if (block[shape][rotation][j][i] == 1){ //블럭에서1인부분을
				board[i + cp_y][j + cp_x] = 1;		//보드배열에 지금 블럭의 위치 저장
			}
		}
	}
}

//한줄 지우고 위에 쌓인 블럭 내리기
void deleteLine(int num){
	//줄 초기화
	for (int i = 0; i < COL; i++){
		if (i == 0 || i == COL - 1){
			board[num][i] = 3;
		}
		else{
			board[num][i] = 0;
		}
	}
	//한칸씩바로위의보드배열내용을내려서저장
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
			if (board[i][j] == 0 || board[i][j] == 3)	//0 : 빈칸 3 :벽
				break;
		}
		if (j == COL - 1){		//한줄이 다 채워졌음
			deleteLine(i);
			i++;			//내려온 줄도 채워져 있을 수 있으므로
			showBoard();
			Sleep(300);		//한줄씩지워지는거보여주려고!
		}
	}
}