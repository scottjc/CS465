package Project5;

import java.math.BigInteger;
import java.util.Objects;

public class RSA {

	public static void main(String[] args) 
	{
		//RSA encryption c = m^e %n
		//RSA decryption m = c^d %n
		
		//get ext Euclidan algorithmn up and running for calculating d and such.
		//Select p and q, which are 2 512 bit primes.
		//phi(n) = (p-1)(q-1), which is relatively prime to 65537
		 BigInteger p = new BigInteger("6997040548236306032270028142697844577679802860009390483825615834396766817911865846408328563633463004219619254639348141491179371158391819359706834377936017");
		 BigInteger q = new BigInteger("8593701979078313474945043728666349994614714557320430419781846880808258697302068260916571720507008823663836468130075453764283766701743551377885890781118909");
		// n = p*q, e = 65537
		 BigInteger n = p.multiply(q);
		 BigInteger e = new BigInteger("65537");
		 BigInteger phiN = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
		 
		 //Verify that (p-1)(q-1) is relatively prime to 65537 (which means the greatest common prime should be 1).---
		 System.out.println(n);
		 System.out.println(phiN);
		 //double[] temp = extendedEuc(phiN.doubleValue(), e.doubleValue());
		 //double gcd = temp[0];
		 //System.out.println(gcd);
		 
		 //calculate the secret exponent d s.t. e*d = 1 (mod phi(n))--------------------------------------
	    BigInteger g[] = extendedEuclid(phiN, e);
	    BigInteger d = g[1];
	    System.out.println(d);
	    

		//verify that for numbers m times less than n, ((m^e %n)^d)%n == m--------------------------------
	    BigInteger m = new BigInteger("212788291171290661843158364587637197333531097895143818988755059744299426205080018037256906030614828346200464605058471107535343001238694433356829682872"); //m^e % n
		BigInteger dm = modExpo(m,e,n);
		System.out.println(dm);
		
		
		BigInteger dc = new BigInteger("38836004958973333808444474744049950597152340928568277009922464771958929445481001650323472151575854567252425265205811145901606099326089029141609035935574695159638546746822086067633268843681771212436983924976927634925742602911873891599802616821582340073810951502143926279012766864221984005313249745229167595735");
		BigInteger nm = modExpo(dc,d,n);
		System.out.println(nm);
	}


	//This function will find what x^y % N is using the modular exponentiation algorithm.
	public static BigInteger modExpo(BigInteger x, BigInteger y, BigInteger N)
	{
		//System.out.println(x + " " + y + " " + N);

		if (y == BigInteger.ZERO) return BigInteger.ONE;

		BigInteger z = modExpo(x, y.divide(new BigInteger("2")), N);


		if(y.mod(new BigInteger("2")) == BigInteger.ZERO)//Sees if the number is even of not.
		{
			//return ((long)Math.pow(z, 2)) % N;
			return z.pow(2).mod(N);
		}
		else
		{
			//return (x * ((long)Math.pow(z, 2))) % N;
			return x.multiply(z.pow(2)).mod(N);
		}
	}
	
	/*  
	 This function will perform the Extended Euclidean algorithm
     to find the GCD of a and b. We assume here that a and b
     are non-negative (and not both zero). This function also
     will return numbers j and k such that d = j*a + k*b, where d is the GCD of a and b.
     https://cgi.csc.liv.ac.uk/~martin/teaching/comp202/Java/GCD.html
	 */
//    public static double[] extendedEuc(double a, double b)
//    {
//        double[] ans = new double[3];
//        double q;
//
//        if (b == 0)  {  /*  If b = 0, then we're done...  */
//            ans[0] = a;
//            ans[1] = 1;
//            ans[2] = 0;
//        }
//        else
//        {     
//        	/*  Otherwise, make a recursive function call  */ 
//            q = a/b;
//            ans = extendedEuc (b, a % b);
//            double temp = ans[1] - ans[2]*q;
//            ans[1] = ans[2];
//            ans[2] = temp;
//        }
//        //System.out.println("gcd = " + ans[0] + ", x = " + ans[1] + ", y = " + ans[2]);//gcd , x, y
//        return ans;
//    }
    
    //this one returns d
    public static BigInteger[] extendedEuclid(BigInteger a, BigInteger b)
    {
        if (Objects.equals(b, BigInteger.ZERO))
        {
            return new BigInteger[] {BigInteger.ONE, BigInteger.ZERO, a};
        }

        BigInteger[] primes = extendedEuclid(b, a.mod(b));
            // x', y', d

        BigInteger middlePrime = primes[0].subtract(a.divide(b).multiply(primes[1]));
            //x' - floor(a/b)*y'

        return new BigInteger[] {primes[1], middlePrime, primes[2]};
    }
    
}

