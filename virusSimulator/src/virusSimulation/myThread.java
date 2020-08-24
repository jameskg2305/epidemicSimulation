package virusSimulation;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.Semaphore;

public class myThread implements Runnable{
	Random random = new Random();
	ForkJoinPool f;
	ForkJoinPool f1;
	static boolean running = true;
	boolean starting = false;
	long startTime;
	Semaphore s;
	
	public myThread(){
		starting = false;
	}
	@Override
	public void run() {
		f = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		f1 = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		// TODO Auto-generated method stub
		long start = 0;
		long fps=0;
		while(running){
			long now = System.currentTimeMillis();
				if(start>1000/60){
					start = 0;
					if(starting==true){
						moveParticles();
						recordInfect();
						virusSim.d.repaint();
						if(virusSim.infected==0 || virusSim.infected+virusSim.recovered==virusSim.quantity){
							running = false;
						}
						virusSim.time =System.currentTimeMillis()-startTime;
					}
				
				}
			if(System.currentTimeMillis()-now>1000/100){
				fps = 1000/(System.currentTimeMillis()-now);
				virusSim.f.setTitle(fps+" fps");
			}
			start += System.currentTimeMillis()-now;
		}
	}
	 void recordInfect() {
		// TODO Auto-generated method stub
		recursiveCount r = new recursiveCount(Runtime.getRuntime().availableProcessors(), virusSim.p);
		f1.invoke(r);
		virusSim.infected = r.count;
		virusSim.recovered = r.rcount;
	}
	void moveParticles() {
		// TODO Auto-generated method stub
		
		recursiveParticle r = new recursiveParticle(Runtime.getRuntime().availableProcessors(), random
				,virusSim.p);
		f.invoke(r);
	}
}

class recursiveParticle extends RecursiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int threads;
	Random random;
	recursiveParticle r1;
	recursiveParticle r2;
	particle[] array;
	public recursiveParticle(int availableProcessors, Random random, particle[] array) {
		// TODO Auto-generated constructor stub
		this.threads = availableProcessors;
		this.random = random;
		this.array = array;
	}

	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		if(threads>1){
			//System.out.println(start+" "+ fin);
			particle[] p = Arrays.copyOfRange(array, 0, array.length/2);
			particle[] p1 = Arrays.copyOfRange(array, array.length/2, (array.length));
			
			r1 = new recursiveParticle(threads>>1, random, p);
			r2 = new recursiveParticle(threads>>1, random, p1);
			/*r1.threads = threads>>1;
			r1.random = random;
			r1.start = start;
			r1.fin = (start + fin)/2;
			r1.r1 = r1;
			r1.r2=r1;
			
			r2.threads = threads>>1;
			r2.random = random;
			r2.start = (start + fin)/2;
			r2.fin = start;
			r2.r1 = r2;
			r2.r2 = r2;*/
			
			invokeAll(r1,r2);
		}else{
			double dist = 0;
			for(particle p : array){
				p.x +=(int)((random.nextDouble()*10)-5);
				p.y +=(int)((random.nextDouble()*10)-5);
				if(p.x>virusSim.w){
					p.x=virusSim.w;
				}else if(p.x<0){
					p.x=0;
				}
				if(p.y>(virusSim.h)){
					p.y=(virusSim.h);
				}else if(p.y<0){
					p.y=0;
				}
				if(p.infected==true){
					if(p.timer>1000/virusSim.freq){
						p.timer=0;
						for(int j=0;j<virusSim.p.length;j++){
								if(virusSim.p[j].infected==false && virusSim.p[j].recovered==false){
									 dist = 
										Math.sqrt(
										 Math.pow(p.x-virusSim.p[j].x,2)
										+Math.pow(p.y-virusSim.p[j].y,2));
									if(dist<=virusSim.reach){
										virusSim.p[j].infected = true;
										virusSim.p[j].infectionTime = System.currentTimeMillis();
									}
								}
						}
					}
					if(System.currentTimeMillis()-p.infectionTime>virusSim.duration){
						p.infected = false;
						p.recovered = true;
					}
					p.timer+=1000/60;
				}
			}
		}
	}
	
}

class recursiveCount extends RecursiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int threads;
	int start;
	int fin;
	int count;
	int rcount;
	particle[] array;
	public recursiveCount(int t, particle[] array){
		this.threads = t;
		count=0;
		rcount=0;
		this.array = array;
	}
	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		if(threads>1){
			//System.out.println(start+" "+ fin);
			particle[] p = Arrays.copyOfRange(array, 0, array.length/2);
			particle[] p1 = Arrays.copyOfRange(array, array.length/2, (array.length));
			recursiveCount r1 = new recursiveCount(threads/2, p);
			recursiveCount r2 = new recursiveCount(threads/2, p1);
			invokeAll(r1,r2);
			count=r1.count+r2.count;
			rcount=r1.rcount+r2.rcount;
		}else{
			for(particle p : array){
				if(p.recovered){
					rcount++;
				}else if(p.infected){
					count++;
				}
			}
			
		}
	}
	
}
