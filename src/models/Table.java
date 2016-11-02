package models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

public class Table extends AbstractTableModel{
 
    ResultSet resultSet;
    ResultSetMetaData metaData; //Contiene información sobre la estructura de un ResulSet,especialmente sobre sus nom campos
    int columnCount;
    int rowCount;
    
    public Table(ResultSet rs){
      this.resultSet=rs;
      try{
          metaData=rs.getMetaData();
          resultSet.last(); //Coloca el cursor en el último registro del resultSet
          rowCount=resultSet.getRow();
          columnCount=metaData.getColumnCount();   
      }
      catch( SQLException sql){
      }
    }
    
    //GETTER
    @Override
    public int getRowCount() {
        return rowCount; 
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
          try {
            resultSet.absolute(fila+1);
            Object o=resultSet.getObject(columna +1);
            return o;
        }
        catch (SQLException sql){
            return sql.toString();
        }  
    }  
}
