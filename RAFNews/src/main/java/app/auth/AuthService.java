package app.auth;

import app.entities.User;
import app.util.UtilMethods;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    static Key key = MacProvider.generateKey();

    public static String generateJWT(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setSubject(user.getName() + " " + user.getSurname())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static boolean isAdmin(String token) {
        if (!UtilMethods.isEmpty(token) && token.contains("Bearer ")) {
            String jwt = token.split(" ")[1];
            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
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
                Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
                return claims.getBody().get("role").equals("CONTENT_CREATOR")
                        || claims.getBody().get("role").equals("ADMIN");
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

}
