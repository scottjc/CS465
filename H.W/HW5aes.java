import java.nio.charset.Charset;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class HW5aes {

	public static String toHexString(byte[] ba) {
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < ba.length; i++)
			str.append(String.format("%02x", ba[i]));
		return str.toString();
	}

	public static void main(String[] args) throws Exception {
		//H.W. #5 AES.
		//https://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html
		//http://stackoverflow.com/questions/13102788/is-there-any-sample-java-code-that-does-aes-encryption-exactly-like-this-website 
		/*
			GGGGGGGGGGGGGGG
			GGGGGGGGGGGGGGG
			GGGGGGGGGGGGGGG
			GGGGGGGGGGGGGGG
			AAAAAAAAAAAAAAA
			C
		 */

		String keyHex = "00000000000000000000000000123456";
		String plainText = "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGAAAAAAAAAAAAAAAC";
		System.out.println(plainText);
		String plaintextHex = toHexString(plainText.getBytes(Charset.forName("UTF-8")));//put as a byte array. To Hex	
		//47474747474747474747474747474747474747474747474747474747474747474747474747474747474747474747474747474747474747474747474741414141414141414141414141414143
		
		
		SecretKey key = new SecretKeySpec(DatatypeConverter
				.parseHexBinary(keyHex), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding ");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] result = cipher.doFinal(DatatypeConverter
				.parseHexBinary(plaintextHex));
		

		System.out.println(DatatypeConverter.printHexBinary(result));
		//E1692336570F215D1E4C729611A7AC0FE1692336570F215D1E4C729611A7AC0FE1692336570F215D1E4C729611A7AC0F3D90F45AA5FFAB16C8FFFC7101DB16E073D90664985A3CD0D9A9E21C800B644D
	
		cipher.init(Cipher.DECRYPT_MODE, key);
	    byte[] decryptedBytes = cipher.doFinal(result);
	    String decryptedText = new String(decryptedBytes, "UTF8");
	    
	    System.out.println(decryptedText);
	}
}