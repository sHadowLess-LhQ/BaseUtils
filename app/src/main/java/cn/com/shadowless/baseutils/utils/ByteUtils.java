package cn.com.shadowless.baseutils.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字节工具类
 *
 * @author sHadowLess
 */
public class ByteUtils {

    /**
     * 私有构造函数，不允许外部实例化
     */
    private ByteUtils() {
    }

    /**
     * 字节数组转十六进制字符串（如 [0x1A, 0x2B] → "1A2B"）
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转字节数组（如 "1A2B" → [0x1A, 0x2B]）
     *
     * @param hex 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * 字节数组转 int（大端模式）
     *
     * @param bytes 字节数组
     * @return int
     */
    public static int bytesToInt(byte[] bytes) {
        return (bytes[0] & 0xFF) << 24 |
                (bytes[1] & 0xFF) << 16 |
                (bytes[2] & 0xFF) << 8 |
                (bytes[3] & 0xFF);
    }

    /**
     * 小端模式：低位字节在前，高位字节在后
     * 示例：字节数组 [0x78, 0x56, 0x34, 0x12] → int 0x12345678
     *
     * @param bytes 字节数组
     * @return int
     */
    public static int bytesToIntLittleEndian(byte[] bytes) {
        return (bytes[3] & 0xFF) << 24 |
                (bytes[2] & 0xFF) << 16 |
                (bytes[1] & 0xFF) << 8 |
                (bytes[0] & 0xFF);
    }

    /**
     * int 转字节数组（大端模式）
     *
     * @param value int
     * @return 字节数组
     */
    public static byte[] intToBytes(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }

    /**
     * 小端模式：低位字节在前，高位字节在后
     * 示例：int 0x12345678 → 字节数组 [0x78, 0x56, 0x34, 0x12]
     *
     * @param value int
     * @return 字节数组
     */
    public static byte[] intToBytesLittleEndian(int value) {
        return new byte[]{
                (byte) value,
                (byte) (value >> 8),
                (byte) (value >> 16),
                (byte) (value >> 24)
        };
    }

    /**
     * 字节数组转 float（需结合 int 转换，大端模式）
     *
     * @param bytes 字节数组
     * @return float
     */
    public static float bytesToFloat(byte[] bytes) {
        return Float.intBitsToFloat(bytesToInt(bytes));
    }

    /**
     * 字节数组转 float（需结合 int 转换，小端模式）
     *
     * @param bytes 字节数组
     * @return float
     */
    public static float bytesToFloatLittleEndian(byte[] bytes) {
        return Float.intBitsToFloat(bytesToIntLittleEndian(bytes));
    }

    /**
     * 计算 CRC16-MODBUS 校验码
     *
     * @param data 数据
     * @return 校验码
     */
    public static byte[] crc16Modbus(byte[] data) {
        int crc = 0xFFFF;
        for (byte b : data) {
            crc ^= (b & 0xFF);
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x0001) != 0) {
                    crc = (crc >> 1) ^ 0xA001;
                } else {
                    crc >>= 1;
                }
            }
        }
        return new byte[]{(byte) crc, (byte) (crc >> 8)};
    }

    /**
     * 计算异或校验码（可指定起始位置）
     *
     * @param data  数据数组
     * @param start 起始索引（从0开始）
     * @return 校验码 byte
     */
    public static byte xorCheck(byte[] data, int start) {
        return xorCheck(data, start, data.length);
    }

    /**
     * 计算异或校验码（指定起始位置和校验长度）
     *
     * @param data  数据数组
     * @param start 起始索引
     * @param len   校验长度（若超过剩余长度则取实际长度）
     * @return 校验码 byte
     */
    public static byte xorCheck(byte[] data, int start, int len) {
        if (data == null || start < 0 || start >= data.length || len <= 0) {
            return 0;
        }
        int end = Math.min(start + len, data.length);
        byte xor = data[start];
        for (int i = start + 1; i < end; i++) {
            xor ^= data[i];
        }
        return xor;
    }

    /**
     * 字节数组转 UTF-8 字符串（处理文本协议）
     *
     * @param bytes   字节数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String bytesToUtf8(byte[] bytes, Charset charset) {
        return new String(bytes, charset);
    }

    /**
     * GBK 字符串转字节数组（兼容中文设备）
     *
     * @param text        字符串
     * @param charsetName 字符集名称
     * @return 字节数组
     */
    public static byte[] charsetToBytes(String text, String charsetName) {
        try {
            return text.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            return new byte[0];
        }
    }
}
