package com.banco.Saint_Patrik.Service;

import com.banco.Saint_Patrik.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * MÉTODO PARA PODER ENVIAR UN MAIL
     *
     * METHOD TO BE ABLE TO SEND AN EMAIL
     *
     * @param user1
     * @param user2
     * @param to
     * @param amount
     * @param decription
     */
    public void sendMail(String to, UserEntity user1, UserEntity user2, Double amount, String decription) throws MailException {

        String from = "bancosaintpatrick@gmail.com";
        String subject = "TRANSACTION CONFIRMATION";

        String value = String.format("$%,.2f", amount);

        String body = "\n\nTRANSFER DETAILS: "
                + "\n\nFrom: " + user1.getName() + " " + user1.getSurname()
                + "\nMail: " + user1.getMail()
                + "\n\nTo: " + user2.getName() + " " + user2.getSurname()
                + "\nMail: " + user2.getMail()
                + "\n\nAmount: " + value
                + "\n\nReason: " + decription;

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);
    }

}



