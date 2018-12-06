/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoavanzada;

import java.awt.Point;
import proyectoavanzada.Poligonos.RectanguloAgregacion;

/**
 *
 * @author Pablo
 */
public class Agregacion {
    
    public Relacion relacion;
    public Point puntoCentral;
    public RectanguloAgregacion rectanguloAgregacion;
    
    public Agregacion(Relacion relacion) {
        this.puntoCentral=relacion.poligono.punto;
        
        Entidad entidad1 = relacion.entidadesSelec.get(0);
        Entidad entidad2 = relacion.entidadesSelec.get(1);
        entidad1.rectangulo.PosicionAgregacionIzq(puntoCentral);
        entidad2.rectangulo.PosicionAgregacionDer(puntoCentral);
        
        this.relacion = relacion;
        
        //Rectangulo
        Point punto1=puntoCentral;
        Point punto3=puntoCentral;

        punto1.x=punto1.x-300;
        
        punto3.x=punto3.x+300;

        RectanguloAgregacion aaa = new RectanguloAgregacion(punto1,punto3);
        this.rectanguloAgregacion = aaa;


    }

}