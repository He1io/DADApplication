package connectionSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Helio
 */

//PATRÓN DE DISEÑO SINGLETON
public class ConnectionSQL{
    //PROPIEDADES
    private static ConnectionSQL instance=null;
    private Connection connection;
    
    //CONSTRUCTOR
    protected ConnectionSQL(){
        //Solo existe para evitar nuevas instancias
    }
    
    //GET INSTANCE
    public static ConnectionSQL getInstance(){
        if(instance==null){
            instance=new ConnectionSQL();
        }
        return instance;
    }
    
    //ABRIR CONEXIÓN
    public void openConnection() {
        try { 
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "root");

        } catch (SQLException sql){
            System.out.println("Error abriendo la conexión.");
            System.err.println(sql.getMessage());
            connection = null;
        }
    }
    
    //CERRAR CONEXION
    public void closeConexion(){
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Error cerrando la connection");
        }
    }
  
    //GETTER
    public Connection getConexion() {
        
        return connection;
    }
}