package com.cyrillrx.monitor.provider

/**
 * @author Cyril Leroux
 *          Created on 06/07/2019.
 */
abstract class StatProvider {

    abstract fun fetchData(): Int
}