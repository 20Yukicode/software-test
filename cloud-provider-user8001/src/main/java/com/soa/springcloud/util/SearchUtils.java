package com.soa.springcloud.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SearchUtils {
    public static double similarScoreCos(String text1, String text2){
        if(text1 == null || text2 == null){
            //只要有一个文本为null，规定相似度分值为0，表示完全不相等
            return 0.0;
        }else if("".equals(text1)&&"".equals(text2)) return 1.0;
        Set<Integer> ASII=new TreeSet<>();
        Map<Integer, Integer> text1Map=new HashMap<>();
        Map<Integer, Integer> text2Map=new HashMap<>();
        for(int i=0;i<text1.length();i++){
            Integer temp1=new Integer(text1.charAt(i));
            if(text1Map.get(temp1)==null) text1Map.put(temp1,1);
            else text1Map.put(temp1,text1Map.get(temp1)+1);
            ASII.add(temp1);
        }
        for(int j=0;j<text2.length();j++){
            Integer temp2=new Integer(text2.charAt(j));
            if(text2Map.get(temp2)==null) text2Map.put(temp2,1);
            else text2Map.put(temp2,text2Map.get(temp2)+1);
            ASII.add(temp2);
        }
        double xy=0.0;
        double x=0.0;
        double y=0.0;
        //计算
        for (Integer it : ASII) {
            Integer t1=text1Map.get(it)==null?0:text1Map.get(it);
            Integer t2=text2Map.get(it)==null?0:text2Map.get(it);
            xy+=t1*t2;
            x+=Math.pow(t1, 2);
            y+=Math.pow(t2, 2);
        }
        if(x==0.0||y==0.0) return 0.0;
        return xy/Math.sqrt(x*y);
    }
}