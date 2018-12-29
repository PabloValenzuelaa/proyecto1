/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoavanzada;

import java.awt.Point;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import proyectoavanzada.Poligonos.Rectangulo;
import proyectoavanzada.Poligonos.RectanguloAgregacion;

/**
 *
 * @author Pablo
 */
public class Agregacion extends Entidad{
    public Relacion relacion;
    public Point puntoCentral;
    public RectanguloAgregacion rectanguloAgregacion;
    public ArrayList <Point> puntos = new ArrayList<>();
    public ArrayList <Point> puntosElem = new ArrayList<>();
    //public Line linea;

    public Agregacion(Relacion relacion, Point puntoCentral, Line lineaa, Text nombre, Rectangulo rectangulo,Pane pane) {
        super(nombre, rectangulo);
        this.relacion = relacion;
        this.puntoCentral = puntoCentral;
        
        puntoCentral.x=puntoCentral.x-300;
        
        Point puntoMenor = new Point();
        Point puntoMayor = new Point();
        
        obtenerPuntos();
        puntoMenor.setLocation(obtenerMenor());
        puntoMayor.setLocation(obtenerMayor());
        
        puntoMenor.x=puntoMenor.x-20;
        puntoMenor.y=puntoMenor.y-20;

        puntoMayor.x=puntoMayor.x+20;
        puntoMayor.y=puntoMayor.y+20;
        
        //PUNTOS
        Point puntoA = new Point(); //arriba
        puntoA.setLocation(puntoMenor);
        puntos.add(puntoA);
        
        Point puntoB = new Point(); //derecha
        puntoB.x=puntoMayor.x;
        puntoB.y=puntoMenor.y;
        puntos.add(puntoB);
        
        Point puntoC = new Point(); //abajo
        puntoC.setLocation(puntoMayor);
        puntos.add(puntoC);
        
        Point puntoD = new Point(); //izquierda
        puntoD.x=puntoMenor.x;
        puntoD.y=puntoMayor.y;
        puntos.add(puntoD);

        RectanguloAgregacion aaa = new RectanguloAgregacion(puntoMenor,puntoMayor);
        this.rectanguloAgregacion = aaa;
    }
    public void mover(Point punto,Pane pane){
        Point puntoMenor = new Point();
        Point puntoMayor = new Point();
        puntosElem.clear();
        puntos.clear();
        obtenerPuntos();

        puntoMenor.setLocation(obtenerMenor());
        puntoMayor.setLocation(obtenerMayor());
        
        puntoMenor.x=puntoMenor.x-20;
        puntoMenor.y=puntoMenor.y-20;

        puntoMayor.x=puntoMayor.x+20;
        puntoMayor.y=puntoMayor.y+20;
        
        //PUNTOS
        Point puntoA = new Point(); //arriba
        puntoA.setLocation(puntoMenor);
        puntos.add(puntoA);
        
        Point puntoB = new Point(); //derecha
        puntoB.x=puntoMayor.x;
        puntoB.y=puntoMenor.y;
        puntos.add(puntoB);
        
        Point puntoC = new Point(); //abajo
        puntoC.setLocation(puntoMayor);
        puntos.add(puntoC);
        
        Point puntoD = new Point(); //izquierda
        puntoD.x=puntoMenor.x;
        puntoD.y=puntoMayor.y;
        puntos.add(puntoD);
        
        this.puntoCentral=punto;
        super.nombre.setLayoutX(puntoMenor.x-190);
        super.nombre.setLayoutY(puntoMenor.y-75); 
        rectanguloAgregacion.Mover(puntoMenor,puntoMayor, pane);
        
    }
    
    public void obtenerPuntos(){
        puntosElem.clear();
        puntosElem.addAll(relacion.poligono.puntos);
        for (int i = 0; i < relacion.entidadesSelec.size(); i++) {
            if(relacion.entidadesSelec.get(i) instanceof Agregacion){
                Agregacion agrega = (Agregacion) relacion.entidadesSelec.get(i);
                puntosElem.addAll(agrega.puntos);
            }
            else{
                puntosElem.addAll(relacion.entidadesSelec.get(i).rectangulo.puntos);
                for (int j = 0; j < relacion.entidadesSelec.get(i).entidadesHeredadas.size(); j++) {
                    puntosElem.addAll(relacion.entidadesSelec.get(i).entidadesHeredadas.get(j).rectangulo.puntos);
                }
            }
            for (int j = 0; j < relacion.entidadesSelec.get(i).atributos.size(); j++) {
                puntosElem.addAll(relacion.entidadesSelec.get(i).atributos.get(j).poligono.puntos);
                if(relacion.entidadesSelec.get(i).atributos.get(j).tipo== TipoAtributo.compuesto){
                    for (int k = 0; k < relacion.entidadesSelec.get(i).atributos.get(j).atributos.size(); k++) {
                        puntosElem.addAll(relacion.entidadesSelec.get(i).atributos.get(j).atributos.get(k).poligono.puntos);
                    }
                }
                
            }
        }
    }
    public Point obtenerMenor(){
        Point puntoMenor = new Point();
        puntoMenor.setLocation(puntosElem.get(0));
        for (int i = 0; i < puntosElem.size(); i++) {
            if (puntosElem.get(i).x < puntoMenor.x){
                puntoMenor.x = puntosElem.get(i).x;
            }
            
        }
        for (int i = 0; i < puntosElem.size(); i++) {
            if(puntosElem.get(i).y < puntoMenor.y){
                puntoMenor.y = puntosElem.get(i).y;
            }
            
        }
        return puntoMenor;
    }
    public Point obtenerMayor(){
        Point puntoMayor = new Point();
        puntoMayor.setLocation(puntosElem.get(0));
        for (int i = 0; i < puntosElem.size(); i++) {
            if (puntosElem.get(i).x > puntoMayor.x){
                puntoMayor.x = puntosElem.get(i).x;
            }
            if(puntosElem.get(i).y > puntoMayor.y){
                puntoMayor.y = puntosElem.get(i).y;
            }
            
        }
        return puntoMayor;
    }
    public void actualizar(Pane pane){
        obtenerPuntos();
        puntos.clear();
        Point puntoMenor = new Point();
        Point puntoMayor = new Point();
        
        obtenerPuntos();
        puntoMenor.setLocation(obtenerMenor());
        puntoMayor.setLocation(obtenerMayor());
        
        puntoMenor.x=puntoMenor.x-20;
        puntoMenor.y=puntoMenor.y-20;

        puntoMayor.x=puntoMayor.x+20;
        puntoMayor.y=puntoMayor.y+20;
        
        //PUNTOS
        Point puntoA = new Point(); //arriba
        puntoA.setLocation(puntoMenor);
        puntos.add(puntoA);
        
        Point puntoB = new Point(); //derecha
        puntoB.x=puntoMayor.x;
        puntoB.y=puntoMenor.y;
        puntos.add(puntoB);
        
        Point puntoC = new Point(); //abajo
        puntoC.setLocation(puntoMayor);
        puntos.add(puntoC);
        
        Point puntoD = new Point(); //izquierda
        puntoD.x=puntoMenor.x;
        puntoD.y=puntoMayor.y;
        puntos.add(puntoD);
        rectanguloAgregacion.Borrar(pane);
        RectanguloAgregacion agrega = new RectanguloAgregacion(puntoMenor,puntoMayor);
        agrega.Dibujar(pane);
        this.rectanguloAgregacion = agrega;
    }
}
