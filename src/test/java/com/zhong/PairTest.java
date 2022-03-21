package com.zhong;

import javafx.util.Pair;
import org.apache.lucene.util.fst.PairOutputs;
import org.junit.Test;

/**
 * @date 2022/3/16 18:11
 */
public class PairTest {



    @Test
    public void pp(){

        Integer ss = null;
        Integer ff = null;


        Pair<Integer, Integer> pair = new Pair<>(ss, ff);

        Integer key = pair.getKey();
        Integer value = pair.getValue();



        System.out.println(key + "  +  "+value);





    }


}
