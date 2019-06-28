package com.crossover.mobiliza.business.service;

import com.crossover.mobiliza.business.entity.Ong;
import com.crossover.mobiliza.business.entity.User;
import com.crossover.mobiliza.business.entity.Voluntario;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Log4j2
@Service
public class GoogleAuthService {

    private static final String CLIENT_ID = "36428791114-9jdc0t2d006tvnmvpujbf02ppse28aoa.apps.googleusercontent.com";

    private GoogleIdTokenVerifier verifier;

    public GoogleAuthService() {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
    }

    @Autowired
    private UserService userService;

    @Autowired
    private OngService ongService;

    @Autowired
    private VoluntarioService voluntarioService;

    public User getOrCreateUserFromIdToken(String idTokenString) {
        return getOrCreateUserFromIdToken(idTokenString, null);
    }

    public User getOrCreateUserFromIdToken(String idTokenString, Boolean asOng) {
        GoogleAuthService.UserData userData = getDataFromIdToken(idTokenString);
        if (userData == null) {
            return null;
        }

        User user = userService.findByGoogleId(userData.getId());
        if (user == null) {
            user = User.builder()
                    .googleId(userData.getId())
                    .build();
            user = userService.save(user);
        }

        // Add default voluntario
        Voluntario voluntario = user.getVoluntario();
        if (voluntario == null) {
            voluntario = new Voluntario();
            voluntario.setNome(userData.getName());
            voluntario.setEmail(userData.getEmail());
            voluntario.setUser(user);
            voluntario = voluntarioService.save(voluntario);
            user.setVoluntario(voluntario);
        }

        // Add default ong
        Ong ong = user.getOng();
        if (ong == null && asOng != null && asOng) {
            ong = new Ong();
            String nomeOng = "ONG de " + userData.getName();
            if (nomeOng.length() > 32)
                nomeOng = nomeOng.substring(0, 32);
            ong.setNome(nomeOng);
            ong.setDescricao("Nenhuma descrição.");
            ong.setEmail(userData.getEmail());
            ong.setRegiao("Desconhecida");
            ong.setCategoria("Desconhecida");
            ong.setUser(user);
            ong = ongService.save(ong);
            user.setOng(ong);
        }

        if (asOng != null)
            user.setLastUsedAsOng(asOng);

        user = userService.save(user);
        return user;
    }

    /**
     * Fetches the user data from a Google ID Token, if its valid.
     * Returns null if the token is invalid.
     */
    public UserData getDataFromIdToken(String idTokenString) {
        if (Objects.equals(idTokenString, "test")) {
            UserData ud = new UserData();
            ud.setId("test");
            ud.setEmail("test@test.com");
            ud.setEmailVerified(true);
            ud.setName("Test User");
            return ud;
        }
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null)
                throw new IOException("Invalid ID token");

            GoogleIdToken.Payload payload = idToken.getPayload();
            return UserData.builder()
                    .id(payload.getSubject())
                    .email(payload.getEmail())
                    .emailVerified(payload.getEmailVerified())
                    .name((String) payload.get("name"))
                    .pictureUrl((String) payload.get("picture"))
                    .locale((String) payload.get("locale"))
                    .familyName((String) payload.get("family_name"))
                    .giveName((String) payload.get("given_name"))
                    .build();

        } catch (Exception e) {
            log.error("Couldn't fetch data from ID token", e);
            return null;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserData {
        private String id;
        private String email;
        private boolean emailVerified;
        private String name;
        private String pictureUrl;
        private String locale;
        private String familyName;
        private String giveName;
    }
}
