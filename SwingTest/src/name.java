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
    	Frame f=new Frame("SWING 연습");
    	f.setLayout(new FlowLayout());
    	
    	Checkbox c=new Checkbox("사과");
    	f.add(c);
    	Checkbox c1=new Checkbox("딸기");
    	f.add(c1);
    	Checkbox c2=new Checkbox("귤");
    	f.add(c2);
    	f.add(new Label("       "));
    	CheckboxGroup crg=new CheckboxGroup();
    	f.add(new Checkbox("여자",crg,true));
    	f.add(new Checkbox("남자",crg,false));
    	f.add(new Label("       "));
    	Choice d=new Choice();
    	d.add("자바");
    	d.add("스윙");
    	f.add(d);
    	f.add(new Label("                 "));
    	f.add(new Label("좋아하는 도시를 선택하시오"));
    	f.add(new Label("                 "));
    	List e=new List(4,true);
    	e.add("서울");
    	e.add("부산");
    	e.add("대전");
    	e.add("전주");
        f.add(e);
        TextField f0=new TextField("Enter Name",40);
        f.add(f0);
        TextArea f1=new TextArea("MEMO",4,50);
        TextField f2=new TextField();
        f1.append(f2.getText());
        f.add(f1);
        Button b=new Button("확인");
    	f.add(b);
    	f.setSize(400,330);
    	f.setVisible(true);
    }
    
}