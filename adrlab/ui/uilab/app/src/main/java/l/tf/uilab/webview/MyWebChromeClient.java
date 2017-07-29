package l.tf.uilab.webview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.*;
import l.tf.uilab.Utils;

import static l.tf.uilab.Utils.logMethodCalled;

/**
 * @author ltf
 * @since 17/7/25, 下午4:49
 */
public class MyWebChromeClient extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        logMethodCalled();
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        logMethodCalled();
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
        logMethodCalled();
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        super.onReceivedTouchIconUrl(view, url, precomposed);
        logMethodCalled();
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
        logMethodCalled();
    }

    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
        super.onShowCustomView(view, requestedOrientation, callback);
        logMethodCalled();
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        logMethodCalled();
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        logMethodCalled();
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public void onRequestFocus(WebView view) {
        super.onRequestFocus(view);
        logMethodCalled();
    }

    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
        logMethodCalled();
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        logMethodCalled();
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        logMethodCalled();
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        logMethodCalled();
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        logMethodCalled();
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
        logMethodCalled();
        super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
    }

    @Override
    public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
        logMethodCalled();
        super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        logMethodCalled();
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        logMethodCalled();
        super.onGeolocationPermissionsHidePrompt();
    }

    @Override
    public void onPermissionRequest(PermissionRequest request) {
        logMethodCalled();
        super.onPermissionRequest(request);
    }

    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        logMethodCalled();
        super.onPermissionRequestCanceled(request);
    }

    @Override
    public boolean onJsTimeout() {
        logMethodCalled();
        return super.onJsTimeout();
    }

    @Override
    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        logMethodCalled();
        super.onConsoleMessage(message, lineNumber, sourceID);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        logMethodCalled();
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        logMethodCalled();
        return super.getDefaultVideoPoster();
    }

    @Override
    public View getVideoLoadingProgressView() {
        logMethodCalled();
        return super.getVideoLoadingProgressView();
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        logMethodCalled();
        super.getVisitedHistory(callback);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        logMethodCalled();
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }
}
