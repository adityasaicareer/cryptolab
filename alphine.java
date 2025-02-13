public class alphine {
    public static void main(String args[])
    {
        String test="aditya";
        String en="";
        for(int i=1;i<=test.length();i++)
        {
            int t=(int)test.charAt(i-1);
            t=t-97;
            t=(t*7)%26;
            t=(t+2)%26;
            en=en+(char)(t+97);
        }
        System.out.println(en);

        int d=0;
        while(true)
        {
            if((7*d)%26==1)
            {
                break;
            }
            else{
                d++;
            }
        }

        String dec="";
        for(int i=1;i<=en.length();i++)
        {
            int t=(int)en.charAt(i-1);
            t=t-97;
            t=t-2;
            if(t<0)
            {
                t=26+(t)%26;
            }
            else{
                t=t%26;
            }

            t=(t*d)%26;
            dec=dec+(char)(t+97);
        }
        System.out.println(dec);

    }
    
}
