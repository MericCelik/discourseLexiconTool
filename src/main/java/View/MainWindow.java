/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DLVTReader.readerDLVT;
import core.Annotation;
import core.Span;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultCaret;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @author Murathan
 */
public class MainWindow extends javax.swing.JFrame {

    private HashMap<String, ArrayList<String>> connectiveSenseMap;
    private HashMap<String, Set<String>> senseConnectiveMap;

    private HashMap<String, ArrayList<Annotation>> connectiveAnnotationMap;
    private HashMap<String, Integer> connectiveNumberofAnnotation;

    private readerDLVT reader;
    private Image im = Toolkit.getDefaultToolkit().getImage("youtube.png");

    public MainWindow() throws IOException, SAXException, ParserConfigurationException {

        reader = new readerDLVT("testing.xml");
        connectiveSenseMap = reader.getConnectiveSenseMap();
        connectiveAnnotationMap = reader.getConnectiveAnnotationMap();
        connectiveNumberofAnnotation = reader.getConnectiveNumberofAnnotation();
        senseConnectiveMap = reader.getSenseConnectiveMap();
        initComponents();

        legendLabel.setText("<html><font  color=\"black\"><b> <ul> <li> The connectives are underlined </li> <br />"
                + " <li> The second argument of the discourse relations are written in bold  </li> <br /> </b> </html>");
    }

    public void initWithNewFile(String dir) throws ParserConfigurationException, SAXException, IOException {

        System.out.println("View.MainWindow.initWithNewFile()");
        reader = new readerDLVT(dir);
        connectiveSenseMap = reader.getConnectiveSenseMap();
        connectiveAnnotationMap = reader.getConnectiveAnnotationMap();
        connectiveNumberofAnnotation = reader.getConnectiveNumberofAnnotation();
        senseConnectiveMap = reader.getSenseConnectiveMap();
        initComponents();

        legendLabel.setText("<html><font  color=\"black\"><b> <ul> <li> The connectives are underlined </li> <br />"
                + " <li> The second argument of the discourse relations are written in bold  </li> <br /> </b> </html>");

    }

    /**
     * Creates new form MainWindow
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        connBasedonSenseDialog = new javax.swing.JDialog();
        conSenseList_pane1 = new javax.swing.JScrollPane();
        conSenseList = new javax.swing.JList<>();
        legendDialog = new javax.swing.JDialog();
        legendLabel = new javax.swing.JLabel();
        allAnnotationDialogue = new javax.swing.JDialog();
        allAnnotationScrollPane = new javax.swing.JScrollPane();
        allAnnotationPane = new javax.swing.JTextPane();
        openFileDialog = new javax.swing.JDialog();
        jFileChooser1 = new javax.swing.JFileChooser();
        searchButton = new javax.swing.JButton();
        mainScrollPane = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        listScrollPane = new javax.swing.JScrollPane();
        JList_connective = new javax.swing.JList<>();
        searchField = new javax.swing.JTextField();
        legendButton = new javax.swing.JButton();
        conInfoLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        connBasedonSenseDialog.setMinimumSize(new java.awt.Dimension(200, 400));

        conSenseList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        conSenseList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                conSenseListMouseClicked(evt);
            }
        });
        conSenseList_pane1.setViewportView(conSenseList);

        javax.swing.GroupLayout connBasedonSenseDialogLayout = new javax.swing.GroupLayout(connBasedonSenseDialog.getContentPane());
        connBasedonSenseDialog.getContentPane().setLayout(connBasedonSenseDialogLayout);
        connBasedonSenseDialogLayout.setHorizontalGroup(
            connBasedonSenseDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(conSenseList_pane1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );
        connBasedonSenseDialogLayout.setVerticalGroup(
            connBasedonSenseDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(conSenseList_pane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
        );

        legendDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        legendDialog.setTitle("Legend");
        legendDialog.setAlwaysOnTop(true);
        legendDialog.setMinimumSize(new java.awt.Dimension(450, 200));

        legendLabel.setText("jLabel1");

        javax.swing.GroupLayout legendDialogLayout = new javax.swing.GroupLayout(legendDialog.getContentPane());
        legendDialog.getContentPane().setLayout(legendDialogLayout);
        legendDialogLayout.setHorizontalGroup(
            legendDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(legendDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(legendDialogLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(legendLabel)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        legendDialogLayout.setVerticalGroup(
            legendDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(legendDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(legendDialogLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(legendLabel)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        allAnnotationDialogue.setMinimumSize(new java.awt.Dimension(700, 300));

        DefaultCaret caret = (DefaultCaret)allAnnotationPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        allAnnotationScrollPane.setViewportView(allAnnotationPane);

        javax.swing.GroupLayout allAnnotationDialogueLayout = new javax.swing.GroupLayout(allAnnotationDialogue.getContentPane());
        allAnnotationDialogue.getContentPane().setLayout(allAnnotationDialogueLayout);
        allAnnotationDialogueLayout.setHorizontalGroup(
            allAnnotationDialogueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allAnnotationDialogueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(allAnnotationScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
                .addContainerGap())
        );
        allAnnotationDialogueLayout.setVerticalGroup(
            allAnnotationDialogueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(allAnnotationScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
        );

        openFileDialog.setMinimumSize(new java.awt.Dimension(650, 300));
        openFileDialog.setPreferredSize(new java.awt.Dimension(654, 400));
        openFileDialog.setSize(new java.awt.Dimension(700, 400));

        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout openFileDialogLayout = new javax.swing.GroupLayout(openFileDialog.getContentPane());
        openFileDialog.getContentPane().setLayout(openFileDialogLayout);
        openFileDialogLayout.setHorizontalGroup(
            openFileDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
            .addGroup(openFileDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE))
        );
        openFileDialogLayout.setVerticalGroup(
            openFileDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(openFileDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Discourse Lexicon Tool");
        setIconImage(im);
        setMinimumSize(new java.awt.Dimension(500, 300));
        setName("mainFrame"); // NOI18N

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        jTextPane1.setEditable(false);
        caret = (DefaultCaret)jTextPane1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        jTextPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextPane1MouseClicked(evt);
            }
        });
        mainScrollPane.setViewportView(jTextPane1);

        Collator trCollator = Collator.getInstance(new Locale("tr", "TR"));
        ArrayList<String> connectiveList = new ArrayList<> (connectiveSenseMap.keySet());
        Collections.sort(connectiveList, trCollator);
        JList_connective.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = connectiveList.toArray(new String[connectiveList.size()]);
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        JList_connective.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                JList_connectiveValueChanged(evt);
            }
        });
        listScrollPane.setViewportView(JList_connective);

        searchField.setText("Search connective..");
        searchField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchFieldMouseClicked(evt);
            }
        });
        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });

        legendButton.setText("Legend");
        legendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legendButtonActionPerformed(evt);
            }
        });

        jButton1.setText("See All Annotations");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jMenuBar1.setAutoscrolls(true);

        jMenu1.setText("File");

        jMenuItem1.setText("Open File");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(legendButton)
                .addGap(38, 38, 38))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(listScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(searchButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addComponent(conInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButton1))
                    .addComponent(mainScrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(legendButton)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(conInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(mainScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        String searchToken = searchField.getText();
        JList_connective.setSelectedValue(searchToken, true);
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

    private void legendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_legendButtonActionPerformed
        // TODO add your handling code here:
        legendDialog.setVisible(true);

    }//GEN-LAST:event_legendButtonActionPerformed

    private void searchFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchFieldMouseClicked
        // TODO add your handling code here:
        searchField.setText("");
    }//GEN-LAST:event_searchFieldMouseClicked

    private void JList_connectiveValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_JList_connectiveValueChanged
        // TODO add your handling code here:
        String chosenConnective = (String) JList_connective.getSelectedValue();
        int noOfAnno = connectiveNumberofAnnotation.get(chosenConnective);
        jButton1.setEnabled(true);
        Random randGenerator = new Random();
        Annotation chosenAnnotation;
        ArrayList<String> senseList = this.connectiveSenseMap.get(chosenConnective);
        String conInfo = "<html> <font  face=\"verdana\" color=\"black\"><b>" + "The connective <i>" + chosenConnective + "</i> is annotated <u>";
        if (senseList.size() == 1) {
            conInfo = conInfo + noOfAnno + " </u> times. It conveys only one sense (Unambiguous)</b></font>";
        } else {
            conInfo = conInfo + noOfAnno + "</u> times. It conveys <u>" + senseList.size() + "</u>  different senses." + "</Strong></font>";
        }
        conInfoLabel.setText(conInfo);

        String output = "<html> <ol>";
        for (String str : senseList) {
            String[] senseTokens = str.split(":");
            output = output + "<li> "; // + str + </li> ";
            for (String token : senseTokens) {
                String tokenTmp = token;
                int len = token.length() - tokenTmp.replaceAll(" ", "").length();
                if (len > 1) {
                    token = token.replaceAll(" ", "-");
                    token = token.substring(1);
                    System.out.println(token + " " + len);
                }
                output = output + "<font face=\"verdana\" color=\"blue\"><u>  " + token + "</u></font>" + " : ";
            }
            output = output.substring(0, output.length() - 3);
            output = output + "<br /> </li> ";
            int randomNoForAnnotation = randGenerator.nextInt(connectiveAnnotationMap.get(chosenConnective).size());
            TreeMap<Integer, Span> argMapforPrettyPrint = connectiveAnnotationMap.get(chosenConnective).get(randomNoForAnnotation).getArgMapforPrettyPrint();
            for (Integer i : argMapforPrettyPrint.keySet()) {
                String text = argMapforPrettyPrint.get(i).getText();
                if (argMapforPrettyPrint.get(i).getBelongsTo().equalsIgnoreCase("arg1")) {
                    output = output + "<font face=\"verdana\" color=\"black\"><em>" + " " + text + "</em></font>";
                } else if (argMapforPrettyPrint.get(i).getBelongsTo().equalsIgnoreCase("arg2")) {
                    output = output + "<font face=\"verdana\" color=\"black\"><b>" + " " + text + "</b></font>";
                } else if (argMapforPrettyPrint.get(i).getBelongsTo().equalsIgnoreCase("conn")) {
                    output = output + "<font face=\"verdana\" color=\"black\">" + " <u>" + text + "</u></font>";
                } else if (argMapforPrettyPrint.get(i).getBelongsTo().equalsIgnoreCase("mod")) {
                    output = output + "<font face=\"verdana\" color=\"black\">" + " " + text + "</font>";
                }
            }
            output = output + "<br />" + "<br />";
        }
        output = output + "</ol></html>";
        jTextPane1.setContentType("text/html");
        jTextPane1.setText(output);
    }//GEN-LAST:event_JList_connectiveValueChanged

    private void jTextPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextPane1MouseClicked
        // TODO add your handling code here:

        String selectedText = jTextPane1.getSelectedText();
        System.out.println(selectedText);
        if (selectedText != null) {
            selectedText = selectedText.replaceAll("-", " ");
        }
        System.out.println(selectedText);

        Set<String> senseSet = senseConnectiveMap.keySet();

        for (String str : senseSet) {
            if (selectedText != null && str.contains(selectedText)) {

                HashSet<String> conSet = (HashSet<String>) senseConnectiveMap.get(str);
                conSenseList.setModel(new javax.swing.AbstractListModel<String>() {
                    String[] strings = conSet.toArray(new String[conSet.size()]);

                    public int getSize() {
                        return strings.length;
                    }

                    public String getElementAt(int i) {
                        return strings[i];
                    }
                });
                connBasedonSenseDialog.setVisible(true);
            }
        }
    }//GEN-LAST:event_jTextPane1MouseClicked

    private void conSenseListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conSenseListMouseClicked
        // TODO add your handling code here:
        String selectedCon = (String) conSenseList.getSelectedValue();
        JList_connective.setSelectedValue(selectedCon, true);
        connBasedonSenseDialog.setVisible(false);
    }//GEN-LAST:event_conSenseListMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String chosenConnective = (String) JList_connective.getSelectedValue();
        int noOfAnno = connectiveNumberofAnnotation.get(chosenConnective);

        Random randGenerator = new Random();
        Annotation chosenAnnotation;
        ArrayList<String> senseList = this.connectiveSenseMap.get(chosenConnective);
        String conInfo = "<html> <font  face=\"verdana\" color=\"black\"><b>" + "The connective <i>" + chosenConnective + "</i> is annotated <u>";
        if (senseList.size() == 1) {
            conInfo = conInfo + noOfAnno + " </u> times. It conveys only one sense (Unambiguous)</b></font>";
        } else {
            conInfo = conInfo + noOfAnno + "</u> times. It conveys <u>" + senseList.size() + "</u>  different senses." + "</Strong></font>";
        }
        conInfoLabel.setText(conInfo);

        String output = "<html> <ol>";
        for (String str : senseList) {
            String[] senseTokens = str.split(":");
            output = output + "<li> "; // + str + </li> ";
            for (String token : senseTokens) {
                String tokenTmp = token;
                int len = token.length() - tokenTmp.replaceAll(" ", "").length();
                if (len > 1) {
                    token = token.replaceAll(" ", "-");
                    token = token.substring(1);
                    System.out.println(token + " " + len);
                }
                output = output + "<font face=\"verdana\" color=\"blue\"><u>  " + token + "</u></font>" + " : ";
            }
            output = output.substring(0, output.length() - 3);
            output = output + "</li>  <br /> <ul>";
            //  int randomNoForAnnotation = randGenerator.nextInt(connectiveAnnotationMap.get(chosenConnective).size());
            for (Annotation anno : connectiveAnnotationMap.get(chosenConnective)) {
                output = output + "<li>";
                TreeMap<Integer, Span> argMapforPrettyPrint = anno.getArgMapforPrettyPrint();

                for (Integer i : argMapforPrettyPrint.keySet()) {
                    String text = argMapforPrettyPrint.get(i).getText();
                    if (argMapforPrettyPrint.get(i).getBelongsTo().equalsIgnoreCase("arg1")) {
                        output = output + "<font face=\"verdana\" color=\"black\">" + " " + text + "</font>";
                    } else if (argMapforPrettyPrint.get(i).getBelongsTo().equalsIgnoreCase("arg2")) {
                        output = output + "<font face=\"verdana\" color=\"black\"><b>" + " " + text + "</b></font>";
                    } else if (argMapforPrettyPrint.get(i).getBelongsTo().equalsIgnoreCase("conn")) {
                        output = output + "<font face=\"verdana\" color=\"black\">" + " <u>" + text + "</u></font>";
                    } else if (argMapforPrettyPrint.get(i).getBelongsTo().equalsIgnoreCase("mod")) {
                        output = output + "<font face=\"verdana\" color=\"black\">" + " " + text + "</font>";
                    }
                }
                output = output + "</li> <br />" + "<br />";
            }
            output = output + "</ul>";
        }
        output = output + "</ol></html>";
        System.out.println(output);

        allAnnotationPane.setContentType("text/html");
        allAnnotationPane.setText(output);
        allAnnotationDialogue.setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
     //   openFileDialog.setVisible(true);


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
   /*     File selectedFile = jFileChooser1.getSelectedFile();
        String selectedFilePath = selectedFile.getAbsolutePath();
        System.out.println(selectedFilePath);

        try {
            openFileDialog.setVisible(false);

            initWithNewFile(selectedFilePath);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }//GEN-LAST:event_jFileChooser1ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainWindow().setVisible(true);
                } catch (IOException | SAXException | ParserConfigurationException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> JList_connective;
    private javax.swing.JDialog allAnnotationDialogue;
    private javax.swing.JTextPane allAnnotationPane;
    private javax.swing.JScrollPane allAnnotationScrollPane;
    private javax.swing.JLabel conInfoLabel;
    private javax.swing.JList<String> conSenseList;
    private javax.swing.JScrollPane conSenseList_pane1;
    private javax.swing.JDialog connBasedonSenseDialog;
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JButton legendButton;
    private javax.swing.JDialog legendDialog;
    private javax.swing.JLabel legendLabel;
    private javax.swing.JScrollPane listScrollPane;
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JDialog openFileDialog;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables

}
