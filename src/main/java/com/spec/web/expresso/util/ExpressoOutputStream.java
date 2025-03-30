package com.spec.web.expresso.util;

import java.io.IOException;
import java.io.OutputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * Wraps java output streams hiding close and flush method.
 */
@Slf4j
public class ExpressoOutputStream extends OutputStream {

    OutputStream outputStream;

    /**
     * Constructor wraps java OutputStream.
     * 
     * @param outputStream Java output stream.
     */
    public ExpressoOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Wrapper over output stream works as same as Java output stream.
     * 
     * @param b Wrapper over output stream works as same as Java output stream.
     * 
     */
    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);

    }

    /**
     * Hides the close method.
     */
    @Override
    public void close() throws IOException {
        log.warn("close() Method on Response -> outputStream is blocked");
    }

    /**
     * Hides the flush method.
     */
    @Override
    public void flush() throws IOException {
        log.warn("flush() Method on Response -> outputStream is blocked");
    }

    /**
     * Wrapper over output stream works as same as Java output stream.
     * 
     * @param b Wrapper over output stream works as same as Java output stream.
     */
    @Override
    public void write(byte[] b) throws IOException {
        outputStream.write(b);
    }

    /**
     * Wrapper over output stream works as same as Java output stream.
     * 
     * @param b   Wrapper over output stream works as same as Java output stream.
     * @param off Wrapper over output stream works as same as Java output stream.
     * @param len Wrapper over output stream works as same as Java output stream.
     * 
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        outputStream.write(b, off, len);
    }

    /**
     * 
     * Wrapper over output stream works as same as Java output stream.
     * 
     * @param obj Wrapper over output stream works as same as Java output stream.
     *
     */
    @Override
    public boolean equals(Object obj) {
        return outputStream.equals(obj);
    }

    /**
     * 
     * Wrapper over output stream works as same as Java output stream.
     *
     */
    @Override
    public int hashCode() {
        return outputStream.hashCode();
    }

    /**
     * 
     * Wrapper over output stream works as same as Java output stream.
     * 
     */
    @Override
    public String toString() {
        return outputStream.toString();
    }

    /**
     * Closes the output stream. The method sould not be called by the user.
     */
    public void _closeFlush() {
        try {
            this.outputStream.flush();
            this.outputStream.close();
        } catch (Exception e) {
            log.error("Error while closing output stream {}", e);
        }
    }

}
