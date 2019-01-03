/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoavanzada;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import proyectoavanzada.Poligonos.Poligono;
import proyectoavanzada.Poligonos.Rectangulo;

/**
 * FXML Controller class
 *
 * @author Matias
 */
public class EntidadController implements Initializable {
    
    public double posX;
    public double posY;
    public boolean sePuedeCrearEntidad=false;
    public boolean sePuedeCrearRelacion=false;
    public boolean sePuedeDibujar=false;
    public boolean sePuedeSeleccionar=false;
    public boolean sePuedeCrearAtributo=false;
    public boolean sePuedeSeleccionarEntidadAtributo=false;
    public Rectangulo rectangulo;
    
    
    public int posicionPuntoMenorXPoligono=0;
    public int posicionPuntoMenorYPoligono=0;
    public int posicionPuntoMenorXRectangulo=0;
    public int posicionPuntoMenorYRectangulo=0;
    public int p=0;
    public int contadorPuntos=0;
    
    @FXML 
    public Circle circulo;
    public ContextMenu contextMenuAtributos = new ContextMenu();
    public ContextMenu contextMenuEntidades = new ContextMenu();
    public ContextMenu contextMenuHerencia = new ContextMenu();
    @FXML
    public Text nombreEntidad;
    public double largoTexto=0;
    public Text textito = new Text();
    @FXML
    public Text texto= null;
    @FXML
    public Text nombre;
    @FXML
    public Text nombreHerencia=new Text();
    @FXML
    public Text textoBotonCrear;
    @FXML
    public Pane pane;
    @FXML
    public TextField insertarTexto1;
    @FXML
    public Button botonEntidad;
    @FXML
    public Button terminar;
    @FXML
    public ImageView imagenTerminar;
    @FXML
    public Button botonCrear;
    @FXML
    public Button botonMover;
    @FXML
    public Button borrar;
    @FXML
    public Button herencia;
    public ArrayList<Entidad> entidades= new ArrayList();
    public ArrayList<Entidad> entidadesBorradas= new ArrayList();
    public ArrayList<Relacion> relaciones= new ArrayList();
    public ArrayList<Atributo> atributosCompuestos= new ArrayList();   
    public ArrayList<Atributo> atributos= new ArrayList();    
    public ArrayList<Entidad> entidadesSeleccionadas= new ArrayList();
    public ArrayList<Relacion> relacionesSeleccionadas= new ArrayList();
    public ArrayList<Entidad> entidadesHeredadas= new ArrayList();
    public ArrayList<Relacion> relacionesAModificar= new ArrayList();
    public ArrayList<Agregacion> agregaciones= new ArrayList();
    public ArrayList<ArrayList<Union>> UnionesBorradas=new ArrayList<>();
    
    public ArrayList modificaciones= new ArrayList();
    public ArrayList redo= new ArrayList();
    public int relacionNumero=0;
    public Point puntoCar=new Point();
    
    public static ArrayList<UnionHerencia> herencias= new ArrayList();
    public ArrayList<UnionHerencia> herenciasBorradas= new ArrayList();
    public ArrayList distanciaEntrePuntos= new ArrayList();
    public ArrayList circulos= new ArrayList();
    public ArrayList<Line> lineas = new ArrayList();
    public ArrayList<Union> borradas=new ArrayList<>();
    public Point punto;
    public Point puntoDisSol = new Point();
    public int posPunto;
    public int posPunto2;
    public ArrayList<Union> uniones=new ArrayList();
    public boolean crearHerencia=false;
    public boolean elegirEntidadesHeredadas=false,sePuedeEditarCar=false,sePuedeDibujarDoble=false,sePuedeDibujarSimple=false;
    public boolean atributoEntidad=false;
    public boolean atributoRelacion=false;
    boolean sePuedeSeleccionarBorrar = false;
    public boolean seSeleccionoEntidad=false;
    public boolean seSeleccionoRelacion=false;
    public boolean seSeleccionoAtributo=false;
    public boolean seSeleccionoHerencia=false;
    public boolean seSeleccionoAgregacion=false;
    public boolean seSeleccionoCompuesto=false;
    public boolean sePuedeEditar=false;
    public boolean sePuedeCrearAgregacion=false;
    public int objetoNumero=-1;
    public int agregacionNumero=0;
    public Atributo atributoCompuesto;
    public boolean seMueveElemento=false;
    public boolean seMueveAgr=false;
    public boolean seMueve=false;
    public Point puntoDif= new Point();
    public Object movido;
    public ArrayList objetosmovidos=new ArrayList();
    public ArrayList<Point> puntosTrasladados=new ArrayList<>();
    @FXML
    public void transportar(){
        punto = MouseInfo.getPointerInfo().getLocation();
        Point punto2=new Point(punto);
        seMueve=true;
        Object objeto= mover(punto);
        seMueve=false;
        if(objeto!=null){
            if(objeto== movido){
                puntosTrasladados.add(punto2);                
            }
            else{
                movido=objeto;
                puntosTrasladados.clear();
            }
                
        }
    }
    
    @FXML
    public Object mover(Point punto){
         //para saber si muevo alguna entidad/relacion/atributo y diferenciarlo de agregacion
        if(!seMueveElemento ){
            for (int i = 0; i < relaciones.size(); i++) { //mueve relaciones
                punto = MouseInfo.getPointerInfo().getLocation();
                punto.x=punto.x-300;
                
                boolean dentro=true;
                boolean contiene=false;
                int cualEs=0;
                if(relaciones.get(i).poligono.seleccionar(punto) ){ //interseccion
                    seMueveElemento=true;
                    if(relaciones.get(i) instanceof RelaciónDebil){
                        punto.x=punto.x+300;
                        Poligono poligono2 = relaciones.get(i).poligono;
                        RelaciónDebil nv= (RelaciónDebil)relaciones.get(i);
                        nv.poligono2.mover(punto);
                        contadorPuntos--;
                        puntosDeControl();
                        punto.x=punto.x-300;
                    }
                    relaciones.get(i).nombre.setLayoutX(punto.x-15);
                    relaciones.get(i).nombre.setLayoutY(punto.y-15);
                    punto.x=punto.x+300;
                    relaciones.get(i).poligono.mover(punto);
                    if(relaciones.get(i) instanceof RelaciónDebil){
                        RelaciónDebil relacionDebil=(RelaciónDebil)relaciones.get(i);
                        relacionDebil.poligono2.mover(punto);

                    }
                    for (int k = 0; k < circulos.size(); k++) {
                        pane.getChildren().remove(circulos.get(k));
                    } 
                    contadorPuntos--;
                    puntosDeControl();
                    actualizarUniones();
                    actualizarAgregaciones();
                    return relaciones.get(i);
                }
            }
        }
        if(!seMueveElemento){
            for (int i = 0; i < entidades.size(); i++){
               if(seMueve)
               punto = MouseInfo.getPointerInfo().getLocation();
               posX=punto.getX()-280;
               posY=punto.getY();
               if (interseccionTransportar(entidades.get(i).rectangulo.getPuntos(),i)){
                   seMueveElemento=true;
                   if (entidades.get(i) instanceof EntidadDebil){
                        EntidadDebil entidadDebil = (EntidadDebil)entidades.get(i);
                        punto.x=punto.x-20;
                        punto.y=punto.y-20;
                        entidadDebil.rectangulo2.MoverRecantulo2(punto);
                        punto.x=punto.x+20;
                        punto.y=punto.y+20;
                    }

                    Rectangulo rectangulito = entidades.get(i).rectangulo;
                    entidades.get(i).nombre.setLayoutX(posX-40);
                    entidades.get(i).nombre.setLayoutY(posY-20);
                    punto.x=punto.x-20;
                    punto.y=punto.y-20;
                    rectangulito.Mover(punto);
                    for (int k = 0; k < circulos.size(); k++) {
                        
                        pane.getChildren().remove(circulos.get(k));
                    }   

                    contadorPuntos--;
                    puntosDeControl();
                    actualizarUniones();
                    actualizarAgregaciones();

                    for (int j = 0; j < herencias.size(); j++) {
                        herencias.get(j).actualizar1();
                    }
                    return entidades.get(i);

               }
            }
        }
        if (!seMueveElemento){
            for (int i = 0; i < atributos.size(); i++){
                if(seMueve)
                punto = MouseInfo.getPointerInfo().getLocation();
                punto.x-=300;
                punto.y-=20;
                if(atributos.get(i).poligono.seleccionar(punto)){
                    seMueveElemento=true;
                    punto.x+=300;
                    punto.y+=20;
                    atributos.get(i).mover(punto);
                    actualizarUniones();
                    for (int k = 0; k < circulos.size(); k++) {
                        pane.getChildren().remove(circulos.get(k));
                    } 
                    contadorPuntos--;
                    puntosDeControl();
                    actualizarAgregaciones();
                    return atributos.get(i);
                }
            }
        }
        if(seMueve)
        punto = MouseInfo.getPointerInfo().getLocation();
        punto.x=punto.x-200;
        punto.y=punto.y+50;
        if(!seMueveElemento){
            for (int j = 0; j < agregaciones.size(); j++) {
                if(interseccionTransportarAgregacion(agregaciones.get(j).rectanguloAgregacion.punto1,
                    agregaciones.get(j).rectanguloAgregacion.punto3,punto) && false){
                    agregaciones.get(j).mover(punto, pane);
                    //movemos las entidades
                    for (int i = 0; i < agregaciones.get(j).relacion.entidadesSelec.size(); i++) {
                        puntoDif.setLocation(punto);
                        puntoDif.x=puntoDif.x+(agregaciones.get(j).relacion.entidadesSelec.get(i).rectangulo.punto.x-agregaciones.get(j).puntoCentral.x);
                        puntoDif.y=puntoDif.y+(agregaciones.get(j).relacion.entidadesSelec.get(i).rectangulo.punto.y-agregaciones.get(j).puntoCentral.y);
                        puntoDif.x=puntoDif.x+300;
                        puntoDif.y=puntoDif.y+25;
                        agregaciones.get(j).relacion.entidadesSelec.get(i).nombre.setLayoutX(puntoDif.x-290);
                        agregaciones.get(j).relacion.entidadesSelec.get(i).nombre.setLayoutY(puntoDif.y);
                        agregaciones.get(j).relacion.entidadesSelec.get(i).rectangulo.Mover(puntoDif);
                    }
                    //movemos la relacion
                    puntoDif.setLocation(punto);
                    puntoDif.x=puntoDif.x+(agregaciones.get(j).puntoCentral.x-agregaciones.get(j).relacion.poligono.punto.x);
                    puntoDif.y=puntoDif.y+(agregaciones.get(j).puntoCentral.y-agregaciones.get(j).relacion.poligono.punto.y);
                    puntoDif.x=puntoDif.x+300;
                    puntoDif.y=puntoDif.y+25;
                    agregaciones.get(j).relacion.nombre.setLayoutX(puntoDif.x-290);
                    agregaciones.get(j).relacion.nombre.setLayoutY(puntoDif.y);
                    agregaciones.get(j).relacion.poligono.mover(puntoDif);
                    
                    
                    for (int k = 0; k < circulos.size(); k++) {
                        pane.getChildren().remove(circulos.get(k));
                    } 
                    actualizarUniones();
                    actualizarAgregaciones();
                    contadorPuntos--;
                    puntosDeControl();
                    seMueveElemento=true;
                    return agregaciones.get(j);
                }
                
            }
        }
        /*
        hacer este for mas arriba, y condicionar para que no se muevan las relaciones/entidades/atributos
            booleano seMueveAgregacion...
        guardar las distancias respecto al centro...
        */
        seMueveElemento=false;
        return null;
    }
    
    @FXML
    public void dibujar(){
        if(sePuedeDibujar){
            punto = MouseInfo.getPointerInfo().getLocation();
            posX=punto.getX()-300;
            posY=punto.getY()-10;
            textito = new Text();
            textito.setFill(Color.BLACK);
            textito.setStyle(texto.getStyle());
            textito.setFont(texto.getFont());
            textito.setText(insertarTexto1.getText());
            if(textito.getText().length()==0){
                if(entidades.size()>=0 && sePuedeCrearEntidad){
                    textito.setText("E "+(entidades.size()+1));
                }
                else if(relaciones.size()>=0 && sePuedeCrearRelacion){
                    textito.setText("R "+relaciones.size());
                }
                else if(atributos.size()>=0&&sePuedeCrearAtributo){
                    textito.setText("A "+atributos.size());
                }
            }
            textito.setLayoutX(posX+3);
            textito.setLayoutY(posY+10);            
            textito.setVisible(false);

            pane.getChildren().add(textito);
            
            if(insertarTexto1.getText().length()<6){
                largoTexto=6*10;
            }
            else{
                largoTexto=insertarTexto1.getText().length()*10;
            }
            

            if (sePuedeCrearEntidad){
                contextMenuEntidades.show(pane,posX+300,posY+10);
                contadorPuntos--;
                puntosDeControl();
                
                sePuedeCrearEntidad=false;
            }
            else if(sePuedeCrearRelacion){
                //textito.setVisible(true);
                Poligono poligono=new Poligono(pane);
                Poligono poligono2= new Poligono(pane);
                Relacion relacion = new Relacion(textito,poligono,entidadesSeleccionadas);
                Relacion relacion2= new RelaciónDebil(poligono2,textito,poligono,entidadesSeleccionadas);
                
                if(entidadesSeleccionadas.size()>0){
                    if(entidadesSeleccionadas.size()==1||entidadesSeleccionadas.size()==2){
                        for (int i = 0; i < entidadesSeleccionadas.size(); i++) { //busco si hay una entidad debil
                            if(entidadesSeleccionadas.get(i) instanceof EntidadDebil){
                                poligono2.Dibujar(4, (int) 75+5, punto);
                                relacion=relacion2;
                                break;
                            }
                        }
                        poligono.Dibujar(4, (int) 75, punto);
                    }
                    else{
                        for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
                            if(entidadesSeleccionadas.get(i) instanceof EntidadDebil){
                                poligono2.Dibujar(entidadesSeleccionadas.size(), (int) 75+5, punto);
                                relacion=relacion2;
                                break;
                            }
                        }
                        poligono.Dibujar(entidadesSeleccionadas.size(), (int) 75, punto);
                    }
                    textito.setLayoutX(posX-30);
                    textito.setLayoutY(posY);            
                    textito.setVisible(true);
                    if(entidadesSeleccionadas.size()==1){
                        relacion.unoAuno=true;
                        relacion2.unoAuno=true;
                    }
                    modificaciones.add(relacion);
                    relaciones.add(relacion);
                    //funcion para unir
                    for(int i=0; i<entidadesSeleccionadas.size();i++){ //entidades a relacionar
                        //dibujar linea que une
                        if(entidadesSeleccionadas.get(i) instanceof EntidadDebil){
                            entidadesSeleccionadas.get(i).relaciones.add(relacion2);
                            Union union=new Union(relacion2, entidadesSeleccionadas.get(i), null,null,puntoCar);
                            union.doble=true;
                            
                            entidadesSeleccionadas.get(i).uniones.add(union);
                            relacion2.uniones.add(union);
                            Line lineaa =union.getLinea();
                            uniones.add(union);
                            lineaa.setStroke(Color.BLACK); //color de la linea que une
                            lineaa.setStrokeWidth(1);
                            lineaa.setStrokeLineCap(StrokeLineCap.ROUND);
                            pane.getChildren().add(lineaa);
                            entidadesSeleccionadas.get(i).getLineas().add(lineaa);
                            relacion2.getLineas().add(lineaa);
                            
                        }
                        if(entidadesSeleccionadas.get(i) instanceof Agregacion){
                            entidadesSeleccionadas.get(i).relaciones.add(relacion);
                            Union union=new Union(relacion, null, null,(Agregacion)entidadesSeleccionadas.get(i),puntoCar);
                            entidadesSeleccionadas.get(i).uniones.add(union);
                            
                            relacion.uniones.add(union);
                            Line lineaa=union.getLinea();
                            puntoCar=union.puntoCar;
                            textito = new Text();
                            textito.setFill(Color.BLACK);
                            textito.setStyle(texto.getStyle());
                            textito.setFont(texto.getFont());
                            textito.setText("N");
                            textito.setLayoutX(puntoCar.x);
                            textito.setLayoutY(puntoCar.y);            
                            textito.setVisible(true);
                            pane.getChildren().add(textito);
                            union.setCar(textito);
                            uniones.add(union);
                            lineaa.setStroke(Color.BLACK); //colo de la linea que une
                            lineaa.setStrokeWidth(1);
                            lineaa.setStrokeLineCap(StrokeLineCap.ROUND);
                            pane.getChildren().add(lineaa);
                            entidadesSeleccionadas.get(i).getLineas().add(lineaa);
                            //entidadesSeleccionadas.get(i).getLineas().add(lineaa);
                            relacion.getLineas().add(lineaa);
                        }
                        else{
                            entidadesSeleccionadas.get(i).relaciones.add(relacion);
                            Union union=new Union(relacion, entidadesSeleccionadas.get(i), null,null,puntoCar);
                            Line lineaa=union.getLinea();
                            relacion.uniones.add(union);
                            puntoCar=union.puntoCar;
                            textito = new Text();
                            textito.setFill(Color.BLACK);
                            textito.setStyle(texto.getStyle());
                            textito.setFont(texto.getFont());
                            textito.setText("N");
                            textito.setLayoutX(puntoCar.x);
                            textito.setLayoutY(puntoCar.y);            
                            textito.setVisible(true);
                            pane.getChildren().add(textito);
                            union.setCar(textito);
                            uniones.add(union);
                            entidadesSeleccionadas.get(i).uniones.add(union);
                            lineaa.setStroke(Color.BLACK); //colo de la linea que une
                            lineaa.setStrokeWidth(1);
                            lineaa.setStrokeLineCap(StrokeLineCap.ROUND);
                            pane.getChildren().add(lineaa);
                            entidadesSeleccionadas.get(i).getLineas().add(lineaa);
                            relacion.getLineas().add(lineaa);
                        }
                    }
                    if(entidadesSeleccionadas.size()==1){
                        relacion.unoAuno=true;
                        Union union=new Union(relacion, entidadesSeleccionadas.get(0), null,null,puntoCar);
                        union.unoAuno=true;
                        relacion.uniones.add(union);
                        Line lineaa=union.getLinea();
                        uniones.add(union);
                        lineaa.setStroke(Color.BLACK); //coloco de la linea que une
                        lineaa.setStrokeWidth(1);
                        lineaa.setStrokeLineCap(StrokeLineCap.ROUND);
                        pane.getChildren().add(lineaa);
                        entidadesSeleccionadas.get(0).getLineas().add(lineaa);
                    }
                      
                    for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
                        if(entidadesSeleccionadas.get(i) instanceof Agregacion){
                            Agregacion agregacion= (Agregacion)entidadesSeleccionadas.get(i);
                            agregacion.rectanguloAgregacion.repintarNegro();
                        
                        }
                        else{
                            entidadesSeleccionadas.get(i).rectangulo.Borrar();
                            entidadesSeleccionadas.get(i).rectangulo.Dibujar();
                            entidadesSeleccionadas.get(i).rectangulo.seleccionado=false;
                        }
                    }
                    contadorPuntos--;
                    puntosDeControl();
                    System.out.println("Ya cree la relacion...");
                    sePuedeCrearRelacion=false;  
                }
                else{
                    textito.setVisible(false);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Informacion");
                    alert.setHeaderText("Entidades");
                    alert.setContentText("Debe Seleccionar entidades");
                    alert.showAndWait();
                    
                }
            }
            else if(sePuedeCrearAtributo){
                textito.setLayoutX(posX-30);
                textito.setLayoutY(posY);
                if(seSeleccionoCompuesto){
                    crearCompuesto();
                }
                else{
                    contextMenuAtributos.show(pane,posX+300,posY+10);
                    if(entidadesSeleccionadas.size()==1 || relacionesSeleccionadas.size()==1){
                        //Creamos los atributos correspondientes mediante el sub_menu...
                    }
                    else{
                        textito.setVisible(false);
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Informacion");
                        alert.setHeaderText("Entidades");
                        alert.setContentText("Debe Seleccionar entidades o relaciones");
                        alert.showAndWait();
                    }
                }
                sePuedeCrearAtributo=false;
                
            }
            repintarTodoNegro();
        }
    
        else if(crearHerencia){
                punto = MouseInfo.getPointerInfo().getLocation();
                punto.x-=300;
                punto.y-=25;
                for (int i = 0; i < entidades.size(); i++) {
                    if (interseccionRectangulo(entidades.get(i),punto)){
                        crearHerencia=false;
                        UnionHerencia herencia=new UnionHerencia(entidades.get(i), pane);
                        puntoDisSol.setLocation(herencia.puntoCirculo);
                        puntoDisSol.x-=305;
                        puntoDisSol.y-=10;
                        crearHerencia=false;
                        herencias.add(herencia);
                        modificaciones.add(herencia);
                        elegirEntidadesHeredadas=true;
                        sePuedeSeleccionar=false;
                        seSeleccionoHerencia=true;
                        contadorPuntos--;
                        puntosDeControl();
                        contextMenuHerencia.show(pane,punto.x+300,punto.y+70);
                        break;
                    }
                }    
        }
        else if (sePuedeSeleccionar){
                //seSeleccionoRelacion=false; 
                //seSeleccionoEntidad=false; 
                //seSeleccionoHerencia=false;
                Point punto = MouseInfo.getPointerInfo().getLocation();
                posX=punto.getX()-300;
                posY=punto.getY()-25;
                for (int i = 0; i < entidades.size(); i++) {
                    //entidades.get(i).rectangulo.imprimirPuntos();
                    if (interseccionRectangulo(entidades.get(i).rectangulo.getPuntos(),i)){
                        if (sePuedeCrearAtributo){
                            atributoEntidad=true;
                            atributoRelacion=false;
                            for (int j = 0; j < relacionesSeleccionadas.size(); j++) {
                                relacionesSeleccionadas.get(j).poligono.repintarNegro();
                            }
                            relacionesSeleccionadas.clear();
                            for (int p = 0; p < entidadesSeleccionadas.size(); p++) {
                                entidadesSeleccionadas.get(p).rectangulo.Borrar();
                                entidadesSeleccionadas.get(p).rectangulo.Dibujar();
                                entidadesSeleccionadas.get(p).rectangulo.seleccionado=false;
                            }
                            entidades.get(i).rectangulo.Dibujar2();
                            entidades.get(i).rectangulo.seleccionado=false;
                            entidadesSeleccionadas.clear();
                            //REPINTAMOS NEGRO
                        }
                        //ENTIDAD SELECCIONADA
                        if (sePuedeEditar){
                            insertarTexto1.setVisible(true);
                            botonCrear.setVisible(true);//MODIFICAR NOMBRE DE CREAR--MODIFICAR (textoBotonCrear)
                            textoBotonCrear.setVisible(true);
                            textoBotonCrear.setText("Editar");
                            seSeleccionoEntidad=true;
                            seSeleccionoRelacion=false;
                            seSeleccionoAtributo=false;
                            seSeleccionoHerencia=false;
                            
                            objetoNumero=i;
                        }
                        if(sePuedeEditarCar){
                            botonCrear.setVisible(true);//MODIFICAR NOMBRE DE CREAR--MODIFICAR (textoBotonCrear)
                            textoBotonCrear.setVisible(true);
                            textoBotonCrear.setText("Editar");
                            nombre.setText("Cardinalidad");
                            nombre.setVisible(true);
                            seSeleccionoEntidad=true;
                            seSeleccionoRelacion=false;
                            seSeleccionoAtributo=false;
                            seSeleccionoHerencia=false;
                            objetoNumero=i;
                        }
                        if(sePuedeDibujarDoble){
                            if(entidades.get(i) instanceof Entidad){
                                for (int j = 0; j < entidades.get(i).relaciones.size(); j++) {
                                    Union union=new Union(entidades.get(i).relaciones.get(j), entidades.get(i), null,null,puntoCar);
                                    union.puntoCar=puntoCar;
                                    union.doble=true;
                                    union.editDoble=true;
                                    Line lineaa =union.getLinea();
                                    uniones.add(union);
                                    entidades.get(i).uniones.add(union);
                                    entidades.get(i).relaciones.get(j).uniones.add(union);
                                    modificaciones.add(union);
                                    lineaa.setStroke(Color.BLACK); //color de la linea que une
                                    lineaa.setStrokeWidth(1);
                                    lineaa.setStrokeLineCap(StrokeLineCap.ROUND);
                                    pane.getChildren().add(lineaa);
                                    entidades.get(i).getLineas().add(lineaa);
                                    //relacion2.getLineas().add(lineaa);
                                }
                                entidades.get(i).rectangulo.seleccionado=false;
                            }
                            sePuedeSeleccionar=false;
                            sePuedeDibujarDoble=false;
                            
                        }
                        if(sePuedeDibujarSimple){
                            botonCrear.setVisible(true);//MODIFICAR NOMBRE DE CREAR--MODIFICAR (textoBotonCrear)
                            textoBotonCrear.setVisible(true);
                            textoBotonCrear.setText("Simple");
                            nombre.setText("Simple");
                            nombre.setVisible(true);
                            seSeleccionoEntidad=true;
                            seSeleccionoRelacion=false;
                            seSeleccionoAtributo=false;
                            seSeleccionoHerencia=false;
                            objetoNumero=i;
                            
                        }
                        seSeleccionoEntidad=true;
                        entidadesSeleccionadas.add(entidades.get(i));
                    }
                    
                }
                punto = MouseInfo.getPointerInfo().getLocation();
                punto.x-=300;
                punto.y-=20;
                for (int i = 0; i < relaciones.size(); i++) {
                    //punto.x
                    if(relaciones.get(i).poligono.seleccionar(punto)){
                        relaciones.get(i).poligono.Dibujar2();
                        if (sePuedeCrearAtributo){
                            atributoEntidad=false;
                            atributoRelacion=true;
                            for (int j = 0; j < relacionesSeleccionadas.size(); j++) {
                                relacionesSeleccionadas.get(j).poligono.repintarNegro();
                            }
                            relacionesSeleccionadas.clear();
                            for (int p = 0; p < entidadesSeleccionadas.size(); p++) {
                                entidadesSeleccionadas.get(p).rectangulo.Borrar();
                                entidadesSeleccionadas.get(p).rectangulo.Dibujar();
                                entidadesSeleccionadas.get(p).rectangulo.seleccionado=false;
                            }
                            entidadesSeleccionadas.clear();
                        }
                        if (sePuedeEditar){
                            insertarTexto1.setVisible(true);
                            botonCrear.setVisible(true);//MODIFICAR NOMBRE DE CREAR--MODIFICAR
                            textoBotonCrear.setVisible(true);
                            textoBotonCrear.setText("Editar");
                            seSeleccionoRelacion=true;
                            seSeleccionoEntidad=false;
                            seSeleccionoAtributo=false;
                            seSeleccionoHerencia=false;
                            objetoNumero=i;
                        }
                        relacionNumero=i;
                        relacionesSeleccionadas.add(relaciones.get(i));
                        seSeleccionoRelacion=true;
                    }
                }
                for (int i = 0; i < atributos.size(); i++){
                    punto = MouseInfo.getPointerInfo().getLocation();
                    punto.x-=300;
                    punto.y-=20;
                    if(atributos.get(i).poligono.seleccionar(punto)){
                        if (sePuedeEditar){
                            for (int j = 0; j < atributos.size(); j++) {
                                atributos.get(j).poligono.repintarNegro();
                            }
                            atributos.get(i).poligono.Dibujar2();
                            atributos.get(i).nombresEditados.add(atributos.get(i).texto.getText());
                            insertarTexto1.setVisible(true);
                            botonCrear.setVisible(true);//MODIFICAR NOMBRE DE CREAR--MODIFICAR
                            textoBotonCrear.setVisible(true);
                            textoBotonCrear.setText("Editar");
                            seSeleccionoRelacion=false;
                            seSeleccionoEntidad=false;
                            seSeleccionoAtributo=true;
                            seSeleccionoHerencia=false;
                            objetoNumero=i;
                        }
                    }
                }
                for (int i = 0; i < herencias.size(); i++) {
                    herencias.get(i).circulo.repintarNegro();
                    if(herencias.get(i).circulo.seleccionar(punto)){
                        herencias.get(i).circulo.Dibujar2();
                        objetoNumero=i;
                        seSeleccionoRelacion=false;
                        seSeleccionoEntidad=false;
                        seSeleccionoAtributo=false;
                        seSeleccionoHerencia=true;
                        botonCrear.setVisible(true);//MODIFICAR NOMBRE DE CREAR--MODIFICAR
                        textoBotonCrear.setVisible(true);
                        textoBotonCrear.setText("Editar");
                    }
                }
                //seleccionamos la agregacion
                System.out.println("AAA "+sePuedeCrearRelacion);
                if((!seSeleccionoRelacion && !seSeleccionoEntidad && !seSeleccionoHerencia) || (sePuedeCrearRelacion && !seSeleccionoEntidad)){
                    punto = MouseInfo.getPointerInfo().getLocation();
                    punto.x=punto.x-300;
                    punto.y=punto.y-25;
                    for (int i = 0; i < agregaciones.size(); i++) {
                        if(interseccionTransportarAgregacion(agregaciones.get(i).rectanguloAgregacion.punto1,
                            agregaciones.get(i).rectanguloAgregacion.punto3,punto)){
                            System.out.println("TE PILLE MALDITA");
                            agregaciones.get(i).rectanguloAgregacion.PintarColorCrimson();
                            System.out.println("Pinteee");
                            
                            if(sePuedeEditar){
                                for (int j = 0; j < agregaciones.size(); j++) {
                                    agregaciones.get(j).rectanguloAgregacion.repintarNegro();
                                }
                                agregaciones.get(i).rectanguloAgregacion.PintarColorCrimson();
                                relacionesSeleccionadas.clear();
                                seSeleccionoRelacion=false;
                                seSeleccionoEntidad=false;
                                seSeleccionoAtributo=false;
                                seSeleccionoHerencia=false;
                                seSeleccionoAgregacion=true;
                                objetoNumero=i;
                                agregacionNumero=i;
                                insertarTexto1.setVisible(true);
                                botonCrear.setVisible(true);//MODIFICAR NOMBRE DE CREAR--MODIFICAR
                                textoBotonCrear.setVisible(true);
                                textoBotonCrear.setText("Editar");
                            }
                            if(sePuedeDibujarDoble){
                                System.out.println("ENTREEEEEEEEEEEEEEEEEEEEEE");
                                for (int j = 0; j < agregaciones.get(i).relaciones.size(); j++) {
                                    System.out.println("ENTRE AL FOR ");
                                    Union union=new Union(agregaciones.get(i).relaciones.get(j), null, null,agregaciones.get(i),puntoCar);
                                    union.dobleAgregacion=true;
                                    union.puntoCar=puntoCar;
                                    union.doble=true;
                                    Line lineaa =union.getLinea();
                                    uniones.add(union);
                                    union.editDoble=true;
                                    modificaciones.add(union);
                                    lineaa.setStroke(Color.BLACK); //color de la linea que une
                                    lineaa.setStrokeWidth(1);
                                    lineaa.setStrokeLineCap(StrokeLineCap.ROUND);
                                    pane.getChildren().add(lineaa);
                                    agregaciones.get(i).getLineas().add(lineaa);
                                    //pintamos el rectangulo
                                    agregaciones.get(i).rectanguloAgregacion.PintarColorCrimson();
                                    //relacion2.getLineas().add(lineaa);
                                }
                                entidades.get(i).rectangulo.seleccionado=false;
                                sePuedeDibujarDoble=false;
                                sePuedeSeleccionar=false;
                            }
                            if(sePuedeDibujarSimple){
                                botonCrear.setVisible(true);//MODIFICAR NOMBRE DE CREAR--MODIFICAR (textoBotonCrear)
                                textoBotonCrear.setVisible(true);
                                textoBotonCrear.setText("Simple");
                                nombre.setText("Simple");
                                nombre.setVisible(true);
                                seSeleccionoAgregacion=true;
                                seSeleccionoRelacion=false;
                                seSeleccionoAtributo=false;
                                seSeleccionoHerencia=false;
                                objetoNumero=i;
                                sePuedeDibujarDoble=false;
                                agregacionNumero=i;
                            }
                            if(sePuedeEditarCar){
                                botonCrear.setVisible(true);//MODIFICAR NOMBRE DE CREAR--MODIFICAR (textoBotonCrear)
                                textoBotonCrear.setVisible(true);
                                textoBotonCrear.setText("Editar");
                                nombre.setText("Cardinalidad");
                                nombre.setVisible(true);
                                seSeleccionoAgregacion=true;
                                seSeleccionoRelacion=false;
                                seSeleccionoAtributo=false;
                                seSeleccionoHerencia=false;
                                objetoNumero=i;
                                agregacionNumero=i;
                            }
                            
                            seSeleccionoAgregacion=true;
                            //pintamos el rectangulo
                            entidadesSeleccionadas.add(agregaciones.get(i));
                            break;
                        }
                    }
                }
            
        }
        else if (sePuedeSeleccionarBorrar){
                borradas.clear();
                Point punto = MouseInfo.getPointerInfo().getLocation();
                punto.x-=300;
                punto.y-=25;
                int a=0;
                for (int i = 0; i < relaciones.size(); i++) {
                    //entidades.get(i).rectangulo.imprimirPuntos();
                    if (relaciones.get(i).poligono.seleccionar(punto)){
                        modificaciones.add(relaciones.get(i));
                        uniones.removeAll(relaciones.get(i).uniones);
                        for (int j = 0; j < relaciones.get(i).uniones.size(); j++) {
                            pane.getChildren().remove(relaciones.get(i).uniones.get(j).linea);
                            pane.getChildren().remove(relaciones.get(i).uniones.get(j).car);
                        }
                        a=1;
                        borrarRelacion(relaciones.get(i));
                    }
                }
                for (int i = 0; i < atributos.size(); i++) {
                    //entidades.get(i).rectangulo.imprimirPuntos();
                    if (atributos.get(i).poligono.seleccionar(punto)){
                        modificaciones.add(atributos.get(i));
                        borrarAtributo(atributos.get(i));
                        a=1;
                    }
                }
                for (int i = 0; i < entidades.size(); i++) {
                    //entidades.get(i).rectangulo.imprimirPuntos();
                    if (interseccionRectangulo(entidades.get(i), punto)){
                        modificaciones.add(entidades.get(i));
                        borrarEntidad(entidades.get(i));
                        a=1;
                    }
                }
                if(a==0){
                    for (int i = 0; i < agregaciones.size(); i++) {
                        if(interseccionTransportarAgregacion(agregaciones.get(i).rectanguloAgregacion.punto1,
                        agregaciones.get(i).rectanguloAgregacion.punto3,punto)){
                        borrarAgregacion(agregaciones.get(i));
                        sePuedeSeleccionarBorrar=false;
                        break;
                        }
                    }
                }
                for (int i = 0; i < borradas.size(); i++) {
                    uniones.remove(borradas.get(i));
                    pane.getChildren().remove(borradas.get(i).linea);
                    pane.getChildren().remove(borradas.get(i).car);
                }
                UnionesBorradas.add(borradas);
        }
        insertarTexto1.setText("");
        sePuedeDibujar=false;
    }
    
    @FXML
    public void crearEntidad(){
        movido=null;
        sePuedeSeleccionarBorrar=false;
        nombre.setVisible(true);
        sePuedeCrearEntidad=true;
        sePuedeCrearRelacion=false;
        nombre.setText("Nombre entidad: ");
        insertarTexto1.setVisible(true);
        botonCrear.setVisible(true);
        textoBotonCrear.setVisible(true);
        sePuedeSeleccionar=false;
        nombreEntidad.setText("");
        
        if(entidadesSeleccionadas.size()>0){
            for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
                entidadesSeleccionadas.get(i).rectangulo.Borrar();
                entidadesSeleccionadas.get(i).rectangulo.Dibujar();
                entidadesSeleccionadas.get(i).rectangulo.seleccionado=false;
            }
        }
    }
    
    @FXML
    public void crearRelacion(){
        movido=null;
        entidadesSeleccionadas.clear();
        for (int j = 0; j < relacionesSeleccionadas.size(); j++) {
            relacionesSeleccionadas.get(j).poligono.repintarNegro();
        }
        relacionesSeleccionadas.clear();
        for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
            entidadesSeleccionadas.get(i).rectangulo.Borrar();
            entidadesSeleccionadas.get(i).rectangulo.Dibujar();
            entidadesSeleccionadas.get(i).rectangulo.seleccionado=false;
        }
        
        entidadesSeleccionadas=new ArrayList();
        sePuedeSeleccionarBorrar=false;
        //sePuedeDibujar=false;
        sePuedeSeleccionar=true;
        nombre.setVisible(true);
        sePuedeCrearRelacion=true;
        sePuedeCrearEntidad=false;
        nombre.setText("Nombre relación: ");
        insertarTexto1.setVisible(true);
        botonCrear.setVisible(true);
        textoBotonCrear.setVisible(true);
    }
    
    @FXML
    public void crearAtributo(){
        movido=null;
        sePuedeSeleccionarBorrar=false;
        entidadesSeleccionadas.clear();
        for (int j = 0; j < relacionesSeleccionadas.size(); j++) {
            relacionesSeleccionadas.get(j).poligono.repintarNegro();
        }
        relacionesSeleccionadas.clear();
        for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
            entidadesSeleccionadas.get(i).rectangulo.Borrar();
            entidadesSeleccionadas.get(i).rectangulo.Dibujar();
            entidadesSeleccionadas.get(i).rectangulo.seleccionado=false;
        }
        posX= MouseInfo.getPointerInfo().getLocation().x;
        posY= MouseInfo.getPointerInfo().getLocation().y;
        entidadesSeleccionadas=new ArrayList();
        sePuedeDibujar=false;
        sePuedeSeleccionar=true;
        nombre.setVisible(true);
        sePuedeCrearRelacion=false;
        sePuedeCrearEntidad=false;
        sePuedeCrearAgregacion=false;
        sePuedeCrearAtributo=true;
        nombre.setText("Nombre Atributo: ");
        insertarTexto1.setVisible(true);
        botonCrear.setVisible(true);
        textoBotonCrear.setVisible(true);
    }
    
    @FXML
    public void CrearHerecia(){
        movido=null;
        crearHerencia=true;
        terminar.setVisible(true);
        imagenTerminar.setVisible(true);
        herencia.setVisible(false);
        sePuedeSeleccionarBorrar=false;
        
    }
    
    @FXML
    public void CrearAgregacion(){
        movido=null;
        for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
            if(entidadesSeleccionadas.get(i) instanceof Agregacion){
                Agregacion agregacion= (Agregacion)entidadesSeleccionadas.get(i);
                agregacion.rectanguloAgregacion.repintarNegro();

            }
            else{
                entidadesSeleccionadas.get(i).rectangulo.Borrar();
            entidadesSeleccionadas.get(i).rectangulo.Dibujar();
            entidadesSeleccionadas.get(i).rectangulo.seleccionado=false;
            }
        }
        entidadesSeleccionadas.clear();
        for (int j = 0; j < relacionesSeleccionadas.size(); j++) {
            relacionesSeleccionadas.get(j).poligono.repintarNegro();
        }
        relacionesSeleccionadas.clear();
        sePuedeSeleccionarBorrar=false;
        sePuedeCrearAgregacion=true;
        sePuedeCrearRelacion=false;
        sePuedeCrearEntidad=false;
        sePuedeCrearAtributo=false;
        sePuedeSeleccionar=true;
        botonCrear.setVisible(true);
        entidadesSeleccionadas.clear();
        textoBotonCrear.setText("Crear");
        nombre.setText("Nombre agregación: ");
        insertarTexto1.setText("");
        insertarTexto1.setVisible(true);
        textoBotonCrear.setVisible(true);
        sePuedeDibujar=false;
        
    }
    @FXML
    public void terminar(){
        movido=null;
        if(seSeleccionoHerencia){
            for (int i = 0; i < entidadesHeredadas.size(); i++) {
                herencias.get(herencias.size()-1).agregarHerencia(entidadesHeredadas.get( i));
                herencias.get(herencias.size()-1).entidad.entidadesHeredadas.add(entidadesHeredadas.get( i));
            }
            herencias.get(herencias.size()-1).nombre=nombreHerencia;
            entidadesHeredadas.clear();
            elegirEntidadesHeredadas=false;
            nombre.setVisible(false);
            sePuedeCrearEntidad=false;
            sePuedeCrearRelacion=true;
            insertarTexto1.setVisible(false);
            botonCrear.setVisible(false);
            textoBotonCrear.setVisible(false);
            sePuedeSeleccionar=false;
            nombreEntidad.setText("");
            terminar.setVisible(false);
            imagenTerminar.setVisible(false);
            herencia.setVisible(true);
            seSeleccionoHerencia=false;
        }
        else if(seSeleccionoCompuesto){
            seSeleccionoCompuesto=false;
            terminar.setVisible(false);
            imagenTerminar.setVisible(false);
            herencia.setVisible(true);
        }
    }
    
    public boolean interseccionRectangulo(ArrayList<Point> puntos,int entidadNum){
        if ((entidades.get(entidadNum).rectangulo.getPuntos().get(0).x<=posX
                &&posX<=entidades.get(entidadNum).rectangulo.getPuntos().get(1).x
                &&entidades.get(entidadNum).rectangulo.getPuntos().get(1).y<=posY
                &&posY<=entidades.get(entidadNum).rectangulo.getPuntos().get(2).y)&&!entidades.get(entidadNum).rectangulo.seleccionado ){
                entidades.get(entidadNum).rectangulo.Dibujar2();
                entidades.get(entidadNum).rectangulo.seleccionado=true;
                return true;
        }
        return false;
    }
    
    @FXML
    public void crear(){
        redo.clear();
        if(sePuedeEditar){
            boolean textoCorto=false;
            if(insertarTexto1.getText().length()==0){
                textoCorto=true;
            }
            if(seSeleccionoEntidad){
                for (int p = 0; p < entidadesSeleccionadas.size(); p++) {
                    entidadesSeleccionadas.get(p).rectangulo.Borrar();
                    entidadesSeleccionadas.get(p).rectangulo.Dibujar();
                    entidadesSeleccionadas.get(p).rectangulo.seleccionado=false;
                }
                entidades.get(objetoNumero).nombresEditados.add(entidades.get(objetoNumero).nombre.getText());
                modificaciones.add(entidades.get(objetoNumero));
                if(textoCorto){
                    entidades.get(objetoNumero).nombre.setText("Entidad "+(entidades.size()+1));
                }
                else{
                    entidades.get(objetoNumero).nombre.setText(insertarTexto1.getText());
                }
                entidadesSeleccionadas.clear();
                seSeleccionoEntidad=false;
            }
            else if (seSeleccionoRelacion){
                for (int j = 0; j < relacionesSeleccionadas.size(); j++) {
                    relacionesSeleccionadas.get(j).poligono.repintarNegro();
                }
                relaciones.get(objetoNumero).nombresEditados.add(relaciones.get(objetoNumero).nombre.getText());
                modificaciones.add(relaciones.get(objetoNumero));
                if(textoCorto){
                    relaciones.get(objetoNumero).nombre.setText("Relación "+(relaciones.size()+1));
                }
                else{
                    relaciones.get(objetoNumero).nombre.setText(insertarTexto1.getText());
                }
                relacionesSeleccionadas.clear();
                seSeleccionoRelacion=false;
            }
            else if (seSeleccionoAtributo){
                for (int i = 0; i < atributos.size(); i++) {
                    atributos.get(i).poligono.repintarNegro();
                }
                atributos.get(objetoNumero).nombresEditados.add(atributos.get(objetoNumero).texto.getText());
                modificaciones.add(atributos.get(objetoNumero));
                if(textoCorto){
                    atributos.get(objetoNumero).texto.setText("Atributo "+(atributos.size())+1);
                }
                else{
                    atributos.get(objetoNumero).texto.setText(insertarTexto1.getText());
                }
                seSeleccionoAtributo=false;
            }
            else if(seSeleccionoAgregacion){
                //cambiamos texto
                for (int i = 0; i < agregaciones.size(); i++) {
                    agregaciones.get(i).rectanguloAgregacion.repintarNegro();
                }
                relacionesSeleccionadas.clear();
                agregaciones.get(objetoNumero).nombresEditados.add(agregaciones.get(objetoNumero).nombre.getText());
                modificaciones.add(agregaciones.get(objetoNumero));
                if(textoCorto){
                    agregaciones.get(objetoNumero).nombre.setText("Agregación "+(agregaciones.size()+1));
                }
                else{
                    agregaciones.get(objetoNumero).nombre.setText(insertarTexto1.getText());
                }
                seSeleccionoAgregacion=false;
            }
            else if(seSeleccionoHerencia){
                for (int i = 0; i < herencias.size(); i++) {
                    herencias.get(i).circulo.repintarNegro();
                }
                if(herencias.get(objetoNumero).nombre.getText().equals("D")){
                    herencias.get(objetoNumero).nombre.setText("S");
                }
                else{
                    herencias.get(objetoNumero).nombre.setText("D");
                    seSeleccionoHerencia=false;
                }
            }
            sePuedeEditar=false;
            sePuedeSeleccionar=false;
            sePuedeCrearRelacion=false;
            sePuedeCrearAgregacion=false;
            sePuedeCrearEntidad=false;
            textoBotonCrear.setText("Crear");
            insertarTexto1.setText("");
            insertarTexto1.setVisible(false);
            textoBotonCrear.setVisible(false);
            botonCrear.setVisible(false);
        }
        if(sePuedeEditarCar){
            if(seSeleccionoEntidad){
                  
                for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
                    if(entidadesSeleccionadas.get(i) instanceof Agregacion){
                            Agregacion agregacion= (Agregacion)entidadesSeleccionadas.get(i);
                            agregacion.rectanguloAgregacion.repintarNegro();
                        
                        }
                        else{
                            entidadesSeleccionadas.get(i).rectangulo.Borrar();
                        entidadesSeleccionadas.get(i).rectangulo.Dibujar();
                        entidadesSeleccionadas.get(i).rectangulo.seleccionado=false;
                        }
                }
                for (int i = 0; i < uniones.size(); i++) {
                    //FALTA UN IIIIF
                    if(uniones.get(i).relacion!=null&&uniones.get(i).entidad!=null)
                    if(uniones.get(i).entidad.equals(entidades.get(objetoNumero))&&uniones.get(i).relacion.equals(relaciones.get(relacionNumero))){
                        modificaciones.add(uniones.get(i));
                        if(uniones.get(i).car.getText().equals("N")){
                          uniones.get(i).car.setText("1");  
                        }
                        else if(uniones.get(i).car.getText().equals("1")) {
                            uniones.get(i).car.setText("N");
                        }
                        break;
                    }
                }
                
                for (int p = 0; p < relaciones.size(); p++) {
                    relaciones.get(p).poligono.repintarNegro();
                }
                seSeleccionoEntidad=false;
            }
            //AGREGACION
            else if(seSeleccionoAgregacion){
                //cambiamos texto
                for (int i = 0; i < agregaciones.size(); i++) {
                    agregaciones.get(i).rectanguloAgregacion.repintarNegro();
                }
                relacionesSeleccionadas.clear();
                //agregaciones.get(objetoNumero).nombresEditados.add(agregaciones.get(objetoNumero).nombre);
                for (int i = 0; i < uniones.size(); i++) { //SE CAEEE EN ESTA WEAAAAAAAAAAAAAAAA
                    if(uniones.get(i).agregacion!=null)
                    if(uniones.get(i).agregacion.equals(agregaciones.get(agregacionNumero))&&uniones.get(i).relacion.equals(relaciones.get(relacionNumero))){
                        if(uniones.get(i).car.getText().equals("N")){
                          uniones.get(i).car.setText("1");  
                        }
                        else if(uniones.get(i).car.getText().equals("1")) {
                            uniones.get(i).car.setText("N");
                        }
                        break;
                    }
                }
                seSeleccionoAgregacion=false;
            }
            sePuedeEditarCar=false;
            sePuedeSeleccionar=false;
            textoBotonCrear.setText("Crear");
            insertarTexto1.setText("");
            insertarTexto1.setVisible(false);
            textoBotonCrear.setVisible(false);
            botonCrear.setVisible(false);
            nombre.setVisible(false);
        }
        if(sePuedeDibujarSimple){ 
            if(seSeleccionoAgregacion){
                for (int i = 0; i < agregaciones.size(); i++) {
                    agregaciones.get(i).rectanguloAgregacion.repintarNegro();
                }
                for (int i = 0; i < uniones.size(); i++) {
                    if(uniones.get(i).agregacion!=null)
                    if(uniones.get(i).agregacion.equals(agregaciones.get(agregacionNumero))&&uniones.get(i).relacion.equals(relaciones.get(relacionNumero))){
                        if(uniones.get(i).doble){
                            pane.getChildren().remove(uniones.get(i).linea);
                            uniones.remove(i);  //HAY QUE BORRAR LA LINEA CULIA
                        }
                    }
                }
                for (int p = 0; p < relaciones.size(); p++) {
                    relaciones.get(p).poligono.repintarNegro();
                }
                seSeleccionoAgregacion=false;
            }
            else if(seSeleccionoEntidad){
                //repintar entidaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaad
                for (int i = 0; i < uniones.size(); i++) {
                    if(uniones.get(i).entidad!=null&&uniones.get(i).relacion!=null)
                    if(uniones.get(i).entidad.equals(entidades.get(objetoNumero))&&uniones.get(i).relacion.equals(relaciones.get(relacionNumero))){
                        System.out.println(uniones.get(i).doble);
                        if(uniones.get(i).doble){
                            pane.getChildren().remove(uniones.get(i).linea);
                            uniones.get(i).entidad.rectangulo.seleccionado=false;
                            uniones.remove(i);  //HAY QUE BORRAR LA LINEA CULIA
                        }
                        
                    }
                }
                for (int p = 0; p < relaciones.size(); p++) {
                    relaciones.get(p).poligono.repintarNegro();
                }
                seSeleccionoEntidad=false;
            }
            sePuedeEditarCar=false;
            sePuedeSeleccionar=false;
            textoBotonCrear.setText("Crear");
            insertarTexto1.setText("");
            insertarTexto1.setVisible(false);
            textoBotonCrear.setVisible(false);
            botonCrear.setVisible(false);
            nombre.setVisible(false);
            sePuedeDibujarSimple=false;
        }
        else{
            if(sePuedeCrearAgregacion){
                textito = new Text();
                textito.setFill(Color.BLACK);
                textito.setStyle(texto.getStyle());
                textito.setFont(texto.getFont());
                textito.setText(insertarTexto1.getText());
                if(textito.getText().length()==0){
                    if(entidades.size()>=0){
                        textito.setText("Agregacion "+(agregaciones.size()+1));
                    }
                }
                textito.setLayoutX(relacionesSeleccionadas.get(0).poligono.punto.x-490);
                textito.setLayoutY(relacionesSeleccionadas.get(0).poligono.punto.y-75);            
                textito.setVisible(true);
                pane.getChildren().add(textito);
                Agregacion agregacion = new Agregacion(relacionesSeleccionadas.get(0),relacionesSeleccionadas.get(0).poligono.punto,null,textito,null,pane);
                //Relacion relacion, Point puntoCentral, Line linea, Text nombre, Rectangulo rectangulo
                agregaciones.add(agregacion);
                modificaciones.add(agregacion);
                agregaciones.get(agregaciones.size()-1).rectanguloAgregacion.Dibujar(pane);
                actualizarUniones();
                sePuedeCrearAgregacion=false;
                for (int j = 0; j < relacionesSeleccionadas.size(); j++) {
                    relacionesSeleccionadas.get(j).poligono.repintarNegro();
                }
                relacionesSeleccionadas.clear();
                for (int i = 0; i < agregaciones.size(); i++) {
                    agregaciones.get(i).rectanguloAgregacion.repintarNegro();
                }
            }
            sePuedeDibujar=true;
            nombre.setVisible(false);
            insertarTexto1.setVisible(false);
            botonCrear.setVisible(false);
            textoBotonCrear.setVisible(false);
            sePuedeSeleccionar=false;
        }
        sePuedeEditar=false;
        sePuedeSeleccionar=false;
        seSeleccionoAgregacion=false;
        seSeleccionoEntidad=false;
        seSeleccionoRelacion=false;
        seSeleccionoAtributo=false;
        actualizarAgregaciones();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crearContextMenuAtributos();
        crearContextMenuEntidades();
        texto.setVisible(false);
        nombre.setVisible(false);
        botonCrear.setVisible(false);
        textoBotonCrear.setVisible(false);
        insertarTexto1.setVisible(false);
        circulo.setVisible(false);
        terminar.setVisible(false);
        imagenTerminar.setVisible(false);
        crearContextMenuHerencia();
    }     
    
    public boolean interseccionRectangulo(Entidad entidad,Point punto){
        if ((entidad.rectangulo.getPuntos().get(0).x<=punto.x
                &&punto.x<=entidad.rectangulo.getPuntos().get(1).x
                &&entidad.rectangulo.getPuntos().get(1).y<=punto.y
                &&punto.y<=entidad.rectangulo.getPuntos().get(2).y)&&!entidad.rectangulo.seleccionado ){
                return true;
        }
        return false;
    }
    
    public boolean interseccionTransportar(ArrayList<Point> puntos,int entidadNum){
        if (entidades.get(entidadNum).rectangulo.getPuntos().get(0).x<=(posX-20)
                &&(posX-20)<=entidades.get(entidadNum).rectangulo.getPuntos().get(1).x
                &&entidades.get(entidadNum).rectangulo.getPuntos().get(1).y<=(posY-20)
                &&(posY-20)<=entidades.get(entidadNum).rectangulo.getPuntos().get(2).y){
                Rectangulo rectangulo = entidades.get(entidadNum).rectangulo;
                rectangulo.Dibujar2();
                return true;
        }
        return false;
    }
    public boolean interseccionTransportarAgregacion(Point punto1,Point punto3,Point puntoCentro){
        if ( (punto1.x<=puntoCentro.x) && (puntoCentro.x<=punto3.x) && 
             (punto1.y<=puntoCentro.y) &&(puntoCentro.y<=punto3.y)){
            return true;
        }
        return false;
    }
    
    @FXML
    public void puntosDeControl(){
        contadorPuntos++;
        if(contadorPuntos%2!=0){
            for (int i = 0; i < entidades.size(); i++) {
                for (int j = 0; j < entidades.get(i).rectangulo.puntos.size(); j++) {
                    Circle circulo = new Circle();
                    circulo.setStroke(Color.TOMATO);
                    circulo.setStrokeWidth(6);
                    circulo.setStrokeLineCap(StrokeLineCap.ROUND);
                    circulo.setCenterX(entidades.get(i).rectangulo.puntos.get(j).x);
                    circulo.setCenterY(entidades.get(i).rectangulo.puntos.get(j).y);            
                    circulos.add(circulo);
                    pane.getChildren().add(circulo);

                }
            }
           
            for (int i = 0; i < atributos.size(); i++) {
                for (int j = 0; j < atributos.get(i).poligono.puntos.size(); j++) {
                   
                    Circle circulo = new Circle();
                    circulo.setStroke(Color.MAGENTA);
                    circulo.setStrokeWidth(6);
                    circulo.setStrokeLineCap(StrokeLineCap.ROUND);
                    circulo.setCenterX(atributos.get(i).poligono.puntos.get(j).x);
                    circulo.setCenterY(atributos.get(i).poligono.puntos.get(j).y);            
                    circulos.add(circulo);
                    pane.getChildren().add(circulo);
                }
            }
            if(relaciones.size()>0){
                for (int i = 0; i < relaciones.size(); i++) {
                    for (int j = 0; j < relaciones.get(i).poligono.puntos.size(); j++) {
                        Circle circulo = new Circle();
                        circulo.setStroke(Color.MAGENTA);
                        circulo.setStrokeWidth(6);
                        circulo.setStrokeLineCap(StrokeLineCap.ROUND);
                        circulo.setCenterX(relaciones.get(i).poligono.puntos.get(j).x);
                        circulo.setCenterY(relaciones.get(i).poligono.puntos.get(j).y);            
                        circulos.add(circulo);
                        pane.getChildren().add(circulo);
                    }

                }

            }
            for (int i = 0; i < agregaciones.size(); i++) {
                for (int j = 0; j < agregaciones.get(i).puntos.size(); j++) {
                    Circle circulo = new Circle();
                    circulo.setStroke(Color.TOMATO);
                    circulo.setStrokeWidth(6);
                    circulo.setStrokeLineCap(StrokeLineCap.ROUND);
                    circulo.setCenterX(agregaciones.get(i).puntos.get(j).x);
                    circulo.setCenterY(agregaciones.get(i).puntos.get(j).y);            
                    circulos.add(circulo);
                    pane.getChildren().add(circulo);

                }
            }
        }
        else{
            for (int i = 0; i < circulos.size(); i++) {
                pane.getChildren().remove(circulos.get(i));
        }
        
        }
    }
    
    @FXML
    public void limpiar(){
        pane.getChildren().clear();
        relaciones.clear();
        entidades.clear();
        entidadesSeleccionadas.clear();
        relacionesSeleccionadas.clear();
        agregaciones.clear();
        //Poligono poligono=new Poligono(pane);
        //poligono.puntosBolean.clear();
        herencias.clear();
        uniones.clear();
        atributos.clear();
        
    }
    
    @FXML
    public void guardar() throws AWTException, IOException, DocumentException{
        BufferedImage imagen=Imagen.capturarPantalla();
        String extension="JPG";
        imagen=imagen.getSubimage(300,20,1065, 700);
         FileChooser fileChooser = new FileChooser();
                 
        //Ingreso de filtro de extensión
        FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("png", ".png");
        fileChooser.getExtensionFilters().add(extFilter);
        
        FileChooser.ExtensionFilter extFilter2 =
                new FileChooser.ExtensionFilter("pdf", ".pdf");
        fileChooser.getExtensionFilters().add(extFilter2);

        //Mostrar diálogo de guardar archivo
        final Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        String nombre2 = file.toString();
        System.out.println(nombre2 +" 1");
        nombre2=(nombre2.replace("\\", "/"));
        System.out.println(nombre2 +" 2");
        URL url = file.toURL();
        if(file != null){
            try {
                //png
                ImageIO.write(imagen, "png", file);
                
                if(fileChooser.getSelectedExtensionFilter()==extFilter2){
                    //pdf
                    Document documento = new Document();
                    try {
                        String nombre3=nombre2.substring(0, nombre2.length()-4);
                        String nombre4=nombre2.substring(nombre2.length()-4, nombre2.length());
                        String nombreFinal=nombre3+" "+nombre4;
                        PdfWriter.getInstance(documento, new FileOutputStream(nombreFinal));
                        
                        documento.open();
                        Image imagen2 = Image.getInstance(url);
                        imagen2.scaleAbsoluteWidth(500f);
                        imagen2.scaleAbsoluteHeight(300f);
                        documento.add(imagen2);
                        documento.close();
                        //borrar imagen2

                    } catch (DocumentException ex) {
                     // Atrapamos excepciones concernientes al documentoo.
                    } catch (java.io.IOException ex) {
                     // Atrapamos excepciones concernientes al I/O.
                    }
                }
            } catch (IOException ex) {
            }
        }
    
          
    }
    
    public void crearContextMenuAtributos(){
        MenuItem item1= new MenuItem("Genéricos");
        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("PUNTEADa");
                item1AccionAtributos();
            }
        });

        MenuItem item2= new MenuItem("Clave");
        item2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Clave");
                item2AccionAtributos();
            }
        });

        MenuItem item3= new MenuItem("Clave Parcial");
        item3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Clave parcial");
                item3AccionAtributos();
            }
        });

        MenuItem item4= new MenuItem("multiValuados");
        item4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("MultiValuados");
                item4AccionAtributos();
            }
        });

        MenuItem item5= new MenuItem("Compuesto");
        item5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Compuesto");
                item5AccionAtributos();
            }
        });

        MenuItem item6= new MenuItem("Derivado");
        item6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Derivado");
                item6AccionAtributos();
            }
        });
        
        contextMenuAtributos.getItems().addAll(item1,item2,item3,item4,item5,item6);
    }
    
    public void crearContextMenuEntidades(){
        MenuItem item1= new MenuItem("Fuerte");
        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Debil");
                item1AccionEntidades();
            }
        });

        MenuItem item2= new MenuItem("Débil");
        item2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Debil");
                item2AccionEntidades();
            }
        });
        contextMenuEntidades.getItems().addAll(item1,item2);
    }
    public void crearContextMenuHerencia(){
        MenuItem item1= new MenuItem("Disyuncion");
        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                nombreHerencia=new Text(puntoDisSol.x, puntoDisSol.y,"D");
                pane.getChildren().add(nombreHerencia);
                herencias.get(herencias.size()-1).nombre=nombreHerencia;
            }
        });

        MenuItem item2= new MenuItem("Solapamiento");
        item2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //S
                 nombreHerencia=new Text(puntoDisSol.x, puntoDisSol.y,"S");
                 pane.getChildren().add(nombreHerencia);
                 herencias.get(herencias.size()-1).nombre=nombreHerencia;
            }
        });
        contextMenuHerencia.getItems().addAll(item1,item2);
    }
    
    //generico
    public void item1AccionAtributos(){
        //donde está el texto
        textito.setVisible(true);
        //donde está la entidad para relacionarla
        
        Atributo atributo=new Atributo(pane, punto, textito, TipoAtributo.generico, 20);
        atributo.dibujar();
        atributos.add(atributo);
        contadorPuntos--;
        puntosDeControl();
        modificaciones.add(atributo);
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo,null,puntoCar);
            atributo.entidad=entidadesSeleccionadas.get(0);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            pane.getChildren().add(linea);
            uniones.add(union);
            entidadesSeleccionadas.get(0).atributos.add(atributo);
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo,null,puntoCar);
            atributo.relacion=relacionesSeleccionadas.get(0);
            atributo.relacion=relacionesSeleccionadas.get(0);
            Line linea=union.CrearRelacionPoligono(relacionesSeleccionadas.get(0).poligono,atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            pane.getChildren().add(linea);
            relacionesSeleccionadas.get(0).atributos.add(atributo);
        }
        atributoEntidad=false;
        atributoRelacion=false;
    } 
    
    //clave
    public void item2AccionAtributos(){
        textito.setVisible(true);
        //donde está la entidad para relacionarla
        Atributo atributo=new Atributo(pane, punto, textito, TipoAtributo.clave, 20);
        atributo.dibujar();
        atributos.add(atributo);
        contadorPuntos--;
        puntosDeControl();
        modificaciones.add(atributo);
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo,null,puntoCar);
            atributo.entidad=entidadesSeleccionadas.get(0);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            entidadesSeleccionadas.get(0).atributos.add(atributo);
            pane.getChildren().add(linea);
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo,null,puntoCar);
            atributo.relacion=relacionesSeleccionadas.get(0);
            Line linea=union.CrearRelacionPoligono(relacionesSeleccionadas.get(0).poligono,atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            pane.getChildren().add(linea);
            relacionesSeleccionadas.get(0).atributos.add(atributo);
        }
        atributoEntidad=false;
        atributoRelacion=false;
    } 
    
    //clave parcial
    public void item3AccionAtributos(){
        textito.setVisible(true);
        Atributo atributo=new Atributo(pane, punto, textito, TipoAtributo.claveParcial, 20);
        atributo.dibujar();      
        atributos.add(atributo);
        contadorPuntos--;
        puntosDeControl();  
        modificaciones.add(atributo);
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo,null,puntoCar);
            atributo.entidad=entidadesSeleccionadas.get(0);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            entidadesSeleccionadas.get(0).atributos.add(atributo);
            pane.getChildren().add(linea);
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo,null,puntoCar);
            atributo.relacion=relacionesSeleccionadas.get(0);
            Line linea=union.CrearRelacionPoligono(relacionesSeleccionadas.get(0).poligono,atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            pane.getChildren().add(linea);
            relacionesSeleccionadas.get(0).atributos.add(atributo);
        }
        atributoEntidad=false;
        atributoRelacion=false;
    } 
    
    //multivaluados
    public void item4AccionAtributos(){
        textito.setVisible(true);
        Atributo atributo=new Atributo(pane, punto, textito, TipoAtributo.multivaluados, 20);
        atributo.dibujar();      
        atributos.add(atributo);
        contadorPuntos--;
        puntosDeControl();  
        modificaciones.add(atributo);
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo,null,puntoCar);
            atributo.entidad=entidadesSeleccionadas.get(0);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            entidadesSeleccionadas.get(0).atributos.add(atributo);
            pane.getChildren().add(linea);
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo,null,puntoCar);
            atributo.relacion=relacionesSeleccionadas.get(0);
            Line linea=union.CrearRelacionPoligono(relacionesSeleccionadas.get(0).poligono,atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            pane.getChildren().add(linea);
            relacionesSeleccionadas.get(0).atributos.add(atributo);
        }
        atributoEntidad=false;
        atributoRelacion=false;
        
    } 
    public void crearCompuesto(){
        textito.setVisible(true);
        //donde está la entidad para relacionarla
        Atributo atributo=new Atributo(pane, punto, textito, TipoAtributo.compuesto, 20);
        herencia.setVisible(false);
        terminar.setVisible(true);
        imagenTerminar.setVisible(true);
        atributo.dibujar();
        atributos.add(atributo);
        modificaciones.add(atributo);
        Union union =new Union(null, null, atributo,null,puntoCar);
        Line linea=union.CrearRelacionPoligono(atributoCompuesto.poligono, atributo.poligono);
        union.linea=linea;
        union.atributo2=atributoCompuesto;
        pane.getChildren().add(linea);
        uniones.add(union);
        atributoCompuesto.atributos.add(atributo);
        contadorPuntos--;
        puntosDeControl();
        
    }
    //compuestos
    public void item5AccionAtributos(){
        textito.setVisible(true);
        //donde está la entidad para relacionarla
        Atributo atributo=new Atributo(pane, punto, textito, TipoAtributo.compuesto, 20);
        herencia.setVisible(false);
        terminar.setVisible(true);
        imagenTerminar.setVisible(true);
        atributo.dibujar();
        atributos.add(atributo);
        atributoCompuesto=atributo;
        modificaciones.add(atributo);
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo,null,puntoCar);
            atributo.entidad=entidadesSeleccionadas.get(0);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            entidadesSeleccionadas.get(0).atributos.add(atributo);
            pane.getChildren().add(linea);
            uniones.add(union);
            contadorPuntos--;
            puntosDeControl();
            
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo,null,puntoCar);
            atributo.relacion=relacionesSeleccionadas.get(0);
            Line linea=union.CrearRelacionPoligono(relacionesSeleccionadas.get(0).poligono,atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            pane.getChildren().add(linea);
            relacionesSeleccionadas.get(0).atributos.add(atributo);
        }
        seSeleccionoCompuesto=true;
        atributoEntidad=false;
        atributoRelacion=false;
        
        
    } 
    
    //derivado
    public void item6AccionAtributos(){
        textito.setVisible(true);
        //donde está la entidad para relacionarla
        Atributo atributo=new Atributo(pane, punto, textito, TipoAtributo.Derivados, 20);
        modificaciones.add(atributo);
        atributo.dibujar();
        atributos.add(atributo);
        contadorPuntos--;
        puntosDeControl();
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo,null,puntoCar);
            atributo.entidad=entidadesSeleccionadas.get(0);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            entidadesSeleccionadas.get(0).atributos.add(atributo);
            pane.getChildren().add(linea);
            uniones.add(union);
            
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo,null,puntoCar);
            atributo.relacion=relacionesSeleccionadas.get(0);
            Line linea=union.CrearRelacionPoligono(relacionesSeleccionadas.get(0).poligono,atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            pane.getChildren().add(linea);
            relacionesSeleccionadas.get(0).atributos.add(atributo);
        }
        atributoEntidad=false;
        atributoRelacion=false;
        
        
    } 
    
    //fuerte (normal)
    public void item1AccionEntidades(){
        textito.setVisible(true);
        //crearRectangulo
        Rectangulo rectangulo = new Rectangulo(pane,largoTexto,punto);
        rectangulo.Dibujar();

        Entidad entidad = new Entidad(textito,rectangulo);
        entidades.add(entidad);
        modificaciones.add(entidad);
        contadorPuntos--;
        puntosDeControl();
        if(elegirEntidadesHeredadas){
            entidadesHeredadas.add(entidad);
        }
    } 
    
    //debil (doble linea)
    public void item2AccionEntidades(){
        textito.setVisible(true);
        //crearRectangulo
        Rectangulo rectangulo = new Rectangulo(pane,largoTexto,punto);
        rectangulo.Dibujar();
        punto.x=punto.x-3;
        punto.y=punto.y-3;
        largoTexto+=6;
        Rectangulo rectangulo2 = new Rectangulo(pane,largoTexto,punto);
        rectangulo2.lineaInferior= new Line(punto.x-300,punto.y+21,punto.x-300+largoTexto,punto.y+21);
        rectangulo2.lineaIzquierda= new Line(punto.x-300,punto.y-25,punto.x-300,punto.y+21);
        rectangulo2.lineaDerecha= new Line(punto.x+largoTexto-300,punto.y-25,punto.x+largoTexto-300,punto.y+21);
        //correr las lineas
        rectangulo2.Dibujar();

        Entidad entidadDebil = new EntidadDebil(textito,rectangulo,rectangulo2);
	entidades.add(entidadDebil);
        modificaciones.add(entidadDebil);
        
        contadorPuntos--;
        puntosDeControl();
        if(elegirEntidadesHeredadas){
            entidadesHeredadas.add(entidadDebil);
        }
    }

    public void actualizarAgregaciones(){
        for (int i = 0; i < agregaciones.size(); i++) {
            agregaciones.get(i).actualizar(pane);
        }
    }
    
    public void actualizarUniones(){
        for (int i = 0; i < uniones.size(); i++) {
            pane.getChildren().remove(uniones.get(i).linea);
        }
        for (int i = 0; i < uniones.size(); i++) {
            pane.getChildren().add(uniones.get(i).getLinea());
            if(uniones.get(i).car!=null){
                if(pane.getChildren().contains(uniones.get(i).getCar()))
                uniones.get(i).getCar();
                else
                    pane.getChildren().add(uniones.get(i).car);
            }
        }
        
    }
    
    @FXML 
    public void seleccionar(){
        movido=null;
        //repintanos todo 
        for (int j = 0; j < relacionesSeleccionadas.size(); j++) {
            relacionesSeleccionadas.get(j).poligono.repintarNegro();
        }
        relacionesSeleccionadas.clear();
        for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
            if(entidadesSeleccionadas.get(i) instanceof Agregacion){
                Agregacion agregacion= (Agregacion)entidadesSeleccionadas.get(i);
                agregacion.rectanguloAgregacion.repintarNegro();
            }
            else{
                entidadesSeleccionadas.get(i).rectangulo.Borrar();
                entidadesSeleccionadas.get(i).rectangulo.Dibujar();
                entidadesSeleccionadas.get(i).rectangulo.seleccionado=false;
            }
        }
        entidadesSeleccionadas.clear();
        sePuedeSeleccionarBorrar=true;
        sePuedeDibujar=false;
        
    }
    @FXML
    public void editar(){
        movido=null;
        sePuedeSeleccionar=true;
        sePuedeEditar=true;
        
    }
    public void borrarEntidad(Entidad entidad){
        for (int i = 0; i < uniones.size(); i++) {
            entidad.rectangulo.Borrar();
            pane.getChildren().remove(entidad.nombre);
            if(uniones.get(i).entidad!=null &&uniones.get(i).entidad==(entidad)){
                borradas.add(uniones.get(i));
                if(uniones.get(i).atributo!=null)
                    for (int j = 0; j < uniones.get(i).entidad.atributos.size(); j++) {
                        borrarAtributo(uniones.get(i).entidad.atributos.get(j));
                    }
                else if(uniones.get(i).relacion!= null){
                    uniones.get(i).relacion.entidadesSelec.remove(entidad);
                    if(uniones.get(i).relacion.entidadesSelec.size()==0){
                        relaciones.remove(uniones.get(i).relacion);
                        pane.getChildren().remove(uniones.get(i).relacion.nombre);
                        
                    }
                    else if(uniones.get(i).relacion.entidadesSelec.size()==1){
                        for (int j = 0; j < uniones.size(); j++){
                            if(uniones.get(j).relacion==uniones.get(i).relacion &&uniones.get(j).entidad==uniones.get(i).relacion.entidadesSelec.get(0)){
                                Union union=(new Union(uniones.get(j).relacion, uniones.get(j).entidad,null,null,puntoCar));
                                union.unoAuno=true;
                                union.CrearRelacion(uniones.get(j).relacion.poligono);
                                uniones.add(union);
                                break;
                            }
                        }
                    } 
                    boolean debil=false;
                    if(uniones.get(i).relacion instanceof RelaciónDebil){
                        for (int j = 0; j < uniones.get(j).relacion.entidadesSelec.size(); j++) { //busco si hay una entidad debil
                            if(uniones.get(j).relacion.entidadesSelec.get(j) instanceof EntidadDebil){
                                debil=true;
                            }
                                
                        }
                    }
                    if(!debil){
                        try{
                            RelaciónDebil relacion=(RelaciónDebil)uniones.get(i).relacion;
                            relacion.poligono2.actualizar(0);
                        }catch(Exception e){}
                    }
                    uniones.get(i).relacion.poligono.actualizar(uniones.get(i).relacion.entidadesSelec.size());
                    actualizarUniones();
                
                }
                
            }
            
           
        }
        for (int j = 0; j < herencias.size(); j++) {
            for (int i = 0; i < herencias.get(j).entidadesHeredadas.size(); i++) {
                    if(herencias.get(j).entidadesHeredadas.get(i)==entidad){
                        herencias.get(j).entidadesHeredadas.remove(entidad);
                        herencias.get(j).entidadesBorradas.add(entidad);
                        herencias.get(j).actualizar1();
                    }
            } 
            if(entidad==herencias.get(j).entidad){
                herenciasBorradas.add(herencias.get(j));
                for (int k = 0; k < herencias.get(j).entidadesHeredadas.size(); k++) {
                    borrarEntidad(herencias.get(j).entidadesHeredadas.get(k));
                    k-=1;
                }
                herencias.get(j).borrar();
                herencias.remove(j);
            }
            
        }
        entidad.rectangulo.Borrar();
        if(entidad instanceof EntidadDebil){
            EntidadDebil entidadDebil=(EntidadDebil)entidad;
            entidadDebil.rectangulo2.Borrar();
        }
        entidades.remove(entidad);
        pane.getChildren().remove(entidad.nombre);
        entidades.remove(entidad);
        sePuedeSeleccionarBorrar=false;
    }
    public void borrarAgregacion(Agregacion agregacion){
        agregacion.rectanguloAgregacion.Borrar(pane);
        pane.getChildren().remove(agregacion.nombre);
        for (int i = 0; i < uniones.size(); i++) {
            if(uniones.get(i).agregacion!=null &&uniones.get(i).agregacion==(agregacion)){
                borradas.add(uniones.get(i));
                if(uniones.get(i).relacion!= null){
                    uniones.get(i).relacion.entidadesSelec.remove(agregacion);
                    if(uniones.get(i).relacion.entidadesSelec.size()==0){
                        relaciones.remove(uniones.get(i).relacion);
                        pane.getChildren().remove(uniones.get(i).relacion.nombre);
                        
                    }
                    else if(uniones.get(i).relacion.entidadesSelec.size()==1){
                        for (int j = 0; j < uniones.size(); j++){
                            if(uniones.get(j).relacion==uniones.get(i).relacion &&uniones.get(j).entidad==uniones.get(i).relacion.entidadesSelec.get(0)){
                                Union union=(new Union(uniones.get(j).relacion, uniones.get(j).entidad,null,null,puntoCar));
                                union.unoAuno=true;
                                union.CrearRelacion(uniones.get(j).relacion.poligono);
                                uniones.add(union);
                                break;
                            }
                        }
                    } 
                    boolean debil=false;
                    if(uniones.get(i).relacion instanceof RelaciónDebil){
                        for (int j = 0; j < uniones.get(j).relacion.entidadesSelec.size(); j++) { //busco si hay una entidad debil
                            if(uniones.get(j).relacion.entidadesSelec.get(j) instanceof EntidadDebil){
                                debil=true;
                            }
                                
                        }
                    }
                    if(!debil){
                        try{
                            RelaciónDebil relacion=(RelaciónDebil)uniones.get(i).relacion;
                            relacion.poligono2.actualizar(0);
                        }catch(Exception e){}
                    }
                    uniones.get(i).relacion.poligono.actualizar(uniones.get(i).relacion.entidadesSelec.size());
                    actualizarUniones();
                
                }
                
            }
            
           
        }
        
        agregaciones.remove(agregacion);
        sePuedeSeleccionarBorrar=false;
    }
    public void borrarRelacion(Relacion relacion){
         for (int i = 0; i < uniones.size(); i++){
             if(uniones.get(i).relacion!=null &&uniones.get(i).relacion==relacion){
                 borradas.add(uniones.get(i));
                 if(uniones.get(i).atributo!=null){
                     borrarAtributo(uniones.get(i).atributo);
                 }
                 
             }
             
         }
        if(relacion instanceof RelaciónDebil){
            RelaciónDebil relacionDebil=(RelaciónDebil)relacion;
            relacionDebil.poligono2.borrar();
        }
        for (int i = 0; i < agregaciones.size(); i++) {
            if(agregaciones.get(i).relacion.equals(relacion)){
                System.out.println("jjd");
                borrarAgregacion(agregaciones.get(i));
                }
        }
        relacion.poligono.borrar();
        relaciones.remove(relacion);
        pane.getChildren().remove(relacion.nombre);
        sePuedeSeleccionarBorrar=false;
    }
    public void borrarAtributo(Atributo atributo){
        for (int i = 0; i < uniones.size(); i++){
            if(uniones.get(i).atributo==atributo){
                borradas.add(uniones.get(i));
                pane.getChildren().remove(uniones.get(i).linea);
                if(uniones.get(i).relacion!=null){
                    uniones.get(i).relacion.atributos.remove(atributo);
                    uniones.get(i).relacion.atributos.remove(atributo);
                }
                if(atributo.tipo==TipoAtributo.compuesto){
                    if(atributo.atributos.size()>0){
                        for (int j = 0; j < atributo.atributos.size(); j++) {
                            borrarAtributo(atributo.atributos.get(j));
                        }
                    }
                }
            }
        }
        atributos.remove(atributo);
        atributo.poligono.borrar();
        pane.getChildren().remove(atributo.texto);
        sePuedeSeleccionarBorrar=false;
        
    }
    @FXML 
    public void undo() throws InterruptedException{
        movido=null;
        int size=modificaciones.size();
        System.out.println("tamaño undo: "+ size);
        if(size==10){
            modificaciones.clear();
            return;
        }
        if(size==0){
            return;
        }
        if(modificaciones.get(size-1) instanceof UnionHerencia){
            UnionHerencia her=(UnionHerencia)modificaciones.get(size-1);
            if(herencias.contains(her)){
                pane.getChildren().removeAll(her.circulo.lineas);
                pane.getChildren().remove(her.nombre);
                for (int i = 0; i < her.lineas.size(); i++) {
                    pane.getChildren().remove(her.lineas.get(i));
                }
            }else{
                pane.getChildren().addAll(her.circulo.lineas);
                pane.getChildren().add(her.nombre);
                for (int i = 0; i < her.lineas.size(); i++) {
                    pane.getChildren().add(her.lineas.get(i));
                }
            }
        }
        if(modificaciones.get(size-1) instanceof Union){
            Union union=(Union)modificaciones.get(size-1);
            if(uniones.contains(union)){
                if(!union.editDoble){
                    try {
                        if(union.car.getText().equals("N")){
                        union.car.setText("1");  
                        }
                        else if(union.car.getText().equals("1")) {
                            union.car.setText("N");
                        }
                    } catch (Exception e) {
                        uniones.remove(union);
                    pane.getChildren().remove(union.linea);}
                    union.editDoble=false;
                    } 
                }
            else{
                uniones.add(union);
                pane.getChildren().add(union.linea);
            }
            
        }
        if(modificaciones.get(size-1) instanceof  Agregacion){
            System.out.println("undo agregacion");
            Agregacion agregacion=(Agregacion)modificaciones.get(size-1);
            if(agregacion.nombresEditados.size()>0){
                    agregacion.nombresEditados2.add(agregacion.nombre.getText());
                    agregacion.nombre.setText(agregacion.nombresEditados.get(agregacion.nombresEditados.size()-1));
                    agregacion.nombresEditados.remove(agregacion.nombresEditados.size()-1);
            }else
            if(agregaciones.contains(agregacion)){
                agregacion.rectanguloAgregacion.Borrar(pane);
                pane.getChildren().remove(agregacion.nombre);
                agregaciones.remove(agregacion);
                if(agregacion.nombresEditados.size()>0){
                    agregacion.nombresEditados2.add(agregacion.nombre.getText());
                    System.out.println(agregacion.nombresEditados.get(agregacion.nombresEditados.size()-1));
                    agregacion.nombre.setText(agregacion.nombresEditados.get(agregacion.nombresEditados.size()-1));
                    agregacion.nombresEditados.remove(agregacion.nombresEditados.size()-1);
                }
            }else{
                
                agregacion.rectanguloAgregacion.Dibujar(pane);
                pane.getChildren().add(agregacion.nombre);
                agregaciones.add(agregacion);
                
            }
            redo.add(modificaciones.get(size-1));
            modificaciones.remove(size-1);
            return;
        }
        if(modificaciones.get(size-1) instanceof ArrayList){
            ArrayList<Point> puntosMov=(ArrayList<Point>)modificaciones.get(size-1) ;
            for (int i = puntosMov.size()-1; i > 0; i--) {
                mover(puntosMov.get(i));
            }
            
        }
        if(modificaciones.get(size-1) instanceof Entidad){//quiere decir que lo ultimo que se creo fue una entidad, por lo se se procede a borrar esta
            System.out.println("undo entidad");
            Entidad entidad=(Entidad)modificaciones.get(size-1);
            if(entidad.nombresEditados.size()>0){
                    entidad.nombresEditados2.add(entidad.nombre.getText());
                    entidad.nombre.setText(entidad.nombresEditados.get(entidad.nombresEditados.size()-1));
                    entidad.nombresEditados.remove(entidad.nombresEditados.size()-1);
            }else
            undoEntidad(entidad);
                
        }
        if(modificaciones.get(size-1) instanceof Relacion){//quiere decir que lo ultimo que se creo fue una Relacción, por lo se se procede a borrar esta
            System.out.println("undo relacion");
            Relacion rel=(Relacion)modificaciones.get(size-1);
            if(rel.nombresEditados.size()>0){
                    rel.nombresEditados2.add(rel.nombre.getText());
                    rel.nombre.setText(rel.nombresEditados.get(rel.nombresEditados.size()-1));
                    rel.nombresEditados.remove(rel.nombresEditados.size()-1);
            }else
            if(relaciones.contains(modificaciones.get(size-1))){
                borrarRelacion((Relacion)modificaciones.get(size-1));
                relaciones.remove((Relacion)modificaciones.get(size-1));

                for (int i = 0; i < borradas.size(); i++) {
                    uniones.remove(borradas.get(i));
                    pane.getChildren().remove(borradas.get(i).linea);
                    pane.getChildren().remove(borradas.get(i).car);
                }
                borradas.clear();
            }else{
                Relacion relacion=(Relacion)modificaciones.get(size-1);
                relaciones.add(relacion);
                relacion.poligono.mover(relacion.poligono.punto);
                pane.getChildren().add(relacion.nombre);
                for (int i = 0; i < relacion.entidadesSelec.size(); i++) {
                    for (int j = 0; j <relacion.entidadesSelec.get(i).uniones.size(); j++) {
                        if(relaciones.contains(relacion.entidadesSelec.get(i).uniones.get(j).relacion)){
                            uniones.add(relacion.entidadesSelec.get(i).uniones.get(j));
                        }
                    }
                }
                for (int i = 0; i < relacion.atributos.size(); i++) {
                    atributos.add(relacion.atributos.get(i));
                    Union union=new Union(relacion, null,relacion.atributos.get(i),null,puntoCar);
                    uniones.add(union);
                    relacion.atributos.get(i).mover(relacion.atributos.get(i).punto);
                    pane.getChildren().add(relacion.atributos.get(i).texto);
                }
                actualizarUniones();            }
        }
        if(modificaciones.get(size-1) instanceof Atributo){//quiere decir que lo ultimo que se creo fue una Relacción, por lo se se procede a borrar esta
            System.out.println("undo atributo");
            Atributo atributo=(Atributo)modificaciones.get(size-1);
            if(atributo.nombresEditados.size()>0){
                    atributo.nombresEditados2.add(atributo.texto.getText());
                    atributo.texto.setText(atributo.nombresEditados.get(atributo.nombresEditados.size()-1));
                    atributo.nombresEditados.remove(atributo.nombresEditados.size()-1);
            }else
            undoAtributo(atributo);
        }
        redo.add(modificaciones.get(size-1));
        modificaciones.remove(size-1);
        contadorPuntos--;
        puntosDeControl();
    }
    public void redo(){
        System.out.println("tamaño del redo"+redo.size());
        
        int size=redo.size();
        if(size==0){
            return;
        }
        if(redo.get(size-1) instanceof UnionHerencia){
            UnionHerencia her=(UnionHerencia)redo.get(size-1);
            if(herencias.contains(her)){
                pane.getChildren().removeAll(her.circulo.lineas);
                pane.getChildren().remove(her.nombre);
                for (int i = 0; i < her.lineas.size(); i++) {
                    pane.getChildren().remove(her.lineas.get(i));
                }
            }else{
                pane.getChildren().addAll(her.circulo.lineas);
                pane.getChildren().add(her.nombre);
                for (int i = 0; i < her.lineas.size(); i++) {
                    pane.getChildren().add(her.lineas.get(i));
                }
            }
        }
        if(redo.get(size-1) instanceof Union){
            Union union=(Union)redo.get(size-1);
            if(uniones.contains(union)){
                try {
                    if(union.car.getText().equals("N")){
                    union.car.setText("1");  
                    }
                    else if(union.car.getText().equals("1")) {
                        union.car.setText("N");
                    }
                } catch (Exception e) {
                    uniones.remove(union);
                    pane.getChildren().remove(union.linea);
                
                    union.editDoble=false;
                }
            }
          
            else{
                uniones.add(union);
                pane.getChildren().add(union.linea);
            }
        }
        if(redo.get(size-1) instanceof  Agregacion){
             Agregacion agregacion=(Agregacion)redo.get(size-1);
            if(agregacion.nombresEditados2.size()>0){
                    agregacion.nombre.setText(agregacion.nombresEditados2.get(agregacion.nombresEditados2.size()-1));
                    agregacion.nombresEditados2.remove(agregacion.nombresEditados2.size()-1);
            }else{
            if(agregaciones.contains(agregacion)){
                agregacion.rectanguloAgregacion.Borrar(pane);
                pane.getChildren().remove(agregacion.nombre);
                agregaciones.remove(agregacion);
            }else{
                agregacion.rectanguloAgregacion.Dibujar(pane);
                pane.getChildren().add(agregacion.nombre);
                agregaciones.add(agregacion);
            }
            modificaciones.add( redo.get(size-1));
            redo.remove(size-1);
            return;
            }
        }
        if(redo.get(size-1) instanceof ArrayList){
            ArrayList<Point> puntosMov=(ArrayList<Point>)redo.get(size-1) ;
            for (int i = 0; i <puntosMov.size(); i++) {
                mover(puntosMov.get(i));
            }
            
        }
        if(redo.get(size-1) instanceof Entidad){//quiere decir que lo ultimo que se creo fue una entidad, por lo se se procede a borrar esta
            System.out.println("redo entidad");
            Entidad entidad=(Entidad)redo.get(size-1);
            if(entidad.nombresEditados2.size()>0){
                    System.out.println(entidad.nombresEditados2.get(entidad.nombresEditados2.size()-1));
                    entidad.nombre.setText(entidad.nombresEditados2.get(entidad.nombresEditados2.size()-1));
                    entidad.nombresEditados2.remove(entidad.nombresEditados2.size()-1);
            }else
            undoEntidad(entidad);
                
        }
        if(redo.get(size-1) instanceof Relacion){//quiere decir que lo ultimo que se creo fue una Relacción, por lo se se procede a borrar esta
            System.out.println("redo relacion");
            Relacion relacion=(Relacion)redo.get(size-1);
            if(relacion.nombresEditados2.size()>0){
                    relacion.nombre.setText(relacion.nombresEditados2.get(relacion.nombresEditados2.size()-1));
                    relacion.nombresEditados2.remove(relacion.nombresEditados2.size()-1);
            }else
            if(relaciones.contains(redo.get(size-1))){
                borrarRelacion((Relacion)redo.get(size-1));
                relaciones.remove((Relacion)redo.get(size-1));
                uniones.removeAll(relacion.uniones);
                for (int i = 0; i < relacion.uniones.size(); i++) {
                    pane.getChildren().remove(relacion.uniones.get(i).linea);
                    pane.getChildren().remove(relacion.uniones.get(i).car);
                }
                borradas.clear();
            }else{
                relaciones.add(relacion);
                relacion.poligono.mover(relacion.poligono.punto);
                pane.getChildren().add(relacion.nombre);
                uniones.addAll(relacion.uniones);
                for (int i = 0; i < relacion.atributos.size(); i++) {
                    atributos.add(relacion.atributos.get(i));
                    Union union=new Union(relacion, null,relacion.atributos.get(i),null,puntoCar);
                    uniones.add(union);
                    relacion.atributos.get(i).mover(relacion.atributos.get(i).punto);
                    pane.getChildren().add(relacion.atributos.get(i).texto);
                }
                actualizarUniones();            }
        }
        if(redo.get(size-1) instanceof Atributo){//quiere decir que lo ultimo que se creo fue una Relacción, por lo se se procede a borrar esta
            System.out.println("redo atributo");
            Atributo atributo=(Atributo)redo.get(size-1);
            if(atributo.nombresEditados2.size()>0){
                    atributo.texto.setText(atributo.nombresEditados2.get(atributo.nombresEditados2.size()-1));
                    atributo.nombresEditados2.remove(atributo.nombresEditados2.size()-1);
            }else
            if(atributos.contains(atributo)){
                borrarAtributo(atributo);
                atributos.remove(atributo);
                for (int i = 0; i < borradas.size(); i++) {
                    uniones.remove(borradas.get(i));
                    pane.getChildren().remove(borradas.get(i).linea);
                    pane.getChildren().remove(borradas.get(i).car);
                }                
                borradas.clear();
            }else{
                atributos.add(atributo);
                atributo.dibujar();
                pane.getChildren().add(atributo.texto);
                System.out.println(atributo.entidad);
                Union union=new Union(atributo.relacion, atributo.entidad, atributo,null,puntoCar);
                uniones.add(union);
                if(atributo.tipo==TipoAtributo.compuesto){
                    if(atributo.atributos.size()>0){
                        for (int j = 0; j < atributo.atributos.size(); j++) {
                            atributo.atributos.get(j).dibujar();
                            atributos.add(atributo.atributos.get(j));
                            pane.getChildren().add(atributo.atributos.get(j).texto);
                            
                        }
                    }
                }
                actualizarUniones();
            }
        }
        contadorPuntos--;
        puntosDeControl();
        modificaciones.add( redo.get(size-1));
        redo.remove(size-1);
    }
    public void undoAtributo(Atributo atributo){
        if(atributos.contains(atributo)){
                borrarAtributo(atributo);
                atributos.remove(atributo);
                for (int i = 0; i < borradas.size(); i++) {
                    uniones.remove(borradas.get(i));
                    pane.getChildren().remove(borradas.get(i).linea);
                    pane.getChildren().remove(borradas.get(i).car);
                }                
                borradas.clear();
            }else{
                atributos.add(atributo);
                atributo.dibujar();
                Union union=new Union(atributo.relacion, atributo.entidad,atributo,null,puntoCar);
                uniones.add(union);
                pane.getChildren().add(atributo.texto);
                if(atributo.tipo==TipoAtributo.compuesto){
                    if(atributo.atributos.size()>0){
                        for (int j = 0; j < atributo.atributos.size(); j++) {
                            atributo.atributos.get(j).dibujar();
                            union=new Union(null, null,atributo,null,puntoCar);
                            union.atributo2=atributo.atributos.get(j);
                            uniones.add(union);
                            atributos.add(atributo.atributos.get(j));
                            pane.getChildren().add(atributo.atributos.get(j).texto);
                        }
                    }
                }
                actualizarUniones();
                
            }
    }
    public void undoEntidad(Entidad entidad){
        if(entidades.contains(entidad)){
                
                borrarEntidad(entidad);
                entidad.uniones=borradas;
                for (int i = 0; i < borradas.size(); i++) {
                    uniones.remove(borradas.get(i));
                    pane.getChildren().remove(borradas.get(i).linea);
                    pane.getChildren().remove(borradas.get(i).car);
                
                borradas.clear();
                }
                //agregamos este objeto a una llista de objetos 
            }else{
                entidades.add(entidad);
                try {
                pane.getChildren().add(entidad.nombre);
                } catch (Exception e) {
                }
                if (entidad instanceof EntidadDebil){
                    EntidadDebil entidadDebil = (EntidadDebil)entidad;
                    entidadDebil.rectangulo2.Dibujar();
                }
                for (int i = 0; i < uniones.size(); i++) {
                    for (int j = 0; j <entidad.relaciones.size(); j++) {
                        entidad.relaciones.get(j).entidadesSelec.add(entidad);
                        if(uniones.get(i).relacion!=null&&uniones.get(i).relacion==entidad.relaciones.get(j)){
                            if (uniones.get(i).unoAuno) {
                                pane.getChildren().remove(uniones.get(i).linea);
                                pane.getChildren().remove(uniones.get(i).car);
                                uniones.remove(i);
                            }
                        }
                    }
                    
                }
                
                for (int i = 0; i < entidad.uniones.size(); i++) {
                    if(relaciones.contains(entidad.uniones.get(i).relacion)){
                        uniones.add(entidad.uniones.get(i));
                    }
                }
                Rectangulo rectangulito = entidad.rectangulo;
                rectangulito.Dibujar();
                for (int k = 0; k < circulos.size(); k++) {
                    pane.getChildren().remove(circulos.get(k));
                }   
                
                contadorPuntos--;
                puntosDeControl();
                actualizarUniones();
                if(herenciasBorradas.size()>0){
                    for (int i = 0; i < herenciasBorradas.get(herenciasBorradas.size()-1).entidadesBorradas.size(); i++) {
                        herenciasBorradas.get(herenciasBorradas.size()-1).entidadesBorradas.get(i).rectangulo.Dibujar();
                        pane.getChildren().add(herenciasBorradas.get(herenciasBorradas.size()-1).entidadesBorradas.get(i).nombre);
                        entidades.add(herenciasBorradas.get(herenciasBorradas.size()-1).entidadesBorradas.get(i));
                        for (int j = 0; j < herenciasBorradas.get(herenciasBorradas.size()-1).entidadesBorradas.get(i).atributos.size(); j++) {
                            
                        }
                    }
                    herenciasBorradas.get(herenciasBorradas.size()-1).entidadesHeredadas.addAll(herenciasBorradas.get(herenciasBorradas.size()-1).entidadesBorradas);
                    herenciasBorradas.get(herenciasBorradas.size()-1).entidadesBorradas.clear();
                    herenciasBorradas.get(herenciasBorradas.size()-1).actualizar1();
                    herencias.add(herenciasBorradas.get(herenciasBorradas.size()-1));
                    herenciasBorradas.remove(herenciasBorradas.size()-1);
                }
            }
    }
public boolean estaDentro(ArrayList<Point> puntos,int cualEs){//puntos: de la relacion
        ArrayList <Point> puntosAgrega=agregaciones.get(cualEs).puntos;
        for (int i = 0; i < puntos.size(); i++) {
            for (int j = 0; j < puntosAgrega.size(); j++) {
                
            }
            //agregaciones.get(cualEs).puntos
        }
        return false;
    }
public void editarCardinalidad(){
       entidadesSeleccionadas.clear();
       sePuedeSeleccionar=true;
       sePuedeEditarCar=true;
   }
   public void doble(){
       entidadesSeleccionadas.clear();
       sePuedeSeleccionar=true;
       sePuedeDibujarDoble=true;
   }
   public void simple(){
       entidadesSeleccionadas.clear();
       sePuedeSeleccionar=true;
       sePuedeDibujarSimple=true;
   }
   
   public void repintarTodoNegro(){
        for (int p = 0; p < entidades.size(); p++) {
            entidades.get(p).rectangulo.Borrar();
            entidades.get(p).rectangulo.Dibujar();
            entidades.get(p).rectangulo.seleccionado=false;
        }
        for (int j = 0; j < relaciones.size(); j++) {
            relaciones.get(j).poligono.repintarNegro();
        }
        for (int i = 0; i < atributos.size(); i++) {
            atributos.get(i).poligono.repintarNegro();
        }
   }

   @FXML
   public void validar(){
       boolean clave=false;
       String advertencia="";
       boolean claveParcial=false;
       int contador=0;
       for (int i = 0; i < entidades.size(); i++) {
            for (int j = i+1; j < entidades.size()+1; j++) {
                if(j==entidades.size()){
                    
                }
                else if(entidades.get(i).nombre.getText().equals(entidades.get(j).nombre.getText())){
                    System.out.println("MISMO NOMBREEEEEEEEEEEEE ENTIDAD");
                    contador++;
                }
            }
        }
        
        for (int i = 0; i < herencias.size(); i++) {
            ArrayList nombresPadre= new ArrayList();
            ArrayList nombresHijos= new ArrayList();
            for (int j = 0; j < herencias.get(i).entidad.atributos.size(); j++) {
                nombresPadre.add(herencias.get(i).entidad.atributos.get(j).texto.getText());
                for (int k = 0; k < herencias.get(i).entidad.atributos.get(j).atributos.size(); k++) {
                    nombresPadre.add(herencias.get(i).entidad.atributos.get(j).atributos.get(k).texto.getText());
                }
            }
            for (int j = 0; j < herencias.get(i).entidad.entidadesHeredadas.size(); j++) {
                for (int r = 0; r < herencias.get(i).entidad.entidadesHeredadas.get(j).atributos.size(); r++) {
                    nombresHijos.add(herencias.get(i).entidad.entidadesHeredadas.get(j).atributos.get(r).texto.getText());
                    for (int k = 0; k < herencias.get(i).entidad.entidadesHeredadas.get(j).atributos.get(r).atributos.size(); k++) {
                        nombresHijos.add(herencias.get(i).entidad.entidadesHeredadas.get(j).atributos.get(r).atributos.get(k).texto.getText());
                    }
                }
            }
            for (int m = 0; m < nombresPadre.size(); m++) {
                for (int j =0 ; j < nombresHijos.size(); j++) {
                    if(nombresHijos.get(j).equals(nombresPadre.get(m))){
                        System.out.println("IGUAAAAAAAAAAAAAAAAAAAL");
                        contador++;
                        advertencia=advertencia+("\nEl atributo "+ nombresPadre.get(m)+" posee el mismo nombre de ");
                    }
                }
            }
        }
       
       for (int i = 0; i < entidades.size(); i++) {
           clave=false;
           if(entidades.get(i).atributos.size()==0){
               if(entidades.get(i) instanceof EntidadDebil){
                   contador++;
                    advertencia=advertencia+("\nA entidad "+ entidades.get(i).nombre.getText() +" le falta un atributo clave parcial");
               }
                else{
                   contador++;
                    advertencia=advertencia+"\nA entidad "+ entidades.get(i).nombre.getText() +" le falta un atributo clave";
                }
           }
           else{
               if(entidades.get(i) instanceof EntidadDebil){
                    for (int j = 0; j < entidades.get(i).atributos.size(); j++) {
                        if(entidades.get(i).atributos.get(j).tipo.equals(TipoAtributo.claveParcial)){
                            claveParcial=true;
                        }
                    }
                    if(!claveParcial){
                        contador++;
                        advertencia=advertencia+("\nA entidad "+ entidades.get(i).nombre.getText() +" le falta un atributo clave parcial");
                    }
                    else{
                        claveParcial=false;
                    }
                }
               else{
                   for (int j = 0; j < entidades.get(i).atributos.size(); j++) {
                        if(entidades.get(i).atributos.get(j).tipo.equals(TipoAtributo.clave)){
                            clave=true;
                        }
                    }
                    if(!clave){
                        contador++;
                        advertencia=advertencia+("\nA entidad "+ entidades.get(i).nombre.getText() +" le falta un atributo clave ");
                    }
                    else{
                        clave=false;
                    }
               }
           }
        }
        if(contador!=0){
            
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informacion");
        alert.setHeaderText("Entidades");
        alert.setContentText(advertencia);
        alert.showAndWait();
   }    
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informacion");
        alert.setHeaderText("Entidades");
        alert.setContentText("Programa correcto");
        alert.showAndWait();
        }
   }
}

