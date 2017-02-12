package com.kit.gui2.views


import com.kit.core.Session
import com.kit.gui2.controllers.LoginController
import com.kit.gui2.styles.KitStyle
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.control.TitledPane
import javafx.scene.input.KeyCode
import tornadofx.*

/**
 * Created by Matt on 11/02/2017.
 */
class LoginView : View("Login View") {

    override val root = borderpane()

    val controller: LoginController by inject()

    private val emailField: TextField = textfield {
        promptText = "Email"
        text = if (Session.get().email == null) "" else Session.get().email.value
        isDisable = true
        setOnKeyPressed {
            if (it.code != KeyCode.ENTER) return@setOnKeyPressed

            if (passwordField.text.isBlank()) passwordField.requestFocus()
            else tryLogin()
        }
    }

    private val passwordField = passwordfield {
        promptText = "Password"
        isDisable = true
        setOnKeyPressed {
            if (it.code != KeyCode.ENTER) return@setOnKeyPressed

            if (emailField.text.isBlank()) emailField.requestFocus()
            else tryLogin()
        }
    }

    private val rememberMeCheckBox = checkbox {
        text = "Remember Me"
        isSelected = Session.get().apiKey != null
        isDisable = true
    }

    private val loginButton = button {
        addClass(KitStyle.blue)
        text = "Login"
        isDisable = true
        setOnAction {
            if (emailField.text.isNotBlank() && passwordField.text.isNotBlank()) tryLogin()
        }
    }

    private val createAccountButton = button {
        text = "Create Account"
        isDisable = true
        setOnAction {
            controller.createAccount()
        }
    }

    val titlePane = titledpane {
        text = "Login"
        alignment = Pos.CENTER
        isCollapsible = false
        maxWidth = 300.0
    }

    init {
        with(titlePane) {
            vbox {
                alignment = Pos.CENTER_RIGHT
                spacing = 2.0
                this += emailField
                this += passwordField
                this += rememberMeCheckBox
                hbox {
                    alignment = Pos.CENTER_RIGHT
                    spacing = 2.0
                    this += createAccountButton
                    this += loginButton
                }
            }
        }
        with(root) {
            addClass(KitStyle.wrapperLight)
            center {
                this += titlePane
            }
        }
        if (Session.get().apiKey != null)
            passwordField.text = "pre-entered"
    }

    fun setDisabled(disabled: Boolean) {
        Platform.runLater {
            emailField.isDisable = disabled
            passwordField.isDisable = disabled
            rememberMeCheckBox.isDisable = disabled
            loginButton.isDisable = disabled
            createAccountButton.isDisable = disabled
        }
    }

    private fun tryLogin() {
        setDisabled(true)
        titlePane.text = "Logging in..."
        controller.login(emailField.text, passwordField.text, rememberMeCheckBox.isSelected)
        setDisabled(false)
    }
}
