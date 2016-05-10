package browse.by.voice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;

import java.util.Arrays;

import browse.by.voice.example.FragmentDummy;
import browse.by.voice.example.FragmentDummy2;
import browse.by.voice.example.FragmentDummy3;
import browse.by.voice.example.FragmentDummy4;
import browse.by.voice.example.FragmentDummy5;
import browse.by.voice.example.FragmentInstruction;
import browse.by.voice.materialnavigationdrawer.MaterialNavigationDrawer;
import browse.by.voice.materialnavigationdrawer.activity.MaterialNavHeadItemActivity;
import browse.by.voice.materialnavigationdrawer.head.MaterialHeadItem;
import browse.by.voice.materialnavigationdrawer.menu.MaterialMenu;
import browse.by.voice.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import browse.by.voice.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class MainActivity extends MaterialNavHeadItemActivity {

    public MaterialNavigationDrawer drawer = null;
    private static String ConnectionButtonText = "Connect";
    static FragmentInstruction fragmentInstruction = new FragmentInstruction();
    private static FragmentDummy fragmentDummy = new FragmentDummy();
    private FragmentDummy2 fragmentDummy2 = new FragmentDummy2();
    private FragmentDummy3 fragmentDummy3 = new FragmentDummy3();
    private FragmentDummy4 fragmentDummy4 = new FragmentDummy4();
    private FragmentDummy5 fragmentDummy5 = new FragmentDummy5();
    public static Boolean connected = false;
    public MaterialMenu globalMenu;


    SharedPreferences prefs = null;


    @Override
    protected boolean finishActivityOnNewIntent() {
        return false;
    }

    @Override
    protected int getNewIntentRequestCode(Class clazz) {
        return 0;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle bundle = new Bundle();
        bundle.putString("instruction", "Hello Amigo.");

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Device Connect", fragmentInstruction, "Connect to PC"));
        menu.add(new MaterialItemSectionFragment(this, "My Voice", fragmentDummy, "Switch on the mic"));
        menu.add(new MaterialItemSectionFragment(this, "Voice Commands", fragmentDummy2, "List of voice commands"));
        menu.add(new MaterialItemSectionFragment(this, "How To Use", fragmentDummy4, "How To Use"));
        menu.add(new MaterialItemSectionFragment(this, "About Us", fragmentDummy3, "About Us"));
        menu.add(new MaterialItemSectionFragment(this, "Rate Us", fragmentInstruction, "Rate Us"));
        menu.add(new MaterialItemSectionFragment(this, "Scanner", fragmentDummy5, "Scanner"));

        // App page. Rate us
        View v = menu.getSection(5).getView();
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=browse.by.voice"));
                startActivity(browserIntent);
                drawer.closeDrawer();
            }
        });


        // create Head Item
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "Browse By Voice", null, null, R.drawable.icon_patterns, menu);
        this.addHeadItem(headItem);
        globalMenu = menu;

        // load menu
        this.loadMenu(getCurrentHeadItem().getMenu());

        // load the first MaterialItemSectionFragment from the menu
        this.loadStartFragmentFromMenu(getCurrentHeadItem().getMenu());

        prefs = getSharedPreferences("browse.by.voice", MODE_PRIVATE);
    }

    public static String ConnectionButtonText(String text){
        if(text.equals("Chrome aborting Browse By Voice")){
            connected = false;
            fragmentInstruction.MessageFromActivity(text);
        }
        if (text.equals("Chrome requesting master key dialog")){
            connected = true;
            fragmentDummy.MessageFromActivity(text);
        }
        if (text.equals("Chrome requesting master key dialog close")){
            connected = true;
            fragmentDummy.MessageFromActivity(text);
        }
        if(text.equals("Connect")){
            connected = false;
            ConnectionButtonText = "Connect";
        }
        if(text.equals("Disconnect")){
            connected = true;
            ConnectionButtonText = "Disconnect";
        }
        if(text.equals("Open")){
            ConnectionButtonText = ConnectionButtonText;
        }
        return ConnectionButtonText;
    }


    public void SendMessageToFragment(String text){
        String Commands[] = {"Piyush, come in.","Abort Mission. I repeat, Abort Mission."};
        if(text.equals("Piyush, come in.")){
            connected = true;
        }
        if(text.equals("Abort Mission. I repeat, Abort Mission.")){
            connected = false;
        }
        if(connected && !Arrays.asList(Commands).contains(text)) {
            fragmentInstruction.MessageFromActivity(text);
        }
    }


    @Override
    public void afterInit(Bundle savedInstanceState) {
        if (prefs.getBoolean("firstrun", true)) {
            fragmentInstruction.MessageFromActivity("first run");
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            fragmentInstruction.MessageFromActivity("tour guide");
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

}