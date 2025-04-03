package cn.com.shadowless.baseutils.utils;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * The type List utils.
 *
 * @author sHadowLess
 */
public class ListUtils {

    /**
     * Instantiates a new List utils.
     */
    private ListUtils() {

    }

    /**
     * The interface Key extractor.
     *
     * @param <T> the type parameter
     * @param <K> the type parameter
     */
    public interface KeyExtractor<T, K> {
        /**
         * Extract key k.
         *
         * @param item the item
         * @return the k
         */
        K extractKey(T item);
    }

    /**
     * The interface Condition.
     *
     * @param <T> the type parameter
     */
    public interface Condition<T> {
        /**
         * Should remove boolean.
         *
         * @param item the item
         * @return the boolean
         */
        boolean rule(T item);
    }

    /**
     * Delete by condition.
     *
     * @param <T>       the type parameter
     * @param list      the list
     * @param condition the condition
     */
    public static <T> void deleteByCondition(List<T> list, Condition<T> condition) {
        if (list == null) {
            return;
        }
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T element = iterator.next();
            if (condition.rule(element)) {
                iterator.remove();
            }
        }
    }

    /**
     * Gets same data by variable.
     *
     * @param <T>          the type parameter
     * @param <K>          the type parameter
     * @param list1        the list 1
     * @param list2        the list 2
     * @param keyExtractor the key extractor
     * @return the same data by variable
     */
    public static <T, K> List<T> getSameDataByVariable(List<T> list1, List<T> list2, KeyExtractor<T, K> keyExtractor) {
        if (list1 == null || list2 == null) {
            return new ArrayList<>();
        }
        Set<K> keys2 = new HashSet<>();
        for (T obj : list2) {
            keys2.add(keyExtractor.extractKey(obj));
        }
        List<T> commonData = new ArrayList<>();
        for (T obj : list1) {
            K key = keyExtractor.extractKey(obj);
            if (keys2.contains(key)) {
                commonData.add(obj);
            }
        }
        return commonData;
    }

    /**
     * Gets different data by variable.
     *
     * @param <T>          the type parameter
     * @param <K>          the type parameter
     * @param list1        the list 1
     * @param list2        the list 2
     * @param keyExtractor the key extractor
     * @return the different data by variable
     */
    public static <T, K> List<T> getDifferentDataByVariable(List<T> list1, List<T> list2, KeyExtractor<T, K> keyExtractor) {
        if (list1 == null || list2 == null) {
            return new ArrayList<>();
        }
        Set<K> keys1 = new HashSet<>();
        for (T obj : list1) {
            keys1.add(keyExtractor.extractKey(obj));
        }
        Set<K> keys2 = new HashSet<>();
        for (T obj : list2) {
            keys2.add(keyExtractor.extractKey(obj));
        }
        List<T> differentData = new ArrayList<>();
        for (T obj : list1) {
            K key = keyExtractor.extractKey(obj);
            if (!keys2.contains(key)) {
                differentData.add(obj);
            }
        }
        for (T obj : list2) {
            K key = keyExtractor.extractKey(obj);
            if (!keys1.contains(key)) {
                differentData.add(obj);
            }
        }
        return differentData;
    }

    /**
     * 获取两个 List 中相同的数据
     *
     * @param <T>   the type parameter
     * @param list1 第一个列表
     * @param list2 第二个列表
     * @return 相同数据的列表 same data by hash
     */
    public static <T> List<T> getSameDataByHash(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null) {
            return new ArrayList<>();
        }
        Set<T> set1 = new HashSet<>(list1);
        Set<T> set2 = new HashSet<>(list2);
        Set<T> commonData = new HashSet<>(set1);
        commonData.retainAll(set2);
        return new ArrayList<>(commonData);
    }

    /**
     * 获取两个 List 中不同的数据
     *
     * @param <T>   the type parameter
     * @param list1 第一个列表
     * @param list2 第二个列表
     * @return 不同数据的列表 different data by hash
     */
    public static <T> List<T> getDifferentDataByHash(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null) {
            return new ArrayList<>();
        }
        Set<T> set1 = new HashSet<>(list1);
        Set<T> set2 = new HashSet<>(list2);
        Set<T> commonData = new HashSet<>(set1);
        commonData.retainAll(set2);
        Set<T> differentData = new HashSet<>();
        differentData.addAll(set1);
        differentData.addAll(set2);
        differentData.removeAll(commonData);
        return new ArrayList<>(differentData);
    }

    /**
     * Linear search object t.
     *
     * @param <T>       the type parameter
     * @param list      the list
     * @param condition the condition
     * @return the t
     */
    public static <T> T linearSearchObject(List<T> list, Condition<T> condition) {
        if (list == null) {
            return null;
        }
        int index = linearSearchIndex(list, condition);
        return index == -1 ? null : list.get(index);
    }

    /**
     * Linear search index int.
     *
     * @param <T>       the type parameter
     * @param list      the list
     * @param condition the condition
     * @return the int
     */
    public static <T> int linearSearchIndex(List<T> list, Condition<T> condition) {
        if (list == null) {
            return -1;
        }
        for (int i = 0; i < list.size(); i++) {
            if (condition.rule(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Binary search object t.
     *
     * @param <T>  the type parameter
     * @param list the list
     * @param data the data
     * @return the t
     */
    public static <T extends Comparable<T>> T binarySearchObject(List<T> list, T data) {
        if (list == null) {
            return null;
        }
        int index = binarySearchIndex(list, data, null);
        return index == -1 ? null : list.get(index);
    }

    /**
     * Binary search object t.
     *
     * @param <T>        the type parameter
     * @param list       the list
     * @param data       the data
     * @param comparator the comparator
     * @return the t
     */
    public static <T> T binarySearchObject(List<T> list, T data, Comparator<T> comparator) {
        if (list == null) {
            return null;
        }
        int index = binarySearchIndex(list, data, comparator);
        return index == -1 ? null : list.get(index);
    }

    /**
     * Binary search index int.
     *
     * @param <T>  the type parameter
     * @param list the list
     * @param data the data
     * @return the int
     */
    public static <T extends Comparable<T>> int binarySearchIndex(List<T> list, T data) {
        if (list == null) {
            return -1;
        }
        return binarySearchIndex(list, data, null);
    }

    /**
     * Binary search index int.
     *
     * @param <T>        the type parameter
     * @param list       the list
     * @param data       the data
     * @param comparator the comparator
     * @return the int
     */
    public static <T> int binarySearchIndex(List<T> list, T data, Comparator<T> comparator) {
        if (list == null) {
            return -1;
        }
        if (comparator != null) {
            Collections.sort(list, comparator);
        }
        return Collections.binarySearch(list, data, comparator);
    }

    /**
     * Deep copy list list.
     *
     * @param <T>          the type parameter
     * @param originalList the original list
     * @return the list
     * @throws NoSuchMethodException     the no such method exception
     * @throws InvocationTargetException the invocation target exception
     * @throws IllegalAccessException    the illegal access exception
     */
    public static <T> List<T> deepCopyList(List<T> originalList) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (originalList == null || originalList.size() == 0) {
            return new ArrayList<>();
        }
        List<T> copiedList = new ArrayList<>(originalList.size());
        for (T item : originalList) {
            if (item instanceof List) {
                List<?> subList = (List<?>) item;
                copiedList.add((T) deepCopyList(subList));
            } else if (item instanceof Cloneable) {
                Method cloneMethod = item.getClass().getMethod("clone");
                T clonedItem = (T) cloneMethod.invoke(item);
                copiedList.add(clonedItem);
            } else {
                copiedList.add(item);
            }
        }
        return copiedList;
    }
}
