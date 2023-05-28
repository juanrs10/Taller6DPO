package model;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.event.PrintEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.Array;

//DICT DEPENDENCIES

import java.util.Dictionary;
import java.lang.Enum; 
import java.util.Hashtable;
public class Restaurante {

    public ArrayList<ProductoMenu> menu; 

    Dictionary <String,Producto> menuHashed = new Hashtable<>();
    
    Dictionary <String,Ingrediente> ingredientesHashed = new Hashtable<>();

    Dictionary<String, Combo> combosHashed = new Hashtable<>();

    public ArrayList<Ingrediente> ingredientes; 

    public ArrayList<Combo> combos; 

    public Pedido elPedido; 

    public void iniciarPedido(String nombreCliente, String direccionCliente){
        
        elPedido = new Pedido(nombreCliente,direccionCliente);
        
    }

    public void cerrarGuardarPedido(){

    }

    public Pedido getPedidoEnCurso(){
        
        return elPedido; 
    }

    public ArrayList<ProductoMenu> getMenuBase(){

        return menu; 

    }

    public ArrayList<Ingrediente> getIngredientes(){

        return ingredientes; 
        
    }

    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) {
        try {
            cargarIngredientes(archivoIngredientes);
            cargarCombos(archivoCombos);
            cargarMenu(archivoMenu);
        } catch (IngredienteRepetidoException | ProductoRepetidoException e) {
            System.out.println(e.getMessage());
        }
    }
    

    private void cargarIngredientes(File archivoIngredientes) throws IngredienteRepetidoException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivoIngredientes));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split("\n");
                int index = elements.length-1;
                for (int i=0; i<=index; i++) {
                    String [] splited = elements[i].split(";");
                    if (ingredientesHashed.get(splited[0]) != null) {
                        throw new IngredienteRepetidoException("Ingrediente repetido: " + splited[0]);
                    }
                    Ingrediente ingrediente = new Ingrediente(splited[0],Integer.parseInt(splited[1]));
                    ingredientesHashed.put(splited[0], ingrediente);
                    ingredientes.add(ingrediente);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private void cargarMenu(File archivoMenu) throws ProductoRepetidoException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivoMenu));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split("\n");
                int index = elements.length-1;
                for (int i=0; i<=index; i++) {
                    String [] splited = elements[i].split(";");
                    if (menuHashed.get(splited[0]) != null) {
                        throw new ProductoRepetidoException("Producto repetido: " + splited[0]);
                    }
                    ProductoMenu productoMenu = new ProductoMenu(splited[0],Integer.parseInt(splited[1]));
                    menuHashed.put(splited[0], productoMenu);
                    menu.add(productoMenu);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void cargarCombos(File archivoCombos) throws ProductoRepetidoException {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(archivoCombos));
            String line; 
    
            while ((line = reader.readLine()) != null);
    
            reader.close();
    
            String[] elements = line.split("\n");
    
            int index = elements.length-1;
    
            for (int i=0;i<=index;i++){
    
                String [] splited = elements[i].split(";");
    
                int indexS = splited.length-1;
    
                // Verifica si el combo ya existe antes de agregarlo
                if (combosHashed.get(splited[0]) != null) {
                    throw new ProductoRepetidoException("Combo repetido: " + splited[0]);
                }
    
                Combo combo = new Combo(splited[0],Integer.parseInt(splited[1]));
    
                for (int j = 1; j<=indexS;j++){
    
                    Producto itemCombo = menuHashed.get(splited[j]);
    
                    combo.agregarItemACombo(itemCombo);
    
                }
    
                // Agrega el combo al hashmap y a la lista
                combosHashed.put(splited[0], combo);
                combos.add(combo);
            }
    
        }
    
        catch (IOException e){
            System.out.println(e);
        }
    }
    
}
