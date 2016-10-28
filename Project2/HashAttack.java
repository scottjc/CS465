package Project2;

import java.util.*;

public class HashAttack 
{
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static void main(String[] args) throws Exception
	{
		//Do test for bit size = 4
		//preImageTestWrapper(4, 100);

		//Do test for bit size = 8
		//preImageTestWrapper(8, 100);
		
		//Do test for bit size = 10
		//preImageTestWrapper(10, 100);

		//Do test for bit size = 12
		//preImageTestWrapper(12, 100);
		
		//Do test for bit size = 14
		//preImageTestWrapper(14, 10);

		//Do test for bit size = 16
		preImageTestWrapper(16, 10);
		
		//Do test for bit size = 18
		//preImageTestWrapper(18, 10);

		//Do test for bit size = 20
		//preImageTestWrapper(20, 10);
		
		//Do test for bit size = 22
		//preImageTestWrapper(22, 10);

		//Do test for bit size = 24
		//preImageTestWrapper(24, 3);
		


		
		//Do test for bit size = 4
		//collisionTestWrapper(4, 100);

		//Do test for bit size = 8
		//collisionTestWrapper(8, 100);

		//Do test for bit size = 12
		//collisionTestWrapper(12, 100);

		//Do test for bit size = 16
		//collisionTestWrapper(16, 100);

		//Do test for bit size = 20
		//collisionTestWrapper(20, 100);

		//Do test for bit size = 24
		//collisionTestWrapper(24, 100);

		//Do test for bit size = 28
		//collisionTestWrapper(28, 100);

		//Do test for bit size = 32
		//collisionTestWrapper(32, 5);

		//Do test for bit size = 36
		//collisionTestWrapper(36, 5);

		//Do test for bit size = 40
		//collisionTestWrapper(40, 5);

	}
	
	//provided encryption code
	public static byte[] encrypt(String x) throws Exception 
	{
		java.security.MessageDigest d = null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return d.digest();
	}
	
	//gets an average
	public static int getAverage(List<Integer> input) 
	{
		int result = 0;
		for(Iterator<Integer> i = input.iterator(); i.hasNext();) 
		{
			result += i.next();
		}
		return result / input.size();
	}

	//wrapper for preImage
	public static void preImageTestWrapper(int bitSize, int numRuns) throws Exception 
	{
		List<Integer> runs = new ArrayList<>();
		for(int i = 0; i < numRuns; i++) 
		{
			runs.add(preImageAttack(bitSize));
		}
		int output = getAverage(runs);
		System.out.println("For bit size = " + bitSize + " with " + numRuns + " runs: " + output);
	}

	//wrapper for collisionTest
	public static void collisionTestWrapper(int bitSize, int numRuns) throws Exception 
	{
		List<Integer> runs = new ArrayList<>();
		for(int i = 0; i < numRuns; i++) 
		{
			runs.add(collisionAttack(bitSize));
		}
		int output = getAverage(runs);
		System.out.println("For bit size = " + bitSize + " with " + numRuns + " runs: " + output);
	}

	//returns number of iterations to find a collision against a certain string for the number of bits given (needs to be a multiple of 4!!!)
	public static int preImageAttack(int numBits) throws Exception
	{
		
		RandomString randomStringGen = new RandomString(10);
		int count = 0;
		HashMap<String, String> hashes = new HashMap<>();
		boolean found = false;
		boolean putIn= false;

		while(!found) 
		{
			String str = randomStringGen.nextString();
			String truncHash = bytesToHex(encrypt(str)).substring(0, numBits / 4);
			if(hashes.get(truncHash) != null) 
			{
				found = true;
				//System.out.println("Found collision with inputs: " + str + " & " + hashes.get(truncHash) + " at hash: " + truncHash);
			}
			else if(putIn == false)
			{
				hashes.put(truncHash, str);
				putIn = true;
			}
			count++;
		}
		return count;
	}

	//returns number of iterations to find a collision for a hash of the number of bits given (needs to be a multiple of 4!!!)
	public static int collisionAttack(int numBits) throws Exception
	{
		RandomString randomStringGen = new RandomString(10);
		int count = 0;
		HashMap<String, String> hashes = new HashMap<>();
		boolean found = false;

		while(!found) 
		{
			String str = randomStringGen.nextString();
			String truncHash = bytesToHex(encrypt(str)).substring(0, numBits / 4);
			if(hashes.get(truncHash) != null) 
			{
				found = true;
				//System.out.println("Found collision with inputs: " + str + " & " + hashes.get(truncHash) + " at hash: " + truncHash);
			}
			else hashes.put(truncHash, str);
			count++;
		}
		return count;
	}

	public static String bytesToHex(byte[] bytes) 
	{
		char[] hexChars = new char[bytes.length * 2];

		for ( int j = 0; j < bytes.length; j++ ) 
		{
			int pos = j * 2;
			int v = bytes[j] & 0xFF;//AND them with 0xFF, then shift 4
			hexChars[pos] = hexArray[v >>> 4];
			hexChars[pos + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
