package userinterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import listmaker.NameAnalyzer;
import tools.Message;
import tools.MyFile;

public class GUI extends JFrame implements ActionListener{

    public GUI(){
        setTitle("Creador de lista de asistencia para fiestas PIEP");
        initComponents();
        escuchadores();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        areaListaPrevia = new javax.swing.JTextArea();
        btnCrear = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        areaListaPrevia.setColumns(20);
        areaListaPrevia.setRows(5);
        jScrollPane1.setViewportView(areaListaPrevia);

        btnCrear.setText("Crear lista");

        btnLimpiar.setText("Limpiar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(251, 251, 251)
                .addComponent(btnLimpiar)
                .addGap(61, 61, 61)
                .addComponent(btnCrear)
                .addContainerGap(218, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar)
                    .addComponent(btnCrear))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void actionPerformed(ActionEvent evt){
        Object pressed= evt.getSource();
        
        if(pressed==btnCrear){
            String[] array= areaListaPrevia.getText().split("\n");
            analizarLista(array);
            new Message().showMessage(Message.INFO, "Lista exportada");
        }
        else if(pressed==btnLimpiar){
            areaListaPrevia.setText("");
        }
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaListaPrevia;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void escuchadores(){
        btnCrear.addActionListener(this);
        btnLimpiar.addActionListener(this);
    }
    
    private void analizarLista(String[] nombres){
        ArrayList<String> listaCorrecta= new ArrayList<>();
        NameAnalyzer analizador= new NameAnalyzer();
        for(String nom: nombres)
            listaCorrecta.add(analizador.analyze(nom));
        
        Collections.sort(listaCorrecta);
//        for(String s: listaCorrecta)
//            System.out.println(s);
        
        String path= makePath("../ListaExportada.txt");
        new MyFile().writeFile(path, listaCorrecta);
    }
    
    private String makePath(String previousPath){
        if(System.getProperty("os.name").equals("Windows"))
            return previousPath.replaceAll("/", "\\");
        else
            return previousPath;
    }
}
