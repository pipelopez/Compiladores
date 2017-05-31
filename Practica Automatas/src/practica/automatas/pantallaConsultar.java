/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.automatas;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel
 */
public class pantallaConsultar extends javax.swing.JFrame {

    /**
     * Creates new form pantallaConsultar
     */
    private Vector index;
    private automata autOriginal;
    private DefaultTableModel modelo;
    private automata autDete;

    public Vector[][] trans;
    public int es = 0;

    public pantallaConsultar(Vector b, String n, String conv) {
        initComponents();
        index = b;
        TAestado.setText(conv);
        jTable2.getTableHeader().setVisible(false);

        //Seleccionar el automata consultado
        Iterator p = index.iterator();
        while (p.hasNext()) {
            automata s = (automata) p.next();
            String r = s.getNombre();
            if (r.equals(n)) {
                autOriginal = s;
                break;
            }
        }

      //cargar el automata consultado
        modelo = new DefaultTableModel(autOriginal.getEstados().size() + 1, autOriginal.getSimbolos().size() + 2);
        modelo.setValueAt(null, 0, 0);
        Vector trans[][] = autOriginal.getTransiciones();

        for (int i = 0; i < autOriginal.getSimbolos().size(); i++) {
            Vector k = autOriginal.getSimbolos();
            String s = k.get(i).toString();
            modelo.setValueAt(s, 0, i + 1);

        }

        for (int i = 0; i < autOriginal.getEstados().size(); i++) {
            estado s = (estado) autOriginal.getEstados().get(i);
            modelo.setValueAt(s.getIcono(), i + 1, 0);

            for (int j = 0; j < autOriginal.getSimbolos().size(); j++) {
                modelo.setValueAt(trans[i + 1][j + 1], i + 1, j + 1);
            }

            if (s.isInicial()) {
                if (s.isAceptacion()) {

                    modelo.setValueAt(1, i + 1, autOriginal.getSimbolos().size() + 1);
                } else {

                    modelo.setValueAt(0, i + 1, autOriginal.getSimbolos().size() + 1);
                }
            } else {
                if (s.isAceptacion()) {

                    modelo.setValueAt(1, i + 1, autOriginal.getSimbolos().size() + 1);
                } else {

                    modelo.setValueAt(0, i + 1, autOriginal.getSimbolos().size() + 1);
                }
            }

        }
        jTable1.getTableHeader().setVisible(false);
        jTable1.setModel(modelo);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnConvertir = new javax.swing.JButton();
        btnEvaluarHilera = new javax.swing.JButton();
        btnMinimizar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TAestado = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        TAdes = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        btnGenerarHilera = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reductor de Automatas");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Automata No Deterministico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Automata Deterministico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(300, 300, 300))
        );

        btnConvertir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnConvertir.setText("Convertir");
        btnConvertir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvertirActionPerformed(evt);
            }
        });

        btnEvaluarHilera.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEvaluarHilera.setText("Evaluar Hilera");
        btnEvaluarHilera.setEnabled(false);
        btnEvaluarHilera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEvaluarHileraActionPerformed(evt);
            }
        });

        btnMinimizar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMinimizar.setText("Simplificar");
        btnMinimizar.setEnabled(false);
        btnMinimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinimizarActionPerformed(evt);
            }
        });

        TAestado.setEditable(false);
        TAestado.setColumns(20);
        TAestado.setRows(5);
        jScrollPane3.setViewportView(TAestado);

        TAdes.setEditable(false);
        TAdes.setColumns(20);
        TAdes.setRows(5);
        jScrollPane5.setViewportView(TAdes);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("\" * \" Estado inicial");

        btnGenerarHilera.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGenerarHilera.setText("Generar Hilera");
        btnGenerarHilera.setEnabled(false);
        btnGenerarHilera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarHileraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(jLabel4))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEvaluarHilera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConvertir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMinimizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGenerarHilera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(btnConvertir)
                        .addGap(5, 5, 5)
                        .addComponent(btnMinimizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEvaluarHilera)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenerarHilera))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnConvertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvertirActionPerformed
        btnConvertir.setEnabled(false);

        autOriginal = autOriginal.convierteDeter(autOriginal, jTable1, TAdes);        
        autDete = autOriginal.getAutMin();
        int ss = autDete.getEstados().size() + 1;
        trans = autDete.getTransiciones();
        modelo = new DefaultTableModel(ss, autOriginal.getSimbolos().size() + 1);
        modelo.setValueAt(null, 0, 0);

        for (int k = 0; k < ss; k++) {
            for (int ll = 0; ll < autOriginal.getSimbolos().size() + 1; ll++) {
                if (trans[k][ll] != null) {
                    if ((k == 0 && ll != 0) || (ll == 0 && k != 0) || (ll == autOriginal.getSimbolos().size())) {
                        modelo.setValueAt(trans[k][ll].get(0), k, ll);
                    } else {
                        modelo.setValueAt(trans[k][ll], k, ll);
                    }
                }
            }
        }

        jTable2.setModel(modelo);
        btnMinimizar.setEnabled(true);


    }//GEN-LAST:event_btnConvertirActionPerformed

    private void btnEvaluarHileraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEvaluarHileraActionPerformed
        try {
            String hilera = JOptionPane.showInputDialog("Ingrese la hilera que requiere verificación:", "");
            this.TAdes.setText(autOriginal.evaluarHilera(hilera, autDete));
        } catch (Exception e) {

        }
    }//GEN-LAST:event_btnEvaluarHileraActionPerformed

    public void setTA(String a) {
        TAdes.setText(a);
    }
    private void btnMinimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinimizarActionPerformed

        String mod = autOriginal.simplificarAutomata(autOriginal, jTable2);
        autDete = autOriginal.getAutMin();
        TAdes.setText(mod);
        btnMinimizar.setEnabled(false);
        btnEvaluarHilera.setEnabled(true);
        this.btnGenerarHilera.setEnabled(true);

    }//GEN-LAST:event_btnMinimizarActionPerformed

    private void btnGenerarHileraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarHileraActionPerformed
        this.TAdes.setText(autOriginal.genHilera(autDete));
    }//GEN-LAST:event_btnGenerarHileraActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(pantallaConsultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pantallaConsultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pantallaConsultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pantallaConsultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantallaConsultar(null, "", "").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea TAdes;
    private javax.swing.JTextArea TAestado;
    private javax.swing.JButton btnConvertir;
    private javax.swing.JButton btnEvaluarHilera;
    private javax.swing.JButton btnGenerarHilera;
    private javax.swing.JButton btnMinimizar;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
