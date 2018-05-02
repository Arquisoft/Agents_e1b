package asw.agents.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hasher {
	
	public static String convertToMD5(String in) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(in.getBytes(), 0, in.length());
		return new BigInteger(1, digest.digest()).toString(16);
	}
}