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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class Reservation extends JFrame implements ActionListener{
	JTextField stdId = new JTextField(10);
	JTextField stdName = new JTextField(10);
	JTextField seatNum = new JTextField(5);
	JButton btnFind = new JButton("�˻�");
	JButton btnReservate = new JButton("����");
	JButton btnCancel = new JButton("���");
		
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	
	JLabel lbImg = new JLabel();
	
	MainFrame mf;
	Connection con;
	Statement stmt;
	ResultSet rs;

	String s_id=null;
	
	public Reservation(MainFrame mf){
		super("����");
		this.mf=mf;
		this.con=MainFrame.con;
		
		Border loweredetched;
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		
		add(p1);
		p1.add(new JLabel("�й�"));
		p1.add(stdId);
		p1.add(btnFind);
		p1.add(new JLabel("�̸�"));
		p1.add(stdName);
		stdName.setEnabled(false);
		p1.setBorder(loweredetched);
		
		
		
		if(mf.lectureRoom.equals("326")){
			ImageIcon image = new ImageIcon("seat326.png");
			lbImg.setIcon(image);
		}else if(mf.lectureRoom.equals("327")){
			ImageIcon image = new ImageIcon("seat327.png");
			lbImg.setIcon(image);
		}
		add(p2);
		p2.add(lbImg);
		
		p3.setLayout(new GridLayout(1, 6));
		p3.add(new JLabel(" "));
		p3.add(new JLabel("�¼���ȣ"));
		p3.add(seatNum);
		p3.add(btnReservate);
		p3.add(btnCancel);
		
		add("North",p1);
		add("Center", p2);
		add("South", p3);
		
		btnFind.addActionListener(this);
		btnReservate.addActionListener(this);
		btnCancel.addActionListener(this);
		
		setBounds(300, 300, 600, 515);
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				mf.progressSet();
				dispose();
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnFind){
			stdFind();
		}else if(e.getSource()==btnReservate){
			if(s_id==null){
				JOptionPane.showMessageDialog(this, "�й��� �Է����ּ���.");
			}else{
				try {
					rs=new DBConnect(seatNum.getText()).getConSeat();
					if(rs!=null){
						if(Integer.parseInt(rs.getString("reservation"))==0){
							String sql = "UPDATE Seat SET reservation="+s_id
									+" WHERE seatId="+rs.getString("seatId")+" AND lrnum="+mf.lectureRoom;
							System.out.println(sql);
							stmt = con.createStatement();
							stmt.executeUpdate(sql);
							JOptionPane.showMessageDialog(this, "����Ǿ����ϴ�.");
						}
						else{
							JOptionPane.showMessageDialog(this, "����Ǿ��ִ� �¼��Դϴ�.");
						}
					}else{
						JOptionPane.showMessageDialog(this, "�¼��� �����ϴ�.");
					}
				} catch (SQLException e1) {
					// TODO �ڵ� ������ catch ���
					e1.printStackTrace();
				}
			}
		}else if(e.getSource()==btnCancel){
			mf.progressSet();
			this.dispose();
		}
		else this.dispose();
	}
	public void stdFind(){
		try {
			DBConnect db=new DBConnect(stdId.getText());
			if(db.getConRSeat()==null){
				s_id=stdId.getText();
				rs=db.findStd();
				if(rs!=null){
					stdName.setText(rs.getString("stdName"));
				}
				else stdName.setText("X");
			}else{
				JOptionPane.showMessageDialog(this, "�����ϼ̽��ϴ�.");
			}
		} catch (SQLException e1) {
			// TODO �ڵ� ������ catch ���
			e1.printStackTrace();
		}
		
	}
	
}


