package virusSimulation;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class virusSim {
	static particle[] p;
	static panel jp;
	static menu menup;
	static int infected;
	static int recovered;
	static long time;
	static frame f;
	static int duration;
	static myThread t;
	static int quantity;
	static int reach;
	static int freq;
	static int w;
	static int h;
	static drawParticles d;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		f = new frame();
		f.setSize(800, 800);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		time = 0;
		menup = new menu();
		menup.setLayout(null);
		f.add(menup);
		menup.revalidate();
		t = new myThread();
		t.run();
		//startThread();
		
		/*jp = new panel();
		jp.setLayout(null);
		f.add(jp);
		jp.revalidate();
		//jp.repaint();
		createPart(400);
		t = new myThread();
		t.run();*/
		//startl();
		
	}
	public static void startl(int i, int j, int k, int duration2, int wid, int hei){
		//jp = new panel();
		//jp.setLayout(null);
		w=wid;
		h=hei;
		f.setBounds(0, 0, w, h);
		createPart(i);
		d = new drawParticles();
		d.setLayout(null);
		f.remove(menup);
		f.add(d);
		d.revalidate();
		f.createBufferStrategy(3);
		t.startTime = System.currentTimeMillis();
		t.starting=true;
		freq = j;
		reach = k;
		quantity = i;
		duration = duration2;
		//drawParticles.createThr();
		//System.out.println(t.starting);
	}
	
	static void createPart(int size){
		p = new particle[size];
		Random random = new Random();
		for(int i=0;i<size;i++){
			
			p[i] = new particle((int)(random.nextDouble()*w), 
					(int)(random.nextDouble()*h),(long) 0,false);
			if(i==0){
				p[i].infected=true;
				p[i].infectionTime = System.currentTimeMillis();
				infected = 1;
			}
		}
	}
}

class frame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}

class panel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage c;
	BufferedImage in;
	BufferedImage rec;
	JLabel infectedlabel = new JLabel();
	JLabel timeLabel = new JLabel();
	JLabel recoverLabel = new JLabel();
	public panel(){
		try {
			c = ImageIO.read(new File("e.png"));
			in = ImageIO.read(new File("i.png"));
			rec = ImageIO.read(new File("rec.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infectedlabel.setFont(new Font("Ariel", Font.PLAIN, 30));
		infectedlabel.setBackground(Color.WHITE);
		infectedlabel.setOpaque(true);
		
		timeLabel.setFont(new Font("Ariel", Font.PLAIN, 30));
		timeLabel.setBackground(Color.WHITE);
		timeLabel.setOpaque(true);
		
		recoverLabel.setFont(new Font("Ariel", Font.PLAIN, 30));
		recoverLabel.setBackground(Color.WHITE);
		recoverLabel.setOpaque(true);
	}
	
	
}

class particle{
	int x;
	int y;
	long timer;
	long infectionTime;
	boolean infected;
	boolean recovered;
	public particle(int x, int y, long timer, boolean inf){
		this.x = x;
		this.y = y;
		this.timer = timer;
		this.infected = inf;
	}
}

