package com.mobapphome.mahads.mahfragments

/**
 * Created by settar on 11/7/16.
 */

class MAHFragmentExeption : Exception {
    constructor() : super() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(cause: Throwable) : super(cause) {}
}
