package com.github.houbb.ioc.test.util;

import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.ioc.support.envrionment.ConfigurableEnvironment;
import com.github.houbb.ioc.support.envrionment.impl.DefaultEnvironment;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <p> project: ioc-ClassUtilTest </p>
 * <p> create on 2019/11/30 20:56 </p>
 *
 * @author Administrator
 * @since 0.1.10
 */
public class ClassUtilTest {

    @Test
    public void getParentClassTest() {
        List<Class> classList = ClassUtil.getAllInterfacesAndSuperClass(DefaultEnvironment.class);
        System.out.println(classList);
        System.out.println(Arrays.toString(ConfigurableEnvironment.class.getInterfaces()));
    }

}
