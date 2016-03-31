package com.gs.config;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *  Contiene los métodos necesarios para la interconexión con Node js.
 * @author SIGSA
 */

//<dependency>
//<groupId>org.jsoup</groupId>
//<artifactId>jsoup</artifactId>
//<version>1.8.3</version>
//</dependency>

public class RequestNodeJS {
    
    private String urlNodeJs;

    /**
     * Establece la ruta del servidor de Node js a la clase.
     * @param urlNodeJs URL del servidor de Node js.
     */
    public void setUrlNodeJs(String urlNodeJs) {
        this.urlNodeJs = urlNodeJs;
    }
   
    /**
     * Realiza una petición a un método en el servidor de Node js, usando un nombre de usuario para notificar
     * y el nombre de método de solicitud.
     * @param username Nombre de usuario al cual se le hará la notificación.
     * @param method Método del servidor de Node js al cual se hará la petición.
     */
    public void sendRequestWithUsernameAndMethod(String username, String method){
        Map<String, String> data = new HashMap<>();
        data.put("usuario", username);
        sendRequestGeneralMethod(data, method);
    }
    
    /**
     * Realiza la petición con nombre de usuario con un mensaje enviado al usuario y el nombre del método de solicitud.
     * @param username Nombre de usuario al cuál se le realizará la notificación.
     * @param message Mensaje de notificación que se le envía al usuario.
     * @param method Nombre del método de la petición en Node js.
     */
    public void sendRequestWithUsernameMessageAndMethod(String username, String message, String method){
        Map<String, String> data = new HashMap<>();
        data.put("usuario", username);
        data.put("message", message);
        sendRequestGeneralMethod(data, method);
    }
    
    /**
     * Realiza una petición a un método específico en el servidor de Node js.
     * @param data HasMap de datos enviados en la petición.
     * @param method Nombre del método de la petición en Node js.
     */
    private void sendRequestGeneralMethod(Map<String,String> data, String method){
        try {
            //Document doc = Jsoup.connect(urlNodeJs + method)
            Jsoup.connect(urlNodeJs + method)
                .data(data)
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .post();
            //System.out.println("\nRespuesta con "+ urlNodeJs + method +" :\n"+doc);
        } catch (Exception ex) {
            System.out.println("RequestNodeJS with method "+ method +" :::  " + ex.getMessage());
        }
    }
}
