package cn.com.shadowless.baseutils.utils;


import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The type Cache utils.
 */
public class CacheUtils {

    /**
     * The constant DEFAULT_MAX_SIZE.
     */
    private static final long DEFAULT_MAX_SIZE = Long.MAX_VALUE;
    /**
     * The constant DEFAULT_MAX_COUNT.
     */
    private static final int DEFAULT_MAX_COUNT = Integer.MAX_VALUE;

    /**
     * The constant context.
     */
    private static Context context;

    /**
     * The constant SEC.
     */
    public static final int SEC = 1;
    /**
     * The constant MIN.
     */
    public static final int MIN = 60;
    /**
     * The constant HOUR.
     */
    public static final int HOUR = 360;
    /**
     * The constant DAY.
     */
    public static final int DAY = 8640;

    /**
     * The constant sCacheMap.
     */
    private static final Map<String, CacheUtils> sCacheMap = new HashMap<>();
    /**
     * The M cache manager.
     */
    private final CacheManager mCacheManager;

    /**
     * The constant cachePath.
     */
    private static String cachePath = "";

    /**
     * Init.
     *
     * @param context the context
     */
    public static void init(Context context) {
        init(context, "");
    }

    /**
     * Init.
     *
     * @param context   the context
     * @param cachePath the cache path
     */
    public static void init(Context context, String cachePath) {
        if (context instanceof Application) {
            throw new RuntimeException("请传入应用级的上下文");
        }
        CacheUtils.cachePath = cachePath;
        if (TextUtils.isEmpty(CacheUtils.cachePath)) {
            CacheUtils.cachePath = context.getCacheDir().getAbsolutePath();
        }
        CacheUtils.context = context;
    }

    /**
     * 获取缓存实例
     * <p>在/data/data/com.xxx.xxx/cache/cacheUtils目录</p>
     * <p>缓存尺寸不限</p>
     * <p>缓存个数不限</p>
     *
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance() {
        return getInstance("", DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT);
    }

    /**
     * 获取缓存实例
     * <p>在/data/data/com.xxx.xxx/cache/cacheName目录</p>
     * <p>缓存尺寸不限</p>
     * <p>缓存个数不限</p>
     *
     * @param cacheName 缓存目录名
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(String cacheName) {
        return getInstance(cacheName, DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT);
    }

    /**
     * 获取缓存实例
     * <p>在/data/data/com.xxx.xxx/cache/cacheUtils目录</p>
     *
     * @param maxSize  最大缓存尺寸，单位字节
     * @param maxCount 最大缓存个数
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(long maxSize, int maxCount) {
        return getInstance("", maxSize, maxCount);
    }

    /**
     * 获取缓存实例
     * <p>在/data/data/com.xxx.xxx/cache/cacheName目录</p>
     *
     * @param cacheName 缓存目录名
     * @param maxSize   最大缓存尺寸，单位字节
     * @param maxCount  最大缓存个数
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(String cacheName, long maxSize, int maxCount) {
        if (isSpace(cacheName)) {
            cacheName = "cache";
        }
        File file = new File(cachePath, cacheName);
        return getInstance(file, maxSize, maxCount);
    }

    /**
     * 获取缓存实例
     * <p>在cacheDir目录</p>
     * <p>缓存尺寸不限</p>
     * <p>缓存个数不限</p>
     *
     * @param cacheDir 缓存目录
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(@NonNull File cacheDir) {
        return getInstance(cacheDir, DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT);
    }

    /**
     * 获取缓存实例
     * <p>在cacheDir目录</p>
     *
     * @param cacheDir 缓存目录
     * @param maxSize  最大缓存尺寸，单位字节
     * @param maxCount 最大缓存个数
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(@NonNull File cacheDir, long maxSize, int maxCount) {
        final String cacheKey = cacheDir.getAbsoluteFile() + "_" + Process.myPid();
        CacheUtils cache = sCacheMap.get(cacheKey);
        if (cache == null) {
            cache = new CacheUtils(cacheDir, maxSize, maxCount);
            sCacheMap.put(cacheKey, cache);
        }
        return cache;
    }

    /**
     * Instantiates a new Cache utils.
     *
     * @param cacheDir the cache dir
     * @param maxSize  the max size
     * @param maxCount the max count
     */
    private CacheUtils(@NonNull File cacheDir, long maxSize, int maxCount) {
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            throw new RuntimeException("can't make dirs in " + cacheDir.getAbsolutePath());
        }
        mCacheManager = new CacheManager(cacheDir, maxSize, maxCount);
    }

    ///////////////////////////////////////////////////////////////////////////
    // byte 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入字节数组
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, @NonNull byte[] value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入字节数组
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull byte[] value, int saveTime) {
        if (value.length <= 0) {
            return;
        }
        if (saveTime >= 0) {
            value = CacheHelper.newByteArrayWithTime(saveTime, value);
        }
        File file = mCacheManager.getFileBeforePut(key);
        CacheHelper.writeFileFromBytes(file, value);
        mCacheManager.updateModify(file);
        mCacheManager.put(file);

    }

    /**
     * 缓存中读取字节数组
     *
     * @param key 键
     * @return 存在且没过期返回对应值 ，否则返回{@code null}
     */
    public byte[] getBytes(@NonNull String key) {
        return getBytes(key, null);
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值 ，否则返回默认值{@code defaultValue}
     */
    public byte[] getBytes(@NonNull String key, byte[] defaultValue) {
        final File file = mCacheManager.getFileIfExists(key);
        if (file == null) {
            return defaultValue;
        }
        byte[] data = CacheHelper.readFile2Bytes(file);
        if (CacheHelper.isDue(data)) {
            mCacheManager.removeByKey(key);
            return defaultValue;
        }
        mCacheManager.updateModify(file);
        return CacheHelper.getDataWithoutDueTime(data);
    }

    ///////////////////////////////////////////////////////////////////////////
    // String 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入String
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, @NonNull String value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入String
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull String value, int saveTime) {
        put(key, CacheHelper.string2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取String
     *
     * @param key 键
     * @return 存在且没过期返回对应值 ，否则返回{@code null}
     */
    public String getString(@NonNull String key) {
        return getString(key, null);
    }

    /**
     * 缓存中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值 ，否则返回默认值{@code defaultValue}
     */
    public String getString(@NonNull String key, String defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) {
            return defaultValue;
        }
        return CacheHelper.bytes2String(bytes);
    }

    ///////////////////////////////////////////////////////////////////////////
    // JSONObject 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入JSONObject
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, @NonNull JSONObject value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入JSONObject
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull JSONObject value, int saveTime) {
        put(key, CacheHelper.jsonObject2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取JSONObject
     *
     * @param key 键
     * @return 存在且没过期返回对应值 ，否则返回{@code null}
     */
    public JSONObject getJSONObject(@NonNull String key) {
        return getJSONObject(key, null);
    }

    /**
     * 缓存中读取JSONObject
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值 ，否则返回默认值{@code defaultValue}
     */
    public JSONObject getJSONObject(@NonNull String key, JSONObject defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) {
            return defaultValue;
        }
        return CacheHelper.bytes2JSONObject(bytes);
    }


    ///////////////////////////////////////////////////////////////////////////
    // JSONArray 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入JSONArray
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, @NonNull JSONArray value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入JSONArray
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull JSONArray value, int saveTime) {
        put(key, CacheHelper.jsonArray2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取JSONArray
     *
     * @param key 键
     * @return 存在且没过期返回对应值 ，否则返回{@code null}
     */
    public JSONArray getJSONArray(@NonNull String key) {
        return getJSONArray(key, null);
    }

    /**
     * 缓存中读取JSONArray
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值 ，否则返回默认值{@code defaultValue}
     */
    public JSONArray getJSONArray(@NonNull String key, JSONArray defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) {
            return defaultValue;
        }
        return CacheHelper.bytes2JSONArray(bytes);
    }


    ///////////////////////////////////////////////////////////////////////////
    // Bitmap 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入Bitmap
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, @NonNull Bitmap value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入Bitmap
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull Bitmap value, int saveTime) {
        put(key, CacheHelper.bitmap2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取Bitmap
     *
     * @param key 键
     * @return 存在且没过期返回对应值 ，否则返回{@code null}
     */
    public Bitmap getBitmap(@NonNull String key) {
        return getBitmap(key, null);
    }

    /**
     * 缓存中读取Bitmap
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值 ，否则返回默认值{@code defaultValue}
     */
    public Bitmap getBitmap(@NonNull String key, Bitmap defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) {
            return defaultValue;
        }
        return CacheHelper.bytes2Bitmap(bytes);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Drawable 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入Drawable
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, @NonNull Drawable value) {
        put(key, CacheHelper.drawable2Bytes(value));
    }

    /**
     * 缓存中写入Drawable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull Drawable value, int saveTime) {
        put(key, CacheHelper.drawable2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取Drawable
     *
     * @param key 键
     * @return 存在且没过期返回对应值 ，否则返回{@code null}
     */
    public Drawable getDrawable(@NonNull String key) {
        return getDrawable(key, null);
    }

    /**
     * 缓存中读取Drawable
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值 ，否则返回默认值{@code defaultValue}
     */
    public Drawable getDrawable(@NonNull String key, Drawable defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) {
            return defaultValue;
        }
        return CacheHelper.bytes2Drawable(bytes);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Parcelable 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入Parcelable
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, @NonNull Parcelable value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入Parcelable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull Parcelable value, int saveTime) {
        put(key, CacheHelper.parcelable2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取Parcelable
     *
     * @param <T>     the type parameter
     * @param key     键
     * @param creator 建造器
     * @return 存在且没过期返回对应值 ，否则返回{@code null}
     */
    public <T> T getParcelable(@NonNull String key, @NonNull Parcelable.Creator<T> creator) {
        return getParcelable(key, creator, null);
    }

    /**
     * 缓存中读取Parcelable
     *
     * @param <T>          the type parameter
     * @param key          键
     * @param creator      建造器
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值 ，否则返回默认值{@code defaultValue}
     */
    public <T> T getParcelable(@NonNull String key, @NonNull Parcelable.Creator<T> creator, T defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) {
            return defaultValue;
        }
        return CacheHelper.bytes2Parcelable(bytes, creator);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Serializable 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入Serializable
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, @NonNull Serializable value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入Serializable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull Serializable value, int saveTime) {
        put(key, CacheHelper.serializable2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取Serializable
     *
     * @param key 键
     * @return 存在且没过期返回对应值 ，否则返回{@code null}
     */
    public Object getSerializable(@NonNull String key) {
        return getSerializable(key, null);
    }

    /**
     * 缓存中读取Serializable
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值 ，否则返回默认值{@code defaultValue}
     */
    public Object getSerializable(@NonNull String key, Object defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) {
            return defaultValue;
        }
        return CacheHelper.bytes2Object(getBytes(key));
    }

    /**
     * 获取缓存大小
     * <p>单位：字节</p>
     *
     * @return 缓存大小 cache size
     */
    public long getCacheSize() {
        return mCacheManager.getCacheSize();
    }

    /**
     * 获取缓存个数
     *
     * @return 缓存个数 cache count
     */
    public int getCacheCount() {
        return mCacheManager.getCacheCount();
    }

    /**
     * 根据键值移除缓存
     *
     * @param key 键
     * @return {@code true}: 移除成功<br>{@code false}: 移除失败
     */
    public boolean remove(@NonNull String key) {
        return mCacheManager.removeByKey(key);
    }

    /**
     * 清除所有缓存
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean clear() {
        return mCacheManager.clear();
    }

    /**
     * The type Cache manager.
     */
    private class CacheManager {
        /**
         * The Cache size.
         */
        private final AtomicLong cacheSize;
        /**
         * The Cache count.
         */
        private final AtomicInteger cacheCount;
        /**
         * The Size limit.
         */
        private final long sizeLimit;
        /**
         * The Count limit.
         */
        private final int countLimit;
        /**
         * The Last usage dates.
         */
        private final Map<File, Long> lastUsageDates = Collections.synchronizedMap(new HashMap<File, Long>());
        /**
         * The Cache dir.
         */
        private final File cacheDir;

        /**
         * Instantiates a new Cache manager.
         *
         * @param cacheDir   the cache dir
         * @param sizeLimit  the size limit
         * @param countLimit the count limit
         */
        private CacheManager(File cacheDir, long sizeLimit, int countLimit) {
            this.cacheDir = cacheDir;
            this.sizeLimit = sizeLimit;
            this.countLimit = countLimit;
            cacheSize = new AtomicLong();
            cacheCount = new AtomicInteger();
            calculateCacheSizeAndCacheCount();
        }

        /**
         * Calculate cache size and cache count.
         */
        private void calculateCacheSizeAndCacheCount() {
            new Thread(() -> {
                int size = 0;
                int count = 0;
                final File[] cachedFiles = cacheDir.listFiles();
                if (cachedFiles != null) {
                    for (File cachedFile : cachedFiles) {
                        size += cachedFile.length();
                        count += 1;
                        lastUsageDates.put(cachedFile, cachedFile.lastModified());
                    }
                    cacheSize.getAndAdd(size);
                    cacheCount.getAndAdd(count);
                }
            }).start();
        }

        /**
         * Gets cache size.
         *
         * @return the cache size
         */
        private long getCacheSize() {
            return cacheSize.get();
        }

        /**
         * Gets cache count.
         *
         * @return the cache count
         */
        private int getCacheCount() {
            return cacheCount.get();
        }

        /**
         * Gets file before put.
         *
         * @param key the key
         * @return the file before put
         */
        private File getFileBeforePut(String key) {
            File file = new File(cacheDir, String.valueOf(key.hashCode()));
            if (file.exists()) {
                cacheCount.addAndGet(-1);
                cacheSize.addAndGet(-file.length());
            }
            return file;
        }

        /**
         * Gets file if exists.
         *
         * @param key the key
         * @return the file if exists
         */
        private File getFileIfExists(String key) {
            File file = new File(cacheDir, String.valueOf(key.hashCode()));
            if (!file.exists()) {
                return null;
            }
            return file;
        }

        /**
         * Put.
         *
         * @param file the file
         */
        private void put(File file) {
            cacheCount.addAndGet(1);
            cacheSize.addAndGet(file.length());
            while (cacheCount.get() > countLimit || cacheSize.get() > sizeLimit) {
                cacheSize.addAndGet(-removeOldest());
                cacheCount.addAndGet(-1);
            }
        }

        /**
         * Update modify.
         *
         * @param file the file
         */
        private void updateModify(File file) {
            Long millis = System.currentTimeMillis();
            file.setLastModified(millis);
            lastUsageDates.put(file, millis);
        }

        /**
         * Remove by key boolean.
         *
         * @param key the key
         * @return the boolean
         */
        private boolean removeByKey(String key) {
            File file = getFileIfExists(key);
            if (file == null) {
                return true;
            }
            if (!file.delete()) {
                return false;
            }
            cacheSize.addAndGet(-file.length());
            cacheCount.addAndGet(-1);
            lastUsageDates.remove(file);
            return true;
        }

        /**
         * Clear boolean.
         *
         * @return the boolean
         */
        private boolean clear() {
            File[] files = cacheDir.listFiles();
            if (files == null || files.length <= 0) {
                return true;
            }
            boolean flag = true;
            for (File file : files) {
                if (!file.delete()) {
                    flag = false;
                    continue;
                }
                cacheSize.addAndGet(-file.length());
                cacheCount.addAndGet(-1);
                lastUsageDates.remove(file);
            }
            if (flag) {
                lastUsageDates.clear();
                cacheSize.set(0);
                cacheCount.set(0);
            }
            return flag;
        }

        /**
         * 移除旧的文件
         *
         * @return 移除的字节数 long
         */
        private long removeOldest() {
            if (lastUsageDates.isEmpty()) {
                return 0;
            }
            Long oldestUsage = Long.MAX_VALUE;
            File oldestFile = null;
            Set<Map.Entry<File, Long>> entries = lastUsageDates.entrySet();
            synchronized (lastUsageDates) {
                for (Map.Entry<File, Long> entry : entries) {
                    Long lastValueUsage = entry.getValue();
                    if (lastValueUsage < oldestUsage) {
                        oldestUsage = lastValueUsage;
                        oldestFile = entry.getKey();
                    }
                }
            }
            if (oldestFile == null) {
                return 0;
            }
            long fileSize = oldestFile.length();
            if (oldestFile.delete()) {
                lastUsageDates.remove(oldestFile);
                return fileSize;
            }
            return 0;
        }
    }

    /**
     * The type Cache helper.
     */
    private static class CacheHelper {

        /**
         * The Time info len.
         */
        static final int timeInfoLen = 14;

        /**
         * New byte array with time byte [ ].
         *
         * @param second the second
         * @param data   the data
         * @return the byte [ ]
         */
        private static byte[] newByteArrayWithTime(int second, byte[] data) {
            byte[] time = createDueTime(second).getBytes();
            byte[] content = new byte[time.length + data.length];
            System.arraycopy(time, 0, content, 0, time.length);
            System.arraycopy(data, 0, content, time.length, data.length);
            return content;
        }

        /**
         * 创建过期时间
         *
         * @param second 秒
         * @return _$millis$_ string
         */
        private static String createDueTime(int second) {
            return String.format(Locale.getDefault(), "_$%010d$_", System.currentTimeMillis() / 1000 + second);
        }

        /**
         * Is due boolean.
         *
         * @param data the data
         * @return the boolean
         */
        private static boolean isDue(byte[] data) {
            long millis = getDueTime(data);
            return millis != -1 && System.currentTimeMillis() > millis;
        }

        /**
         * Gets due time.
         *
         * @param data the data
         * @return the due time
         */
        private static long getDueTime(byte[] data) {
            if (hasTimeInfo(data)) {
                String millis = new String(copyOfRange(data, 2, 12));
                try {
                    return Long.parseLong(millis) * 1000;
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
            return -1;
        }

        /**
         * Get data without due time byte [ ].
         *
         * @param data the data
         * @return the byte [ ]
         */
        private static byte[] getDataWithoutDueTime(byte[] data) {
            if (hasTimeInfo(data)) {
                return copyOfRange(data, timeInfoLen, data.length);
            }
            return data;
        }

        /**
         * Copy of range byte [ ].
         *
         * @param original the original
         * @param from     the from
         * @param to       the to
         * @return the byte [ ]
         */
        private static byte[] copyOfRange(byte[] original, int from, int to) {
            int newLength = to - from;
            if (newLength < 0) {
                throw new IllegalArgumentException(from + " > " + to);
            }
            byte[] copy = new byte[newLength];
            System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
            return copy;
        }

        /**
         * Has time info boolean.
         *
         * @param data the data
         * @return the boolean
         */
        private static boolean hasTimeInfo(byte[] data) {
            return data != null
                    && data.length >= timeInfoLen
                    && data[0] == '_'
                    && data[1] == '$'
                    && data[12] == '$'
                    && data[13] == '_';
        }

        /**
         * Write file from bytes.
         *
         * @param file  the file
         * @param bytes the bytes
         */
        private static void writeFileFromBytes(File file, byte[] bytes) {
            FileChannel fc = null;
            try {
                fc = new FileOutputStream(file, false).getChannel();
                fc.write(ByteBuffer.wrap(bytes));
                fc.force(true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fc != null) {
                    try {
                        fc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * Read file 2 bytes byte [ ].
         *
         * @param file the file
         * @return the byte [ ]
         */
        private static byte[] readFile2Bytes(File file) {
            FileChannel fc = null;
            try {
                fc = new RandomAccessFile(file, "r").getChannel();
                int size = (int) fc.size();
                MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
                byte[] data = new byte[size];
                mbb.get(data, 0, size);
                return data;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (fc != null) {
                    try {
                        fc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * String 2 bytes byte [ ].
         *
         * @param string the string
         * @return the byte [ ]
         */
        private static byte[] string2Bytes(String string) {
            if (string == null) {
                return null;
            }
            return string.getBytes();
        }

        /**
         * Bytes 2 string string.
         *
         * @param bytes the bytes
         * @return the string
         */
        private static String bytes2String(byte[] bytes) {
            if (bytes == null) {
                return null;
            }
            return new String(bytes);
        }

        /**
         * Json object 2 bytes byte [ ].
         *
         * @param jsonObject the json object
         * @return the byte [ ]
         */
        private static byte[] jsonObject2Bytes(JSONObject jsonObject) {
            if (jsonObject == null) {
                return null;
            }
            return jsonObject.toString().getBytes();
        }

        /**
         * Bytes 2 json object json object.
         *
         * @param bytes the bytes
         * @return the json object
         */
        private static JSONObject bytes2JSONObject(byte[] bytes) {
            if (bytes == null) {
                return null;
            }
            try {
                return new JSONObject(new String(bytes));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Json array 2 bytes byte [ ].
         *
         * @param jsonArray the json array
         * @return the byte [ ]
         */
        private static byte[] jsonArray2Bytes(JSONArray jsonArray) {
            if (jsonArray == null) {
                return null;
            }
            return jsonArray.toString().getBytes();
        }

        /**
         * Bytes 2 json array json array.
         *
         * @param bytes the bytes
         * @return the json array
         */
        private static JSONArray bytes2JSONArray(byte[] bytes) {
            if (bytes == null) {
                return null;
            }
            try {
                return new JSONArray(new String(bytes));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Parcelable 2 bytes byte [ ].
         *
         * @param parcelable the parcelable
         * @return the byte [ ]
         */
        private static byte[] parcelable2Bytes(Parcelable parcelable) {
            if (parcelable == null) {
                return null;
            }
            Parcel parcel = Parcel.obtain();
            parcelable.writeToParcel(parcel, 0);
            byte[] bytes = parcel.marshall();
            parcel.recycle();
            return bytes;
        }

        /**
         * Bytes 2 parcelable t.
         *
         * @param <T>     the type parameter
         * @param bytes   the bytes
         * @param creator the creator
         * @return the t
         */
        private static <T> T bytes2Parcelable(byte[] bytes, Parcelable.Creator<T> creator) {
            if (bytes == null) {
                return null;
            }
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0);
            T result = creator.createFromParcel(parcel);
            parcel.recycle();
            return result;
        }

        /**
         * Serializable 2 bytes byte [ ].
         *
         * @param serializable the serializable
         * @return the byte [ ]
         */
        private static byte[] serializable2Bytes(Serializable serializable) {
            if (serializable == null) {
                return null;
            }
            ByteArrayOutputStream baos;
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos = new ByteArrayOutputStream());
                oos.writeObject(serializable);
                return baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * Bytes 2 object object.
         *
         * @param bytes the bytes
         * @return the object
         */
        private static Object bytes2Object(byte[] bytes) {
            if (bytes == null) {
                return null;
            }
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                return ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * Bitmap 2 bytes byte [ ].
         *
         * @param bitmap the bitmap
         * @return the byte [ ]
         */
        private static byte[] bitmap2Bytes(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }

        /**
         * Bytes 2 bitmap bitmap.
         *
         * @param bytes the bytes
         * @return the bitmap
         */
        private static Bitmap bytes2Bitmap(byte[] bytes) {
            return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        /**
         * Drawable 2 bytes byte [ ].
         *
         * @param drawable the drawable
         * @return the byte [ ]
         */
        private static byte[] drawable2Bytes(Drawable drawable) {
            return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable));
        }

        /**
         * Bytes 2 drawable drawable.
         *
         * @param bytes the bytes
         * @return the drawable
         */
        private static Drawable bytes2Drawable(byte[] bytes) {
            return bytes == null ? null : Bitmap2Drawable(bytes2Bitmap(bytes));
        }

        /**
         * Drawable 2 bitmap bitmap.
         *
         * @param drawable the drawable
         * @return the bitmap
         */
        private static Bitmap drawable2Bitmap(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable BitmapDrawable = (BitmapDrawable) drawable;
                if (BitmapDrawable.getBitmap() != null) {
                    return BitmapDrawable.getBitmap();
                }
            }
            Bitmap bitmap;
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        /**
         * Bitmap 2 drawable drawable.
         *
         * @param Bitmap the bitmap
         * @return the drawable
         */
        private static Drawable Bitmap2Drawable(Bitmap Bitmap) {
            try {
                return Bitmap == null ? null : new BitmapDrawable(context.getResources(), Bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Is space boolean.
     *
     * @param s the s
     * @return the boolean
     */
    private static boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
