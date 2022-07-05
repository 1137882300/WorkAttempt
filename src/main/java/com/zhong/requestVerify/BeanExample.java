package com.zhong.requestVerify;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.ConstraintViolation;
import javax.validation.GroupSequence;
import javax.validation.Validation;
import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @date 2022/7/5 11:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@GroupSequenceProvider(value = BizGroupSequenceProvider.class)
public class BeanExample {
    /*
        @NotEmpty 用在集合类上面
        @NotBlank 用在String上面
        @NotNull 用在基本类型上
     */

    @NotNull(message = "num不能为空")
    @Min(value = 10, message = "不能低于10")
    @Max(value = 100, message = "不能超过100")
    private Integer num;

    @Max(value = 100, message = "size不能超过100", groups = {VerifySize.class})
    private Integer size;

    @NotBlank(message = "name不能为空", groups = {VerifyName.class})
    private String name;

    @NotBlank(message = "desc不能为空", groups = {VerifyDesc.class})
    private String desc;

    @NotEmpty(message = "list不能为空")
    private List<Long> list;

    @NotBlank(message = "联系邮箱不能为空")
    @Email(message = "邮箱格式不对")
    private String email;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String mobile;

    @Future(message = "时间必须是将来时间")
    private Date createTime;

    /**
     * DefaultGroupSequenceProvider来实现多字段联合校验
     */

    /**
     * 同一个对象要复用,比如UserDTO在更新时候要校验userId,在保存的时候不需要校验userId,在两种情况下都要校验username,那就用上groups
     */

    /**
     * 一个组可以定义为其他组的序列，使用它进行验证的时候必须符合该序列规定的顺序。在使用组序列验证的时候，如果序列前边的组验证失败，则后面的组将不再给予验证。那就用到：@GroupSequence
     */

    // 组序列
    @GroupSequence({VerifyDesc.class, VerifyName.class})
    public interface Group {
    }

    public interface VerifyName {

    }

    public interface VerifyDesc {

    }

    public interface VerifySize {

    }


    //测试
    public static void main(String[] args) {
        BeanExample beanExample = new BeanExample();
        beanExample.setNum(10);
        beanExample.setList(Lists.newArrayList(1L));
        beanExample.setEmail("sxxx");
        beanExample.setMobile("ssxxx");
        beanExample.setSize(500);
        Set<ConstraintViolation<BeanExample>> result = Validation.buildDefaultValidatorFactory().getValidator().validate(beanExample, Group.class);
        result.forEach(System.out::println);
    }

}
