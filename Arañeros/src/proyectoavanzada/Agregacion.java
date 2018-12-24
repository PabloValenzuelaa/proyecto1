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
    //public Line linea;

    public Agregacion(Relacion relacion, Point puntoCentral, Line lineaa, Text nombre, Rectangulo rectangulo) {
        super(nombre, rectangulo);
        this.relacion = relacion;
        this.puntoCentral = puntoCentral;
        
        puntoCentral.x=puntoCentral.x-300;
        
        Point puntoEntidad1=new Point();
        puntoEntidad1.setLocation(puntoCentral);
        puntoEntidad1.x+=310;
        Point puntoEntidad2=new Point();
        puntoEntidad2.setLocation(puntoCentral);
        puntoEntidad2.x+=230;
        
        Entidad entidad1 = relacion.entidadesSelec.get(0);
        entidad1.rectangulo.PosicionAgregacionIzq(puntoEntidad1);
        
        entidad1.nombre.setLayoutX(puntoEntidad1.x-295);
        entidad1.nombre.setLayoutY(puntoEntidad1.y);
        
        //Rectangulo
        Point punto1=new Point();
        punto1.setLocation(puntoCentral);
        Point punto3=new Point();
        punto3.setLocation(puntoCentral);
        punto1.x-=200;
        punto1.y-=100;

        punto3.x+=200;
        punto3.y+=80;
        
        //PUNTOS
        Point puntoA = new Point(); //arriba
        puntoA.x=(punto1.x+punto3.x)/2;
        puntoA.y=punto1.y;
        puntos.add(puntoA);
        
        Point puntoB = new Point(); //derecha
        puntoB.x=punto3.x;
        puntoB.y=(punto1.y+punto3.y)/2;
        puntos.add(puntoB);
        
        Point puntoC = new Point(); //abajo
        puntoC.x=(punto1.x+punto3.x)/2;
        puntoC.y=punto3.y;
        puntos.add(puntoC);
        
        Point puntoD = new Point(); //izquierda
        puntoD.y=(punto1.y+punto3.y)/2;
        puntoD.x=punto1.x;
        puntos.add(puntoD);
        
        //relacion a 2 entidades
        if (relacion.entidadesSelec.size()==2){
            System.out.println("WUAAAAAAA DOS");
            Entidad entidad2 = relacion.entidadesSelec.get(1);
            entidad2.rectangulo.PosicionAgregacionDer(puntoEntidad2);
            entidad2.nombre.setLayoutX(puntoEntidad1.x+25);
            entidad2.nombre.setLayoutY(puntoEntidad1.y);
            //punto3.x+=120;
        }
        RectanguloAgregacion aaa = new RectanguloAgregacion(punto1,punto3);
        this.rectanguloAgregacion = aaa;
    }
    public void mover(Point punto,Pane pane){
        Point punto1=new Point();
        punto1.setLocation(puntoCentral);
        Point punto3=new Point();
        punto3.setLocation(puntoCentral);
        punto1.x-=200;
        punto1.y-=100;

        punto3.x+=200;
        punto3.y+=80;
        puntos.clear();
        
        Point puntoA = new Point(); //arriba
        puntoA.x=(punto1.x+punto3.x)/2;
        puntoA.y=punto1.y;
        puntos.add(puntoA);
        
        Point puntoB = new Point(); //derecha
        puntoB.x=punto3.x;
        puntoB.y=(punto1.y+punto3.y)/2;
        puntos.add(puntoB);
        
        Point puntoC = new Point(); //abajo
        puntoC.x=(punto1.x+punto3.x)/2;
        puntoC.y=punto3.y;
        puntos.add(puntoC);
        
        Point puntoD = new Point(); //izquierda
        puntoD.y=(punto1.y+punto3.y)/2;
        puntoD.x=punto1.x;
        puntos.add(puntoD);
        
        this.puntoCentral=punto;
        super.nombre.setLayoutX(punto.x-190);
        super.nombre.setLayoutY(punto.y-75); 
        rectanguloAgregacion.Mover(punto, pane);
    }
    
}