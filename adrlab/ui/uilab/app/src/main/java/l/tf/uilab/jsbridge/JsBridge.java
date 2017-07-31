package l.tf.uilab.jsbridge;

import android.webkit.WebView;

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;

/**
 * @author ltf
 * @since 17/7/29, 下午4:46
 */
public class JsBridge {
    private WeakReference<WebView> mWebView;
    //private

    public JsBridge(WeakReference<WebView> mWebView, Object... modules) {
        this.mWebView = mWebView;
    }
}
