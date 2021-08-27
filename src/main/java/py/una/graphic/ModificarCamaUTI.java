/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.graphic;

/**
 *
 * @author Victor-Ayala
 */
public class ModificarCamaUTI extends javax.swing.JPanel {

    /**
     * Creates new form ModificarCamaUTI
     */
    public ModificarCamaUTI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelForm = new javax.swing.JPanel();
        panelEstado = new javax.swing.JPanel();
        labelNroCama = new javax.swing.JLabel();
        labelEstado = new javax.swing.JLabel();
        comboBoxEstado = new javax.swing.JComboBox<>();
        txtNroCama = new javax.swing.JTextField();
        panelBtn = new javax.swing.JPanel();
        btnModificar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        jLabel1.setText("MODIFICAR");
        panelTitulo.add(jLabel1);

        add(panelTitulo, java.awt.BorderLayout.PAGE_START);

        panelForm.setLayout(new java.awt.BorderLayout());

        labelNroCama.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelNroCama.setText("Nº de cama:");
        labelNroCama.setToolTipText("");

        labelEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelEstado.setText("Estado:");

        comboBoxEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        comboBoxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ocupado", "Desocupado" }));

        txtNroCama.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout panelEstadoLayout = new javax.swing.GroupLayout(panelEstado);
        panelEstado.setLayout(panelEstadoLayout);
        panelEstadoLayout.setHorizontalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstadoLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelEstado)
                    .addComponent(labelNroCama))
                .addGap(113, 113, 113)
                .addGroup(panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNroCama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        panelEstadoLayout.setVerticalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstadoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNroCama)
                    .addComponent(txtNroCama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEstado)
                    .addComponent(comboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        panelForm.add(panelEstado, java.awt.BorderLayout.PAGE_START);

        btnModificar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnModificar.setText("Modificar");
        panelBtn.add(btnModificar);

        panelForm.add(panelBtn, java.awt.BorderLayout.CENTER);

        add(panelForm, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> comboBoxEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelEstado;
    private javax.swing.JLabel labelNroCama;
    private javax.swing.JPanel panelBtn;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JTextField txtNroCama;
    // End of variables declaration//GEN-END:variables
}
