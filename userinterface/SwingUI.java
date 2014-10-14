package scripts.userinterface;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Font;
import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;

import scripts.data.FletchingRecipe;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class SwingUI extends JFrame {
	
	public static FletchingRecipe Recipe;
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
		setBounds(100, 100, 344, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setForeground(new Color(128, 128, 128));
		horizontalStrut.setBounds(-25, 34, 404, 9);
		contentPane.add(horizontalStrut);
		
		JLabel lblAutoFletcherElite = new JLabel("Auto Fletcher Elite");
		lblAutoFletcherElite.setForeground(new Color(106, 90, 205));
		lblAutoFletcherElite.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblAutoFletcherElite.setBounds(70, 11, 216, 25);
		contentPane.add(lblAutoFletcherElite);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBounds(-37, 112, 397, 9);
		contentPane.add(horizontalGlue);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalGlue_1.setBounds(-18, 196, 397, 9);
		contentPane.add(horizontalGlue_1);
		
		JLabel lblSelectFirstItem = new JLabel("Select First Item:");
		lblSelectFirstItem.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblSelectFirstItem.setBounds(10, 54, 133, 19);
		contentPane.add(lblSelectFirstItem);
		
		JLabel lblSelectSecondItem = new JLabel("Select Second Item:");
		lblSelectSecondItem.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblSelectSecondItem.setBounds(10, 132, 133, 19);
		contentPane.add(lblSelectSecondItem);
		
		final JButton btnStart = new JButton("");
		btnStart.setEnabled(false);
		final JComboBox comboBox_1 = new JComboBox();
		final JComboBox comboBox = new JComboBox();
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String FirstBoxItem = (String)comboBox.getSelectedItem();
				String SecondBoxItem = (String)comboBox_1.getSelectedItem();
				
				Set<String> s = new HashSet<String>();
			    Iterator<String> keySetIterator = FletchingRecipe.Recipes.keySet().iterator();
			    while(keySetIterator.hasNext()){
			      String key = keySetIterator.next();
			      if(FletchingRecipe.Recipes.get(key).getItem1().getInGameName().equals(FirstBoxItem)){
			    	  if(FletchingRecipe.Recipes.get(key).getItem2().getInGameName().equals(SecondBoxItem)){
						  System.out.println("Swing UI Selected: " +FletchingRecipe.Recipes.get(key).getEndProduct().getInGameName());
			    		  Recipe = FletchingRecipe.Recipes.get(key);
			    	  }
			      }
			    }
			}
		});

		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String FirstBoxItem = (String)comboBox.getSelectedItem();
				String SecondBoxItem = (String)comboBox_1.getSelectedItem();
				
				Set<String> s = new HashSet<String>();
			    Iterator<String> keySetIterator = FletchingRecipe.Recipes.keySet().iterator();
			    while(keySetIterator.hasNext()){
			      String key = keySetIterator.next();
			      if(FletchingRecipe.Recipes.get(key).getItem1().getInGameName().equals(FirstBoxItem)){
			    	  if(FletchingRecipe.Recipes.get(key).getItem2().getInGameName().equals(SecondBoxItem)){
			    		  btnStart.setEnabled(true);
						try {
							 java.net.URL where = new URL("http://warfront1.github.io/AutoFletcherElite/UserInterfaces/Images/"+FletchingRecipe.Recipes.get(key).getEndProduct().getImageURL());
				    		  ImageIcon anotherIcon = new ImageIcon(where);
				    		  btnStart.setText("Fletch "+FletchingRecipe.Recipes.get(key).getEndProduct().getInGameName());
							  btnStart.setIcon(anotherIcon);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	  }
			      }
			    }
			}
		});
		
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSecondItemBox (comboBox, comboBox_1 );
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {""}));
		setFirstItemBox(comboBox);
		comboBox.setBounds(46, 81, 216, 20);
		contentPane.add(comboBox);
		
		
		comboBox_1.setBounds(46, 162, 216, 20);
		contentPane.add(comboBox_1);
		
		
		btnStart.setBounds(33, 259, 229, 57);
		contentPane.add(btnStart);
	}
	
	public void setFirstItemBox (JComboBox Box){
		FletchingRecipe.loadRecipes();
		
		Set<String> s = new HashSet<String>();
	    Iterator<String> keySetIterator = FletchingRecipe.Recipes.keySet().iterator();
	    while(keySetIterator.hasNext()){
	      String key = keySetIterator.next();
	      s.add(FletchingRecipe.Recipes.get(key).getItem1().getInGameName());
	    }
	    Box.setModel(new DefaultComboBoxModel(s.toArray()));
	}
	public void setSecondItemBox (JComboBox FirstBox, JComboBox SecondBox){
		
		String FirstBoxItem = (String)FirstBox.getSelectedItem();
		
		Set<String> s = new HashSet<String>();
	    Iterator<String> keySetIterator = FletchingRecipe.Recipes.keySet().iterator();
	    while(keySetIterator.hasNext()){
	      String key = keySetIterator.next();
	      if(FletchingRecipe.Recipes.get(key).getItem1().getInGameName().equals(FirstBoxItem)){
	    	  s.add(FletchingRecipe.Recipes.get(key).getItem2().getInGameName());
	      }
	    }
	    SecondBox.setModel(new DefaultComboBoxModel(s.toArray()));
	}
}
