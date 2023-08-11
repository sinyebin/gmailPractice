package com.example.gmailPractice;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import static com.example.gmailPractice.GoogleOAuth.getCredentials;

@Service
public class GmailService {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Gmail API Sample";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public Object getList() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT,JSON_FACTORY,getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        String user = "me";
        ListLabelsResponse listLabelsResponse = service.users().labels().list(user).execute();
        List<Label> labels = listLabelsResponse.getLabels();
        if(labels.isEmpty()){
            return ("No labels found.");
        }else{
            return labels;
        }
    }

    public Object createLabel() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT,JSON_FACTORY,getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        String user = "me";
        Label label = new Label();
        label.setLabelListVisibility("labelShow");
        label.setMessageListVisibility("show");
        label.setName("샘플");
        Label listLabelsResponse = service.users().labels().create(user,label).execute();
        Label labels = listLabelsResponse.clone();
        if(labels.isEmpty()){
            return ("No labels found.");
        }else{
            return labels;
        }
    }

    public Object getLabel() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT,JSON_FACTORY,getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        String user = "me";
        Label listLabelsResponse = service.users().labels().get(user,"Label_6").execute();
        Label labels = listLabelsResponse.clone();
        if(labels.isEmpty()){
            return ("No labels found.");
        }else{
            return labels;
        }
    }


    public Object delLabel() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT,JSON_FACTORY,getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        String user = "me";
        service.users().labels().delete(user,"Label_6").execute();
        return null;
    }


}
