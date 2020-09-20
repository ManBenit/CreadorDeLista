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
            boolean bndRev= false; //review flag because it'll be searched into both of dictionaries
            int nom_ap=0; //name or lastname: 1->name, 2->lastname
            
            //Name review
            for(ArrayList<Object> variantes: names){
                if(variantes.contains(namePart.toUpperCase())){
                    bndRev=true;
                    nom_ap=1;
                    break;
                }
            }
            
            
            //Lastname review
            if(!bndRev){
                for(ArrayList<Object> variantes: lastNames){
                    if(variantes.contains(namePart.toUpperCase())){
                        bndRev=true;
                        nom_ap=2;
                        break;
                    }
                }
            }
            
            //If it has not been checked, add the element at file
            if(!bndRev){
                String[] opciones= {"Nombre", "Apellido"};
                int tipo= new Message().enterInfoB(namePart.toUpperCase()+" es... ?", opciones);
                
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
