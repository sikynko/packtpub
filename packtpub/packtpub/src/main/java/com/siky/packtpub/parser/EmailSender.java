package com.siky.packtpub.parser;

import com.siky.packtpub.com.siky.packtpub.model.EmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component("emailComponent")
public class EmailSender implements Command<EmailDTO, Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    @Value("${email.from}")
    private String from;

    @Value("${email.recipient}")
    private String recipient;


    @Autowired
    private JavaMailSender sender;

    @Override
    public Void execute(EmailDTO emailDTO) throws MailSendException, MessagingException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            LOGGER.info("Sending: " + emailDTO.toString());
            String body = "Title: " + emailDTO.getTitle() + "\r\n\nDescription: "+emailDTO.getDescription();
            helper.setTo(recipient);
            helper.setFrom(from);
            helper.setText(body);
            helper.setSubject("Packtpub.com new book : " + emailDTO.getTitle());

        } catch (MessagingException e) {
            throw new MessagingException("(MessagingException) Wrong variables", e);
        }

        try {
            sender.send(message);
            LOGGER.info("Email has been sent");
        } catch (MailException e) {
            throw new MailSendException("(MessagingException)Couldn't send email", e);
        }

        return null;
    }
}
