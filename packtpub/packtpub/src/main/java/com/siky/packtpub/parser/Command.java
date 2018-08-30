package com.siky.packtpub.parser;

public interface Command<T, E> {

    E execute(T t) throws Exception;
}
