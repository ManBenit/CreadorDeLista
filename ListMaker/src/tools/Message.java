package tools;

import javax.swing.JOptionPane;

public class Message{
    public static final int INFO= 1;
    public static final int WARNING= 2;
    public static final int ERROR= 0;
    public static final int QUESTION= 3;
    
    public void showMessage(int type, String message){
        JOptionPane.showMessageDialog(null, message, "Application says:", type);
    }
    
    public void showMessage(int type, String message, String deQuien){
        JOptionPane.showMessageDialog(null, message, deQuien, type);
    }
    
    public String enterInfo(int type, String message, String title){
        return JOptionPane.showInputDialog(null, message, title, type);
    }
    
    //Show a ComboBox with options
    public String enterInfo(String message, Object[] options){
        Object ret= JOptionPane.showInputDialog(null, message, "Choose", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        return ret.toString();
    }
    
    /*Show a button for each element of options
    If any option is selected, the aprt will be placed into Nombre.xml by default*/
    public int enterInfoB(String message, Object[] options){
        int s=0;
        int seleccion = JOptionPane.showOptionDialog( null, message,
            "Application says:",JOptionPane.UNDEFINED_CONDITION,
            JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
            options,null);

        if (seleccion != -1)
           s= seleccion;
        
        return s;
    }
    
    public int selectYesNo(String message){
        int s=0;
        int seleccion = JOptionPane.showOptionDialog( null, message,
            "Application says:",JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
            new Object[] {"No", "Yes"},"Yes");

        if (seleccion != -1)
           s= seleccion;
        
        return s;
    }
    
    
    
}
