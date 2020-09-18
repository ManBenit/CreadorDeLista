package userinterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import listmaker.NameAnalyzer;

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        areaListaPrevia.setColumns(20);
        areaListaPrevia.setRows(5);
        jScrollPane1.setViewportView(areaListaPrevia);

        btnCrear.setText("Crear lista");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(477, Short.MAX_VALUE)
                .addComponent(btnCrear)
                .addGap(118, 118, 118))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(btnCrear)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void actionPerformed(ActionEvent evt){
        Object pressed= evt.getSource();
        
        if(pressed==btnCrear){
            String[] array= areaListaPrevia.getText().split("\n");
            analizarLista(array);
        }
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaListaPrevia;
    private javax.swing.JButton btnCrear;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void escuchadores(){
        btnCrear.addActionListener(this);
    }
    
    private void analizarLista(String[] nombres){
        ArrayList<String> listaCorrecta= new ArrayList<>();
        NameAnalyzer analizador= new NameAnalyzer();
        for(String nom: nombres)
            listaCorrecta.add(analizador.analyze(nom));
        
        for(String s: listaCorrecta)
            System.out.println(s);
    }
}
