/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

/**
 *
 * @author Usuario
 */
public class BusinessLogic extends Exception {

    /**
     * Creates a new instance of <code>BusinessLogic</code> without detail
     * message.
     */
    public BusinessLogic() {
    }

    /**
     * Constructs an instance of <code>BusinessLogic</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public BusinessLogic(String msg) {
        super(msg);
    }
}
