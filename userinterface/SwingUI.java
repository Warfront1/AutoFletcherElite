package scripts.userinterface;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.Box;

import scripts.data.ItemData.Arrows;
import scripts.data.ItemData.Bolts;
import scripts.data.ItemData.Bows;
import scripts.data.ItemData.Darts;
import scripts.data.ItemData.Arrows.AttachArrowTips;
import scripts.data.ItemData.Arrows.HeadlessArrows;
import scripts.data.ItemData.Bolts.AttachBoltTips;
import scripts.data.ItemData.Bolts.AttachFeathersToBolts;
import scripts.data.ItemData.Bolts.CutGemstoTips;
import scripts.data.ItemData.Bolts.UncutGemCutting;
import scripts.data.ItemData.Bows.Cutting;
import scripts.data.ItemData.Bows.Stringing;
import scripts.data.ItemData.Darts.AttachFeathersToDartTips;

public class SwingUI extends JFrame {
	
	public static Object Object = null;
	public static boolean Start = false;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingUI frame = new SwingUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SwingUI() {
		setTitle("Auto Fletcher Elite");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 238, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				for(Component i : contentPane.getComponents()){
					if(i.getName()!=null){
						if(i.getName().equals("comboBox2")){
							contentPane.remove(i);
						}
						else if((i.getName().equals("comboBox3"))){
							contentPane.remove(i);
						}
						else if((i.getName().equals("StartButton"))){
							contentPane.remove(i);
						}
						contentPane.revalidate();
						contentPane.repaint();

					}
				}
				
				final JComboBox comboBox2 = new JComboBox();
				comboBox2.setName("comboBox2");
				comboBox2.setBounds(37, 176, 133, 20);
				comboBox2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						addButton();
					}
				});
				
				final JComboBox comboBox3 = new JComboBox();
				comboBox3.setName("comboBox3");
				comboBox3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Object = comboBox3.getSelectedItem();
						addButton();
					}
				});
				comboBox3.setBounds(37, 258, 133, 20);
				
				
				switch((String) comboBox.getSelectedItem()){
					case "Bows":
						comboBox2.setModel(new DefaultComboBoxModel(new String[]{"Cutting","Stringing"}));
						comboBox2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								switch((String) comboBox2.getSelectedItem()){
									case "Cutting":
										comboBox3.setModel(new DefaultComboBoxModel(Bows.Cutting.values()));
									break;
									case "Stringing":
										comboBox3.setModel(new DefaultComboBoxModel(Bows.Stringing.values()));
									break;
								}
								contentPane.add(comboBox3);
								contentPane.revalidate();
								contentPane.repaint();
							}
						});
					break;
					case "Arrows":
						comboBox2.setModel(new DefaultComboBoxModel(new Object[]{"Arrow Tips", "Headless Arrows", "Shafts"}));
						comboBox2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								switch((String) comboBox2.getSelectedItem()){
									case "Arrow Tips":
										comboBox3.setModel(new DefaultComboBoxModel(Arrows.AttachArrowTips.values()));
										contentPane.add(comboBox3);
										break;
									case "Headless Arrows":
										Object = Arrows.HeadlessArrows.Shafts;
									break;
									case "Shafts":
										Object = Bows.Cutting.Shafts;
									break;
								}
								
								contentPane.revalidate();
								contentPane.repaint();
							}
						});
					break;
					case "Darts":
						comboBox2.setModel(new DefaultComboBoxModel(new Object[]{"Attach Feathers"}));
						comboBox2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								switch((String) comboBox2.getSelectedItem()){
									case "Attach Feathers":
										comboBox3.setModel(new DefaultComboBoxModel(Darts.AttachFeathersToDartTips.values()));
									break;
								}
								contentPane.add(comboBox3);
								contentPane.revalidate();
								contentPane.repaint();
							}
						});
					break;
					case "Bolts":
						comboBox2.setModel(new DefaultComboBoxModel(new Object[]{"Attach Bolt Tips" , "Attach Feathers" , "Cut: Gems -> Tips", "Cut: Uncut Gems -> Gems"}));
						comboBox2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								switch((String) comboBox2.getSelectedItem()){
									case "Attach Bolt Tips":
										comboBox3.setModel(new DefaultComboBoxModel(Bolts.AttachBoltTips.values()));
									break;
									case "Attach Feathers":
										comboBox3.setModel(new DefaultComboBoxModel(Bolts.AttachFeathersToBolts.values()));
									break;
									case  "Cut: Gems -> Tips":
										comboBox3.setModel(new DefaultComboBoxModel(Bolts.CutGemstoTips.values()));
									break;
									case "Cut: Uncut Gems -> Gems":
										comboBox3.setModel(new DefaultComboBoxModel(Bolts.UncutGemCutting.values()));
									break;
								}
								contentPane.add(comboBox3);
								contentPane.revalidate();
								contentPane.repaint();
							}
						});
					break;
				}
				
				contentPane.add(comboBox2);
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new Object[] {"Bows","Arrows","Darts","Bolts"}));
		comboBox.setBounds(37, 96, 133, 20);
		contentPane.add(comboBox);
		
//		JComboBox comboBox_1 = new JComboBox();
//		comboBox_1.setBounds(37, 176, 133, 20);
//		contentPane.add(comboBox_1);
//		
//		JComboBox comboBox_2 = new JComboBox();
//		comboBox_2.setBounds(37, 258, 133, 20);
//		contentPane.add(comboBox_2);
		
		JLabel Label1 = new JLabel("Fletching Methods:");
		Label1.setBounds(10, 71, 160, 14);
		contentPane.add(Label1);
		
		JLabel label2 = new JLabel("Secondary Options:");
		label2.setBounds(10, 151, 171, 14);
		contentPane.add(label2);
		
		JLabel Label3 = new JLabel("Third Options:");
		Label3.setBounds(10, 236, 160, 14);
		contentPane.add(Label3);
		
		JLabel lblAutoFletcherElite = new JLabel("Auto Fletcher Elite");
		lblAutoFletcherElite.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblAutoFletcherElite.setBounds(21, 11, 217, 31);
		contentPane.add(lblAutoFletcherElite);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(-12, 32, 239, 19);
		contentPane.add(horizontalStrut);
		
		
//		JLabel lblFletch = new JLabel("Fletch:");
//		lblFletch.setBounds(26, 74, 46, 14);
//		contentPane.add(lblFletch);
//		contentPane.add(new JLabel("Fletch:").setBounds(26, 74, 46, 14));
	}
	public void addButton(){
		if(Object!=null){
			JButton btnStart = new JButton("Start");
			btnStart.setBounds(61, 324, 89, 23);
			btnStart.setName("StartButton");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.out.println(Object);
					Start=true;
				}
			});
			contentPane.add(btnStart);
			
			contentPane.revalidate();
			contentPane.repaint();
		}
	}
}
