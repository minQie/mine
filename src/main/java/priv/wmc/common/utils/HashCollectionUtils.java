package priv.wmc.common.utils;

import java.util.Collection;

/**
 * @author Wang Mincong
 * @date 2020-07-07 21:34:16
 */
public final class HashCollectionUtils {

    private HashCollectionUtils() {}

    /**
     * 根据指定大小和HashMap扩容规则获取不会扩容的一次性大小
     *
     * @param list 希望存储的集合
     * @return 不会发送扩容的HashMap应该初始化的大小
     */
    public static int getAppropriateSize(Collection<?> list) {
        return getAppropriateSize(list.size());
    }

    /**
     * 根据指定大小和HashMap扩容规则获取不会扩容的一次性大小
     *
     * @param wishSize 希望存储的元素数量
     * @return 不会发送扩容的HashMap应该初始化的大小
     */
    public static int getAppropriateSize(int wishSize) {
        return Math.max((int) (wishSize/.75f) + 1, 16);
    }

}
