package Project1;

import java.util.Arrays;

public class AES 
{

	public static byte[] rcon = { (byte) 0x00, // Rcon[] is 1-based, so the first entry is just a place holder. Used in Rotword()
			(byte) 0x01, (byte) 0x02, (byte) 0x04, (byte) 0x08, 
			(byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x80, 
			(byte) 0x1B, (byte) 0x36, (byte) 0x6C, (byte) 0xD8, 
			(byte) 0xAB, (byte) 0x4D, (byte) 0x9A, (byte) 0x2F, 
			(byte) 0x5E, (byte) 0xBC, (byte) 0x63, (byte) 0xC6, 
			(byte) 0x97, (byte) 0x35, (byte) 0x6A, (byte) 0xD4, 
			(byte) 0xB3, (byte) 0x7D, (byte) 0xFA, (byte) 0xEF, 
			(byte) 0xC5, (byte) 0x91, (byte) 0x39, (byte) 0x72, 
			(byte) 0xE4, (byte) 0xD3, (byte) 0xBD, (byte) 0x61, 
			(byte) 0xC2, (byte) 0x9F, (byte) 0x25, (byte) 0x4A, 
			(byte) 0x94, (byte) 0x33, (byte) 0x66, (byte) 0xCC, 
			(byte) 0x83, (byte) 0x1D, (byte) 0x3A, (byte) 0x74, 
			(byte) 0xE8, (byte) 0xCB, (byte) 0x8D};

	public static byte[][] sBox = {
			{ (byte)  0x63, (byte)  0x7c, (byte)  0x77, (byte)  0x7b, (byte)  0xf2, (byte)  0x6b, (byte)  0x6f, (byte)  0xc5, (byte)  0x30, (byte)  0x01, (byte)  0x67, (byte)  0x2b, (byte)  0xfe, (byte)  0xd7, (byte)  0xab, (byte)  0x76 } ,
			{ (byte)  0xca, (byte)  0x82, (byte)  0xc9, (byte)  0x7d, (byte)  0xfa, (byte)  0x59, (byte)  0x47, (byte)  0xf0, (byte)  0xad, (byte)  0xd4, (byte)  0xa2, (byte)  0xaf, (byte)  0x9c, (byte)  0xa4, (byte)  0x72, (byte)  0xc0 } ,
			{ (byte)  0xb7, (byte)  0xfd, (byte)  0x93, (byte)  0x26, (byte)  0x36, (byte)  0x3f, (byte)  0xf7, (byte)  0xcc, (byte)  0x34, (byte)  0xa5, (byte)  0xe5, (byte)  0xf1, (byte)  0x71, (byte)  0xd8, (byte)  0x31, (byte)  0x15 } ,
			{ (byte)  0x04, (byte)  0xc7, (byte)  0x23, (byte)  0xc3, (byte)  0x18, (byte)  0x96, (byte)  0x05, (byte)  0x9a, (byte)  0x07, (byte)  0x12, (byte)  0x80, (byte)  0xe2, (byte)  0xeb, (byte)  0x27, (byte)  0xb2, (byte)  0x75 } ,
			{ (byte)  0x09, (byte)  0x83, (byte)  0x2c, (byte)  0x1a, (byte)  0x1b, (byte)  0x6e, (byte)  0x5a, (byte)  0xa0, (byte)  0x52, (byte)  0x3b, (byte)  0xd6, (byte)  0xb3, (byte)  0x29, (byte)  0xe3, (byte)  0x2f, (byte)  0x84 } ,
			{ (byte)  0x53, (byte)  0xd1, (byte)  0x00, (byte)  0xed, (byte)  0x20, (byte)  0xfc, (byte)  0xb1, (byte)  0x5b, (byte)  0x6a, (byte)  0xcb, (byte)  0xbe, (byte)  0x39, (byte)  0x4a, (byte)  0x4c, (byte)  0x58, (byte)  0xcf } ,
			{ (byte)  0xd0, (byte)  0xef, (byte)  0xaa, (byte)  0xfb, (byte)  0x43, (byte)  0x4d, (byte)  0x33, (byte)  0x85, (byte)  0x45, (byte)  0xf9, (byte)  0x02, (byte)  0x7f, (byte)  0x50, (byte)  0x3c, (byte)  0x9f, (byte)  0xa8 } ,
			{ (byte)  0x51, (byte)  0xa3, (byte)  0x40, (byte)  0x8f, (byte)  0x92, (byte)  0x9d, (byte)  0x38, (byte)  0xf5, (byte)  0xbc, (byte)  0xb6, (byte)  0xda, (byte)  0x21, (byte)  0x10, (byte)  0xff, (byte)  0xf3, (byte)  0xd2 } ,
			{ (byte)  0xcd, (byte)  0x0c, (byte)  0x13, (byte)  0xec, (byte)  0x5f, (byte)  0x97, (byte)  0x44, (byte)  0x17, (byte)  0xc4, (byte)  0xa7, (byte)  0x7e, (byte)  0x3d, (byte)  0x64, (byte)  0x5d, (byte)  0x19, (byte)  0x73 } ,
			{ (byte)  0x60, (byte)  0x81, (byte)  0x4f, (byte)  0xdc, (byte)  0x22, (byte)  0x2a, (byte)  0x90, (byte)  0x88, (byte)  0x46, (byte)  0xee, (byte)  0xb8, (byte)  0x14, (byte)  0xde, (byte)  0x5e, (byte)  0x0b, (byte)  0xdb } ,
			{ (byte)  0xe0, (byte)  0x32, (byte)  0x3a, (byte)  0x0a, (byte)  0x49, (byte)  0x06, (byte)  0x24, (byte)  0x5c, (byte)  0xc2, (byte)  0xd3, (byte)  0xac, (byte)  0x62, (byte)  0x91, (byte)  0x95, (byte)  0xe4, (byte)  0x79 } ,
			{ (byte)  0xe7, (byte)  0xc8, (byte)  0x37, (byte)  0x6d, (byte)  0x8d, (byte)  0xd5, (byte)  0x4e, (byte)  0xa9, (byte)  0x6c, (byte)  0x56, (byte)  0xf4, (byte)  0xea, (byte)  0x65, (byte)  0x7a, (byte)  0xae, (byte)  0x08 } ,
			{ (byte)  0xba, (byte)  0x78, (byte)  0x25, (byte)  0x2e, (byte)  0x1c, (byte)  0xa6, (byte)  0xb4, (byte)  0xc6, (byte)  0xe8, (byte)  0xdd, (byte)  0x74, (byte)  0x1f, (byte)  0x4b, (byte)  0xbd, (byte)  0x8b, (byte)  0x8a } ,
			{ (byte)  0x70, (byte)  0x3e, (byte)  0xb5, (byte)  0x66, (byte)  0x48, (byte)  0x03, (byte)  0xf6, (byte)  0x0e, (byte)  0x61, (byte)  0x35, (byte)  0x57, (byte)  0xb9, (byte)  0x86, (byte)  0xc1, (byte)  0x1d, (byte)  0x9e } ,
			{ (byte)  0xe1, (byte)  0xf8, (byte)  0x98, (byte)  0x11, (byte)  0x69, (byte)  0xd9, (byte)  0x8e, (byte)  0x94, (byte)  0x9b, (byte)  0x1e, (byte)  0x87, (byte)  0xe9, (byte)  0xce, (byte)  0x55, (byte)  0x28, (byte)  0xdf } ,
			{ (byte)  0x8c, (byte)  0xa1, (byte)  0x89, (byte)  0x0d, (byte)  0xbf, (byte)  0xe6, (byte)  0x42, (byte)  0x68, (byte)  0x41, (byte)  0x99, (byte)  0x2d, (byte)  0x0f, (byte)  0xb0, (byte)  0x54, (byte)  0xbb, (byte)  0x16 }
	}; 

	//inverse box
	public static byte[][] isBox = 
		{
				{ (byte) 0x52, (byte) 0x09, (byte) 0x6a, (byte) 0xd5, (byte) 0x30, (byte) 0x36, (byte) 0xa5, (byte) 0x38, (byte) 0xbf, (byte) 0x40, (byte) 0xa3, (byte) 0x9e, (byte) 0x81, (byte) 0xf3, (byte) 0xd7, (byte) 0xfb } ,
				{ (byte) 0x7c, (byte) 0xe3, (byte) 0x39, (byte) 0x82, (byte) 0x9b, (byte) 0x2f, (byte) 0xff, (byte) 0x87, (byte) 0x34, (byte) 0x8e, (byte) 0x43, (byte) 0x44, (byte) 0xc4, (byte) 0xde, (byte) 0xe9, (byte) 0xcb } ,
				{ (byte) 0x54, (byte) 0x7b, (byte) 0x94, (byte) 0x32, (byte) 0xa6, (byte) 0xc2, (byte) 0x23, (byte) 0x3d, (byte) 0xee, (byte) 0x4c, (byte) 0x95, (byte) 0x0b, (byte) 0x42, (byte) 0xfa, (byte) 0xc3, (byte) 0x4e } ,
				{ (byte) 0x08, (byte) 0x2e, (byte) 0xa1, (byte) 0x66, (byte) 0x28, (byte) 0xd9, (byte) 0x24, (byte) 0xb2, (byte) 0x76, (byte) 0x5b, (byte) 0xa2, (byte) 0x49, (byte) 0x6d, (byte) 0x8b, (byte) 0xd1, (byte) 0x25 } ,
				{ (byte) 0x72, (byte) 0xf8, (byte) 0xf6, (byte) 0x64, (byte) 0x86, (byte) 0x68, (byte) 0x98, (byte) 0x16, (byte) 0xd4, (byte) 0xa4, (byte) 0x5c, (byte) 0xcc, (byte) 0x5d, (byte) 0x65, (byte) 0xb6, (byte) 0x92 } ,
				{ (byte) 0x6c, (byte) 0x70, (byte) 0x48, (byte) 0x50, (byte) 0xfd, (byte) 0xed, (byte) 0xb9, (byte) 0xda, (byte) 0x5e, (byte) 0x15, (byte) 0x46, (byte) 0x57, (byte) 0xa7, (byte) 0x8d, (byte) 0x9d, (byte) 0x84 } ,
				{ (byte) 0x90, (byte) 0xd8, (byte) 0xab, (byte) 0x00, (byte) 0x8c, (byte) 0xbc, (byte) 0xd3, (byte) 0x0a, (byte) 0xf7, (byte) 0xe4, (byte) 0x58, (byte) 0x05, (byte) 0xb8, (byte) 0xb3, (byte) 0x45, (byte) 0x06 } ,
				{ (byte) 0xd0, (byte) 0x2c, (byte) 0x1e, (byte) 0x8f, (byte) 0xca, (byte) 0x3f, (byte) 0x0f, (byte) 0x02, (byte) 0xc1, (byte) 0xaf, (byte) 0xbd, (byte) 0x03, (byte) 0x01, (byte) 0x13, (byte) 0x8a, (byte) 0x6b } ,
				{ (byte) 0x3a, (byte) 0x91, (byte) 0x11, (byte) 0x41, (byte) 0x4f, (byte) 0x67, (byte) 0xdc, (byte) 0xea, (byte) 0x97, (byte) 0xf2, (byte) 0xcf, (byte) 0xce, (byte) 0xf0, (byte) 0xb4, (byte) 0xe6, (byte) 0x73 } ,
				{ (byte) 0x96, (byte) 0xac, (byte) 0x74, (byte) 0x22, (byte) 0xe7, (byte) 0xad, (byte) 0x35, (byte) 0x85, (byte) 0xe2, (byte) 0xf9, (byte) 0x37, (byte) 0xe8, (byte) 0x1c, (byte) 0x75, (byte) 0xdf, (byte) 0x6e } ,
				{ (byte) 0x47, (byte) 0xf1, (byte) 0x1a, (byte) 0x71, (byte) 0x1d, (byte) 0x29, (byte) 0xc5, (byte) 0x89, (byte) 0x6f, (byte) 0xb7, (byte) 0x62, (byte) 0x0e, (byte) 0xaa, (byte) 0x18, (byte) 0xbe, (byte) 0x1b } ,
				{ (byte) 0xfc, (byte) 0x56, (byte) 0x3e, (byte) 0x4b, (byte) 0xc6, (byte) 0xd2, (byte) 0x79, (byte) 0x20, (byte) 0x9a, (byte) 0xdb, (byte) 0xc0, (byte) 0xfe, (byte) 0x78, (byte) 0xcd, (byte) 0x5a, (byte) 0xf4 } ,
				{ (byte) 0x1f, (byte) 0xdd, (byte) 0xa8, (byte) 0x33, (byte) 0x88, (byte) 0x07, (byte) 0xc7, (byte) 0x31, (byte) 0xb1, (byte) 0x12, (byte) 0x10, (byte) 0x59, (byte) 0x27, (byte) 0x80, (byte) 0xec, (byte) 0x5f } ,
				{ (byte) 0x60, (byte) 0x51, (byte) 0x7f, (byte) 0xa9, (byte) 0x19, (byte) 0xb5, (byte) 0x4a, (byte) 0x0d, (byte) 0x2d, (byte) 0xe5, (byte) 0x7a, (byte) 0x9f, (byte) 0x93, (byte) 0xc9, (byte) 0x9c, (byte) 0xef } ,
				{ (byte) 0xa0, (byte) 0xe0, (byte) 0x3b, (byte) 0x4d, (byte) 0xae, (byte) 0x2a, (byte) 0xf5, (byte) 0xb0, (byte) 0xc8, (byte) 0xeb, (byte) 0xbb, (byte) 0x3c, (byte) 0x83, (byte) 0x53, (byte) 0x99, (byte) 0x61 } ,
				{ (byte) 0x17, (byte) 0x2b, (byte) 0x04, (byte) 0x7e, (byte) 0xba, (byte) 0x77, (byte) 0xd6, (byte) 0x26, (byte) 0xe1, (byte) 0x69, (byte) 0x14, (byte) 0x63, (byte) 0x55, (byte) 0x21, (byte) 0x0c, (byte) 0x7d }
		};
	
	public static byte[][] displayKey;

	public static void main(String[] args) 
	{	
		//TO CHANGE WITH VALUES WE WILL USE, JUST CHANGE THIS TO 1,2, OR 3!!!
		int choice = 3;
		
		
		String inputString = null;
		String secondInputString = null;
		String keyString = null;
		
		switch(choice)
		{
		case 1:
			//AES-128
			inputString =  "00 11 22 33 44 55 66 77 88 99 aa bb cc dd ee ff";
			secondInputString = "69 c4 e0 d8 6a 7b 04 30 d8 cd b7 80 70 b4 c5 5a";
			keyString  = "00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f";
			break;

		case 2:
			//AES-192
			inputString =  "00 11 22 33 44 55 66 77 88 99 aa bb cc dd ee ff";
			secondInputString = "dd a9 7c a4 86 4c df e0 6e af 70 a0 ec 0d 71 91";
			keyString = "00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f 10 11 12 13 14 15 16 17";	
			break; 
		case 3:
			//AES-256
			inputString =  "00 11 22 33 44 55 66 77 88 99 aa bb cc dd ee ff";
			secondInputString = "8e a2 b7 ca 51 67 45 bf ea fc 49 90 4b 49 60 89";
			keyString = "00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f 10 11 12 13 14 15 16 17 18 19 1a 1b 1c 1d 1e 1f";
			break;
		}

		displayKey = null;
		//first we encode
		encode(AES.stringToBytes(inputString), AES.stringToBytes(keyString));
		//System.out.println("no decrypt yet!!!");
		decode(AES.stringToBytes(secondInputString), AES.stringToBytes(keyString));
	}

	//plaintext to cyphertext
	public static void encode(byte[] stateArray, byte[] keyArray)
	{
		//put the array into a 2d byte array, then the Key too.---------------------------------------------------
		byte[][] state = new byte[stateArray.length/4][4];
		for(int i = 0; i < stateArray.length/4; i++)//column for bits
		{
			for(int j = 0; j < 4; j++)//column row coordinate going down
			{
				state[i][j] = stateArray[(i*4) + j];
			}
		}
		//quick check on a transposed matrix
//		for(int i = 0; i < 4; i++)
//		{
//			System.out.println(byteArrayToString(state[i]));//show this column
//		}
//		
		byte[][] allKeys = expandKeys(keyArray);
		byte[][] key = getKey(0, allKeys);
		//get the right number of rounds
		int rounds = getNumRounds(keyArray.length);

		System.out.println("\nEncoding " + keyArray.length * 8);
		System.out.println("round[ 0].input:   " + byteArrayToString(stateArray));
		System.out.println("round[ 0].k_sch:   " + byteArrayToString(keyArray));

		addRoundKey(state, key);

		for(int i = 1; i <= rounds; i++)
		{
			printLine(String.format("round[ " + i + "].start:  ", i), state);
			subBytes(state);
			printLine(String.format("round[ " + i + "].s_box:  ", i), state);
			shiftRows(state);
			printLine(String.format("round[ " + i + "].s_row:  ", i), state);
			if(i != rounds)
			{
				mixColumns(state);
				printLine(String.format("round[ " + i + "].m_col:  ", i), state);
			}
			key = getKey(i, allKeys);
			addRoundKey(state, key);
			printLine(String.format("round[ " + i + "].k_sch:  ", i), key);
			displayKey = key;
		}
		printLine(String.format("round[ " + rounds + "].output: ", rounds), state);
	}

	//cause we need this elsewhere
	private static int getNumRounds(int keyLen)
	{
		switch(keyLen){
		case 16: 
			return 10;
		case 24: 
			return 12;
		case 32: 
			return 14;
		default: 
			System.out.println("WRONG NUMBER OF KEYS!!!!");
			return 0;
		}
	}
	
	//cyphertext to plaintext
	public static void decode(byte[] stateArray, byte[] keyArray)
	{
		byte[][] state = new byte[stateArray.length/4][4];
		for(int i = 0; i < stateArray.length/4; i++)//column for bits
		{
			for(int j = 0; j < 4; j++)//column row coordinate going down
			{
				state[i][j] = stateArray[(i*4) + j];
			}
		}
		//quick check on a transposed matrix
//		for(int i = 0; i < 4; i++)
//		{
//			System.out.println(byteArrayToString(state[i]));//show this column
//		}

		byte[][] allKeys = expandKeys(keyArray);
		int rounds = getNumRounds(keyArray.length);

		System.out.println("\nDecoding " + keyArray.length * 8);
		System.out.println(String.format("round[ 0].iinput:  ", rounds) + byteArrayToString(stateArray));
		//System.out.println(String.format("round[%d].ik_sch:  ", rounds) + byteArrayToString(keyArray));
		
		//print out the right key (The encrypted one)
		String message = "round[ 0].ik_sch:  ";
		for(int i = 0; i < displayKey[0].length; i++)
		{
			for(int j = 0; j < displayKey.length; j++)
			{
				// this little guy puts two characters together, like we have
				message += String.format("%02x", displayKey[i][j]) + " ";
			}
		}
		System.out.println(message);

		byte[][] key = getKey(rounds, allKeys);
		addRoundKey(state, key);

		//count backwards in rounds, but display the rounds going up for some reason.
		for(int i = rounds - 1; i >= 0; i--)
		{
			printLine(String.format("round[ " + (rounds - i) + "].istart: ", i), state);
			ishiftRows(state);
			printLine(String.format("round[ " + (rounds - i) + "].is_row: ", i), state);
			isubBytes(state);
			printLine(String.format("round[ " + (rounds - i) + "].is_box: ", i), state);
			key = getKey(i, allKeys);
			printLine(String.format("round[ " + (rounds - i) + "].ik_sch: ", i), key);
			addRoundKey(state, key);
			//printLine(String.format("round[%2d].ik_add: ", i), state);
			if(i != 0)
			{
				printLine(String.format("round[ " + (rounds - i) + "].ik_add: ", i), state);
				imixColumns(state);		
				//printLine(String.format("round[%2d].im_col: ", i), state);
			}
		} 
		printLine(String.format("round[ " + rounds + "].ioutput:", 0), state);
	}

	//put our key into a crypted 2d byte matrix. SEE SPEC SUDOCODE
	private static byte[][] expandKeys(byte[] currKey)
	{
		//initialize variables
		// number of rounds
		int Nr = getNumRounds(currKey.length);
		// number of columns. Can be a bunch. key bit length/32
		int Nk = currKey.length / 4;
		// number of rows in each column. ALWAYS 4
		int Nb = 4;

		byte[] temp;
		byte[][] w = new byte[Nb * (Nr + 1)][4];

		for(int i = 0; i < Nk; i++)
		{
			byte[] nxt = {currKey[4*i], currKey[4*i+1], currKey[4*i+2], currKey[4*i+3]};
			w[i] = nxt;
		}
		for(int i = Nk; i < Nb * (Nr+1); i++)//for certain columns, preform this
		{
			//need a temp copy of this thingy
			temp = Arrays.copyOf(w[i-1], w[i-1].length);
			if (i % Nk == 0)
			{
				//rotate left once, just like shiftRows. substitute with s-box values, just like subBytes does.
				temp = subWord(rotWord(temp));
				//XOR temp0 with this particular rcon value for a new temp value
				temp[0] ^= rcon[i/Nk];
			} 
			else if (Nk > 6 && i % Nk == 4)
			{
				temp = subWord(temp);
			}
			//for this row, XOR for a new result
			for(int j = 0; j < temp.length; j++)
			{
				w[i][j] = (byte) (w[i-Nk][j] ^ temp[j]);
			}
		}
		return w;
	}

	private static byte[][] getKey(int round, byte[][] allKeys)
	{
		byte[][] blockKey = new byte[4][4];
		int start = round * 4;
		for(int i = start; i < start + 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				blockKey[i - start][j] = allKeys[i][j];
		//		System.out.println(printLine("Round Key: ", roundKey));
			}
		}
		return blockKey;
	}

	private static byte[] rotWord(byte[] word)
	{
		//rotate the bytes to the left one. Just like in shiftRows
		byte tempy = word[0];
		for(int i = 0; i < 3; i++)
		{
			word[i] = word[i + 1];
		}
		word[3] = tempy;
		
		return word;
	}

	private static byte[] subWord(byte[] b)
	{
		//basically a smaller version of subBytes!
		//iterate through the table substituting 
		for(int i = 0; i < b.length; i++)
		{
			//	get left most 4 bits to hex / get rightmost to hex
			b[i] = sBox[(b[i] & 0XF0) >> 4][b[i] & 0x0F];
		}

		return b;
	}

	
	//Bug function 1
	private static byte[][] subBytes(byte[][] state)
	{
		//iterate through the table substituting 
		for(int i = 0; i < state.length; i++)
		{
			for(int j = 0; j < state[i].length; j++)
			{
				//				 get left most 4 bits to hex / get rightmost to hex
				state[i][j] = sBox[(state[i][j] & 0XF0) >> 4][state[i][j] & 0x0F];
			}
		}
		return state;
	}

	//Big function 1.5
	private static byte[][] isubBytes(byte[][] state)
	{
		for(int i = 0; i < state.length; i++)
		{
			for(int j = 0; j < state[0].length; j++)
			{
				//				 get left most 4 bits to hex - get rightmost to hex
				state[i][j] = isBox[(state[i][j] & 0xF0) >> 4][state[i][j] & 0x0F];
			}
		}
		return state;
	}

	//Big function 2
	private static byte[][] shiftRows(byte[][] state)
	{
		//you shift all row contents by it's index
		//row1 1 time left
		byte p01 = state[0][1];
		for(int i = 0; i < 3; i++)
		{
			state[i][1] = state[i+1][1];
		}
		state[3][1] = p01;
		
		//row 2 2 times left;
		byte p02 = state[0][2];
		byte p12 = state[1][2];
		state[0][2] = state[2][2];
		state[1][2] = state[3][2];
		state[2][2] = p02;
		state[3][2] = p12;
		
		//row 3 3 times left
		byte p33 = state[3][3];
		for(int i = 3; i > 0 ; i--)
		{
			state[i][3] = state[i - 1][3];
		}
		state[0][3] = p33;
		
		return state;
	}

	//Big function 2.5
	private static byte[][] ishiftRows(byte[][] state)
	{
		//now we do right shifts based on inverse row indexes
		//shift row 1 3 times right
		byte row2 = state[3][1]; 
		for(int i = 3; i > 0; i--)
		{
			state[i][1] = state[i - 1][1];
		}
		state[0][1] = row2;
		
		 //shift row 2 2 times right
		byte row3a = state[0][2];
		byte row3b = state[1][2];
		state[0][2] = state[2][2];
		state[1][2] = state[3][2];
		state[2][2] = row3a;
		state[3][2] = row3b;
		
		// shift row 3 1 time right
		byte row4 = state[0][3];
		for(int i = 0; i < 3; i++)
		{
			state[i][3] = state[i + 1][3];
		}
		state[3][3] = row4;
		
		return state;
	}

	//Big function 3
	private static byte[][] mixColumns(byte[][] state)
	{
		//we perform ffMultiply with each column in our 2d array. This is done with predefined hex values.
		//this is like doing a dot product with this column
		byte[] hexTable = { 0x00, 0x01, 0x02, 0x03 };
		for(int i = 0; i < 4; i++)
		{
			byte[] col = state[i];
			byte[] resCol = new byte[4];
			resCol[0] = (byte) (ffMult(hexTable[2], col[0]) ^ ffMult(hexTable[3], col[1]) ^ ffMult(hexTable[1], col[2]) ^ ffMult(hexTable[1], col[3]));
			resCol[1] = (byte) (ffMult(hexTable[1], col[0]) ^ ffMult(hexTable[2], col[1]) ^ ffMult(hexTable[3], col[2]) ^ ffMult(hexTable[1], col[3]));
			resCol[2] = (byte) (ffMult(hexTable[1], col[0]) ^ ffMult(hexTable[1], col[1]) ^ ffMult(hexTable[2], col[2]) ^ ffMult(hexTable[3], col[3]));
			resCol[3] = (byte) (ffMult(hexTable[3], col[0]) ^ ffMult(hexTable[1], col[1]) ^ ffMult(hexTable[1], col[2]) ^ ffMult(hexTable[2], col[3]));
			state[i] =  resCol;
		}
		return state;
	}
	
	//Big funciton 3.5
	private static byte[][] imixColumns(byte[][] state)
	{
		//we perform ffMultiply with each column in our 2d array. This is done with MORE predefined hex values.
		byte[] hexTable = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e };
		for(int i = 0; i < 4; i++)
		{
			byte[] col = state[i];
			byte[] newCol = new byte[4];
			newCol[0] = (byte) (ffMult(hexTable[0x0e], col[0]) ^ ffMult(hexTable[0x0b], col[1]) ^ ffMult(hexTable[0x0d], col[2]) ^ ffMult(hexTable[0x09], col[3]));
			newCol[1] = (byte) (ffMult(hexTable[0x09], col[0]) ^ ffMult(hexTable[0x0e], col[1]) ^ ffMult(hexTable[0x0b], col[2]) ^ ffMult(hexTable[0x0d], col[3]));
			newCol[2] = (byte) (ffMult(hexTable[0x0d], col[0]) ^ ffMult(hexTable[0x09], col[1]) ^ ffMult(hexTable[0x0e], col[2]) ^ ffMult(hexTable[0x0b], col[3]));
			newCol[3] = (byte) (ffMult(hexTable[0x0b], col[0]) ^ ffMult(hexTable[0x0d], col[1]) ^ ffMult(hexTable[0x09], col[2]) ^ ffMult(hexTable[0x0e], col[3]));
			state[i] =  newCol;
		}
		return state;
	}

	//part of Big function 3 and 3.5. Does our XOR operation
	private static byte ffMult(byte val1, byte val2)
	{
		byte temp = val2;
		byte total = 0x00;
		//for each bit
		for(int i = 0; i < 8; i++)
		{
			//if you left shift and and it with 0x01 to get 0x01, your total should be XORed with temp for a new total;
			if(((val1 >> (i))& 0x01) == 0x01) total ^= temp;
			//if anding with 0x80 ==0x80, you XOR with 0X1B				  if not, you bit shift by 1.
			temp = (((temp & 0x80) == 0x80)? (byte) ((temp << 1) ^ 0x1B): (byte) (temp << 1));
		}
		return (byte) total;
	}
	
	//Big function 4
	private static byte[][] addRoundKey(byte[][] state, byte[][] key)
	{
		for(int i = 0; i < state.length; i++)
		{
			for(int j = 0; j < state[0].length; j++)
			{
				//Round Key XOR-d with the State for a new result of State
				state[i][j] ^= key[i][j];
			}
		}
		return state;
	}

	//iterate through and make a byte array
	public static byte[] stringToBytes(String input)
	{
		String[] parts = input.split(" ");
		byte[] result = new byte[parts.length];
		for(int i = 0; i < parts.length; i++)
			result[i] = (byte)Integer.parseInt(parts[i], 16);
		return result;
	}

	//convert a byte array to a string with correct placement
	public static String byteArrayToString(byte[] bytes)
	{
		String result = "";
		for(int i = 0; i < bytes.length; i++){
			if(i != 0)
				result += " ";
			result += String.format("%02x", bytes[i]);
		}
		return result;
	}

	//public static String printLine(byte[][] bytes){ return printLine("", bytes);}
	public static String printLine(String message, byte[][] bytes)
	{
		String result = message + " ";
		//		String result = message + " \n";
		for(int i = 0; i < bytes.length; i++){
			for(int j = 0; j < bytes[0].length; j++)
				result += String.format("%02x", bytes[i][j]) + " ";
		}

		System.out.println(result);
		return result;
	}
}