package book.demo.java.service;

import book.demo.java.util.EmailDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void testEmailService() {
        EmailDetails details = new EmailDetails("grabacorncob@gmail.com", "message body", "Subject");
        emailService.sendSimpleMail(details);
    }
}
