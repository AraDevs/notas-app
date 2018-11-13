package aradevs.com.gradecheck;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bikomobile.multipart.Multipart;
import com.bikomobile.multipart.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import aradevs.com.gradecheck.helpers.ServerHelper;
import aradevs.com.gradecheck.helpers.SharedHelper;
import aradevs.com.gradecheck.helpers.ZipHelper;
import aradevs.com.gradecheck.models.Users;
import aradevs.com.gradecheck.singleton.AppSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.ContentValues.TAG;

/*
import com.github.developerpaul123.filepickerlibrary.FilePickerActivity;
import com.github.developerpaul123.filepickerlibrary.enums.Request;
import com.github.developerpaul123.filepickerlibrary.enums.ThemeType;*/


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
    String id = "";
    Uri tempUri = null;
    @BindView(R.id.appealLoading)
    LinearLayout appealLoading;
    @BindView(R.id.appealViewContainer)
    LinearLayout appealViewContainer;
    Users u;
    SharedHelper sh;
    Context context;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sh = new SharedHelper(getActivity());
        u = sh.getUser();
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
                //check if we have the permissions to read the external storage
                if (Build.VERSION.SDK_INT >= 23) {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent filePicker = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        filePicker.addCategory(Intent.CATEGORY_OPENABLE);
                        filePicker.setType("image/*");
                        startActivityForResult(filePicker, 42);
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }
                break;
            case R.id.appealSend:
                //calling zip helper
                ZipHelper zh = new ZipHelper();
                InputStream is;
                File f;
                try {
                    //if there's a file path, compress it
                    if (tempUri != null) {
                        is = getActivity().getContentResolver().openInputStream(tempUri);
                        f = new File(Objects.requireNonNull(tempUri.getPath()));
                        zh.zip(is, zipPath, f.getName());
                    }
                    //initialize the multipart request
                    Multipart multipart = new Multipart(getActivity().getApplicationContext());

                    //if there's a file, add it to the form data
                    if (tempUri != null) {
                        Uri zipUri = Uri.parse(zipPath);
                        multipart.addFile("application/zip", "filePath", "tempGradeCheck.zip", zipUri);
                    }
                    multipart.addParam("description", appealDescription.getText().toString());
                    multipart.addParam("gradeId", id);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token", u.getToken());
                    MultipartRequest multipartRequest = multipart.getRequest(ServerHelper.URL + ServerHelper.CORRECTIONS, params, new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            //notifying about changes
                            Toast.makeText(getActivity().getApplicationContext(), "Enviado con exito", Toast.LENGTH_SHORT).show();
                            cleanFile();
                            appealDescription.setText("");
                            disableLoading();
                            //returning to grades fragment
                            getFragmentManager().popBackStackImmediate();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                if (error.networkResponse.statusCode == 401) {
                                    sh.sessionExpired(getActivity());
                                } else {
                                    Toast.makeText(context, new String(error.networkResponse.data), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(context, "Error de servidor", Toast.LENGTH_SHORT).show();
                            }
                            disableLoading();
                        }
                    });
                    //send request to queue
                    AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(multipartRequest, getActivity().getApplicationContext());
                    enableLoading();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.appealRemoveFile:
                //remove the file variables
                cleanFile();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        context = getActivity().getApplicationContext();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 42 && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                assert uri != null;
                tempUri = uri;
                File f = new File(Objects.requireNonNull(uri.getPath()));
                appealFileText.setText(f.getName());
                appealRemoveFile.setVisibility(View.VISIBLE);
                Log.e(TAG, "Uri: " + uri.toString());
            }
        }
    }

    private void cleanFile() {
        appealFilePath.setText("");
        appealFileText.setText("Sin archivo");
        tempUri = null;
        appealRemoveFile.setVisibility(View.GONE);
    }

    private void enableLoading() {
        appealLoading.setVisibility(View.VISIBLE);
        appealViewContainer.setVisibility(View.GONE);
    }

    private void disableLoading() {
        appealLoading.setVisibility(View.GONE);
        appealViewContainer.setVisibility(View.VISIBLE);
    }
}
