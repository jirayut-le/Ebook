package com.example.delusion.ebook;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.delusion.ebook.MainActivity.user;

public class UserView extends AppCompatActivity {

    private TextView balanceTxt;
    private Button addBalanceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        balanceTxt = (TextView) findViewById(R.id.currentBalance);
        addBalanceBtn = (Button) findViewById(R.id.addBalance);

        setBalanceText();

        addBalanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UserView.this);
                builder.setTitle("Add Balance");
                View viewInflated = getLayoutInflater().inflate(R.layout.add_balance_layout, null);
                builder.setView(viewInflated);

                final AlertDialog alertDialog = builder.create();

                final EditText amountAdd = (EditText) viewInflated.findViewById(R.id.editText_add);
                Button addBtn = (Button) viewInflated.findViewById(R.id.addFundBtn);
                Button cancelBtn = (Button) viewInflated.findViewById(R.id.cancelAdd);

                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String amount = amountAdd.getText().toString();
                        if(!amount.equals("")) {
                            addBalance(amount);
                            Toast.makeText(UserView.this, "Add " + amount + " to balance.", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(UserView.this, "Error you must fill amount to add.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });
    }

    private void setBalanceText(){
        balanceTxt.setText(Double.toString(user.getFund()));
    }

    private void addBalance(String fund){
        user.addFund(fund);
        setBalanceText();
    }
}
