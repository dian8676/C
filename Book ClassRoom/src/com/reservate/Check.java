package com.reservate;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Check extends JFrame implements ActionListener{
	JPanel pan1=new JPanel();
	JPanel pan2=new JPanel();
	JPanel pan3=new JPanel();
	JPanel pan4=new JPanel();
	JPanel pan5=new JPanel();

	JTextField stdId=new JTextField(10);//학번입력
	JTextField lecRoom=new JTextField(5);
	JTextField seatId=new JTextField(5);	
	
	JButton btnFind=new JButton("검색");//검색버튼
	JButton btnOk=new JButton("확인");

	ResultSet rs;
	
	public Check(MainFrame mf){
		super("조회");
		
		setLayout(new FlowLayout());
		
		pan1.add(new JLabel("학번   "));
		pan1.add(stdId);
		pan1.add(btnFind);
				
		pan2.add(new JLabel("================================"));
		
		pan3.add(new JLabel("강의실 "));
		pan3.add(lecRoom);
		lecRoom.setEditable(false);
		pan4.add(new JLabel("좌석 "));
		pan4.add(seatId);
		seatId.setEditable(false);
		pan5.add(btnOk);
		
		add(pan1);
		add(pan2);
		add(pan3);
		add(pan4);
		add(pan5);
		
		btnFind.addActionListener(this);
		btnOk.addActionListener(this);
				
		setBounds(500,300,300,200);
		setVisible(true);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){				
				dispose();
			}
		});
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnFind){ //검색버튼
			try {
				rs = new DBConnect(stdId.getText()).getConRSeat();
				if(rs!=null){
					lecRoom.setText(rs.getString("lrnum"));
					seatId.setText(rs.getString("seatid"));
				}
				else{
					lecRoom.setText("x");
					seatId.setText("x");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}			
		}else if(e.getSource()==btnOk){//확인버튼
			this.dispose();
		}
		
	}
}





