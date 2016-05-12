/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.core.luciernaga.Programa;
import com.core.luciernaga.Luciernaga;
import com.core.luciernaga.Util;
import com.google.common.net.UrlEscapers;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author pichon
 */
public class ProgramaTest {
    
    public static int numLuciernagasAgrupadas = 0;
    private char[] palabra1, palabra2, palabra3, variables;
    private Random numAleatorio = new Random();
    private int[] numeros= {0,1,2,3,4,5,6,7,8,9};
    private int[] mapeo;
    private int valPalabra1;
    private int valPalabra2;
    private int valPalabra3;
    private int longP1, longP2, longP3, longVar;
    
    public ProgramaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        init("earth".toCharArray(), "uranus".toCharArray(), "saturn".toCharArray(), "".toCharArray());
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //EARTH + URANUS = SATURN
     @Test
//     @Ignore
     public void first() throws IOException, URISyntaxException {
         Programa p = new Programa();
         p.setPalabra1("EARTH".toCharArray());
         p.setPalabra2("URANUS".toCharArray());
         p.setPalabra3("SATURN".toCharArray());

         int n = 0;
         while(n < 1){
             p.programa();
             n++;
         }
         if (p.getLuciernagaSolucion() != null) {
             Util.info("Solucionado");
         }
     }

    @Test
    @Ignore
    public void second() throws IOException, URISyntaxException {
        if(Desktop.isDesktopSupported())
        {
                String complete = "http://chart.apis.google.com/chart?cht=lc&chs=200x125&chd=t:";
           String scape = UrlEscapers.urlFragmentEscaper().escape(complete);
           Desktop.getDesktop().browse(new URI(scape));           
        }
    }
    
     
     @Test
     @Ignore
     public void pruebaAcercar() {
         Programa p = new Programa();
         p.setLongVariable(10);
         int posicion, aux, numero = 0, num, iteracion;
         int[] luc1 = new int[]{1,2,9,4,5,6,7,8,3,0};
         int[] luc2 = new int[]{7,5,3,9,6,2,1,0,4,8};
         int[] lucAux;
         boolean bandera = true;
                 
         double distanciaAux, distancia;
         distancia = p.obtenerDistancia(luc1, luc2);
         System.out.println(distancia);
         System.out.println(Arrays.toString(luc1));
         System.out.println(Arrays.toString(luc2));
         
         for (int j = 0; j < 25; j++) {
            for (int i = 0; i < 10; i++) {
                iteracion = 0;
                lucAux = luc2;
                if(luc2[i] != luc1[i]){
                    if(luc2[i] < luc1[i]){
                        numero = 1;               
                    }
                    if(luc2[i] > luc1[i]){
                        numero = -1; 
                    }
                    num = luc2[i] + numero;
                    while( (bandera) && (iteracion < 3)){
                        posicion = buscarPos(num, luc2);
                        aux = luc2[i];
                        luc2[i] = luc2[posicion];
                        luc2[posicion] = aux;
 
                        distanciaAux = p.obtenerDistancia(luc1, luc2);
                        System.out.println(distanciaAux);
                 
                        if(distancia < distanciaAux){
                            //System.out.println("Se alejo");
                            luc2 = lucAux;
                            num = num + numero;
                        }
                        if(distancia > distanciaAux){
                            System.out.println("Se acerco");
                            System.out.println(Arrays.toString(luc1));
                            System.out.println(Arrays.toString(luc2));
                            distancia = distanciaAux;
                            bandera = false;
                        }
                        iteracion++;
                    }
                    bandera = true;
                }
             }
         }
     }

    
      public int[] mapeoLuciernaga(int cantVar, Random r){
        mapeo = new int[cantVar];
        for(int i = 0; i < cantVar; i++){
            mapeo[i] = numAleatorio.nextInt(10); 
        }
        encontrarValoresPalabras();
        
        int k=cantVar;
        int[] resultado=new int[cantVar];
        int res;
        int[] numeros= {0,1,2,3,4,5,6,7,8,9};
        for(int i=0;i<cantVar;i++){
            res= r.nextInt(k);            
            resultado[i]=numeros[res];
            numeros[res]=numeros[k-1];
            k--;
            
        }
        return resultado;
    }
      
      public int diferencia(int a, int b){
        int resultado = 0;
        if(a>b){
            resultado = a-b;
        }
        if(a<b){
            resultado = b-a;
        }
        return resultado;
    }
      
      public int[] elegirLuciernaga(int[] primera, int[] segunda){
          for (int i = 0; i < primera.length -2; i++) {
              int dif = diferencia(primera[i], segunda[i]);
              int difNext = diferencia(primera[i], segunda[i+1]);
              if (difNext < dif) {
                  int aux = segunda[i];
                  segunda[i] = segunda[i+1];
                  segunda[i+1] = aux;
              }
          }
          int dif = diferencia(primera[primera.length-1], segunda[segunda.length - 1]);
          int difNext = diferencia(primera[primera.length - 1], segunda[0]);
          if (difNext < dif) {
                  int aux = segunda[segunda.length - 1];
                  segunda[segunda.length - 1] = segunda[0];
                  segunda[0] = aux;
              }
          return segunda;
      }
      
      public void init(char[] p1, char[] p2, char[] p3, char[] var){
        palabra1 = p1;
        palabra2 = p2;
        palabra3 = p3;
        variables = var;
        longP1 = palabra1.length;
        longP2 = palabra2.length;
        longP3 = palabra3.length;
        longVar = variables.length;
    }
      
      public void encontrarValoresPalabras(){
          
            int peso;
            int valPalabra1 =0;
            int valPalabra2 =0;
            int valPalabra3 =0;            
            
            peso = longP1 - 1;
            for(int i = 0; i < longP1; i++){
                for(int j = 0; j < longVar; j++){
                    if(palabra1[i] == variables[j]){
                        valPalabra1 = (int) (valPalabra1 + mapeo[j]*Math.pow(10, peso));
                        break;
                    } 
                }
                peso = peso - 1;
            }
            
            peso = longP2 - 1;
            for(int i = 0; i < longP2; i++){
                for(int j = 0; j < longVar; j++){
                    if(palabra2[i] == variables[j]){
                        valPalabra2 = (int) (valPalabra2 + mapeo[j]*Math.pow(10, peso));
                        break;
                    } 
                }
                peso = peso - 1;
            }
            
            peso = longP3 - 1;
            for(int i = 0; i < longP3; i++){
                for(int j = 0; j < longVar; j++){
                    if(palabra3[i] == variables[j]){
                        valPalabra3 = (int) (valPalabra3 + mapeo[j]*Math.pow(10, peso));
                        break;
                    } 
                }
                peso = peso - 1;
            }
            
    }

    private int buscarPos(int num, int[] luc2) {
        int pos = 0;
        for (int j = 0; j < 10; j++) {
            if(luc2[j] == num){
                pos = j;
                break;
            }
        }
        return pos;
    }

      
}
