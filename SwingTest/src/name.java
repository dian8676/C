import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.TextArea;
import java.awt.TextField;

public class name{
    public static void main(String[] args){
    	Frame f=new Frame("SWING ����");
    	f.setLayout(new FlowLayout());
    	
    	Checkbox c=new Checkbox("���");
    	f.add(c);
    	Checkbox c1=new Checkbox("����");
    	f.add(c1);
    	Checkbox c2=new Checkbox("��");
    	f.add(c2);
    	f.add(new Label("       "));
    	CheckboxGroup crg=new CheckboxGroup();
    	f.add(new Checkbox("����",crg,true));
    	f.add(new Checkbox("����",crg,false));
    	f.add(new Label("       "));
    	Choice d=new Choice();
    	d.add("�ڹ�");
    	d.add("����");
    	f.add(d);
    	f.add(new Label("                 "));
    	f.add(new Label("�����ϴ� ���ø� �����Ͻÿ�"));
    	f.add(new Label("                 "));
    	List e=new List(4,true);
    	e.add("����");
    	e.add("�λ�");
    	e.add("����");
    	e.add("����");
        f.add(e);
        TextField f0=new TextField("Enter Name",40);
        f.add(f0);
        TextArea f1=new TextArea("MEMO",4,50);
        TextField f2=new TextField();
        f1.append(f2.getText());
        f.add(f1);
        Button b=new Button("Ȯ��");
    	f.add(b);
    	f.setSize(400,330);
    	f.setVisible(true);
    }
    
}