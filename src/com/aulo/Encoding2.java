package com.aulo;
import java.io.*; 
//import java.awt.*;
//import java.util.*;
public class  Encoding2
{
	public  String UTF8ToStr(String unicode)
{   //char c;
     int start = 0;
    int end = 0;
    final StringBuffer buffer = new StringBuffer();
    while( start > -1 ) {
      end = unicode.indexOf( "\\\\u", start + 2 ); 
      String charStr = "";
      if( end == -1 ) {
        charStr = unicode.substring( start + 2, unicode.length() );
      } else {
        charStr = unicode.substring( start + 2, end);
      }
      char letter = (char) Integer.parseInt( charStr, 16 );
      buffer.append( new Character( letter ).toString() );
      start = end;
    }
    return buffer.toString();
}
 public static String toFormatUnicode(String strText)
		 throws UnsupportedEncodingException 
{
           char c; 
			String strRet = ""; 
			int intAsc; 
			String strHex; 
			for (int i = 0; i < strText.length(); i++) { 
			    	c = strText.charAt(i); 
				    intAsc = (int) c; 
					strHex = Integer.toHexString(intAsc); 
					if (intAsc > 128) { 
					     strRet += "\\u" + strHex;
					}else{
						 strRet += "\\u00" + strHex;
					}
			}	
		return strRet;
}
//aulo add========================================
 	public static String toUnicode(String theString, boolean escapeSpace) { 
		int len = theString.length(); 
		int bufLen = len * 2; 
		if (bufLen < 0) { 
			bufLen = Integer.MAX_VALUE; 
		} 
		StringBuffer outBuffer = new StringBuffer(bufLen); 

		for (int x = 0; x < len; x++) { 
			char aChar = theString.charAt(x); 
			// Handle common case first, selecting largest block that 
			// avoids the specials below 
			if ( (aChar > 61) && (aChar < 127)) { 
			if (aChar == '\\') { 
				outBuffer.append('\\'); 
				outBuffer.append('\\'); 
				continue; 
		} 
        outBuffer.append(aChar); 
        continue; 
      } 
      switch (aChar) { 
        case ' ': 
          if (x == 0 || escapeSpace) { 
            outBuffer.append('\\'); 
          } 
          outBuffer.append(' '); 
          break; 
        case '\t': 
          outBuffer.append('\\'); 
          outBuffer.append('t'); 
          break; 
        case '\n': 
          outBuffer.append('\\'); 
          outBuffer.append('n'); 
          break; 
        case '\r': 
          outBuffer.append('\\'); 
          outBuffer.append('r'); 
          break; 
        case '\f': 
          outBuffer.append('\\'); 
          outBuffer.append('f'); 
          break; 
        case '=': // Fall through 
        case ':': // Fall through 
        case '#': // Fall through 
        case '!': 
          outBuffer.append('\\'); 
          outBuffer.append(aChar); 
          break; 
        default: 
          if ( (aChar < 0x0020) || (aChar > 0x007e)) { 
            outBuffer.append('\\'); 
            outBuffer.append('U'); 
            outBuffer.append(toHex( (aChar >> 12) & 0xF)); 
            outBuffer.append(toHex( (aChar >> 8) & 0xF)); 
            outBuffer.append(toHex( (aChar >> 4) & 0xF)); 
            outBuffer.append(toHex(aChar & 0xF)); 
          } 
          else { 
            outBuffer.append(aChar); 
          } 
      } 
    } 
    return outBuffer.toString(); 
  } 

public  static String unicodeToStr(String unicode)
{   //char c;
     int start = 0; 
	 int end = 0;
	 int nextU = 0;
    final StringBuffer buffer = new StringBuffer();
    start = unicode.indexOf( "\\u", start); 
	if(start>0){
      buffer.append( unicode.substring(0, start));
	}
    while( start > -1 ) {
	  end = start + 2 +4;
	  //indexOf(String str, int fromIndex)从指定的索引处开始，返回第一次出现的指定子字符串在此字符串中的索引
      String charStr = "";
	  if(end >unicode.length()){ 
	     buffer.append( unicode.substring(start, unicode.length()));
		 System.out.println("000->"+unicode.substring(start, unicode.length()));
		 start = -1;
	  }else{
	    if(chkIsUnicode(unicode.substring( start , end)))
		 {
              charStr = unicode.substring( start+2 , end);//+ 2
               char letter = (char) Integer.parseInt( charStr, 16 );
		       buffer.append( new Character( letter ).toString() ); 
			   System.out.println("111->"+unicode.substring(start, end));
		       start = end;
		 }else{
               buffer.append(unicode.substring(start , end)); 
			   System.out.println("111##->"+unicode.substring(start, end));
		       start = end;
		 }
      start = unicode.indexOf( "\\u", start);
	  if( start == -1 ) { 
		 if(end<=unicode.length()-1)
		  {
			  buffer.append( unicode.substring(end, unicode.length()) );
			  System.out.println("222->"+unicode.substring(end, unicode.length()));
		}
      }else{
              //System.out.println("333#start="+start+"#end="+end);
		  if(start>end){
			 buffer.append( unicode.substring(end, start) );
			 System.out.println("333->"+unicode.substring(end, start));
		  }
	  }
	 }
    }//end while
    return buffer.toString();
}
public static  boolean chkIsUnicode(String str)
{   int num =0;
	int start = str.indexOf( "\\u", 0); 
    if(start!=0) { return false;}
	else{//四个十六进制数表示一个unicode编码
      if(str.length()!=6){ return false;}
	  else{
		 char tmpchar;
	     for(int index =2;index<6;index++)
		 {   
			 tmpchar = str.charAt(index);
			 if((tmpchar>='0' && tmpchar<='9') 
				 || (tmpchar>='A' && tmpchar<='F')
				 || (tmpchar>='a' && tmpchar<='f')
			     ){
                 num=num+1;
			 }
		 }
		 if(num==4){return true;}
		 else{ return false;}
	  }
	}
	//return false;
 }
  private static char toHex(int nibble) 
  { 
    return hexDigit[ (nibble & 0xF)]; 
  } 

  private static final char[] hexDigit = { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 
      'F' 
  };
/*
 * 统计汉字的个数
 */
  public static String getChineseChar(String str)
   {
	StringBuffer sb = new StringBuffer();
	String tempStr;
	int count = 0;
	for (int i = 0; i < str.length(); i++) 
	{
		tempStr = String.valueOf(str.charAt(i));
		if (tempStr.getBytes().length == 2) 
		{
			sb.append(tempStr);
			count++;
		}
	}
	sb.append("====汉字的总数为："+count);
   return sb.toString();
  }  
}
