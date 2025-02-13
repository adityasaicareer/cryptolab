public class caesercipher {

    public static void main(String args[])
    {
        String text="aditya";
        String encrypted="";
        for(int i=1;i<=text.length();i++)
        {
            int test=(int)text.charAt(i-1);
            test=(test-97+5)%26;
            test=test+97;
            encrypted=encrypted+(char)test;


        }
        

        System.out.println(encrypted);
        // decryption

        String decrypted="";

        for(int i=1;i<=encrypted.length();i++)
        {
            int test=(int)encrypted.charAt(i-1);
            test=(test-97-5);
            if(test<0)
            {
                test=26+(test)%26;
            }
            else{
                test=test%26;
            }

            test=test+97;
            decrypted=decrypted+(char)test;
        }

        System.out.println(decrypted);
    }
    
}
