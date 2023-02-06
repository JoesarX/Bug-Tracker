package teoria.pkg1.soft.devel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.DefaultComboBoxModel;

public class HiloRefresh extends Thread {

    public boolean alive;
    private JTable ProyectosTable;
    private JTable ProyectosTable2;
    private JTable BugsTable;
    private String username;
    private String password;
    private String url;
    private String DeveloperID;
    private JCheckBox BoxAbiertos;
    private JCheckBox BoxFinalizados;
    private JCheckBox BoxAbiertos2;
    private JCheckBox BoxFinalizados2;
    private JComboBox ProyectosComboBox;
    private JTextArea ComentariosTextArea;
    private String usu;
    private boolean flag;
    private int counter = 0;
    private int counter2 = 0;
    private int FirstCounter = 0;
    private ArrayList<String> viejo = new ArrayList<String>();
    private ArrayList<String> nuevo = new ArrayList<String>();
    

    public HiloRefresh(JTable ProyectosTable, JTable ProyectosTable2, JTable BugsTable, String url, String username, String password, String DeveloperID, JCheckBox BoxAbiertos, JCheckBox BoxFinalizados, JCheckBox BoxAbiertos2, JCheckBox BoxFinalizados2, JComboBox ProyectosComboBox, JTextArea ComentariosTextArea, String usu, boolean flag) {
        alive = true;
        this.ProyectosTable = ProyectosTable;
        this.ProyectosTable2 = ProyectosTable2;
        this.BugsTable = BugsTable;
        this.username = username;
        this.password = password;
        this.url = url;
        this.DeveloperID = DeveloperID;
        this.BoxAbiertos = BoxAbiertos;
        this.BoxFinalizados = BoxFinalizados;
        this.BoxAbiertos2 = BoxAbiertos2;
        this.BoxFinalizados2 = BoxFinalizados2;
        this.ProyectosComboBox = ProyectosComboBox;
        this.ComentariosTextArea = ComentariosTextArea;
        this.usu = usu;
        this.flag = flag;
    }

    public boolean itsAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void updateTablas() {
        if (BugsTable.getSelectedRow() != -1 && counter < 6) {
            counter++;
        } else {
            counter = 0;
            try {
                System.out.println("bien");
                Connection connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to SQL Server.");
                Statement statement = connection.createStatement();

                ResultSet rs;
                DefaultTableModel modelo = (DefaultTableModel) ProyectosTable.getModel();
                modelo.setRowCount(0);
                String sql = "select distinct  B.Codigo_Proyecto, PS.Nombre \n"
                        + "from Bug B inner join Proyecto_Software PS on PS.Codigo_Proyecto=B.Codigo_Proyecto \n"
                        + "where B.Codigo_Desarrollador="
                        + DeveloperID;
                rs = statement.executeQuery(sql);
                //ProyectosComboBox.setModel(new DefaultComboBoxModel<>());
                while (rs.next()) {
                    String codigo = rs.getString("Codigo_Proyecto");
                    String nombre = rs.getString("Nombre");
                    Object[] newrow = {codigo, nombre};
                    modelo.addRow(newrow);
                    //ProyectosComboBox.addItem(nombre);
                }
                //ProyectosComboBox.addItem("Todos");
                ProyectosTable.setModel(modelo);

                ResultSet rs2;
                DefaultTableModel modelo2 = (DefaultTableModel) BugsTable.getModel();
                modelo2.setRowCount(0);
                String sql2 = "select B.Codigo_Bug, B.Descripcion, B.Codigo_Proyecto, B.Urgencia, B.Estado, Fecha_Inicio, Fecha_Final \n"
                        + "from Bug B \n"
                        + "where B.Codigo_Desarrollador="
                        + DeveloperID;
                rs2 = statement.executeQuery(sql2);
                while (rs2.next()) {
                    String Codigo_Bug = rs2.getString("Codigo_Bug");
                    String Descripcion = rs2.getString("Descripcion");
                    String Codigo_Proyecto = rs2.getString("Codigo_Proyecto");
                    String Urgencia = rs2.getString("Urgencia");
                    String Estado = rs2.getString("Estado");
                    String Fecha_Inicio = rs2.getString("Fecha_Inicio");
                    String Fecha_Final = rs2.getString("Fecha_Final");
                    Object[] newrow = {Codigo_Bug, Descripcion, Codigo_Proyecto, Urgencia, Estado, Fecha_Inicio, Fecha_Final};
                    //System.out.println(Estado);
                    //System.out.println(BoxAbiertos.isSelected());
                    if (BoxAbiertos.isSelected() == true && (Estado.contains("Asignado"))) {
                        System.out.println("added");
                        modelo2.addRow(newrow);
                    } else if (BoxFinalizados.isSelected() == true && Estado.equalsIgnoreCase("Finalizado")) {
                        modelo2.addRow(newrow);
                    }
                    //modelo2.addRow(newrow);

                }
                BugsTable.setModel(modelo2);
                connection.close();
                System.out.println("Refresh Bien");
            } catch (SQLException ex) {
                //Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al Updatear Tabla Proyectos");
            }
        }
        if (ProyectosTable2.getSelectedRow() != -1 && counter2 < 6) {
            counter2++;
        } 
        else {
            try {
                counter2 = 0;
                Connection connection = DriverManager.getConnection(url, username, password);
                //System.out.println("Connected to SQL Server.");
                Statement statement = connection.createStatement();

                ResultSet rs;
                DefaultTableModel modeloClone = (DefaultTableModel) ProyectosTable2.getModel();
                modeloClone.setRowCount(0);
                String sql = "select distinct  B.Codigo_Proyecto, PS.Nombre \n"
                        + "from Bug B inner join Proyecto_Software PS on PS.Codigo_Proyecto=B.Codigo_Proyecto \n"
                        + "where B.Codigo_Desarrollador="
                        + DeveloperID;
                rs = statement.executeQuery(sql);
                //ProyectosComboBox.setModel(new DefaultComboBoxModel<>());
                while (rs.next()) {
                    String codigo = rs.getString("Codigo_Proyecto");
                    String nombre = rs.getString("Nombre");
                    Object[] newrow = {codigo, nombre};
                    modeloClone.addRow(newrow);
                    //ProyectosComboBox.addItem(nombre);
                }
                //ProyectosComboBox.addItem("Todos");
                ProyectosTable2.setModel(modeloClone);
            } catch (SQLException ex) {
                //Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al Updatear Tabla Proyectos");
            }
        }
        if (FirstCounter == 0) {
            try {
                //System.out.println("bien");
                Connection connection = DriverManager.getConnection(url, username, password);
                //System.out.println("Connected to SQL Server.");
                Statement statement = connection.createStatement();

                ResultSet rs;
                String sql = "select C.Codigo_Comentario, C.Username, C.Codigo_Bug from Comentario C";
                rs = statement.executeQuery(sql);
                //ProyectosComboBox.setModel(new DefaultComboBoxModel<>());
                while (rs.next()) {
                    String codigo = rs.getString("Codigo_Comentario");
                    String user = rs.getString("Username");
                    String bugu = rs.getString("Codigo_Bug");
                    if (usu.equalsIgnoreCase(user) == false) {
                        String add = codigo + "-" + bugu;
                        viejo.add(add);
                    }
                }
                connection.close();
                FirstCounter = 1;

            } catch (SQLException ex) {
                //Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al Updatear Tabla Proyectos");
            }
        } else {
            try {
                //System.out.println("bien");
                Connection connection = DriverManager.getConnection(url, username, password);
                //System.out.println("Connected to SQL Server.");
                Statement statement = connection.createStatement();

                ResultSet rs;
                String sql = "select C.Codigo_Comentario, C.Username, C.Codigo_Bug from Comentario C";
                rs = statement.executeQuery(sql);
                //ProyectosComboBox.setModel(new DefaultComboBoxModel<>());
                nuevo.clear();
                while (rs.next()) {
                    String codigo = rs.getString("Codigo_Comentario");
                    String user = rs.getString("Username");
                    String bugu = rs.getString("Codigo_Bug");
                    if (usu.equalsIgnoreCase(user) == false) {
                        String add = codigo + "-" + bugu;
                        nuevo.add(add);
                    }
                }
                connection.close();
                FirstCounter = 1;

            } catch (SQLException ex) {
                //Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al Updatear Tabla Proyectos");
            }

            if (nuevo.equals(viejo) == false) {
                String cambios = "Hay nuevos Comentarios en los siguientes Bugs:\n";
                for (int i = 0; i < nuevo.size(); i++) {
                    if (viejo.contains(nuevo.get(i)) == false) {
                        String[] splited = nuevo.get(i).toString().split("-");
                        cambios += "1 Nuevo Comentario en Bug: " + splited[1] + "\n";
                    }
                }
                System.out.println(viejo);
                System.out.println(nuevo);
                viejo.clear();
                viejo = (ArrayList) nuevo.clone();
                nuevo.clear();
                try {
                    Connection connection = DriverManager.getConnection(url, username, password);
                    System.out.println("Connected to SQL Server.");
                    Statement statement = connection.createStatement();
                    ResultSet rs3;
                    String model3 = "";
                    String sql3 = "select B.Codigo_Desarrollador, B.Codigo_Proyecto, B.Estado, B.Codigo_Bug, C.Comentario, C.Username, U.Rol, convert(varchar, C.Datetime, 0) as Datetime \n"
                            + "from Bug B inner join Comentario C on C.Codigo_Bug=B.Codigo_Bug inner join Users U on U.Username=C.Username  \n"
                            + "where B.Codigo_Desarrollador = "
                            + DeveloperID + " order by B.Codigo_Proyecto, B.Codigo_Bug, C.Datetime";
                    rs3 = statement.executeQuery(sql3);

                    String currentProject = "";
                    String currentBug = "";
                    boolean contador = false;
                    while (rs3.next()) {
                        String Codigo_Bug = rs3.getString("Codigo_Bug");
                        String Codigo_Proyecto = rs3.getString("Codigo_Proyecto");
                        String Codigo_Desarrollador = rs3.getString("Codigo_Desarrollador");
                        String Comentario = rs3.getString("Comentario");
                        String Username = rs3.getString("Username");
                        String Rol = (rs3.getString("Rol")).substring(0, 3);
                        String date = rs3.getString("Datetime");
                        String Estado = rs3.getString("Estado");
                        if (BoxAbiertos2.isSelected() == true && (Estado.contains("Asignado"))) {
                            //System.out.println("added");
                            if (currentProject.equals(Codigo_Proyecto) == false) {
                                if (contador) {
                                    model3 += "\n";
                                }
                                model3 += "PROJECTO: " + Codigo_Proyecto + "\n";
                                currentProject = Codigo_Proyecto;

                            }
                            contador = true;
                            if (currentBug.equals(Codigo_Bug) == false) {
                                model3 += "    BUG: " + Codigo_Bug + "\n";

                                currentBug = Codigo_Bug;
                            }
                            model3 += "        " + Username + " (" + Rol + ")" + ": " + Comentario + " - " + date + "\n";
                        } else if (BoxFinalizados2.isSelected() == true && Estado.equalsIgnoreCase("Finalizado")) {
                            if (currentProject.equals(Codigo_Proyecto) == false) {
                                if (contador) {
                                    model3 += "\n";
                                }
                                model3 += "PROJECTO: " + Codigo_Proyecto + "\n";
                                currentProject = Codigo_Proyecto;

                            }
                            contador = true;
                            if (currentBug.equals(Codigo_Bug) == false) {
                                model3 += "    BUG: " + Codigo_Bug + " - FINALIZADO\n";

                                currentBug = Codigo_Bug;
                            }
                            model3 += "        " + Username + " (" + Rol + ")" + ": " + Comentario + " - " + date + "\n";
                        }

                    }
                    ComentariosTextArea.setText(model3);
                    connection.close();
                } catch (SQLException ex) {
                    //Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error al Updatear Tabla Proyectos");
                }
                JOptionPane.showMessageDialog(null, cambios);
            }

        }
    }
    
    //Hilo para admin y analista --------------------------------------------------------------------------------------------------------------------------
     public void updateTablas2() {
        if (FirstCounter == 0) {
            try {
                //System.out.println("bien");
                Connection connection = DriverManager.getConnection(url, username, password);
                //System.out.println("Connected to SQL Server.");
                Statement statement = connection.createStatement();

                ResultSet rs;
                String sql = "select C.Codigo_Comentario, C.Username, C.Codigo_Bug from Comentario C";
                rs = statement.executeQuery(sql);
                //ProyectosComboBox.setModel(new DefaultComboBoxModel<>());
                while (rs.next()) {
                    String codigo = rs.getString("Codigo_Comentario");
                    String user = rs.getString("Username");
                    String bugu = rs.getString("Codigo_Bug");
                    if (usu.equalsIgnoreCase(user) == false) {
                        String add = codigo + "-" + bugu;
                        viejo.add(add);
                    }
                }
                connection.close();
                FirstCounter = 1;

            } catch (SQLException ex) {
                //Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al Updatear Tabla Proyectos");
            }
        } else {
            try {
                //System.out.println("bien");
                Connection connection = DriverManager.getConnection(url, username, password);
                //System.out.println("Connected to SQL Server.");
                Statement statement = connection.createStatement();

                ResultSet rs;
                String sql = "select C.Codigo_Comentario, C.Username, C.Codigo_Bug from Comentario C";
                rs = statement.executeQuery(sql);
                //ProyectosComboBox.setModel(new DefaultComboBoxModel<>());
                nuevo.clear();
                while (rs.next()) {
                    String codigo = rs.getString("Codigo_Comentario");
                    String user = rs.getString("Username");
                    String bugu = rs.getString("Codigo_Bug");
                    if (usu.equalsIgnoreCase(user) == false) {
                        String add = codigo + "-" + bugu;
                        nuevo.add(add);
                    }
                }
                connection.close();
                FirstCounter = 1;

            } catch (SQLException ex) {
                //Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al Updatear Tabla Proyectos");
            }
            if (nuevo.equals(viejo) == false) {
                String cambios = "Hay nuevos Comentarios en los siguientes Bugs:\n";
                for (int i = 0; i < nuevo.size(); i++) {
                    if (viejo.contains(nuevo.get(i)) == false) {
                        String[] splited = nuevo.get(i).toString().split("-");
                        cambios += "1 Nuevo Comentario en Bug: " + splited[1] + "\n";
                    }
                }
                System.out.println(viejo);
                System.out.println(nuevo);
                viejo.clear();
                viejo = (ArrayList) nuevo.clone();
                nuevo.clear();
                try {
                    Connection connection = DriverManager.getConnection(url, username, password);
                    System.out.println("Connected to SQL Server.");
                    Statement statement = connection.createStatement();
                    ResultSet rs3;
                    String model3 = "";
                    String sql3 = "select B.Codigo_Desarrollador, B.Codigo_Proyecto, B.Estado, B.Codigo_Bug, C.Comentario, C.Username, U.Rol, convert(varchar, C.Datetime, 0) as Datetime \n"
                            + "from Bug B inner join Comentario C on C.Codigo_Bug=B.Codigo_Bug inner join Users U on U.Username=C.Username  \n"
                            + " order by B.Codigo_Proyecto, B.Codigo_Bug, C.Datetime";
                    rs3 = statement.executeQuery(sql3);

                    String currentProject = "";
                    String currentBug = "";
                    boolean contador = false;
                    while (rs3.next()) {
                        String Codigo_Bug = rs3.getString("Codigo_Bug");
                        String Codigo_Proyecto = rs3.getString("Codigo_Proyecto");
                        String Codigo_Desarrollador = rs3.getString("Codigo_Desarrollador");
                        String Comentario = rs3.getString("Comentario");
                        String Username = rs3.getString("Username");
                        String Rol = (rs3.getString("Rol")).substring(0, 3);
                        String date = rs3.getString("Datetime");
                        String Estado = rs3.getString("Estado");
                        if (BoxAbiertos2.isSelected() == true && (Estado.contains("Asignado"))) {
                            //System.out.println("added");
                            if (currentProject.equals(Codigo_Proyecto) == false) {
                                if (contador) {
                                    model3 += "\n";
                                }
                                model3 += "PROJECTO: " + Codigo_Proyecto + "\n";
                                currentProject = Codigo_Proyecto;

                            }
                            contador = true;
                            if (currentBug.equals(Codigo_Bug) == false) {
                                model3 += "    BUG: " + Codigo_Bug + "\n";

                                currentBug = Codigo_Bug;
                            }
                            model3 += "        " + Username + " (" + Rol + ")" + ": " + Comentario + " - " + date + "\n";
                        } else if (BoxFinalizados2.isSelected() == true && Estado.equalsIgnoreCase("Finalizado")) {
                            if (currentProject.equals(Codigo_Proyecto) == false) {
                                if (contador) {
                                    model3 += "\n";
                                }
                                model3 += "PROJECTO: " + Codigo_Proyecto + "\n";
                                currentProject = Codigo_Proyecto;

                            }
                            contador = true;
                            if (currentBug.equals(Codigo_Bug) == false) {
                                model3 += "    BUG: " + Codigo_Bug + " - FINALIZADO\n";

                                currentBug = Codigo_Bug;
                            }
                            model3 += "        " + Username + " (" + Rol + ")" + ": " + Comentario + " - " + date + "\n";
                        }

                    }
                    ComentariosTextArea.setText(model3);
                    connection.close();
                } catch (SQLException ex) {
                    //Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error al Updatear Tabla Proyectos");
                }
                JOptionPane.showMessageDialog(null, cambios);
            }
        }
    }

    @Override
    public void run() {
        while (alive) {
            //System.out.println("Que quiere el pueblo? Secso");
            if (flag) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    System.out.println("Error con sleep Hilo");
                }
                updateTablas();
            } else {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    System.out.println("Error con sleep Hilo2");
                }
                updateTablas2();
            }
        }
    }
}
