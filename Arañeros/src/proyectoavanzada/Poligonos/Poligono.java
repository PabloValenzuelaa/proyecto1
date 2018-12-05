/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoavanzada.Poligonos;

import java.awt.Point;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author Diali
 */
public class Poligono {
    public Point punto;
    public ArrayList<Point> puntos=new ArrayList();
    public ArrayList<Point> puntosAux=new ArrayList();
    public ArrayList<Line> lineas=new ArrayList();
    public ArrayList<Boolean> puntosBolean= new ArrayList();
    public int n, largo;
    
    public Pane pane;

    public Poligono(Pane pane) {
        this.puntos = puntos;
        this.lineas = lineas;
        this.pane = pane;
    }

    public ArrayList<Point> getPuntos() {
        return puntos;
    }

    public ArrayList<Point> getPuntosAux() {
        return puntosAux;
    }
    
    public void Dibujar(int n, int largo, Point puntoMouse) {
        this.n=n;
        this.largo=largo;
        generarPuntos(n, largo,puntoMouse);
        generarLineas();
        for (int i = 0; i < this.lineas.size(); i++) {
            lineas.get(i).setStroke(Color.BLACK);
            lineas.get(i).setStrokeWidth(1);
            lineas.get(i).setStrokeLineCap(StrokeLineCap.ROUND);
            pane.getChildren().add(lineas.get(i));
        }
    }
    

    public void setPuntosBolean(ArrayList<Boolean> puntosBolean) {
        this.puntosBolean = puntosBolean;
    }

    public void generarPuntos(int n, int radio, Point puntoMouse){
        this.punto=puntoMouse;
        for (int i =0; i <= n; i++) {
            Point punto=new Point();
            Double angle = i * 2 * 3.14 / n;
            punto.x = (int) ((radio * cos(angle))+ puntoMouse.x)-300;
            punto.y= (int) ((radio * sin(angle))+puntoMouse.y)-15;
            puntos.add(punto);
            puntosBolean.add(Boolean.FALSE);
        }
    }
    
    public void generarPuntosElipse(int n, int radio, Point puntoMouse){
        this.punto=puntoMouse;
        for (int i = 0; i <= n; i++) {
            Point punto=new Point();
            Double angle = i * 2 * 3.14 / n;
            punto.x = (int) (((radio+80) * cos(angle))+ puntoMouse.x)-300;
            punto.y= (int) ((radio * sin(angle))+puntoMouse.y)-15;
            puntos.add(punto);
            puntosBolean.add(Boolean.FALSE);
        }
    }
    public void DibujarElipsePunteada(int n, int largo, Point puntoMouse) {
        this.n=n;
        this.largo=largo;
        generarPuntosElipse(n, largo,puntoMouse);
        generarLineas();
        int j=2;
        for (int i = 0; i < this.lineas.size(); i++) {
            if(i==j){
                i=i+2;
                j=j+4;
            }
            else{
            lineas.get(i).setStroke(Color.BLACK);
            lineas.get(i).setStrokeWidth(1);
            lineas.get(i).setStrokeLineCap(StrokeLineCap.ROUND);
            pane.getChildren().add(lineas.get(i));
            }
        }
    }
    
   public void DibujarElipseNormal(int n, int largo, Point puntoMouse) {
        this.n=n;
        this.largo=largo;
        generarPuntosElipse(n, largo,puntoMouse);
        generarLineas();
        for (int i = 0; i < this.lineas.size(); i++) {
            
            lineas.get(i).setStroke(Color.BLACK);
            lineas.get(i).setStrokeWidth(1);
            lineas.get(i).setStrokeLineCap(StrokeLineCap.ROUND);
            pane.getChildren().add(lineas.get(i));
            
        }
    }
    public void DibujarElipseNormalDoble(int n, int largo, Point puntoMouse) {
        this.n=n;
        this.largo=largo;
        generarPuntosElipseDoble(n, largo,puntoMouse);
        generarLineas();
        for (int i = 0; i < this.lineas.size(); i++) {
            
            lineas.get(i).setStroke(Color.BLACK);
            lineas.get(i).setStrokeWidth(1);
            lineas.get(i).setStrokeLineCap(StrokeLineCap.ROUND);
            pane.getChildren().add(lineas.get(i));
            
        }
    }
    
    public void generarPuntosElipseDoble(int n, int radio, Point puntoMouse){
    generarPuntosElipse(n,radio,puntoMouse);
        for (int i = 0; i <= n; i++) {
            Point punto=new Point();
            Double angle = i * 2 * 3.14 / n;
            punto.x = (int) (((radio+85) * cos(angle))+ puntoMouse.x)-300;
            punto.y= (int) (((radio+4) * sin(angle))+puntoMouse.y)-15;
            puntos.add(punto);
            puntosBolean.add(Boolean.FALSE);
        }
    }
    
    public void generarLineas(){
        int i;
        for ( i = 0; i < puntos.size()-1; i++) {
            Line linea=new Line(puntos.get(i).x, puntos.get(i).y,puntos.get(i+1).x, puntos.get(i+1).y);
            lineas.add(linea);
        }
    }
    public boolean seleccionar(Point punto){
        boolean seleccion=false;
        //System.out.println("punto: "+punto.toString());
        Point puntoMaxX=puntos.get(0);
        Point puntoMaxY=puntos.get(0);
        Point puntoMinX=puntos.get(0);
        Point puntoMinY=puntos.get(0);
        for (int i = 0; i < puntos.size(); i++) {//definimos el punto maximo en x   
                 if(puntos.get(i).x>puntoMaxX.x){
                     puntoMaxX=puntos.get(i);
                 }
        }  
        for (int i = 0; i < puntos.size(); i++) {//definimos el punto maximo en Y
                 if(puntos.get(i).y> puntoMaxY.y){
                     puntoMaxY=puntos.get(i);
                 }
        }     
        for (int i = 0; i < puntos.size(); i++) {//definimos el punto min en x
                 
                 if(puntos.get(i).x<puntoMinX.x){
                     puntoMinX=puntos.get(i);
                 }
        }     
        for (int i = 0; i < puntos.size(); i++) {//definimos el punto min en y
                 
                 if(puntos.get(i).y<puntoMinY.y){
                     puntoMinY=puntos.get(i);
                 }
        }
        if(punto.x<puntoMaxX.x&&
            punto.x>puntoMinX.x&&
            punto.y<puntoMaxY.y&&
           punto.y >puntoMinY.y){
            return true;
        }
        
        return false;
    }
    public void borrar(){
        for (int i = 0; i < lineas.size(); i++) {
             pane.getChildren().remove(lineas.get(i));
        }
    }
     public void mover(Point punto){
         borrar();
         lineas.clear();
         puntos.clear();
         Dibujar(n, largo, punto);
     }
     public void actualizar(int n){
         this.n=n;
         if(n==1 || n==2){
             n=4;
         }
         borrar();
         lineas.clear();
         puntos.clear();
         
         Dibujar(n, largo, punto);
     }
     
     public void generarCurva1(int n, int radio, Point puntoMouse){
        this.punto=puntoMouse;
        puntoMouse.x-=10;
        for (int i =1; i <= n; i++) {
            Point punto=new Point();
            Double angle = i * 2 * 3.14 / n;
            punto.x = (int) ((radio * cos(angle))+ puntoMouse.x)-300;
            punto.y= (int) ((radio * sin(angle))+puntoMouse.y)-15;
            if((i>7*n/8&&i<n))
            puntos.add(punto);
        }
        for (int i =1; i <= n; i++) {
            Point punto=new Point();
            Double angle = i * 2 * 3.14 / n;
            punto.x = (int) ((radio * cos(angle))+ puntoMouse.x)-300;
            punto.y= (int) ((radio * sin(angle))+puntoMouse.y)-15;
            if((i>0&&i<3*n/8))
            puntos.add(punto);
        }
        generarLineas();
        for (int i = 0; i < this.lineas.size(); i++) {
            
            lineas.get(i).setStroke(Color.BLACK);
            lineas.get(i).setStrokeWidth(1);
            lineas.get(i).setStrokeLineCap(StrokeLineCap.ROUND);
            pane.getChildren().add(lineas.get(i));
            
        }
     }
     public void generarCurva2(int n, int radio, Point puntoMouse){
        this.punto=puntoMouse;
        puntoMouse.x+=10;
        for (int i =0; i <= n; i++) {
            Point punto=new Point();
            Double angle = i * 2 * 3.14 / n;
            punto.x = (int) ((radio * cos(angle))+ puntoMouse.x)-300;
            punto.y= (int) ((radio * sin(angle))+puntoMouse.y)-15;
            if((i>n/8&&i<5*n/8))
            puntos.add(punto);
        }
        generarLineas();
        for (int i = 0; i < this.lineas.size(); i++) {
            
            lineas.get(i).setStroke(Color.BLACK);
            lineas.get(i).setStrokeWidth(1);
            lineas.get(i).setStrokeLineCap(StrokeLineCap.ROUND);
            pane.getChildren().add(lineas.get(i));
            
        }
     }
     public void generarCurva3(int n, int radio, Point puntoMouse){
        this.punto=puntoMouse;
        puntoMouse.y+=10;
        for (int i =0; i <= n; i++) {
            Point punto=new Point();
            Double angle = i * 2 * 3.14 / n;
            punto.x = (int) ((radio * cos(angle))+ puntoMouse.x)-300;
            punto.y= (int) ((radio * sin(angle))+puntoMouse.y)-15;
            if((i>3*n/8&&i<7*n/8))
            puntos.add(punto);
        }
        generarLineas();
        for (int i = 0; i < this.lineas.size(); i++) {
            
            lineas.get(i).setStroke(Color.BLACK);
            lineas.get(i).setStrokeWidth(1);
            lineas.get(i).setStrokeLineCap(StrokeLineCap.ROUND);
            pane.getChildren().add(lineas.get(i));
            
        }
     }
     public void generarCurva4(int n, int radio, Point puntoMouse){
        this.punto=puntoMouse;
        puntoMouse.y+=10;
        for (int i =0; i <= n; i++) {
            Point punto=new Point();
            Double angle = i * 2 * 3.14 / n;
            punto.x = (int) ((radio * cos(angle))+ puntoMouse.x)-300;
            punto.y= (int) ((radio * sin(angle))+puntoMouse.y)-15;
            if((i>5*n/8&&i<n))
            puntos.add(punto);
        }
        for (int i =0; i <= n; i++) {
            Point punto=new Point();
            Double angle = i * 2 * 3.14 / n;
            punto.x = (int) ((radio * cos(angle))+ puntoMouse.x)-300;
            punto.y= (int) ((radio * sin(angle))+puntoMouse.y)-15;
            if((i>0&&i<n/8))
            puntos.add(punto);
        }
        generarLineas();
        for (int i = 0; i < this.lineas.size(); i++) {
            
            lineas.get(i).setStroke(Color.BLACK);
            lineas.get(i).setStrokeWidth(1);
            lineas.get(i).setStrokeLineCap(StrokeLineCap.ROUND);
            pane.getChildren().add(lineas.get(i));
            
        }
        
     }
     public void Dibujar2() {
        for (int i = 0; i < this.lineas.size(); i++) {
            lineas.get(i).setStroke(Color.CRIMSON);
        }
    }
    public void repintarNegro(){
        for (int i = 0; i < this.lineas.size(); i++) {
            lineas.get(i).setStroke(Color.BLACK);
        }
    }   
}
    

