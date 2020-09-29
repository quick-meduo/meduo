/*
 *
 *  * Copyright (c) 2019.  the original author or authors.
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.quick.meduo.common.utils;

public class XORUtils {
    private static XORUtils instance;

    private XORUtils() {
    }
    public static XORUtils getInstance() {
        if (instance == null) {
            synchronized (XORUtils.class) {
                if (instance == null) {
                    instance = new XORUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 对一个数字进行异或加解密
     * @param  res int类型
     * @param  key String类型
     * @return int对象
     * @throws
     */
    public int code(int res, String key) {
        return res ^ key.hashCode();
    }

    /**
     * 异或加密
     * @param  res String类型
     * @param  key String类型
     * @return String对象
     * @throws
     */
    public String encode(String res, String key) {
        byte[] bs = res.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return parseByte2HexStr(bs);
    }

    /**
     * 异或解密
     * @param  res String类型
     * @param  key String类型
     * @return String对象
     * @throws
     */
    public String decode(String res, String key) {
        byte[] bs = parseHexStr2Byte(res);
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }
    /**
     * @param  buf  byte[]类型
     * @return String对象
     * @throws
     */
    private String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * @param  hexStr String类型
     * @return byte[]对象
     * @throws
     */
    private byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1){
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}

