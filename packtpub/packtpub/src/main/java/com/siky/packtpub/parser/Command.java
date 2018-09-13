package com.siky.packtpub.parser;

public interface Command<T, S> {

    S execute(T t) throws Exception;
}
