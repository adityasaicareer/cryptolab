public class Gcd {

    public static void gcd(int a,int b)
    {
        if(b!=0)
        {
            gcd(b,a%b);        
        }
        else{
            System.out.println(a);
        }
    }

    public static void main(String argsp[])
    {
        gcd(10,15);
    }
    
}
