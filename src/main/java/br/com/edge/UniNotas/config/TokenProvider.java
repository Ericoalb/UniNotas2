package br.com.edge.UniNotas.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenProvider {

    @Value("${jwt.expiration:86400000}")
    private long expirationTime;

    @Value("${jwt.key:chaveSecretaSuperSeguraParaAssinaturaDoTokenJwtUniNotas2026ComMaisDe256Bits}")
    private String key;

    //Gerar um token

    public String gerarToken(Authentication authentication)
    {
       UserDetails user = (UserDetails) authentication.getPrincipal();
       return buildToken(user.getUsername());
    }

    public String gerarToken(String username)
    {
        return buildToken(username);
    }

    private String buildToken(String username)
    {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey()
    {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    //Obter subject (email) do token

    public String getEmail(String token)
    {
        return getClaims(token).getSubject();
    }

    //Validar um token

    public boolean isTokenValid(String token)
    {
        try
        {
            getClaims(token);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public Claims getClaims(String token)
    {
        //Validar assinatura
        //validar expiration

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
