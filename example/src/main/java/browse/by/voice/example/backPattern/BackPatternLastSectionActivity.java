package browse.by.voice.example.backPattern;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import browse.by.voice.example.FragmentDummy;
import browse.by.voice.example.FragmentInstruction;
import browse.by.voice.materialnavigationdrawer.MaterialNavigationDrawer;
import browse.by.voice.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import browse.by.voice.materialnavigationdrawer.menu.MaterialMenu;
import browse.by.voice.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;

public class BackPatternLastSectionActivity extends MaterialNavNoHeaderActivity {

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
        bundle.putString("instruction", "This pattern opens the last open section. If no last section " +
                "available, it close the current activity.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "LastSectionFragment Back Pattern"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));

        // load menu
        this.loadMenu(menu);

        // load first MaterialItemSectionFragment in the menu, because there is no start position
        this.loadStartFragmentFromMenu(menu);

        // set back pattern
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_LAST_SECTION_FRAGMENT);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}