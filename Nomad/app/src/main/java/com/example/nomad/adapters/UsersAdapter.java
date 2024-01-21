package com.example.nomad.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.UserDTO;
import com.example.nomad.services.UserService;

import java.util.ArrayList;

public class UsersAdapter extends ArrayAdapter<UserDTO> {

    private ArrayList<UserDTO> data;
    private Context context;
    private FragmentActivity activity;

    private UserService userService = new UserService();

    public UsersAdapter(@NonNull Context context, ArrayList<UserDTO> data, FragmentActivity activity) {
        super(context, R.layout.table_row, data);
        this.data = data;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public UserDTO getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.table_row,
                    parent, false);
        }

        TextView columnFirstName = convertView.findViewById(R.id.column_first_name);
        TextView columnLastName = convertView.findViewById(R.id.column_last_name);
        TextView columnUsername = convertView.findViewById(R.id.column_username);
        TextView columnAddress = convertView.findViewById(R.id.column_address);
        TextView columnPhone = convertView.findViewById(R.id.column_phone);

        Button blockButton = convertView.findViewById(R.id.button_block_unblock);

        UserDTO user = getItem(position);

        columnFirstName.setText(user.getFirstName());
        columnLastName.setText(user.getLastName());
        columnUsername.setText(user.getUsername());
        columnAddress.setText(user.getAddress());
        columnPhone.setText(user.getPhoneNumber());

        if (user.isSuspended()) {
            blockButton.setText("Un-block");
        }else{
            blockButton.setText("Block");
        }

        blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isSuspended()) {
                    userService.unSuspendUSer(user.getId());
                    user.setSuspended(false);
                    blockButton.setText("Block");
                }else{
                    userService.suspendUSer(user.getId());
                    user.setSuspended(true);
                    blockButton.setText("Un-block");
                }
            }
        });
        return convertView;
    }
}
