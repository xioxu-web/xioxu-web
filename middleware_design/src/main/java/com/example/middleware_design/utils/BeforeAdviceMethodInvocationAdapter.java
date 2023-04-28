package com.example.middleware_design.utils;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.AdviceSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public class BeforeAdviceMethodInvocationAdapter implements MethodInvocation {

    private Object _object;
    private Method _method;
    private Object[] _arguments;

    public static BeforeAdviceMethodInvocationAdapter createFrom(JoinPoint aJoinPoint) {
        if (aJoinPoint.getSignature() instanceof MethodSignature) {
            return new BeforeAdviceMethodInvocationAdapter(aJoinPoint.getThis(),
                    ((MethodSignature) aJoinPoint.getSignature()).getMethod(),
                    aJoinPoint.getArgs());

        } else if (aJoinPoint.getSignature() instanceof AdviceSignature) {
            return new BeforeAdviceMethodInvocationAdapter(aJoinPoint.getThis(),
                    ((AdviceSignature) aJoinPoint.getSignature()).getAdvice(),
                    aJoinPoint.getArgs());

        } else {
            throw new IllegalArgumentException("The joint point signature is invalid: expected a MethodSignature or an AdviceSignature but was " + aJoinPoint.getSignature());
        }
    }

    public BeforeAdviceMethodInvocationAdapter(Object anObject, Method aMethod, Object[] someArguments) {
        _object = anObject;
        _method = aMethod;
        _arguments = someArguments;
    }

    @Override
    public Object[] getArguments() {
        return _arguments;
    }

    @Override
    public Method getMethod() {
        return _method;
    }

    @Override
    public Object proceed() throws Throwable {
        return null;
    }

    @Override
    public Object getThis() {
        return _object;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return null;
    }
}
