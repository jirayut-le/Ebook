package com.example.delusion.ebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class FundView extends AppCompatActivity {

    private TextView balanceTxt;
    private Button addBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_view);

        balanceTxt = (TextView) findViewById(R.id.currentBalance);
        addBalance = (Button) findViewById(R.id.addBalance);
        setBalanceText();
    }

    private void setBalanceText(){
        balanceTxt.setText(getIntent().getStringExtra("balance"));
    }

    private void addBalance(){

    }
}
