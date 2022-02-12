import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import java.awt.event.KeyEvent; 
import java.io.IOException; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class radar extends PApplet {

 // Th\u01b0 vi\u1ec7n Serial
 // Th\u01b0 vi\u1ec7n \u0111\u1ecdc d\u1eef li\u1ec7u

Serial myPort; // X\u00e1c \u0111\u1ecbnh \u0111\u1ed1i t\u01b0\u1ee3ng
 
// Khai b\u00e1o c\u00e1c bi\u1ebfn :v
String angle="";
String distance="";
String data="";
String noObject;
float pixsDistance;
int iAngle, iDistance;
int index1=0;
int index2=0;
PFont orcFont;
public void setup() {
    size (1920, 1080); // Thi\u1ebft l\u1eadp m\u00e0n h\u00ecnh
    smooth();
    myPort = new Serial(this,"COM5", 9600); // Ch\u1ecdn giao ti\u1ebfp c\u1ed5ng COM4, t\u1ed1c \u0111\u1ed9 9600
    myPort.bufferUntil('.'); // \u0110\u1ecdc d\u1eef li\u1ec7u t\u1eeb c\u1ed5ng COM4...\u0111\u1ebfn khi nh\u1eadn \u0111c d\u1ea5u '.' th\u1ef3 d\u1eebng l\u1ea1i....!!!! ( Processing s\u1ebd nh\u1eadn \u0111c gi\u00e1 tr\u1ecb nh\u01b0 ph\u1ea7n Code Arduino \u0111\u00e3 g\u1eedi)
    orcFont = loadFont("OCRAExtended-30.vlw");//Ch\u1ecdn Font cho n\u00f3 nguy hi\u1ec3m t\u00fd, c\u00e1c b\u1ea1n c\u00f3 th\u1ec3 ch\u1ecdn Font kh\u00e1c theo \u00fd m\u00ecnh
}
public void draw() {
    fill(98,245,31);
    textFont(orcFont);
    // C\u00e1i n\u00e0y m\u00ecnh c\u0169ng kh\u00f4ng hi\u1ec3u l\u1eafm...h\u00ecnh nh\u01b0 n\u00f3 m\u00f4 ph\u1ecfng d\u00f2ng chuy\u1ec3n \u0111\u1ed9ng...M\u00e0 th\u00f4i,c\u00e1c b\u1ea1n c\u1ee9 coppy cho nhanh
    noStroke();
    fill(0,4);
    rect(0, 0, width, height-height*0.065f);
    fill(98,245,31); // green color
    // G\u1ecdi c\u00e1c h\u00e0m v\u1ebd Radar
    drawRadar();
    drawLine();
    drawObject();
    drawText();
}
public void serialEvent (Serial myPort) { // \u0110\u1ecdc d\u1eef li\u1ec7u t\u1eeb c\u1ed5ng COM
    data = myPort.readStringUntil('.');// \u0110\u1ecdc d\u1eef li\u1ec7u \u0111\u1ebfn khi ph\u00e1t hi\u1ec7n d\u1ea5u "." r\u1ed3i g\u00e1n v\u00e0o bi\u1ebfn "data"
    data = data.substring(0,data.length()-1);
    index1 = data.indexOf(","); // T\u00ecm k\u00fd t\u1ef1 "," r\u1ed3i g\u00e1n v\u00e0o "index1"
    angle= data.substring(0, index1); // \u0110\u1ecdc d\u1eef li\u1ec7u t\u1eeb k\u00fd t\u1ef1 \u0111\u1ea7u ti\u00ean...\u0111\u1ebfn khi nh\u1eadn d\u1ea5u "," r\u1ed3i g\u00e1n v\u00e0o bi\u1ebfn "angle"
    distance= data.substring(index1+1, data.length()); // \u0110\u1ecdc d\u1eef li\u1ec7u t\u1eeb k\u00fd t\u1ef1 sau d\u1ea5u "," \u0111\u1ebfn d\u1ea5u "." ( \u0111\u1ebfn h\u1ebft \u0111\u00f3 )
    // Chuy\u1ec3n \u0111\u1ed5i c\u00e1c chu\u1ed7i th\u00e0nh s\u1ed1 nguy\u00ean
    iAngle = PApplet.parseInt(angle);
    iDistance = PApplet.parseInt(distance);
}
// \u0110\u1ebfn ph\u1ea7n x\u00e2y d\u1ef1ng giao di\u1ec7n Radar...\u0110\u00e2y l\u00e0 v\u1ebd h\u00ecnh \u00e1...c\u00e1c b\u1ea1n c\u1ee9 coppy
public void drawRadar() {
    pushMatrix();
    translate(width/2,height-height*0.074f); // di chuy\u1ec3n t\u1ecda \u0111\u1ed9 \u0111\u1ebfn v\u1ecb tr\u00ed m\u1edbi!!!
    strokeWeight(2);
    stroke(98,245,31);
    // V\u1ebd c\u00e1c v\u1ec7t line sao \u00fd!!! keke
    arc(0,0,(width-width*0.0625f),(width-width*0.0625f),PI,TWO_PI);
    arc(0,0,(width-width*0.27f),(width-width*0.27f),PI,TWO_PI);
    arc(0,0,(width-width*0.479f),(width-width*0.479f),PI,TWO_PI);
    arc(0,0,(width-width*0.687f),(width-width*0.687f),PI,TWO_PI);
    // V\u1ebd c\u00e1c \u0111\u01b0\u1eddng g\u00f3c
    line(-width/2,0,width/2,0);
    line(0,0,(-width/2)*cos(radians(30)),(-width/2)*sin(radians(30)));
    line(0,0,(-width/2)*cos(radians(60)),(-width/2)*sin(radians(60)));
    line(0,0,(-width/2)*cos(radians(90)),(-width/2)*sin(radians(90)));
    line(0,0,(-width/2)*cos(radians(120)),(-width/2)*sin(radians(120)));
    line(0,0,(-width/2)*cos(radians(150)),(-width/2)*sin(radians(150)));
    line((-width/2)*cos(radians(30)),0,width/2,0);
    popMatrix();
}
public void drawObject() {
    pushMatrix();
    translate(width/2,height-height*0.074f); // Di chuy\u1ec3n con tr\u1ecf \u0111\u1ebfn t\u1ecda \u0111\u1ed9 kh\u00e1c...r\u1ed3i v\u1ebd ti\u1ebfp
    strokeWeight(9);
    stroke(255,10,10); // M\u00e0u \u0111\u1ecf
    pixsDistance = iDistance*((height-height*0.1666f)*0.025f); // Kho\u1ea3ng c\u00e1ch..t\u1eeb cm \u0111\u1ed5i th\u00e0nh pixcel ( theo c\u00f4ng th\u1ee9c )
    // N\u1ebfu kho\u1ea3ng c\u00e1ch < 40cm
    if(iDistance<40){
        // V\u1ebd c\u00e1c \u0111\u1ed1i t\u01b0\u1ee3ng g\u00f3c v\u00e0 kho\u1ea3ng c\u00e1ch...!!!!
        line(pixsDistance*cos(radians(iAngle)),-pixsDistance*sin(radians(iAngle)),(width-width*0.505f)*cos(radians(iAngle)),-(width-width*0.505f)*sin(radians(iAngle)));
    }
    popMatrix();
}
public void drawLine() {
    pushMatrix();
    strokeWeight(9);
    stroke(30,250,60);
    translate(width/2,height-height*0.074f); // L\u1ea1i di chuy\u1ec3n con tr\u1ecf \u0111\u1ebfn v\u1ecb tr\u00ed kh\u00e1c
    line(0,0,(height-height*0.12f)*cos(radians(iAngle)),-(height-height*0.12f)*sin(radians(iAngle)));
    popMatrix();
}
public void drawText() { // Vi\u1ebft c\u00e1c d\u00f2ng ch\u1eef tr\u00ean m\u00e0n h\u00ecnh
    pushMatrix();
    if(iDistance>40) {
        noObject = "Ra kh\u1ecfi ph\u1ea1m vi";
    }
    else {
        noObject = "Trong ph\u1ea1m vi";
    }
    fill(0,0,0);
    noStroke();
    rect(0, height-height*0.0648f, width, height);
    fill(98,245,31);
    textSize(25);
    text("10cm",width-width*0.3854f,height-height*0.0833f);
    text("20cm",width-width*0.281f,height-height*0.0833f);
    text("30cm",width-width*0.177f,height-height*0.0833f);
    text("40cm",width-width*0.0729f,height-height*0.0833f);
    textSize(40);
    text("Object: " + noObject, width-width*0.875f, height-height*0.0277f);
    text("Angle: " + iAngle +" \u00b0", width-width*0.48f, height-height*0.0277f);
    text("Distance: ", width-width*0.26f, height-height*0.0277f);
    if(iDistance<40) {
        text(" " + iDistance +" cm", width-width*0.225f, height-height*0.0277f);
    }
    textSize(25);
    fill(98,245,60);
    translate((width-width*0.4994f)+width/2*cos(radians(30)),(height-height*0.0907f)-width/2*sin(radians(30)));
    rotate(-radians(-60));
    text("30\u00b0",0,0);
    resetMatrix();
    translate((width-width*0.503f)+width/2*cos(radians(60)),(height-height*0.0888f)-width/2*sin(radians(60)));
    rotate(-radians(-30));
    text("60\u00b0",0,0);
    resetMatrix();
    translate((width-width*0.507f)+width/2*cos(radians(90)),(height-height*0.0833f)-width/2*sin(radians(90)));
    rotate(radians(0));
    text("90\u00b0",0,0);
    resetMatrix();
    translate(width-width*0.513f+width/2*cos(radians(120)),(height-height*0.07129f)-width/2*sin(radians(120)));
    rotate(radians(-30));
    text("120\u00b0",0,0);
    resetMatrix();
    translate((width-width*0.5104f)+width/2*cos(radians(150)),(height-height*0.0574f)-width/2*sin(radians(150)));
    rotate(radians(-60));
    text("150\u00b0",0,0);
    popMatrix();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "radar" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
