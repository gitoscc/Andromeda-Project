/*
 * Copyright 2009 Google Inc.
 */

package com.google.devtools.simple.classfiles;

/**
 * Exception indicating that there are either too many local variables in a
 * method or that the code segment exceeds is maximum size.
 *
 * @author Herbert Czymontek
 */
@SuppressWarnings("serial")
public final class MethodTooBigException extends RuntimeException {

  MethodTooBigException(String msg) {
    super(msg);
  }
}