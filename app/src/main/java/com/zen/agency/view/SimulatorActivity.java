package com.zen.agency.view;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.zen.agency.R;

import java.math.BigDecimal;

public class SimulatorActivity extends AppCompatActivity {


    AppCompatButton calculate;
    AppCompatTextView result;
    AppCompatEditText et_house_price;
    AppCompatEditText et_financial_co;
    AppCompatEditText et_loan_rate;
    AppCompatEditText et_loan_period;
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calculate = findViewById(R.id.calculate);
        result = findViewById(R.id.result);
        et_house_price = findViewById(R.id.et_house_price);
        et_financial_co = findViewById(R.id.et_financial_co);
        et_loan_rate = findViewById(R.id.et_loan_rate);
        et_loan_period = findViewById(R.id.et_loan_period);

        String price = getIntent().getStringExtra("price");
        if (!TextUtils.isEmpty(price)) {
            et_house_price.setText(price);
        }

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = et_house_price.getText().toString();
                String financial = et_financial_co.getText().toString();
                String loanRate = et_loan_rate.getText().toString();
                String loanPeriod = et_loan_period.getText().toString();
                BigDecimal fi = new BigDecimal(financial);
                BigDecimal priceBD = new BigDecimal(price);
                BigDecimal needLoan = priceBD.subtract(fi);
                String needLoanNumber = needLoan.toString();
                Double loan, rate;
                int term;
                do {
                    loan = Double.parseDouble(needLoanNumber);
                    if (loan <= 99) {
                        Toast.makeText(SimulatorActivity.this, "Loan amount must be greater than 99", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } while (loan <= 99);

                do {
                    term = Integer.parseInt(loanPeriod);
                    if (term <= 0) {
                        Toast.makeText(SimulatorActivity.this, "Loan term must be at least one year", Toast.LENGTH_LONG).show();
                        return;
                    } else if (term > 50) {
                        Toast.makeText(SimulatorActivity.this, "Loan term cannot exceed 50 years", Toast.LENGTH_LONG).show();
                        return;
                    }
                } while (term <= 0 || term > 50);
                do {
                    rate = Double.parseDouble(loanRate);
                    if (rate < 0) {
                        Toast.makeText(SimulatorActivity.this, "Interest rate cannot be negative", Toast.LENGTH_LONG).show();
                        return;
                    }
                } while (rate < 0);
                // Call functions
                double monthlyPayment = calculateMonthlyPayment(loan, term, rate, 0);
                double totalInterestAccrued = calculateInterestAccrued(monthlyPayment, loan, 0, term);
                BigDecimal b = new BigDecimal(monthlyPayment);
                double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                StringBuilder sb = new StringBuilder();
                sb.append("Monthly Payments: " + f1);
                sb.append("\n");
                sb.append("Total Accrued Interest: " + (int) Math.round(totalInterestAccrued));
                result.setText(sb.toString());
            }
        });

    }

    /**
     * Returns monthly payment amount on a loan.
     *
     * @param loan    Loan amount
     * @param term    Loan term in years
     * @param rate    Interest rate per year on a loan
     * @param downPay Downpayment on a loan
     * @return Monthly payment on a loan
     */
    public static double calculateMonthlyPayment(double loan, int term, double rate, double downPay) {
        double monthlyRate = (rate / 100.0) / 12;
        int termsInMonths = term * 12;
        loan -= downPay;
        double monthlyPayment = (monthlyRate * loan) / (1 - Math.pow((1 + monthlyRate), -termsInMonths));
        return monthlyPayment;
    }

    /**
     * Returns total interest accrued for the period of a loan.
     *
     * @param loan           Loan amount
     * @param term           Loan term in years
     * @param downPay        Downpayment on a loan
     * @param monthlyPayment Monthly payment on a loan
     * @return Total interest accrued
     */
    public static double calculateInterestAccrued(double monthlyPayment, double loan, double downPay, int term) {
        int termsInMonths = term * 12;
        loan -= downPay;
        double totalCost = monthlyPayment * termsInMonths;
        double totalInterestAccrued = totalCost - loan;
        return totalInterestAccrued;
    }
}