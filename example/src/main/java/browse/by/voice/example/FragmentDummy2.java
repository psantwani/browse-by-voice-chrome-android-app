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

public class FragmentDummy2 extends Fragment {

    private WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dummyactivity2,container,false);
        webView = (WebView)v.findViewById(R.id.commandsList);
        String customHtml = "<html>";
        customHtml =  customHtml + "<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\" />";
        customHtml = customHtml + " <table id=\"featureTable\"> <tr><td> Refresh </td></tr><tr><td> Refreshes the current website page. </td></tr><tr><td> New window </td></tr><tr><td> Opens a new window. </td></tr><tr><td>Close window</td></tr><tr><td>Closes the window in focus</td></tr><tr><td>Next window</td></tr><tr><td>Switches to the next open window, if any.</td></tr><tr><td> New tab </td></tr><tr><td> Opens a new tab. </td></tr><tr><td> Reopen tab </td></tr><tr><td> Reopens the last closed tab(s). </td></tr><tr><td> Close other tabs </td></tr><tr><td> Closes all the other tabs on the active window except the current page tab. </td></tr><tr><td> First tab </td></tr><tr><td> Moves to the first tab off all the open tabs. </td></tr><tr><td> Last tab </td></tr><tr><td> Moves to the last tab off all the open tabs. </td></tr><tr><td> Move to left </td></tr><tr><td> Moves the current page tab to left by one tab. </td></tr><tr><td> Move to right </td></tr><tr><td> Moves the current page tab to right by one tab. </td></tr><tr><td> Next tab </td></tr><tr><td> Switches to the tab on the right of the active tab. </td></tr><tr><td> Previous tab </td></tr><tr><td> Switches to the tab on the left of the active tab. </td></tr><tr><td> Close tab </td></tr><tr><td> Closes the active tab. </td></tr><tr><td> Back </td></tr><tr><td> Navigates to the previous page. </td></tr><tr><td> Forward </td></tr><tr><td> Navigates to the next page. </td></tr><tr><td> Zoom in </td></tr><tr><td> Enlarges the size of the browser content. </td></tr><tr><td> Zoom out </td></tr><tr><td> Reduces the size of the browser content. </td></tr><tr><td> Normal size </td></tr><tr><td> Sets the size of the browser content to 100%. </td></tr><tr><td> Top </td></tr><tr><td> Scrolls to the top of the current page. </td></tr><tr><td> Bottom </td></tr><tr><td> Scrolls to the bottom of the current page. </td></tr><tr><td> Down </td></tr><tr><td> Scrolls down the page. </td></tr><tr><td> Up </td></tr><tr><td> Scrolls up the page. </td></tr><tr><td> Bookmark this page </td></tr><tr><td> Saves the page link in the 'Other Bookmarks' folder. </td></tr><tr><td> Clear history </td></tr><tr><td> Clears your browsing history. </td></tr><tr><td> Clear downloads </td></tr><tr><td> Clears your 'downloads' history. </td></tr><tr><td>Print this page</td></tr><tr><td>Prints the page you are currently on.</td></tr><tr><td> Open Facebook </td></tr><tr><td> Navigates the page to facebook.com </td></tr><tr><td> Open Google </td></tr><tr><td> Navigates the page to google.com </td></tr><tr><td> Open Twitter </td></tr><tr><td> Navigates the page to twitter.com </td></tr><tr><td> Open YouTube </td></tr><tr><td> Navigates the page to youtube.com </td></tr><tr><td> Save website </td></tr><tr><td> Saves the website as a popular site. In order to open this website again after saving, directly use the command - Open website_name' </td></tr><tr><td>Search 'keyword'</td></tr><tr><td>Uses the search box on the page to search for your keyword.</td></tr><tr><td> Label Images </td></tr><tr><td> Tags all the images on the screen, each with a unique number. In order to open an image, just call out the tagged number on that image. </td></tr><tr><td> Left </td></tr><tr><td> Slides to the previous image in the gallery. </td></tr><tr><td> Right </td></tr><tr><td> Slides to the next image in the gallery. </td></tr><tr><td> Close </td></tr><tr><td> Closes the opened image. </td></tr><tr><td> Play video </td></tr><tr><td> Plays the video visible on screen. </td></tr><tr><td> Pause video </td></tr><tr><td> Pauses a playing video. </td></tr><tr><td> Mute</td></tr><tr><td> Mutes the sound of the video. </td></tr><tr><td> Unmute</td></tr><tr><td> Unmutes the sound of the video. </td></tr><tr><td> Volume up </td></tr><tr><td> Increases the volume of the video. </td></tr><tr><td> Volume down </td></tr><tr><td> Decreases the volume of the video. </td></tr><tr><td> Label videos </td></tr><tr><td> Tags all the videos on the screen, each with a unique number. In order to play a video, just call out the tagged number on that video. </td></tr><tr><td> Full screen </td></tr><tr><td> Make the video full screen. </td></tr><tr><td> New search </td></tr><tr><td> Navigates to the default search engine website from any web page. </td></tr><tr><td> Google 'search_text' </td></tr><tr><td> Checks Google search engine for the keyword/text. </td></tr><tr><td> Yahoo 'search_text' </td></tr><tr><td> Checks Yahoo search engine for the keyword/text. </td></tr><tr><td> Bing 'search_text' </td></tr><tr><td> Checks Bing search engine for the keyword/text. </td></tr><tr><td> Ask 'search_text' </td></tr><tr><td> Checks Ask search engine for the keyword/text. </td></tr><tr><td> Next page </td></tr><tr><td> Navigates to the next page off the search result pages. </td></tr><tr><td> Previous page </td></tr><tr><td> Navigates to the previous page off the search result pages. </td></tr><tr><td> Check email </td></tr><tr><td> Navigates to the default email provider set by the user under 'Settings'. </td></tr><tr><td> Auto login </td></tr><tr><td> Automatically logs in to the website, if the login credentials are saved for that website under 'Settings'. </td></tr><tr><td>Forget master key</td></tr><tr><td>Forgets the master key you entered, so that next time someone using your computer says 'auto login', they won't be logged in to your account.</td></tr></table> ";
        webView.loadDataWithBaseURL("file:///android_asset/", customHtml, "text/html", "UTF-8", null);
        return v;
    }
}
