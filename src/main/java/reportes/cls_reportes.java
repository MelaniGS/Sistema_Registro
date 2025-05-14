/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import java.sql.DriverManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ASUS
 */
public class cls_reportes {
    private static final String URL = "jdbc:mysql://localhost:3306/persona_database?zeroDateTimeBehavior=CONVERT_TO_NULL [root on Default schema]";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static java.sql.Connection connection = null;

    public JasperViewer ReporteCostoCompraProducto() {
        JasperViewer obj = null;
        try {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("conectado");
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(cls_reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("src\\main\\java\\reportes\\imprimirFactura.jasper");
            JasperPrint printer = JasperFillManager.fillReport(reporte, null, connection);
            obj = new JasperViewer(printer, false);
            obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            obj.setVisible(true);

        } catch (JRException ex) {
            java.util.logging.Logger.getLogger(cls_reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return obj;
    }
}
