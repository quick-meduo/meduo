/*
 * Copyright (c) 2019 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quick.meduo.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

 /**
  *@Description:
  * String operations utility
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2019/11/12
  *@Since: 1.0
  */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
    private static Pattern pattern = Pattern.compile("<([a-zA-Z]+)[^<>]*>");

     /**
      * Convert string to byte array, by using UTF-8 encoding
      * @param str
      * @return
      */
    public static byte[] getBytes(String str){
        if (str != null){
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }else{
            return null;
        }
    }

     /**
      * Convert byte array to String with UTF-8 encoding
      * @param bytes
      * @return
      */
    public static String toString(byte[] bytes){
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return StringUtils.EMPTY;
        }
    }

     /**
      * Check if v string inclued in the give string list
      * @param v
      * @param list
      * @return
      */
    public static boolean Contain(String v, String... list){
        if (v != null){
            for (String s : list){
                if (v.equals(StringUtils.trim(s))){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Convert given object to Double
     */
    public static Double toDouble(Object val){
        if (val == null){
            return 0D;
        }
        try {
            return Double.valueOf(StringUtils.trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * Convert given object to float
     */
    public static Float toFloat(Object val){
        return toDouble(val).floatValue();
    }

    /**
     * Convert input to long
     */
    public static Long toLong(Object val){
        return toDouble(val).longValue();
    }

    /**
     * Convert input to int
     */
    public static Integer toInteger(Object val){
        return toLong(val).intValue();
    }

    /**
     * convert input to camelCase
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * convert input to camelCase with first letter capitalized
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * convert input to under score case
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * set val if not null
     */
    public static void setValueIfNotBlank(String target, String source) {
        if (StringUtils.isNotBlank(source)){
            target = source;
        }
    }



    /**
     * camel case to under score conversion
     */
    public static String camelToUnderline(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

}
