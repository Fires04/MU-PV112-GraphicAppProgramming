package project2;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;

/**
 *
 * @author Milos
 */
public class MainWindow extends javax.swing.JFrame {

    private GLProfile profile;
    private GLJPanel canvas;
    
    private Scene scene;
    
    public MainWindow() {
        initComponents();
        
        profile = GLProfile.get(GLProfile.GL2);
        
        canvas = new GLJPanel(new GLCapabilities(profile));
        
        CanvasPanel.add(canvas,BorderLayout.CENTER);
        
        scene = new Scene();
        
        canvas.addGLEventListener(scene);
        
        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start();
        
        scene.setAzimuth(AzimuthSlider.getValue());
        scene.setAltitude(AltitudeSlider.getValue());
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Splitter = new javax.swing.JSplitPane();
        GuiPanel = new javax.swing.JPanel();
        AzimuthLabel = new javax.swing.JLabel();
        AzimuthSlider = new javax.swing.JSlider();
        AltitudeLabel = new javax.swing.JLabel();
        AltitudeSlider = new javax.swing.JSlider();
        CanvasPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Splitter.setDividerLocation(600);

        GuiPanel.setLayout(new javax.swing.BoxLayout(GuiPanel, javax.swing.BoxLayout.Y_AXIS));

        AzimuthLabel.setText("Azimuth:");
        AzimuthLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GuiPanel.add(AzimuthLabel);

        AzimuthSlider.setMajorTickSpacing(36);
        AzimuthSlider.setMaximum(360);
        AzimuthSlider.setPaintTicks(true);
        AzimuthSlider.setToolTipText("Azimuth");
        AzimuthSlider.setAlignmentX(0.0F);
        AzimuthSlider.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        AzimuthSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                AzimuthSliderStateChanged(evt);
            }
        });
        GuiPanel.add(AzimuthSlider);

        AltitudeLabel.setText("Altitude:");
        AltitudeLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GuiPanel.add(AltitudeLabel);

        AltitudeSlider.setMajorTickSpacing(8);
        AltitudeSlider.setMaximum(89);
        AltitudeSlider.setPaintTicks(true);
        AltitudeSlider.setToolTipText("Altitude");
        AltitudeSlider.setAlignmentX(0.0F);
        AltitudeSlider.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        AltitudeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                AltitudeSliderStateChanged(evt);
            }
        });
        GuiPanel.add(AltitudeSlider);

        Splitter.setRightComponent(GuiPanel);

        CanvasPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        CanvasPanel.setVerifyInputWhenFocusTarget(false);
        CanvasPanel.setLayout(new java.awt.BorderLayout());
        Splitter.setLeftComponent(CanvasPanel);

        getContentPane().add(Splitter, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AzimuthSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_AzimuthSliderStateChanged
        scene.setAzimuth(AzimuthSlider.getValue());
    }//GEN-LAST:event_AzimuthSliderStateChanged

    private void AltitudeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_AltitudeSliderStateChanged
        scene.setAltitude(AltitudeSlider.getValue());
    }//GEN-LAST:event_AltitudeSliderStateChanged

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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AltitudeLabel;
    private javax.swing.JSlider AltitudeSlider;
    private javax.swing.JLabel AzimuthLabel;
    private javax.swing.JSlider AzimuthSlider;
    private javax.swing.JPanel CanvasPanel;
    private javax.swing.JPanel GuiPanel;
    private javax.swing.JSplitPane Splitter;
    // End of variables declaration//GEN-END:variables
}