package com.kit.gui2.views

import com.kit.gui2.styles.KitStyle
import tornadofx.*

/**
 * Created by Matt on 11/02/2017.
 */
class LoadingView : View("Loading View") {

    override val root = borderpane()

    init {
        with(root) {
            addClass(KitStyle.wrapperLight)
            center {
                progressbar {
                    addClass(KitStyle.blue)
                    maxWidth = 300.0
                }
            }
        }
    }
}
