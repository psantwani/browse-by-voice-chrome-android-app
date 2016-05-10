package browse.by.voice.example.theme;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import browse.by.voice.example.FragmentDummy;
import browse.by.voice.example.FragmentInstruction;
import browse.by.voice.example.theme.actionBarOverlayActivity.FragmentActionBarOverlay;
import browse.by.voice.materialnavigationdrawer.MaterialNavigationDrawer;
import browse.by.voice.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import browse.by.voice.materialnavigationdrawer.menu.MaterialMenu;
import browse.by.voice.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import browse.by.voice.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;

public class ActionBarOverlayActivity extends MaterialNavNoHeaderActivity {

    MaterialNavigationDrawer drawer = null;

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

        // information text for the fragment
        Bundle bundle = new Bundle();
        bundle.putString("instruction", "Open the menu and press the section 'Show Overlay', to see it."
                + " To add overlay support, see 'android:theme=\"@style/ActionBarOverlayTheme\"' in the AndroidManifest.xml and the" +
                " part for this activity." +
                " The style is defined in the styles.xml with the name ActionBarOverlayTheme. " +
                "Or set it on runtime. For this, see the source code from this example.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Actionbar Overlay"));
        menu.add(new MaterialItemSectionFragment(this, "Show Overlay", new FragmentActionBarOverlay(), "Show Overlay"));
        menu.add(new MaterialItemDevisor());
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));

        // load menu
        this.loadMenu(menu);

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(menu);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}