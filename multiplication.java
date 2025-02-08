public class multiplication {

    public static void main(String args[])
    {
        String input="aditya";
        String encoded="";

        for(int i=1;i<=input.length();i++)
        {
            int v=(int)input.charAt(i-1);
            v=v-97;
            v=(v*5)%26;

            encoded+=(char)(v+97);
        }
        System.out.println(encoded);

        int di=0;
        while(true)
        {
            if((5*di)%26==1)
            {
                break;
            }
            else{
                di=di+1;
            }
        }
        System.out.println(di);

        // decoding 
        String decoded="";
        for(int j=1;j<=encoded.length();j++)
        {
            int v=(int)encoded.charAt(j-1);
            v=v-97;
            v=(v*di)%26;
            decoded+=(char)(v+97);


        }
        System.out.println(decoded);
    }
    
}
