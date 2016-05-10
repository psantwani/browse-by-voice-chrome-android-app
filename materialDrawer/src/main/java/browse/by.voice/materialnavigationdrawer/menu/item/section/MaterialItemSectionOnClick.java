package browse.by.voice.materialnavigationdrawer.menu.item.section;

import android.graphics.drawable.Drawable;
import android.view.View;

import browse.by.voice.materialnavigationdrawer.MaterialNavigationDrawer;
import browse.by.voice.materialnavigationdrawer.listener.MaterialSectionOnClickListener;


public class MaterialItemSectionOnClick extends MaterialItemSection implements View.OnTouchListener, View.OnClickListener {

    public MaterialItemSectionOnClick(MaterialNavigationDrawer drawer, String title) {
        super.init(drawer, null, false);

        super.sectionListener = null;
        setTitle(title);
        //super.title = title;
    }

    public MaterialItemSectionOnClick(MaterialNavigationDrawer drawer, String title, Drawable icon) {
        super.init(drawer, icon, false);

        super.sectionListener = null;
        setTitle(title);
        //super.title = title;
        //super.init(ctx, hasIcon, target, bottom, changeListener, fullBanner);
    }

    public MaterialItemSectionOnClick(MaterialNavigationDrawer drawer, Drawable icon, boolean iconBanner) {
        super.init(drawer, icon, iconBanner);

        super.sectionListener = null;
        setTitle("");
        //super.title = title;
        //super.init(ctx, hasIcon, target, bottom, changeListener, fullBanner);
    }

    public void setOnSectionClickListener(final MaterialSectionOnClickListener sectionListener) {
        super.sectionListener = sectionListener;
    }
}
