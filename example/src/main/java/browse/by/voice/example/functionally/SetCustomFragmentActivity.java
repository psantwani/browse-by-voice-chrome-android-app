package browse.by.voice.example.functionally;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import browse.by.voice.example.FragmentDummy;
import browse.by.voice.example.FragmentInstruction;
import browse.by.voice.materialnavigationdrawer.MaterialNavigationDrawer;
import browse.by.voice.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import browse.by.voice.materialnavigationdrawer.menu.MaterialMenu;
import browse.by.voice.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;

/**
 * Created by marc on 23.02.2015.
 */
public class SetCustomFragmentActivity extends MaterialNavNoHeaderActivity {

    // info: see manifest for the dark theme

    MaterialNavigationDrawer drawer = null;

    @Override
    protected boolean finishActivityOnNewIntent() {
        return false;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));

        // load menu
        this.loadMenu(menu);

        // information text for the fragment
        Bundle bundle = new Bundle();
        bundle.putString("instruction", "This activity starts with a custom fragment. " +
                "It's not from the menu.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // set custom fragment
        this.changeFragment(fragmentInstruction, "Custom Fragment Instruction");
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }

    @Override
    protected int getNewIntentRequestCode(Class clazz) {
        return 0;
    }
}
