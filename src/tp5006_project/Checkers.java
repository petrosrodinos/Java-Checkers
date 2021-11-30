package tp5006_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.util.*;


public class Checkers extends JFrame{
	
	public Checkers() {
		super("Checkers tp5006/5016");
		firstmenu();
		//secondmenu();
		
    }
	
	public void firstmenu() {
		
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
                secondmenu();
            	}
            }
        });
        
        firstmenu.add(play,BorderLayout.SOUTH);
        tb.add(colors);        
        firstmenu.add(tb);
        add(firstmenu);	
	}
	
	public void secondmenu() {
		
		firstmenu.setVisible(false);
		logarea = new JTextArea(10, 10);
		//scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel = new JPanel(new BorderLayout());
		jPanel1 = new JPanel(new GridLayout(8, 8, 0, 0));
        JLabel playername = new JLabel();
        setSize(650, 600);
        pa = new JPanel[8][8];
        
        red = new ImageIcon(getClass().getResource("/img/pr.png"));
        white = new ImageIcon(getClass().getResource("/img/pw.png"));
        red2 = new ImageIcon(getClass().getResource("/img/kr.png"));
        white2 = new ImageIcon(getClass().getResource("/img/kw.png"));
        
        startTime = System.nanoTime();
        
        int pb=0;
        player = 1; //poios paixtis paizei kathe fora
        final int[] prev = new int[2];  //proigoumeni thesi kinisis
        final int[][] point = new int[8][8]; //pinakas pou apothikeuei xroma kathe paixti
        final int[][] color = new int[8][8]; //pinakas gia kathe xroma(skouro,anoixto)
        List<String> events = new ArrayList<>(); //lista gia ta events
        pointcolor="";
        a = 0;
        c=0; //metavliti gia na elexeis ta click kathe paixti(proto click epilegei pouli deutero kinisi)
//      playercolor="WHITE";
//      computercolor="RED";
//      name="petros";    
        
        for(int i = 0; i < point.length; i++) {
        	for(int j = 0; j < point.length; j++) {
             point[i][j] = 0;
        	}
        }
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JPanel p = new JPanel();

                p.setName("Panel" + i + "_" + j);
                p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (pb%2 != 0) { 
                	color[i][j]=0; //prasino
                    p.setBackground(new Color(102, 204, 0));
                    if(i!=3 && i!=4 && i<5) {
                    	p.add(new JLabel(red));
                    	point[i][j]=1;
                    }
                    if(i<=7 && i>=5) {
                    	p.add(new JLabel(white));
                    	point[i][j]=2;
                    }
                    
                }else {
                	p.setBackground(new Color(255, 255, 153));
                	color[i][j]=1; //kitrino
                }              
                
                
                pb++;
                if(a==0) {
                events.add("Timer Started");
                a=1;
                }
                playername.setText(" "+name + " is Playing");

                p.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    	JPanel p = (JPanel) e.getSource();                        
                        String s = p.getName();
                        String[] numbers = s.replaceAll("^\\D+", "").split("\\D+");
                        int x = Integer.valueOf(numbers[0]);
                        int y = Integer.valueOf(numbers[1]);
                        System.out.println("X:"+x+ " Y:"+y);
                        
                        
                       if(endmatch(point)=="") { //elexei an teleiose to game
                          if(player==1) { //players code
 
                        	if(c==0){ //proto click
                        		
	                    		if(checkpoint(x,y,point)==playercolor) { //an to pouli einai to xroma toy paixti
	                    			pointcolor=playercolor;
	                    			prev[0]=x;
	                    			prev[1]=y;
	                    			c=1;
	                    		}else if(checkpoint(x,y,point)=="EMPTY"){
	                    			c=0;
	                    		}
	                    		
	                    		
	                    		
	                           }else if(c==1){ //deutero click
	                        	   
	                        	   if(playercolor=="RED") {  //elegxei diagonious gia kathe paixti
	                        		   mprosta = x==prev[0]+1 && (y==prev[1]+1 || y==prev[1]-1);
	                        		   piso = x==prev[0]-1 && (y==prev[1]+1 || y==prev[1]-1);
		                    	   }else {
		                    		   mprosta = x==prev[0]-1 && (y==prev[1]+1 || y==prev[1]-1);
		                    		   piso = x==prev[0]+1 && (y==prev[1]+1 || y==prev[1]-1);
		                    		}
	                        	   
		                    		if(checkpoint(x,y,point)=="EMPTY" && checkcolor(x,y,color) && mprosta){ //gia na proxorisei otan einai keno
		                    			if(pointcolor==playercolor) {
		                    				
		                    				if(playercolor=="RED") {
		                    					l = new JLabel(red);
		                    					point[x][y]=1;
		                    				}else {
		                    					l = new JLabel(white);
		                    					point[x][y]=2;
		                    				}		                    				 
		                    				p.add(l);
		                    				events.add("\n"+ name + " Moved to: "+String.valueOf(x) + ":"+String.valueOf(y) + "\n");
		                    				
		                    				JPanel pc=pa[prev[0]][prev[1]];
		                    				JLabel lc=(JLabel) pc.getComponent(0);
		                    				lc.setIcon(null);
		                    				point[prev[0]][prev[1]]=0;
		                    				
		                    			}
		                    			
		                    			playername.setText(" Computer is Playing");
		                    			player=2;
		                    			p.revalidate();
		                    			
		                    		}else if(checkpoint(x,y,point)!="EMPTY" && checkpoint(x,y,point)==computercolor  && checkcolor(x,y,color) && (piso || mprosta)){ //otan iparxei antipalos mprosta i piso
		                    			//System.out.println("exei piso i mprosta");
		                    			
		                    			JPanel pc=pa[prev[0]][prev[1]];        //feugei tou player
	                    				JLabel lc=(JLabel) pc.getComponent(0);
	                    				lc.setIcon(null);
	                    				point[prev[0]][prev[1]]=0;
	                    				
	                    				JPanel pc1=pa[x][y];                 //feugei tou computer
	                    				JLabel lc1=(JLabel) pc1.getComponent(0);
	                    				lc1.setIcon(null);
	                    				point[x][y]=0;
	                    				
	                    				if(playercolor=="RED") {         //metakinite tou player stin thesi tou computer
	                    					l = new JLabel(red);
	                    					point[x][y]=1;
	                    				}else {
	                    					l = new JLabel(white);
	                    					point[x][y]=2;
	                    				}		                    				 
	                    				p.add(l);
	                    				events.add("\n"+ name + " took a pouli from Computer\n at "+String.valueOf(x)+":"+String.valueOf(y)+"\n");
	                    				
	                    				
		                    		}
		                    		playername.setText(" Computer is Playing");
	                    			player=2;
	                    			p.revalidate();
	                    			c=0;
		                            
		                    	}
                        	  
                        	  }else {//computers code
                        		  if(c==0){  //proto click                  		
      	                    		if(checkpoint(x,y,point)==computercolor) {
      	                    			pointcolor=computercolor;
      	                    			prev[0]=x;
      	                    			prev[1]=y;
      	                    			c=1;
      	                    		}else if(checkpoint(x,y,point)=="EMPTY"){
      	                    			c=0;
      	                    		}
      	                    		
      	                           }else if(c==1){  //deutero click
      	                        	   
      	                        	 if(playercolor=="RED") {  //elegxei diagonious gia kathe paixti
      	                        		mprosta = x==prev[0]-1 && (y==prev[1]+1 || y==prev[1]-1) ;
      	                        		piso = x==prev[0]+1 && (y==prev[1]+1 || y==prev[1]-1);
		                    		}else {
		                    			mprosta = x==prev[0]+1 && (y==prev[1]+1 || y==prev[1]-1);
		                    			piso = x==prev[0]-1 && (y==prev[1]+1 || y==prev[1]-1);
		                    		}
      	                        	   
      	                        	 if(checkpoint(x,y,point)=="EMPTY" && checkcolor(x,y,color) && mprosta) {
      		                    			if(pointcolor==computercolor) {
      		                    				
      		                    				if(computercolor=="RED") {
      		                    					l = new JLabel(red);
      		                    					point[x][y]=1;
      		                    				}else {
      		                    					l = new JLabel(white);
      		                    					point[x][y]=2;
      		                    				}		                    				 
      		                    				p.add(l);
      		                    				events.add("Computer Moved to: "+String.valueOf(x) + ":"+String.valueOf(y));
      		                    				JPanel pc=pa[prev[0]][prev[1]];
      		                    				JLabel lc=(JLabel) pc.getComponent(0);
      		                    				lc.setIcon(null);
      		                    				
      		                    				
      		                    				point[prev[0]][prev[1]]=0;
      		                    				
      		                    			}
      		                    			playername.setText(" "+name + " is Playing");
      		                    			player=1;
      		                    			p.revalidate();
      		                    			
      		                    			
      		                    		}else if( checkpoint(x,y,point)!="EMPTY" && checkpoint(x,y,point)==playercolor && checkcolor(x,y,color)  && (piso || mprosta)){ //otan iparxei antipalos mprosta i piso
    		                    			//System.out.println("exei piso i mprosta");
    		                    			
    		                    			JPanel pc=pa[prev[0]][prev[1]];        //feugei tou player
    	                    				JLabel lc=(JLabel) pc.getComponent(0);
    	                    				lc.setIcon(null);
    	                    				point[prev[0]][prev[1]]=0;
    	                    				
    	                    				JPanel pc1=pa[x][y];                 //feugei tou computer
    	                    				JLabel lc1=(JLabel) pc1.getComponent(0);
    	                    				lc1.setIcon(null);
    	                    				point[x][y]=0;
    	                    				
    	                    				if(computercolor=="RED") {         //metakinite tou computer stin thesi tou player
    	                    					l = new JLabel(red);
    	                    					point[x][y]=1;
    	                    				}else {
    	                    					l = new JLabel(white);
    	                    					point[x][y]=2;
    	                    				}		                    				 
    	                    				p.add(l);
    	                    				events.add("Computer took a pouli from " + name +"\n at "+String.valueOf(x)+":"+String.valueOf(y)+"\n");
    		                    			
    		                    			
    		                    		} 
      	                        	 	playername.setText(" "+name + " is Playing");
			                    		player=1;
			                    		p.revalidate();
			                    		c=0;
      		                    	}
	                    	}
                        }

                      }
                    });
                 
                
                pa[i][j] = p;
                jPanel1.add(p);


            }
            pb++;
        }
        
       if(endmatch(point)!="") {
    	   if(endmatch(point)==playercolor) {
    	    	events.add("Winner is "+name+"\n");
    	    }else {
    	    	events.add("Winner is Computer\n");
    	    }
    	   long stopTime = System.nanoTime();
	  	   events.add("Game lasted for: "+String.valueOf((stopTime - startTime)/1000000000)+" seconds");
       }
      
    
        logarea.setMargin(new Insets(3, 3, 3, 3));
        logarea.setEditable(false);
        logarea.setVisible(false);
        Border border = BorderFactory.createLineBorder(Color.RED);
        logarea.setBorder(border);
        Font font = new Font("Verdana", Font.BOLD, 15);
        logarea.setFont(font);
        
                      
        playername.setFont(new Font("Verdana", Font.BOLD, 25));
        playername.setPreferredSize(new Dimension(50, 50));
        playername.setForeground(new Color(51, 153, 255));
        
        mb = new JMenuBar();       
        
        game = new JMenu("Game");
        newgame = new JMenuItem("New Game");
        newgame.addActionListener(new ActionListener() {  //new game
            @Override
            public void actionPerformed(ActionEvent e) {
               panel.setVisible(false);
               firstmenu();
            }
        });
        restart = new JMenuItem("Restart");
        restart.addActionListener(new ActionListener() {  //restart
            @Override
            public void actionPerformed(ActionEvent e) {
            	panel.setVisible(false);
            	secondmenu();
              }
        });
        exit = new JMenuItem("Exit");                       //exit
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }
        });

        options = new JMenu("Options");
        show = new JMenuItem("Show History");
        hide = new JMenuItem("Hide History");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	logarea.setVisible(true);
            	logarea.setText(events.toString().replace("[", "").replace("]", "").replace(",", ""));
            	setSize(800, 600);
            }
        });
        hide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	logarea.setVisible(false);
            	setSize(650, 600);
            }
        });
        
        help = new JMenu("Help");
        about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        game.add(newgame);
        game.add(restart);
        game.add(exit);        
        options.add(show);
        options.add(hide);      
        help.add(about);        
        mb.add(game);
        mb.add(help);        
        mb.add(options);        
        setJMenuBar(mb);
       
        
        panel.add(logarea);
        panel.add(jPanel1);
        panel.add(mb,BorderLayout.NORTH);
        panel.add(playername,BorderLayout.SOUTH); 
        panel.add(logarea,BorderLayout.EAST);
        
       
        add(panel);                
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	}
	
	public String checkpoint(int x,int y,int point[][]) { //elexei to xroma gia kathe pouli pou patiete
		if(point[x][y]==1) {
			return "RED";                      	
        }else if(point[x][y]==2){                      	
        	return "WHITE";
        }else {
        	return "EMPTY";
        }
	}
	
	public boolean checkcolor(int x,int y,int color[][]) { //elexei to xroma toy tetragonoy
		if(color[x][y]==0) {
			return true;
        }else {
        	return false;
        }
        
	}
	
	public String endmatch(int point[][]){  //elexei poios nikise
		int a=0;
		int b=0;
		for(int i=0;i<point.length; i++) {
			for(int j=0;j<point.length; j++) {
				if(point[i][j]==1) {
					a=1;
				}
				if(point[i][j]==2) {
					b=1;
				}
			}
		}
		if(a==0 && b==1) {
			return "WHITE";
		}else if(b==0 && a==1){
			return "RED";
		}else {
			return "";
		}
	}
	
	
public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Checkers().setVisible(true);
            }
        });
    }
    
    private JPanel jPanel1,panel,firstmenu;
    private JPanel[][] pa;
    private JMenuBar mb;
    private JMenu game,options,help;
    private JMenuItem newgame,restart,exit,about,show,hide;
    private JToolBar tb;
    private JComboBox<String> colors;
    private  JTextField namevalue;
    private JTextArea logarea;
    private JButton play;
    private JScrollPane scroll;
    private ImageIcon red,white,red2,white2;
    public String name,color,playercolor,computercolor,pointcolor;
    private JLabel l;
    long startTime;
    int c,a,player;
    boolean mprosta,piso;
    

	
	
}

