package com.reservate;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

class MainFrame extends JFrame implements ActionListener {
	JMenuBar bar = new JMenuBar();// 메뉴바
	JMenu menu;
	JMenu subMenu;
	JMenuItem item;
	JPanel pan1 = new JPanel();
	JPanel pan2 = new JPanel();
	JProgressBar progress = new JProgressBar(0, 100);
	
	//이미지 넣기
	JLabel lbImg1 = new JLabel();
	JLabel lbImg2 = new JLabel();
	
	//다시 로그인 하는 경우를 위해 Connection을  static으로 선언
	static Connection con;

	String lectureRoom="326";
	String homeAway="home";
	
	String sql;
	Statement stmt;
	ResultSet rs;
	//예약률을 저장하는 변수
	int salesRate;
	//전체 좌석수를 저장하는 변수
	int totNum;
	//전체 잔여좌석수
	int curNum;
	
	//디폴트 생성자
	public MainFrame(){}
	
	public MainFrame(Connection conn) {
		super("강의실 좌석 예약 프로그램");		
		con = conn;
		

		//구매현황 처리
		progressSet();
		pan1.setLayout(new GridLayout(4,5));
		
		pan1.add(new JLabel("           "));		
		pan1.add(new JLabel(""));
		pan1.add(new JLabel("             "));
		pan1.add(new JLabel(""));		
		pan1.add(new JLabel(""));
		
		pan1.add(new JLabel("             "));
		pan1.add(new JLabel("                 좌석 예약률"));
		pan1.add(progress);
		pan1.add(new JLabel(""));
		pan1.add(new JLabel(""));		
				
		pan1.add(new JLabel(""));
		pan1.add(new JLabel(""));
		pan1.add(new JLabel(""));
		pan1.add(new JLabel(""));
		pan1.add(new JLabel(""));
		
		pan1.add(new JLabel(""));
		pan1.add(new JLabel(""));
		pan1.add(new JLabel(""));
		pan1.add(new JLabel(""));
		pan1.add(new JLabel(""));
		add(pan1);		
		
		ImageIcon image2 = new ImageIcon("ksu.jpg");
		lbImg2.setIcon(image2);
		pan2.add(lbImg2);
		add(pan2);
		
		setJMenuBar(bar);// 프레임에 메뉴바 붙이기
		menu = new JMenu("접속");
		bar.add(menu);
		item = new JMenuItem("로그인");
		menu.add(item);
		item.addActionListener(this);
		menu.addSeparator();
		item = new JMenuItem("로그아웃");
		menu.add(item);
		item.addActionListener(this);
		menu.addSeparator();
		item = new JMenuItem("끝내기");
		menu.add(item);
		item.addActionListener(this);

		menu = new JMenu("강의실");
		bar.add(menu);
		subMenu = new JMenu("   예약     ");
		item = new JMenuItem("326");
		subMenu.add(item);
		item.addActionListener(this);
		subMenu.addSeparator();
		item = new JMenuItem("327");
		subMenu.add(item);
		item.addActionListener(this);
		menu.add(subMenu);		
		menu.addSeparator();
		item = new JMenuItem("   조회    ");
		menu.add(item);
		item.addActionListener(this);
	
		menu = new JMenu("좌석관리");
		bar.add(menu);
		item = new JMenuItem("좌석초기화");
		menu.add(item);
		item.addActionListener(this);
		
		menu = new JMenu("관리");
		bar.add(menu);
		item = new JMenuItem("회원관리");
		menu.add(item);
		item.addActionListener(this);
		
		add("North",pan1);
		
		
		setBounds(300, 150, 800, 600);
		setVisible(true);
		
		//X눌렀을 때 connection 처리를 위한 클래스 
		class CloseWin extends WindowAdapter{
			Connection conn;
			public CloseWin(Connection con){
				this.conn = con;
			}
			public void windowClosing(WindowEvent e){
				try{
					if(conn!=null){					
						conn.close();
					}
					System.exit(0);
						
				}catch(SQLException se){
					System.out.println(se.getMessage());
				}
			}
		}		
		this.addWindowListener(new CloseWin(con));	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("로그인")) {
			connectDB();
		}else if(e.getActionCommand().equals("로그아웃")) {			
			conClose();
		}else if(e.getActionCommand().equals("끝내기")) {
			try{
				if(con != null){
					con.close();
				}
				this.dispose();
			}catch(SQLException se){
				System.out.println(se.getMessage());
			}
		}else if(e.getActionCommand().equals("326")) {
			if(isConnected()){
				lectureRoom = "326"; 
				new Reservation(this);
			}
		}
		else if(e.getActionCommand().equals("327")) {
			if(isConnected()){
				lectureRoom = "327";
				new Reservation(this);
			}
		}else if(e.getActionCommand().equals("   조회    ")) {
			if(isConnected()){
				new Check(this);
			}
		}else if(e.getActionCommand().equals("좌석초기화")) {
			if(isConnected()){
				initSeats();
				progressSet();
			}
		}else if(e.getActionCommand().equals("회원관리")){
			if(isConnected()){
				new com.manage.MemberManagements().MmStart();
			}
		}
	}
	
	public boolean isConnected(){
		if (con == null) {
			JOptionPane.showMessageDialog(this, "로그인 되어있지 않습니다.");
			return false;
		}
		return true;
	}
	public void conClose() {
		try {
			if(isConnected()){
				con.close();
				con = null;
				JOptionPane.showMessageDialog(this, "접속이 종료되었습니다.");
			}			
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public void connectDB() {
		if(con != null){
			JOptionPane.showMessageDialog(this, "이미 접속 중이십니다. 접속종료후 시도바랍니다.");
			return;
		}
		new ReLogin(this);
	}
	//Connection 멤버변수 셋팅
	public static void setCon(Connection conn){
		con = conn;
	}
	
	// 좌석의 수를 초기화한다.
	public void initSeats(){
		getSeatsTot();
		try{	
			sql = "update Seat set reservation=0";
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(this, "초기화를 완료 했습니다");
			totNum=curNum=0;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			se.printStackTrace();
		}
		
	}
	
	// SEATS 테이블에 저장된 총 좌석수, 잔여좌석을 
	// 각각  seatTot[], curNum[]에 저장. 
	public void getSeatsTot(){		 
		try{
			DBConnect db=new DBConnect();
			totNum=curNum=0;
			
			rs=db.stmt.executeQuery("SELECT * FROM Seat");
			while(rs.next()){
				totNum++;
				if(rs.getInt("reservation")==0){
					curNum++;
				}
			}
			
			System.out.println("전체좌석수:"+totNum);
			System.out.println("잔여좌석수:"+curNum);
			
		}catch(SQLException se){
			System.out.println(se.getMessage());			
		}		
		
	}
	
	//판매율을 계산하는 메서드
	public void salesRate(){
		salesRate = 100-(int)(((double)curNum/(double)totNum)*100);
		System.out.println(salesRate);
	}
	public void progressSet(){
		//진행막대 셋팅을 위해 구매율을 계산한다.
		System.out.println("프로그레스 호출");
		getSeatsTot();
		salesRate();
		
		//진행막대 셋팅
		progress.setValue(0);
		progress.setStringPainted(true);
		Progress pr = new Progress(this);
		pr.start();
		
	}
	
}



