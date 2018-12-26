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
        
        Point puntoEntidad1=new Point();
        puntoEntidad1.setLocation(puntoCentral);
        puntoEntidad1.x+=310;
        Point puntoEntidad2=new Point();
        puntoEntidad2.setLocation(puntoCentral);
        puntoEntidad2.x+=230;
        
        Point puntoMenor = new Point();
        Point puntoMayor = new Point();
        
        obtenerPuntos();
        puntoMenor.setLocation(obtenerMenor());
        puntoMayor.setLocation(obtenerMayor());
        
        puntoMenor.x=puntoMenor.x-20;
        puntoMenor.y=puntoMenor.y-20;

        puntoMayor.x=puntoMayor.x-20;
        puntoMayor.y=puntoMayor.y-20;
        
        //Rectangulo
        Point punto1=new Point();
        punto1.setLocation(puntoCentral);
        Point punto3=new Point();
        punto3.setLocation(puntoCentral);
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
        try{
            if (relacion.entidadesSelec.get(0) instanceof Agregacion){
                Agregacion agrega = (Agregacion) relacion.entidadesSelec.get(0);
                for (int i = 0; i < agrega.puntos.size(); i++) {
                    if(agrega.puntos.get(i).x<punto1.x){
                        punto1.x=agrega.puntos.get(i).x-20;
                    }
                    if(agrega.puntos.get(i).y<punto1.y){
                        punto1.y=agrega.puntos.get(i).y-20;
                    }
                    if(agrega.puntos.get(i).x>punto3.x){
                        punto3.x=agrega.puntos.get(i).x+20;
                    }
                    if(agrega.puntos.get(i).y>punto3.y){
                        punto3.y=agrega.puntos.get(i).y+20;
                    }
                    puntos.clear();
                    puntoA = new Point(); //arriba
                    puntoA.x=(punto1.x+punto3.x)/2;
                    puntoA.y=punto1.y;
                    puntos.add(puntoA);

                    puntoB = new Point(); //derecha
                    puntoB.x=punto3.x;
                    puntoB.y=(punto1.y+punto3.y)/2;
                    puntos.add(puntoB);

                    puntoC = new Point(); //abajo
                    puntoC.x=(punto1.x+punto3.x)/2;
                    puntoC.y=punto3.y;
                    puntos.add(puntoC);

                    puntoD = new Point(); //izquierda
                    puntoD.y=(punto1.y+punto3.y)/2;
                    puntoD.x=punto1.x;
                    puntos.add(puntoD);
                }
            }
            else{
                Entidad entidad1 = relacion.entidadesSelec.get(0);
                entidad1.rectangulo.PosicionAgregacionIzq(puntoEntidad1);
                entidad1.nombre.setLayoutX(puntoEntidad1.x-295);
                entidad1.nombre.setLayoutY(puntoEntidad1.y);
            }
            //relacion a 2 entidades
            if (relacion.entidadesSelec.size()==2){
                if (relacion.entidadesSelec.get(1) instanceof Agregacion){
                    Agregacion agrega = (Agregacion) relacion.entidadesSelec.get(1);
                    for (int i = 0; i < agrega.puntos.size(); i++) {
                        if(agrega.puntos.get(i).x<punto1.x){
                            punto1.x=agrega.puntos.get(i).x-20;
                        }
                        if(agrega.puntos.get(i).y<punto1.y){
                            punto1.y=agrega.puntos.get(i).y-20;
                        }
                        if(agrega.puntos.get(i).x>punto3.x){
                            punto3.x=agrega.puntos.get(i).x+20;
                        }
                        if(agrega.puntos.get(i).y>punto3.y){
                            punto3.y=agrega.puntos.get(i).y+20;
                        }
                        //PUNTOS
                        puntos.clear();
                        puntoA = new Point(); //arriba
                        puntoA.x=(punto1.x+punto3.x)/2;
                        puntoA.y=punto1.y;
                        puntos.add(puntoA);

                        puntoB = new Point(); //derecha
                        puntoB.x=punto3.x;
                        puntoB.y=(punto1.y+punto3.y)/2;
                        puntos.add(puntoB);

                        puntoC = new Point(); //abajo
                        puntoC.x=(punto1.x+punto3.x)/2;
                        puntoC.y=punto3.y;
                        puntos.add(puntoC);

                        puntoD = new Point(); //izquierda
                        puntoD.y=(punto1.y+punto3.y)/2;
                        puntoD.x=punto1.x;
                        puntos.add(puntoD);
                        
                    }
                }
                else{//agregacion
                    Entidad entidad2 = relacion.entidadesSelec.get(1);
                    entidad2.rectangulo.PosicionAgregacionDer(puntoEntidad2);
                    entidad2.nombre.setLayoutX(puntoEntidad1.x+25);
                    entidad2.nombre.setLayoutY(puntoEntidad1.y);
                }
                //punto3.x+=120;
            }
        }
        catch(Exception e){
            
        }
        RectanguloAgregacion aaa = new RectanguloAgregacion(punto1,punto3);
        this.rectanguloAgregacion = aaa;
    }
    public void mover(Point punto,Pane pane){
        Point puntoEntidad1=new Point();
        puntoEntidad1.setLocation(puntoCentral);
        puntoEntidad1.x+=310;
        Point puntoEntidad2=new Point();
        puntoEntidad2.setLocation(puntoCentral);
        puntoEntidad2.x+=230;
        
        Point puntoMenor = new Point();
        Point puntoMayor = new Point();
        obtenerPuntos();
        puntoMenor.setLocation(obtenerMenor());
        puntoMayor.setLocation(obtenerMayor());
        
        puntoMenor.x=puntoMenor.x-20;
        puntoMenor.y=puntoMenor.y-20;

        puntoMayor.x=puntoMayor.x-20;
        puntoMayor.y=puntoMayor.y-20;
        
        //Rectangulo
        Point punto1=new Point();
        punto1.setLocation(puntoCentral);
        Point punto3=new Point();
        punto3.setLocation(puntoCentral);
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
        try{
            if (relacion.entidadesSelec.get(0) instanceof Agregacion){
                Agregacion agrega = (Agregacion) relacion.entidadesSelec.get(0);
                for (int i = 0; i < agrega.puntos.size(); i++) {
                    if(agrega.puntos.get(i).x<punto1.x){
                        punto1.x=agrega.puntos.get(i).x-20;
                    }
                    if(agrega.puntos.get(i).y<punto1.y){
                        punto1.y=agrega.puntos.get(i).y-20;
                    }
                    if(agrega.puntos.get(i).x>punto3.x){
                        punto3.x=agrega.puntos.get(i).x+20;
                    }
                    if(agrega.puntos.get(i).y>punto3.y){
                        punto3.y=agrega.puntos.get(i).y+20;
                    }
                    puntos.clear();
                    puntoA = new Point(); //arriba
                    puntoA.x=(punto1.x+punto3.x)/2;
                    puntoA.y=punto1.y;
                    puntos.add(puntoA);

                    puntoB = new Point(); //derecha
                    puntoB.x=punto3.x;
                    puntoB.y=(punto1.y+punto3.y)/2;
                    puntos.add(puntoB);

                    puntoC = new Point(); //abajo
                    puntoC.x=(punto1.x+punto3.x)/2;
                    puntoC.y=punto3.y;
                    puntos.add(puntoC);

                    puntoD = new Point(); //izquierda
                    puntoD.y=(punto1.y+punto3.y)/2;
                    puntoD.x=punto1.x;
                    puntos.add(puntoD);
                }
            }
            else{
                Entidad entidad1 = relacion.entidadesSelec.get(0);
                entidad1.rectangulo.PosicionAgregacionIzq(puntoEntidad1);
                entidad1.nombre.setLayoutX(puntoEntidad1.x-295);
                entidad1.nombre.setLayoutY(puntoEntidad1.y);
            }
            //relacion a 2 entidades
            if (relacion.entidadesSelec.size()==2){
                if (relacion.entidadesSelec.get(1) instanceof Agregacion){
                    Agregacion agrega = (Agregacion) relacion.entidadesSelec.get(1);
                    for (int i = 0; i < agrega.puntos.size(); i++) {
                        if(agrega.puntos.get(i).x<punto1.x){
                            punto1.x=agrega.puntos.get(i).x-20;
                        }
                        if(agrega.puntos.get(i).y<punto1.y){
                            punto1.y=agrega.puntos.get(i).y-20;
                        }
                        if(agrega.puntos.get(i).x>punto3.x){
                            punto3.x=agrega.puntos.get(i).x+20;
                        }
                        if(agrega.puntos.get(i).y>punto3.y){
                            punto3.y=agrega.puntos.get(i).y+20;
                        }
                        //PUNTOS
                        puntos.clear();
                        puntoA = new Point(); //arriba
                        puntoA.x=(punto1.x+punto3.x)/2;
                        puntoA.y=punto1.y;
                        puntos.add(puntoA);

                        puntoB = new Point(); //derecha
                        puntoB.x=punto3.x;
                        puntoB.y=(punto1.y+punto3.y)/2;
                        puntos.add(puntoB);

                        puntoC = new Point(); //abajo
                        puntoC.x=(punto1.x+punto3.x)/2;
                        puntoC.y=punto3.y;
                        puntos.add(puntoC);

                        puntoD = new Point(); //izquierda
                        puntoD.y=(punto1.y+punto3.y)/2;
                        puntoD.x=punto1.x;
                        puntos.add(puntoD);
                        
                    }
                }
                else{//agregacion
                    Entidad entidad2 = relacion.entidadesSelec.get(1);
                    entidad2.rectangulo.PosicionAgregacionDer(puntoEntidad2);
                    entidad2.nombre.setLayoutX(puntoEntidad1.x+25);
                    entidad2.nombre.setLayoutY(puntoEntidad1.y);
                }
                //punto3.x+=120;
            }
            
        }
        catch(Exception e){
            
        }
        
        this.puntoCentral=punto;
        super.nombre.setLayoutX(punto.x-190);
        super.nombre.setLayoutY(punto.y-75); 
        rectanguloAgregacion.Mover(punto1,punto3, pane);
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
}
