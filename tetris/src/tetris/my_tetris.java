package tetris;

import java.awt.Color;
import java.awt.event.KeyEvent;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import acm.util.RandomGenerator;


public class my_tetris extends GraphicsProgram{

	RandomGenerator rgen = new RandomGenerator();
	GObject[] currpieces = new GObject[4];
	GObject[] currshadow = new GObject[4];
	boolean timefornew = true;
	GRect back;
	String currpiece;
	int blocksize = 25;
	int numrows = 10;
	int numcols = 20;
	int width = blocksize * numrows;
	int height = blocksize * numcols;
	int currstate = 0;
	double hingex = 0;
	double hingey = 0;
	boolean isturning = false;
	boolean ismoving = false;

	public void drawbckg() {
		back = new GRect(width, height);
		back.setFilled(true);
		back.setFillColor(Color.BLACK);
		add(back, (getWidth()/2-back.getWidth())/2, (getHeight()-back.getHeight())/2);
		
		for(int i = 0; i < numrows; i++) {
			GLine horz = new GLine(back.getX() + i * blocksize, back.getY(), back.getX() + i * blocksize, back.getY() + back.getHeight());
			horz.setColor(Color.WHITE);
			add(horz);
		}
		
		for(int i = 0; i < numcols; i++) {
			GLine vert = new GLine(back.getX(), back.getY() + i * blocksize, back.getX() + back.getWidth(), back.getY() + i * blocksize);
			vert.setColor(Color.WHITE);
			add(vert);
			GLabel indx = new GLabel(Double.toString(vert.getY()));
			add(indx, back.getX()-indx.getWidth(), back.getY() + i * blocksize);
		}
	}
	
	public GObject[] findlowest(GObject[] currpieces) {
		GObject[] temp = currpieces;
		for(int i=0; i<numcols; i++) {
			for(int j=0; j<temp.length; j++){
				if((getElementAt(temp[j].getX()+blocksize/2, temp[j].getY()+blocksize/10) != null && getElementAt(temp[j].getX()+blocksize/2, temp[j].getY()+blocksize/10) != back && !isthesamething(currpieces, getElementAt(temp[j].getX()+blocksize/2, temp[j].getY()+blocksize/10))) || temp[j].getY()+blocksize +5 > back.getHeight()+back.getY()) {
					if(temp[j].getY()+blocksize +5 > back.getHeight()+back.getY()) {
						for(int z=0; z<temp.length; z++){
							temp[z].setLocation(temp[z].getX()+blocksize/4, temp[z].getY()+blocksize/4);
						}
					} else {
						for(int z=0; z<temp.length; z++){
							temp[z].setLocation(temp[z].getX()+blocksize/4, temp[z].getY()-3*blocksize/4);
						}
					}
					return temp;
				}
			}
			for(int j=0; j<temp.length; j++){
				temp[j].setLocation(temp[j].getX(), temp[j].getY()+blocksize);
			}
		}
		for(int j=0; j<temp.length; j++){
			temp[j].setLocation(temp[j].getX()+blocksize/4, temp[j].getY()+blocksize/4);
		}
		return temp;
	}
	
	public void setshadow() {
		GObject[] temp = new GObject[4];
		GOval sone = new GOval(blocksize/2, blocksize/2);
		GOval stwo = new GOval(blocksize/2, blocksize/2);
		GOval sthree = new GOval(blocksize/2, blocksize/2);
		GOval sfour = new GOval(blocksize/2, blocksize/2);
		sone.setFilled(true);
		stwo.setFilled(true);
		sthree.setFilled(true);
		sfour.setFilled(true);
//		sone.setFillColor(currpieces[0].getColor());
//		stwo.setFillColor(currpieces[1].getColor());
//		sthree.setFillColor(currpieces[2].getColor());
//		sfour.setFillColor(currpieces[3].getColor());
		sone.setFillColor(Color.red);
		stwo.setFillColor(Color.red);
		sthree.setFillColor(Color.red);
		sfour.setFillColor(Color.red);
		sone.setLocation(currpieces[0].getX(), currpieces[0].getY());
		stwo.setLocation(currpieces[1].getX(), currpieces[1].getY());
		sthree.setLocation(currpieces[2].getX(), currpieces[2].getY());
		sfour.setLocation(currpieces[3].getX(), currpieces[3].getY());
		temp[0] = sone;
		temp[1] = stwo;
		temp[2] = sthree;
		temp[3] = sfour;
		currshadow = findlowest(temp);
		add(currshadow[0]);
		add(currshadow[1]);
		add(currshadow[2]);
		add(currshadow[3]);
	}
	
	
	public void drawL(double startX, double startY) {
		GRect one = new GRect(blocksize, blocksize);
		GRect two = new GRect(blocksize, blocksize);
		GRect three = new GRect(blocksize, blocksize);
		GRect four = new GRect(blocksize, blocksize);
		one.setFilled(true);
		two.setFilled(true);
		three.setFilled(true);
		four.setFilled(true);
		one.setFillColor(Color.ORANGE);
		two.setFillColor(Color.ORANGE);
		three.setFillColor(Color.ORANGE);
		four.setFillColor(Color.ORANGE);
		one.setLocation(startX, startY);
		two.setLocation(startX, startY + blocksize);
		three.setLocation(startX, startY + 2 * blocksize);
		four.setLocation(startX + blocksize, startY + 2 * blocksize);
		
		
		currpieces[0] = one;
		currpieces[1] = two;
		currpieces[2] = three;
		currpieces[3] = four;
		one.setLocation(startX, startY);
		two.setLocation(startX, startY + blocksize);
		three.setLocation(startX, startY + 2 * blocksize);
		four.setLocation(startX + blocksize, startY + 2 * blocksize);
		
		
		
//		setshadow();
		
		add(one);
		add(two);
		add(three);
		add(four);
		
		currpiece = "L";
		hingex = two.getX();
		hingey = two.getY();
	}
	
	public void drawrevL(double startX, double startY) {
		GRect one = new GRect(blocksize, blocksize);
		GRect two = new GRect(blocksize, blocksize);
		GRect three = new GRect(blocksize, blocksize);
		GRect four = new GRect(blocksize, blocksize);
		one.setFilled(true);
		two.setFilled(true);
		three.setFilled(true);
		four.setFilled(true);
		one.setFillColor(Color.BLUE);
		two.setFillColor(Color.BLUE);
		three.setFillColor(Color.BLUE);
		four.setFillColor(Color.BLUE);
		one.setLocation(startX, startY);
		two.setLocation(startX, startY + blocksize);
		three.setLocation(startX, startY + 2 * blocksize);
		four.setLocation(startX - blocksize, startY + 2 * blocksize);
		currpieces[0] = one;
		currpieces[1] = two;
		currpieces[2] = three;
		currpieces[3] = four;
		
//		setshadow();

		add(one);
		add(two);
		add(three);
		add(four);
		
		currpiece = "revL";
		hingex = two.getX();
		hingey = two.getY();
	}
	
	public void drawsquig(double startX, double startY) {
		GRect one = new GRect(blocksize, blocksize);
		GRect two = new GRect(blocksize, blocksize);
		GRect three = new GRect(blocksize, blocksize);
		GRect four = new GRect(blocksize, blocksize);
		one.setFilled(true);
		two.setFilled(true);
		three.setFilled(true);
		four.setFilled(true);
		one.setFillColor(Color.GREEN);
		two.setFillColor(Color.GREEN);
		three.setFillColor(Color.GREEN);
		four.setFillColor(Color.GREEN);
		one.setLocation(startX, startY);
		two.setLocation(startX - blocksize, startY);
		three.setLocation(startX - blocksize, startY + blocksize);
		four.setLocation(startX - 2 * blocksize, startY + blocksize);

		currpieces[0] = one;
		currpieces[1] = two;
		currpieces[2] = three;
		currpieces[3] = four;
		
//		setshadow();

		add(one);
		add(two);
		add(three);
		add(four);
		
		currpiece = "squig";
		hingex = two.getX();
		hingey = two.getY();
	}
	
	public void drawrevsquig(double startX, double startY) {
		GRect one = new GRect(blocksize, blocksize);
		GRect two = new GRect(blocksize, blocksize);
		GRect three = new GRect(blocksize, blocksize);
		GRect four = new GRect(blocksize, blocksize);
		one.setFilled(true);
		two.setFilled(true);
		three.setFilled(true);
		four.setFilled(true);
		one.setFillColor(Color.RED);
		two.setFillColor(Color.RED);
		three.setFillColor(Color.RED);
		four.setFillColor(Color.RED);
		one.setLocation(startX, startY);
		two.setLocation(startX + blocksize, startY);
		three.setLocation(startX + blocksize, startY + blocksize);
		four.setLocation(startX + 2 * blocksize, startY + blocksize);


		currpieces[0] = one;
		currpieces[1] = two;
		currpieces[2] = three;
		currpieces[3] = four;
		
//		setshadow();

		add(one);
		add(two);
		add(three);
		add(four);
		
		currpiece = "revsquig";
		hingex = two.getX();
		hingey = two.getY();
	}
	
	public void drawT(double startX, double startY) {
		GRect one = new GRect(blocksize, blocksize);
		GRect two = new GRect(blocksize, blocksize);
		GRect three = new GRect(blocksize, blocksize);
		GRect four = new GRect(blocksize, blocksize);
		one.setFilled(true);
		two.setFilled(true);
		three.setFilled(true);
		four.setFilled(true);
		one.setFillColor(Color.PINK);
		two.setFillColor(Color.PINK);
		three.setFillColor(Color.PINK);
		four.setFillColor(Color.PINK);
		one.setLocation(startX, startY);
		two.setLocation(startX + blocksize, startY);
		three.setLocation(startX + 2 * blocksize, startY);
		four.setLocation(startX + blocksize, startY + blocksize);

		currpieces[0] = one;
		currpieces[1] = two;
		currpieces[2] = three;
		currpieces[3] = four;
		
//		setshadow();
		
		add(one);
		add(two);
		add(three);
		add(four);
		
		currpiece = "T";
		hingex = two.getX();
		hingey = two.getY();
	}
	
	public void drawLine(double startX, double startY) {
		GRect one = new GRect(blocksize, blocksize);
		GRect two = new GRect(blocksize, blocksize);
		GRect three = new GRect(blocksize, blocksize);
		GRect four = new GRect(blocksize, blocksize);
		one.setFilled(true);
		two.setFilled(true);
		three.setFilled(true);
		four.setFilled(true);
		one.setFillColor(Color.CYAN);
		two.setFillColor(Color.CYAN);
		three.setFillColor(Color.CYAN);
		four.setFillColor(Color.CYAN);
		one.setLocation(startX, startY);
		two.setLocation(startX + blocksize, startY);
		three.setLocation(startX + 2 * blocksize, startY);
		four.setLocation(startX + 3 * blocksize, startY);

		currpieces[0] = one;
		currpieces[1] = two;
		currpieces[2] = three;
		currpieces[3] = four;
		
//		setshadow();

		add(one);
		add(two);
		add(three);
		add(four);
		
		currpiece = "l";
		hingex = two.getX();
		hingey = two.getY();
	}
	
	public void drawsquer(double startX, double startY) {
		GRect one = new GRect(blocksize, blocksize);
		GRect two = new GRect(blocksize, blocksize);
		GRect three = new GRect(blocksize, blocksize);
		GRect four = new GRect(blocksize, blocksize);
		one.setFilled(true);
		two.setFilled(true);
		three.setFilled(true);
		four.setFilled(true);
		one.setFillColor(Color.YELLOW);
		two.setFillColor(Color.YELLOW);
		three.setFillColor(Color.YELLOW);
		four.setFillColor(Color.YELLOW);
		one.setLocation(startX, startY);
		two.setLocation(startX + blocksize, startY);
		three.setLocation(startX, startY + blocksize);
		four.setLocation(startX + blocksize, startY + blocksize);
		
		currpieces[0] = one;
		currpieces[1] = two;
		currpieces[2] = three;
		currpieces[3] = four;
		
//		setshadow();
		
		add(one);
		add(two);
		add(three);
		add(four);
		
		currpiece = "square";
		hingex = two.getX();
		hingey = two.getY();
	}
	
	public void nextpiece(double X, double Y){
		
		if(timefornew) return;
		
		int num = rgen.nextInt(0, 6);
		switch (num) {
			case 0: drawL(X, Y); break;
			case 1: drawrevL(X, Y); break;
			case 2: drawsquig(X, Y); break;
			case 3: drawrevsquig(X, Y); break;
			case 4: drawT(X, Y); break;
			case 5: drawLine(X, Y); break;
			case 6: drawsquer(X, Y); break;
		}
		
	}
	
	boolean isthesamething(GObject[] currpieces, GObject piece) {
		for(int i=0; i<4; i++) if(currpieces[i] == piece) return true;
		return false;
	}
	
	public void init() {
		setSize(800, 600);
		addKeyListeners();
	}
	
	public void setstate(GObject[] currpieces, int state) {
		if(currpiece == "L") {
			if(state == 0) {
				currpieces[0].setLocation(hingex, hingey - blocksize);
				currpieces[2].setLocation(hingex, hingey + blocksize);
				currpieces[3].setLocation(hingex + blocksize, hingey + blocksize);
			} else if(state == 1) {
				currpieces[0].setLocation(hingex + blocksize, hingey);
				currpieces[2].setLocation(hingex - blocksize, hingey);
				currpieces[3].setLocation(hingex - blocksize, hingey + blocksize);
			} else if(state == 2) {
				currpieces[0].setLocation(hingex, hingey + blocksize);
				currpieces[2].setLocation(hingex, hingey - blocksize);
				currpieces[3].setLocation(hingex - blocksize, hingey - blocksize);
			} else if(state == 3) {
				currpieces[0].setLocation(hingex - blocksize, hingey);
				currpieces[2].setLocation(hingex + blocksize, hingey);
				currpieces[3].setLocation(hingex + blocksize, hingey - blocksize);
			}
			
		} else if(currpiece == "revL") {
			if(state == 0) {
				currpieces[0].setLocation(hingex, hingey - blocksize);
				currpieces[2].setLocation(hingex, hingey + blocksize);
				currpieces[3].setLocation(hingex - blocksize, hingey + blocksize);
			} else if(state == 1) {
				currpieces[0].setLocation(hingex + blocksize, hingey);
				currpieces[2].setLocation(hingex - blocksize, hingey);
				currpieces[3].setLocation(hingex - blocksize, hingey - blocksize);
			} else if(state == 2) {
				currpieces[0].setLocation(hingex, hingey + blocksize);
				currpieces[2].setLocation(hingex, hingey - blocksize);
				currpieces[3].setLocation(hingex + blocksize, hingey - blocksize);
			} else if(state == 3) {
				currpieces[0].setLocation(hingex - blocksize, hingey);
				currpieces[2].setLocation(hingex + blocksize, hingey);
				currpieces[3].setLocation(hingex + blocksize, hingey + blocksize);
			}
		} else if(currpiece == "squig") {
			if(state % 2 == 0) {
				currpieces[0].setLocation(hingex + blocksize, hingey);
				currpieces[2].setLocation(hingex, hingey + blocksize);
				currpieces[3].setLocation(hingex - blocksize, hingey + blocksize);
			} else if(state % 2 == 1) {
				currpieces[0].setLocation(hingex, hingey + blocksize);
				currpieces[2].setLocation(hingex - blocksize, hingey);
				currpieces[3].setLocation(hingex - blocksize, hingey - blocksize);
			}
		} else if(currpiece == "revsquig") {
			if(state % 2 == 0) {
				currpieces[0].setLocation(hingex - blocksize, hingey);
				currpieces[2].setLocation(hingex, hingey + blocksize);
				currpieces[3].setLocation(hingex + blocksize, hingey + blocksize);
			} else if(state % 2 == 1) {
				currpieces[0].setLocation(hingex, hingey - blocksize);
				currpieces[2].setLocation(hingex - blocksize, hingey);
				currpieces[3].setLocation(hingex - blocksize, hingey + blocksize);
			}
		} else if(currpiece == "T") {
			if(state == 0) {
				currpieces[0].setLocation(hingex - blocksize, hingey);
				currpieces[2].setLocation(hingex + blocksize, hingey);
				currpieces[3].setLocation(hingex, hingey + blocksize);
			} else if(state == 1) {
				currpieces[0].setLocation(hingex, hingey + blocksize);
				currpieces[2].setLocation(hingex, hingey - blocksize);
				currpieces[3].setLocation(hingex - blocksize, hingey);
			} else if(state == 2) {
				currpieces[0].setLocation(hingex + blocksize, hingey);
				currpieces[2].setLocation(hingex - blocksize, hingey);
				currpieces[3].setLocation(hingex, hingey - blocksize);
			} else if(state == 3) {
				currpieces[0].setLocation(hingex, hingey + blocksize);
				currpieces[2].setLocation(hingex, hingey - blocksize);
				currpieces[3].setLocation(hingex + blocksize, hingey);
			}
		} else if(currpiece == "l") {
			if(state % 2 == 0) {
				currpieces[0].setLocation(hingex - blocksize, hingey);
				currpieces[2].setLocation(hingex + blocksize, hingey);
				currpieces[3].setLocation(hingex + 2*blocksize, hingey);
			} else if(state % 2 == 1) {
				currpieces[0].setLocation(hingex, hingey - blocksize);
				currpieces[2].setLocation(hingex, hingey + blocksize);
				currpieces[3].setLocation(hingex, hingey + 2*blocksize);
			}
		} else if(currpiece == "square") {
			//lmao
		}
//		for(int i=0; i<4; i++) {
//			remove(currshadow[i]);
//			currshadow[i] = null;
//		}
//		setshadow();
	}
	
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(!isturning) {
				if(!rightside(currpieces)) {
					for(int i=0; i<4; i++) {
						currpieces[i].move(blocksize, 0);
//						remove(currshadow[i]);
//						currshadow[i] = null;
					}
					hingex = currpieces[1].getX();
					hingey = currpieces[1].getY();
//					setshadow();
				}
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(!isturning) {
				ismoving = true;
				if(!leftside(currpieces)) {
					for(int i=0; i<4; i++) {
//						remove(currshadow[i]);
//						currshadow[i] = null;
						currpieces[i].move(-blocksize, 0);
					}
					hingex = currpieces[1].getX();
					hingey = currpieces[1].getY();
//					setshadow();
				}
				ismoving = false;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(!isturning && !timefornew) {
				ismoving = true;
				if(!touchingbot(currpieces)) {
					for(int i=0; i<4; i++) {
						currpieces[i].move(0, blocksize);
					}
					hingex = currpieces[1].getX();
					hingey = currpieces[1].getY();
				} else {
					for(int i=0; i<4; i++) {
						currpieces[i] = null;
					}
					timefornew = true;
				}
				ismoving = false;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_V) {
			if(!ismoving) {
				isturning = true;
				if(currstate == 3) currstate = 0;
				else currstate++;
				setstate(currpieces, currstate);
				isturning = false;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_C) {
			if(!ismoving) {
				isturning = true;
				if(currstate == 0) currstate = 3;
				else currstate--;
				setstate(currpieces, currstate);
				isturning = false;
			}
		}
		
	}
	
	boolean rightside(GObject[] currpieces) {
		for(int i=0; i<4; i++) {
			if(currpieces[i].getX() > back.getX() + back.getWidth() - blocksize - blocksize/2) return true;
			if(getElementAt(currpieces[i].getX() + blocksize + blocksize/2, currpieces[i].getY() + blocksize/2) != null){
				if(!getElementAt(currpieces[i].getX() + blocksize + blocksize/2, currpieces[i].getY() + blocksize/2).equals(back) && !isthesamething(currpieces, getElementAt(currpieces[i].getX() + blocksize + blocksize/2, currpieces[i].getY() + blocksize/2))){
					return true;
				}
			}
		}
		return false;
	}
	
	boolean leftside(GObject[] currpieces) {
		for(int i=0; i<4; i++) {
			if(currpieces[i].getX() < back.getX() + blocksize/2) return true;
			if(getElementAt(currpieces[i].getX() - blocksize/2, currpieces[i].getY() + blocksize/2) != null){
				if(!getElementAt(currpieces[i].getX() - blocksize/2, currpieces[i].getY() + blocksize/2).equals(back) && !isthesamething(currpieces, getElementAt(currpieces[i].getX() - blocksize/2, currpieces[i].getY() + blocksize/2))){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean shadowoverlap(GObject obj){
		for(int i=0; i< currshadow.length; i++) {
			if(obj == currshadow[i]) return true;
		}
		return false;
	}
	
	boolean touchingbot(GObject[] currpieces) {
		try {
			for(int i=0; i<4; i++) {
				if(currpieces[i].getY() > back.getY() + back.getHeight() - blocksize - 10) return  true;
				if(getElementAt(currpieces[i].getX() + blocksize/2, currpieces[i].getY() + blocksize + blocksize/2) != null){
					if(!shadowoverlap(getElementAt(currpieces[i].getX() + blocksize/2, currpieces[i].getY() + blocksize + blocksize/2)) && !getElementAt(currpieces[i].getX() + blocksize/2, currpieces[i].getY() + blocksize + blocksize/2).equals(back) && !isthesamething(currpieces, getElementAt(currpieces[i].getX() + blocksize/2, currpieces[i].getY() + blocksize + blocksize/2))){
						return true;
					}
				}
			}
			return false;
		} catch (java.lang.NullPointerException e){
			return true;
		}
	}
	
	public void bringtopdown(int line) {
		for(int i = line-1; i>-1; i--) {
			for(int j = 0; j < numrows; j++) {
				double ex = back.getX() + blocksize/2 + j * blocksize;
				double ey = back.getY() + blocksize/2 + i * blocksize;
				if(!getElementAt(ex, ey).equals(back) && getElementAt(ex, ey) != null) {
					getElementAt(ex, ey).move(0, blocksize);
				}
			}
		}
	}
	
	public void checktenlines() {
		for(int i=0; i < numcols; i++) {
			GObject[] line = new GObject[10];
			int numofobjs = 0;
			for(int j=0; j<numrows; j++) {
				double ex = back.getX() + blocksize/2 + j*blocksize;
				double ey = back.getY() + blocksize/2 + i*blocksize;
				if(!getElementAt(ex, ey).equals(null) && !getElementAt(ex, ey).equals(back)) {
					line[numofobjs] = getElementAt(ex, ey);
					numofobjs++;
				}
			}
			if(numofobjs == numrows) {
				for(int z=0; z<numrows; z++) {
					remove(line[z]);
				}
				pause(500);
				cleared += numofobjs;
				if(i != 0) bringtopdown(i);
			}
		}
	}
	int cleared = 0;
	
	public void run() {
		drawbckg();
		int speed = 1000;
		while(true) {
			if(timefornew) {
				if(!getElementAt(back.getX() + back.getWidth()/2 + blocksize/2, back.getY() + blocksize/2).equals(null) && !getElementAt(back.getX() + back.getWidth()/2 + blocksize/2, back.getY() + blocksize/2).equals(back)) break;
				timefornew = false;
				nextpiece(back.getX() + back.getWidth()/2, back.getY());
			} else {
				if(!timefornew && !touchingbot(currpieces)) {
					if(!isturning) {
						ismoving = true;
						for(int i=0; i<4; i++) {
							currpieces[i].move(0, blocksize);
						}
						hingex = currpieces[1].getX();
						hingey = currpieces[1].getY();
						ismoving = false;
//						

					
					}
				} else {
					for(int i=0; i<4; i++) {
						currpieces[i] = null;
//						remove(currshadow[i]);
//						currshadow[i] = null;
					}
					timefornew = true;
					checktenlines();
					currstate = 0;
					if(cleared > 5) speed = 5*speed/6; cleared = 0;
				}
			}
			pause(speed);
		}
	}
}
