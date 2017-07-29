package l.tf.uilab.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import l.tf.uilab.R;

/**
 * @author ltf
 * @since 17/7/25, 下午4:22
 */
public class WebviewActivity extends Activity implements View.OnClickListener {

    WebView mWebView;
    Button mBtnGo;
    Button mBtnRefresh;
    EditText mEdtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        mWebView = (WebView) findViewById(R.id.web_view);
        mBtnGo = (Button) findViewById(R.id.webview_go);
        mBtnRefresh = (Button) findViewById(R.id.webview_refresh);
        mEdtUrl = (EditText) findViewById(R.id.webview_url);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.loadUrl("http://10.2.45.241/");

        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });

        mBtnGo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
