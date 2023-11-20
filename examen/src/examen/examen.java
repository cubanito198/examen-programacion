package examen;

import java.util.logging.Logger;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import javax.swing.JPanel;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.DriverManager;

public class examen extends JPanel  {
@Override public void paint(Graphics g){
    super.paint(g);
    Graphics2D misgraficos = (Graphics2D) g;
    int basegrafica = 360;
    misgraficos.drawLine(10, 10, 10, 360);           
    misgraficos.drawLine(10, basegrafica, 360, basegrafica);
    

    int[] barras = new int[]{100,300,200,200,100,200,50,200,25,50,100};
        String url = "jdbc:sqlite:/Users/LUIS/Desktop/CLASE/2 bases de datos/Euromillones.db";
        String user = ""; 
        String password = "";

       
       

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("que numeros debes de juagar para ganar.");
           
            try (Statement statement = connection.createStatement()) {      
                String query = "";      
                String[] coleccion = new String[]{"n1","n2","n3","n4","n5","e1","e2"};
                int contador = 0;
                for(String elemento : coleccion){
                    contador++;
                  
                    query = "SELECT COUNT("+elemento+") as total,"+elemento+" AS numero FROM Euromillones GROUP BY "+elemento+" ORDER BY COUNT("+elemento+") DESC LIMIT 1";
                    try (ResultSet resultSet = statement.executeQuery(query)) {
                        while (resultSet.next()) {
                        int total = resultSet.getInt("total");
                        int numero = resultSet.getInt("numero");
                        System.out.println("En la tabla "+elemento+" deberias juagr el numero"+numero+" porque ha salido todas estas veces: "+total+"");
                        misgraficos.fillRect(contador*30+20, basegrafica-total, 20, total);
                        }
                    }
                }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
       
    } catch (SQLException ex) {
        Logger.getLogger(examen.class.getName()).log(Level.SEVERE, null, ex);
      }

  }
    public static void main(String[] args) throws SQLException  {
    JFrame marco = new JFrame("grafica");
    examen mimarco = new examen();
    marco.add(mimarco);
    marco.setSize(400,400);
    marco.setVisible(true);
    marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}
}