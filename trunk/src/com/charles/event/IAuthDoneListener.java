/**
 * 
 */
package com.charles.event;

/**
 * @author charles
 *
 */
public interface IAuthDoneListener {

	/**
	 * @param type
	 * @param result
	 */
	void onAuthDone(int type, Object result);
}