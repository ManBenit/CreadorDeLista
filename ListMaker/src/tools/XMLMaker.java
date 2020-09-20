package tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLMaker{
    private Document doc;
    //private Element root;
    private String pathToSave;
    
    public XMLMaker(){
        try{
            DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder= docFactory.newDocumentBuilder();
            doc= docBuilder.newDocument();
            
            //Create by default the root element
            //root= doc.createElement("rootnode");
            //doc.appendChild(root);
        }catch(ParserConfigurationException ex){
            new Message().showMessage(Message.ERROR, ex.toString());
        }
    }
    
    public void read(File file){
        try {
            //InputStream contentXML= new FileInputStream(file); //Funciona con el IS también
            DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder= docFactory.newDocumentBuilder();
            doc= docBuilder.parse(file); //contentXML
            doc.getDocumentElement().normalize(); //Coloca cada nodo en el árbol interno
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            new Message().showMessage(Message.ERROR, "Error al leer XML:\n"+ex);
        }
    }
    
    public ArrayList<Node> getNodes(String nodeName){
        ArrayList<Node> nodeList= new ArrayList<>();
        
        NodeList lista= doc.getElementsByTagName(nodeName);
        for(int i=0; i<lista.getLength(); i++)
            nodeList.add(lista.item(i));
        
        return nodeList;
    }
    
    //Method which gets a children list from a parent,
    //flag fullNode is: true->get all node, false->get only text content
    //It means, arraylist is Node type or String type, so it'll be casted on user class
    public ArrayList<Object> getChildNodes(Node parentNode, boolean fullNode){
        ArrayList<Object> childrenNodeList= new ArrayList<>();
        
        NodeList children= parentNode.getChildNodes();
        for(int i=0; i<children.getLength(); i++)
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE)
                if(fullNode)
                    childrenNodeList.add(children.item(i));
                else
                    childrenNodeList.add(children.item(i).getTextContent());
            
        return childrenNodeList;
    }
    
    //Write a new file
    public void write(String fileName){
        try{
            //Se escribe el contenido del XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathToSave+fileName+".xml"));
            transformer.transform(source, result);
        }catch(TransformerException ex){
            new Message().showMessage(Message.ERROR, ex.toString());
        }
    }
    
    //Write into existing file
    public void load(){
        
    }
    
    public Element createNode(Element parent, String name, String content){
        //if parent is null, then it is flying node (direct at document, out of root)
        //if name is null, error
        //if content is null, it is autoclosed node
        
        if(name==null){
            new Message().showMessage(Message.ERROR, "El nodo debe tener un nombre");
            return null;
        }
        else{
            if(parent==null){
                Element el= doc.createElement(name);
                if(content!=null) el.setTextContent(content);
                doc.appendChild(el);
                return el;
            }
            else{
                Element el = doc.createElement(name);
                if(content!=null) el.setTextContent(content);
                parent.appendChild(el);
                return el;
            }
        }
    }
    
    public void addAttribute(Element container, String attrName, String value){
        Attr attr = doc.createAttribute(attrName);
        attr.setValue(value);
        container.setAttributeNode(attr);
    }
    
    //Necessary to specify where files will be located, call before encrypt or decrypt
    public void setDestinationPath(String path){
        StringBuilder sb= new StringBuilder(path);
        if(System.getProperty("os.name").equals("Windows"))
            sb.append("\\");
        else
            sb.append("/");
        pathToSave= sb.toString();
    }
    
}