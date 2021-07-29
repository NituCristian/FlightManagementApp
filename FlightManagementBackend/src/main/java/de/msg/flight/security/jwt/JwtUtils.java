package de.msg.flight.security.jwt;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import de.msg.flight.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

	public static final long JWT_TOKEN_VALIDITY = 18000;

	// must be 16 bytes key
	private final String keyToStringFormat = "hxdy$mHDDGG1683U";

	private static final String CLAIM_DETAILS = "claimDetails";

	private static final String ENCRYPTION_ALGORITHM = "AES";

	@Value("${jwt.signing.key}")
	private String secret;

	public String getUsernameFromToken(final String token) {

		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(final String token) {

		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {

		final Claims claims = getAllClaimsFromToken(token);

		return claimsResolver.apply(claims);
	}

	public String encodeKey(final String str) {

		final byte[] encoded = Base64.getEncoder().encode(str.getBytes());

		return new String(encoded);
	}

	public String generateToken(final UserDto user) {

		final Map<String, String> claims = new HashMap<>();

		final Claim claim = new Claim(user.getRole(), user.getCompany().getId());

		try {
			claims.put(CLAIM_DETAILS, this.encrypt(claim, this.encodeKey(this.keyToStringFormat)));
		} catch (final InvalidKeyException e) {

			LOGGER.error("The given key is invalid.");
		} catch (final NoSuchAlgorithmException e) {

			LOGGER.error("The given encryption algorithm is invalid.");
		} catch (final NoSuchPaddingException e) {

			LOGGER.error("The given padding is invalid. None was found.");
		} catch (final IllegalBlockSizeException e) {

			LOGGER.error("Illegal block size.");
		} catch (final BadPaddingException e) {

			LOGGER.error("The given padding is invalid.");
		} catch (final JSONException e) {

			LOGGER.error("Malformed jwtToken claims");
		}

		return "Bearer " + generateTokenHelper(claims, user.getUsername());
	}

	public String encrypt(final Claim claim, final String secret) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, JSONException, IllegalBlockSizeException, BadPaddingException {

		final Key key = generateKey(secret);
		final Cipher c = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		final JSONObject jo = new JSONObject();

		c.init(Cipher.ENCRYPT_MODE, key);

		jo.put("companyId", claim.getCompanyId());
		jo.put("role", claim.getRole());

		final String jsonString = jo.toString();
		final byte[] bytes = c.doFinal(jsonString.getBytes());

		return Base64.getEncoder().encodeToString(bytes);
	}

	public String decrypt(final String strToDecrypt, final String secret) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		final Key key = generateKey(secret);

		final Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);

		return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	}

	public Boolean validateToken(final String token, final UserDetails userDetails) {

		final String username = getUsernameFromToken(token);

		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public Boolean validateUserRole(final String token, final UserDetails userDetails) {

		final Claims claim = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

		try {
			final String decryptedClaims = decrypt((String) claim.get(CLAIM_DETAILS),
					this.encodeKey(this.keyToStringFormat));

			final JSONObject jsonClaim = new JSONObject(decryptedClaims);

			return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList()).containsAll(Arrays.asList(jsonClaim.get("role")));
		} catch (final InvalidKeyException e) {

			LOGGER.error("The given key is invalid.");
		} catch (final NoSuchAlgorithmException e) {

			LOGGER.error("The given encryption algorithm is invalid.");
		} catch (final NoSuchPaddingException e) {

			LOGGER.error("The given padding is invalid. None was found.");
		} catch (final IllegalBlockSizeException e) {

			LOGGER.error("Illegal block size.");
		} catch (final BadPaddingException e) {

			LOGGER.error("The given padding is invalid.");
		} catch (final JSONException e) {

			LOGGER.error("Malformed jwtToken claims");
		}

		return false;
	}

	private Claims getAllClaimsFromToken(final String token) {

		return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(final String token) {

		final Date expiration = getExpirationDateFromToken(token);

		return expiration.before(new Date());
	}

	private String generateTokenHelper(final Map<String, String> claims, final String subject) {

		return Jwts.builder().claim(CLAIM_DETAILS, claims.get(CLAIM_DETAILS)).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, this.secret).compact();
	}

	private static Key generateKey(final String secret) {

		final byte[] decoded = Base64.getDecoder().decode(secret.getBytes());

		return new SecretKeySpec(decoded, ENCRYPTION_ALGORITHM);
	}

}
