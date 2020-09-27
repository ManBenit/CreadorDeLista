package listmaker;

import dictionaries.Dictionary;
import java.util.ArrayList;
import tools.Message;

public class NameAnalyzer {
    private ArrayList<ArrayList<Object>> names;
    private ArrayList<ArrayList<Object>> lastNames;
    
    public NameAnalyzer(){
        StringBuilder sb= new StringBuilder("..");
        if(System.getProperty("os.name").equals("Windows"))
            sb.append("\\");
        else
            sb.append("/");
        
        names= Dictionary.loadDictionary(sb.toString()+"Nombre.xml").getDictionary("nombre");
        lastNames= Dictionary.loadDictionary(sb.toString()+"Apellido.xml").getDictionary("apellido");
    }
    
    public String analyze(String fullName){
        StringBuilder nombres= new StringBuilder("");
        StringBuilder apellidos= new StringBuilder("");
        
        String[] fnParts= fullName.split(" ");
        
        for(String namePart: fnParts){
            boolean bndConfName= false; //review flag because it'll be searched into both of dictionaries
            boolean bndConfLName= false;
            int nom_ap=0; //name or lastname: 1->name, 2->lastname
            
            //Name review
            for(ArrayList<Object> variantes: names){
                if(variantes.contains(namePart.toUpperCase())){
                    bndConfName=true;
                    //nom_ap=1;
                    break;
                }
            }
            
            
            //Lastname review
            for(ArrayList<Object> variantes: lastNames){
                if(variantes.contains(namePart.toUpperCase())){
                    bndConfLName=true;
                    //nom_ap=2;
                    break;
                }
            }
            
            
            if(!bndConfName && !bndConfLName){
                String[] opciones= {"Nombre", "Apellido", "Ambos"};
                int tipo= new Message().enterInfoB(namePart.toUpperCase()+" es... ?", opciones, "Registro de nuevo elemento de nombre");
                
                if(tipo==0){
                    nom_ap=1;
                    Dictionary.getInstance().addName(namePart.toUpperCase());
                    reloadDictionary(opciones[tipo].toLowerCase());
                }
                else if(tipo==1){
                    nom_ap=2;
                    Dictionary.getInstance().addLastName(namePart.toUpperCase());
                    reloadDictionary(opciones[tipo].toLowerCase());
                }
                else if(tipo==2){
                    Dictionary.getInstance().addName(namePart.toUpperCase());
                    reloadDictionary(opciones[tipo].toLowerCase());
                    Dictionary.getInstance().addLastName(namePart.toUpperCase());
                    reloadDictionary(opciones[tipo].toLowerCase());
                    
                    String[] partAs= {"Nombre", "Apellido"};
                    int t= new Message().enterInfoB(
                            "Y en "+fullName+", "+namePart.toUpperCase()+" es... ?", 
                            partAs,
                            "Función del elemento de nombre"
                    );

                    if(t==0)
                        nom_ap=1;
                    else
                        nom_ap=2;
                }
            }
            else if(!bndConfName && bndConfLName)
                nom_ap=2;
            else if(bndConfName && !bndConfLName)
                nom_ap=1;
            else if(bndConfName && bndConfLName){
                String[] opciones= {"Nombre", "Apellido"};
                int tipo= new Message().enterInfoB(
                        "En "+fullName+", "+namePart.toUpperCase()+" es... ?", 
                        opciones,
                        "Función del elemento de nombre"
                );
                
                if(tipo==0)
                    nom_ap=1;
                else
                    nom_ap=2;
            }
            
            //Finnaly make the rigth name form
            if(nom_ap==0)
                new Message().showMessage(Message.ERROR, "Ha ocurrido un error al elegir la opción");
            else{
                //Format to capital letter
                StringBuilder parte= new StringBuilder(namePart.toLowerCase());
                parte.replace(0, 1, String.valueOf(parte.charAt(0)).toUpperCase());
                parte.append(" ");

                if(nom_ap==1)
                    nombres.append(parte.toString());
                else
                    apellidos.append(parte.toString());
            }
        }//full name iteration
        
        return apellidos.append(nombres.toString()).toString();
    }
    
    private void reloadDictionary(String name){
        StringBuilder sb= new StringBuilder("..");
        if(System.getProperty("os.name").equals("Windows"))
            sb.append("\\");
        else
            sb.append("/");
        
        if(name.equals("nombre")){
            names= Dictionary.loadDictionary(sb.toString()+"Nombre.xml").getDictionary(name);
        }
        else{
            lastNames= Dictionary.loadDictionary(sb.toString()+"Apellido.xml").getDictionary(name);
        }
    }
}
