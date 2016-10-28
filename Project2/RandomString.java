package Project2;

import java.util.Random;

public class RandomString {


	private static final char[] symbols;
	private final Random random = new Random();//random number
	private final char[] buf;//our buffer

	static {
		StringBuilder tmp = new StringBuilder();
		for (char ch = '0'; ch <= '9'; ++ch)
			tmp.append(ch);
		for (char ch = 'a'; ch <= 'z'; ++ch)
			tmp.append(ch);
		symbols = tmp.toString().toCharArray();
	}

	public String nextString() 
	{
		for (int idx = 0; idx < buf.length; ++idx)//fill our predefined buffer with a random symbol, which
			//will be our string
		{
			buf[idx] = symbols[random.nextInt(symbols.length)];
		}
		return new String(buf);
	}
	
	public RandomString(int length) 
	{
		if (length < 1)
			throw new IllegalArgumentException("length < 1: " + length);
		buf = new char[length];
	}
}


