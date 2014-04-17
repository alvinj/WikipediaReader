package com.alvinalexander.wikireader.client

import scala.collection.mutable.ArrayBuffer

/**
 * Base behavior for the data store classes.
 */
trait BaseDataStore {

    val FILE_PATH_SEPARATOR = Resources.SLASH
    val USER_HOME_DIR = Resources.USER_HOME_DIR
    val DB_DIR = Resources.DB_DIR

    val records = new ArrayBuffer[String]()

}