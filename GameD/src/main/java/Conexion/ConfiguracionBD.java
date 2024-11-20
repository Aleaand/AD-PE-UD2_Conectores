package Conexion;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author Alejandra
 */
public class ConfiguracionBD {

    /**
     * Carga una configuración desde un archivo XML.
     *
     * @param archivo Ruta del archivo XML desde el que se cargará la
     * configuración.
     * @return La configuración cargada desde el XML.
     * @throws Exception Si ocurre un error al leer o parsear el archivo XML.
     */
    public static Configuracion cargarDesdeXML(String archivo) throws Exception {
        File xmlFile = new File(archivo);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        document.getDocumentElement().normalize();

        Configuracion config = new Configuracion();
        config.setHost(document.getElementsByTagName("host").item(0).getTextContent());
        config.setPort(Integer.parseInt(document.getElementsByTagName("port").item(0).getTextContent()));
        config.setUser(document.getElementsByTagName("user").item(0).getTextContent());
        config.setPass(document.getElementsByTagName("pass").item(0).getTextContent());
        config.setNick_name(document.getElementsByTagName("nick_name").item(0).getTextContent());

        return config;
    }

    /**
     * Guarda una configuración en un archivo XML.
     *
     * @param configuracion La configuración a guardar.
     * @param archivo La ruta del archivo donde se guardará la configuración.
     * @throws Exception Si ocurre un error al guardar el archivo XML.
     */
    public static void guardarConfiguracionEnXML(Configuracion configuracion, String archivo) throws Exception {
        try {
            JAXBContext context = JAXBContext.newInstance(Configuracion.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Para formato legible

            // Crear archivo
            File archivoXML = new File(archivo + ".xml");
            marshaller.marshal(configuracion, archivoXML);

            System.out.println("Archivo XML guardado correctamente en: " + archivoXML.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error al guardar XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
