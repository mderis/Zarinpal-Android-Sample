# Zarinpal Android Sample
Library to use ZarinPal payment service in android apps

This library lets android developers have _in-app-purchase_ using **_ZarinPal_** service.

##How to use
###Prepare project:
1. Download and add this jar file into your project as a library:
  1. Copy jar file in project-directory/app/libs/
  2. Right click on file in _Android Studio_ and click on _Add as library_
2. Add our payment Activity to your project manifest file as shown below:
  
  ```xml
  <application>
    .
    .
    .
       <activity android:name="ir.moslem_deris.apps.zarinpal.PaymentActivity"/>
    .
    .
    .
  </application>
  ```
3. Add okHttp library to your project dependencies:

  ``` compile 'com.squareup.okhttp3:okhttp:3.5.0' ```
  
###How to code:
Create a Payment object and put the data you want in it like the code below:
```java
Payment payment = new PaymentBuilder()
                .setMerchantID("4ff2f25a-82c8-45eb-b540-4c5b5ee8aeb5")  //  This is an example, put your own merchantID here.
                .setAmount(100)                                         //  In Toman
                .setDescription("put payment description here")
                .setEmail("moslem.deris@gmail.com")                     //  This field is custom.
                .setMobile("09123456789")                               //  This field is custom.
                .create();
```
Everywhere you want to run the pay method just call `ZarinPal.pay(x, y, z);`

x: Current activity, just pass`this`

y: your Payment object

z: OnPaymentListener

###Example code
```java
ZarinPal.pay(this, payment, new OnPaymentListener() {
            @Override
            public void onSuccess(String refID) {
                Log.wtf("TAG", "HOOOORAAAY!!! your refID is: " + refID);
            }

            @Override
            public void onFailure(ZarinPalError error) {
                String errorMessage = "";
                switch (error){
                    case INVALID_PAYMENT: errorMessage = "پرداخت تایید نشد"; break;
                    case USER_CANCELED:   errorMessage = "پرداخت توسط کاربر متوقف شد"; break;
                    case NOT_ENOUGH_DATA: errorMessage = "اطلاعات پرداخت کافی نیست"; break;
                    case UNKNOWN:         errorMessage = "خطای ناشناخته"; break;
                }
                Log.wtf("TAG", "ERROR: " + errorMessage);
            }
        });
```
