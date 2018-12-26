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
import proyectoavanzada.EntidadController;
import proyectoavanzada.Relacion;

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
    public Point puntoMenor;
    public Point puntoMayor;
    public RectanguloAgregacion(Point punto1,Point punto3) {
    
        this.punto1=punto1;
        this.punto3=punto3;
        
        this.lineaSuperior= new Line(punto1.x,punto1.y,punto3.x,punto1.y);
        this.lineaInferior= new Line(punto3.x,punto3.y,punto1.x,punto3.y);
        this.lineaIzquierda= new Line(punto1.x,punto1.y,punto1.x,punto3.y);
        this.lineaDerecha= new Line(punto3.x,punto1.y,punto3.x,punto3.y);

    }
    
    public void Mover(Point punto,Pane pane,Relacion relacion) {
        Borrar(pane);
        puntoMenor.setLocation(punto);
        puntoMayor.setLocation(punto);
        for (int i = 0; i < relacion.entidadesSelec.size(); i++) {
            for (int j = 0; j < relacion.entidadesSelec.get(i).atributos.size(); j++) {
                for (int k = 0; k < relacion.entidadesSelec.get(i).atributos.get(j).poligono.puntos.size(); k++) {
                    if (relacion.entidadesSelec.get(i).atributos.get(j).poligono.puntos.get(k).x < puntoMenor.x){
                        puntoMenor.x = relacion.entidadesSelec.get(i).atributos.get(j).poligono.puntos.get(k).x;
                    }
                    if(relacion.entidadesSelec.get(i).atributos.get(j).poligono.puntos.get(k).y < puntoMenor.y){
                        puntoMenor.y = relacion.entidadesSelec.get(i).atributos.get(j).poligono.puntos.get(k).y;
                    }

                    if (relacion.entidadesSelec.get(i).atributos.get(j).poligono.puntos.get(k).x > puntoMayor.x){
                        puntoMayor.x = relacion.entidadesSelec.get(i).atributos.get(j).poligono.puntos.get(k).x;
                    }
                    if(relacion.entidadesSelec.get(i).atributos.get(j).poligono.puntos.get(k).y > puntoMayor.y){
                        puntoMayor.y = relacion.entidadesSelec.get(i).atributos.get(j).poligono.puntos.get(k).y;
                    }
                }
                
            }
        }
        
        for (int i = 0; i < relacion.atributos.size() ; i++) {
            for (int j = 0; j < relacion.atributos.get(i).poligono.puntos.size(); j++) {
                if (relacion.atributos.get(i).poligono.puntos.get(j).x < puntoMenor.x){
                    puntoMenor.x = relacion.atributos.get(i).poligono.puntos.get(j).x;
                }
                if(relacion.atributos.get(i).poligono.puntos.get(j).y < puntoMenor.y){
                    puntoMenor.y = relacion.atributos.get(i).poligono.puntos.get(j).y;
                }

                if (relacion.atributos.get(i).poligono.puntos.get(j).x > puntoMayor.x){
                    puntoMayor.x = relacion.atributos.get(i).poligono.puntos.get(j).x;
                }
                if(relacion.atributos.get(i).poligono.puntos.get(j).y > puntoMayor.y){
                    puntoMayor.y = relacion.atributos.get(i).poligono.puntos.get(j).y;
                }
            }

        }
        for (int i = 0; i < EntidadController.herencias.size(); i++) {
            if(EntidadController.herencias.get(i).entidad.equals(relacion.entidadesSelec.get(0)) || 
               EntidadController.herencias.get(i).entidad.equals(relacion.entidadesSelec.get(1)) ){
                for (int j = 0; j < EntidadController.herencias.get(i).entidad.rectangulo.puntos.size(); j++) {
                    if (EntidadController.herencias.get(i).entidad.rectangulo.puntos.get(j).x<puntoMenor.x){
                        puntoMenor.x=EntidadController.herencias.get(i).entidad.rectangulo.puntos.get(j).x;
                    }
                    if (EntidadController.herencias.get(i).entidad.rectangulo.puntos.get(j).y<puntoMenor.y){
                        puntoMenor.y=EntidadController.herencias.get(i).entidad.rectangulo.puntos.get(j).y;
                    }
                    if (EntidadController.herencias.get(i).entidad.rectangulo.puntos.get(j).x>puntoMenor.x){
                        puntoMayor.x=EntidadController.herencias.get(i).entidad.rectangulo.puntos.get(j).x;
                    }
                    if (EntidadController.herencias.get(i).entidad.rectangulo.puntos.get(j).y>puntoMenor.y){
                        puntoMayor.y=EntidadController.herencias.get(i).entidad.rectangulo.puntos.get(j).y;
                    }
                }
            }
            if (EntidadController.herencias.get(i).entidadesHeredadas.contains(relacion.entidadesSelec.get(0)) || 
                EntidadController.herencias.get(i).entidadesHeredadas.contains(relacion.entidadesSelec.get(1) )){
                for (int j = 0; j < EntidadController.herencias.get(i).entidadesHeredadas.size(); j++) {
                    for (int k = 0; k < EntidadController.herencias.get(i).entidadesHeredadas.get(j).rectangulo.puntos.size(); k++) {
                        if (EntidadController.herencias.get(i).entidadesHeredadas.get(j).rectangulo.puntos.get(k).x<puntoMenor.x){
                            puntoMenor.x=EntidadController.herencias.get(i).entidadesHeredadas.get(j).rectangulo.puntos.get(k).x;
                        }
                        if (EntidadController.herencias.get(i).entidadesHeredadas.get(j).rectangulo.puntos.get(k).y<puntoMenor.y){
                            puntoMenor.y=EntidadController.herencias.get(i).entidadesHeredadas.get(j).rectangulo.puntos.get(k).y;
                        }
                        
                        if (EntidadController.herencias.get(i).entidadesHeredadas.get(j).rectangulo.puntos.get(k).x>puntoMayor.x){
                            puntoMayor.x=EntidadController.herencias.get(i).entidadesHeredadas.get(j).rectangulo.puntos.get(k).x;
                        }
                        if (EntidadController.herencias.get(i).entidadesHeredadas.get(j).rectangulo.puntos.get(k).y>puntoMayor.y){
                            puntoMayor.y=EntidadController.herencias.get(i).entidadesHeredadas.get(j).rectangulo.puntos.get(k).y;
                        }
                    }
                }
            }
        }
        
        
        puntoMenor.x=puntoMenor.x-20;
        puntoMenor.y=puntoMenor.y-20;
        puntoMayor.x=puntoMayor.x+20;
        puntoMayor.y=puntoMayor.y+20;
        //Rectangulo
        Point punto1=new Point();
        punto1.setLocation(punto);
        Point punto3=new Point();
        punto3.setLocation(punto);
        punto1.x-=200;
        punto1.y-=100;

        punto3.x+=200;
        punto3.y+=80;
        if(punto1.x>puntoMenor.x){
            punto1.x=puntoMenor.x;
        }
        if(punto1.y>puntoMenor.y){
            punto1.y=puntoMenor.y;
        }
        
        if(punto3.x<puntoMayor.x){
            punto1.x=puntoMenor.x;
        }
        if(punto3.y<puntoMayor.y){
            punto1.y=puntoMenor.y;
        }
        
        
        
        
        
        //GENERAR PUNTO 1 Y 3
        //this.punto3.x+=80;

        
        this.lineaSuperior= new Line(punto1.x,punto1.y,punto3.x,punto1.y);
        this.lineaInferior= new Line(punto3.x,punto3.y,punto1.x,punto3.y);
        this.lineaIzquierda= new Line(punto1.x,punto1.y,punto1.x,punto3.y);
        this.lineaDerecha= new Line(punto3.x,punto1.y,punto3.x,punto3.y);        
          
        
        Dibujar(pane);
    }
    

    public void Dibujar(Pane pane) {
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
    
    public void PintarColorCrimson() {
        lineaSuperior.setStroke(Color.CRIMSON);
        lineaInferior.setStroke(Color.CRIMSON);
        lineaIzquierda.setStroke(Color.CRIMSON);
        lineaDerecha.setStroke(Color.CRIMSON);
        
    }
    public void repintarNegro(){
        lineaSuperior.setStroke(Color.BLACK);
        lineaInferior.setStroke(Color.BLACK);
        lineaIzquierda.setStroke(Color.BLACK);
        lineaDerecha.setStroke(Color.BLACK);
    }
    

    public void Borrar(Pane pane) {
        pane.getChildren().remove(lineaDerecha);
        pane.getChildren().remove(lineaInferior);
        pane.getChildren().remove(lineaSuperior);
        pane.getChildren().remove(lineaIzquierda);
    }
}