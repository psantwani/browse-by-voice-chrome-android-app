package browse.by.voice.example.listener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import browse.by.voice.R;
import browse.by.voice.example.FragmentDummy;
import browse.by.voice.example.FragmentInstruction;
import browse.by.voice.materialnavigationdrawer.MaterialNavigationDrawer;
import browse.by.voice.materialnavigationdrawer.activity.MaterialNavHeadItemActivity;
import browse.by.voice.materialnavigationdrawer.head.MaterialHeadItem;
import browse.by.voice.materialnavigationdrawer.listener.MaterialHeadItemBackgroundOnClickListener;
import browse.by.voice.materialnavigationdrawer.menu.MaterialMenu;
import browse.by.voice.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import browse.by.voice.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class HeadItemOneBackgroundOnClickListenerActivity extends MaterialNavHeadItemActivity {

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
        bundle.putString("instruction", "Open the drawer and click on the background image from the head item.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Head Item Style (One Item), Background OnClick Listener"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));

        // create Head Item
        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_item_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "My HeadItem", "My Subtitle", drawableAppIcon, R.drawable.mat1, menu);
        // set Avatar on click listener
        headItem.setBackgroundOnClickListener(new MaterialHeadItemBackgroundOnClickListener() {
            @Override
            public void onClick(MaterialHeadItem headItem) {
                Toast.makeText(drawer, "background on click", Toast.LENGTH_LONG).show();
            }
        });
        this.addHeadItem(headItem);

        // load menu
        this.loadMenu(getCurrentHeadItem().getMenu());

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(getCurrentHeadItem().getMenu());
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}