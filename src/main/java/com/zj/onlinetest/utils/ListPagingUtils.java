package com.zj.onlinetest.utils;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zj
 * @Date: 2019/4/18 22:07
 * @Description: list 分页工具
 */
public class ListPagingUtils {
    /**
     * 分页
     * @param lists list<?> 集合
     * @param nowPage 当前页 (页数从0开始)
     * @param pageSize 每页显示的记录条数
     * @return object
     */
        public static List<Object> getPaging(List<?> lists,Integer nowPage,Integer pageSize) {
            // 总记录条数
            int count = lists.size();
            // 分页集合
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < count; i += pageSize) {
                List<Object> temp = new ArrayList<>();
                if ((count - i) >= pageSize) {
                    for (int j = 0; j < pageSize; j++) {
                        temp.add(lists.get(i + j));
                    }
                } else {
                    for (int j = 0; j < count - i; j++) {
                        temp.add(lists.get(i + j));
                    }
                }
                list.add(temp);
            }
            // 返回当前页
            return (List<Object>) list.get(nowPage);
        }

}
