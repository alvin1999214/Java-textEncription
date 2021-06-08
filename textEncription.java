import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;

public class textEncription{

    //Check prime number
    public static int isPrime(int num){
        int prime = 1;
        for(int i=2;i<num;i++){
            if((num%i)==0){
                prime = 0;
            }
        }
        return num;
    }

    //next prime number
    public static int nextPrime(int num){
        num++;
        for(int i=2;i<num;i++){
            if(num%i == 0){
                num++;
                i=2;
            }
            else{
                continue;
            }
        }
        return num;
    }

    //privious prime number
    public static int priviousPrime(int num){
        num--;
        for(int i=2;i<num;i++){
            if(num%i == 0){
                num--;
                i=2;
            }
            else{
                continue;
            }
        }
        return num;
    }

    //get gcd
    public static int gcd(int a, int b){
        if(a==0){
            return b;
        }
        return gcd(b%a,a);
    }

    //get lcm
    public static int lcm(int a, int b){
        return (a/gcd(a,b))*b;
    }

    //mod inverse
    public static int modInverse(int a, int m){
        for(int x=1;x<m;x++){
            if(((a%m)*(x%m))%m == 1){
                return x;
            }
        }
        return 1;
    }

    public static void Encryption(String msg){

        //The message need to encryption will by pass to String msg
        
        Scanner userInput = new Scanner(System.in);

        //Convert text to ASCII numbers
        int[] intMsg = new int[msg.length()];    
        byte[] msgArray = msg.getBytes(StandardCharsets.US_ASCII);
        for(int i=0;i<msg.length();i++){
            intMsg[i] = msgArray[i];
        }
        //End of Convert text to ASCII numbers

        //Getting public key N and E from user
        System.out.println("Please Enter Your Public Key 'N' For Encryption");
        int n = userInput.nextInt(); //public key n
        System.out.println("Please Enter Your Public Key 'E' For Encryption");
        int e = userInput.nextInt(); //public key e
        //End of Getting public key N and E from user
        
        //Encrypt the ASCII number by public key N and E
        System.out.print("Your Encrypted Message is: ");

        //Encrypt the number by usng for-loop
        for(int i=0;i<intMsg.length;i++){
            int temp = intMsg[i];
            for(int r=1;r<e;r++){
                temp=temp*intMsg[i]%n;
            }
            System.out.print(temp+n+" "); //Add public key N at each cipher text
        }
        //End of Encrypt the ASCII number by public key N and E

        System.out.println((n+(n+1))); //Add the public key N in the last by N+(N+1)
        System.out.println("Done!");
       
    }

    public static void Decryption(String msg){
        //cipher text will be pass by String msg

        Scanner userInput = new Scanner(System.in);

        //Getting private key D from user
        System.out.println("Please Enter Your Private Key 'd' For Encryption");
        int d = userInput.nextInt();
        //End of Getting private key D from user

        //Convert the string message in to a array
        String[] splited = msg.split(" ");
        int[] intMsg = new int[splited.length];
        for(int i=0;i<splited.length;i++){
            intMsg[i] = Integer.parseInt(splited[i]);
        }
        //End of Convert the string message in to a array
        
        //Getting the public key N
        int n = intMsg[intMsg.length-1];
        n=(n-1)/2;
        //Copy a new array without public key N
        int[] newIntMsg = Arrays.copyOf(intMsg,intMsg.length-1);
        
        //Decrypte the cipher text to ASCII number by using for-loop
        System.out.print("Your Decrypted Message is: ");
        for(int i=0;i<newIntMsg.length;i++){
            int temp = newIntMsg[i]-n;
            for(int r=1;r<d;r++){
                temp=temp*newIntMsg[i]%n;                
            }
            //Convert ASCII number to character
            String strAscii = Character.toString((char)temp);
            System.out.print(strAscii);
        }
        System.out.println("");
        System.out.println("Done!");
    }

    public static void keyGenerator(int num){
        int num1 = nextPrime(num);
        int num2 = priviousPrime(num);

        int n = num1*num2;
        int lcm = lcm(num1-1,num2-1);
        //int f = (num1-1)*(num2-1);
        int e = ThreadLocalRandom.current().nextInt(2,lcm-1);
        //int e = ThreadLocalRandom.current().nextInt(1,f-1);
        /*while(gcd(e, f)!=1){
            //e = ThreadLocalRandom.current().nextInt(1,f-1);
        }*/
        e = nextPrime(e);
        while(lcm%e==0||e<=2){
            e = nextPrime(e);
            while(e>lcm||lcm%e==0){
                e = priviousPrime(e);
            }
        }

        

        int d = modInverse(e, lcm);
        //System.out.println("lcm: "+lcm+" num1: "+num1+" num2: "+num2);
        System.out.println("Your Public Key is: N="+n+" E="+e+"\nYour Private Key is:  "+d);

    }

    public static void main(String[] args){
        
        //Menu
        System.out.println("Text Encription");
        System.out.println("Please Select the number");
        System.out.print("1. Encryption\n2. Decryption\n3. Generate Keys\nYour Choice: ");

        //Choose the function(1.Encryption 2.Decrytion)
        Scanner userInput = new Scanner(System.in);
        int choice = userInput.nextInt();
        
        //Encryption
        if (choice == 1){
            System.out.println("Encryption");
            userInput.nextLine();
            System.out.println("Please enter a message you want to encrypt");
            String msg = userInput.nextLine();
            Encryption(msg);
        }

        //Decryption
        else if(choice == 2){
            System.out.println("Decryption");
            userInput.nextLine();
            System.out.println("Please enter a message you want to decrypt");

            String msg = userInput.nextLine();
            Decryption(msg);
        }

        //Key Generator
        else if(choice == 3){
            
            System.out.print("Please Enter a number(11-100): ");
            int num = userInput.nextInt();
            if(num>10&&num<101){
                keyGenerator(num);
            }
            else{
                System.out.println("Invalid Value! Program Stop!");
            }
            
        }
        else{
            System.out.println("Invalid Value! Program Stop!");
        }
    }
}

