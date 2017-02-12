package com.kit.gui2.controllers

import com.kit.Application
import com.kit.core.Session
import com.kit.core.model.Property
import com.kit.gui2.styles.KitStyle
import com.kit.gui2.views.LoadingView
import com.kit.gui2.views.LoginView
import com.kit.http.CreateTokenRequest
import com.kit.http.UserAccount
import com.kit.http.UserToken
import com.kit.util.HttpUtil
import com.kit.util.JacksonUtil
import javafx.application.Platform
import org.apache.http.HttpStatus
import org.apache.http.client.fluent.Executor
import org.apache.http.client.fluent.Request
import org.apache.http.entity.ContentType
import org.apache.http.util.EntityUtils
import org.apache.log4j.Logger
import tornadofx.Controller
import tornadofx.FX
import tornadofx.addClass
import tornadofx.removeClass
import java.awt.Desktop
import java.net.URL
import java.util.concurrent.Executors

/**
 * Created by Matt on 11/02/2017.
 */

private const val API_URL = "https://api.07kit.com/user"

class LoginController: Controller() {

    private val view: LoginView by inject()

    private val logger = Logger.getLogger(LoginController::class.java)

    fun start() {
        if (!Session.get().apiToken.isNullOrBlank()) {
            view.titlePane.text = "Logging in..."
            val executor = Executors.newSingleThreadExecutor()
            executor.execute {
                logger.info("Existing API token found - trying to retrieve account info...")
                if (loadAccount(Session.get().apiToken, true, Session.get().email.value)) {
                    logger.info("Logged in with pre-existing key.")
                    return@execute
                }
                Platform.runLater { view.setDisabled(true) }
            }
            return
        }
        view.setDisabled(false)
    }

    fun login(email: String, password: String, rememberMe: Boolean) {
        try {
            val executor = Executors.newSingleThreadExecutor()
            executor.execute {
                val response = Executor.newInstance(HttpUtil.getClient()).execute(Request.Post(API_URL + "/token")
                        .bodyString(JacksonUtil.serialize(CreateTokenRequest(email, password)), ContentType.APPLICATION_JSON)).returnResponse()
                if (response.statusLine.statusCode == HttpStatus.SC_OK) {
                    val userToken = JacksonUtil.deserialize(EntityUtils.toString(response.entity), UserToken::class.java)
                    if (loadAccount(userToken.uuid, rememberMe, email)) {
                        logger.info("Logged in.")
                        return@execute
                    }
                }
                logger.error("Invalid login, response: [" + response.toString() + "]")
                failLogin()
            }
        } catch (e: Exception) {
            logger.error("Error", e)
            failLogin()
        }
    }

    fun createAccount() {
        try {
            Desktop.getDesktop().browse(URL("https://07kit.com").toURI())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun loadAccount(uuid: String, rememberMe: Boolean, email: String): Boolean {
        val getAccountResponse = Executor.newInstance(HttpUtil.getClient()).execute(Request.Get(API_URL)
                .addHeader("Authorization", "Bearer " + uuid)).returnResponse()

        if (getAccountResponse.statusLine.statusCode != HttpStatus.SC_OK) return false

        val userAccount = JacksonUtil.deserialize(EntityUtils.toString(getAccountResponse.entity), UserAccount::class.java)
        if (userAccount == null || userAccount.status != UserAccount.Status.ACTIVE || userAccount.type == null) return false

        Session.get().userAccount = userAccount
        Session.get().apiToken = uuid

        var emailProperty = Session.get().email
        var apiKeyProperty = Session.get().apiKey
        if (rememberMe) {
            if (emailProperty == null)
                emailProperty = Property(Session.EMAIL_PROPERTY_KEY, email)
            else
                emailProperty.value = email
            emailProperty.save()

            if (apiKeyProperty == null)
                apiKeyProperty = Property(Session.API_KEY_PROPERTY_KEY, uuid)
            else
                apiKeyProperty.value = uuid
            apiKeyProperty.save()
        } else {
            if (emailProperty != null)
                emailProperty.remove()
            if (apiKeyProperty != null)
                apiKeyProperty.remove()
        }
        Session.get().onAuthenticated()
        Application.FRAME.setFXContent(FX.find(LoadingView::class.java).root)
        Application.FRAME.loadGame()
        return true
    }

    private fun failLogin() {
        Platform.runLater {
            view.titlePane.addClass(KitStyle.red)
            view.titlePane.text = "Login failed!"
        }
    }

}