package browse.by.voice.materialnavigationdrawer.listener;

import browse.by.voice.materialnavigationdrawer.head.MaterialHeadItem;

public interface MaterialHeadItemChangeListener {

    public void onBeforeChangeHeadItem(MaterialHeadItem newHeadItem);

    public void onAfterChangeHeadItem(MaterialHeadItem newHeadItem);
}
