public class hillcipher {

    public static void main(String args[])
    {
        int key[][]=new int[2][2];
        key[0][0]=1;
        key[0][1]=2;
        key[1][0]=3;
        key[1][1]=4;

        String input="aditya";
        String encoded="";
        for(int i=0;i<input.length();i=i+2)
        {
            int inp[][]=new int[2][1];
            inp[0][0]=((int)input.charAt(i)-97)%26;
            inp[1][0]=((int)input.charAt(i)-97)%26;
            int out[][]=new int[2][1];
            for(int j=1;j<=2;j++)
            {
                int val=0;
                for(int k=1;k<=2;k++)
                {
                    val=key[j-1][k-1]*inp[k-1][0];
                }
                out[j-1][0]=val%26;
                System.out.println(val%26);
                encoded+=(char)((val%26)+97);
            }
            
        }
        System.out.println(encoded);
        

    }
    
}
