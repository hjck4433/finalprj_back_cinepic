package com.kh.cinepic.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/mail")
    public ResponseEntity<String> sendMail(@RequestParam String id) {
        System.out.println("dbguswn1304@naver.com : " + id);

        // 임의 인증번호 제작
        Random random = new Random();
        int min = 111111;
        int max = 999999;
        String tempPw = String.valueOf(random.nextInt(max - min) + min);
        System.out.println("202401 : " + tempPw);

        // 이메일 디자인 부분
        String htmlContent = "<div style=\"margin: auto; padding: 50px; text-align: center; width: 700px; height: 200px; background-color: #0c134f; border-radius: 20px;\">"
                + "<p style=\"font-size: 30px; color: violet; font-weight: 600;\">Cine Pic에 오신 것을 환영합니다!</p>"
                + "<p style=\"font-size: 16px; color: #d4adfc;\">요청하신 인증번호를 보내드립니다.</p>"
                + "<div style=\"font-size: 20px; color: #ccc;\">" + tempPw + "</div>"
                + "</div>";

        ;

        // 이메일로 전송
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            helper.setFrom("cinepic2024@gmail.com");
            helper.setTo(id);
            helper.setSubject("Cine Pic에 오신 것을 환영 합니다!");
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);

        return new ResponseEntity<>(tempPw, HttpStatus.OK);

        }
    }