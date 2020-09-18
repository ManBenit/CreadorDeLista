package dictionaries;

import java.io.File;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import tools.XMLMaker;

public final class Dictionary {
    private XMLMaker dic;
    private ArrayList<ArrayList<Object>> map;
    
    private Dictionary(String path){
        map= new ArrayList<>();
        dic= new XMLMaker();
        dic.read(new File(path));
    }
    
    private Dictionary(){
        dic= new XMLMaker();
    }
    
    //Instance with xml dictionary loading
    public static Dictionary loadDictionary(String path){
        return new Dictionary(path);
    }
    
    //Instance only
    public static Dictionary getInstance(){
        return new Dictionary();
    }
    
    private void getData(String dicNodeName){
        //Get all 'nombre' nodes
        ArrayList<Node> nodosNombre= dic.getNodes(dicNodeName);
        
        //From each 'nombre' node, get 'variante' nodes and make the map
        for(Node n: nodosNombre)
            map.add(dic.getChildNodes(n, false)); //manejo en forma de String
    }
    
    public ArrayList<ArrayList<Object>> getDictionary(String nodeName){
        getData(nodeName);
        return map;
    }
    
    public void addName(String name){
        XMLMaker xml= new XMLMaker();
//        xml.setDestinationPath("..");
//        Element e= xml.createNode(null, "nombre", null);
//        xml.createNode(e, "variante", name);
//        xml.write("NuevoNombre");

        
    }
    
    public void addLastName(String lastName){
//        Element e= dic.createNode("apellido", null);
//        dic.read(readFrom("Apellido.xml"));
//        dic.addChildNode(e, "variante", lastName);
    }
    
    private File readFrom(String name){
        StringBuilder sb= new StringBuilder("..");
        if(System.getProperty("os.name").equals("Windows"))
            sb.append("\\");
        else
            sb.append("/");
        
        return new File(sb.toString()+name);
    }
}
