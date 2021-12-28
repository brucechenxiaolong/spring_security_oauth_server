package com.test.springcloud.util;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Locale;

/**
 * StringUtils.from apache common-lang3
 *
 */
public class StringUtils {
	
public static final String DOT = ".";
    
    private static final int INDEX_NOT_FOUND = -1;
    
    public static final String COMMA = ",";
    
    public static final String EMPTY = "";
    
    public static final String LF = "\n";
    

    public static String newStringForUtf8(byte[] bytes) {
        return new String(bytes, Charset.forName("UTF-8"));
    }
    
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    public static String defaultIfEmpty(String str, String defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }
    
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }
    
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }
    
    public static String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != INDEX_NOT_FOUND) {
            int end = str.indexOf(close, start + open.length());
            if (end != INDEX_NOT_FOUND) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }
    
    @SuppressWarnings("rawtypes")
	public static String join(Collection collection, String separator) {
        if (collection == null) {
            return null;
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        Object[] objects = collection.toArray();
        
        for (int i = 0; i < collection.size(); i++) {
            if (objects[i] != null) {
                stringBuilder.append(objects[i]);
                if (i != collection.size() - 1 && separator != null) {
                    if (separator != null) {
                        stringBuilder.append(separator);
                    }
                }
            }
        }
        
        return stringBuilder.toString();
    }
    
    public static String escapeJavaScript(String str) {
        return escapeJavaStyleString(str, true, true);
    }
    
    private static String escapeJavaStyleString(String str, boolean escapeSingleQuotes, boolean escapeForwardSlash) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter writer = new StringWriter(str.length() * 2);
            escapeJavaStyleString(writer, str, escapeSingleQuotes, escapeForwardSlash);
            return writer.toString();
        } catch (IOException ioe) {
            // this should never ever happen while writing to a StringWriter
            return null;
        }
    }
    
    private static void escapeJavaStyleString(Writer out, String str, boolean escapeSingleQuote,
            boolean escapeForwardSlash) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        }
        if (str == null) {
            return;
        }
        int sz;
        sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);
            
            // handle unicode
            if (ch > 0xfff) {
                out.write("\\u" + hex(ch));
            } else if (ch > 0xff) {
                out.write("\\u0" + hex(ch));
            } else if (ch > 0x7f) {
                out.write("\\u00" + hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.write('\\');
                        out.write('b');
                        break;
                    case '\n':
                        out.write('\\');
                        out.write('n');
                        break;
                    case '\t':
                        out.write('\\');
                        out.write('t');
                        break;
                    case '\f':
                        out.write('\\');
                        out.write('f');
                        break;
                    case '\r':
                        out.write('\\');
                        out.write('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            out.write("\\u00" + hex(ch));
                        } else {
                            out.write("\\u000" + hex(ch));
                        }
                        break;
                }
            } else {
                switch (ch) {
                    case '\'':
                        if (escapeSingleQuote) {
                            out.write('\\');
                        }
                        out.write('\'');
                        break;
                    case '"':
                        out.write('\\');
                        out.write('"');
                        break;
                    case '\\':
                        out.write('\\');
                        out.write('\\');
                        break;
                    case '/':
                        if (escapeForwardSlash) {
                            out.write('\\');
                        }
                        out.write('/');
                        break;
                    default:
                        out.write(ch);
                        break;
                }
            }
        }
    }
    
    private static String hex(char ch) {
        return Integer.toHexString(ch).toUpperCase(Locale.ENGLISH);
    }
    
    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        String str1 = str.toString().toLowerCase();
        String str2 = searchStr.toString().toLowerCase();
        return str1.contains(str2);
    }
    
    public static boolean contains(final CharSequence str, final CharSequence searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.toString().contains(searchStr);
    }
    
    public static boolean isNoneBlank(final CharSequence... css) {
        return !isAnyBlank(css);
    }
    
    public static boolean isAnyBlank(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean startsWith(final CharSequence str, final CharSequence prefix) {
        return startsWith(str, prefix, false);
    }
    
    private static boolean startsWith(final CharSequence str, final CharSequence prefix, final boolean ignoreCase) {
        if (str == null || prefix == null) {
            return str == null && prefix == null;
        }
        if (prefix.length() > str.length()) {
            return false;
        }
        if (ignoreCase) {
            String lowerCaseStr = str.toString().toLowerCase();
            String lowerCasePrefix = str.toString().toLowerCase();
            return lowerCaseStr.startsWith(lowerCasePrefix);
        } else {
            return str.toString().startsWith(prefix.toString());
        }
    }
    
    public static boolean startsWithIgnoreCase(final CharSequence str, final CharSequence prefix) {
        return startsWith(str, prefix, true);
    }
    
    public static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }
    
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }
    
    public static String[] split(final String str, String separatorChars) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new String[0];
        }
        if (separatorChars == null){
            separatorChars = " +";
        }
        return str.split(separatorChars);
    }
    

}
