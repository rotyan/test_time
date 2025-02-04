import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

/*
画地图、画边界、分图标、设置改变状态、设置重置按钮
*/

//Class GamePanel
//The main class of the game

class GamePanel extends JPanel implements KeyListener
{
 private static final long serialVersionUID = 1L;
 
 private char[][] map; //Map state
 private char[][] box;
 private int sX,sY;  //Map size
 private Point player,p; //Point of player
 private int Num=0;  //Number of boxs
 private Point[] boxs; //Points of boxs;
 //private Point[] trg; //Points of target
 //private Point[] path;
 //private int steps;
 private Point[] dir;
 
 private int size;
 private Image imgfloor;
 private Image imgwall;
 private Image imgplayer;
 private Image imgbox1;
 private Image imgbox2;
 private Image imgtrg;
 
 //method
 public GamePanel(char b[][],int w,int h,int size)throws Exception
 {
  
  sX = b.length;
  sY = b[0].length;
  Num = 0;
  
  map =   new char[sX][sY];
  box =   new char[sX][sY];
  player= new Point();
  p = new Point();
  boxs= new Point[sX*sY];
  
  dir = new Point[4];
  dir[0]= new Point(0,-1); //left
  dir[1]= new Point(-1,0); //up
  dir[2]= new Point(0, 1); //right
  dir[3]= new Point(1, 0); //down
  
  File parent = new File("E:\\java_game");
  
  imgfloor = ImageIO.read(new File(parent,"Box_floor.gif"));  
  imgwall  = ImageIO.read(new File(parent,"Box_wall.gif"));
  imgplayer= ImageIO.read(new File(parent,"Box_player.gif"));
  imgtrg  = ImageIO.read(new File(parent,"Box_trg.gif"));
  imgbox1  = ImageIO.read(new File(parent,"Box_box1.gif"));
  imgbox2  = ImageIO.read(new File(parent,"Box_box2.gif"));
  
  this.size=size;
  
  //set Map
  for(int i=0;i<sX;i++){ 
   for(int j=0;j<sY;j++){
    switch(b[i][j]){
     case 'P': //Player
      map[i][j]='.';
      p.setLocation(i, j);
      break;
     case 'X': //Target
      map[i][j]='X';
      break;
     case 'B': //Box
      map[i][j]='.';
      boxs[Num++]=new Point(i, j);
      break;
     case '#': //Wall
      map[i][j]='#';
      break;
     case '.': //floor
      map[i][j]='.';
      break;
     default :
      map[i][j]='.';
      break;
    }
   }
  }//end set Map
  
  reset(); //reset Game
 }
 
 public void reset(){
  player.setLocation(p);
  for(int i=0;i<sX;i++)
   for(int j=0;j<sY;j++)
    box[i][j]=' ';
  for(int i=0;i<Num;i++)
   box[boxs[i].x][boxs[i].y]='B';
  //steps=0;
  repaint();
 }
 
 public void undo(){
  
 }
 
 public boolean judge(){
  for(int i=0;i<sX;i++)
   for(int j=0;j<sY;j++)
    if(box[i][j]=='B'&&map[i][j]!='X')
     return false;
  return true;
 }
 
 public void paint(Graphics g){
  //draw map
  for(int i=0;i<sX;i++){
   for(int j=0;j<sY;j++){
    switch(map[i][j]){
     case '.':
      g.drawImage(imgfloor, j*size, i*size, null);
      break;
     case '#':
      g.drawImage(imgwall, j*size, i*size, null);
      break;
     case 'X':
      g.drawImage(imgfloor, j*size, i*size, null);
      break;
    }
    if(box[i][j]=='B'&&map[i][j]=='X') g.drawImage(imgbox2, j*size, i*size, null);
    else if(box[i][j]=='B') g.drawImage(imgbox1, j*size, i*size, null);
    else if(map[i][j]=='X') g.drawImage(imgtrg, j*size, i*size, null);
   }
  }
  //draw player
  g.drawImage(imgplayer, player.y*size, player.x*size, null);
  
  g.drawRect(0, 0, sY*size, sX*size);
  
  //get Focus
  this.requestFocusInWindow(); /* ********* */
 }
 
 
 /* methods of KeyListener */
 
 public void keyPressed(KeyEvent e){
  int x=player.x;
  int y=player.y;
  int k,x0,y0;
  
  k=e.getKeyCode()-37;
  
  if(k<0||k>=4) return;
  
  x+=dir[k].x;
  y+=dir[k].y;

  if(x<0||x>=sX||y<0||y>=sY) return;
  if(map[x][y]=='#') return;
  
  if(box[x][y]!='B'&&(map[x][y]=='.'||map[x][y]=='X')){
   player.x=x;
   player.y=y;
  }
  if(box[x][y]=='B'){
   x0=x+dir[k].x;
   y0=y+dir[k].y;
   if(x0<0||y0<0||x0>=sX||y0>=sY) return;
   if(map[x0][y0]!='#'&&box[x0][y0]!='B'){
    box[x][y]=' ';
    box[x0][y0]='B';
    player.x=x;
    player.y=y;
   }
  }
  
  repaint();
  
  if(judge()){
            JOptionPane.showMessageDialog(null,"You win!");
   reset();
  }
 }
 
 public void keyReleased(KeyEvent e){}
 public void keyTyped(KeyEvent e){}
 
}


 
//Class PushBox
//Creat JFrame to Play the game

public class PushBox extends JFrame
{
 private static final long serialVersionUID = 1L;
 
 private JPanel top;
 private GamePanel center;
 private JPanel bottom;
 
 private JButton breset; //Button of reset
 private JButton bundo; //Button of undo
 
 private int width,height,size;
 private int sX,sY;
 
 //method of constitution
 public PushBox(char[][] b)throws Exception{
  super("PushBox Game");

  //set Window
  size=35;
  sX=b.length;
  sY=b[0].length;
  width=size*sY+10;
  height=size*sX+100;
  
  top     = new JPanel();
  center  = new GamePanel(b,width,height-100,size); //Panel of Game
  bottom  = new JPanel();
  breset = new JButton("Reset");
  bundo = new JButton("←Undo");
  
  //add Listener
  center.addKeyListener(center);
  breset.addActionListener(new ResetListener());
  bundo.addActionListener(new UndoListener());
  
  top.add(breset);
  //top.add(bundo);
  add(top,"North");
  add(center,"Center");
  add(bottom,"South");
  
  setSize(width,height);
  setVisible(true);
 }
 
 //classes of Listener
 class ResetListener implements ActionListener{
  public void actionPerformed(ActionEvent e){
   center.reset();
  }
 }
 class UndoListener implements ActionListener{
  public void actionPerformed(ActionEvent e){
   center.undo();
  }
 }
  
 //method main
 public static void main(String[] args)throws Exception{
  char[][] b=new char[5][6];
  
  b[0]="##X##P".toCharArray();
  b[1]="##.##.".toCharArray();
  b[2]=".B.#..".toCharArray();
  b[3]="X..B..".toCharArray();
  b[4]="##....".toCharArray();
  
  PushBox p=new PushBox(b);
  p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
}