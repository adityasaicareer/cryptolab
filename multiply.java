public class multiply {

    public static void main(String args[])
    {
        String text="hello";
        String encrypt="";
        for(int i=1;i<=text.length();i++)
        {
            int test=(int)text.charAt(i-1);
            test=test-97;
            test=(test*7)%26;
            test=test+97;
            encrypt=encrypt+(char)test;
        }
        System.out.println(encrypt);

        int d=0;
        while(true)
        {
            if((7*d)%26==1)
            {
                break;
            }
            else{
                d=d+1;
            }
        }
        String decrypt="";

        for(int i=1;i<=encrypt.length();i++)
        {
            int test=(int)encrypt.charAt(i-1);
            test=(test-97)*d;
            test=test%26;
            test=test+97;
            decrypt=decrypt+(char)test;
        }
        System.out.println(decrypt);
    }
    
}
