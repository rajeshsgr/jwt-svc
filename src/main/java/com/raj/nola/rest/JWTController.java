package com.raj.nola.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/jwt")
public class JWTController {

	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@RequestMapping(value = "/token/{uName}",method=RequestMethod.GET)
	public String generateJWT(@PathVariable("uName") String uName) {

		Claims claims = Jwts.claims().setSubject(uName);
		claims.put("auth", "ROLE_ADM");
		
		String token= "Bearer "+Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
		
		return token;

	}
	
	@RequestMapping(value = "/tokenValidate",method=RequestMethod.POST)
	private void parseJWT(@RequestBody String jwt) {
	 
		System.out.println(jwt);
	    //This line will throw an exception if it is not a signed JWS (as expected)
		
		
	    Claims claims = Jwts.parser()         
	       .setSigningKey(jwtSecret)
	       .parseClaimsJws(jwt).getBody();
	    System.out.println("ID: " + claims.getId());
	    System.out.println("Subject: " + claims.getSubject());
	    System.out.println("Issuer: " + claims.getIssuer());
	    System.out.println("Expiration: " + claims.getExpiration());
	    
	    
	    JwsHeader claimHeader = Jwts.parser()         
	 	       .setSigningKey(jwtSecret)
	 	       .parseClaimsJws(jwt).getHeader();
	    
	    System.out.println("Algo: " + claimHeader.getAlgorithm());

	}

}
