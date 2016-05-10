package browse.by.voice.example;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import browse.by.voice.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class FragmentDummy3 extends Fragment {

    private WebView AboutUsContent;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dummyactivity3, container, false);
        AboutUsContent = (WebView)v.findViewById(R.id.aboutUsContent);
        String customHtml = "<html>";
        customHtml =  customHtml + "<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\" />";
        customHtml = customHtml +
                "<html><body><div><br><img src='bbv_128.png'><p id=\"featureText\">While many people try to teach their parents and grandparents how to surf the web, most find it challenging to acclimatize them to the poorly placed computer components. Even after they take cognizance of the functional requirements of the components, it is a tedious task for them to type a sentence on a keyboard that is not alphabetically arranged or to drag the mouse pointer from one corner of the screen to the other to simply click a button. The frustration of the impatient makes you wonder if the QWERTY keyboard and the harmless mouse are amongst the worst User Experience designs ever. 'Browse by Voice' is an end-to-end speech based browsing application that captures user commands from your computer based audio input or your android device. My plan is to extend this application to other foreign languages as well. I'd appreciate support and suggestions from people using this application and contribute towards innovation of perceptual computing.</p><p id=\"featureText\">If you'd like to get in touch with me, send an email to : <br>browsebyvoice@gmail.com<br>Regards, Developer.</p></div></body></html>";
        AboutUsContent.loadDataWithBaseURL("file:///android_asset/", customHtml, "text/html", "UTF-8", null);
        //AboutUsContent.loadData(customHtml,"text/html","UTF-8");
        return v;

    }
}
