package backend;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	
	public static String getHash(String hashingString) throws NoSuchAlgorithmException {
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(
		  hashingString.getBytes(StandardCharsets.UTF_8));
		
		String s = new String(encodedhash, StandardCharsets.UTF_8);
		
		return s;

	}

}
