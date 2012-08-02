package com.aulo;
import java.io.*; 
import java.awt.*;
import java.util.*; 
public class StrTools
{
  //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //aulo add == unicode format
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

public  String unicodeToStr(String unicode)
{   //char c;
     int start = 0;
    int end = 0;
    final StringBuffer buffer = new StringBuffer();
    while( start > -1 ) {
      end = unicode.indexOf( "\\u", start + 2 ); //\\\\u
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
/*
 * 统计汉字的个数
 */
  public String getChineseChar(String str)
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

 //aulo add========================================
 	private String toUnicode(String theString, boolean escapeSpace) { 
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

  private static char toHex(int nibble) 
  { 
    return hexDigit[ (nibble & 0xF)]; 
  } 

  private static final char[] hexDigit = { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 
      'F' 
  }; 
 /*
 手机上通用的UTF8转换程序 
 http://www.j2medev.com/code/j2me/gui/200607/2787.html
 最近发现JAVA的UTF转换函数有BUG，在某些手机上（如K700）会出现乱码，与是上网查询了一下，
 在国外论坛上找到了一个UTF8转换函数，在我们公司所有测试机上都能正常转换，觉得效果还可以
 */
private final String readUnicodeFileUTF8(String filename) {
        StringBuffer sb = new StringBuffer(256);
        try {
            int[] surrogatePair = new int[2];
            InputStream is = this.getClass().getResourceAsStream(filename);

            int val = 0;
            int unicharCount = 0;
            while ((val = readNextCharFromStreamUTF8(is))!=-1) {
                unicharCount++;
                if (val <= 0xFFFF) {
                    // if first value is the Byte Order Mark (BOM), do not add
                    if (! (unicharCount == 1 && val == 0xFEFF)) {
                        sb.append((char)val);
                    }
                } else {
                    supplementCodePointToSurrogatePair(val, surrogatePair);
                    sb.append((char)surrogatePair[0]);
                    sb.append((char)surrogatePair[1]);
                }
            }
            is.close();
        } catch (Exception e) {};

        return new String(sb);
    }
   
    private final static int readNextCharFromStreamUTF8(InputStream is) {
        int c = -1;
        if (is==null) return c;
        boolean complete = false;
       
        try {
            int byteVal;
            int expecting=0;
            int composedVal=0;
           
            while (!complete && (byteVal = is.read()) != -1) {
                if (expecting > 0 && (byteVal & 0xC0) == 0x80) {  /* 10xxxxxx */
                    expecting--;
                    composedVal = composedVal | ((byteVal & 0x3F) << (expecting*6));
                    if (expecting == 0) {
                        c = composedVal;
                        complete = true;
                        //System.out.println("appending: U+" + Integer.toHexString(composedVal) );
                    }
                } else {
                    composedVal = 0;
                    expecting = 0;
                    if ((byteVal & 0x80) == 0) {    /* 0xxxxxxx */
                        // one byte character, no extending byte expected
                        c = byteVal;
                        complete = true;
                        //System.out.println("appending: U+" + Integer.toHexString(byteVal) );
                    } else if ((byteVal & 0xE0) == 0xC0) {  /* 110xxxxx */
                        expecting = 1;  // expecting 1 extending byte
                        composedVal = ((byteVal & 0x1F) << 6);
                    } else if ((byteVal & 0xF0) == 0xE0) {  /* 1110xxxx */
                        expecting = 2;  // expecting 2 extending bytes
                        composedVal = ((byteVal & 0x0F) << 12);
                    } else if ((byteVal & 0xF8) == 0xF0) {  /* 11110xxx */
                        expecting = 3;  // expecting 3 extending bytes
                        composedVal = ((byteVal & 0x07) << 18);
                    } else {
                        // non conformant utf-8, ignore or catch error
                    }
                }
            }
           
        } catch (Exception e) {
            System.out.println(e.toString());
        }
       
        return c;
    }

    private final static void supplementCodePointToSurrogatePair(int codePoint, int[] surrogatePair) {
        int high4 = ((codePoint >> 16) & 0x1F) - 1;
        int mid6 = ((codePoint >> 10) & 0x3F);
        int low10 = codePoint & 0x3FF;

        surrogatePair[0] = (0xD800 | (high4 << 6) | (mid6));
        surrogatePair[1] = (0xDC00 | (low10));
    }


};