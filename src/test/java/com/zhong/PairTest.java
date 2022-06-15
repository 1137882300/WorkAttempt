package com.zhong;

import com.zhong.constants.OperatorConstants;
import com.zhong.constants.OperatorValue;
import com.zhong.constants.QueryValue;
import com.zhong.request.QueryTagListRequest;
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


        System.out.println(key + "  +  " + value);


    }

    @Test
    public void interfaceValue() {
        QueryTagListRequest request = QueryTagListRequest.builder()
                .displayName(org.apache.commons.lang3.tuple.Pair.of("name", new OperatorValue().fuzzyMatch()))
                .build();
        System.out.println(request.getDisplayName().getKey());
        System.out.println(request.getDisplayName().getRight());
    }

    @Test
    public void test() {
        QueryValue queryValue = new QueryValue();
        queryValue.setValue("valueName");
        queryValue.fuzzyMatch();
        QueryTagListRequest build = QueryTagListRequest.builder()
                .tagName(queryValue)
                .build();
        System.out.println(build);
    }


}
