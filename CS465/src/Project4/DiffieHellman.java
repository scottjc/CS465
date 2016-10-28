package Project4;

import java.math.BigInteger;

public class DiffieHellman 
{
	
    public static void main(String[] args) 
    {
    	//first test
    	BigInteger g = new BigInteger ("5");
    	BigInteger p = new BigInteger ("10420114435343421584900872011420348447007082906095422844459465548247386401885848518124279228981416500145014945974773331228264181981978897461511905365936923");
    	//System.out.println(p.bitLength());//512 bits
    	BigInteger s = new BigInteger ("1247406511514577109853981639498844634596584766477349928071780564800311676683314247220052798676162460640686522815060909484623005051280404717674816311464841");//the secret a or b.
    	
    	//System.out.println(s.isProbablePrime(11));
    	BigInteger key = modExpo(g,s,p);
    	System.out.println("Key is " + key);
    	
    	
    	BigInteger gttmp = new BigInteger("6786879591842003170888935955573243990293513472420357660127477921836739206240073386532817630466099355654377338077640710019075598687282671731622676774287483");
    	BigInteger ans = modExpo(gttmp, s, p);
    	System.out.println("message is " + ans);
    	
    	
//    	g = new BigInteger("5");
//    	p = new BigInteger("16");
//    	s = new BigInteger("7");
//    	BigInteger thing = modExpo(g, p, s);
    }

    
    //This function will find what x^y%z is using the modular exponentiation algorithm.
    static BigInteger modExpo(BigInteger x, BigInteger y, BigInteger N)
    {
        System.out.println(x + " " + y + " " + N);

        if (y == BigInteger.ZERO) return BigInteger.ONE;

        BigInteger z = modExpo(x, y.divide(new BigInteger("2")), N);
        

        if(y.mod(new BigInteger("2")) == BigInteger.ZERO)//Sees if the number is even of not.
        {
            //return ((long)Math.pow(z, 2)) % N;
        	//System.out.println("returning " + z.pow(2).mod(N));
            return z.pow(2).mod(N);
        }
        else
        {
            //return (x * ((long)Math.pow(z, 2))) % N;
        	//System.out.println("returning " + x.multiply(z.pow(2)).mod(N));
        	return x.multiply(z.pow(2)).mod(N);
        }
    }
}
