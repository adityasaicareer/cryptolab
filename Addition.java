public class Addition
{
    public static void main(String args[])
    {
        String input="aditya";
        String encoded="";

        for(int i=1;i<=input.length();i++)
        {
            int v=(int)input.charAt(i-1);

            v=(v-97+5)%26;
            encoded+=(char)(v+97);

        }

        System.out.println(encoded);

        // decryption
        String decoded="";
        for(int i=1;i<=encoded.length();i++)
        {
            int v=(int)encoded.charAt(i-1);
            v=(v-5-97);
            if(v<0)
            {
                v=26+v;
            }
           

            decoded+=(char)(v+97);

        }

        System.out.println(decoded);

    }
}