package com.reservate;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Login extends JFrame implements ActionListener{
	JTextField tfId = new JTextField(10);
	JTextField tfPwd = new JTextField(10);
	JButton btnLogin = new JButton("�α���");
	JButton btnCancel = new JButton("���");
		
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	
	Connection con;
	
	public Login() {
		super("�α���");
		setLayout(new FlowLayout());
		p1.add(new JLabel("�й�"));
		p1.add(tfId);
		
		p2.add(new JLabel("�̸�"));
		p2.add(tfPwd);
		
		p3.add(btnLogin);
		p3.add(btnCancel);
		
		add(p1);
		add(p2);
		add(p3);
				
		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		
		setBounds(500, 300, 200, 150);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==btnLogin){
			DBConnect db=new DBConnect(tfId.getText(),tfPwd.getText());
			ResultSet rs;
			try {
				rs = db.getConStd();
				if(rs!=null){
					new MainFrame(db.conn);
					this.dispose();
				}else{
					JOptionPane.showMessageDialog(this, "������ �����ϴ�.");
				}
			} catch (SQLException e1) {
				// TODO �ڵ� ������ catch ���
				e1.printStackTrace();
			}
		}else{
			this.dispose();
		}
	}
}
public class LoginPage {
	public static void main(String[] args) {
		new Login();
	}
}
