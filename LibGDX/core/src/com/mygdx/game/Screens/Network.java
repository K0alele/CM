package com.mygdx.game.Screens;


import java.util.ArrayList;


import processing.core.PApplet;
import ketai.net.wifidirect.*;  //(1)
import ketai.net.*;
import ketai.ui.*;
import oscP5.*;
import netP5.*;

public class Sketch extends PApplet {
    KetaiWiFiDirect direct;

    OscP5 oscP5;
    String clientIP = "";
    NetAddress myRemoteLocation;

    public void settings() {
        fullScreen(P2D);
        //size(600, 900); // tamanho área
    }

    public void setup() {
        //orientation(LANDSCAPE);
        textSize(24);
        stroke(255);
        oscP5 = new OscP5(this, 12000);  //(3)
        direct = new KetaiWiFiDirect(this);
    }

    public void mouseDragged() {
        OscMessage m = new OscMessage("/remoteCursor/");  // definição de nova mensagem
        m.add(pmouseX);
        m.add(pmouseY);
        if (clientIP != "")
            myRemoteLocation = new NetAddress(clientIP, 12000);
        else if (direct.getIPAddress() != KetaiNet.getIP())
            myRemoteLocation = new NetAddress(direct.getIPAddress(), 12000);
        if (myRemoteLocation != null)
            oscP5.send(m, myRemoteLocation);
    }

    public void oscEvent(OscMessage m) {
        if (direct.getIPAddress() != m.netAddress().address())
            clientIP = m.netAddress().address();
        if (m.checkAddrPattern("/remoteCursor/")) {
            if (m.checkTypetag("ii")) {
                remoteCursor.x = m.get(0).intValue();
                remoteCursor.y = m.get(1).intValue();
            }
        }

    }
}
