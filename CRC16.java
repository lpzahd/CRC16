/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crc16;

import java.util.Arrays;

//Tiempo para una trama de 1024 bytes, haciendo crc y detectando error es 6 segundos con 63 centesimas
//Quitando los println() ya no dura nada,

public class CRC16 {

//private final static String divisor = "10001000000100001";    
//private final static String divisor = "11000000000000101";
private final static String divisor = "10001000000100001"; //divisor  CRC-16-IBM  RAZON DE USO: https://en.wikipedia.org/wiki/Cyclic_redundancy_check#Computation_of_CRC
private static int longitudDivisor;    


public static void main(String... a) {
    //String bitsMandar = "010010000110100100100001";
    String bitsMandar = "01100010001001001001010101000101001111111011001011111011111010011000110000111001100101111100111011100110111000111011011110110000101101010110111110100101101000011011001100000000010000100010000110101100000001001001101010101011001111001001101010010011111100011010110100011000101100110101001001111010010111001111011011101100100101010011110110100000001111100101001011101000010101110010111110101101110110111001101101011101010001101001010000100001111100101000001000001001001001110000001000001011001111000000011001111100011010101010000111110011111100101101011010001010001110001000000000100001001101100111011110111100100100000010011101110001000010111100100100101110110010010100010100111010100010100011000001001010110011011101010010101110001011100011011110001010010100101010010100111010101001111110110010000111110011011010110001101111101000010010000100111101101010110000000101000111100100111011111100101011001001011011001001101110101100011001100100000101101101001001000001001000101111111000011101101110110001001001011110011000001100000110001000100100100101010100010100111111101100101111101111101001100011000011100110010111110011101110011011100011101101111011000010110101011011111010010110100001101100110000000001000010001000011010110000000100100110101010101100111100100110101001001111110001101011010001100010110011010100100111101001011100111101101110110010010101001111011010000000111110010100101110100001010111001011111010110111011011100110110101110101000110100101000010000111110010100000100000100100100111000000100000101100111100000001100111110001101010101000011111001111110010110101101000101000111000100000000010000100110110011101111011110010010000001001110111000100001011110010010010111011001001010001010011101010001010001100000100101011001101110101001010111000101110001101111000101001010010101001010011101010100111111011001000011111001101101011000110111110100001001000010011110110101011000000010100011110010011101111110010101100100101101100100110111010110001100110010000010110110100100100000100100010111111100001110110111011000100100101111001100000110000011000100010010010010101010001010011111110110010111110111110100110001100001110011001011111001110111001101110001110110111101100001011010101101111101001011010000110110011000000000100001000100001101011000000010010011010101010110011110010011010100100111111000110101101000110001011001101010010011110100101110011110110111011001001010100111101101000000011111001010010111010000101011100101111101011011101101110011011010111010100011010010100001000011111001010000010000010010010011100000010000010110011110000000110011111000110101010100001111100111111001011010110100010100011100010000000001000010011011001110111101111001001000000100111011100010000101111001001001011101100100101000101001110101000101000110000010010101100110111010100101011100010111000110111100010100101001010100101001110101010011111101100100001111100110110101100011011111010000100100001001111011010101100000001010001111001001110111111001010110010010110110010011011101011000110011001000001011011010010010000010010001011111110000111011011101100010010010111100110000011000001100010001001001001010101000101001111111011001011111011111010011000110000111001100101111100111011100110111000111011011110110000101101010110111110100101101000011011001100000000010000100010000110101100000001001001101010101011001111001001101010010011111100011010110100011000101100110101001001111010010111001111011011101100100101010011110110100000001111100101001011101000010101110010111110101101110110111001101101011101010001101001010000100001111100101000001000001001001001110000001000001011001111000000011001111100011010101010000111110011111100101101011010001010001110001000000000100001001101100111011110111100100100000010011101110001000010111100100100101110110010010100010100111010100010100011000001001010110011011101010010101110001011100011011110001010010100101010010100111010101001111110110010000111110011011010110001101111101000010010000100111101101010110000000101000111100100111011111100101011001001011011001001101110101100011001100100000101101101001001000001001000101111111000011101101110110001001001011110011000001100000110001000100100100101010100010100111111101100101111101111101001100011000011100110010111110011101110011011100011101101111011000010110101011011111010010110100001101100110000000001000010001000011010110000000100100110101010101100111100100110101001001111110001101011010001100010110011010100100111101001011100111101101110110010010101001111011010000000111110010100101110100001010111001011111010110111011011100110110101110101000110100101000010000111110010100000100000100100100111000000100000101100111100000001100111110001101010101000011111001111110010110101101000101000111000100000000010000100110110011101111011110010010000001001110111000100001011110010010010111011001001010001010011101010001010001100000100101011001101110101001010111000101110001101111000101001010010101001010011101010100111111011001000011111001101101011000110111110100001001000010011110110101011000000010100011110010011101111110010101100100101101100100110111010110001100110010000010110110100100100000100100010111111100001110110111011000100100101111001100000110000011000100010010010010101010001010011111110110010111110111110100110001100001110011001011111001110111001101110001110110111101100001011010101101111101001011010000110110011000000000100001000100001101011000000010010011010101010110011110010011010100100111111000110101101000110001011001101010010011110100101110011110110111011001001010100111101101000000011111001010010111010000101011100101111101011011101101110011011010111010100011010010100001000011111001010000010000010010010011100000010000010110011110000000110011111000110101010100001111100111111001011010110100010100011100010000000001000010011011001110111101111001001000000100111011100010000101111001001001011101100100101000101001110101000101000110000010010101100110111010100101011100010111000110111100010100101001010100101001110101010011111101100100001111100110110101100011011111010000100100001001111011010101100000001010001111001001110111111001010110010010110110010011011101011000110011001000001011011010010010000010010001011111110000111011011101100010010010111100110000011000001100010001001001001010101000101001111111011001011111011111010011000110000111001100101111100111011100110111000111011011110110000101101010110111110100101101000011011001100000000010000100010000110101100000001001001101010101011001111001001101010010011111100011010110100011000101100110101001001111010010111001111011011101100100101010011110110100000001111100101001011101000010101110010111110101101110110111001101101011101010001101001010000100001111100101000001000001001001001110000001000001011001111000000011001111100011010101010000111110011111100101101011010001010001110001000000000100001001101100111011110111100100100000010011101110001000010111100100100101110110010010100010100111010100010100011000001001010110011011101010010101110001011100011011110001010010100101010010100111010101001111110110010000111110011011010110001101111101000010010000100111101101010110000000101000111100100111011111100101011001001011011001001101110101100011001100100000101101101001001000001001000101111111000011101101110110001001001011110011000001100000110001000100100100101010100010100111111101100101111101111101001100011000011100110010111110011101110011011100011101101111011000010110101011011111010010110100001101100110000000001000010001000011010110000000100100110101010101100111100100110101001001111110001101011010001100010110011010100100111101001011100111101101110110010010101001111011010000000111110010100101110100001010111001011111010110111011011100110110101110101000110100101000010000111110010100000100000100100100111000000100000101100111100000001100111110001101010101000011111001111110010110101101000101000111000100000000010000100110110011101111011110010010000001001110111000100001011110010010010111011001001010001010011101010001010001100000100101011001101110101001010111000101110001101111000101001010010101001010011101010100111111011001000011111001101101011000110111110100001001000010011110110101011000000010100011110010011101111110010101100100101101100100110111010110001100110010000010110110100100100000100100010111111100001110110111011000100100101111001100000110000";
    
    
    
    
    String bitsMandar2 = "01100010001001001001010101000101001111111011001011111011111010011000110000111001100101111100111011100110111000111011011110110000101101010110111110100101101000011011001100000000010000100010000110101100000001001001101010101011001111001001101010010011111100011010110100011000101100110101001001111010010111001111011011101100100101010011110110100000001111100101001011101000010101110010111110101101110110111001101101011101010001101001010000100001111100101000001000001001001001110000001000001011001111000000011001111100011010101010000111110011111100101101011010001010001110001000000000100001001101100111011110111100100100000010011101110001000010111100100100101110110010010100010100111010100010100011000001001010110011011101010010101110001011100011011110001010010100101010010100111010101001111110110010000111110011011010110001101111101000010010000100111101101010110000000101000111100100111011111100101011001001011011001001101110101100011001100100000101101101001001000001001000101111111000011101101110110001001001011110011000001100000110001000100100100101010100010100111111101100101111101111101001100011000011100110010111110011101110011011100011101101111011000010110101011011111010010110100001101100110000000001000010001000011010110000000100100110101010101100111100100110101001001111110001101011010001100010110011010100100111101001011100111101101110110010010101001111011010000000111110010100101110100001010111001011111010110111011011100110110101110101000110100101000010000111110010100000100000100100100111000000100000101100111100000001100111110001101010101000011111001111110010110101101000101000111000100000000010000100110110011101111011110010010000001001110111000100001011110010010010111011001001010001010011101010001010001100000100101011001101110101001010111000101110001101111000101001010010101001010011101010100111111011001000011111001101101011000110111110100001001000010011110110101011000000010100011110010011101111110010101100100101101100100110111010110001100110010000010110110100100100000100100010111111100001110110111011000100100101111001100000110000011000100010010010010101010001010011111110110010111110111110100110001100001110011001011111001110111001101110001110110111101100001011010101101111101001011010000110110011000000000100001000100001101011000000010010011010101010110011110010011010100100111111000110101101000110001011001101010010011110100101110011110110111011001001010100111101101000000011111001010010111010000101011100101111101011011101101110011011010111010100011010010100001000011111001010000010000010010010011100000010000010110011110000000110011111000110101010100001111100111111001011010110100010100011100010000000001000010011011001110111101111001001000000100111011100010000101111001001001011101100100101000101001110101000101000110000010010101100110111010100101011100010111000110111100010100101001010100101001110101010011111101100100001111100110110101100011011111010000100100001001111011010101100000001010001111001001110111111001010110010010110110010011011101011000110011001000001011011010010010000010010001011111110000111011011101100010010010111100110000011000001100010001001001001010101000101001111111011001011111011111010011000110000111001100101111100111011100110111000111011011110110000101101010110111110100101101000011011001100000000010000100010000110101100000001001001101010101011001111001001101010010011111100011010110100011000101100110101001001111010010111001111011011101100100101010011110110100000001111100101001011101000010101110010111110101101110110111001101101011101010001101001010000100001111100101000001000001001001001110000001000001011001111000000011001111100011010101010000111110011111100101101011010001010001110001000000000100001001101100111011110111100100100000010011101110001000010111100100100101110110010010100010100111010100010100011000001001010110011011101010010101110001011100011011110001010010100101010010100111010101001111110110010000111110011011010110001101111101000010010000100111101101010110000000101000111100100111011111100101011001001011011001001101110101100011001100100000101101101001001000001001000101111111000011101101110110001001001011110011000001100000110001000100100100101010100010100111111101100101111101111101001100011000011100110010111110011101110011011100011101101111011000010110101011011111010010110100001101100110000000001000010001000011010110000000100100110101010101100111100100110101001001111110001101011010001100010110011010100100111101001011100111101101110110010010101001111011010000000111110010100101110100001010111001011111010110111011011100110110101110101000110100101000010000111110010100000100000100100100111000000100000101100111100000001100111110001101010101000011111001111110010110101101000101000111000100000000010000100110110011101111011110010010000001001110111000100001011110010010010111011001001010001010011101010001010001100000100101011001101110101001010111000101110001101111000101001010010101001010011101010100111111011001000011111001101101011000110111110100001001000010011110110101011000000010100011110010011101111110010101100100101101100100110111010110001100110010000010110110100100100000100100010111111100001110110111011000100100101111001100000110000011000100010010010010101010001010011111110110010111110111110100110001100001110011001011111001110111001101110001110110111101100001011010101101111101001011010000110110011000000000100001000100001101011000000010010011010101010110011110010011010100100111111000110101101000110001011001101010010011110100101110011110110111011001001010100111101101000000011111001010010111010000101011100101111101011011101101110011011010111010100011010010100001000011111001010000010000010010010011100000010000010110011110000000110011111000110101010100001111100111111001011010110100010100011100010000000001000010011011001110111101111001001000000100111011100010000101111001001001011101100100101000101001110101000101000110000010010101100110111010100101011100010111000110111100010100101001010100101001110101010011111101100100001111100110110101100011011111010000100100001001111011010101100000001010001111001001110111111001010110010010110110010011011101011000110011001000001011011010010010000010010001011111110000111011011101100010010010111100110000011000001100010001001001001010101000101001111111011001011111011111010011000110000111001100101111100111011100110111000111011011110110000101101010110111110100101101000011011001100000000010000100010000110101100000001001001101010101011001111001001101010010011111100011010110100011000101100110101001001111010010111001111011011101100100101010011110110100000001111100101001011101000010101110010111110101101110110111001101101011101010001101001010000100001111100101000001000001001001001110000001000001011001111000000011001111100011010101010000111110011111100101101011010001010001110001000000000100001001101100111011110111100100100000010011101110001000010111100100100101110110010010100010100111010100010100011000001001010110011011101010010101110001011100011011110001010010100101010010100111010101001111110110010000111110011011010110001101111101000010010000100111101101010110000000101000111100100111011111100101011001001011011001001101110101100011001100100000101101101001001000001001000101111111000011101101110110001001001011110011000001100000110001000100100100101010100010100111111101100101111101111101001100011000011100110010111110011101110011011100011101101111011000010110101011011111010010110100001101100110000000001000010001000011010110000000100100110101010101100111100100110101001001111110001101011010001100010110011010100100111101001011100111101101110110010010101001111011010000000111110010100101110100001010111001011111010110111011011100110110101110101000110100101000010000111110010100000100000100100100111000000100000101100111100000001100111110001101010101000011111001111110010110101101000101000111000100000000010000100110110011101111011110010010000001001110111000100001011110010010010111011001001010001010011101010001010001100000100101011001101110101001010111000101110001101111000101001010010101001010011101010100111111011001000011111001101101011000110111110100001001000010011110110101011000000010100011110010011101111110010101100100101101100100110111010110001100110010000010110110100100100000100100010111111100001110110111011000100100101111001100000110000";;
    //0000000000000000
    
    
    CRC16 crc = new CRC16();
    int[] hola = crc.getDividendoArrayFromString(bitsMandar, longitudDivisor);
    System.out.print("\n");
    int[] hola2 = crc.getDivisorArrayFromString(divisor, 0);
    byte[] hola3 = crc.calculate_crc(hola, hola2);
    
    System.out.println("\n\nSALTOOOOOOOOOOOOOO\n\n");
    boolean error_O_No = detectarError( bitsMandar2 , hola3);
    System.out.println("Se ha detectado error: " + error_O_No);
    
} 


public CRC16(){
    this.longitudDivisor = this.divisor.length();
}

public static int[] getDividendoArrayFromString(String dividendoString, int offset){
    
    char[] arrayBits = dividendoString.toCharArray();
    int[] exponentes = new int[arrayBits.length];
    int longitud = arrayBits.length;
    
    for(int i=0; i<arrayBits.length; i++){
        if(String.valueOf(arrayBits[i]).equals("1")){
            exponentes[i] = (longitud+offset) - (i+2);
        }else if(i==arrayBits.length){
            
        }else{
            exponentes[i] = -1;
        }
        
    }
    /*
    System.out.print(" \n ");
    for(int i=0; i<exponentes.length; i++){
        System.out.print(" $ " + exponentes[i] + " $ ");
    }
    System.out.print(" \n ");
    */
    return exponentes;
    
}

public static int[] getDividendoArrayFromString2(String dividendoString, int offset){
    
    char[] arrayBits = dividendoString.toCharArray();
    int[] exponentes = new int[arrayBits.length];
    int longitud = arrayBits.length;
    
    for(int i=0; i<arrayBits.length; i++){
        if(String.valueOf(arrayBits[i]).equals("1")){
            exponentes[i] = (longitud+offset) - (i);
        }else if(i==arrayBits.length){
            
        }else{
            exponentes[i] = -1;
        }
        
    }
    /*
    System.out.print(" \n ");
    for(int i=0; i<exponentes.length; i++){
        System.out.print(" $ " + exponentes[i] + " $ ");
    }
    System.out.print(" \n ");
    */
    
    return exponentes;
    
}

public static int[] getDivisorArrayFromString(String dividendoString, int offset){
    
    char[] arrayBits = dividendoString.toCharArray();
    int[] exponentes = new int[arrayBits.length];
    int longitud = arrayBits.length;
    
    for(int i=0; i<arrayBits.length; i++){
        if(String.valueOf(arrayBits[i]).equals("1")){
            exponentes[i] = (longitud+offset) - (i+1);
        }else if(i==arrayBits.length){
            
        }else{
            exponentes[i] = -1;
        }
        
    }
    /*
    for(int i=0; i<exponentes.length; i++){
        System.out.print(" $ " + exponentes[i] + " $ ");
    }
    */
    return exponentes;
    
}

byte[] calculate_crc(int[] dividendoOriginal, int[] divisor) {
    
    int[] dividendo = new int[dividendoOriginal.length+this.longitudDivisor-1];
    int[] resultado = new int[dividendoOriginal.length];
    
    
    for(int i=0; i<dividendoOriginal.length;i++){
        dividendo[i] = dividendoOriginal[i];
    }
    for(int i=0; i<divisor.length-1;i++){
        dividendo[i+dividendoOriginal.length] = -1;
    }
    
    /*
    System.out.print("\nLEN:  " + (dividendo.length) + "\n");
    for(int i=0; i<dividendo.length; i++){
            System.out.print(" $ " + dividendo[i] + " $ ");
    }
    System.out.print("\n");
    */
    
    int multiplicador = 1;
    
    int indice = 0;
    while(multiplicador >= 0){
        //System.out.println("HOLA");
        int indiceMayor=0;
        for(int i=0; i<dividendo.length; i++){
            if(dividendo[i]==-1){
            }else{
                indiceMayor = dividendo[i];
                //System.out.println("HOLA: " + indiceMayor);
                break;
            }
        }
        
        //System.out.println("HOLA2: " + divisor[0]);
        
        multiplicador = indiceMayor -  divisor[0];
        if(multiplicador <0){
            break;
        }
//        resultado[multiplicador] = multiplicador;
        
        for(int i=0; i<divisor.length; i++){
            if(divisor[i]!=-1){
                int restador = multiplicador + divisor[i];
                
                /*System.out.println("MULTIPLICADOR: " + multiplicador);
                System.out.println("Divisor: " + divisor[i]);
                System.out.println("Restador: " + restador);
                System.out.println("Restando  : " + dividendo[Math.abs(restador+1-dividendo.length)] + "\n");
                */
                
                if(restador==dividendo[Math.abs(restador+1-dividendo.length)]){
                    /*System.out.println("Restador: " + restador);
                    System.out.println("Restando  : " + dividendo[Math.abs(restador+1-dividendo.length)] + "\n");*/
                    dividendo[Math.abs(restador+1-dividendo.length)] = -1;
                }else{
                    //System.out.println("AAAAA \n");
                    dividendo[Math.abs(restador+1-dividendo.length)] = restador;
                }
            }
        }
        /*
        for(int i=0; i<dividendo.length; i++){
            System.out.print(" $ " + dividendo[i] + " $ ");
        }
        System.out.print("\n");
        */
        
    }
    
    String crc="";
    
    for(int i=dividendo.length-1; i>=0; i--){
        if(i<dividendoOriginal.length){
            //System.out.println("HEY: ");
            break;
        }
        if(dividendo[i]!=-1){
            crc = "1" + crc ;
            //System.out.println("HEY: " + crc);
        }else{
            crc = "0" + crc ;
            //System.out.println("HEY: " + crc);
        }
    }
    
    System.out.println("CRC: " + crc);
    char[] arrayBits = crc.toCharArray();
    byte[] bytes = new byte[crc.length()/8];
    
    
    for(int i=0; i<crc.length()/8; i++){
        String binaryString = "";
        for(int j=0; j<8 ; j++){
            binaryString = binaryString + arrayBits[j+(8*i)];
        }
        //System.out.println("Binary String: " + binaryString);
        byte twosComplement = getByte(binaryString);
        bytes[i] = twosComplement;
        //System.out.println("Complementos dos: " + twosComplement);
    }
    
    return bytes;
}

public byte getByte(String binaryString) 
    { 
        binaryString = turnStrignAround(binaryString);
        char[] arrayBits = binaryString.toCharArray();
        
        int byteEquivalent = 0;
        
        for(int i=0; i<binaryString.length(); i++){
            if(arrayBits[i]=='1'){
                if(i==binaryString.length()-1){
                    byteEquivalent = byteEquivalent - (int)Math.pow(2, i);
                }else{
                    byteEquivalent = byteEquivalent + (int)Math.pow(2, i);
                }
        }
        
    }   
        
    return (byte)byteEquivalent;
}

public String turnStrignAround(String normal){
    StringBuilder turnedAround = new StringBuilder();
        
    for(int i = normal.length() - 1; i >= 0; i--)
    {
        turnedAround.append(normal.charAt(i));
    }

    System.out.println("Reversed string is:");
    System.out.println(turnedAround.toString());
    
    return turnedAround.toString();
    
}


public static boolean detectarError(/*byte[]*/ String mensaje, byte[] crc){
    
   String cadenaMensaje = mensaje /* convertirBytesToString(mensaje) */;
   String cadenaCrc =  convertirBytesToString(crc);
   
   String mensajeCompleto = cadenaMensaje + cadenaCrc;
   String residuo = longDivision(mensajeCompleto);
   
   char[] arrayBits = residuo.toCharArray();
   
   for(int i=0; i< arrayBits.length; i++){
       if(arrayBits[i]=='1'){
           return true;
       }
   }
   
   
   return false;
    
}

public static String longDivision(String mensajeCompleto){
    
    int[] divisorArray = getDivisorArrayFromString(divisor,0);
    int[] dividendoArray = getDividendoArrayFromString2(mensajeCompleto,0);
    
    /*
    System.out.print("\nLEN:  " + (dividendoArray.length) + "\n");
    for(int i=0; i<dividendoArray.length; i++){
            System.out.print(" $ " + dividendoArray[i] + " $ ");
    }
    System.out.print("\n");
    System.out.print("\nLEN:  " + (divisorArray.length) + "\n");
    for(int i=0; i<divisorArray.length; i++){
            System.out.print(" $ " + divisorArray[i] + " $ ");
    }
    System.out.print("\n");
    */
    
    int multiplicador = 1;
    
    int indice = 0;
    while(multiplicador >= 0){
        //System.out.println("HOLA");
        int indiceMayor=0;
        for(int i=0; i<dividendoArray.length; i++){
            if(dividendoArray[i]==-1){
            }else{
                indiceMayor = dividendoArray[i];
                //System.out.println("HOLA: " + indiceMayor);
                break;
            }
        }
        
        //System.out.println("HOLA2: " + divisorArray[0]);
        
        multiplicador = indiceMayor -  divisorArray[0];
        if(multiplicador <=0){
            break;
        }
//        resultado[multiplicador] = multiplicador;
        
        for(int i=0; i<divisorArray.length; i++){
            if(divisorArray[i]!=-1){
                int restador = multiplicador + divisorArray[i];
                /*
                System.out.println("MULTIPLICADOR: " + multiplicador);
                System.out.println("Divisor: " + divisorArray[i]);
                System.out.println("LEN: " + dividendoArray.length);
                System.out.print("\nLEN:  " + (dividendoArray.length) + "\n");
                
                System.out.println("Restador: " + restador);
                System.out.println("Restando  : " + dividendoArray[Math.abs(restador-dividendoArray.length)] + "\n");
                */
                if(restador==dividendoArray[Math.abs(restador-dividendoArray.length)]){
                    /*System.out.println("Restador: " + restador);
                    System.out.println("Restando  : " + dividendoArray[Math.abs(restador-dividendoArray.length)] + "\n");*/
                    dividendoArray[Math.abs(restador-dividendoArray.length)] = -1;
                }else{
                    //System.out.println("AAAAA \n");
                    dividendoArray[Math.abs(restador-dividendoArray.length)] = restador;
                }
            }
        }
        /*
        for(int i=0; i<dividendoArray.length; i++){
            System.out.print(" $ " + dividendoArray[i] + " $ ");
        }
        System.out.print("\n");
        */
        
    }
    
    String residuo="";
    
    for(int i=dividendoArray.length-1; i>=0; i--){
        if(dividendoArray[i]!=-1){
            residuo = "1" + residuo ;
            //System.out.println("RESIDUO: " + residuo);
        }else{
            residuo = "0" + residuo ;
            //System.out.println("RESIDUO: " + residuo);
        }
    }
    
    //System.out.println("RESIDUO: " + residuo);
    
    return residuo;
    
}



public static String convertirBytesToString(byte[] bytesArray){
        
        String tramaTrabajando = "";
        for(int i=0; i <bytesArray.length; i++){
            //System.out.println("Hola: " + bytesArray[i]);
            tramaTrabajando = tramaTrabajando + String.format("%8s", Integer.toBinaryString(bytesArray[i] & 0xFF)).replace(' ', '0');
        }
        
        //System.out.println("Hola 3: " + tramaTrabajando);
        return tramaTrabajando;
}


}