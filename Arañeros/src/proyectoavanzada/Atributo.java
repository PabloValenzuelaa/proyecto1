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
import javafx.scene.text.Text;
import proyectoavanzada.Poligonos.Poligono;

/**
 *
 * @author Diali
 */
public class Atributo {
    public Pane pane;
    public Poligono poligono;
    public Point punto;
    public Text texto;
    public ArrayList<Text> nombresEditados=new ArrayList();
    public ArrayList<Text> nombresEditados2=new ArrayList();
    public TipoAtributo tipo;
    public int largo;
    public Relacion relacion=null;
    public Entidad entidad=null;
    public ArrayList<Atributo> atributos =new ArrayList();
    //public Atributo generico;

    public Atributo(Pane pane, Point punto, Text texto, TipoAtributo tipo, int largo) {
        this.pane = pane;
        this.punto = punto;
        this.texto = texto;
        this.tipo = tipo;
        this.largo = largo;
    }


    public void dibujar(){
        this.poligono=new Poligono(pane);
        if(tipo.equals(TipoAtributo.Derivados)){
            poligono.DibujarElipsePunteada(100, largo, punto);
            texto.setLayoutX(punto.x-330);
            texto.setLayoutY(punto.y-10);
        }
        else if(tipo.equals(TipoAtributo.compuesto)){
            poligono.DibujarElipseNormal(100, largo, punto);
            texto.setLayoutX(punto.x-350);
            texto.setLayoutY(punto.y-10);
        }
        else if(tipo.equals(TipoAtributo.generico)){
            poligono.DibujarElipseNormal(100, largo, punto);
            texto.setLayoutX(punto.x-330);
            texto.setLayoutY(punto.y-10);
        }
        else if(tipo.equals(TipoAtributo.clave)){
            
            texto.setLayoutX(punto.x-330);
            texto.setLayoutY(punto.y-10);
            Line linea=new Line(punto.x-350, punto.y-5,punto.x-250, punto.y-5);
            poligono.lineas.add(linea);
            poligono.DibujarElipseNormal(100, largo, punto);
        }
        else if(tipo.equals(TipoAtributo.claveParcial)){
            
            texto.setLayoutX(punto.x-330);
            texto.setLayoutY(punto.y-10);
            int j=8;
            int k=0;
            for (int i = 0; i < 12; i++) {
                System.out.println("J: "+j);
                if(i%2==0){
                  Line linea= new Line(punto.x-350+k, punto.y-5,punto.x-350+j, punto.y-5);
                  linea.setStroke(Color.BLACK);
                  k+=8;
                  j+=8;
                }
                else{
                   Line linea= new Line(punto.x-350+k, punto.y-5,punto.x-350+j, punto.y-5);
                   linea.setStroke(Color.WHITE);
                  poligono.lineas.add(linea); 
                  k+=8;
                  j+=8;
                }
            }
            poligono.DibujarElipseNormal(100, largo, punto);
        }
        else if(tipo.equals(TipoAtributo.multivaluados)){
            try{
            poligono.DibujarElipseNormalDoble(100, largo, punto);
            texto.setLayoutX(punto.x-330);
            texto.setLayoutY(punto.y-10);
            }catch (Exception e){
        }
    }
        
    }

    public Poligono getPoligono() {
        return poligono;
    }

    public void setPoligono(Poligono poligono) {
        this.poligono = poligono;
    }

    public Text getTexto() {
        return texto;
    }

    public void setTexto(Text texto) {
        this.texto = texto;
    }
    public void mover(Point punto){
        poligono.borrar();
        this.punto=punto;
        dibujar();
    }
}
