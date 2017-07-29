package l.tf.uilab.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.webkit.*;

import static l.tf.uilab.Utils.logMethodCalled;

/**
 * @author ltf
 * @since 17/7/25, 下午4:48
 */
public class MyWebViewClient extends WebViewClient {

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        logMethodCalled();
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        logMethodCalled();
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        logMethodCalled();
        super.onLoadResource(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        logMethodCalled();
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        logMethodCalled();
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        logMethodCalled();
        super.onTooManyRedirects(view, cancelMsg, continueMsg);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        logMethodCalled();
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        logMethodCalled();
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        logMethodCalled();
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        logMethodCalled();
        super.onReceivedSslError(view, handler, error);
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        logMethodCalled();
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        logMethodCalled();
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        logMethodCalled();
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        logMethodCalled();
        super.onUnhandledKeyEvent(view, event);
    }

    @Override
    public void onUnhandledInputEvent(WebView view, InputEvent event) {
        logMethodCalled();
        super.onUnhandledInputEvent(view, event);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        logMethodCalled();
        super.onScaleChanged(view, oldScale, newScale);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        logMethodCalled();
        super.onReceivedLoginRequest(view, realm, account, args);
    }
}
