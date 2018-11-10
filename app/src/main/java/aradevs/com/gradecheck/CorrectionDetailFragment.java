package aradevs.com.gradecheck;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import aradevs.com.gradecheck.models.Corrections;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class CorrectionDetailFragment extends Fragment {


    @BindView(R.id.appealDetailState)
    TextView appealDetailState;
    @BindView(R.id.appealDetailDescription)
    TextView appealDetailDescription;
    @BindView(R.id.correctionDetailOwner)
    TextView correctionDetailOwner;
    @BindView(R.id.appealViewContainer)
    LinearLayout appealViewContainer;
    Unbinder unbinder;
    Corrections corrections;

    public CorrectionDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_correction_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        corrections = bundle.getParcelable("correction");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String state = "Estado: " + corrections.getState();
        appealDetailState.setText(state);
        appealDetailDescription.setText(corrections.getDescription());
        correctionDetailOwner.setText(corrections.getOwner());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }
}
