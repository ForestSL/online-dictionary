package Client;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DicPanel extends JFrame{
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	private boolean bd,yd,by;
	private int bdNum,ydNum,byNum;
	private String bdExp,ydExp,byExp;
	private String sWord;
	private ImageIcon likeIcon=new ImageIcon("image/like.gif");
	private ImageIcon unlikeIcon=new ImageIcon("image/unlike.gif");
	//private JFrame handle=this;

	private InputPanel inputPanel=new InputPanel();
	private OutPutPanel outputPanel=new OutPutPanel();
//	private ButtonPanel buttonPanel=new ButtonPanel();


	public DicPanel(DataInputStream fs,DataOutputStream ts){
		fromServer=fs;
		toServer=ts;
		
		//add inputPanel outputPanel tipPanel to the frame
		setTitle("Online Dicionary");
		setSize(700,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new BorderLayout(3,3));
		add(inputPanel,BorderLayout.NORTH);
		add(outputPanel,BorderLayout.CENTER);
//		add(buttonPanel,BorderLayout.SOUTH);
	}

	class InputPanel extends JPanel{
		public InputPanel(){
			setLayout(new GridLayout(3, 1));
			setBackground(new Color(12,160,223));
			//setBackground(Color.WHITE);
			
			JPanel title=new JPanel();
			title.setBackground(Color.WHITE);
			title.setLayout(new FlowLayout(FlowLayout.CENTER));
			title.add(new JLabel("My Dictionary\n"));
			
			add(title);
			add(new SearchPanel());
			add(new CheckJPanel());
		}
	}
	
	class CheckJPanel extends JPanel{
		//create check boxes and labels here
		private JCheckBox JCHKbd=new JCheckBox("百度");
		private JCheckBox JCHKyd=new JCheckBox("有道");
		private JCheckBox JCHKby=new JCheckBox("必应");
		
		public CheckJPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new FlowLayout(FlowLayout.LEFT,95,5));
			setBackground(Color.WHITE);
			JCHKbd.setBackground(Color.WHITE);
			JCHKyd.setBackground(Color.WHITE);
			JCHKby.setBackground(Color.WHITE);
			bd=yd=by=false;
			add(JCHKbd);
			add(JCHKyd);
			add(JCHKby);
			
		
		JCHKbd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				bd=JCHKbd.isSelected();
			}		
		});
		JCHKyd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				yd=JCHKyd.isSelected();
			}		
		});
		JCHKby.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				by=JCHKby.isSelected();
			}		
		});
		
		}
		
	}
	class SearchPanel extends JPanel{
		private JLabel inp=new JLabel("Input");
		private JTextField jtfInputText=new JTextField("Enter word here");
		private JButton jbtSearch=new JButton("Search",new ImageIcon("image/search.gif"));
		private boolean start=false;
		
		public SearchPanel(){
			setLayout(new FlowLayout(FlowLayout.LEFT,40,5));
			setBackground(Color.WHITE);
			jtfInputText.setPreferredSize(new Dimension(320, 30));
			
			jbtSearch.setPreferredSize(new Dimension(180, 30));
			jbtSearch.setBackground(Color.WHITE);
			jbtSearch.setBorder(null);
			
			add(inp);
			add(jtfInputText);
			add(jbtSearch);

			jtfInputText.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					if(start==false){
						jtfInputText.setText(null);
						start=true;
					}
					
				}
			});
			jtfInputText.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e){
					switch(e.getKeyCode()){
						case KeyEvent.VK_ENTER:{//reaction to enter--search
							String info=jtfInputText.getText();
							//send word to server
							try {
								if(IsWord(info)==false) return;
								outputPanel.fOPT.JBlike.setIcon(unlikeIcon);
								outputPanel.sOPT.JBlike.setIcon(unlikeIcon);
								outputPanel.tOPT.JBlike.setIcon(unlikeIcon);
								sWord=info;
								toServer.writeUTF(3+" "+info+" 0 0 0");
								toServer.flush();
								String tp=fromServer.readUTF(); 
								toServer.writeUTF(2+" "+info);
								toServer.flush();
								
								//get return tips from server
								info=fromServer.readUTF(); 
								//show info on outPutpanel
								//info="3!!!!1!!!!2!!!!exp3!!!!exp1!!!!exp2";
								String[] infoArr=new String[6];
								infoArr=info.split("!!!!");
								bdNum=Integer.parseInt(infoArr[0]);
								ydNum=Integer.parseInt(infoArr[1]);
								byNum=Integer.parseInt(infoArr[2]);
								bdExp=infoArr[3];
								ydExp=infoArr[4];
								byExp=infoArr[5];
								outputPanel.fOPT.setVisible(false);
								outputPanel.sOPT.setVisible(false);
								outputPanel.tOPT.setVisible(false);
								if(bdNum>=ydNum&&bdNum>=byNum){
									outputPanel.fOPT.JTFexp.setText(bdExp);
									outputPanel.fOPT.JBlike.setText(bdNum+"    ");
									if(bd)outputPanel.fOPT.setVisible(true);
									if(ydNum>=byNum){
										if(yd)outputPanel.sOPT.setVisible(true);
										if(by)outputPanel.tOPT.setVisible(true);
										outputPanel.sOPT.JTFexp.setText(ydExp);outputPanel.tOPT.JTFexp.setText(byExp);
										outputPanel.sOPT.JBlike.setText(ydNum+"    ");outputPanel.tOPT.JBlike.setText(byNum+"    ");
										}
									else{
										if(by)outputPanel.sOPT.setVisible(true);
										if(yd)outputPanel.tOPT.setVisible(true);
										outputPanel.sOPT.JTFexp.setText(byExp);outputPanel.fOPT.JTFexp.setText(ydExp);
										outputPanel.sOPT.JBlike.setText(byNum+"    ");outputPanel.tOPT.JBlike.setText(ydNum+"    ");
										}
								}
								else if(byNum>=ydNum&&byNum>=bdNum){
									outputPanel.fOPT.JTFexp.setText(byExp);
									outputPanel.fOPT.JBlike.setText(byNum+"    ");
									if(by)outputPanel.fOPT.setVisible(true);
									if(ydNum>=bdNum){
										if(yd)outputPanel.sOPT.setVisible(true);if(bd)outputPanel.tOPT.setVisible(true);
										outputPanel.sOPT.JTFexp.setText(ydExp);outputPanel.tOPT.JTFexp.setText(bdExp);
										outputPanel.sOPT.JBlike.setText(ydNum+"    ");outputPanel.tOPT.JBlike.setText(bdNum+"    ");
										}
									else{
										if(bd)outputPanel.sOPT.setVisible(true);if(yd)outputPanel.tOPT.setVisible(true);
										outputPanel.sOPT.JTFexp.setText(bdExp);outputPanel.fOPT.JTFexp.setText(ydExp);
										outputPanel.sOPT.JBlike.setText(bdNum+"    ");outputPanel.tOPT.JBlike.setText(ydNum+"    ");
										}
								}
								else if(ydNum>=bdNum&&ydNum>=byNum){
									outputPanel.fOPT.JTFexp.setText(ydExp);
									outputPanel.fOPT.JBlike.setText(ydNum+"    ");
									if(yd)outputPanel.fOPT.setVisible(true);
									if(bdNum>=byNum){
										if(bd)outputPanel.sOPT.setVisible(true);if(by)outputPanel.tOPT.setVisible(true);
										outputPanel.sOPT.JTFexp.setText(bdExp);outputPanel.tOPT.JTFexp.setText(byExp);
										outputPanel.sOPT.JBlike.setText(bdNum+"    ");outputPanel.tOPT.JBlike.setText(byNum+"    ");
										}
									else{
										if(by)outputPanel.sOPT.setVisible(true);if(bd)outputPanel.tOPT.setVisible(true);
										outputPanel.sOPT.JTFexp.setText(byExp);outputPanel.fOPT.JTFexp.setText(bdExp);
										outputPanel.sOPT.JBlike.setText(byNum+"    ");outputPanel.tOPT.JBlike.setText(bdNum+"    ");
										}
								}
							} catch (IOException ex) {
								System.err.println(ex);
							}
						}
					}
				}
			});
		
			jbtSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String info=jtfInputText.getText();
					//send word to server
					try {
						if(IsWord(info)==false) return;
						outputPanel.fOPT.JBlike.setIcon(unlikeIcon);
						outputPanel.sOPT.JBlike.setIcon(unlikeIcon);
						outputPanel.tOPT.JBlike.setIcon(unlikeIcon);
						sWord=info;
						toServer.writeUTF(3+" "+info+" 0 0 0");
						toServer.flush();
						String tp=fromServer.readUTF(); 
						toServer.writeUTF(2+" "+info);
						toServer.flush();
						
						//get return tips from server
						info=fromServer.readUTF(); 
						//show info on outPutpanel
						//info="3!!!!1!!!!2!!!!exp3!!!!exp1!!!!exp2";
						String[] infoArr=new String[6];
						infoArr=info.split("!!!!");
						bdNum=Integer.parseInt(infoArr[0]);
						ydNum=Integer.parseInt(infoArr[1]);
						byNum=Integer.parseInt(infoArr[2]);
						bdExp=infoArr[3];
						ydExp=infoArr[4];
						byExp=infoArr[5];
						outputPanel.fOPT.setVisible(false);
						outputPanel.sOPT.setVisible(false);
						outputPanel.tOPT.setVisible(false);
						if(bdNum>=ydNum&&bdNum>=byNum){
							outputPanel.fOPT.JTFexp.setText(bdExp);
							outputPanel.fOPT.JBlike.setText(bdNum+"    ");
							if(bd)outputPanel.fOPT.setVisible(true);
							if(ydNum>=byNum){
								if(yd)outputPanel.sOPT.setVisible(true);
								if(by)outputPanel.tOPT.setVisible(true);
								outputPanel.sOPT.JTFexp.setText(ydExp);outputPanel.tOPT.JTFexp.setText(byExp);
								outputPanel.sOPT.JBlike.setText(ydNum+"    ");outputPanel.tOPT.JBlike.setText(byNum+"    ");
								}
							else{
								if(by)outputPanel.sOPT.setVisible(true);
								if(yd)outputPanel.tOPT.setVisible(true);
								outputPanel.sOPT.JTFexp.setText(byExp);outputPanel.fOPT.JTFexp.setText(ydExp);
								outputPanel.sOPT.JBlike.setText(byNum+"    ");outputPanel.tOPT.JBlike.setText(ydNum+"    ");
								}
						}
						else if(byNum>=ydNum&&byNum>=bdNum){
							outputPanel.fOPT.JTFexp.setText(byExp);
							outputPanel.fOPT.JBlike.setText(byNum+"    ");
							if(by)outputPanel.fOPT.setVisible(true);
							if(ydNum>=bdNum){
								if(yd)outputPanel.sOPT.setVisible(true);if(bd)outputPanel.tOPT.setVisible(true);
								outputPanel.sOPT.JTFexp.setText(ydExp);outputPanel.tOPT.JTFexp.setText(bdExp);
								outputPanel.sOPT.JBlike.setText(ydNum+"    ");outputPanel.tOPT.JBlike.setText(bdNum+"    ");
								}
							else{
								if(bd)outputPanel.sOPT.setVisible(true);if(yd)outputPanel.tOPT.setVisible(true);
								outputPanel.sOPT.JTFexp.setText(bdExp);outputPanel.fOPT.JTFexp.setText(ydExp);
								outputPanel.sOPT.JBlike.setText(bdNum+"    ");outputPanel.tOPT.JBlike.setText(ydNum+"    ");
								}
						}
						else if(ydNum>=bdNum&&ydNum>=byNum){
							outputPanel.fOPT.JTFexp.setText(ydExp);
							outputPanel.fOPT.JBlike.setText(ydNum+"    ");
							if(yd)outputPanel.fOPT.setVisible(true);
							if(bdNum>=byNum){
								if(bd)outputPanel.sOPT.setVisible(true);if(by)outputPanel.tOPT.setVisible(true);
								outputPanel.sOPT.JTFexp.setText(bdExp);outputPanel.tOPT.JTFexp.setText(byExp);
								outputPanel.sOPT.JBlike.setText(bdNum+"    ");outputPanel.tOPT.JBlike.setText(byNum+"    ");
								}
							else{
								if(by)outputPanel.sOPT.setVisible(true);if(bd)outputPanel.tOPT.setVisible(true);
								outputPanel.sOPT.JTFexp.setText(byExp);outputPanel.fOPT.JTFexp.setText(bdExp);
								outputPanel.sOPT.JBlike.setText(byNum+"    ");outputPanel.tOPT.JBlike.setText(bdNum+"    ");
								}
						}
					} catch (IOException ex) {
						System.err.println(ex);
					}
				}

	});	
				
			
		}
	}

	class OutPutPanel extends JPanel{
		public JPexp fOPT=new JPexp("exp1",3);
		public JPexp sOPT=new JPexp("exp2",2);
		public JPexp tOPT=new JPexp("exp3",1);
		public OutPutPanel(){
			setBackground(new Color(221,231,253));
			add(fOPT);
			add(sOPT);
			add(tOPT);
		}
	}
	class JPexp extends JPanel{
		public JTextField JTFexp=new JTextField("EXP here");
		public JButton JBlike=new JButton("num",unlikeIcon);
		private boolean likeMark=false;
		public JPexp(String exp,int no){
			setLayout(new BorderLayout());
			setBackground(Color.WHITE);
			setBorder(new LineBorder(new Color(127,157,185), 1, false));
			setVisible(false);
			setPreferredSize(new Dimension(600, 80));
			JTFexp.setSize(350,40);
			JTFexp.setBackground(Color.WHITE);
			JTFexp.setBorder(null);
			JTFexp.setEditable(false);
			JBlike.setBackground(Color.WHITE);
			JBlike.setSize(20, 7);
			JBlike.setBorder(null);
			JBlike.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					likeMark=!likeMark;
					String likeInfo;
					if(likeMark){
						JBlike.setIcon(likeIcon);
						String[] noTp=JBlike.getText().split(" ");
						int no=1+Integer.valueOf(noTp[0]);
						JBlike.setText(no+"    ");
						
						//send like change to server
						
						String type=JTFexp.getText();
						if(type.indexOf("百度")!=-1)	likeInfo="3 "+sWord+" 1 0 0";
						else if(type.indexOf("有道")!=-1)	likeInfo="3 "+sWord+" 0 1 0";
						else	likeInfo="3 "+sWord+" 0 0 1";
						try {
							//send login info to server
							toServer.writeUTF(likeInfo);
							toServer.flush();
							
							//get return tips from server
							likeInfo=fromServer.readUTF();		
							
						} catch (IOException ex) {
							System.err.println(ex);
						}
					}
					else{
						JBlike.setIcon(unlikeIcon);
						String[] noTp=JBlike.getText().split(" ");
						int no=-1+Integer.valueOf(noTp[0]);
						JBlike.setText(no+"    ");

						//send like change to server
						String type=JTFexp.getText();
						if(type.indexOf("百度")!=-1)	likeInfo="3 "+sWord+" -1 0 0";
						else if(type.indexOf("有道")!=-1)	likeInfo="3 "+sWord+" 0 -1 0";
						else	likeInfo="3 "+sWord+" 0 0 -1";
						try {
							//send login info to server
							toServer.writeUTF(likeInfo);
							toServer.flush();
							
							//get return tips from server
							likeInfo=fromServer.readUTF();		
							
						} catch (IOException ex) {
							System.err.println(ex);
						}
					}
					
				}
			});
			
			JTFexp.setText(exp);
			JBlike.setText(no+"    ");
			add(JTFexp,BorderLayout.CENTER);
			add(JBlike,BorderLayout.EAST);
		}
	}
	public static boolean IsWord(String str) {
		String pattern = "[A-Za-z1-9-]+";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		return m.matches();
	}
}