package com.towers.huellacarbonobackend.service.security;

import com.towers.huellacarbonobackend.entity.data.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final UsuarioService usuarioService;
    private final TemplateEngine templateEngine;
    @Value("${app.front.url}")
    private String appFrontUrl;

    public void enviarCorreoRestablecimiento(Usuario usuario, String password) {
        String token = usuarioService.generateTokenRestablecimiento(usuario);

        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        Context context = new Context();
        context.setVariable("razonSocial", usuario.getEmpresa().getRazonSocial());
        context.setVariable("username", usuario.getUsername());
        context.setVariable("password", password);

        String urlRestablecimiento = appFrontUrl + "/configurationuser/" + token;
        context.setVariable("urlRestablecimiento", urlRestablecimiento);

        String contenidoHtml = templateEngine.process("email-restablecer-clave", context);

        try {
            helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            helper.setTo(usuario.getEmpresa().getCorreo());
            helper.setSubject("Configuración de contraseña - Huella de Carbono");
            helper.setText(contenidoHtml, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(mensaje);
    }
}
