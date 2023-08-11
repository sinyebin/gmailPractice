package com.example.gmailPractice;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import static com.example.gmailPractice.GoogleOAuth.getCredentials;

@Service
public class SendMailService {


    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Gmail API Sample";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Create a draft email.
     *
     * @param fromEmailAddress - Email address to appear in the from: header
     * @param toEmailAddress   - Email address of the recipient
     * @return the created draft, {@code null} otherwise.
     * @throws MessagingException - if a wrongly formatted address is encountered.
     * @throws IOException        - if service account credentials file not found.
     */

    public Draft createDraftMessage(String fromEmailAddress,
                                           String toEmailAddress)
            throws MessagingException, IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT,JSON_FACTORY,getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        // Create the email content
        String messageSubject = "초안 메시지 샘플";
        String bodyText = "이건 메시지 메시지";

        // Encode as MIME message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(fromEmailAddress));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(toEmailAddress));
        email.setSubject(messageSubject);
        email.setText(bodyText);

        // Encode and wrap the MIME message into a gmail message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message message = new Message();
        message.setRaw(encodedEmail);

        try {
            // Create the draft message
            Draft draft = new Draft();
            draft.setMessage(message);
            draft = service.users().drafts().create("me", draft).execute();
            System.out.println("Draft id: " + draft.getId());
            System.out.println(draft.toPrettyString());
            return draft;
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to create draft: " + e.getMessage());
            } else {
                throw e;
            }
        }
        return null;
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param toEmailAddress   email address of the receiver
     * @param fromEmailAddress email address of the sender, the mailbox account
     * @param subject          subject of the email
     * @param bodyText         body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException - if a wrongly formatted address is encountered.
     */
    public static MimeMessage createEmail(String toEmailAddress,
                                          String fromEmailAddress,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(fromEmailAddress));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(toEmailAddress));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException        - if service account credentials file not found.
     * @throws MessagingException - if a wrongly formatted address is encountered.
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param fromEmailAddress - Email address to appear in the from: header
     * @param toEmailAddress   - Email address of the recipient
     * @return the sent message, {@code null} otherwise.
     * @throws MessagingException - if a wrongly formatted address is encountered.
     * @throws IOException        - if service account credentials file not found.
     */
    public static Message sendEmail(String fromEmailAddress,
                                    String toEmailAddress,
                                    String subject,
                                    String bodyText)
            throws MessagingException, IOException, GeneralSecurityException {
        /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
            guides on implementing OAuth2 for your application.*/
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT,JSON_FACTORY,getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Create the email content
//        String messageSubject = "Test message";
//        String bodyText = "lorem ipsum.";

        // Encode as MIME message
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//        MimeMessage email = new MimeMessage(session);
//        email.setFrom(new InternetAddress(fromEmailAddress));
//        email.addRecipient(javax.mail.Message.RecipientType.TO,
//                new InternetAddress(toEmailAddress));
//        email.setSubject(messageSubject);
//        email.setText(bodyText);
        MimeMessage mimeMessage=createEmail(fromEmailAddress,fromEmailAddress,subject,bodyText);

        // Encode and wrap the MIME message into a gmail message
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        email.writeTo(buffer);
//        byte[] rawMessageBytes = buffer.toByteArray();
//        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
//        Message message = new Message();
//        message.setRaw(encodedEmail);
        Message message = createMessageWithEmail(mimeMessage);

        try {
            // Create send message
            message = service.users().messages().send("me", message).execute();
            System.out.println("Message id: " + message.getId());
            System.out.println(message.toPrettyString());
            return message;
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }
        return null;
    }
}
