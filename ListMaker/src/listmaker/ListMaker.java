package listmaker;

import tools.XMLMaker;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import userinterface.GUI;

public class ListMaker {

    public static void main(String[] args) {
        long tiempoInicio= System.currentTimeMillis();
        
//        XMLMaker xml= new XMLMaker();
//        xml.setDestinationPath("..");
//        Element e= xml.createNode(null, "webos", null);
//        xml.addAttribute(e, "valorXD", null);
//        Element e2= xml.createNode(e, "adentro", null);
//        xml.createNode(e2, "masadentro", "ertertert");
//        xml.write("Prueba");

        
        //xml.read(new MyFile().select("xml"));
        //"C:\\Users\\emili\\Documents\\GitHub\\CreadorDeLista\\Nombre.xml"
        
        /*String nom1= "Emanuelle";
        for(ArrayList<Object> variantes: mapaNombres){
            if(variantes.contains(nom1)){
                System.out.println("Coincide");
                break;
            }
        }*/
        
        GUI gui= new GUI();
        gui.setVisible(true);
        
//        NameAnalyzer analizador= new NameAnalyzer();
//        String g=analizador.analyze("MARco antonio Guerra CAsTellanos cholo");
//        System.out.println(g);
        
        
        long tiempoFinal= System.currentTimeMillis();
        System.out.println("Ejecución: "+(tiempoFinal-tiempoInicio));
    }
    
}
