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
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bikomobile.multipart.Multipart;
import com.bikomobile.multipart.MultipartRequest;
import com.github.developerpaul123.filepickerlibrary.FilePickerActivity;
import com.github.developerpaul123.filepickerlibrary.enums.Request;
import com.github.developerpaul123.filepickerlibrary.enums.ThemeType;

import java.io.File;
import java.io.IOException;

import aradevs.com.gradecheck.helpers.ServerHelper;
import aradevs.com.gradecheck.helpers.ZipHelper;
import aradevs.com.gradecheck.singleton.AppSingleton;
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

    String zipPath = "/storage/emulated/0/tempGradeCheck.zip";
    String path = "";
    String id;

    public AppealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appeal, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
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
                        Intent filePickerDialog = new Intent(getActivity(), FilePickerActivity.class);
                        filePickerDialog.putExtra(FilePickerActivity.THEME_TYPE, ThemeType.ACTIVITY);
                        filePickerDialog.putExtra(FilePickerActivity.REQUEST, Request.FILE);
                        startActivityForResult(filePickerDialog, 42);
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }
                break;
            case R.id.appealSend:
                ZipHelper zh = new ZipHelper();
                try {
                    if (!path.equals("")) {
                        zh.zip(path, zipPath, getActivity());
                    }
                    Uri uri = Uri.parse(zipPath);
                    File f = new File(zipPath);
                    Log.e("URI", uri.getPath());

                    Multipart multipart = new Multipart(getActivity().getApplicationContext());
                    if (!path.equals("")) {
                        multipart.addFile("application/zip", "filePath", f.getName(), uri);
                    }
                    multipart.addParam("description", appealDescription.getText().toString());
                    multipart.addParam("gradeId", id);

                    MultipartRequest multipartRequest = multipart.getRequest(ServerHelper.URL + ServerHelper.CORRECTIONS, new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                    //send request to queue
                    AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(multipartRequest, getActivity().getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            Toast.makeText(getActivity().getApplicationContext(), "File Selected: " + data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH), Toast.LENGTH_LONG).show();
            File f = new File(data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH));
            appealFileText.setText(f.getName());
            path = f.getAbsolutePath();
        }
    }
}
