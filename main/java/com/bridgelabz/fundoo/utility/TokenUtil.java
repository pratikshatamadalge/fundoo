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
	}
}
