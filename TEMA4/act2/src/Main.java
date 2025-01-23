import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Main {
    public static void main(String[] args) {
        // Enviar un correo usando SMTP
        enviarCorreoSMTP("tu_correo@gmail.com", "tu_contraseña", "destinatario@gmail.com");

        // Realizar una solicitud HTTP GET
        realizarSolicitudHTTP("http://www.example.com");
    }

    public static void enviarCorreoSMTP(String user, String pass, String destinatario) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Prueba de correo SMTP");
            message.setText("Este es un correo de prueba enviado desde Java usando SMTP.");

            Transport.send(message);
            System.out.println("Correo enviado correctamente.");
        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    public static void realizarSolicitudHTTP(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println("Código de respuesta HTTP: " + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            System.out.println("Error al realizar la solicitud HTTP: " + e.getMessage());
        }
    }
}
