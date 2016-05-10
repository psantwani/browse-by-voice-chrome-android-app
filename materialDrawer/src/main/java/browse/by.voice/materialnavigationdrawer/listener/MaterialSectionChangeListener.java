package browse.by.voice.materialnavigationdrawer.listener;

import browse.by.voice.materialnavigationdrawer.menu.item.section.MaterialItemSection;

public interface MaterialSectionChangeListener {
    public void onBeforeChangeSection(MaterialItemSection newSection);

    public void onAfterChangeSection(MaterialItemSection newSection);
}
