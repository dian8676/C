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
	JMenuBar bar = new JMenuBar();// �޴���
	JMenu menu;
	JMenu subMenu;
	JMenuItem item;
	JPanel pan1 = new JPanel();
	JPanel pan2 = new JPanel();
	JProgressBar progress = new JProgressBar(0, 100);
	
	//�̹��� �ֱ�
	JLabel lbImg1 = new JLabel();
	JLabel lbImg2 = new JLabel();
	
	//�ٽ� �α��� �ϴ� ��츦 ���� Connection��  static���� ����
	static Connection con;

	String lectureRoom="326";
	String homeAway="home";
	
	String sql;
	Statement stmt;
	ResultSet rs;
	//������� �����ϴ� ����
	int salesRate;
	//��ü �¼����� �����ϴ� ����
	int totNum;
	//��ü �ܿ��¼���
	int curNum;
	
	//����Ʈ ������
	public MainFrame(){}
	
	public MainFrame(Connection conn) {
		super("���ǽ� �¼� ���� ���α׷�");		
		con = conn;
		

		//������Ȳ ó��
		progressSet();
		pan1.setLayout(new GridLayout(4,5));
		
		pan1.add(new JLabel("           "));		
		pan1.add(new JLabel(""));
		pan1.add(new JLabel("             "));
		pan1.add(new JLabel(""));		
		pan1.add(new JLabel(""));
		
		pan1.add(new JLabel("             "));
		pan1.add(new JLabel("                 �¼� �����"));
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
		
		setJMenuBar(bar);// �����ӿ� �޴��� ���̱�
		menu = new JMenu("����");
		bar.add(menu);
		item = new JMenuItem("�α���");
		menu.add(item);
		item.addActionListener(this);
		menu.addSeparator();
		item = new JMenuItem("�α׾ƿ�");
		menu.add(item);
		item.addActionListener(this);
		menu.addSeparator();
		item = new JMenuItem("������");
		menu.add(item);
		item.addActionListener(this);

		menu = new JMenu("���ǽ�");
		bar.add(menu);
		subMenu = new JMenu("   ����     ");
		item = new JMenuItem("326");
		subMenu.add(item);
		item.addActionListener(this);
		subMenu.addSeparator();
		item = new JMenuItem("327");
		subMenu.add(item);
		item.addActionListener(this);
		menu.add(subMenu);		
		menu.addSeparator();
		item = new JMenuItem("   ��ȸ    ");
		menu.add(item);
		item.addActionListener(this);
	
		menu = new JMenu("�¼�����");
		bar.add(menu);
		item = new JMenuItem("�¼��ʱ�ȭ");
		menu.add(item);
		item.addActionListener(this);
		
		menu = new JMenu("����");
		bar.add(menu);
		item = new JMenuItem("ȸ������");
		menu.add(item);
		item.addActionListener(this);
		
		add("North",pan1);
		
		
		setBounds(300, 150, 800, 600);
		setVisible(true);
		
		//X������ �� connection ó���� ���� Ŭ���� 
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
		if(e.getActionCommand().equals("�α���")) {
			connectDB();
		}else if(e.getActionCommand().equals("�α׾ƿ�")) {			
			conClose();
		}else if(e.getActionCommand().equals("������")) {
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
		}else if(e.getActionCommand().equals("   ��ȸ    ")) {
			if(isConnected()){
				new Check(this);
			}
		}else if(e.getActionCommand().equals("�¼��ʱ�ȭ")) {
			if(isConnected()){
				initSeats();
				progressSet();
			}
		}else if(e.getActionCommand().equals("ȸ������")){
			if(isConnected()){
				new com.manage.MemberManagements().MmStart();
			}
		}
	}
	
	public boolean isConnected(){
		if (con == null) {
			JOptionPane.showMessageDialog(this, "�α��� �Ǿ����� �ʽ��ϴ�.");
			return false;
		}
		return true;
	}
	public void conClose() {
		try {
			if(isConnected()){
				con.close();
				con = null;
				JOptionPane.showMessageDialog(this, "������ ����Ǿ����ϴ�.");
			}			
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public void connectDB() {
		if(con != null){
			JOptionPane.showMessageDialog(this, "�̹� ���� ���̽ʴϴ�. ���������� �õ��ٶ��ϴ�.");
			return;
		}
		new ReLogin(this);
	}
	//Connection ������� ����
	public static void setCon(Connection conn){
		con = conn;
	}
	
	// �¼��� ���� �ʱ�ȭ�Ѵ�.
	public void initSeats(){
		getSeatsTot();
		try{	
			sql = "update Seat set reservation=0";
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(this, "�ʱ�ȭ�� �Ϸ� �߽��ϴ�");
			totNum=curNum=0;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			se.printStackTrace();
		}
		
	}
	
	// SEATS ���̺� ����� �� �¼���, �ܿ��¼��� 
	// ����  seatTot[], curNum[]�� ����. 
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
			
			System.out.println("��ü�¼���:"+totNum);
			System.out.println("�ܿ��¼���:"+curNum);
			
		}catch(SQLException se){
			System.out.println(se.getMessage());			
		}		
		
	}
	
	//�Ǹ����� ����ϴ� �޼���
	public void salesRate(){
		salesRate = 100-(int)(((double)curNum/(double)totNum)*100);
		System.out.println(salesRate);
	}
	public void progressSet(){
		//���ื�� ������ ���� �������� ����Ѵ�.
		System.out.println("���α׷��� ȣ��");
		getSeatsTot();
		salesRate();
		
		//���ื�� ����
		progress.setValue(0);
		progress.setStringPainted(true);
		Progress pr = new Progress(this);
		pr.start();
		
	}
	
}



