package ${basePackageValue}.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtProcess {
	
    private static final String salt = "cgrneedsalt";
    
	public static String getJwtToken(String userId, String userName, String mdmCode) {
		Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("mdmCode", mdmCode);
		JwtBuilder builder= Jwts.builder()
				.setClaims(claims)
				.setId(userId)
				.setSubject(userName)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, salt);
				//.setExpiration(new Date(System.currentTimeMillis() + 60*1000*60*2*12));
		return builder.compact();
	}
	
	public static JwtCheckResUserInfo checkToken(String token) {
		JwtCheckResUserInfo jwtCheckRes = new JwtCheckResUserInfo();
		Claims claims = Jwts.parser().setSigningKey(salt).parseClaimsJws(token).getBody();
		if(null != claims) {
			jwtCheckRes = new JwtCheckResUserInfo(claims.getId(), claims.get("mdmCode").toString(), claims.getSubject());
		}else {
			jwtCheckRes = null;
		}
		return jwtCheckRes;
	}
}
