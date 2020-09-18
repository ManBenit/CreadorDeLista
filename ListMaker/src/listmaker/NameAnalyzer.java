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
        
        //Pair of [nX, String] or [aX, String] to order it after
        //ArrayList<String[]> pairToOrder= new ArrayList<>();
        
        String[] fnParts= fullName.split(" ");
        
        for(String namePart: fnParts){
            boolean bndRev= false; //review flag because it'll be searched into both of dictionaries
            
            //Name review
            for(ArrayList<Object> variantes: names){
                if(variantes.contains(namePart.toUpperCase())){
                    //Format to capital letter
                    StringBuilder parte= new StringBuilder(namePart.toLowerCase());
                    parte.replace(0, 1, String.valueOf(parte.charAt(0)).toUpperCase());
                    parte.append(" ");
                    
                    nombres.append(parte.toString());
                    bndRev=true;
                    break;
                }
            }
            
            
            //Last name review
            if(!bndRev){
                for(ArrayList<Object> variantes: lastNames){
                    if(variantes.contains(namePart.toUpperCase())){
                        //Format to capital letter
                        StringBuilder parte= new StringBuilder(namePart.toLowerCase());
                        parte.replace(0, 1, String.valueOf(parte.charAt(0)).toUpperCase());
                        parte.append(" ");

                        apellidos.append(parte.toString());
                        bndRev=true;
                        break;
                    }
                }
            }
            
            if(!bndRev){
                String[] opciones= {"Nombre", "Apellido"};
                String tipo= new Message().enterInfo(namePart+" es... ?", opciones);
                
                if(tipo.equals("Nombre"))
                    Dictionary.getInstance().addName(namePart);
                else
                    Dictionary.getInstance().addLastName(namePart);
            }
        }//full name iteration
        
        return apellidos.append(nombres.toString()).toString();
    }
    
}
