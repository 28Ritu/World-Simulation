import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;
import java.lang.Comparable;

class InReader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    
    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
    
    static float nextFloat() throws IOException {
    	return Float.parseFloat( next() );
    }
}
abstract class Animal implements Comparable<Animal>
{
	int timestamp, x, y, no_turns;
	float health;
	String rank;
	public Animal(int t, int x1, int y1, float h, String s)
	{
		timestamp=t;
		x=x1;
		y=y1;
		health=h;
		no_turns=0;
		rank=s;
	}
	
	@Override
	public int compareTo(Animal ob1)
	{
		if (this.timestamp!=ob1.timestamp)
		{
			if (this.timestamp<ob1.timestamp)
				return -1;
			else
				return 1;
		}
		else
		{
			if (this.health!=ob1.health)
			{
				if (this.health>ob1.health)
					return -1;
				else
					return 1;
			}
			else
			{
				if ((this instanceof Herbivore)==true && (ob1 instanceof Carnivore)==true)
					return -1;
				else if ((this instanceof Carnivore)==true && (ob1 instanceof Herbivore)==true)
					return 1;
				else if ((this instanceof Herbivore)==true && (ob1 instanceof Herbivore)==true)
				{
					int d1=(this.x*this.x)+(this.y*this.y);
					int d2=(ob1.x*ob1.x)+(ob1.y*ob1.y);
					if (d1<d2)
						return -1;
					else
						return 1;
				}
				else if ((this instanceof Carnivore)==true && (ob1 instanceof Carnivore)==true)
				{
					int d1=(this.x*this.x)+(this.y*this.y);
					int d2=(ob1.x*ob1.x)+(ob1.y*ob1.y);
					if (d1<d2)
						return -1;
					else
						return 1;
				}
			}
		}
		return 0;
	}
	abstract void turn(float grass_x, float grass_y, float grass_r, boolean inside);
}
class Herbivore extends Animal
{
	int grass_capacity;
	public Herbivore(int t, int x1, int y1, float h, int g, String s)
	{
		super(t, x1, y1, h, s);
		grass_capacity=g;
	}
	void turn(float grass_x, float grass_y, float grass_r, boolean inside)
	{
		if (inside==true)
		{
			move_to_grassland(grass_x, grass_y, 5);
			health-=25;
		}
		else
		{
			Random r=new Random();
			float chance=r.nextFloat();
			System.out.print(chance);
			if (chance<0.5)
				move_to_grassland(grass_x, grass_y, 5);
		}
	}
	public void move_to_grassland(float grass_x, float grass_y, int d)
	{
		double r=d/Math.sqrt(Math.pow((grass_x-x), 2)+Math.pow((grass_y-y), 2));
		int x1=(int) Math.round(x+r*(grass_x-x));
		int y1=(int) Math.round(y+r*(grass_y-y));
		int x2=(int) Math.round(x-r*(grass_x-x));
	    int y2=(int) Math.round(y-r*(grass_y-y));
	    
	    double d1=Math.pow(grass_x-x1, 2)+Math.pow(grass_y-y1, 2);
	    double d2=Math.pow(grass_x-x2, 2)+Math.pow(grass_y-y2, 2);
	    
	    if (d1<d2)
	    {
	    	x=x1;
	    	y=y1;
	    }
	    else
	    {
	    	x=x2;
	    	y=y2;
	    }
	    System.out.print("move_to_grassland"+" "+x+" "+y);
	}
	public void go_away(int c_x, int c_y, int d)
	{
		double r=d/Math.sqrt(Math.pow((c_x-x), 2)+Math.pow((c_y-y), 2));
		int x1=(int) Math.round(x+r*(c_x-x));
		int y1=(int) Math.round(y+r*(c_y-y));
		int x2=(int) Math.round(x-r*(c_x-x));
	    int y2=(int) Math.round(y-r*(c_y-y));
	    
	    double d1=Math.pow(c_x-x1, 2)+Math.pow(c_y-y1, 2);
	    double d2=Math.pow(c_x-x2, 2)+Math.pow(c_y-y2, 2);
	    
	    if (d1>d2)
	    {
	    	x=x1;
	    	y=y1;
	    }
	    else
	    {
	    	x=x2;
	    	y=y2;
	    }
	    System.out.print("going away"+" "+x+" "+y);
	}
	public void eat(Grassland G0, boolean flag)
	{
		if (flag==true)
		{
			G0.grass-=grass_capacity;
			health+=0.5*health;
		}
		else
		{
			if (G0.grass!=0)
				health+=0.2*health;
			G0.grass=0;
		}
		System.out.print("eating grass"+" "+x+" "+y);
	}
}
class Carnivore extends Animal
{
	public Carnivore(int t, int x1, int y1, float h, String s)
	{
		super(t, x1, y1, h, s);
	}
	void turn(float h_x, float h_y, float d, boolean inside)
	{
		double r=d/Math.sqrt(Math.pow((h_x-x), 2)+Math.pow((h_y-y), 2));
		int x1=(int) Math.round(x+r*(h_x-x));
		int y1=(int) Math.round(y+r*(h_y-y));
		int x2=(int) Math.round(x-r*(h_x-x));
	    int y2=(int) Math.round(y-r*(h_y-y));
	    
	    double d1=Math.pow(h_x-x1, 2)+Math.pow(h_y-y1, 2);
	    double d2=Math.pow(h_x-x2, 2)+Math.pow(h_y-y2, 2);
	    
	    if (d1<d2)
	    {
	    	x=x1;
	    	y=y1;
	    }
	    else
	    {
	    	x=x2;
	    	y=y2;
	    }
	}
}
class Grassland
{
	float x, y, radius;
	int grass;
	public Grassland(float x1, float y1, float rad, int g)
	{
		x=x1;
		y=y1;
		radius=rad;
		grass=g;
	}
}
public class World {
	Grassland[] G=new Grassland[2];
	Herbivore[] H=new Herbivore[2];
	Carnivore[] C=new Carnivore[2];
	Herbivore h;
	Carnivore c;
	public int MaxTime(int[] T)
	{
		int max=0;
		for (int i=0; i<T.length; i++)
		{
			if (T[i]>max)
				max=T[i];
		}
		return max;
	}
	public int FindMax(int max_time, int new_time)
	{
		if (max_time>new_time)
			return max_time;
		else
			return new_time;
	}
	public void herbivore_turn_if_no_carnivore(double d1, double d2, Grassland[] G, Herbivore h)
	{
		if (d1<=(G[0].radius*G[0].radius))            // if inside grassland 1
			h.turn(G[1].x, G[1].y, G[1].radius, true);
		else if (d2<=(G[1].radius*G[1].radius))       // if inside grassland 2
			h.turn(G[0].x, G[0].y, G[0].radius, true);
		else if (d1>(G[0].radius*G[0].radius) && d2>(G[1].radius*G[1].radius)) // if not inside any grassland
		{
			if (d1<d2)
				h.turn(G[0].x, G[0].y, G[0].radius, false);    // move to grassland 1
			else
				h.turn(G[1].x, G[1].y, G[1].radius, false);    // move to grassland 2
		}
	}
	public void move_away_from_carnivore(Herbivore h, Carnivore[] C, int dist)
	{
		double dd1=Math.pow((C[0].x-h.x), 2)+Math.pow((C[0].y-h.y), 2);
		double dd2=Math.pow((C[1].x-h.x), 2)+Math.pow((C[1].y-h.y), 2);
		if (dd1<dd2)
			h.go_away(C[0].x, C[0].y, dist);
		else
			h.go_away(C[1].x, C[1].y, dist);
	}
	public void herbivore_more_grass(Herbivore h, Grassland G0, Grassland G1, Carnivore[] C)
	{
		Random r=new Random();
		float chance=r.nextFloat();
		if (chance>=0.9)
		{
			System.out.print(chance);
			h.eat(G0, true);
		}
		else
		{
			h.health-=25;
			chance=r.nextFloat();
			if (chance>=0.5)
			{
				System.out.print(chance);
				move_away_from_carnivore(h, C, 2);
			}
			chance=r.nextFloat();
			if (chance>=0.5)
			{
				System.out.print(chance);
				h.move_to_grassland(G1.x, G1.y, 3);
			}
		}
	}
	public void herbivore_less_grass(Herbivore h, Grassland G0, Grassland G1, Carnivore[] C)
	{
		Random r=new Random();
		float chance=r.nextFloat();
		if (chance>=0.2)
		{
			System.out.print(chance);
			h.eat(G0, false);
			
		}
		else
		{
			h.health-=25;
			chance=r.nextFloat();
			if (chance>=0.7)
			{
				System.out.print(chance);
				move_away_from_carnivore(h, C, 4);
			}
			chance=r.nextFloat();
			if (chance>=0.3)
			{
				System.out.print(chance);
				h.move_to_grassland(G1.x, G1.y, 2);
			}
		}
	}
	public void herbivore_one_carnivore_more_grass(Herbivore h, Grassland G0, Grassland G1, Carnivore C)
	{
		Random r=new Random();
		float chance=r.nextFloat();
		if (chance>=0.9)
		{
			System.out.print(chance);
			h.eat(G0, true);
		}
		else
		{
			h.health-=25;
			chance=r.nextFloat();
			if (chance>=0.5)
			{
				System.out.print(chance);
				h.go_away(C.x, C.y, 2);
			}
			chance=r.nextFloat();
			if (chance>=0.5)
			{
				System.out.print(chance);
				h.move_to_grassland(G1.x, G1.y, 3);
			}
		}
	}
	public void herbivore_one_carnivore_less_grass(Herbivore h, Grassland G0, Grassland G1, Carnivore C)
	{
		Random r=new Random();
		float chance=r.nextFloat();
		System.out.print(chance);
		if (chance>=0.2)
		{
			h.eat(G0, false);
		}
		else
		{
			h.health-=25;
			chance=r.nextFloat();
			if (chance>=0.7)
			{
				System.out.print(chance);
				h.go_away(C.x, C.y, 4);
			}
			chance=r.nextFloat();
			if (chance>=0.3)
			{
				System.out.print(chance);
				h.move_to_grassland(G1.x, G1.y, 2);
			}
		}
	}
	public void move_towards_herbivore(Carnivore c, Herbivore[] H, int dist)
	{
		double dd1=Math.pow((H[0].x-c.x), 2)+Math.pow((H[0].y-c.y), 2);
		double dd2=Math.pow((H[1].x-c.x), 2)+Math.pow((H[1].y-c.y), 2);
		if (dd1<dd2)
			c.turn(H[0].x, H[0].y, dist, false);
		else
			c.turn(H[1].x, H[1].y, dist, false);
	}
	public static void main(String []args) throws IOException
	{
		InReader.init(System.in);
		World W=new World();
		System.out.println("Enter Total Final Time for Simulation:");
		int total_time=InReader.nextInt();
		int x, y, rad, grass;
		System.out.println("Enter​ x, ​y ​centre, ​radius ​and Grass ​Available ​for ​First ​Grassland:");
		x=InReader.nextInt();
		y=InReader.nextInt();
		rad=InReader.nextInt();
		grass=InReader.nextInt();
		W.G[0]=new Grassland(x, y, rad, grass);
		
		System.out.println("Enter​ x, ​y ​centre, ​radius ​and Grass ​Available ​for ​Second ​Grassland:");
		x=InReader.nextInt();
		y=InReader.nextInt();
		rad=InReader.nextInt();
		grass=InReader.nextInt();
		W.G[1]=new Grassland(x, y, rad, grass);
		
		int h_time, h_health, h_grass;
		System.out.println("Enter Health and Grass Capacity for Herbivores:");
		h_health=InReader.nextInt();
		h_grass=InReader.nextInt();
		System.out.println("Enter x, y position and timestamp for First Herbivore:");
		x=InReader.nextInt();
		y=InReader.nextInt();
		h_time=InReader.nextInt();
		W.H[0]=new Herbivore(h_time, x, y, h_health, h_grass, "First");
		
		System.out.println("Enter x, y position and timestamp for Second Herbivore:");
		x=InReader.nextInt();
		y=InReader.nextInt();
		h_time=InReader.nextInt();
		W.H[1]=new Herbivore(h_time, x, y, h_health, h_grass, "Second");
		
		int c_time, c_health;
		System.out.println("Enter Health for Carnivores:");
		c_health=InReader.nextInt();
		System.out.println("Enter x, y position and timestamp for First Carnivore:");
		x=InReader.nextInt();
		y=InReader.nextInt();
		c_time=InReader.nextInt();
		W.C[0]=new Carnivore(c_time, x, y, c_health, "First");
		
		System.out.println("Enter x, y position and timestamp for Second Carnivore:");
		x=InReader.nextInt();
		y=InReader.nextInt();
		c_time=InReader.nextInt();
		W.C[1]=new Carnivore(c_time, x, y, c_health, "Second");
		
		PriorityQueue<Animal> PQ=new PriorityQueue<Animal>(4);
		PQ.add(W.H[0]);
		PQ.add(W.H[1]);
		PQ.add(W.C[0]);
		PQ.add(W.C[1]);
		
		int no_herbi=2, no_carni=2;
		int[] Time=new int[4];
		Time[0]=W.H[0].timestamp;
		Time[1]=W.H[1].timestamp;
		Time[2]=W.C[0].timestamp;
		Time[3]=W.C[1].timestamp;
		
		int max_time=W.MaxTime(Time);
		int turns=0;
		System.out.println();
		System.out.println("The Simulation Begins -");
		while(PQ.isEmpty()==false && turns<total_time)
		{
			if (PQ.peek() instanceof Herbivore)
			{
				W.h=(Herbivore) PQ.poll();
				System.out.println("It is "+W.h.rank+" Herbivore.");
				double d1=Math.pow((W.G[0].x-W.h.x), 2)+Math.pow((W.G[0].y-W.h.y), 2);
				double d2=Math.pow((W.G[1].x-W.h.x), 2)+Math.pow((W.G[1].y-W.h.y), 2);
				if (no_carni==0)
				{
					W.herbivore_turn_if_no_carnivore(d1, d2, W.G, W.h);
				}
				else if (no_carni>1)
				{
					if (d1>(W.G[0].radius*W.G[0].radius) && d2>(W.G[1].radius*W.G[1].radius))    // if not inside any grassland
					{
						Random r=new Random();
						float chance=r.nextFloat();
						System.out.println(chance);
						if (chance>=0.95)
						{
							float chance2=r.nextFloat();
							System.out.println(chance2);
							if (chance2>=0.65)
							{
								if (d1<d2)
									W.h.move_to_grassland(W.G[0].x, W.G[0].y, 5);    // move to grassland 1
								else
									W.h.move_to_grassland(W.G[1].x, W.G[1].y, 5);    // move to grassland 2
							}
							else
							{
								W.move_away_from_carnivore(W.h, W.C, 4);
							}
						}
					}
					else
					{
						if (d1<=(W.G[0].radius*W.G[0].radius))            // if inside grassland 1
						{
							if (W.h.grass_capacity<=W.G[0].grass)
								W.herbivore_more_grass(W.h, W.G[0], W.G[1], W.C);
							else
								W.herbivore_less_grass(W.h, W.G[0], W.G[1], W.C);
						}
						else if (d2<=(W.G[1].radius*W.G[1].radius))       // if inside grassland 2
						{
							if (W.h.grass_capacity<=W.G[1].grass)
								W.herbivore_more_grass(W.h, W.G[1], W.G[0], W.C);
							else
								W.herbivore_less_grass(W.h, W.G[1], W.G[0], W.C);
						}
					}
				}
				else if (no_carni==1)
				{
					int n=0;
					if (PQ.contains(W.C[0])==true)
						n=1;
					else if (PQ.contains(W.C[1])==true)
						n=2;	
					if (d1>(W.G[0].radius*W.G[0].radius) && d2>(W.G[1].radius*W.G[1].radius))    // if not inside any grassland
					{
						Random r=new Random();
						float chance=r.nextFloat();
						System.out.println(chance);
						if (chance>=0.95)
						{
							float chance2=r.nextFloat();
							System.out.println(chance2);
							if (chance2>=0.65)
							{
								if (d1<d2)
									W.h.move_to_grassland(W.G[0].x, W.G[0].y, 5);    // move to grassland 1
								else
									W.h.move_to_grassland(W.G[1].x, W.G[1].y, 5);    // move to grassland 2
							}
							else
							{
								if (n==1)
									W.h.go_away(W.C[0].x, W.C[0].y, 4);
								else
									W.h.go_away(W.C[1].x, W.C[1].y, 4);
							}
						}
					}
					else
					{
						if (d1<=(W.G[0].radius*W.G[0].radius))            // if inside grassland 1
						{
							if (W.h.grass_capacity<=W.G[0].grass)
							{
								if (n==1)
									W.herbivore_one_carnivore_more_grass(W.h, W.G[0], W.G[1], W.C[0]);
								else
									W.herbivore_one_carnivore_more_grass(W.h, W.G[0], W.G[1], W.C[1]);
							}
							else
							{
								if (n==1)
									W.herbivore_one_carnivore_less_grass(W.h, W.G[0], W.G[1], W.C[0]);
								else
									W.herbivore_one_carnivore_less_grass(W.h, W.G[0], W.G[1], W.C[1]);
							}
						}
						else if (d2<=(W.G[1].radius*W.G[1].radius))       // if inside grassland 2
						{
							if (W.h.grass_capacity<=W.G[1].grass)
							{
								if (n==1)
									W.herbivore_one_carnivore_more_grass(W.h, W.G[1], W.G[0], W.C[0]);
								else
									W.herbivore_one_carnivore_more_grass(W.h, W.G[1], W.G[0], W.C[1]);
							}
							else
							{
								if (n==1)
									W.herbivore_one_carnivore_less_grass(W.h, W.G[1], W.G[0], W.C[0]);
								else
									W.herbivore_one_carnivore_less_grass(W.h, W.G[1], W.G[0], W.C[1]);
							}
						}
					}	
				}
				d1=Math.pow((W.G[0].x-W.h.x), 2)+Math.pow((W.G[0].y-W.h.y), 2);
				d2=Math.pow((W.G[1].x-W.h.x), 2)+Math.pow((W.G[1].y-W.h.y), 2);
				if (d1>(W.G[0].radius*W.G[0].radius) && d2>(W.G[1].radius*W.G[1].radius))
				{
					if (W.h.no_turns>7)
						W.h.health-=5;
					W.h.no_turns+=1;
				}
				else
					W.h.no_turns=0;
				if (W.h.health>0)
				{
					Random r=new Random();
					int rs=r.nextInt(total_time-max_time)+max_time;
					System.out.print(rs);
					if (rs<=(total_time-1))
					{
						W.h.timestamp=rs;
						max_time=W.FindMax(max_time, rs);
						PQ.add(W.h);
						System.out.println("It's health after taking turn is "+W.h.health+" "+W.h.timestamp);
					}
					else
					{
						System.out.println("It is dead."+W.h.health+" "+W.h.timestamp);
						no_herbi-=1;
					}
				}
				else
				{
					System.out.println("It is dead."+W.h.health+" "+W.h.timestamp);
					no_herbi-=1;
				}
			}
			else if (PQ.peek() instanceof Carnivore)
			{
				W.c=(Carnivore) PQ.poll();
				System.out.println("It is "+W.c.rank+" Carnivore.");
				if (no_herbi>1)
				{
					double d1=Math.sqrt(Math.pow(W.H[0].x-W.c.x, 2)+Math.pow(W.H[0].x-W.c.y, 2));
					double d2=Math.sqrt(Math.pow(W.H[1].x-W.c.x, 2)+Math.pow(W.H[1].x-W.c.y, 2));
					if (d1<1 && d2>=1)
					{
						no_herbi-=1;
						W.c.health+=(2*W.H[0].health)/3;
						PQ.remove(W.H[0]);
					}
					else if (d1>=1 && d2<1)
					{
						no_herbi-=1;
						W.c.health+=(2*W.H[1].health)/3;
						PQ.remove(W.H[1]);
					}
					else if (d1<1 && d2<1)
					{
						no_herbi-=1;
						if (d1<d2)
						{
							W.c.health+=(2*W.H[0].health)/3;
							PQ.remove(W.H[0]);
						}
						else if (d1>d2)
						{
							W.c.health+=(2*W.H[1].health)/3;
							PQ.remove(W.H[1]);
						}
					}
					else
					{
						double dd1=Math.pow((W.G[0].x-W.c.x), 2)+Math.pow((W.G[0].y-W.c.y), 2);
						double dd2=Math.pow((W.G[1].x-W.c.x), 2)+Math.pow((W.G[1].y-W.c.y), 2);
						if (dd1>(W.G[0].radius*W.G[0].radius) && dd2>(W.G[1].radius*W.G[1].radius)) // if not inside any grassland
						{
							Random r=new Random();
							float chance=r.nextFloat();
							if (chance>=0.92)
								W.move_towards_herbivore(W.c, W.H, 4);
							else
								W.c.health-=60;
						}
						else
						{
							Random r=new Random();
							float chance=r.nextFloat();
							if (chance>=0.75)
								W.move_towards_herbivore(W.c, W.H, 2);
							else
								W.c.health-=30;
						}
					}
					double dist1=Math.sqrt(Math.pow(W.H[0].x-W.c.x, 2)+Math.pow(W.H[0].x-W.c.y, 2));
					double dist2=Math.sqrt(Math.pow(W.H[1].x-W.c.x, 2)+Math.pow(W.H[1].x-W.c.y, 2));
					if (dist1>5 && dist2>5)
					{
						if (W.c.no_turns>7)
							W.c.health-=6;
						W.c.no_turns+=1;
					}
					else
						W.c.no_turns=0;
				}
				else if (no_herbi==1)
				{
					int n=0;
					if (PQ.contains(W.H[0])==true)
						n=1;
					else
						n=2;
					if (n==1)
					{
						double d1=Math.sqrt(Math.pow(W.H[0].x-W.c.x, 2)+Math.pow(W.H[0].x-W.c.y, 2));
						if (d1<1)
						{
							no_herbi-=1;
							W.c.health+=(2*W.H[0].health)/3;
							PQ.remove(W.H[0]);
						}
						else
						{
							double dd1=Math.pow((W.G[0].x-W.c.x), 2)+Math.pow((W.G[0].y-W.c.y), 2);
							double dd2=Math.pow((W.G[1].x-W.c.x), 2)+Math.pow((W.G[1].y-W.c.y), 2);
							if (dd1>(W.G[0].radius*W.G[0].radius) && dd2>(W.G[1].radius*W.G[1].radius)) // if not inside any grassland
							{
								Random r=new Random();
								float chance=r.nextFloat();
								if (chance>=0.92)
									W.c.turn(W.H[0].x, W.H[0].y, 4, false);
								else
									W.c.health-=60;
							}
							else
							{
								Random r=new Random();
								float chance=r.nextFloat();
								if (chance>=0.75)
									W.c.turn(W.H[0].x, W.H[0].y, 2, false);
								else
									W.c.health-=30;
							}
						}
						double dist1=Math.sqrt(Math.pow(W.H[0].x-W.c.x, 2)+Math.pow(W.H[0].x-W.c.y, 2));
						if (dist1>5)
						{
							if (W.c.no_turns>7)
								W.c.health-=6;
							W.c.no_turns+=1;
						}
						else
							W.c.no_turns=0;
					}
					else
					{
						double d2=Math.sqrt(Math.pow(W.H[1].x-W.c.x, 2)+Math.pow(W.H[1].x-W.c.y, 2));
						if (d2<1)
						{
							no_herbi-=1;
							W.c.health+=(2*W.H[1].health)/3;
							PQ.remove(W.H[1]);
						}
						else
						{
							double dd1=Math.pow((W.G[0].x-W.c.x), 2)+Math.pow((W.G[0].y-W.c.y), 2);
							double dd2=Math.pow((W.G[1].x-W.c.x), 2)+Math.pow((W.G[1].y-W.c.y), 2);
							if (dd1>(W.G[0].radius*W.G[0].radius) && dd2>(W.G[1].radius*W.G[1].radius)) // if not inside any grassland
							{
								Random r=new Random();
								float chance=r.nextFloat();
								if (chance>=0.92)
									W.c.turn(W.H[1].x, W.H[1].y, 4, false);
								else
									W.c.health-=60;
							}
							else
							{
								Random r=new Random();
								float chance=r.nextFloat();
								if (chance>=0.75)
									W.c.turn(W.H[1].x, W.H[1].y, 2, false);
								else
									W.c.health-=30;
							}
						}
						double dist2=Math.sqrt(Math.pow(W.H[1].x-W.c.x, 2)+Math.pow(W.H[1].x-W.c.y, 2));
						if (dist2>5)
						{
							if (W.c.no_turns>7)
								W.c.health-=6;
							W.c.no_turns+=1;
						}
						else
							W.c.no_turns=0;
					}
				}
				else if (no_herbi==0)
				{
					double dd1=Math.pow((W.G[0].x-W.c.x), 2)+Math.pow((W.G[0].y-W.c.y), 2);
					double dd2=Math.pow((W.G[1].x-W.c.x), 2)+Math.pow((W.G[1].y-W.c.y), 2);
					if (dd1>(W.G[0].radius*W.G[0].radius) && dd2>(W.G[1].radius*W.G[1].radius)) // if not inside any grassland
					{
						W.c.health-=60;
					}
					else
					{
						W.c.health-=30;
					}
					if (W.c.no_turns>7)
						W.c.health-=6;
					W.c.no_turns+=1;
				}
				if (W.c.health>0)
				{
					Random r=new Random();
					int rs=r.nextInt(total_time-max_time)+max_time;
					if (rs<=(total_time-1))
					{
						W.c.timestamp=rs;
						max_time=W.FindMax(max_time, rs);
						PQ.add(W.c);
						System.out.println("It's health after taking turn is "+W.c.health);
					}
					else
					{
						System.out.println("It is dead.");
						no_carni-=1;
					}
				}
				else
				{
					System.out.println("It is dead.");
					no_carni-=1;
				}
			}
			turns+=1;
		}
	}
}
