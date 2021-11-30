package tp5006_project;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class firstmenu extends JFrame{
	public firstmenu() {
		
		menu();
		
	}
	
	public void menu(){
		firstmenu = new JPanel(new BorderLayout());	
		setSize(450, 120); 
		
		tb = new JToolBar();
        tb.setFloatable(false); 
        
        
        tb.add(new JLabel("Enter Your Name:   ")); 
        namevalue = new JTextField(20);
        tb.add(namevalue);           
        
        
        tb.add(new JLabel("Select Your Color:   "));
        colors = new JComboBox(new Object[]{"RED","WHITE"});
        playercolor="RED";
        computercolor="WHITE";
        colors.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    playercolor = colors.getSelectedItem().toString();
                    if(playercolor=="RED") {
                    	computercolor="WHITE";
                    }else {
                    	computercolor="RED";
                    }
                   }
            }
        });
        
        play = new JButton("Play Game");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	name = namevalue.getText().toString();
            	if(!name.isEmpty() && !playercolor.isEmpty()) {
            	//System.out.println("Color: "+playercolor + " Name: "+name);
                //secondmenu();
            	}
            }
        });
        
        firstmenu.add(play,BorderLayout.SOUTH);
        tb.add(colors);        
        firstmenu.add(tb);
        add(firstmenu);	
	}
	
	private JPanel firstmenu;
    private JToolBar tb;
    private JComboBox<String> colors;
    private  JTextField namevalue;
    private JButton play;
    public String name,color,playercolor,computercolor,pointcolor;
    long startTime;
    int c,a,player;
    boolean mprosta,piso;
	
}
