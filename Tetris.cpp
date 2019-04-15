/*1301587 �ڼ���
	��Ʈ����*/


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

#define LEFT   75      // ��������Ű
#define RIGHT  77      // ��������Ű
#define UP     72      // ���ʹ���Ű
#define DOWN   80      // �Ʒ�����Ű

int board[ROW][COL] = { 0 };
int block[][4][4][4] = { // ����ȣ, ȸ����ȣ, 4*4
	{ { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 } },
	{ { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
	{ { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
	{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } }
};

int cp_x;//����������ġ
int cp_y;

class Blocks{
private:			//�ܺο��� �ǵ帱 �� ���� ��
	int shape;
	int rotation;
public:
	void new_block();		//�����������Լ�
	void draw_block();		//���� cmdâ�� �׸���
	void erase_block();		//��ĭ�� ������ �� ���� ���� ���� ��ġ ����� 
	void rotate_block();	//�� ȸ��
	int check();			//���� ��Ҵ��� üũ
	void save_block();		//���� �迭�� ���� �� ����

};
void showBoard();	//�������
void lineCheck();	//���κ����پ�üũ

void gotoxy(int x, int y)
{
	COORD Cur;
	Cur.X = x * 2;
	Cur.Y = y;
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), Cur);
}

//����迭�ʱ�ȭ
void initBoard(){
	for (int i = 0; i < ROW; i++){
		for (int j = 0; j < COL; j++){
			if (j == 0 || j == COL - 1 || i == ROW - 1){
				board[i][j] = 3;	//���� 3����
			}
			else{
				board[i][j] = 0;	//��ĭ�� 0����
			}
		}
	}
}
//������ ������ ���
int gameOver(){
	gotoxy(COL / 2 - 2, ROW);	//����迭�Ʒ���
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
	initBoard();	//���� �迭 �ʱ�ȭ
	while (1){
		//���� ��Ÿ���� �� �ʱⰪ (x,y)
		cp_x = COL / 2 - 2;
		cp_y = 0;

		showBoard();
		bl.new_block();
		bl.draw_block();
		int k = 1;
		while (k)
		{

			if (_kbhit()){	//Ű �Է¹�����
				keytemp = _getch();
				bl.erase_block();
			}
			else{			//Ű �Է��� ������
				keytemp = DOWN;
				Sleep(300);	//�����ѽð������̸� ������
				bl.erase_block();
			}
			switch (keytemp){
			case UP:				//�� : ȸ��
				bl.rotate_block();
				break;
			case LEFT:				//�� : ��������
				cp_x--;
				if (bl.check()){	//���̸�
					cp_x++;			//�������� �ʴ´�									
				}
				break;
			case RIGHT:				//�� : ����������
				cp_x++;
				if (bl.check()){	//���̸�
					cp_x--;			//�������� �ʴ´�						
				}
				break;
			case DOWN:				//�� : �� ������
				cp_y++;
				if (bl.check()){		//�ٴ��̳� ���� ���� ������
					cp_y--;
					k = 0;			//�ݺ�����
					bl.save_block();//���忡 ������
				}
			}

			bl.draw_block();		//�ٲ� ��ġ�� �� �׸���
		}
		lineCheck();				//���� ä�������� Ȯ��
		if (gameOver()){			//���ӿ���
			break;
		}
	}
	system("PAUSE");
}

void Blocks::new_block(){
	srand((unsigned)time(NULL));
	shape = rand() % 7;				//�������� ��� ����
	rotation = rand() % 4;			//�������� ȸ����ȣ ����
}

void showBoard(){
	for (int i = 0; i < ROW; i++){
		gotoxy(0, i);
		for (int j = 0; j < COL; j++){
			if (board[i][j] == 3){		//���̸�
				cout << "��";
			}
			else if (board[i][j] == 1){	//���� ���̸�
				cout << "��";
			}
			else{
				cout << "��";			//�����
			}
		}
	}
}

void Blocks::draw_block(){
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			if (block[shape][rotation][i][j] == 1){		//���迭���� '1'�κκ�(�����)��
				gotoxy(cp_x + i, cp_y + j);
				cout << "��";							//���
			}
		}
		cout << endl;
	}
}

void Blocks::erase_block(){
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			if (block[shape][rotation][i][j] == 1){		//���迭���� '1'�� �κи�
				gotoxy(cp_x + i, cp_y + j);
				cout << "��";							//��ĭ����..����
			}
		}
		cout << endl;
	}
}

void Blocks::rotate_block(){
	if (rotation == 3){			//ȸ����ȣ�� 0~3�����ۿ������Ƿ� 3�̸� 0���� �ٲ�����
		rotation = 0;
		if (check())			//���̸�
			rotation = 3;		//ȸ�����ϰ�!
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
			if (block[shape][rotation][j][i] == 1){			//������1�κκ���
				if (board[i + cp_y][j + cp_x]) return 6;	//���� ������ 6 ���� : over
			}
		}
	}
	return 0;
}

void Blocks::save_block(){
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			if (block[shape][rotation][j][i] == 1){ //������1�κκ���
				board[i + cp_y][j + cp_x] = 1;		//����迭�� ���� ���� ��ġ ����
			}
		}
	}
}

//���� ����� ���� ���� �� ������
void deleteLine(int num){
	//�� �ʱ�ȭ
	for (int i = 0; i < COL; i++){
		if (i == 0 || i == COL - 1){
			board[num][i] = 3;
		}
		else{
			board[num][i] = 0;
		}
	}
	//��ĭ���ٷ����Ǻ���迭����������������
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
			if (board[i][j] == 0 || board[i][j] == 3)	//0 : ��ĭ 3 :��
				break;
		}
		if (j == COL - 1){		//������ �� ä������
			deleteLine(i);
			i++;			//������ �ٵ� ä���� ���� �� �����Ƿ�
			showBoard();
			Sleep(300);		//���پ��������°ź����ַ���!
		}
	}
}