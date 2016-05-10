package browse.by.voice.example;

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

public class FragmentDummy4 extends Fragment {

    private WebView howToUseContent;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dummyactivity4, container, false);
        howToUseContent = (WebView)v.findViewById(R.id.howToUseContent);
        String customHtml = "<html>";

        customHtml =  customHtml + "<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\" />";
        customHtml =  customHtml + "<link rel=\"stylesheet\" type=\"text/css\" href=\"font-awesome.min.css\" />";
        //customHtml = customHtml + "<body><p>Install 'Browse by voice manager' app from the Chrome store.</p><p>Go to the 'Record' section and switch on the recording button. Start talking.</p><p>Refer to the 'List of commands' catalog under 'Settings' section to get started.</p><p>For a richer experience, download 'Browse by voice' android app from Playstore.</p><p>Connect your android app to your PC by using a session password. Go to the 'Android' section to connect</p><p>Once connected, sit back and command your PC browser from your android device.</p></body></html>";
        //howToUseContent.loadData(customHtml,"text/html","UTF-8");
        customHtml = customHtml + "<body><div class=\"features\"><div class=\"feature\"><i class=\"fa fa-arrow-circle-o-down\"></i><h3>Download the PC version from </h3><p><a class=\"downloadlinks\" href=\"https://chrome.google.com/webstore/detail/browse-by-voice/omkiemleoghnkbbkpphhlgpooblcebja\">Browse by voice</a> chrome Extension.</p></div><div class=\"feature\"><i class=\"fa fa-link\"></i><h3>Connect to PC</h3><p>Go to the 'Device Connect' section and connect to PC by using the session password.</p></div><div class=\"feature\"><i class=\"fa fa-newspaper-o\"></i><h3>Command List</h3><p>Refer to the 'Voice Commands' section to get started.</p></div><div class=\"feature\"><i class=\"fa fa-heart-o\"></i><h3>Enjoy</h3><p>Once connected, sit back and enjoy the experience of browsing web by voice.</p></div></div>";
        customHtml = customHtml + "<script type=\"text/javscript\" src=\"jquery.min.js\"></script>";
        customHtml = customHtml + "<script type=\"text/javscript\" src=\"main.js\"></script>";
        customHtml = customHtml + "<script type=\"text/javscript\" src=\"skel.min.js\"></script>";
        customHtml = customHtml + "<script type=\"text/javscript\" src=\"util.min.js\"></script>";
        customHtml = customHtml + "</body></html>";
        howToUseContent.loadDataWithBaseURL("file:///android_asset/", customHtml, "text/html", "UTF-8", null);
        return v;
    }
}
