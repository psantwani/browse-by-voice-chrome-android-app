package browse.by.voice.example.headItemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.amulyakhare.textdrawable.TextDrawable;

import browse.by.voice.R;
import browse.by.voice.example.FragmentDummy;
import browse.by.voice.example.FragmentInstruction;
import browse.by.voice.materialnavigationdrawer.MaterialNavigationDrawer;
import browse.by.voice.materialnavigationdrawer.activity.MaterialNavHeadItemActivity;
import browse.by.voice.materialnavigationdrawer.head.MaterialHeadItem;
import browse.by.voice.materialnavigationdrawer.menu.MaterialMenu;
import browse.by.voice.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import browse.by.voice.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class HeadItemFiveDontCloseOnChangeActivity extends MaterialNavHeadItemActivity {

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

        // add head Item (menu will be loaded automatically)
        this.addHeadItem(getHeadItem1());
        this.addHeadItem(getHeadItem2());
        this.addHeadItem(getHeadItem3());
        this.addHeadItem(getHeadItem4());
        this.addHeadItem(getHeadItem5());

        // load menu
        this.loadMenu(getCurrentHeadItem().getMenu());

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(getCurrentHeadItem().getMenu());
    }

    private MaterialHeadItem getHeadItem1() {

        // information text for the fragment
        Bundle bundle = new Bundle();
        bundle.putString("instruction", "This example shows the head item style with Five items. " +
                "If a new head item is chosen, the drawer will not close. ");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Don't Close Drawer On HeadItem Change (Five Items)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));


        // create Head Item
        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_item_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "A HeadItem", "A Subtitle", drawableAppIcon, R.drawable.mat1, menu);
        headItem.setCloseDrawerOnChanged(false); // don't close the drawer, if this head item chosen

        return headItem;
    }

    private MaterialHeadItem getHeadItem2() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1 (Head 2)", new FragmentDummy(), "Section 1 (Head 2)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));

        // create Head Item
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("B", Color.BLUE);

        MaterialHeadItem headItem = new MaterialHeadItem(this, "B HeadItem", "B Subtitle", headPhoto, R.drawable.mat6, menu);
        headItem.setCloseDrawerOnChanged(false); // don't close the drawer, if this head item chosen

        return headItem;
    }

    private MaterialHeadItem getHeadItem3() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1 (Head 3)", new FragmentDummy(), "Section 1 (Head 3)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));

        // create Head Item
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("C", Color.GRAY);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "C HeadItem", "C Subtitle", headPhoto, R.drawable.mat6, menu);
        headItem.setCloseDrawerOnChanged(false); // don't close the drawer, if this head item chosen

        return headItem;
    }

    private MaterialHeadItem getHeadItem4() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1 (Head 4)", new FragmentDummy(), "Section 1 (Head 4)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));

        // create Head Item
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("D", Color.GRAY);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "D HeadItem", "D Subtitle", headPhoto, R.drawable.mat6, menu);
        headItem.setCloseDrawerOnChanged(false); // don't close the drawer, if this head item chosen

        return headItem;
    }

    private MaterialHeadItem getHeadItem5() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1 (Head 5)", new FragmentDummy(), "Section 1 (Head 5)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));

        // create Head Item
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("E", Color.GRAY);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "E HeadItem", "E Subtitle", headPhoto, R.drawable.mat6, menu);
        headItem.setCloseDrawerOnChanged(false); // don't close the drawer, if this head item chosen

        return headItem;
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}