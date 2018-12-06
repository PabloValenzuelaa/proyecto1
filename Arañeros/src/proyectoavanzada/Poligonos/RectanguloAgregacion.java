/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoavanzada.Poligonos;

import java.awt.Point;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author Pablo
 */
public class RectanguloAgregacion {
    
    public Line lineaSuperior;
    public Line lineaInferior;
    public Line lineaDerecha;
    public Line lineaIzquierda;
    public Point punto1;
    public Point punto3;

    public RectanguloAgregacion(Point punto1,Point punto3) {
    
        this.punto1=punto1;
        this.punto3=punto3;
        
        this.lineaSuperior= new Line(punto1.x,punto1.y,punto3.x,punto1.y);
        this.lineaInferior= new Line(punto3.x,punto3.y,punto1.x,punto3.y);
        this.lineaIzquierda= new Line(punto1.x,punto1.y,punto1.x,punto3.y);
        this.lineaDerecha= new Line(punto1.x,punto3.y,punto3.x,punto3.y);
        
        
    }
    
    
    
    public void Mover(Point punto,Pane pane) {
        Borrar(pane);
        this.lineaSuperior= new Line(punto1.x,punto1.y,punto3.x,punto1.y);
        this.lineaInferior= new Line(punto3.x,punto3.y,punto1.x,punto3.y);
        this.lineaIzquierda= new Line(punto1.x,punto1.y,punto1.x,punto3.y);
        this.lineaDerecha= new Line(punto1.x,punto3.y,punto3.x,punto3.y);
        
        Dibujar(pane);
    }
    

    public void Dibujar(Pane pane) {
        System.out.println("PINTAAAAAAA");
        lineaSuperior.setStroke(Color.BLACK);
        lineaSuperior.setStrokeWidth(1);
        lineaSuperior.setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().add(lineaSuperior);

        
        lineaInferior.setStroke(Color.BLACK);
        lineaInferior.setStrokeWidth(1);
        lineaInferior.setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().add(lineaInferior);

        
        lineaIzquierda.setStroke(Color.BLACK);
        lineaIzquierda.setStrokeWidth(1);
        lineaIzquierda.setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().add(lineaIzquierda);

        
        lineaDerecha.setStroke(Color.BLACK);
        lineaDerecha.setStrokeWidth(1);
        lineaDerecha.setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().add(lineaDerecha);
    }
    
    public void Dibujar2() {
        lineaSuperior.setStroke(Color.CRIMSON);
        lineaInferior.setStroke(Color.CRIMSON);
        lineaIzquierda.setStroke(Color.CRIMSON);
        lineaDerecha.setStroke(Color.CRIMSON);
        
    }
    

    public void Borrar(Pane pane) {
        pane.getChildren().remove(lineaDerecha);
        pane.getChildren().remove(lineaInferior);
        pane.getChildren().remove(lineaSuperior);
        pane.getChildren().remove(lineaIzquierda);
    }
}