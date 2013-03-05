package com.ucfbrickbreaker.brickbreak;
import java.awt.Color;
import java.awt.Graphics;


public class Score extends FallingObject{

private static final long serialVersionUID = 1L;

public int value;


public Score(int x, int y, int value) {
super(x, y);
this.value = value;
}


public void paintComponent(Graphics g){
super.paintComponent(g);
g.setColor(Color.black);
g.drawString(""+value, x, y);
}

@Override
public void acquireObject() {
GUI.score += value;
}

}
