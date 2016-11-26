package Client;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/*
public class DicPanel extends JFrame{
	private DataInputStream fromServer;
	private DataOutputStream toServer;

	private InputPanel inputPanel=new InputPanel();
	private OutputPanel outputPanel=new OutputPanel();
	private TipPanel tipPanel=new TipPanel();

	private int pass_num;
	private int next_num;
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
		//setBackground(Color.BLUE);
		add(inputPanel,BorderLayout.NORTH);
		add(outputPanel,BorderLayout.EAST);
		add(tipPanel,BorderLayout.WEST);
	}


	class InputPanel extends JPanel{
		private JTextField jtfInputText=new JTextField("输入要查询的单词或词组");
		private JButton jbtSearch=new JButton("查词",new ImageIcon("image/search.gif"));
		private boolean start=false;
		public InputPanel(){
			setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
			setBackground(Color.WHITE);
			jtfInputText.setPreferredSize(new Dimension(500, 30));
			jbtSearch.setPreferredSize(new Dimension(60, 30));
			jbtSearch.setBackground(Color.WHITE);
			jbtSearch.setBorder(null);
			add(jtfInputText);
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
							String x=jtfInputText.getText();
							outputPanel.jtaExplanation.setText(x+"\n");
							String res=dict.search(x);
							if(res.equals("$")){
								outputPanel.jtaExplanation.append("    不存在该单词.\n\n");
								outputPanel.jtaExplanation.append("    您是否要找： ");
								String correctWords=dict.check(x);
								outputPanel.jbtCorrect.setText(correctWords);
								outputPanel.jbtCorrect.setEnabled(true);
								outputPanel.jbtCorrect.setVisible(true);
							}
							else {
								String[] outlines=res.split("\\\\t");
								outputPanel.jtaExplanation.append("\n[解释]");
								for(int i=0;i<outlines.length;i++){
									outputPanel.jtaExplanation.append("     "+outlines[i]+"  ");
								}
								jbtlast.setIcon(new ImageIcon("image/lastBlue.gif"));
								if(pass_num<10){
									pass[pass_num]=x;
									pass_num++;
								}
								else{
									for(int i=0;i<9;i++)pass[i]=pass[i+1];
									pass[9]=x;
								}
							}
							break;
						}
						default:{
							outputPanel.jbtCorrect.setEnabled(false);
							outputPanel.jbtCorrect.setVisible(false);
							String currentInput=jtfInputText.getText();
							dict.tip(currentInput,tipPanel.tipWords);
							tipPanel.jlstTip.setListData(tipPanel.tipWords);
							}
					}
				}
			});
			add(jbtSearch);
			jbtSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {//reaction to c--search
					String x=jtfInputText.getText();
					outputPanel.jtaExplanation.setText(x+"\n");
					String res=dict.search(x);
					if(res.equals("$")){
						outputPanel.jtaExplanation.append("    不存在该单词.\n\n");
						outputPanel.jtaExplanation.append("    您是否要找： ");
						String correctWords=dict.check(x);
						outputPanel.jbtCorrect.setText(correctWords);
						outputPanel.jbtCorrect.setEnabled(true);
						outputPanel.jbtCorrect.setVisible(true);
					}
					else {
						String[] outlines=res.split("\\\\t");
						outputPanel.jtaExplanation.append("\n[解释]");
						for(int i=0;i<outlines.length;i++){
							outputPanel.jtaExplanation.append("     "+outlines[i]+"  ");
						}
						jbtlast.setIcon(new ImageIcon("image/lastBlue.gif"));
						if(pass_num<10){
							pass[pass_num]=x;
							pass_num++;
						}
						else{
							for(int i=0;i<9;i++)pass[i]=pass[i+1];
							pass[9]=x;
						}
					}
				}
			});
		}
	}
	class OutputPanel extends JPanel{
		private JTextArea jtaExplanation=new JTextArea();
		private JButton jbtCorrect=new JButton();
		private JTextArea jtaNull1=new JTextArea();
		private JTextArea jtaNull2=new JTextArea();
		public OutputPanel(){
			JPanel jpnOutput=new JPanel();
			JPanel jpncorrect=new JPanel();
			jpncorrect.setPreferredSize(new Dimension(400, 40));
			jpncorrect.setBackground(Color.WHITE);
			jpncorrect.setLayout(new BorderLayout(0,0));
			jpncorrect.add(jbtCorrect,BorderLayout.WEST);
			jbtCorrect.setEnabled(false);
			jbtCorrect.setVisible(false);
			jbtCorrect.setPreferredSize(new Dimension(100,20));
			jbtCorrect.setBackground(Color.WHITE);
			jbtCorrect.setBorder(null);
			jbtCorrect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					jbtCorrect.setEnabled(false);
					jbtCorrect.setVisible(false);
					String key=jbtCorrect.getText();
					outputPanel.jtaExplanation.setText(key+"\n");
					String res=dict.search(key);
					String[] outlines=res.split("\\\\t");
					outputPanel.jtaExplanation.append("\n[解释]");
					for(int i=0;i<outlines.length;i++)
						outputPanel.jtaExplanation.append("     "+outlines[i]+"  ");
				}
			}
			);
			jpnOutput.add(jtaExplanation);
			jpnOutput.add(jpncorrect);
			jpnOutput.setBackground(Color.WHITE);
			jpnOutput.setPreferredSize(new Dimension(505, 600));
			jtaNull1.setPreferredSize(new Dimension(0, 100));
			jtaNull2.setPreferredSize(new Dimension(100, 1));
			jtaExplanation.setPreferredSize(new Dimension(500, 100));
			jtaExplanation.setLineWrap(true);
			jtaExplanation.setWrapStyleWord(true);
			jtaExplanation.setEditable(false);
			jpnOutput.setBorder(new LineBorder(new Color(127,157,185), 1, false));
			jtaExplanation.setFont(new Font("Serif",Font.BOLD,16));
			setLayout(new BorderLayout(3,3));
			add(jpnOutput,BorderLayout.CENTER);
			add(jtaNull1,BorderLayout.EAST);
			add(jtaNull2,BorderLayout.SOUTH);
		}
	}
	class TipPanel extends JPanel{
		private String[] tipWords=new String[80];
		private JList jlstTip=new JList(tipWords);
		private JTextArea jtaNull1=new JTextArea();
		private JTextArea jtaNull2=new JTextArea();
		
		public TipPanel(){
			setBackground(new Color(245,245,255));
			jtaNull1.setPreferredSize(new Dimension(0, 100));
			jtaNull2.setPreferredSize(new Dimension(100, 1));
			JScrollPane TipPanel = new JScrollPane();
			TipPanel.setViewportView(jlstTip);
			//jlstTip.setVisibleRowCount(2);
			//jlstTip.setBounds(0, 0, 400, 400);
			jlstTip.setFixedCellHeight(30);
			jlstTip.setFixedCellWidth(150);
			jlstTip.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					// get selected words
					outputPanel.jbtCorrect.setEnabled(false);
					outputPanel.jbtCorrect.setVisible(false);
					int index=jlstTip.getSelectedIndex();
					if(index>=0&&index<tipWords.length){
						String key=tipWords[jlstTip.getSelectedIndex()];
						outputPanel.jtaExplanation.setText(key+"\n");
						String res=dict.search(key);
						String[] outlines=res.split("\\\\t");
						outputPanel.jtaExplanation.append("\n[解释]");
						for(int i=0;i<outlines.length;i++){
							outputPanel.jtaExplanation.append("     "+outlines[i]+"  ");
						}	
					}
				}
			});
			setLayout(new BorderLayout(3,3));
			add(TipPanel,BorderLayout.CENTER);
			add(jtaNull1,BorderLayout.WEST);
			add(jtaNull2,BorderLayout.SOUTH);
		}
	}


*/