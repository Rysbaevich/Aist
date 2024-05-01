package kg.rysbaevich.aist.service.customer.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import kg.rysbaevich.aist.exceptions.ConflictException;
import kg.rysbaevich.aist.service.customer.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String email, String name, String link) {
        if (isNotValid(email)) {
            throw new IllegalArgumentException(String.format("%s is not valid", email));
        }

        String message = buildVerificationEmail(name, link);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(message, true);
            helper.setTo(email);
            helper.setSubject("Confirm your email");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email: ", e);
            throw new ConflictException("Failed to send email");
        }

        log.info("Verification link send to {}", email);
    }

    @Override
    public void sendResetPasswordEmail(String email, String link) {
        if (isNotValid(email)) {
            throw new IllegalArgumentException(String.format("%s is not valid", email));
        }

        String message = buildResetPasswordEmail(link);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(message, true);
            helper.setTo(email);
            helper.setSubject("Reset your password");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email: ", e);
            throw new ConflictException("Failed to send email");
        }

        log.info("Reset password link send to {}", email);
    }

    private boolean isNotValid(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (Exception ex) {
            result = false;
        }
        return !result;
    }


    private String buildVerificationEmail(String name, String link) {
        return """
                        <div style="font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c">
                            <span style="display:none;font-size:1px;color:#fff;max-height:0"></span>
                            <table role="presentation" width="100%" style="border-collapse:collapse;min-width:100%;width:100%!important" cellpadding="0" cellspacing="0" border="0">
                                <tbody>
                                <tr>
                                    <td width="100%" height="53" bgcolor="#0b0c0c">
                                        <table role="presentation" width="100%" style="border-collapse:collapse;max-width:580px" cellpadding="0" cellspacing="0" border="0" align="center">
                                            <tbody>
                                            <tr>
                                                <td width="70" bgcolor="#0b0c0c" valign="middle">
                                                    <table role="presentation" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
                                                        <tbody>
                                                        <tr>
                                                            <td style="padding-left:10px">
                                                                <a href="#">
                                                                    <span style="font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block">Confirm your email</span>
                                                                </a>
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <table role="presentation" class="m_-6186904992287805515content" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%!important" width="100%">
                                <tbody>
                                <tr>
                                    <td width="10" height="10" valign="middle"></td>
                                    <td>
                                        <table role="presentation" width="100%" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
                                            <tbody>
                                            <tr>
                                                <td bgcolor="#1D70B8" width="100%" height="10"></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                    <td width="10" valign="middle" height="10"></td>
                                </tr>
                                </tbody>
                            </table>
                            <table role="presentation" class="m_-6186904992287805515content" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%!important" width="100%">
                                <tbody>
                                <tr>
                                    <td height="30"><br></td>
                                </tr>
                                <tr>
                                    <td width="10" valign="middle"><br></td>
                                    <td style="font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px">
                                        <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Hi, """ + name + """
                                        </p>
                        <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Thank you for registering. Please click on the below link to activate your account:</p>
                        <blockquote style="Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px">
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">
                                <a href=""" + link + """
                                            >Activate Now</a>
                                            </p>
                                        </blockquote>
                                            Link will expire soon.
                                        <p>See you soon</p>
                                    </td>
                                    <td width="10" valign="middle"><br></td>
                                </tr>
                                <tr>
                                    <td height="30"><br></td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="yj6qo"></div>
                            <div class="adL"></div>
                        </div>""";
    }



    private String buildResetPasswordEmail(String link) {
        return """
                        <div style="font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c">
                            <span style="display:none;font-size:1px;color:#fff;max-height:0"></span>
                            <table role="presentation" width="100%" style="border-collapse:collapse;min-width:100%;width:100%!important" cellpadding="0" cellspacing="0" border="0">
                                <tbody>
                                <tr>
                                    <td width="100%" height="53" bgcolor="#0b0c0c">
                                        <table role="presentation" width="100%" style="border-collapse:collapse;max-width:580px" cellpadding="0" cellspacing="0" border="0" align="center">
                                            <tbody>
                                            <tr>
                                                <td width="70" bgcolor="#0b0c0c" valign="middle">
                                                    <table role="presentation" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
                                                        <tbody>
                                                        <tr>
                                                            <td style="padding-left:10px">
                                                                <a href="#">
                                                                    <span style="font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block">Confirm your email</span>
                                                                </a>
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <table role="presentation" class="m_-6186904992287805515content" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%!important" width="100%">
                                <tbody>
                                <tr>
                                    <td width="10" height="10" valign="middle"></td>
                                    <td>
                                        <table role="presentation" width="100%" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
                                            <tbody>
                                            <tr>
                                                <td bgcolor="#1D70B8" width="100%" height="10"></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                    <td width="10" valign="middle" height="10"></td>
                                </tr>
                                </tbody>
                            </table>
                            <table role="presentation" class="m_-6186904992287805515content" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%!important" width="100%">
                                <tbody>
                                <tr>
                                    <td height="30"><br></td>
                                </tr>
                                <tr>
                                    <td width="10" valign="middle"><br></td>
                                    <td style="font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px">
                                        <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Hi</p>
                        <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Please click on the below link to reset your password:</p>
                        <blockquote style="Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px">
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">
                                <a href=""" + link + """
                                            Reset password</a>
                                            </p>
                                        </blockquote>
                                            Link will expire soon.
                                        <p>See you soon</p>
                                    </td>
                                    <td width="10" valign="middle"><br></td>
                                </tr>
                                <tr>
                                    <td height="30"><br></td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="yj6qo"></div>
                            <div class="adL"></div>
                        </div>""";
    }
}
