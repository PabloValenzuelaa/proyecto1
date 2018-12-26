/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoavanzada;

import java.awt.Point;
import javafx.scene.shape.Line;
import proyectoavanzada.Poligonos.Poligono;
import proyectoavanzada.Poligonos.Rectangulo;

/**
 *
 * @author Diali
 */
public class Union {
    public Relacion relacion;
    public Entidad entidad;
    
    public Atributo atributo;
    
    public Atributo atributo2=null;
    public boolean unoAuno;
    
    public boolean doble;
    Line linea;
    
    public Agregacion agregacion;

    public Union(Relacion relacion, Entidad entidad, Atributo atributo, Agregacion agregacion) {
        this.relacion = relacion;
        this.entidad = entidad;
        this.atributo = atributo;
        this.agregacion=agregacion;
        unoAuno=false;
        doble=false;
    }
    
    
    public Line  CrearRelacion(Poligono poligono){
        double distanciaMinima= 7000;
        Point punto1Ant=new Point();
        Point punto2Ant=new Point();
        Point punto1=new Point();
        Point punto2=new Point();
        for (int j = 0; j <entidad.rectangulo.puntos.size(); j++) { //puntos del rectangulo
            for (int k = 1; k < poligono.getPuntos().size(); k++) { //puntos del poligono
                    double distanciaSiguiente=entidad.rectangulo.puntos.get(j).distance(poligono.getPuntos().get(k));
                    if(distanciaSiguiente<distanciaMinima  ){
                        distanciaMinima=distanciaSiguiente;
                        punto1Ant=punto1;
                        punto2Ant=punto2;
                        punto1=entidad.rectangulo.puntos.get(j);
                        punto2=poligono.getPuntos().get(k);
                    }
            }
        }
        if(unoAuno){
            linea=new Line(punto1Ant.x, punto1Ant.y, punto2Ant.x, punto2Ant.y);
            return linea;
        }
        Line linea=new Line(punto1.x, punto1.y, punto2.x, punto2.y);
        return linea;
    }
    public Line  CrearRelacionAgregacion(Poligono poligono){
        double distanciaMinima= 7000;
        Point punto1Ant=new Point();
        Point punto2Ant=new Point();
        Point punto1=new Point();
        Point punto2=new Point();
        for (int j = 0; j <agregacion.puntos.size(); j++) { //puntos del rectangulo
            for (int k = 1; k < poligono.getPuntos().size(); k++) { //puntos del poligono
                    double distanciaSiguiente=agregacion.puntos.get(j).distance(poligono.getPuntos().get(k));
                    if(distanciaSiguiente<distanciaMinima  ){
                        distanciaMinima=distanciaSiguiente;
                        punto1Ant=punto1;
                        punto2Ant=punto2;
                        punto1=agregacion.puntos.get(j);
                        punto2=poligono.getPuntos().get(k);
                    }
            }
        }
       
        Line linea=new Line(punto1.x, punto1.y, punto2.x, punto2.y);
        return linea;
    }
    public Line  CrearRelacionPoligono(Poligono poligono,Poligono poligono2){
        double distanciaMinima= 7000;
        Point punto1Ant=new Point();
        Point punto2Ant=new Point();
        Point punto1=new Point();
        Point punto2=new Point();
        for (int j = 0; j <poligono2.puntos.size(); j++) { //puntos del rectangulo
            for (int k = 1; k < poligono.getPuntos().size(); k++) { //puntos del poligono
                    double distanciaSiguiente=poligono2.puntos.get(j).distance(poligono.getPuntos().get(k));
                    if(distanciaSiguiente<distanciaMinima  ){
                        distanciaMinima=distanciaSiguiente;
                        punto1Ant=punto1;
                        punto2Ant=punto2;
                        punto1=poligono2.puntos.get(j);
                        punto2=poligono.getPuntos().get(k);
                    }
            }
        }
        linea=new Line(punto1.x, punto1.y, punto2.x, punto2.y);
        return linea;
    }
     public Line  CrearRelacionDoble(){
        double distanciaMinima= 7000;
        Point punto1Ant=new Point();
        Point punto2Ant=new Point();
        Point punto1=new Point();
        Point punto2=new Point();
        for (int j = 0; j <entidad.rectangulo.puntos.size(); j++) { //puntos del rectangulo
            for (int k = 1; k < relacion.poligono.getPuntos().size(); k++) { //puntos del poligono
                    double distanciaSiguiente=entidad.rectangulo.puntos.get(j).distance(relacion.poligono.getPuntos().get(k));
                    if(distanciaSiguiente<distanciaMinima  ){
                        distanciaMinima=distanciaSiguiente;
                        punto1Ant=punto1;
                        punto2Ant=punto2;
                        punto1=entidad.rectangulo.puntos.get(j);
                        punto2=relacion.poligono.getPuntos().get(k);
                    }
            }
        }
        linea=new Line(punto1.x+3, punto1.y+3, punto2.x+3, punto2.y+3);
        return linea;
    }

    public Line getLinea() {
        
        if(atributo!=null&&entidad!=null){
            this.linea=CrearRelacion(atributo.poligono);
            return linea;
        }
        if(atributo!=null&&relacion!=null){
            this.linea=CrearRelacionPoligono(atributo.poligono,relacion.poligono);
            return linea;
        }
        if(entidad!=null&&relacion!=null){
            if(doble){
                this.linea=CrearRelacionDoble();
                return linea;
            }
            this.linea=CrearRelacion(relacion.poligono);
            return linea;
        }
        if(relacion!=null&&agregacion!=null){
            this.linea=CrearRelacionAgregacion(relacion.poligono);
            
            return linea;
        }
        if(atributo2!=null){
            this.linea=CrearRelacionPoligono(atributo2.poligono, atributo.poligono);
        }
        return linea;
        
    }
    public void borrarLinea(){
        this.linea=null;
    }
}
