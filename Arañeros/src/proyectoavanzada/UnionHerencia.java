/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoavanzada;

import java.awt.Point;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Text;
import proyectoavanzada.Poligonos.Poligono;
import proyectoavanzada.Poligonos.Rectangulo;

/**
 *
 * @author Diali
 */
public class UnionHerencia {
    public Entidad entidad;
    public ArrayList<Entidad> entidadesHeredadas= new ArrayList();
    public ArrayList<Entidad> entidadesBorradas= new ArrayList();
    public Poligono circulo;
    public Pane pane;
    public ArrayList <Line> lineas=new ArrayList<>();
    public ArrayList<Poligono> curvas=new ArrayList<>();
    public Text nombre;
    Point punto1=new Point();
    Point punto2=new Point();
    
    Point puntoo=new Point();
    Point puntoCirculo=new Point();
    int rec;
    Line linea1,linea2, linea3,linea4;
    public UnionHerencia(Entidad entidad, Pane pane) {
        this.entidad = entidad;
        this.pane = pane;
        this.circulo=new Poligono(pane);
        int distancia=(int)(entidad.rectangulo.puntos.get(2).distance(entidad.rectangulo.puntos.get(3))/2);
        Point punto=new Point(entidad.rectangulo.puntos.get(2).x,entidad.rectangulo.puntos.get(2).y);
        Point puntito=punto;
        puntito.x+=distancia+300;
        puntito.y+=70;
        circulo.Dibujar(50, 15, puntito);
        puntoo=punto;
        linea1=new Line(punto.getX()-300, puntito.getY()-30, puntito.getX()-300, puntito.getY()-70);
        lineas.add(linea1);
        pane.getChildren().add(linea1);
        
    }
    public void agregarHerencia(Entidad entidad){
        dibujar(entidad);
        entidadesHeredadas.add(entidad);
    }
    public Line uniones(Rectangulo rectangulo){
        double distanciaMinima= 7000;
        for (int j = 0; j <rectangulo.puntos.size(); j++) { //puntos del rectangulo
            for (int k = 1; k < circulo.getPuntos().size(); k++) { //puntos del poligono
                    double distanciaSiguiente=rectangulo.puntos.get(j).distance(circulo.getPuntos().get(k));
                    if(distanciaSiguiente<distanciaMinima  ){
                        distanciaMinima=distanciaSiguiente;
                        punto1=new Point(rectangulo.puntos.get(j).x,rectangulo.puntos.get(j).y) ;
                        punto2=new Point(circulo.getPuntos().get(k).x, circulo.getPuntos().get(k).y);
                        rec=j+1;
                    }
            }
        }
        punto1.x=(int)(rectangulo.puntos.get(0).getX()+rectangulo.puntos.get(1).getX())/2;
        punto1.y=(int)(rectangulo.puntos.get(0).getY()+rectangulo.puntos.get(1).getY())/2;
        puntoCirculo=new Point(punto2);
        Line linea=new Line(punto1.x, punto1.y, punto2.x, punto2.y);
        return linea;
    }
    public void borrar(){
        circulo.borrar();
        circulo.puntos.clear();
        circulo.lineas.clear();
        pane.getChildren().remove(nombre);
        for (int i = 0; i < lineas.size(); i++) {
            pane.getChildren().remove(lineas.get(i));
        }
        lineas.clear();
        for (int i = 0; i < curvas.size(); i++) {
            curvas.get(i).borrar();
            curvas.get(i).puntos.clear();
            curvas.get(i).lineas.clear();
        }
        curvas.clear();
    }
    public void actualizar1(){
        borrar();
        int distancia=(int)(entidad.rectangulo.puntos.get(2).distance(entidad.rectangulo.puntos.get(3))/2);
        Point punto=new Point();
        
        nombre.getLayoutY();
        punto.setLocation(entidad.rectangulo.puntos.get(2).x, entidad.rectangulo.puntos.get(2).y);
        if(punto!=puntoo){
            
            punto.x+=distancia+300;
            punto.y+=70;
            Text nombre2=new Text(punto.x-305,punto.y-10, nombre.getText());
            pane.getChildren().add(nombre2);
            this.nombre=nombre2;
            puntoo=punto;
        }
        circulo.Dibujar(50, 15, punto);
        
        
        Line linea=new Line(punto.getX()-300, punto.getY()-30, punto.getX()-300, punto.getY()-70);
        lineas.add(linea);
        pane.getChildren().add(linea);
        for (int i = 0; i < entidadesHeredadas.size(); i++) {
            actualizar2(i);
        }
        
        
    }
    public void actualizar2(int i){
            dibujar(entidadesHeredadas.get(i));
    }
    public void dibujar(Entidad entidad){
        Line linea=uniones(entidad.rectangulo);
        lineas.add(linea);
        pane.getChildren().add(linea);
        
        int ctrlx=(int)(punto1.getX()+punto2.getX())/2;
        int ctrly=(int)(punto1.getY()+punto2.getY())/2;
        Point punto=new Point();
        punto.setLocation(ctrlx+300, ctrly);
        Poligono curva=new Poligono(pane);
        if(rec==1){
            
            curva.generarCurva1(50, 15, punto);
        }
        if(rec==2){
            curva.generarCurva2(50, 15, punto);
        }
        if(rec==3){
            curva.generarCurva4(50, 15, punto);
        }
        if(rec==4){
            curva.generarCurva3(50, 15, punto);
        }
        curvas.add(curva);
    }
}
