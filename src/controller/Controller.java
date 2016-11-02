package controller;

import connectionSQL.ConnectionSQL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import models.Student;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import sql.SQLStudent;
import views.MainView;
import views.ViewStudent;

/**
 *
 * @author Helio
 */
public class Controller implements MouseListener, ActionListener {

    //PROPIEDADES
    Connection connection = ConnectionSQL.getInstance().getConexion();
    SQLStudent sqlStudent;
    MainView mainView;
    ViewStudent viewStudent;

    public Controller() {
            //SQLStudents se encarga de abrir la conexión y ejecutar la consulta, hay que hacerlo lo primero
            //para que al crear la tabla no nos de NullPointerException
            //sqlStudent = new SQLStudent();
            //sqlStudent.query();
            
            mainView = new MainView(this);
            mainView.setVisible(true);
            mainView.setLocationRelativeTo(null);
    }

    //MouseListener
    @Override
    //CLICK EN LA TABLA
    public void mouseClicked(MouseEvent e) {
        //show();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Botón Alumnos
        if (e.getSource() == mainView.getButtonStudents()) {
            try {
                mainView.setVisible(false);
                sqlStudent = new SQLStudent();
                sqlStudent.query();
                viewStudent = new ViewStudent(this);
                viewStudent.setVisible(true);
                viewStudent.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Botón Salir
        if (e.getSource() == viewStudent.getBtnExit()) {
            System.exit(0);
            sqlStudent.closeConnection();
        }

        //Botón Altas
        if (e.getSource() == viewStudent.getBtnInsert()) {
            Student student = new Student();
            student.setName(viewStudent.getTfName().getText());
            student.setId(viewStudent.getTfId().getText());
            student.setFirstSurname(viewStudent.getTfFirstSurname().getText());
            student.setSecondSurname(viewStudent.getTfSecondSurname().getText());

            sqlStudent.insert(student);
        }

        //Botón Modificar
        if (e.getSource() == viewStudent.getBtnUpdate()) {
            Student student = new Student();
            student.setName(viewStudent.getTfName().getText());
            student.setId(viewStudent.getTfId().getText());
            student.setFirstSurname(viewStudent.getTfFirstSurname().getText());
            student.setSecondSurname(viewStudent.getTfSecondSurname().getText());
            student.setRegistry(Integer.parseInt(viewStudent.getTfRegistry().getText()));

            sqlStudent.update(student);
        }

        //Botón Bajas
        if (e.getSource() == viewStudent.getBtnDelete()) {
            Student student = new Student();
            student.setRegistry(Integer.parseInt(viewStudent.getTfRegistry().getText()));

            sqlStudent.delete(student);
        }

        //Botón Informe
        Map<String, Object> params = new HashMap<>();
        /*params.put("reportTitle", "Libros por Students");
        params.put("reportDate", (new java.util.Date()).toString());*/

        try {
            //Ruta del informe.jrxml
            String reportPath = "src/reports/StudentsReport.jrxml";
            // Obtenemos el input stream del informe ya que de otro modo no podriamos 
            // acceder al informe desde el jar.
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(reportPath);
            // Construimos la query
            //String reportQuery = "select * from students;";

            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);

            // Asignamos la query construida al report
            JRDesignQuery newQuery = new JRDesignQuery();
            //newQuery.setText(reportQuery);
            jasperDesign.setQuery(newQuery);

            // Compilamos el informe
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint reportPrint = JasperFillManager.fillReport(jasperReport, params, connection);

            // Creamos el informe a partir del compilado
            //JasperFillManager.fillReportToFile(jasperReport, "informePorLibros.txt", params, conexion);
            JasperViewer jasperViewer = new JasperViewer(reportPrint, false);

            // Ver el informe en un dialog
            // Para ello insertamos el jasperViewer dentro de un JDialog
            JDialog viewerDialog = new JDialog(jasperViewer, "Visualizador de informes", true);
            viewerDialog.setVisible(true);

        } catch (Exception report) {
            System.err.println("Error creando el generando el report");        }
    }

    //MOSTRAR DATOS EN LOS TEXTFIELD
    public void show() {

        try {
            //Para pasarle el registro de la fila seleccionada, cogemos el valor de la tabla en (Fila seleccionada, Campo 0) y lo pasamos a int
            sqlStudent.studentToResultset((int) viewStudent.getTableStudent()
            .getValueAt(viewStudent.getTableStudent().getSelectedRow(), 0));
            sqlStudent.nextRow();
            viewStudent.getTfRegistry().setText("" + sqlStudent.getResultSet().getInt(1));
            viewStudent.getTfId().setText(sqlStudent.getResultSet().getString(2));
            viewStudent.getTfName().setText(sqlStudent.getResultSet().getString(3));
            viewStudent.getTfFirstSurname().setText(sqlStudent.getResultSet().getString(4));
            viewStudent.getTfSecondSurname().setText(sqlStudent.getResultSet().getString(5));
        } catch (SQLException ex) {
            System.out.println("Error cargando los datos del student en el ResultSet");
        }
    }

    //GETTER
    public ResultSet getResultSet() {
        return sqlStudent.getResultSet();
    }
}
