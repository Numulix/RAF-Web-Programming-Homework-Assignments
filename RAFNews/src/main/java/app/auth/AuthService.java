package app.auth;

import app.entities.User;
import app.util.UtilMethods;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    static String clientSecret = "secretKey";
    static byte[] shared = Base64.getEncoder().encode(clientSecret.getBytes(StandardCharsets.UTF_8));

    public static String generateJWT(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("surname", user.getSurname());
        claims.put("role", user.getRole());
        claims.put("status", user.getStatus());

        return Jwts.builder()
                .setSubject(user.getName() + " " + user.getSurname())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, shared)
                .compact();
    }

    public static boolean isAdmin(String token) {
        if (!UtilMethods.isEmpty(token) && token.contains("Bearer ")) {
            String jwt = token.split(" ")[1];
            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(shared).parseClaimsJws(jwt);
                return claims.getBody().get("role").equals("ADMIN");
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    public static boolean isAuthorized(String token) {
        if (!UtilMethods.isEmpty(token) && token.contains("Bearer ")) {
            String jwt = token.split(" ")[1];
            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(shared).parseClaimsJws(jwt);
                return (claims.getBody().get("role").equals("CONTENT_CREATOR")
                        || claims.getBody().get("role").equals("ADMIN"))
                        && claims.getBody().get("status").equals("ACTIVE");
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

}
