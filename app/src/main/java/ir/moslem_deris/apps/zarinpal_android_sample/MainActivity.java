package ir.moslem_deris.apps.zarinpal_android_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ir.moslem_deris.apps.zarinpal.PaymentBuilder;
import ir.moslem_deris.apps.zarinpal.ZarinPal;
import ir.moslem_deris.apps.zarinpal.enums.ZarinPalError;
import ir.moslem_deris.apps.zarinpal.listeners.OnPaymentListener;
import ir.moslem_deris.apps.zarinpal.models.Payment;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textview);
    }

    public void onPaymentButtonClicked(View view){
        //  Show loading message
        textView.setText("در حال انتقال به صفحه پرداخت\nلطفا صبر کنید ...");
        //  Start payment process
        pay();
    }

    private void pay(){
        /* Prepare a Payment object with custom data.
            The standard form we recommend is you create a constant value including your merchantID, and for
            anything you want to sell create a unique payment object (instead of editing your current object times and times)
            and pass the variable holding merchantID, this helps you edit your merchantID safe and easy anytime you wanted.
        */
        Payment payment = new PaymentBuilder()
                .setMerchantID("4ff2f25a-82c8-45eb-b540-4c5b5ee8aeb5")  //  This is an example, put your own merchantID here.
                .setAmount(1000)                                        //  In Toman
                .setDescription("پرداخت تست پلاگین اندروید")
                .setEmail("moslem.deris@gmail.com")                     //  This field is not necessary.
                .setMobile("09123456789")                               //  This field is not necessary.
                .create();

        /* Call pay method and pass your considered payment object.
            Don't call a payment before the last one is finished, if you do the first payment will be destroyed
            and you may even not receive any callbacks (onSuccess or onFailure)
        */
        ZarinPal.pay(this, payment, new OnPaymentListener() {
            @Override
            public void onSuccess(String refID) {
                Toast.makeText(MainActivity.this, refID, Toast.LENGTH_LONG).show();
                Log.wtf("TAG", "::ZarinPal::  RefId: " + refID);
                textView.setText("پرداخت با موفقیت انجام شد\nکد پیگیری: " + refID);
            }

            @Override
            public void onFailure(ZarinPalError error) {
                String errorMessage = "";
                switch (error){
                    case INVALID_PAYMENT: errorMessage = "پرداخت تایید نشد";           break;
                    case USER_CANCELED:   errorMessage = "پرداخت توسط کاربر متوقف شد"; break;
                    case NOT_ENOUGH_DATA: errorMessage = "اطلاعات پرداخت کافی نیست";    break;
                    case UNKNOWN:         errorMessage = "خطای ناشناخته";              break;
                }
                Log.wtf("TAG", "::ZarinPal::  ERROR: " + errorMessage);
                textView.setText("خطا!!!" + "\n" + errorMessage);
            }
        });
    }
}
