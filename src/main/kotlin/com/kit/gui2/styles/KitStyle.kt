package com.kit.gui2.styles

import javafx.scene.paint.Color
import tornadofx.*

/**
 * Created by Matt on 12/02/2017.
 */

class KitStyle : Stylesheet() {

    companion object {

        val background0 = c("#282828")
        val background1 = c("#3c3836")
        val foreground0 = c("#fbf1c7")
        val foreground1 = c("#ebdbb2")
        val redLight = c("#fb4934")
        val redDark = c("#cc241d")
        val greenLight = c("#b8bb26")
        val greenDark = c("#98971a")
        val yellowLight = c("#fabd2f")
        val yellowDark = c("#d79921")
        val blueLight = c("#83a598")
        val blueDark = c("#458588")
        val purpleLight = c("#d3869b")
        val purpleDark = c("#b16286")
        val aquaLight = c("#8ec07c")
        val aquaDark = c("#689d6a")
        val orangeLight = c("#fe8019")
        val orangeDark = c("#d65d0e")
        val grayLight = c("#a89984")
        val grayDark = c("#928374")

        val wrapperLight by cssclass()
        val wrapperDark by cssclass()
        val red by cssclass()
        val green by cssclass()
        val yellow by cssclass()
        val blue by cssclass()
        val purple by cssclass()
        val aqua by cssclass()
        val orange by cssclass()

        val large by cssclass()
        val medium by cssclass()
        val small by cssclass()
    }

    init {
        root {
            backgroundColor += background0
        }
        large {
            minHeight = 46.px
            maxHeight = 46.px
            fontSize = 18.px
        }
        medium {
            minHeight = 30.px
            maxHeight = 30.px
            fontSize = 14.px
        }
        large {
            minHeight = 22.px
            maxHeight = 22.px
            fontSize = 10.px
        }
        wrapperLight {
            backgroundColor += background1
        }
        wrapperDark {
            backgroundColor += background0
        }
        label {
            textFill = foreground0
            fontFamily = "Roboto Mono"
        }
        button {
            textFill = foreground0
            fontFamily = "Roboto Mono"
            backgroundRadius += box(0.px)
            backgroundColor += grayDark
            and(hover) {
                backgroundColor += grayLight
            }
            and(red) {
                backgroundColor += redDark
                and(hover) {
                    backgroundColor += redLight
                }
            }
            and(green) {
                backgroundColor += greenDark
                and(hover) {
                    backgroundColor += greenLight
                }
            }
            and(yellow) {
                backgroundColor += yellowDark
                and(hover) {
                    backgroundColor += yellowLight
                }
            }
            and(blue) {
                backgroundColor += blueDark
                and(hover) {
                    backgroundColor += blueLight
                }
            }
            and(purple) {
                backgroundColor += purpleDark
                and(hover) {
                    backgroundColor += purpleLight
                }
            }
            and(aqua) {
                backgroundColor += aquaDark
                and(hover) {
                    backgroundColor += aquaLight
                }
            }
            and(orange) {
                backgroundColor += orangeDark
                and(hover) {
                    backgroundColor += orangeLight
                }
            }
        }
        progressBar {
            track {
                prefHeight = 10.px
                backgroundRadius += box(0.px)
                backgroundColor += foreground0
            }
            bar {
                backgroundInsets += box(0.px)
                backgroundRadius += box(0.px)
                backgroundColor += grayDark
            }
            and(red) {
                bar {
                    backgroundColor += redDark
                }
            }
            and(green) {
                bar {
                    backgroundColor += greenDark
                }
            }
            and(yellow) {
                bar {
                    backgroundColor += yellowDark
                }
            }
            and(blue) {
                bar {
                    backgroundColor += blueDark
                }
            }
            and(purple) {
                bar {
                    backgroundColor += purpleDark
                }
            }
            and(aqua) {
                bar {
                    backgroundColor += aquaDark
                }
            }
            and(orange) {
                bar {
                    backgroundColor += orangeDark
                }
            }
        }
        textField {
            prefHeight = 30.px
            backgroundColor += foreground0
        }
        s(textField, textArea) {
            borderColor += box(foreground1)
            textFill = background0
            promptTextFill = background1
            fontFamily = "Roboto Mono"
            highlightFill = background1
            highlightTextFill = foreground0
            focused {
                borderColor += box(foreground0)
            }
        }
        titledPane {
            fontFamily = "Roboto Mono"
            textFill = foreground0
            title {
                backgroundColor += grayDark
                backgroundRadius += box(0.px)
                padding = box(11.px)
            }
            content {
                backgroundColor += background1
                borderWidth += box(1.px)
                borderColor += box(grayDark)
            }
            and(red) {
                title {
                    backgroundColor += redDark
                }
                content {
                    borderColor += box(redDark)
                }
            }
            and(green) {
                title {
                    backgroundColor += greenDark
                }
                content {
                    borderColor += box(greenDark)
                }
            }
            and(yellow) {
                title {
                    backgroundColor += yellowDark
                }
                content {
                    borderColor += box(yellowDark)
                }
            }
            and(blue) {
                title {
                    backgroundColor += blueDark
                }
                content {
                    borderColor += box(blueDark)
                }
            }
            and(purple) {
                title {
                    backgroundColor += purpleDark
                }
                content {
                    borderColor += box(purpleDark)
                }
            }
            and(aqua) {
                title {
                    backgroundColor += aquaDark
                }
                content {
                    borderColor += box(aquaDark)
                }
            }
            and(orange) {
                title {
                    backgroundColor += orangeDark
                }
                content {
                    borderColor += box(orangeDark)
                }
            }
        }
        checkBox {
            textFill = foreground0
            labelPadding = box(0.px, 0.px, 0.px, 5.px)
            box {
                backgroundColor += foreground0
                backgroundRadius += box(0.px)
                borderColor += box(foreground1)
                borderWidth += box(1.px)
                mark {
                    shape = "M927.936 272.992l-68.288-68.288c-12.608-12.576-32.96-12.576-45.536 0l-409.44 409.44-194.752-196.16c-12.576-12.576-32.928-12.576-45.536 0l-68.288 68.288c-12.576 12.608-12.576 32.96 0 45.536l285.568 287.488c12.576 12.576 32.96 12.576 45.536 0l500.736-500.768c12.576-12.544 12.576-32.96 0-45.536z"
                }
            }
        }
    }

}