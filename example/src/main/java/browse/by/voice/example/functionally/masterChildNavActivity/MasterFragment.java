package browse.by.voice.example.functionally.masterChildNavActivity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import browse.by.voice.materialnavigationdrawer.MaterialNavigationDrawer;

public class MasterFragment extends Fragment {

    private MaterialNavigationDrawer drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        drawer = (MaterialNavigationDrawer) getActivity();

        Button button = new Button(this.getActivity());
        button.setText("start child fragment");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        button.setGravity(Gravity.CENTER);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.changeFragment(new ChildFragment(), "Child Title");
                // normally currentSection gets unselect on setCustomFragment call
                // in the next relase, i will add a new method without unselect
                drawer.getCurrentSectionFragment().select();

                // call on current git head. drawer.getCurrentSectionFragment().select(); is not needed
                // drawer.setCustomFragment(drawer.getCurrentSectionFragment().getTargetFragment(), drawer.getCurrentSectionFragment().getFragmentTitle(), true, false);
            }
        });

        return button;
    }
}