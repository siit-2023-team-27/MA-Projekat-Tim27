package com.example.nomad.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.activities.HomeActivity;
import com.example.nomad.activities.LoginActivity;
import com.example.nomad.dto.LoginDTO;
import com.example.nomad.dto.UserDTO;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.UserService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    UserDTO loggedUser;

    UserService userService = new UserService();
    AuthService authService = new AuthService();

    EditText editTextName;
    EditText editTextSurname;
    EditText editTextUsername;
    EditText editTextAddress;
    EditText editTextPhone;
    EditText editTextOldPassword;
    EditText editTextNewPassword;
    EditText editTextConfirmPassword;
    Button buttonEdit;
    Button buttonChangePassword;
    Button buttonDelete;
    Button buttonSavePassword;

    LinearLayout passwordLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        editTextName = view.findViewById(R.id.editTextName);
        editTextSurname = view.findViewById(R.id.editTextSurname);
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextOldPassword = view.findViewById(R.id.editTextOldPassword);
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);

        buttonChangePassword = view.findViewById(R.id.button_change_password);
        buttonEdit = view.findViewById(R.id.button_edit);
        buttonDelete = view.findViewById(R.id.button_delete_profile);
        buttonSavePassword = view.findViewById(R.id.button_save_password);

        passwordLayout = view.findViewById(R.id.password_layout);

        setLoggedUser();

        setOnClickDelete();
        setOnClickChangePassword();
        setOnClickEdit();
        setOnClickSavePassword();

        return view;
    }

    public void setLoggedUser() {
        userService.getLoggedUser();
        userService.getLogged().observe(getActivity(), new Observer<UserDTO>() {
            @Override
            public void onChanged(UserDTO userDTO) {
                loggedUser = userDTO;
                editTextName.setText(loggedUser.getFirstName());
                editTextSurname.setText(loggedUser.getLastName());
                editTextUsername.setText(loggedUser.getUsername());
                editTextAddress.setText(loggedUser.getAddress());
                editTextPhone.setText(loggedUser.getPhoneNumber());
            }
        });
    }

    public void setOnClickDelete() {
        buttonDelete.setOnClickListener(v -> {
            userService.deleteUser(loggedUser.getId(), getActivity());
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

    public void setOnClickEdit() {
        buttonEdit.setOnClickListener(v -> {
            loggedUser.setFirstName(String.valueOf(editTextName.getText()));
            loggedUser.setLastName(String.valueOf(editTextSurname.getText()));
            loggedUser.setUsername(String.valueOf(editTextUsername.getText()));
            loggedUser.setAddress(String.valueOf(editTextAddress.getText()));
            loggedUser.setPhoneNumber(String.valueOf(editTextPhone.getText()));
            userService.editUser(loggedUser.getId(), loggedUser);

            Toast.makeText(getActivity(), "User is successfully updated!", Toast.LENGTH_SHORT).show();
        });
    }

    public void setOnClickChangePassword() {
        buttonChangePassword.setOnClickListener(v -> {
            if (passwordLayout.getVisibility() == View.VISIBLE) {
                passwordLayout.setVisibility(View.GONE);
            } else {
                passwordLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setOnClickSavePassword() {
        buttonSavePassword.setOnClickListener(v -> {
            String oldPassword = String.valueOf(editTextOldPassword.getText());
            String newPassword = String.valueOf(editTextNewPassword.getText());
            String confirmPassword = String.valueOf(editTextConfirmPassword.getText());

            authService.reauthenticate(new LoginDTO(loggedUser.getUsername(), oldPassword));
            authService.getIsReauthenticated().observe(getActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean IsReauthenticated) {
                    if(IsReauthenticated) {
                        if(validatePassword(newPassword, confirmPassword)){
                            loggedUser.setPassword(newPassword);
                            userService.editUser(loggedUser.getId(), loggedUser);
                            Toast.makeText(getActivity(), "Password is successfully updated!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Old Password Incorrect", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        });
    }

    public boolean validatePassword(String newPassword, String confirmPassword) {
        if(newPassword.length() < 8) {
            Toast.makeText(getActivity(), "New password must be at least 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!confirmPassword.equals(newPassword)) {
            Toast.makeText(getActivity(), "Passwords must match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}