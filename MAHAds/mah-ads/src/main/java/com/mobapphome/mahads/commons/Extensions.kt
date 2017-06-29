package com.mobapphome.mahandroidupdater.commons

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by settar on 6/23/17.
 */


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
    TODO("Bu metod hele ishlemir, context == null")
}


fun TextView.decorateAsLink() {
    this.movementMethod = LinkMovementMethod.getInstance()
}