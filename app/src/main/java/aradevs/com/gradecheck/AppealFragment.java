package aradevs.com.gradecheck;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppealFragment extends Fragment {


    @BindView(R.id.appealDescription)
    EditText appealDescription;
    @BindView(R.id.appealFileText)
    TextView appealFileText;
    @BindView(R.id.appealFile)
    Button appealFile;
    @BindView(R.id.appealSend)
    Button appealSend;
    Unbinder unbinder;
    @BindView(R.id.appealFilePath)
    TextView appealFilePath;
    @BindView(R.id.appealRemoveFile)
    Button appealRemoveFile;

    public AppealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appeal, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }

    @OnClick({R.id.appealFile, R.id.appealSend, R.id.appealRemoveFile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.appealFile:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //TODO: implementar los archivos
                        Intent filePicker = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        String[] mimeTypes = {"application/zip", "image/*"};
                        filePicker.setType("*/*");
                        filePicker.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                        startActivityForResult(filePicker, 42);
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                }
                break;
            case R.id.appealSend:
                break;
            case R.id.appealRemoveFile:
                appealFilePath.setText("");
                appealFileText.setText("Sin archivo");
                appealRemoveFile.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 42 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                assert uri != null;
                File f = new File(Objects.requireNonNull(uri.getPath()));
                appealFileText.setText(f.getName());
                appealFilePath.setText(f.getAbsolutePath());
                appealRemoveFile.setVisibility(View.VISIBLE);
                Log.e("Archivo", appealFilePath.getText().toString());
            }
        }
    }


}
