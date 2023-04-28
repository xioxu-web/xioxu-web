package com.example.doublebuffer.util;

import com.example.business.entity.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.TreeMap;

/**
 * @author xiaoxu123
 * @deprecated  表达式解析器解析参数
 */
public class ElParser {

    public static String parse(String elString, TreeMap<String,Object> map){
        elString=String.format("#{%s}",elString);
        //创建表达式解析器
        SpelExpressionParser parser = new SpelExpressionParser();
        //通过evaluationContext.setVariable可以在上下文中设定变量。
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

        map.entrySet().stream().forEach((entry)->{
            evaluationContext.setVariable(entry.getKey(),entry.getValue());
        });

        //解析表达式
        Expression expression = parser.parseExpression( elString, new TemplateParserContext() );

        //使用Expression.getValue()获取表达式的值，这里传入了Evaluation上下文
        String value = expression.getValue( evaluationContext, String.class );

        return value;

    }

    public static void main(String[] args) {
        String elString="#order.money";
        String elString2="#user";
        String elString3="#p0"; //暂不支持

        TreeMap<String,Object> map=new TreeMap<>();
        Order order = new Order();
        order.setId(111L);
        order.setMoney(123D);
        map.put("order",order);
        map.put("user","xiaoxu");

        String val = parse(elString, map);
        String val2 = parse(elString2, map);
        String val3 = parse(elString3, map);

        System.out.println(val);
        System.out.println(val2);
        System.out.println(val3);
    }
}
