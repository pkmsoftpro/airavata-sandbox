/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.airavata.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static final String DELIMETER=",";
	public static final String QUOTE="\"";
	
	// Merits for the following function should go to 
	// http://blog.houen.net/java-get-url-from-string/ 
	public static List<String> getURLS(String text) {
		List<String> links = new ArrayList<String>();
		String regex = "\\(?\\b((http|https|ftp)://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while (m.find()) {
			String urlStr = m.group();
			if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
				urlStr = urlStr.substring(1, urlStr.length() - 1);
			}
			if (!links.contains(urlStr)) {
				links.add(urlStr);
			}
		}
		return links;
	}

	public static String createHTMLUrlTaggedString2(String value, List<String> pullLinks) {
		for (String url : pullLinks) {
			String hyperlinkString="<a href='"+url+"'>"+url+"</a>";
			value=value.replaceAll(Pattern.quote(url), hyperlinkString);
		}
		return value;
	}
	public static String createHTMLUrlTaggedString(String value) {
		String urledString = "";
		int lastIndex=0,index=0;
		while(index!=-1){
			index=value.toLowerCase().indexOf("://",lastIndex);
			if (index!=-1){
				int beginIndex=value.lastIndexOf(" ",index);
				urledString+=value.substring(lastIndex,beginIndex+1);
				int endIndex=value.indexOf(" ",index);
				if (beginIndex==-1){
					beginIndex=0;
				}else{
					beginIndex++;
				}
				if (endIndex==-1){
					endIndex=value.length();
				}
				String url=value.substring(beginIndex, endIndex);
				urledString+="<a href='"+url+"'>"+url+"</a>";
				lastIndex=endIndex;
			}
		}
		urledString+=value.substring(lastIndex, value.length());
		return urledString;
	}
	
	private static boolean isQuoted(String s, String delimiter){
		//Check if we need quotes
		if (s.contains(delimiter)){
			//Check if its already quoted
			s=s.replaceAll("\"\"", "");
			return (s.substring(0,1).equals(QUOTE) && s.subSequence(s.length()-1, s.length()).equals(QUOTE));
		}
		//no delimiters present, so already in proper form
		return true;
	}
	
	private static boolean isQuoted(String s){
		return isQuoted(s, DELIMETER);
	}
	
	/**
	 * Create a delimiter separated string out of a list
	 * @param list
	 * @return
	 */
	public static String createDelimiteredString(String[] list) {
        return createDelimiteredString(list, DELIMETER);
    }


    /**
	 * Create a delimiter separated string out of a list
	 * @param list
	 * @return
	 */
	public static String createDelimiteredString(String[] list,String delimiter){
		String s=null;
		for (String ss : list) {
			ss=quoteString(ss, delimiter);
			if (s==null){
				s=ss;
			}else{
				s+=delimiter +ss;
			}
		}
		return s;
	}

	/**
	 * Return a proper quoted string if the string contains the delimiter character
	 * @param s
	 * @return
	 */
	public static String quoteString(String s) {
        return quoteString(s, DELIMETER);
    }


    /**
	 * Return a proper quoted string if the string contains the delimiter character
	 * @param s
	 * @return
	 */
	public static String quoteString(String s,String delimiter){
		if (isQuoted(s,delimiter)){
			return s;
		}else{
			return QUOTE+s.replaceAll(QUOTE, QUOTE+QUOTE)+QUOTE;
		}
	}

	/**
	 * Parse the delimitered string and return elements as a string array 
	 * @param s
	 * @return
	 */
	public static String[] getElementsFromString(String s, String delimeter, String quote) {
		List<String> list=new ArrayList<String>();
		String currentItem="";
		String previousChar=null;
		boolean insideQuote=false;
		for(int i=0;i<s.length();i++){
			String c=s.substring(i,i+1);
			if (c.equals(delimeter)){
				//if not inside a quoted string ignore the delimiter character
				if (insideQuote) {
					currentItem+=c;
				}else{
					list.add(currentItem);
					currentItem = "";
				}
			}else if (c.equals(quote)){
				if (quote.equals(previousChar)){
					//which means previousChar was an escape character, not a quote for the string
					currentItem+=quote;
					if (insideQuote){
						//mistakenly thought previous char was opening quote char, thus need to make this false
						insideQuote=false;
					}else{
						//mistakenly thought previous char was closing quote char, thus need to make this true
						insideQuote=true;
					}
				} else{
					if (insideQuote){
						//quote ended
						insideQuote=false;
					}else{
						//quote beginning
						insideQuote=true;
					}
				}
			}else{
				currentItem+=c;
			}
			previousChar=c;
		}
		list.add(currentItem);
		return list.toArray(new String[]{});
	}
	
	/**
	 * Parse the delimitered string and return elements as a string array 
	 * @param s
	 * @return
	 */
	public static String[] getElementsFromString(String s) {
		return getElementsFromString(s, DELIMETER, QUOTE);
	}

    /**
     * Converts object to String without worrying about null check.
     * 
     * @param object
     * @return The object.toString if object is not null; "" otherwise.
     */
    public static String toString(Object object) {
        if (object == null) {
            return "";
        } else {
            return object.toString();
        }
    }

    /**
     * Trims a specified string, and makes it null if the result is empty string.
     * 
     * @param string
     * @return the string processed
     */
    public static String trimAndNullify(String string) {
        if (string != null) {
            string = string.trim();
            if (string.equals("")) {
                string = null;
            }
        }
        return string;
    }

    /**
     * @param oldName
     * @return Trimmed String
     */
    public static String trimSpaceInString(String oldName) {
        if (oldName == null) {
            return "";
        }
        return oldName.replace(" ", "");
    }

    /**
     * Converts a specified string to a Java identifier.
     * 
     * @param name
     * @return the Java identifier
     */
    public static String convertToJavaIdentifier(String name) {

        final char REPLACE_CHAR = '_';

        if (name == null || name.length() == 0) {
            return "" + REPLACE_CHAR;
        }

        StringBuilder buf = new StringBuilder();

        char c = name.charAt(0);
        if (!Character.isJavaIdentifierStart(c)) {
            // Add _ at the beggining instead of replacing it to _. This is
            // more readable if the name is like 3D_Model.
            buf.append(REPLACE_CHAR);
        }

        for (int i = 0; i < name.length(); i++) {
            c = name.charAt(i);
            if (Character.isJavaIdentifierPart(c)) {
                buf.append(c);
            } else {
                buf.append(REPLACE_CHAR);
            }
        }

        return buf.toString();
    }

    /**
     * Creates a new name by incrementing the number after the underscore at the end of the old name. If there is no
     * underscore and number at the end, put "_2" at the end.
     * 
     * @param oldName
     * @return the new name
     */
    public static String incrementName(String oldName) {

        final char PREFIX = '_';

        String newName;
        if (oldName == null || oldName.length() == 0) {
            newName = "noName";
        } else {
            int lastDashIndex = oldName.lastIndexOf(PREFIX);
            if (lastDashIndex < 0) {
                newName = oldName + PREFIX + 2;
            } else {
                String suffix = oldName.substring(lastDashIndex + 1);
                try {
                    int number = Integer.parseInt(suffix);
                    int newNumber = number + 1;
                    newName = oldName.substring(0, lastDashIndex + 1) + newNumber;
                } catch (RuntimeException e) {
                    // It was not a number
                    newName = oldName + PREFIX + 2;
                }
            }
        }
        return newName;
    }

    /**
     * Returns the local class name of a specified class.
     * 
     * @param klass
     *            The specified class
     * @return The local class name
     */
    public static String getClassName(Class klass) {
        String fullName = klass.getName();
        int index = fullName.lastIndexOf(".");
        if (index < 0) {
            return fullName;
        } else {
            return fullName.substring(index + 1);
        }
    }

    /**
     * @param throwable
     * @return The stackTrace in String
     */
    public static String getStackTraceInString(Throwable throwable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        throwable.printStackTrace(printStream);
        printStream.flush();
        return byteArrayOutputStream.toString();
    }

}