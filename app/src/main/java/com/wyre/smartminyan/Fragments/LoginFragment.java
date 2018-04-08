package com.wyre.smartminyan.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.wyre.smartminyan.Activitys.ListActivity;
import com.wyre.smartminyan.Models.User;
import com.wyre.smartminyan.Networking.VolleyLab;
import com.wyre.smartminyan.Preferences.PreferenceLab;
import com.wyre.smartminyan.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by yaakov on 4/2/18.
 */

public class LoginFragment extends Fragment {
    //member variables for the widgets
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmailAddress;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mEmailLogin;
    private EditText mPasswordLogin;
    private BootstrapButton mCreateAccount;
    private BootstrapButton mLogin;

    public void goToMainScreen() {
      //go to the main screen
        Intent i = new Intent(getActivity(), ListActivity.class);
        startActivity(i);
        //call finish to prevent the user from going back to this activity
    //    getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        //assign the variables
        mFirstName = v.findViewById(R.id.edit_first_name);
        mLastName = v.findViewById(R.id.edit_last_name);
        mEmailAddress = v.findViewById(R.id.edit_email_addess);
        mPassword = v.findViewById(R.id.edit_password);
        mConfirmPassword = v.findViewById(R.id.edit_confirm_password);
        mEmailLogin = v.findViewById(R.id.edit_login_email);
        mPasswordLogin = v.findViewById(R.id.edit_login_password);
        mCreateAccount = v.findViewById(R.id.button_sign_up);
        mLogin = v.findViewById(R.id.button_log_in);
        //Events for buttons
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sanity check that both fields are filled out
                if(mEmailLogin.getText().toString().equals("")||mPasswordLogin.getText().toString().equals("")){
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ooops")
                            .setContentText("An email address and password is required to login")
                            .show();
                    return;
                }
                // use volly to make a post request to the api
                String url = "https://smartminyan.herokuapp.com/api/postlogin";
                JSONObject object = new JSONObject();
                try {
                    object.put("EmailAddress", mEmailLogin.getText().toString());
                    object.put("PassWord", mPasswordLogin.getText().toString());
                } catch(Exception ex){
                    Log.d("smartminyan","error when making login post gson. MSG:" + ex.getMessage());
                }

                JsonObjectRequest LoginRequest = new JsonObjectRequest
                        (Request.Method.POST
                                , url, object, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if(response.toString().contains("Error")){
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("We couldn't login, please double check your credentials and try again")
                                            .show();
                                    return;
                                }
                                User user = new Gson().fromJson(response.toString(),User.class);
                                //store the user away
                                PreferenceLab.getPreferenceLab(getContext()).setUser(user);
                                goToMainScreen();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("There was an error logging in, please ensure you have an internet connection and try again later")
                                        .show();
                                Log.d("smartminyan", "volley error: " + error.getMessage());
                                return;
                            }
                        });
                VolleyLab.getVolleyLab(getContext()).addRequest(LoginRequest);
            }
        });
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("smartminyan", "clicked");
                //make sure none of the sign in feilds are blank
                if (mFirstName.getText().toString().equals("") || mLastName.getText().toString().equals("") || mEmailAddress.getText().toString().equals("") || mPassword.getText().toString().equals("") || mConfirmPassword.getText().toString().equals("")) {
                    //show an error message
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("You must fill in all fields to create an account.")
                            .show();
                    return;
                    //ensure that the password and confirm password matches
                } else if (!mConfirmPassword.getText().toString().equals(mPassword.getText().toString())) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Passwords do not match, please fix and try again.")
                            .show();
                    return;
                }
                //make the request to create the account
                final User user = new User();
                user.EmailAddress = mEmailAddress.getText().toString();
                user.FirstName = mFirstName.getText().toString();
                user.LastName = mLastName.getText().toString();
                user.PassWord = mPassword.getText().toString();
                //create a random Guid for the user
                user.Guid = UUID.randomUUID().toString();
                //create a string to hold the request data
                final String requestBody = new Gson().toJson(user);
                // use volly to make a post request to the api
                String url = "https://smartminyan.herokuapp.com/api/postuser";
                StringRequest LoginRequest = new StringRequest
                        (Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("smart minyan", response);
                                //if response is success then store the users current data and go to next screen else display error message
                                if (response.equals("Success")) {
                                    //store the user and go to the main screen
                                    PreferenceLab.getPreferenceLab(getContext()).setUser(user);
                                    goToMainScreen();
                                } else {
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("This user allready exists, try logging in or sign up with a different email address.")
                                            .show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("There was an error creating the user, please ensure you have an internet connection and try again later")
                                        .show();

                                Log.d("smartminyan", "volley error: " + error.getMessage());
                            }
                        }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }
                };
                //pass the string request into the queve
                VolleyLab.getVolleyLab(getContext()).addRequest(LoginRequest);
            }
        });
        return v;
    }
}
