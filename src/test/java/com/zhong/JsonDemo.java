package com.zhong;

import com.alibaba.fastjson.JSON;
import com.zhong.entity.Dog;
import com.zhong.entity.MultiLanguageString;
import com.zhong.entity.ObList;
import com.zhong.entity.StateEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/11/26 17:50
 */
public class JsonDemo {


    @Test
    public void te (){


        List<Dog> dogs = new ArrayList<>();
        Dog dog = Dog.builder().id(1).state(StateEnum.OPEN).build();
        dogs.add(dog);
        MultiLanguageString string = new MultiLanguageString();
        string.registerDefaultLanguage("巴林");
        ObList obList = ObList.builder().id(23).dogList(dogs).fromCountries(string).build();
        String s = JSON.toJSONString(obList);
        System.out.println(s);

    }
//    itemTransport:{}
//    "fromCountries":[{"countryId":1,"countryAbbreviation":"火星"}],"toCountries":[{"countryId":2,"countryAbbreviation":"巴林"}]


//    "fromCountries":[{"countryId":1,"countryAbbreviation":{"allLanguageString":{"in_ID":"火星"},"defaultLanguageString":"火星","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}],"toCountries":[{"countryId":2,"countryAbbreviation":{"allLanguageString":{"in_ID":"巴林"},"defaultLanguageString":"巴林","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}]
//    "fromCountries":[{"countryId":6,"countryAbbreviation":"缅甸"}],"toCountries":[{"countryId":10,"countryAbbreviation":"香港"}]


//    {            "countryAbbreviation":{"allLanguageString":{"in_ID":"巴林"},"defaultLanguageString":"巴林","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}       }
//    {            "countryAbbreviation":{"allLanguageString":{"in_ID":"缅甸"},"defaultLanguageString":"缅甸","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}       }

//    ,"fromCountries":[{"countryId":6,"countryAbbreviation":{"allLanguageString":{"in_ID":"巴林"},"defaultLanguageString":"巴林","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}]
//    ,"toCountries":[{"countryId":10,"countryAbbreviation":{"allLanguageString":{"in_ID":"缅甸"},"defaultLanguageString":"缅甸","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}]


//    "fromCountries":[{"countryId":6,"countryAbbreviation":{"allLanguageString":{"in_ID":"缅甸"},"defaultLanguageString":"缅甸","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}],"toCountries":[{"countryId":10,"countryAbbreviation":{"allLanguageString":{"in_ID":"香港"},"defaultLanguageString":"香港","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}]}
}
