package sql;

import connectionSQL.ConnectionSQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import models.Student;

/**
 *
 * @author Helio
 */
public class SQLStudent {
    
    ConnectionSQL connection = ConnectionSQL.getInstance();
    ResultSet resultSet;
    
    public SQLStudent(){
        
        ConnectionSQL.getInstance().openConnection();
        //Abrir la conexión
        if(connection.getConexion()==null){
            System.out.print("La conexión no está abierta");
            System.exit(1);
        }
    }
    
    //CONSULTA
    public void executeQuery(String sql) {

        try {
            Statement statement = connection.getConexion().createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Error ejecutando al consulta.");
        }
    }  
    
    public void query(){

        executeQuery("select * from alumnos");
    }
    
    
    //MODIFICACIONES
    public int updateDatabase(String sql) {
        
        try {
            Statement statement = connection.getConexion().createStatement();   
            return statement.executeUpdate(sql);
            //Devuelve el número de la fila modificada, ó 0 si no realizó la consulta
        } catch (SQLException ex) {
            System.out.println("Error modificando la base de datos");
            return -1;
        }
    }
 
    public void update(Student student){
    
        updateDatabase("update alumnos set dni = '" + student.getId() + "',nombre = '" + student.getName() +
        "', apellido1 = '" + student.getFirstSurname()+ "', apellido2 = '" + student.getSecondSurname()+ "' " +
        "where registro =" + student.getRegistry());
    }
     
    public void insert(Student student){
    
        updateDatabase("insert into alumnos values(null,'" + student.getId() + "','" + student.getName() +
        "','" + student.getFirstSurname() + "','" + student.getSecondSurname() + "')");
    }
    
    public void delete(Student student){
    
       updateDatabase("delete from alumnos where registro =" + student.getRegistry());
    }

    //CARGAR EL RESULTSET CON LOS DATOS DE LA FILA SELECCIONADA
    public void studentToResultset(int registro){
        executeQuery("select * from students where registro = "+registro);
    }
    
    //PASAR AL SIGUIENTE REGISTRO DEL RESULTSET
    public void nextRow(){
        try {
            resultSet.next();
        } catch (SQLException ex) {
            System.out.println("Error pasando al siguiente registro");
        }
    }
    
    //GETTER
    public ResultSet getResultSet() {
        return resultSet;
    }
     
    //CERRAR CONEXION
    public void closeConnection(){
       connection.closeConexion();
    }
}
