/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Usuario
 */
public class NoEsUserException extends Exception {

    /**
     * Creates a new instance of <code>NoEsUserException</code> without detail
     * message.
     */
    public NoEsUserException() {
    }

    /**
     * Constructs an instance of <code>NoEsUserException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoEsUserException(String msg) {
        super(msg);
    }
}
