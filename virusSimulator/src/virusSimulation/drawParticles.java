package virusSimulation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class drawParticles extends panel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static ForkJoinPool f = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
	
	static recursiveDraw r;
	
	/*public static void createThr(){
		r = new recursiveDraw(Runtime.getRuntime().availableProcessors(),virusSim.p, virusSim.d.getGraphics(),
				virusSim.d.c, virusSim.d.in,virusSim.d.rec);
		f.invoke(r);
	}*/
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		r = new recursiveDraw(Runtime.getRuntime().availableProcessors(),virusSim.p, g,
				virusSim.d.c, virusSim.d.in,virusSim.d.rec);
		f.invoke(r);
		
		infectedlabel.setText(""+virusSim.infected+" infected");
		infectedlabel.setBounds(100, 200, (int) (Math.log10(virusSim.infected+1)+1)*30 + 100, 30);
		this.add(infectedlabel);
		
		timeLabel.setText(""+(double)virusSim.time/1000 +"s");
		timeLabel.setBounds(100, 250, (int) (Math.log10(virusSim.time*100))*30, 30);
		this.add(timeLabel);
		
		recoverLabel.setText(""+virusSim.recovered+" recovered");
		recoverLabel.setBounds(100, 300, (int) (Math.log10(virusSim.recovered+1)+1)*30 + 130, 30);
		this.add(recoverLabel);
	}
}

class recursiveDraw extends RecursiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int threads;
	int start;
	int fin;
	static Graphics g;
	particle[] array;
	static BufferedImage c;
	static BufferedImage in;
	static BufferedImage rec;
	static boolean lock = true;
	public recursiveDraw(int t, particle[] p){
		this.threads = t;
		this.array = p;
	}
	public recursiveDraw(int t, particle[] p, Graphics g, BufferedImage c,BufferedImage in,BufferedImage rec){
		this.array = p;
		this.threads = t;
		recursiveDraw.g = g;
		recursiveDraw.c = c;
		recursiveDraw.in = in;
		recursiveDraw.rec = rec;
	}
	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		if(threads>1){
			particle[] p = Arrays.copyOfRange(array, 0, array.length/2);
			particle[] p1 = Arrays.copyOfRange(array, array.length/2, (array.length));
			recursiveDraw r1 = new recursiveDraw(threads/2, p);
			recursiveDraw r2 = new recursiveDraw(threads/2, p1);
			invokeAll(r1,r2);
		}else{
			for(particle p : array){
				//System.out.println(virusSim.p[i].x+" "+virusSim.p[i].y);
					if(p.infected==true){
						g.drawImage(in, p.x-5, p.y-5, 10, 10,null);
					}else if(p.recovered==true){
						g.drawImage(rec, p.x-5, p.y-5, 10, 10,null);
					}else{
						g.drawImage(c, p.x-5, p.y-5, 10, 10,null);
					}
			}
		}
	}
	
}