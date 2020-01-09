/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author 2dam
 */
public class PasswordWrongException extends Exception {

    /**
     * Creates a new instance of <code>passwordWrongException</code> without
     * detail message.
     */
    public PasswordWrongException() {
    }

    /**
     * Constructs an instance of <code>passwordWrongException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PasswordWrongException(String msg) {
        super(msg);
    }
}
