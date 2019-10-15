package com.bridgelabz.fundoo.utility;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

@Component
public class TokenUtil {

	public final String TOKEN_SECRET = "Pratiksha";

	/**
	 * create token
	 * 
	 * @param emailId
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws IllegalArgumentException
	 */
	public String createToken(String emailId) {
		try {
			// to set algorithm
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

			String token = JWT.create().withClaim("emailId", emailId).sign(algorithm);
			return token;
		} catch (JWTCreationException exception) {
			exception.printStackTrace();
			// log Token Signing Failed
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param token
	 * @return
	 */
	public String decodeToken(String token) {

		String emailId;
		// for verification algorithm
		Verification verification = null;
		try {
			verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		}
		JWTVerifier jwtverifier = verification.build();
		// to decode token
		DecodedJWT decodedjwt = jwtverifier.verify(token);

		Claim claim = decodedjwt.getClaim("emailId");
		emailId = claim.asString();
		return emailId;

//		System.out.println("token is: " + token);
//		String[] split_string = token.split("\\.");
//		String base64EncodedHeader = split_string[0];
//		String base64EncodedBody = split_string[1];
//		String base64EncodedSignature = split_string[2];
//		
//		System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
//		Base64 base64Url = new Base64(true);
//		String header = new String(base64Url.decode(base64EncodedHeader));
//		System.out.println("JWT Header : " + header);
//		
//		System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
//		String body = new String(base64Url.decode(base64EncodedBody));
//		System.out.println("JWT Body : " + body);
//
//		return body;

	}
}
