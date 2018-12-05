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
    public ArrayList<Relacion> relaciones= new ArrayList();
    public ArrayList<Atributo> atributosCompuestos= new ArrayList();   
    public ArrayList<Atributo> atributos= new ArrayList();    
    public ArrayList<Entidad> entidadesSeleccionadas= new ArrayList();
    public ArrayList<Relacion> relacionesSeleccionadas= new ArrayList();
    public ArrayList<Entidad> entidadesHeredadas= new ArrayList();
    public ArrayList<Relacion> relacionesAModificar= new ArrayList();
    
    public ArrayList<UnionHerencia> herencias= new ArrayList();
    public ArrayList distanciaEntrePuntos= new ArrayList();
    public ArrayList circulos= new ArrayList();
    public ArrayList<Line> lineas = new ArrayList();
    public ArrayList<Union> borradas=new ArrayList<>();
    public Point punto;
    public int posPunto;
    public int posPunto2;
    public ArrayList<Union> uniones=new ArrayList();
    public boolean crearHerencia=false;
    public boolean elegirEntidadesHeredadas=false;
    
    public boolean atributoEntidad=false;
    public boolean atributoRelacion=false;
    boolean sePuedeSeleccionarBorrar = false;
    public boolean seSeleccionoEntidad=false;
    public boolean seSeleccionoRelacion=false;
    public boolean seSeleccionoAtributo=false;
    public boolean seSeleccionoHerencia=false;
    public boolean seSeleccionoCompuesto=false;
    public boolean sePuedeEditar=false;
    public int objetoNumero=-1;
    public Atributo atributoCompuesto;
    
    
    
    
    @FXML
    public void transportar(){
        
        for (int i = 0; i < relaciones.size(); i++) { //mueve relaciones
            punto = MouseInfo.getPointerInfo().getLocation();
            punto.x=punto.x-300;
            
            if(relaciones.get(i).poligono.seleccionar(punto)){
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
                return;
            }
        }
        for (int i = 0; i < entidades.size(); i++){
           punto = MouseInfo.getPointerInfo().getLocation();
           posX=punto.getX()-280;
           posY=punto.getY();
           if (interseccionTransportar(entidades.get(i).rectangulo.getPuntos(),i)){
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
                
                for (int j = 0; j < herencias.size(); j++) {
                        herencias.get(j).actualizar1();
                }
                return;
               
           }
        }
        for (int i = 0; i < atributos.size(); i++){
            punto = MouseInfo.getPointerInfo().getLocation();
            punto.x-=300;
            punto.y-=20;
            if(atributos.get(i).poligono.seleccionar(punto)){
                punto.x+=300;
                punto.y+=20;
                atributos.get(i).mover(punto);
                actualizarUniones();
                for (int k = 0; k < circulos.size(); k++) {
                    pane.getChildren().remove(circulos.get(k));
                } 
                contadorPuntos--;
                puntosDeControl();
                return;
            }
        }
       
        
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
                                relaciones.add(relacion2);
                                break;
                            }
                                
                        }
                        
                        poligono.Dibujar(4, (int) 75, punto);
                    }
                    else{
                        for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
                            if(entidadesSeleccionadas.get(i) instanceof EntidadDebil){
                                poligono2.Dibujar(entidadesSeleccionadas.size(), (int) 75+5, punto);
                                relaciones.add(relacion2);
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
                    relaciones.add(relacion);

                    //funcion para unir
                    for(int i=0; i<entidadesSeleccionadas.size();i++){ //entidades a relacionar
                        //dibujar linea que une
                        if(entidadesSeleccionadas.get(i) instanceof EntidadDebil){
                            entidadesSeleccionadas.get(i).relaciones.add(relacion2);
                            Union union=new Union(relacion2, entidadesSeleccionadas.get(i), null);
                            union.doble=true;
                            Line lineaa =union.getLinea();
                            uniones.add(union);
                            lineaa.setStroke(Color.BLACK); //color de la linea que une
                            lineaa.setStrokeWidth(1);
                            lineaa.setStrokeLineCap(StrokeLineCap.ROUND);
                            pane.getChildren().add(lineaa);
                            entidadesSeleccionadas.get(i).getLineas().add(lineaa);
                            relacion2.getLineas().add(lineaa);
                            
                        }
                        entidadesSeleccionadas.get(i).relaciones.add(relacion);
                        Union union=new Union(relacion, entidadesSeleccionadas.get(i), null);
                        Line lineaa=union.getLinea();
                        uniones.add(union);
                        lineaa.setStroke(Color.BLACK); //colo de la linea que une
                        lineaa.setStrokeWidth(1);
                        lineaa.setStrokeLineCap(StrokeLineCap.ROUND);
                        pane.getChildren().add(lineaa);
                        entidadesSeleccionadas.get(i).getLineas().add(lineaa);
                        relacion.getLineas().add(lineaa);
                    }
                    if(entidadesSeleccionadas.size()==1){
                        relacion.unoAuno=true;
                        Union union=new Union(relacion2, entidadesSeleccionadas.get(0), null);
                        union.unoAuno=true;
                        Line lineaa=union.getLinea();
                        uniones.add(union);
                        lineaa.setStroke(Color.BLACK); //coloco de la linea que une
                        lineaa.setStrokeWidth(1);
                        lineaa.setStrokeLineCap(StrokeLineCap.ROUND);
                        pane.getChildren().add(lineaa);
                        entidadesSeleccionadas.get(0).getLineas().add(lineaa);
                    }
                    sePuedeCrearRelacion=false;  
                    for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
                        entidadesSeleccionadas.get(i).rectangulo.Borrar();
                        entidadesSeleccionadas.get(i).rectangulo.Dibujar();
                        entidadesSeleccionadas.get(i).rectangulo.seleccionado=false;
                    }
                    contadorPuntos--;
                    puntosDeControl();
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
            
            
        }
        else if(crearHerencia){
                Point punto = MouseInfo.getPointerInfo().getLocation();
                punto.x-=300;
                punto.y-=25;
                for (int i = 0; i < entidades.size(); i++) {
                    if (interseccionRectangulo(entidades.get(i),punto)){
                        crearHerencia=false;
                        UnionHerencia herencia=new UnionHerencia(entidades.get(i), pane);
                        crearHerencia=false;
                        herencias.add(herencia);
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
            
            try{
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
                        entidadesSeleccionadas.add(entidades.get(i));
                    }
                }
                punto.x=punto.x-300;
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
                        relacionesSeleccionadas.add(relaciones.get(i));
                        
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
            }catch(Exception e){
                
            }
        }
        else if (sePuedeSeleccionarBorrar){
            
                Point punto = MouseInfo.getPointerInfo().getLocation();
                punto.x-=300;
                punto.y-=25;
                for (int i = 0; i < relaciones.size(); i++) {
                    //entidades.get(i).rectangulo.imprimirPuntos();
                    if (relaciones.get(i).poligono.seleccionar(punto)){
                        borrarRelacion(relaciones.get(i));
                    }
                }
                for (int i = 0; i < atributos.size(); i++) {
                    //entidades.get(i).rectangulo.imprimirPuntos();
                    if (atributos.get(i).poligono.seleccionar(punto)){
                        borrarAtributo(atributos.get(i));
                    }
                }
                for (int i = 0; i < entidades.size(); i++) {
                    //entidades.get(i).rectangulo.imprimirPuntos();
                    if (interseccionRectangulo(entidades.get(i), punto)){
                        borrarEntidad(entidades.get(i));
                    }
                }
                for (int i = 0; i < borradas.size(); i++) {
                    uniones.remove(borradas.get(i));
                    pane.getChildren().remove(borradas.get(i).linea);
                }
                borradas.clear();
                
        }
        
        insertarTexto1.setText("");
        
        sePuedeDibujar=false;
    
    }
    
    @FXML
    public void crearEntidad(){
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
        sePuedeDibujar=false;
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
        sePuedeCrearAtributo=true;
        nombre.setText("Nombre Atributo: ");
        insertarTexto1.setVisible(true);
        botonCrear.setVisible(true);
        textoBotonCrear.setVisible(true);
    }
    @FXML
    public void CrearHerecia(){
        crearHerencia=true;
        terminar.setVisible(true);
        imagenTerminar.setVisible(true);
        herencia.setVisible(false);
        sePuedeSeleccionarBorrar=false;
        
    }
    @FXML
    public void terminar(){
        if(seSeleccionoHerencia){
            for (int i = 0; i < entidadesHeredadas.size(); i++) {
                herencias.get(herencias.size()-1).agregarHerencia(entidadesHeredadas.get( i));
            }
            System.out.println(herencias.get(herencias.size()-1).entidadesHeredadas.size());
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
        if(sePuedeEditar){
            if(seSeleccionoEntidad){
                for (int p = 0; p < entidadesSeleccionadas.size(); p++) {
                    entidadesSeleccionadas.get(p).rectangulo.Borrar();
                    entidadesSeleccionadas.get(p).rectangulo.Dibujar();
                    entidadesSeleccionadas.get(p).rectangulo.seleccionado=false;
                }
                entidades.get(objetoNumero).nombre.setText(insertarTexto1.getText());
                seSeleccionoEntidad=false;
            }
            else if (seSeleccionoRelacion){
                for (int j = 0; j < relacionesSeleccionadas.size(); j++) {
                    relacionesSeleccionadas.get(j).poligono.repintarNegro();
                }
                //relacionesSeleccionadas.clear();
                relaciones.get(objetoNumero).nombre.setText(insertarTexto1.getText());
                seSeleccionoRelacion=false;
            }
            else if (seSeleccionoAtributo){
                for (int i = 0; i < atributos.size(); i++) {
                    atributos.get(i).poligono.repintarNegro();
                }
                atributos.get(objetoNumero).texto.setText(insertarTexto1.getText());
                seSeleccionoAtributo=false;
            }
            sePuedeEditar=false;
            sePuedeSeleccionar=false;
            textoBotonCrear.setText("Crear");
            insertarTexto1.setText("");
            insertarTexto1.setVisible(false);
            textoBotonCrear.setVisible(false);
            botonCrear.setVisible(false);
        }
        
        else{
            sePuedeDibujar=true;
            nombre.setVisible(false);
            insertarTexto1.setVisible(false);
            botonCrear.setVisible(false);
            textoBotonCrear.setVisible(false);
            sePuedeSeleccionar=false;
        }
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
        Poligono poligono=new Poligono(pane);
        poligono.puntosBolean.clear();
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
                nombreHerencia=new Text(punto.x-275, punto.y+70,"D");
                pane.getChildren().add(nombreHerencia);
                herencias.get(herencias.size()-1).nombre=nombreHerencia;
            }
        });

        MenuItem item2= new MenuItem("Solapamiento");
        item2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //S
                 nombreHerencia=new Text(punto.x-275, punto.y+70,"S");
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
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            pane.getChildren().add(linea);
            uniones.add(union);
            entidadesSeleccionadas.get(0).atributos.add(atributo);
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo);
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

        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            entidadesSeleccionadas.get(0).atributos.add(atributo);
            pane.getChildren().add(linea);
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo);
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
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            entidadesSeleccionadas.get(0).atributos.add(atributo);
            pane.getChildren().add(linea);
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo);
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
        
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            uniones.add(union);
            entidadesSeleccionadas.get(0).atributos.add(atributo);
            pane.getChildren().add(linea);
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo);
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
        Union union =new Union(null, null, atributo);
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
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo);
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
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo);
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
        
        atributo.dibujar();
        atributos.add(atributo);
        contadorPuntos--;
        puntosDeControl();
        if(atributoEntidad){
            Union union =new Union(null, entidadesSeleccionadas.get(0), atributo);
            Line linea=union.CrearRelacion(atributo.poligono);
            union.linea=linea;
            entidadesSeleccionadas.get(0).atributos.add(atributo);
            pane.getChildren().add(linea);
            uniones.add(union);
            
        }
        else if (atributoRelacion){
            //FUNCION UNION
            Union union =new Union(relacionesSeleccionadas.get(0),null, atributo);
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
        
        contadorPuntos--;
        puntosDeControl();
        if(elegirEntidadesHeredadas){
            entidadesHeredadas.add(entidadDebil);
        }
    }

    
    
    public void actualizarUniones(){
        for (int i = 0; i < uniones.size(); i++) {
            pane.getChildren().remove(uniones.get(i).linea);
            
        }
        for (int i = 0; i < uniones.size(); i++) {
            pane.getChildren().add(uniones.get(i).getLinea());
            
        }
        
    }
    
    @FXML 
    public void seleccionar(){
        System.out.println("");
        //repintanos todo 
        for (int j = 0; j < relacionesSeleccionadas.size(); j++) {
            relacionesSeleccionadas.get(j).poligono.repintarNegro();
        }
        relacionesSeleccionadas.clear();
        for (int i = 0; i < entidadesSeleccionadas.size(); i++) {
            entidadesSeleccionadas.get(i).rectangulo.Borrar();
            entidadesSeleccionadas.get(i).rectangulo.Dibujar();
            entidadesSeleccionadas.get(i).rectangulo.seleccionado=false;
        }
        entidadesSeleccionadas.clear();
        
        sePuedeSeleccionarBorrar=true;
        sePuedeDibujar=false;
        
    }
    @FXML
    public void editar(){
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
                                Union union=(new Union(uniones.get(j).relacion, uniones.get(j).entidad,null));
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
            if(entidad==herencias.get(j).entidad){
                for (int k = 0; k < herencias.get(j).entidadesHeredadas.size(); k++) {
                    borrarEntidad(herencias.get(j).entidadesHeredadas.get(k));
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
}
