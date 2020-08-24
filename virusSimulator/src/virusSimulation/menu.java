package virusSimulation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class menu extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel displaySize = new JLabel();
	JTextField enterQuantity = new JTextField();
	
	JLabel displayFre = new JLabel("Enter frequency of infection; (per second)");
	JSlider enterFre = new JSlider();
	
	JLabel displayRange = new JLabel("Enter range of infection; (in pixels)");
	JSlider enterRange = new JSlider();
	
	JLabel dd = new JLabel("Enter duration of infection; ");
	JSlider d = new JSlider();
	
	JLabel w = new JLabel("Width in pixels");
	JLabel h = new JLabel("Height in pixels");
	JTextField enterWidth = new JTextField();
	JTextField enterHeight = new JTextField();
	
	JButton button = new JButton("START SIMULATION");
	boolean tsta;
	public menu(){
		this.add(button);
		displaySize.setFont(new Font("Ariel", Font.PLAIN, 10));
		displaySize.setBackground(Color.WHITE);
		displaySize.setOpaque(true);	
		displaySize.setText("Enter number of people; ");
		displaySize.setBounds(100, 200, 200, 30);
		enterQuantity.setBackground(Color.WHITE);
		enterQuantity.setOpaque(true);
		enterQuantity.setBounds(300, 200, 100, 30);
		
		displayFre.setFont(new Font("Ariel", Font.PLAIN, 10));
		displayFre.setBackground(Color.WHITE);
		displayFre.setOpaque(true);	
		displayFre.setBounds(100, 300, 200, 50);
		enterFre.setBackground(Color.WHITE);
		enterFre.setMinimum(0);
		enterFre.setPaintLabels(true);
		enterFre.setPaintTicks(true);
		enterFre.setForeground(new Color(0,0,0));
		enterFre.setMajorTickSpacing(10);
		enterFre.setMinorTickSpacing(1);
		enterFre.setMaximum(60);
		enterFre.setOpaque(true);
		enterFre.setBounds(300, 300, 400, 50);
		
		displayRange.setFont(new Font("Ariel", Font.PLAIN, 10));
		displayRange.setBackground(Color.WHITE);
		displayRange.setOpaque(true);	
		displayRange.setBounds(100, 350, 200, 50);
		enterRange.setBackground(Color.WHITE);
		enterRange.setMinimum(10);
		enterRange.setPaintLabels(true);
		enterRange.setPaintTicks(true);
		enterRange.setForeground(new Color(0,0,0));
		enterRange.setMajorTickSpacing(10);
		enterRange.setMinorTickSpacing(1);
		enterRange.setValue(20);
		enterRange.setMaximum(40);
		enterRange.setOpaque(true);
		enterRange.setBounds(300, 350, 400, 50);
		
		dd.setFont(new Font("Ariel", Font.PLAIN, 10));
		dd.setBackground(Color.WHITE);
		dd.setOpaque(true);	
		dd.setBounds(100, 400, 200, 50);
		d.setBackground(Color.WHITE);
		d.setMinimum(0);
		d.setPaintLabels(true);
		d.setPaintTicks(true);
		d.setForeground(new Color(0,0,0));
		d.setMajorTickSpacing(10);
		d.setValue(7);
		d.setMinorTickSpacing(1);
		d.setMaximum(20);
		d.setOpaque(true);
		d.setBounds(300, 400, 400, 50);
		
		enterWidth.setBackground(Color.WHITE);
		enterWidth.setOpaque(true);
		enterWidth.setText("800");
		enterWidth.setBounds(300, 450, 100, 30);
		
		enterHeight.setBackground(Color.WHITE);
		enterHeight.setOpaque(true);
		enterHeight.setText("800");
		enterHeight.setBounds(300, 480, 100, 30);
		
		w.setFont(new Font("Ariel", Font.PLAIN, 10));
		w.setBackground(Color.WHITE);
		w.setOpaque(true);	
		w.setBounds(100, 450, 200, 30);
		
		h.setFont(new Font("Ariel", Font.PLAIN, 10));
		h.setBackground(Color.WHITE);
		h.setOpaque(true);	
		h.setBounds(100, w.getY()+w.getBounds().height, 200, 30);
		
		
		button.setBounds(0, h.getY()+h.getBounds().height, 800, 100);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int quantity = Integer.parseInt(enterQuantity.getText());
				int freq = enterFre.getValue();
				int range = enterRange.getValue();
				int duration = d.getValue()*1000;
				
				int w = Integer.parseInt(enterWidth.getText());
				int h = Integer.parseInt(enterHeight.getText());
				
				if(freq==0){
					freq=1;
				}
				virusSim.startl(quantity,freq,range, duration, w, h);
			}

		});
		
		
	}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			this.add(enterQuantity);
			this.add(displaySize);
			this.add(displayFre);
			this.add(enterFre);
			this.add(displayRange);
			this.add(enterRange);
			this.add(d);
			this.add(dd);
			this.add(enterWidth);
			this.add(enterHeight);
			this.add(w);
			this.add(h);
	}
}
