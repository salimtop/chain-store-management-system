package GUI;


import GUI.login;
import chainmanagement.Employee;
import chainmanagement.Message;
import chainmanagement.Store;
import java.awt.Color;
import java.awt.Component;
import static java.awt.event.MouseEvent.BUTTON1;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.border.DropShadowBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACK
 */
public class ceoPanel extends javax.swing.JFrame {

    /**
     * Creates new form ceoPanel
     */
      Employee infoPerson=new Employee();
      Employee firePerson=new Employee();
      Employee ceo;
      ArrayList<Employee> sv=new ArrayList<>();
      ArrayList<Employee> all= new ArrayList<>();
      ArrayList<Store> allStore= new ArrayList<>();
      Store refStore=new Store();
      ArrayList<Message> myMess = new ArrayList<>();
      Timer timer1 = new Timer();
      Timer timer2 = new Timer();
      Timer timer3 = new Timer();
      Timer slideTimer1 = new Timer();
      Timer slideTimer2 = new Timer();
      
      
      boolean in;
      boolean clicked;
    
      
      boolean new_message;
      boolean inbox_bool;
      boolean market_bool;      
      boolean sv_bool;
      boolean info_bool = true;
      
    
    
    public ceoPanel(Employee e) {
        
        
        initComponents();
        infoPerson=e;
        ceo = e;
        
        messTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        svTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //jTable3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      
        
        
        //inbox_init();
        
        jLabel16.setVisible(false);
        fireRes.setVisible(false);
        //addResult.setVisible(false);
        pos_info.setForeground(new Color(255,204,51));
        setBackground(new Color(0,0,0,0));
        jPanel1.setBackground(new Color(0,0,0,0));
        DropShadowBorder shadow = new DropShadowBorder();
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(true);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(true);
        shadow.setShadowSize(6);
        jPanel1.setBorder(shadow);
        
        fillInfo();
        
    }
    
    private void fillInfo(){
        svfireid.addItem("NONE");
        myMess = Message.getAll_Messages(infoPerson.getId());
        allStore=refStore.getAll();
        all=infoPerson.getAll();
        for(int i=0;i<all.size();i++){
            if(all.get(i).getId()!=infoPerson.getId() && all.get(i).getStatus().equalsIgnoreCase("sv")){
                sv.add(all.get(i));
                svfireid.addItem(all.get(i).getId()+"");
            }
        }
     
        /*addName.setText("");
        addSName.setText("");
        addPlace.setText("");
        addSalary.setText("");*/
        
        ceoID.setText(infoPerson.getId()+"");
        ceoName.setText(infoPerson.getName());
        ceoSName.setText(infoPerson.getSurname());
        ceoStart.setText(infoPerson.getStarting());
        ceoSalary.setText(infoPerson.getSalary()+"");
        
        DefaultTableModel svlist=(DefaultTableModel)svTable.getModel();
        for(int i=0;i<sv.size();i++){
            svlist.addRow(new Object[]{sv.get(i).getId(),
                                       sv.get(i).getName(),
                                       sv.get(i).getSurname()});
        }
        
        DefaultTableModel messList = (DefaultTableModel)messTable.getModel();
        for (int i = 0; i < myMess.size(); i++) {
            Message control = myMess.get(i);
            if(control.isChecked()){
                messList.addRow(new Object[]{
                ((Employee)infoPerson.get_item(myMess.get(i).getFromID())).getName(),
                myMess.get(i).getSubject()
                });      
            }
            else{
                messList.addRow(new Object[]{
                ("🔴"+((Employee)infoPerson.get_item(myMess.get(i).getFromID())).getName()).toString(),
                myMess.get(i).getSubject()
                });   
            }
            
        }
        
        all = infoPerson.getAll();
        for(int i=0;i<all.size();i++){
            if(infoPerson.getId()!=all.get(i).getId())
                sendID.addItem(all.get(i).getId()+"");
        }    
        
        //Fulfill Supervise CB
        allStore = refStore.getAll();
        for (int i = 0; i < allStore.size(); i++) {
            markList.addItem(allStore.get(i).getStoreID()+"/"+allStore.get(i).getName());
        }
        
        
        
    }
    
    private void setClear(){
        DefaultTableModel svlist=(DefaultTableModel)svTable.getModel();
        //svlist.setRowCount(0);                                            //for some reason this two are duplicating themselves 
        //svTable.removeRowSelectionInterval(0, svlist.getRowCount()-1);    //when used
        
        myMess = Message.getAll_Messages(infoPerson.getId());
        allStore=refStore.getAll();
        all=infoPerson.getAll();
        for(int i=0;i<all.size();i++){
            if(all.get(i).getId()!=infoPerson.getId() && all.get(i).getStatus().equalsIgnoreCase("sv")){
                sv.add(all.get(i));              
            }
        }
        
         DefaultTableModel messList = (DefaultTableModel)messTable.getModel();
        messList.setRowCount(0);
        for (int i = 0; i < myMess.size(); i++) {
            Message control = myMess.get(i);
            if(control.isChecked()){
                messList.addRow(new Object[]{
                ((Employee)infoPerson.get_item(myMess.get(i).getFromID())).getName(),
                myMess.get(i).getSubject()
                });      
            }
            else{
                messList.addRow(new Object[]{
                ("🔴"+((Employee)infoPerson.get_item(myMess.get(i).getFromID())).getName()).toString(),
                myMess.get(i).getSubject()
                });   
            }
        }
        
        
        /*for(int i=0;i<sv.size();i++){
            svlist.addRow(new Object[]{sv.get(i).getId(),
                                       sv.get(i).getName(),
                                       sv.get(i).getSurname()});
        }*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LogoutButton = new javax.swing.JLabel();
        rightButton = new javax.swing.JLabel();
        pos_info = new javax.swing.JLabel();
        pos_sv = new javax.swing.JLabel();
        pos_inbox = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        pos_market = new javax.swing.JLabel();
        jPanelSlider1 = new diu.swe.habib.JPanelSlider.JPanelSlider();
        info = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        ceoID = new javax.swing.JLabel();
        ceoName = new javax.swing.JLabel();
        ceoSName = new javax.swing.JLabel();
        ceoStart = new javax.swing.JLabel();
        ceoSalary = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        svList = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        svTable = new javax.swing.JTable();
        addWorker = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        addSalary = new javax.swing.JTextField();
        addSName = new javax.swing.JTextField();
        addName = new javax.swing.JTextField();
        addPlace = new javax.swing.JTextField();
        addSV = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        addResult = new javax.swing.JLabel();
        deleteWorker = new javax.swing.JPanel();
        svfireid = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        svfirecomp = new javax.swing.JLabel();
        svfirename = new javax.swing.JLabel();
        svfiresname = new javax.swing.JLabel();
        svfirebutton = new javax.swing.JButton();
        fireRes = new javax.swing.JLabel();
        inbox = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messTable = new javax.swing.JTable();
        showMessBut = new javax.swing.JButton();
        sendMessBut = new javax.swing.JButton();
        showMessage = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        showMess = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fromLabel = new javax.swing.JLabel();
        subjectLabel = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        marketList = new javax.swing.JPanel();
        markList = new javax.swing.JComboBox<>();
        supervise = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        sendMessage = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        sendID = new javax.swing.JComboBox<>();
        showID = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(879, 802));

        jPanel2.setBackground(new java.awt.Color(66, 133, 245));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Chain Marketing Management System CEO Panel");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 17, 642, 54));

        LogoutButton.setBackground(new java.awt.Color(48, 117, 233));
        LogoutButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        LogoutButton.setForeground(new java.awt.Color(255, 255, 255));
        LogoutButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogoutButton.setText("Logout");
        LogoutButton.setOpaque(true);
        LogoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LogoutButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LogoutButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                LogoutButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LogoutButtonMouseReleased(evt);
            }
        });
        jPanel2.add(LogoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, 93, 47));

        rightButton.setBackground(new java.awt.Color(48, 117, 233));
        rightButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rightButton.setForeground(new java.awt.Color(255, 255, 255));
        rightButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rightButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/right_arrow.png"))); // NOI18N
        rightButton.setOpaque(true);
        rightButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rightButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                rightButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                rightButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                rightButtonMouseReleased(evt);
            }
        });
        jPanel2.add(rightButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        pos_info.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pos_info.setForeground(new java.awt.Color(255, 255, 255));
        pos_info.setText("Info");
        jPanel2.add(pos_info, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, -1, -1));

        pos_sv.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pos_sv.setForeground(new java.awt.Color(255, 255, 255));
        pos_sv.setText("SV list");
        jPanel2.add(pos_sv, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, -1, -1));

        pos_inbox.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pos_inbox.setForeground(new java.awt.Color(255, 255, 255));
        pos_inbox.setText("Inbox");
        jPanel2.add(pos_inbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/warning.png"))); // NOI18N
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, -1, -1));

        pos_market.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pos_market.setForeground(new java.awt.Color(255, 255, 255));
        pos_market.setText("Market List");
        jPanel2.add(pos_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, -1));

        jPanelSlider1.setBorder(null);
        jPanelSlider1.setPreferredSize(new java.awt.Dimension(879, 697));

        info.setBackground(new java.awt.Color(255, 255, 255));
        info.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 133, 245), 10));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/69636.png"))); // NOI18N
        jLabel11.setText("Photo");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(48, 117, 233));
        jLabel12.setText("ID :");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(48, 117, 233));
        jLabel13.setText("Name :");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(48, 117, 233));
        jLabel14.setText("Work period :");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(48, 117, 233));
        jLabel15.setText("Salary :");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(48, 117, 233));
        jLabel19.setText("Surname :");

        ceoID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ceoID.setForeground(new java.awt.Color(48, 117, 233));
        ceoID.setPreferredSize(new java.awt.Dimension(120, 20));

        ceoName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ceoName.setForeground(new java.awt.Color(48, 117, 233));
        ceoName.setPreferredSize(new java.awt.Dimension(120, 20));

        ceoSName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ceoSName.setForeground(new java.awt.Color(48, 117, 233));
        ceoSName.setPreferredSize(new java.awt.Dimension(120, 20));

        ceoStart.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ceoStart.setForeground(new java.awt.Color(48, 117, 233));
        ceoStart.setPreferredSize(new java.awt.Dimension(120, 20));

        ceoSalary.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ceoSalary.setForeground(new java.awt.Color(48, 117, 233));
        ceoSalary.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bluelogo.png"))); // NOI18N

        javax.swing.GroupLayout infoLayout = new javax.swing.GroupLayout(info);
        info.setLayout(infoLayout);
        infoLayout.setHorizontalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel19)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(33, 33, 33)
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ceoSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ceoStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ceoSName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ceoName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ceoID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(384, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );
        infoLayout.setVerticalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoLayout.createSequentialGroup()
                        .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(ceoID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(ceoName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(ceoSName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ceoStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(20, 20, 20)
                        .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ceoSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        jPanelSlider1.add(info, "card3");

        svList.setBackground(new java.awt.Color(255, 255, 255));
        svList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 133, 245), 10));

        jTabbedPane2.setBackground(new java.awt.Color(66, 133, 245));
        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        svTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Surname"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(svTable);

        jTabbedPane2.addTab("On Work", jScrollPane4);

        addWorker.setBackground(new java.awt.Color(255, 255, 255));
        addWorker.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(48, 117, 233));
        jLabel10.setText("Market ID :");
        addWorker.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(48, 117, 233));
        jLabel28.setText("Surname :");
        addWorker.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(48, 117, 233));
        jLabel29.setText("Name :");
        addWorker.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, -1));
        addWorker.add(addSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 90, -1));
        addWorker.add(addSName, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 90, -1));
        addWorker.add(addName, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 90, -1));
        addWorker.add(addPlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 90, -1));

        addSV.setBackground(new java.awt.Color(48, 117, 233));
        addSV.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        addSV.setForeground(new java.awt.Color(255, 255, 255));
        addSV.setText("ADD");
        addSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSVActionPerformed(evt);
            }
        });
        addWorker.add(addSV, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 180, 60));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(48, 117, 233));
        jLabel18.setText("Salary :");
        addWorker.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 60, -1));

        addResult.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        addResult.setForeground(new java.awt.Color(46, 117, 233));
        addWorker.add(addResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 180, 30));

        jTabbedPane2.addTab("Add SV", addWorker);

        deleteWorker.setBackground(new java.awt.Color(255, 255, 255));
        deleteWorker.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        svfireid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                svfireidActionPerformed(evt);
            }
        });
        deleteWorker.add(svfireid, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 131, -1));

        jLabel32.setText("Compensation :");
        deleteWorker.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, 30));

        jLabel33.setText("ID :");
        deleteWorker.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, 20));

        jLabel34.setText("Name :");
        deleteWorker.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, 30));

        jLabel35.setText("Surname :");
        deleteWorker.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, 30));

        svfirecomp.setText(" ");
        deleteWorker.add(svfirecomp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 130, 30));

        svfirename.setText(" ");
        deleteWorker.add(svfirename, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 130, 30));

        svfiresname.setText(" ");
        deleteWorker.add(svfiresname, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 130, 30));

        svfirebutton.setBackground(new java.awt.Color(48, 117, 233));
        svfirebutton.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        svfirebutton.setForeground(new java.awt.Color(255, 255, 255));
        svfirebutton.setText("FIRE !!!");
        svfirebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                svfirebuttonActionPerformed(evt);
            }
        });
        deleteWorker.add(svfirebutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 240, 70));

        fireRes.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        fireRes.setForeground(new java.awt.Color(48, 117, 233));
        deleteWorker.add(fireRes, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 260, 30));

        jTabbedPane2.addTab("Delete SV", deleteWorker);

        javax.swing.GroupLayout svListLayout = new javax.swing.GroupLayout(svList);
        svList.setLayout(svListLayout);
        svListLayout.setHorizontalGroup(
            svListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        svListLayout.setVerticalGroup(
            svListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        jPanelSlider1.add(svList, "card3");

        inbox.setBackground(new java.awt.Color(255, 255, 255));
        inbox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 133, 245), 10));
        inbox.setPreferredSize(new java.awt.Dimension(879, 462));

        messTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "From", "Subject"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(messTable);

        showMessBut.setText("Show Message");
        showMessBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMessButActionPerformed(evt);
            }
        });

        sendMessBut.setText("Send Message");
        sendMessBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inboxLayout = new javax.swing.GroupLayout(inbox);
        inbox.setLayout(inboxLayout);
        inboxLayout.setHorizontalGroup(
            inboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(inboxLayout.createSequentialGroup()
                .addComponent(showMessBut, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sendMessBut, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        inboxLayout.setVerticalGroup(
            inboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inboxLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addGroup(inboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showMessBut, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(sendMessBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanelSlider1.add(inbox, "card2");

        showMessage.setBackground(new java.awt.Color(255, 255, 255));
        showMessage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 133, 245), 10));
        showMessage.setPreferredSize(new java.awt.Dimension(875, 501));

        showMess.setEditable(false);
        showMess.setColumns(20);
        showMess.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        showMess.setRows(5);
        jScrollPane3.setViewportView(showMess);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(48, 117, 233));
        jLabel5.setText("Message :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(48, 117, 233));
        jLabel6.setText("Subject :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(48, 117, 233));
        jLabel7.setText("From :");

        fromLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        subjectLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jButton5.setBackground(new java.awt.Color(66, 133, 245));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Go Back");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bluelogo.png"))); // NOI18N

        javax.swing.GroupLayout showMessageLayout = new javax.swing.GroupLayout(showMessage);
        showMessage.setLayout(showMessageLayout);
        showMessageLayout.setHorizontalGroup(
            showMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showMessageLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(showMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(showMessageLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(40, 40, 40)
                        .addGroup(showMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fromLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(showMessageLayout.createSequentialGroup()
                                .addGap(650, 650, 650)
                                .addComponent(jButton5))))
                    .addGroup(showMessageLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(27, 27, 27)
                        .addComponent(subjectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(showMessageLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(jLabel20)))
                .addContainerGap())
        );
        showMessageLayout.setVerticalGroup(
            showMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showMessageLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(showMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(fromLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addGroup(showMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(showMessageLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(showMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(subjectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(showMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(showMessageLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(46, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, showMessageLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addContainerGap())))
        );

        jPanelSlider1.add(showMessage, "card2");

        marketList.setBackground(new java.awt.Color(255, 255, 255));
        marketList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 133, 245), 10));
        marketList.setPreferredSize(new java.awt.Dimension(875, 501));

        markList.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        markList.setToolTipText("");
        markList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markListActionPerformed(evt);
            }
        });

        supervise.setBackground(new java.awt.Color(48, 117, 233));
        supervise.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
        supervise.setForeground(new java.awt.Color(255, 255, 255));
        supervise.setText("Supervise");
        supervise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                superviseActionPerformed(evt);
            }
        });

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bluelogo.png"))); // NOI18N

        javax.swing.GroupLayout marketListLayout = new javax.swing.GroupLayout(marketList);
        marketList.setLayout(marketListLayout);
        marketListLayout.setHorizontalGroup(
            marketListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(marketListLayout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addGroup(marketListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(marketListLayout.createSequentialGroup()
                        .addComponent(supervise, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 188, Short.MAX_VALUE)
                        .addComponent(jLabel21))
                    .addComponent(markList, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        marketListLayout.setVerticalGroup(
            marketListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(marketListLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(markList, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(supervise, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addContainerGap())
        );

        jPanelSlider1.add(marketList, "card2");

        sendMessage.setBackground(new java.awt.Color(255, 255, 255));
        sendMessage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 133, 245), 10));
        sendMessage.setPreferredSize(new java.awt.Dimension(879, 462));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(48, 117, 233));
        jLabel2.setText("Subject :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(48, 117, 233));
        jLabel3.setText("To :");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(48, 117, 233));
        jLabel4.setText("Message :");

        jButton3.setBackground(new java.awt.Color(66, 133, 245));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Send");

        jButton4.setBackground(new java.awt.Color(66, 133, 245));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Go Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        sendID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendIDActionPerformed(evt);
            }
        });

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bluelogo.png"))); // NOI18N

        javax.swing.GroupLayout sendMessageLayout = new javax.swing.GroupLayout(sendMessage);
        sendMessage.setLayout(sendMessageLayout);
        sendMessageLayout.setHorizontalGroup(
            sendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sendMessageLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addContainerGap())
            .addGroup(sendMessageLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(sendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sendMessageLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(46, 46, 46)
                        .addComponent(sendID, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(showID, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(410, 410, 410)
                        .addComponent(jButton4))
                    .addGroup(sendMessageLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(17, 17, 17)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sendMessageLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 28, Short.MAX_VALUE))
        );
        sendMessageLayout.setVerticalGroup(
            sendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sendMessageLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(sendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(sendID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGroup(sendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sendMessageLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(sendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(sendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(jButton3)
                        .addGap(0, 25, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sendMessageLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22)
                        .addContainerGap())))
        );

        jPanelSlider1.add(sendMessage, "card2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutButtonMouseEntered
        buttonEnter(LogoutButton);
    }//GEN-LAST:event_LogoutButtonMouseEntered

    private void LogoutButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutButtonMouseExited
        buttonExit(LogoutButton);
    }//GEN-LAST:event_LogoutButtonMouseExited

    private void LogoutButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutButtonMousePressed
        if(evt.getButton() == BUTTON1)
        buttonPress(LogoutButton);
    }//GEN-LAST:event_LogoutButtonMousePressed

    private void LogoutButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutButtonMouseReleased
        buttonRelease(LogoutButton);
        if(clicked && in){           
            login p = new login();
            p.setVisible(true);
            clicked = false;
            this.dispose();
        }
    }//GEN-LAST:event_LogoutButtonMouseReleased

    private void rightButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rightButtonMouseEntered
        if(rightButton.isEnabled())
        buttonEnter(rightButton);
    }//GEN-LAST:event_rightButtonMouseEntered

    private void rightButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rightButtonMouseExited
        if(rightButton.isEnabled())
        buttonExit(rightButton);
    }//GEN-LAST:event_rightButtonMouseExited

    private void rightButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rightButtonMousePressed
        if(evt.getButton() == BUTTON1 && rightButton.isEnabled())
        buttonPress(rightButton);
    }//GEN-LAST:event_rightButtonMousePressed

    private void rightButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rightButtonMouseReleased
        if(rightButton.isEnabled())
        buttonRelease(rightButton);

        if(in){
            if(clicked && info_bool){
                jPanelSlider1.nextPanel(10,svList, jPanelSlider1.right);
                pos_sv.setForeground(new Color(255,204,51));
                pos_info.setForeground(Color.white);
                sv_bool = true;
                info_bool=false;
            }
            else if(clicked && sv_bool){
                jPanelSlider1.nextPanel(10,marketList, jPanelSlider1.right);
                pos_market.setForeground(new Color(255,204,51));
                pos_sv.setForeground(Color.white);
                market_bool = true;
                sv_bool=false;
            }
            else if(clicked && market_bool){
                jPanelSlider1.nextPanel(10,inbox, jPanelSlider1.right);
                pos_inbox.setForeground(new Color(255,204,51));
                pos_market.setForeground(Color.white);
                inbox_bool = true;
                market_bool=false;
            }
           
            else if(clicked && inbox_bool){
                jPanelSlider1.nextPanel(10,info, jPanelSlider1.right);
                pos_info.setForeground(new Color(255,204,51));
                pos_inbox.setForeground(Color.white);
                info_bool = true;
                inbox_bool=false;

            }

        }

        clicked = false;
    }//GEN-LAST:event_rightButtonMouseReleased

    private void showMessButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMessButActionPerformed

        
        try{
            rightButton.setEnabled(false);
            fromLabel.setText(messTable.getValueAt(messTable.getSelectedRow(), 0).toString());
            subjectLabel.setText(messTable.getValueAt(messTable.getSelectedRow(), 1).toString());
            jPanelSlider1.nextPanel(10, showMessage , jPanelSlider1.left);}
        catch(ArrayIndexOutOfBoundsException e){

            JOptionPane.showMessageDialog(jDialog1,
                "You have to select a row to see a message",
                "Error",
                JOptionPane.WARNING_MESSAGE);

        }
       
        Message find = myMess.get(messTable.getSelectedRow());
        if(find != null){
            find.updateCheck();
            showMess.setText(myMess.get(messTable.getSelectedRow()).getContext());
            setClear();
        }
        
          
        
    }//GEN-LAST:event_showMessButActionPerformed

    private void sendMessButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessButActionPerformed
        rightButton.setEnabled(false);
        jPanelSlider1.nextPanel(10, sendMessage, jPanelSlider1.left);
        
    }//GEN-LAST:event_sendMessButActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        rightButton.setEnabled(true);
        jPanelSlider1.nextPanel(10, inbox, jPanelSlider1.right);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        rightButton.setEnabled(true);
        jPanelSlider1.nextPanel(10, inbox, jPanelSlider1.right);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void addSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSVActionPerformed
        if(!(addName.getText().equalsIgnoreCase("")||addName.getText().equals(null))&&!(addSName.getText().equalsIgnoreCase("")||addSName.getText().equals(null))&&!(addPlace.getText().equalsIgnoreCase("")||addPlace.getText().equals(null))&&!(addSalary.getText().equalsIgnoreCase("")||addSalary.getText().equals(null))){
            boolean flag=false;
            boolean af=false;
            DefaultTableModel a=(DefaultTableModel)svTable.getModel();
            for(int i=0;i<allStore.size();i++){
                if(Integer.parseInt(addPlace.getText())==allStore.get(i).getStoreID()){
                    flag=true;
                }
            }
            if(flag){
                Employee add=new Employee(addName.getText(),addSName.getText(),Integer.parseInt(addPlace.getText()));
                add.setSalary(Double.parseDouble(addSalary.getText()));
                add.setStatus("SV");
                af=add.add_item(add);
                if(af){addResult.setText("SUCCESS!!");
                    addName.setText("");
                    addSName.setText("");
                    addPlace.setText("");
                    addSalary.setText("");
                    svfireid.addItem(add.getId()+"");
                    a.addRow(new Object[]{add.getId(),
                                          add.getName(),
                                          add.getSurname()});
                    sendID.addItem(add.getId()+"");
                    setClear();
                }
                else {addResult.setText("FAILURE!!Fill All Fields!");}
            }else{
                addResult.setText("No Matching MarketID!");
            }
        }else{
            addResult.setText("All fields Must be filled!");
        }
    }//GEN-LAST:event_addSVActionPerformed

    private void svfireidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_svfireidActionPerformed
        if(!svfireid.getSelectedItem().toString().equalsIgnoreCase(null)&&!svfireid.getSelectedItem().toString().equalsIgnoreCase("NONE")&&!svfireid.getSelectedItem().toString().equalsIgnoreCase("")){
            firePerson=(Employee)infoPerson.get_item(Integer.parseInt(svfireid.getSelectedItem().toString()));
            svfirename.setText(firePerson.getName());
            svfiresname.setText(firePerson.getSurname());
            svfirecomp.setText(firePerson.getSalary()+"");
            
        }else{
            svfirename.setText("");
            svfiresname.setText("");
            svfirecomp.setText("");
            firePerson=new Employee();
        }
    }//GEN-LAST:event_svfireidActionPerformed

    private void svfirebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_svfirebuttonActionPerformed
        DefaultTableModel a=(DefaultTableModel)svTable.getModel();
        if(!svfireid.getSelectedItem().toString().equalsIgnoreCase("NONE")){
            all=infoPerson.delete_item(all, Integer.parseInt(svfireid.getSelectedItem().toString()));
            fireRes.setText("FIRED!!");
            //sendID.remove(sendID);
            int fired = Integer.parseInt(svfireid.getSelectedItem().toString());
            System.out.println(fired);
            svfireid.removeItemAt(svfireid.getSelectedIndex());
            for(int i=0;i<a.getRowCount();i++){
                Employee r=(Employee)infoPerson.get_item(Integer.parseInt(svfireid.getSelectedItem().toString()));
                if((Integer.parseInt(a.getValueAt(i, 0).toString()))==r.getId()){
                    a.removeRow(i+1);
                }
            }
           
            int cbsize = sendID.getItemCount();
            
                for (int i = 0; i < cbsize; i++) {
                int prs = Integer.parseInt(sendID.getItemAt(i).toString());
               
                if(prs == fired){
                     System.out.println("checked");
                     sendID.removeItemAt(i);           
                }
            }        
            
            setClear();
        }else{
            fireRes.setText("FAILURE!");
        }
    }//GEN-LAST:event_svfirebuttonActionPerformed

    private void sendIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendIDActionPerformed
        // TODO add your handling code here:
        showID.setText(((Employee)infoPerson.get_item(Integer.parseInt(sendID.getSelectedItem().toString()))).getName()+"  "+((Employee)infoPerson.get_item(Integer.parseInt(sendID.getSelectedItem().toString()))).getSurname());
    }//GEN-LAST:event_sendIDActionPerformed

    private void markListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markListActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_markListActionPerformed

    private void superviseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_superviseActionPerformed
        // TODO add your handling code here:
        int supID = Integer.parseInt(markList.getSelectedItem().toString().split("/")[0]);
        ceoSupervisorPanel shift = new ceoSupervisorPanel(Store.findStore(allStore, supID), ceo);
        shift.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_superviseActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LogoutButton;
    private javax.swing.JTextField addName;
    private javax.swing.JTextField addPlace;
    private javax.swing.JLabel addResult;
    private javax.swing.JTextField addSName;
    private javax.swing.JButton addSV;
    private javax.swing.JTextField addSalary;
    private javax.swing.JPanel addWorker;
    private javax.swing.JLabel ceoID;
    private javax.swing.JLabel ceoName;
    private javax.swing.JLabel ceoSName;
    private javax.swing.JLabel ceoSalary;
    private javax.swing.JLabel ceoStart;
    private javax.swing.JPanel deleteWorker;
    private javax.swing.JLabel fireRes;
    private javax.swing.JLabel fromLabel;
    private javax.swing.JPanel inbox;
    private javax.swing.JPanel info;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private diu.swe.habib.JPanelSlider.JPanelSlider jPanelSlider1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JComboBox<String> markList;
    private javax.swing.JPanel marketList;
    private javax.swing.JTable messTable;
    private javax.swing.JLabel pos_inbox;
    private javax.swing.JLabel pos_info;
    private javax.swing.JLabel pos_market;
    private javax.swing.JLabel pos_sv;
    private javax.swing.JLabel rightButton;
    private javax.swing.JComboBox<String> sendID;
    private javax.swing.JButton sendMessBut;
    private javax.swing.JPanel sendMessage;
    private javax.swing.JLabel showID;
    private javax.swing.JTextArea showMess;
    private javax.swing.JButton showMessBut;
    private javax.swing.JPanel showMessage;
    private javax.swing.JLabel subjectLabel;
    private javax.swing.JButton supervise;
    private javax.swing.JPanel svList;
    private javax.swing.JTable svTable;
    private javax.swing.JButton svfirebutton;
    private javax.swing.JLabel svfirecomp;
    private javax.swing.JComboBox<String> svfireid;
    private javax.swing.JLabel svfirename;
    private javax.swing.JLabel svfiresname;
    // End of variables declaration//GEN-END:variables

void buttonExit(Component c){
         timer1.cancel();
        timer3.cancel();

       
            
            TimerTask task1 = new TimerTask() {
                @Override
                public void run() {
                    if (c.getBackground().getRed() >= 48) {

                    c.setBackground(new Color(c.getBackground().getRed()-1,c.getBackground().getGreen()-1,c.getBackground().getBlue()));
                }
                else if(c.getBackground().getRed() < 48){
                    c.setBackground(new Color(c.getBackground().getRed()+1,c.getBackground().getGreen()+1,c.getBackground().getBlue()));
                }
                }
            };
            in = false;
            timer2 = new Timer();
            timer2.scheduleAtFixedRate(task1, 0, 20);
        
        
    }
    void buttonEnter(Component c){
         timer3.cancel();
        timer2.cancel();

        
            
            TimerTask task1 = new TimerTask() {
                @Override
                public void run() {
                     if (c.getBackground().getRed() <= 60) {

                    c.setBackground(new Color(c.getBackground().getRed()+1,c.getBackground().getGreen()+1,c.getBackground().getBlue()));
                }
                }
            };
            in = true;
            timer1 = new Timer();
            timer1.scheduleAtFixedRate(task1, 0, 20);
        
        
    }
    void buttonPress(Component c){
        timer1.cancel();
        timer2.cancel();

       
            clicked = true;
            TimerTask task1 = new TimerTask() {
                @Override
                public void run() {
                    if (c.getBackground().getRed() >= 28) {

                        c.setBackground(new Color(c.getBackground().getRed()-1,c.getBackground().getGreen()-1,c.getBackground().getBlue()));
                    }
                }
            };

            timer3 = new Timer();
            timer3.scheduleAtFixedRate(task1, 0, 20);
        
    }
    void buttonRelease(Component c){
        if(in){
            timer2.cancel();
            timer3.cancel();
            TimerTask task1 = new TimerTask() {
                @Override
                public void run() {
                    if (c.getBackground().getRed() <= 60) {

                        c.setBackground(new Color(c.getBackground().getRed()+1,c.getBackground().getGreen()+1,c.getBackground().getBlue()));
                    }
                }
            };
            timer1 = new Timer();
            timer1.scheduleAtFixedRate(task1, 0, 20);
        
    }
    }
   
}
