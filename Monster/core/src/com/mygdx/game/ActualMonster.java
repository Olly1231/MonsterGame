package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ActualMonster {

        private int x,y,w,h;
        private Color col;
        private int xfactor, yfactor;
        private int speed;

        public ActualMonster(int x, int y,int w, int h, int xfactor, int yfactor, Color c){
            this.x=x;
            this.y=y;
            this.w=w;
            this.h=h;
            this.xfactor=xfactor;
            this.yfactor=yfactor;
            col=c;
        }
        public void draw(ShapeRenderer sr){
            sr.setColor(col);
            sr.rect(x,y,w,h);
        }
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }


        public Color getCol() {
            return col;
        }

        public void setCol(Color col) {
            this.col = col;
        }
        public int getXfactor(){
            return xfactor;
        }
        public void setXfactor(int xfactor){
            this.xfactor = xfactor;
        }
        public int getYfactor(){
            return yfactor;
        }
        public int getSpeed(){
            return speed;
        }
        public void setSpeed(int speed){
            this.speed = speed;
        }
        public void setYfactor(int yfactor){
            this.yfactor = yfactor;
        }
    }




