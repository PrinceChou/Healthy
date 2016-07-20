package android.microanswer.healthy;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 没有的用于代码测试的Activity
 * 由 Micro 创建于 2016/7/13.
 */

public class NoUseFroTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //遍历set--法1
//        Set<Object> data = new HashSet<>();
//        Iterator<Object> iterator1 = data.iterator();
//        while (iterator1.hasNext()) {
//            Object next = iterator1.next();
//            System.out.print(next);
//        }
//
//        for (Iterator<Object> iterator = data.iterator(); iterator.hasNext(); ) {
//            Object next =  iterator.next();
//
//        }
//
//        //遍历set--法2
//        Set<Object> sdata = new HashSet<>();
//        for (Object next : sdata) {
//            System.out.print(next);
//        }
//
//
//        //遍历map--法1
//        Map<String, Object> data2 = new HashMap<>();
//        Set<String> strings = data2.keySet();
//        Iterator<String> iterator2 = strings.iterator();
//        while (iterator2.hasNext()) {
//            String key = iterator2.next();
//            Object o = data2.get(key);
//            System.out.print(o);
//        }
//
//        //遍历map--法2
//        Map<String, Object> datas = new HashMap<>();
//        Set<String> keys = datas.keySet();
//        for (String key : keys) {
//            System.out.print(datas.get(key));
//        }
//
//        //遍历map--法3
//        Map<String, Object> data3 = new HashMap<>();
//        Set<Map.Entry<String, Object>> entries = data3.entrySet();
//        for (Map.Entry<String, Object> item : entries) {
//            System.out.print(item.getValue());
//        }
    }
}
