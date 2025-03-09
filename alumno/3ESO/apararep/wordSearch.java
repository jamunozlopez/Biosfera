/*
Written by Jeremiah McLeod
Email: xdebugx@hotmail.com
webpage: http://www.angelfire.com/il/xdebugx/
*/

import java.awt.*;
import java.awt.image.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.event.*;
public class wordSearch extends Applet implements MouseMotionListener,MouseListener {

  Graphics grBuffer;  //main graphics object

  int height,width;  //height and width of applet

  Image imgBuffer;

  char board[] = new char [750];
  boolean boardColor[] = new boolean [750];
  boolean tempBoardColor[] = new boolean [750];
  boolean wordFound[] = new boolean[25];
  char words[] [] = new char [10] [25];
  int wordDirection[] = new int [10];
  int wordPlace[] = new int [10];
  final int boardXOffset = 100;
  final int boardYOffset = 25;
  final int numWords=10;
  String strWords[] = new String [1000];
  int lettersPerWord [] = new int [14];
  int numWordsInFile;
  String backwards,forwards;
  int mouseDestinationX,mouseDestinationY,mouseSourceX,mouseSourceY;
  boolean isMouseDown=false;
  String whichWords="RANDOM";
static int randomNumber (int low, int high) {
   		// returns a random number between low and high, inclusive
   		return (int)(java.lang.Math.random() * (high - low + 1) + low);
	} // randomNumber


public void init () {
int p,t,g;
int directionAddition;
String fileName;
boolean foundPlace;
StringTokenizer st;
String line;
p=0;
// get parameters
fileName = getParameter("fileName");
whichWords = getParameter("whichWords");
whichWords=whichWords.toUpperCase();
//read file

//setup mouse
addMouseMotionListener (this);
addMouseListener (this);



		try {
			URL url = new URL(getCodeBase(), fileName);

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
               do {// start of DO WHILE LOOP
                    line= in.readLine();
                    st=new StringTokenizer (line,"+");
                    while (st.hasMoreTokens()) {
					strWords[p]=st.nextToken();
					if (strWords[p]!=null) {
					strWords[p]=strWords[p].toUpperCase();
					p+=1;
				    } //if
				    } //while more tokens

					} while (strWords[p] != null);
					in.close ();
			} catch (IOException e) { }
		} catch (MalformedURLException e) { }
numWordsInFile=p-1;

	// put file words to words array
if (whichWords.equals("FIRST")) {
	for (p=0;p<10;p++) {
		for (t=0;t<strWords[p].length();t++) words [p] [t] = strWords[p].charAt(t);
		lettersPerWord[p]=strWords[p].length();
		} // p
} // if whichwords FIRST

if (whichWords.equals("RANDOM")) {
int wordsToRead[] = new int [14];
for (p=0;p<10;p++) {
		wordsToRead[p]=randomNumber (0,numWordsInFile-1);
	    for (t=0;t<p;t++)
			if (wordsToRead[t]==wordsToRead[p])
				p-=1;
}
	for (p=0;p<10;p++) {
		for (t=0;t<strWords[wordsToRead[p]].length();t++) words [p] [t] = strWords[wordsToRead[p]].charAt(t);
		lettersPerWord[p]=strWords[wordsToRead[p]].length();
		} // p


} // which words random







height = size ().height;      //get height and width
width = size().width;
size().height=100;
size().width=100;

 imgBuffer = createImage (width, height); //make buffer image
grBuffer = imgBuffer.getGraphics ();    //associate buffer with buffer image

// fill board with chars
for (p=0;p<749;p++) board[p]= ( (char) randomNumber(65,65+25) );
// set color of chars
for (p=0;p<749;p++) boardColor[p]=false;
// put words to board ///////////////////////////////////////////////
directionAddition=0;
// find place to put words
for (p=0;p<10;p++) {
	foundPlace=false;
	while (foundPlace==false) {
	wordDirection[p]=randomNumber (0,7);
	wordPlace[p] = randomNumber (0,193);
	if (wordDirection[p]==0 && (int) (wordPlace[p]/15)-lettersPerWord[p]>=0) foundPlace=true; //up
	if (wordDirection[p]==1 && (int) (wordPlace[p]/15)+lettersPerWord[p]<=12) foundPlace=true;  //down
	if (wordDirection[p]==2 && wordPlace[p]%15-lettersPerWord[p]>=0) foundPlace=true; // left
	if (wordDirection[p]==3 && wordPlace[p]%15+lettersPerWord[p]<=14) foundPlace=true; // right
    if (wordDirection[p]==4 && (int) (wordPlace[p]/15)-lettersPerWord[p]>=0)
    if (wordPlace[p]%15-lettersPerWord[p]>=0) foundPlace=true; // up left
	if (wordDirection[p]==5 && (int) (wordPlace[p]/15)-lettersPerWord[p]>=0)
	if (wordPlace[p]%15+lettersPerWord[p]<=14) foundPlace=true; //up right
    if (wordDirection[p]==6 && (int) (wordPlace[p]/15)+lettersPerWord[p]<=12)
    if (wordPlace[p]%15-lettersPerWord[p]>=0) foundPlace=true; //down left
	if (wordDirection[p]==7 && (int) (wordPlace[p]/15)+lettersPerWord[p]<=12)
	if (wordPlace[p]%15+lettersPerWord[p]<=14) foundPlace=true; //down right
    //check if char already written doesnt match
    if (foundPlace==true) for (g=0;g<lettersPerWord[p];g++)  {
		if (wordDirection[p]==0) directionAddition = -15;
		if (wordDirection[p]==1) directionAddition = +15;
		if (wordDirection[p]==2) directionAddition = -1;
		if (wordDirection[p]==3) directionAddition = +1;
		if (wordDirection[p]==4) directionAddition = -16;
		if (wordDirection[p]==5) directionAddition = -14;
		if (wordDirection[p]==6) directionAddition = +14;
		if (wordDirection[p]==7) directionAddition = +16;
		if ((boardColor[wordPlace[p]+(g*directionAddition)] == true) && (board[wordPlace[p]+(g*directionAddition)])!= ((char) words[p] [g])) foundPlace=false;
       }//for g
    } // while

//put word chars to board array of chars
for (t=0;t<lettersPerWord[p];t++) {
if (wordDirection[p]==0) directionAddition = -15;
if (wordDirection[p]==1) directionAddition = +15;
if (wordDirection[p]==2) directionAddition = -1;
if (wordDirection[p]==3) directionAddition = +1;
if (wordDirection[p]==4) directionAddition = -16;
if (wordDirection[p]==5) directionAddition = -14;
if (wordDirection[p]==6) directionAddition = +14;
if (wordDirection[p]==7) directionAddition = +16;
board[wordPlace[p]+(t*directionAddition)]=(char) words[p] [t];
boardColor[wordPlace[p]+(t*directionAddition)] = true;
}//t


}// p

//set color of chars
for (p=0;p<749;p++) {
	boardColor[p]=false;
    tempBoardColor[p]=false;
}

for (p=0;p<20;p++) wordFound[p]=false;

}

public void paint (Graphics g) {
int p,t,index;
String letter;
String word;
Font normal=new Font ("TimesRoman", Font.BOLD, 10);
Font wordList=new Font ("TimesRoman", Font.BOLD, 9);
Font fntLetters=new Font ("TimesRoman",Font.BOLD,25);

//erase screen
grBuffer.setColor(Color.green);
grBuffer.fillRect (0,0,640,400);

// draw system to grbuffer
grBuffer.setFont (normal);
grBuffer.setColor (Color.yellow);
grBuffer.fillRect (560,20,70,30);
grBuffer.fillRect (560,70,70,30);
grBuffer.setColor (Color.blue);
grBuffer.drawString ("NEW SEARCH",560,40);
grBuffer.drawString ("SOLVE",575,90);


// draw words
grBuffer.setFont (wordList);
for (p=0;p<numWords;p++) {
	word="";
		for (t=0;t<lettersPerWord [p];t++)
			word = word + words[p] [t];

	if (wordFound[p]==true) grBuffer.setColor(Color.blue);
	else grBuffer.setColor(Color.red);
	grBuffer.drawString (word,3,(p*15)+15);
}
grBuffer.setColor (Color.blue);
grBuffer.drawString (forwards,3,270);
grBuffer.drawString (backwards,3,285);
grBuffer.setFont (new Font ("TimesRoman", Font.BOLD, 13));
grBuffer.setColor (Color.yellow);
grBuffer.drawString ("Word Selected:",3,250);




// draw board of chars
grBuffer.setFont (normal);
index=0;

grBuffer.setColor (Color.black);
//grBuffer.fillRect (0+boardXOffset-5,0,455,400);
for (p=0;p<200;p++) {
	grBuffer.drawLine (0+boardXOffset-5,p,0+boardXOffset-5+453,p);
	Color a =new Color (0,55+p,0);
	grBuffer.setColor (a);
}

for (p=0;p<200;p++) {
	grBuffer.drawLine (0+boardXOffset-5,p+200,0+boardXOffset-5+453,p+200);
	Color a =new Color (0,55+(200-p),0);
	grBuffer.setColor (a);
}

grBuffer.setColor (Color.red);
grBuffer.setFont (fntLetters);
for (p=0;p<13;p++)
	for (t=0;t<15;t++) {
		if (boardColor[(p*15)+t]==true) grBuffer.setColor (Color.blue);
		else grBuffer.setColor (Color.red);
		if (tempBoardColor[(p*15)+t]==true && isMouseDown==true) grBuffer.setColor (Color.blue);
		letter = "" + board[index++];
 		grBuffer.drawString (letter,boardXOffset + (t * 30),boardYOffset + (p *30));
	} // for t

//draw circle
grBuffer.setColor (Color.blue);
if (isMouseDown==true) {
grBuffer.drawLine (mouseSourceX,mouseSourceY,mouseDestinationX,mouseDestinationY);
}



// draw image to screen
g.drawImage (imgBuffer, 0, 0, this);

}



public void update (Graphics g) {

        paint (g);
    }


public void mouseDragged (MouseEvent e) {
    int p;
        if (isMouseDown==false) {
       	mouseSourceX=e.getX();
		mouseSourceY=e.getY();
        for (p=0;p<200;p++) tempBoardColor[p]=false;

	}  else {
		for (p=0;p<200;p++) tempBoardColor[p]=false;


			mouseDestinationX=e.getX();
			mouseDestinationY=e.getY();
	        //System.out.println ("destination X " + mouseDestinationX + "       Y " + mouseDestinationY);
	             proccessChoice ();
    }
     isMouseDown=true;


    repaint ();
	}

public void mouseMoved (MouseEvent me) {
if (isMouseDown==true)  {
    isMouseDown=false;
}
forwards="";
backwards="";
    repaint ();

}





void proccessChoice () {
	double dcurrentX=0.0000000;
	double dcurrentY=0.0000000;
	double plus=0.0000000;
	int currentX=mouseSourceX;
	int currentY=mouseSourceY;
	int charnum=0;
	int t,p;
	int wordSelectedIndex=0;
	char wordSelected[]= new char [255];
	dcurrentX=currentX;
	dcurrentY=currentY;
	boolean whichPlus=false;
	String word="";
	int directionAddition=0;
	if (abs (mouseDestinationX-mouseSourceX) > abs (mouseDestinationY-mouseSourceY)) {
	whichPlus = true;
	plus = (double) ((double) (abs (mouseDestinationY-mouseSourceY)) / (double) (abs (mouseDestinationX-mouseSourceX)));
	}  //if X<Y
	else
	{
	whichPlus = false;;
	plus = (double) ((double) (abs (mouseDestinationX-mouseSourceX)) / (double) (abs (mouseDestinationY-mouseSourceY)));
	} // if else X<Y

//System.out.println ("msx: " + mouseSourceX +" msy:" + mouseSourceY+ "    mdx" +mouseDestinationX + " mdy"+mouseDestinationY + "   whichplus:"+whichPlus+"     plus: " + plus);

while (currentX<mouseDestinationX-3 || currentX>mouseDestinationX+3 || currentY<mouseDestinationY-3 || currentY>mouseDestinationY+3) {
if (whichPlus==true) {
if (mouseDestinationX>mouseSourceX) dcurrentX=dcurrentX+1d;
if (mouseDestinationX<mouseSourceX) dcurrentX=dcurrentX-1d;
if (mouseDestinationY>mouseSourceY) dcurrentY=dcurrentY+plus;
if (mouseDestinationY<mouseSourceY) dcurrentY=dcurrentY-plus;
}
if (whichPlus==false) {
if (mouseDestinationX>mouseSourceX) dcurrentX=dcurrentX+plus;
if (mouseDestinationX<mouseSourceX) dcurrentX=dcurrentX-plus;
if (mouseDestinationY>mouseSourceY) dcurrentY=dcurrentY+1d;
if (mouseDestinationY<mouseSourceY) dcurrentY=dcurrentY-1d;
}
currentX=(int) (dcurrentX);
currentY=(int) (dcurrentY);
for (p=0;p<13;p++)
	for (t=0;t<15;t++)
if (currentX>boardXOffset+(t*30) && currentX<boardXOffset+(t*30)+20 && currentY<boardYOffset+(p*30) && currentY>boardYOffset+(p*30)-20) {
charnum=t+(p*15);
tempBoardColor[charnum]=true;

}//if charnum
} // while
forwards="";
backwards="";
wordSelectedIndex=0;
for (p=0;p<195;p++) if (tempBoardColor[p]==true) {
wordSelected[wordSelectedIndex]=board[p];
wordSelectedIndex++;
} // for p

if (wordSelectedIndex>0) {
  for (p=0;p<wordSelectedIndex;p++) forwards=forwards+wordSelected[p];
  for (p=wordSelectedIndex-1;p>=0;p--) backwards=backwards+wordSelected[p];
  for (p=0;p<numWords;p++) {
	  word="";
	  	for (t=0;t<lettersPerWord [p];t++)
	  		word = word + words[p] [t];
if (forwards.equals(word) || backwards.equals(word)) {
wordFound[p]=true;
for (t=0;t<lettersPerWord[p];t++) {
if (wordDirection[p]==0) directionAddition = -15;
if (wordDirection[p]==1) directionAddition = +15;
if (wordDirection[p]==2) directionAddition = -1;
if (wordDirection[p]==3) directionAddition = +1;
if (wordDirection[p]==4) directionAddition = -16;
if (wordDirection[p]==5) directionAddition = -14;
if (wordDirection[p]==6) directionAddition = +14;
if (wordDirection[p]==7) directionAddition = +16;
boardColor[wordPlace[p]+(t*directionAddition)] = true;
}// if forwards.equals word
}//t
} // for p
} // if wordSelectedIndex
} // processchoice




// returns absolute value of integer
int abs (int x) {
if (x<0) x=x*-1;
return (x);
}

void newSearch () {
	int p,t,g;
	int directionAddition;
	boolean foundPlace;

	// put file words to words array
if (whichWords.equals("FIRST")) {
	for (p=0;p<10;p++) {
		for (t=0;t<strWords[p].length();t++) words [p] [t] = strWords[p].charAt(t);
		lettersPerWord[p]=strWords[p].length();
		} // p
} // if whichwords FIRST

if (whichWords.equals("RANDOM")) {
int wordsToRead[] = new int [14];
for (p=0;p<10;p++) {
		wordsToRead[p]=randomNumber (0,numWordsInFile-1);
	    for (t=0;t<p;t++)
			if (wordsToRead[t]==wordsToRead[p])
				p-=1;
}
	for (p=0;p<10;p++) {
		for (t=0;t<strWords[wordsToRead[p]].length();t++) words [p] [t] = strWords[wordsToRead[p]].charAt(t);
		lettersPerWord[p]=strWords[wordsToRead[p]].length();
		} // p


} // which words random
// fill board with chars
for (p=0;p<749;p++) board[p]= ( (char) randomNumber(65,65+25) );
// set color of chars
for (p=0;p<749;p++) boardColor[p]=false;
// put words to board ///////////////////////////////////////////////
directionAddition=0;
// find place to put words
for (p=0;p<10;p++) {
	foundPlace=false;
	while (foundPlace==false) {
	wordDirection[p]=randomNumber (0,7);
	wordPlace[p] = randomNumber (0,193);
	if (wordDirection[p]==0 && (int) (wordPlace[p]/15)-lettersPerWord[p]>=0) foundPlace=true; //up
	if (wordDirection[p]==1 && (int) (wordPlace[p]/15)+lettersPerWord[p]<=12) foundPlace=true;  //down
	if (wordDirection[p]==2 && wordPlace[p]%15-lettersPerWord[p]>=0) foundPlace=true; // left
	if (wordDirection[p]==3 && wordPlace[p]%15+lettersPerWord[p]<=14) foundPlace=true; // right
    if (wordDirection[p]==4 && (int) (wordPlace[p]/15)-lettersPerWord[p]>=0)
    if (wordPlace[p]%15-lettersPerWord[p]>=0) foundPlace=true; // up left
	if (wordDirection[p]==5 && (int) (wordPlace[p]/15)-lettersPerWord[p]>=0)
	if (wordPlace[p]%15+lettersPerWord[p]<=14) foundPlace=true; //up right
    if (wordDirection[p]==6 && (int) (wordPlace[p]/15)+lettersPerWord[p]<=12)
    if (wordPlace[p]%15-lettersPerWord[p]>=0) foundPlace=true; //down left
	if (wordDirection[p]==7 && (int) (wordPlace[p]/15)+lettersPerWord[p]<=12)
	if (wordPlace[p]%15+lettersPerWord[p]<=14) foundPlace=true; //down right
    //check if char already written doesnt match
    if (foundPlace==true) for (g=0;g<lettersPerWord[p];g++)  {
		if (wordDirection[p]==0) directionAddition = -15;
		if (wordDirection[p]==1) directionAddition = +15;
		if (wordDirection[p]==2) directionAddition = -1;
		if (wordDirection[p]==3) directionAddition = +1;
		if (wordDirection[p]==4) directionAddition = -16;
		if (wordDirection[p]==5) directionAddition = -14;
		if (wordDirection[p]==6) directionAddition = +14;
		if (wordDirection[p]==7) directionAddition = +16;
		if ((boardColor[wordPlace[p]+(g*directionAddition)] == true) && (board[wordPlace[p]+(g*directionAddition)])!= ((char) words[p] [g])) foundPlace=false;
       }//for g
    } // while

//put word chars to board array of chars
for (t=0;t<lettersPerWord[p];t++) {
if (wordDirection[p]==0) directionAddition = -15;
if (wordDirection[p]==1) directionAddition = +15;
if (wordDirection[p]==2) directionAddition = -1;
if (wordDirection[p]==3) directionAddition = +1;
if (wordDirection[p]==4) directionAddition = -16;
if (wordDirection[p]==5) directionAddition = -14;
if (wordDirection[p]==6) directionAddition = +14;
if (wordDirection[p]==7) directionAddition = +16;
board[wordPlace[p]+(t*directionAddition)]=(char) words[p] [t];
boardColor[wordPlace[p]+(t*directionAddition)] = true;
}//t
}// p

//set color of chars
for (p=0;p<749;p++) {
	boardColor[p]=false;
    tempBoardColor[p]=false;
}

for (p=0;p<20;p++) wordFound[p]=false;
repaint ();

}
void solve () {
	int p,t;
	int directionAddition=0;
	for (p=0;p<numWords;p++)
	for (t=0;t<lettersPerWord[p];t++) {
	if (wordDirection[p]==0) directionAddition = -15;
	if (wordDirection[p]==1) directionAddition = +15;
	if (wordDirection[p]==2) directionAddition = -1;
	if (wordDirection[p]==3) directionAddition = +1;
	if (wordDirection[p]==4) directionAddition = -16;
	if (wordDirection[p]==5) directionAddition = -14;
	if (wordDirection[p]==6) directionAddition = +14;
	if (wordDirection[p]==7) directionAddition = +16;
boardColor[wordPlace[p]+(t*directionAddition)] = true;
}//t
repaint ();

}// solve


public void mouseClicked (MouseEvent me) {
	int x,y;
	x=me.getX();
	y=me.getY();
	if (x>=560 && y>=20 && x<=640 && y<=50)  newSearch ();
	if (x>=560 && y>=70 && x<=640 && y<=100) solve ();
}

public void mousePressed (MouseEvent me) {

}
public void mouseReleased (MouseEvent me) {
}
public void mouseEntered (MouseEvent me) {
}
public void mouseExited (MouseEvent me) {
}

} //class wordSearch