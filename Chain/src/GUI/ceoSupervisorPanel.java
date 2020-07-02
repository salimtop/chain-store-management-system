package GUI;


import GUI.login;
import chainmanagement.ColumnColorRenderer;
import chainmanagement.Employee;
import chainmanagement.Product;
import chainmanagement.Store;
import java.awt.Color;
import java.awt.Component;
import static java.awt.event.MouseEvent.BUTTON1;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.jdesktop.swingx.border.DropShadowBorder;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;
import chainmanagement.TimeDifference;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACK
 */
public class ceoSupervisorPanel extends javax.swing.JFrame {

    /**
     * Creates new form supervisorPanel
     */
     Timer timer1 = new Timer();
      Timer timer2 = new Timer();
      Timer timer3 = new Timer();
      Timer slideTimer1 = new Timer();
      Timer slideTimer2 = new Timer();
      
      
      Employee ceo;
      public Store myStore;
      Employee firePerson;
      
      boolean in;
      boolean clicked;
    
      Product refprod=new Product();
      Product ad;
      ArrayList<Product> all=new ArrayList<>();
      ArrayList<Product> myProducts=new ArrayList<>();
      
      boolean chart_bool;
      boolean stock_bool;
      boolean worker_bool=true;
      
      
    public ceoSupervisorPanel(Store infoStore, Employee ceo) {
        
        initComponents();
    lineChart.id = infoStore.getStoreID();
        lineChart l = new lineChart(chart);
        this.ceo = ceo;
        myStore = infoStore;
        workerTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        onVacTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stockTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        //inbox_init();
        jLabel12.setVisible(false);
        jLabel13.setVisible(false);
        warning.setVisible(false);
        pos_worker.setForeground(new Color(255,204,51));
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
        fireid.addItem("NONE");
        
        marketID.setText(infoStore.getStoreID()+"");
        fillInfo();
        
        //System.out.println(infoStore.getName()+"________"+infoStore.getStoreID());
       
    }
    
    void fillInfo(){
        
        //for workers
        DefaultTableModel onWork=(DefaultTableModel)workerTab.getModel();
        DefaultTableModel addWork=(DefaultTableModel)addWorkTab.getModel();
        DefaultTableModel onVacation = (DefaultTableModel)onVacTab.getModel();
        
        for (int i = 0; i < myStore.getEmployees().size() ; i++) {
                if(myStore.getAllEmps().get(i).getPlace()==myStore.getStoreID()){
            //fullfills addworker table
                    addWork.addRow(new Object[]{myStore.getEmployees().get(i).getId(),
                                       myStore.getEmployees().get(i).getName(),
                                       myStore.getEmployees().get(i).getSurname(),
                                       myStore.getEmployees().get(i).getSalary()});
                    
                    
            
            //fullfills onvacation table
            if(myStore.getEmployees().get(i).isOnVacation()){
                String date = myStore.getEmployees().get(i).getVacationTo();
                if(TimeDifference.isVacOver(date)){
                    myStore.getEmployees().get(i).updateOnVacation(false);
                    myStore.getEmployees().get(i).setOnVacation(false);
                }
                else{
                    onVacation.addRow(new Object[]{
                myStore.getEmployees().get(i).getId(),
                myStore.getEmployees().get(i).getName(),
                myStore.getEmployees().get(i).getSurname(),
                myStore.getEmployees().get(i).getVacationTo()
               });
                
                }
                
            }

            
            //fullfills onwork table
            if(!myStore.getEmployees().get(i).isOnVacation())
                onWork.addRow(new Object[]{
                myStore.getEmployees().get(i).getId(),
                myStore.getEmployees().get(i).getName(),
                myStore.getEmployees().get(i).getSurname()
               });
            
            
            //fullfills fire combobox
            fireid.addItem(myStore.getEmployees().get(i).getId()+"");
                }
        }
        
       //for products
       
       DefaultTableModel prodTab = (DefaultTableModel)stockTab.getModel();
        for (int i = 0; i < myStore.getProducts().size(); i++) {
            if(myStore.getProducts().get(i).getPlace()==myStore.getStoreID()){
           String bbfDate = myStore.getProducts().get(i).getExpireDate();
            int keep = TimeDifference.isCloseBbf(bbfDate);
             if(keep == 2){
                warning.setVisible(true);
                bbfDate = "💀💀"+bbfDate;
            }
             else if(keep == 1)
                  bbfDate = "**"+bbfDate;
            if(myStore.getProducts().get(i).getCategory().equalsIgnoreCase("other"))
                bbfDate = "--";
            prodTab.addRow(new Object[]{
                myStore.getProducts().get(i).getStockID(),
                myStore.getProducts().get(i).getBrand(),
                myStore.getProducts().get(i).getType(),
                myStore.getProducts().get(i).getCost(),
                myStore.getProducts().get(i).getPrice(),
                myStore.getProducts().get(i).getCategory(),
                bbfDate,
                myStore.getProducts().get(i).getStock()
                });
            
            }
        }
        
        
    }
    
    
    
    void setClear(){
        
        
        DefaultTableModel onWork=(DefaultTableModel)workerTab.getModel();
        DefaultTableModel addWork=(DefaultTableModel)addWorkTab.getModel();
        DefaultTableModel onVacation = (DefaultTableModel)onVacTab.getModel();
        
        onWork.setNumRows(0);
        addWork.setNumRows(0);
        onVacation.setRowCount(0);
        
        
        for (int i = 0; i < myStore.getEmployees().size() ; i++) {
            if(myStore.getAllEmps().get(i).getPlace()==myStore.getStoreID()){
            if(!myStore.getEmployees().get(i).isOnVacation())
                onWork.addRow(new Object[]{
                myStore.getEmployees().get(i).getId(),
                myStore.getEmployees().get(i).getName(),
                myStore.getEmployees().get(i).getSurname()
               });
            
       
                    addWork.addRow(new Object[]{myStore.getEmployees().get(i).getId(),
                                       myStore.getEmployees().get(i).getName(),
                                       myStore.getEmployees().get(i).getSurname(),
                                       myStore.getEmployees().get(i).getSalary()});
                    
                    
                if(myStore.getEmployees().get(i).isOnVacation())
                   onVacation.addRow(new Object[]{
                     myStore.getEmployees().get(i).getId(),
                    myStore.getEmployees().get(i).getName(),
                    myStore.getEmployees().get(i).getSurname(),
                    myStore.getEmployees().get(i).getVacationTo()
                   });
                
            }
        }
        
    }
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jOptionPane1 = new javax.swing.JOptionPane();
        jDialog1 = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        goBackBut = new javax.swing.JLabel();
        rightButton = new javax.swing.JLabel();
        pos_worker = new javax.swing.JLabel();
        pos_stock = new javax.swing.JLabel();
        pos_chart = new javax.swing.JLabel();
        warning = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        marketID = new javax.swing.JLabel();
        jPanelSlider1 = new diu.swe.habib.JPanelSlider.JPanelSlider();
        workerList = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        workerTab = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        onVacTab = new javax.swing.JTable();
        addWorker = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        addWorkTab = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        changeSal = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        addbuttonw = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        addwname = new javax.swing.JTextField();
        addwsurname = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        addwsalary = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        deleteWorker = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        fireid = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        firecompens = new javax.swing.JLabel();
        firename = new javax.swing.JLabel();
        firesurname = new javax.swing.JLabel();
        firebutton = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        stock = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        stockTab = new javax.swing.JTable();
        chart = new javax.swing.JPanel();

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Photo");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jLabel8.setOpaque(true);

        jButton2.setText("jButton2");

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
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(879, 802));

        jPanel2.setBackground(new java.awt.Color(66, 133, 245));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Chain Marketing Management System Supervisor Panel");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 17, 642, 54));

        goBackBut.setBackground(new java.awt.Color(48, 117, 233));
        goBackBut.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        goBackBut.setForeground(new java.awt.Color(255, 255, 255));
        goBackBut.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goBackBut.setText("Go Back");
        goBackBut.setOpaque(true);
        goBackBut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                goBackButMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                goBackButMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                goBackButMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                goBackButMouseReleased(evt);
            }
        });
        jPanel2.add(goBackBut, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, 93, 47));

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

        pos_worker.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pos_worker.setForeground(new java.awt.Color(255, 255, 255));
        pos_worker.setText("Worker list");
        jPanel2.add(pos_worker, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        pos_stock.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pos_stock.setForeground(new java.awt.Color(255, 255, 255));
        pos_stock.setText("Stock");
        jPanel2.add(pos_stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, -1, -1));

        pos_chart.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pos_chart.setForeground(new java.awt.Color(255, 255, 255));
        pos_chart.setText("Chart");
        jPanel2.add(pos_chart, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, -1, -1));

        warning.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/warning.png"))); // NOI18N
        jPanel2.add(warning, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Market ID :");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 100, 70, 30));

        marketID.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        marketID.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(marketID, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, 90, 30));

        jPanelSlider1.setBorder(null);
        jPanelSlider1.setPreferredSize(new java.awt.Dimension(879, 697));

        workerList.setBackground(new java.awt.Color(255, 255, 255));
        workerList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 133, 245), 10));

        jTabbedPane2.setBackground(new java.awt.Color(66, 133, 245));
        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        workerTab.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(workerTab);

        jTabbedPane2.addTab("On Work", jScrollPane4);

        onVacTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Surname", "Return Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(onVacTab);

        jTabbedPane2.addTab("On Vacation", jScrollPane5);

        addWorker.setBackground(new java.awt.Color(255, 255, 255));
        addWorker.setPreferredSize(new java.awt.Dimension(785, 385));

        addWorkTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Surname", "Salary"
            }
        ));
        jScrollPane7.setViewportView(addWorkTab);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(48, 117, 233));
        jLabel12.setText("Worker Added!");

        changeSal.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        changeSal.setText("select a worker");
        changeSal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                changeSalMouseEntered(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(48, 117, 233));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Change Salary");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        addbuttonw.setBackground(new java.awt.Color(48, 117, 233));
        addbuttonw.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        addbuttonw.setForeground(new java.awt.Color(255, 255, 255));
        addbuttonw.setText("ADD");
        addbuttonw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbuttonwActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(48, 117, 233));
        jLabel29.setText("Name :");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(48, 117, 233));
        jLabel28.setText("Surname :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(48, 117, 233));
        jLabel9.setText("Salary :");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bluelogo.png"))); // NOI18N

        javax.swing.GroupLayout addWorkerLayout = new javax.swing.GroupLayout(addWorker);
        addWorker.setLayout(addWorkerLayout);
        addWorkerLayout.setHorizontalGroup(
            addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addWorkerLayout.createSequentialGroup()
                .addGroup(addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addWorkerLayout.createSequentialGroup()
                        .addGap(550, 550, 550)
                        .addComponent(changeSal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(addWorkerLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addWorkerLayout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 537, Short.MAX_VALUE)
                                .addComponent(jLabel18))
                            .addGroup(addWorkerLayout.createSequentialGroup()
                                .addGroup(addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(addWorkerLayout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addGap(46, 46, 46)
                                        .addComponent(addwname, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(addWorkerLayout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addGap(28, 28, 28)
                                        .addComponent(addwsurname, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(addWorkerLayout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(addwsalary, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(addbuttonw, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(100, 100, 100)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        addWorkerLayout.setVerticalGroup(
            addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addWorkerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(changeSal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addWorkerLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addWorkerLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel29))
                            .addComponent(addwname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addWorkerLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel28))
                            .addComponent(addwsurname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addWorkerLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel9))
                            .addComponent(addwsalary, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(addbuttonw, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addWorkerLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(addWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Add Worker", addWorker);

        deleteWorker.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(48, 117, 233));
        jLabel13.setText("The worker is a customer now");

        fireid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fireidActionPerformed(evt);
            }
        });

        jLabel32.setText("Compensation :");

        jLabel33.setText("ID :");

        jLabel34.setText("Name :");

        jLabel35.setText("Surname :");

        firecompens.setText(" ");

        firename.setText(" ");

        firesurname.setText(" ");

        firebutton.setBackground(new java.awt.Color(48, 117, 233));
        firebutton.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        firebutton.setForeground(new java.awt.Color(255, 255, 255));
        firebutton.setText("FIRE !!!");
        firebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firebuttonActionPerformed(evt);
            }
        });

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bluelogo.png"))); // NOI18N

        javax.swing.GroupLayout deleteWorkerLayout = new javax.swing.GroupLayout(deleteWorker);
        deleteWorker.setLayout(deleteWorkerLayout);
        deleteWorkerLayout.setHorizontalGroup(
            deleteWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteWorkerLayout.createSequentialGroup()
                .addGroup(deleteWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(deleteWorkerLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(deleteWorkerLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel33)
                        .addGap(79, 79, 79)
                        .addComponent(fireid, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(deleteWorkerLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel34)
                        .addGap(58, 58, 58)
                        .addComponent(firename, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(deleteWorkerLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel35)
                        .addGap(39, 39, 39)
                        .addComponent(firesurname, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(deleteWorkerLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel32)
                        .addGap(10, 10, 10)
                        .addComponent(firecompens, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(deleteWorkerLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(firebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(554, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteWorkerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel17))
        );
        deleteWorkerLayout.setVerticalGroup(
            deleteWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteWorkerLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(deleteWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fireid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(deleteWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firename, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(deleteWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firesurname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(deleteWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firecompens, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(firebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addTab("Delete Worker", deleteWorker);

        javax.swing.GroupLayout workerListLayout = new javax.swing.GroupLayout(workerList);
        workerList.setLayout(workerListLayout);
        workerListLayout.setHorizontalGroup(
            workerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        workerListLayout.setVerticalGroup(
            workerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        jPanelSlider1.add(workerList, "card3");

        stock.setBackground(new java.awt.Color(255, 255, 255));
        stock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 133, 245), 10));

        stockTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brand", "Type", "Cost", "Price", "Category", "Expire Date", "Stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(stockTab);

        javax.swing.GroupLayout stockLayout = new javax.swing.GroupLayout(stock);
        stock.setLayout(stockLayout);
        stockLayout.setHorizontalGroup(
            stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)
        );
        stockLayout.setVerticalGroup(
            stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
        );

        jPanelSlider1.add(stock, "card3");

        chart.setBackground(new java.awt.Color(255, 255, 255));
        chart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 133, 245), 10));

        javax.swing.GroupLayout chartLayout = new javax.swing.GroupLayout(chart);
        chart.setLayout(chartLayout);
        chartLayout.setHorizontalGroup(
            chartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 879, Short.MAX_VALUE)
        );
        chartLayout.setVerticalGroup(
            chartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );

        jPanelSlider1.add(chart, "card3");

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    
    
    
    private void goBackButMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goBackButMouseEntered
        buttonEnter(goBackBut);

    }//GEN-LAST:event_goBackButMouseEntered

    private void goBackButMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goBackButMouseExited
        buttonExit(goBackBut);
    }//GEN-LAST:event_goBackButMouseExited

    private void goBackButMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goBackButMousePressed
        if(evt.getButton() == BUTTON1)
        buttonPress(goBackBut);
        
    }//GEN-LAST:event_goBackButMousePressed

    private void goBackButMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goBackButMouseReleased
        buttonRelease(goBackBut);
        if(clicked && in){
            ceoPanel p = new ceoPanel(ceo);
            p.setVisible(true);
            clicked = false;
            this.dispose();
        }

    }//GEN-LAST:event_goBackButMouseReleased

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
           
            if(clicked && worker_bool){
                jPanelSlider1.nextPanel(10,stock, jPanelSlider1.right);
                pos_stock.setForeground(new Color(255,204,51));
                pos_worker.setForeground(Color.white);
                stock_bool = true;
                worker_bool=false;
            }
            else if(clicked && stock_bool){
                jPanelSlider1.nextPanel(10,chart, jPanelSlider1.right);
                pos_chart.setForeground(new Color(255,204,51));
                pos_stock.setForeground(Color.white);
                chart_bool = true;
                stock_bool=false;

            }
            else if(clicked && chart_bool){
                jPanelSlider1.nextPanel(10,workerList, jPanelSlider1.right);
                pos_worker.setForeground(new Color(255,204,51));
                pos_chart.setForeground(Color.white);
                worker_bool = true;
                chart_bool=false;

            }
           

        }

        clicked = false;

    }//GEN-LAST:event_rightButtonMouseReleased

    private void changeSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeSalMouseEntered
        // TODO add your handling code here:
        changeSal.setText("");
    }//GEN-LAST:event_changeSalMouseEntered

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Employee cs=(Employee)ceo.get_item(Integer.parseInt(addWorkTab.getValueAt(addWorkTab.getSelectedRow(), 0).toString()));
        for(int i=0;i<myStore.getEmployees().size();i++){
            if(myStore.getEmployees().get(i).getId()==cs.getId()){
                if(myStore.getEmployees().get(i).updateSalary(Double.parseDouble(changeSal.getText()))){
                    changeSal.setText("SUCCESS!!");
                    
                    setClear();
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addbuttonwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbuttonwActionPerformed

        if(addwname.getText()!=""&&addwsurname.getText()!=""&&addwsalary.getText()!=""){
            Employee add=new Employee(addwname.getText(),addwsurname.getText(),myStore.getStoreID());
            add.setSalary(Integer.parseInt(addwsalary.getText()));
            add.setStatus("WORKER");
            add.add_item(add);
            myStore.setEmployees(myStore.getAllEmps());
            fireid.addItem((add.getId())+"");
            addwname.setText("");
            addwsurname.setText("");
            addwsalary.setText("");
            setClear();            
        }
    }//GEN-LAST:event_addbuttonwActionPerformed

    private void fireidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fireidActionPerformed
        if(!fireid.getSelectedItem().toString().equalsIgnoreCase("none") && !fireid.getSelectedItem().equals(null) && !fireid.getSelectedItem().toString().equals("")){
            firePerson=(Employee)ceo.get_item(Integer.parseInt(fireid.getSelectedItem().toString()));
            firename.setText(firePerson.getName());
            firesurname.setText(firePerson.getSurname());
            firecompens.setText(""+firePerson.getSalary());

        }else{
            firename.setText("");
            firesurname.setText("");
            firecompens.setText("");
        }
    }//GEN-LAST:event_fireidActionPerformed

    private void firebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firebuttonActionPerformed
        if(!fireid.getSelectedItem().toString().equalsIgnoreCase("none")){
            myStore.setEmployees(ceo.delete_item(myStore.getEmployees(), Integer.parseInt(fireid.getSelectedItem().toString())));
            fireid.removeItemAt(fireid.getSelectedIndex());
            setClear();
            jLabel13.setVisible(true);
            //fireid.setSelectedIndex(0);
        }
    }//GEN-LAST:event_firebuttonActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable addWorkTab;
    private javax.swing.JPanel addWorker;
    private javax.swing.JButton addbuttonw;
    private javax.swing.JTextField addwname;
    private javax.swing.JTextField addwsalary;
    private javax.swing.JTextField addwsurname;
    private javax.swing.JTextField changeSal;
    private javax.swing.JPanel chart;
    private javax.swing.JPanel deleteWorker;
    private javax.swing.JButton firebutton;
    private javax.swing.JLabel firecompens;
    private javax.swing.JComboBox<String> fireid;
    private javax.swing.JLabel firename;
    private javax.swing.JLabel firesurname;
    private javax.swing.JLabel goBackBut;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private diu.swe.habib.JPanelSlider.JPanelSlider jPanelSlider1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel marketID;
    private javax.swing.JTable onVacTab;
    private javax.swing.JLabel pos_chart;
    private javax.swing.JLabel pos_stock;
    private javax.swing.JLabel pos_worker;
    private javax.swing.JLabel rightButton;
    private javax.swing.JPanel stock;
    private javax.swing.JTable stockTab;
    private javax.swing.JLabel warning;
    private javax.swing.JPanel workerList;
    private javax.swing.JTable workerTab;
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
