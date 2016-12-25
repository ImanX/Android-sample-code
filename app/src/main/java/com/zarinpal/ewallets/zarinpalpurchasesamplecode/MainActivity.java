package com.zarinpal.ewallets.zarinpalpurchasesamplecode;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPayment = (Button) findViewById(R.id.btnPayment);


        /**
         * When User Return to Application From IPG on Browser
         */


        Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {


                if (isPaymentSuccess) {
                    /* When Payment Request is Success :) */
                    String message = "Your Payment is Success :) " + refID;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    /* When Payment Request is Failure :) */
                    String message = "Your Payment is Failure :(";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }


            }
        });


        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPayment();
            }
        });

    }


    private void requestPayment() {

        ZarinPal       purchase = ZarinPal.getPurchase(this);
        PaymentRequest payment  = ZarinPal.getPaymentRequest();

        payment.setMerchantID("71c705f8-bd37-11e6-aa0c-000c295eb8fc");
        payment.setAmount(100);
        payment.setDescription("In App Purchase Test SDK");
        payment.setCallbackURL("yourapp://app");     /* Your App Scheme */
        payment.setMobile("09355106005");            /* Optional Parameters */
        payment.setEmail("imannamix@gmail.com");     /* Optional Parameters */


        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {


                if (status == 100) {
                    /*
                    When Status is 100 Open Zarinpal PG on Browser
                    */
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Your Payment Failure :(", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
