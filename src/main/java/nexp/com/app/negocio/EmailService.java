/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.negocio;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class EmailService {
    private final String direccionServidorEmail="smtp.gmail.com";
    private final String puertoServidor="587";
    private Properties props = new Properties();
    private String emailUsuarioEmisor;
    private String claveEmailUsuarioEmisor;

    public EmailService(String emailUsuarioEmisor, String claveEmailUsuarioEmisor) {
        this.emailUsuarioEmisor = emailUsuarioEmisor;
        this.claveEmailUsuarioEmisor = claveEmailUsuarioEmisor;
        inicializarPropiedades();
    }

    public EmailService() {
    }
    private void inicializarPropiedades()
    {

        props.setProperty("mail.smtp.host", this.direccionServidorEmail);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", this.puertoServidor);
        props.setProperty("mail.smtp.user", this.emailUsuarioEmisor);
        props.setProperty("mail.smtp.auth", "true");

    }
    public void enviarEmail(String receptor, String asunto, String cuerpoMensaje)
    {
        try
        {
            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.emailUsuarioEmisor));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(receptor));
            message.setContent(cuerpoMensaje,"text/html");
            message.setSubject(asunto);
            Transport t = session.getTransport("smtp");
            t.connect(this.emailUsuarioEmisor, this.claveEmailUsuarioEmisor);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public String getClaveEmailUsuarioEmisor() {
        return claveEmailUsuarioEmisor;
    }

    public void setClaveEmailUsuarioEmisor(String claveEmailUsuarioEmisor) {
        this.claveEmailUsuarioEmisor = claveEmailUsuarioEmisor;
    }

    public String getEmailUsuarioEmisor() {
        return emailUsuarioEmisor;
    }

    public void setEmailUsuarioEmisor(String emailUsuarioEmisor) {
        this.emailUsuarioEmisor = emailUsuarioEmisor;
    }




}
