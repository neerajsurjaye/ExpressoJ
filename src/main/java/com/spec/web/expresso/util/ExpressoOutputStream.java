package com.spec.web.expresso.util;

import java.io.IOException;
import java.io.OutputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExpressoOutputStream extends OutputStream {

    OutputStream outputStream;

    public ExpressoOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);

    }

    @Override
    public void close() throws IOException {
        log.warn("close() Method on Response -> outputStream is blocked");
    }

    @Override
    public void flush() throws IOException {
        log.warn("flush() Method on Response -> outputStream is blocked");
    }

    @Override
    public void write(byte[] b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        outputStream.write(b, off, len);
    }

    @Override
    public boolean equals(Object obj) {
        return outputStream.equals(obj);
    }

    @Override
    public int hashCode() {
        return outputStream.hashCode();
    }

    @Override
    public String toString() {
        return outputStream.toString();
    }

}
