package browse.by.voice.materialnavigationdrawer.menu.item.section;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import browse.by.voice.materialnavigationdrawer.MaterialNavigationDrawer;

public class MaterialItemSectionActivity extends MaterialItemSection {

    private Intent intent;

    public MaterialItemSectionActivity(MaterialNavigationDrawer drawer, String title, Intent intent) {
        super.init(drawer, null, false);

        setTitle(title);
        sectionListener = drawer;

        //super.title = title;
        this.intent = intent;
    }

    public MaterialItemSectionActivity(MaterialNavigationDrawer drawer, String title, Drawable icon, Intent intent) {
        super.init(drawer, icon, false);

        setTitle(title);
        sectionListener = drawer;

        //super.title = title;
        this.intent = intent;
    }

    public MaterialItemSectionActivity(MaterialNavigationDrawer drawer, Drawable icon, boolean iconBanner, Intent intent) {
        super.init(drawer, icon, iconBanner);

        setTitle(title);
        sectionListener = drawer;

        //super.title = title;
        this.intent = intent;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
