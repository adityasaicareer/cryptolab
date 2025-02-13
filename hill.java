public class hill {

    public static void main(String args[])
    {
        String txt="aditya";
        int mat[][]=new int[2][2];
        mat[0][0]=1;
        mat[0][1]=2;
        mat[1][0]=3;
        mat[1][1]=4;
        String en="";
        for(int i=1;i<=txt.length();i=i+2)
        {
            
            
            int first=((int)(txt.charAt(i-1)))-97;
            int second=((int)(txt.charAt(i)))-97;

            int en1=(first*mat[0][0]+second*mat[0][1])%26;
            int en2=(first*mat[1][0]+second*mat[1][1])%26;

            en=en+(char)(en1+97);
            en=en+(char)(en2+97);

            
        }

        System.out.println(en);

    }
    
}
