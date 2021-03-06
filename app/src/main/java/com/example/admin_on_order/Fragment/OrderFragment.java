package com.example.admin_on_order.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_on_order.Adapter.OrderRecyclerAdapter;
import com.example.admin_on_order.R;
import com.example.admin_on_order.isPrinter;
import com.example.admin_on_order.model.OrderModel;
import com.example.admin_on_order.model.OrderRecyclerItem;
import com.example.admin_on_order.model.PrintOrderModel;
import com.example.admin_on_order.testActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sam4s.printer.Sam4sBuilder;
import com.sam4s.printer.Sam4sPrint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class OrderFragment extends Fragment {

    SharedPreferences pref;
    View view;

    private TextView dateResultView;
    private ImageView btnDatePick;
    private String dateResult;
    String storeCode;

    Context context;
    RecyclerView recyclerView;
    private OrderRecyclerAdapter adapter;
    ArrayList<OrderRecyclerItem> items;
    private Activity activity;

    private static String BASE_URL = "http://15.164.232.164:5000/";

    Calendar calendar;
    int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        storeCode = pref.getString("storecode", "");

        dateResultView = (TextView) view.findViewById(R.id.order_date_result);
        btnDatePick = (ImageView) view.findViewById(R.id.btn_order_date);

        recyclerView = view.findViewById(R.id.fragment_order_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        context = getActivity();
        // Current Time now
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateResultView.setText(dateFormat.format(calendar.getTime()));
        dateResult = dateResultView.getText().toString();
        initViewOrder(storeCode, dateResult);

        DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                dateResultView.setText(dateFormat.format(calendar.getTime()));
                dateResult = dateResultView.getText().toString();
                initViewOrder(storeCode, dateResult);
            }
        };

        // Choose Date Picker Dialog
        dateResultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        dateSet,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        return view;
    }

    public void initViewOrder(String storeCode, String dateResult) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String time2 = format2.format(calendar.getTime());
        Log.d("daon_test", "time = "+time2);
        FirebaseDatabase.getInstance().getReference().child("order").child(pref.getString("storename", "")).child(time2).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<OrderRecyclerItem> items1 = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    PrintOrderModel printOrderModel = item.getValue(PrintOrderModel.class);
                    if (printOrderModel != null) {
//                        Log.d("daon_test", printOrderModel.getTime());
//                        if (printOrderModel.getOrderCheck()!= null) {
                            if (printOrderModel.getOrderCheck().equals("x") && printOrderModel.getOrder() != null) {
                                if (printOrderModel.getTable() != null || !printOrderModel.getTable().equals("")
                                        || printOrderModel.getOrder() != null || !printOrderModel.getOrder().equals("")
                                        || printOrderModel.getOrderCheck() != null || !printOrderModel.getOrderCheck().equals("")
                                        || printOrderModel.getTime() != null || !printOrderModel.getTime().equals("")) {

                                    //  ????????? ????????? ????????? ??????
//                        if (printOrderModel.getTable().equals("17") || printOrderModel.getTable().equals("18") || printOrderModel.getTable().equals("19") || printOrderModel.getTable().equals("20")
//                                || printOrderModel.getTable().equals("21") || printOrderModel.getTable().equals("22") || printOrderModel.getTable().equals("23")
//                                || printOrderModel.getTable().equals("???1") || printOrderModel.getTable().equals("???2") || printOrderModel.getTable().equals("???3")) {
//                        if (printOrderModel.getTable().equals("1") || printOrderModel.getTable().equals("2") || printOrderModel.getTable().equals("3") || printOrderModel.getTable().equals("4")){
//                        if (printOrderModel.getTable().equals("5") || printOrderModel.getTable().equals("6") || printOrderModel.getTable().equals("7") || printOrderModel.getTable().equals("8")
//                                || printOrderModel.getTable().equals("9")){
//                        if (printOrderModel.getTable().equals("10") || printOrderModel.getTable().equals("11") || printOrderModel.getTable().equals("12") || printOrderModel.getTable().equals("13")
//                                || printOrderModel.getTable().equals("14") || printOrderModel.getTable().equals("15")|| printOrderModel.getTable().equals("16")|| printOrderModel.getTable().equals("A-4")
//                                || printOrderModel.getTable().equals("A-5")|| printOrderModel.getTable().equals("A-6")) {
                                    ArrayList<OrderModel> body = new ArrayList<>();
                                    body = printOrderModel.getOrder();
                                    OrderRecyclerItem item1 = new OrderRecyclerItem(
                                            printOrderModel.getTable(),
                                            printOrderModel.getOrder(),
                                            item.getKey(),
                                            printOrderModel.getPrice(),
                                            printOrderModel.getOrderCheck(),
                                            printOrderModel.getTime(),
                                            printOrderModel.getMenuNo());
                                    for (int i = 0; i < body.size(); i++) {
                                    if (!body.get(i).getMenuNo().equals("")) {
                                        ///////////??????
                                        if (Integer.parseInt(body.get(i).getMenuNo()) > 6000 && Integer.parseInt(body.get(i).getMenuNo()) < 6021) {
                                            items1.add(item1);
                                            break;
                                        }
                                        if (Integer.parseInt(body.get(i).getMenuNo()) == 6031 || Integer.parseInt(body.get(i).getMenuNo()) == 6032
                                                || Integer.parseInt(body.get(i).getMenuNo()) == 6033 || Integer.parseInt(body.get(i).getMenuNo()) == 6034
                                                || Integer.parseInt(body.get(i).getMenuNo()) == 6036) {
                                            items1.add(item1);
                                            break;
                                        }
                                    }
//
//                                    ///////////?????????
//                                    if (!body.get(i).getMenuNo().equals("")) {
//                                        if (Integer.parseInt(body.get(i).getMenuNo()) > 6020 && Integer.parseInt(body.get(i).getMenuNo()) < 6046) {
//                                            if (Integer.parseInt(body.get(i).getMenuNo()) != 6044 || Integer.parseInt(body.get(i).getMenuNo()) != 6028
//                                                    || Integer.parseInt(body.get(i).getMenuNo()) != 6030 || Integer.parseInt(body.get(i).getMenuNo()) != 6031) {
//                                                items1.add(item1);
//
//                                                break;
//                                            }
//                                        }
//
//                                    }
                                        ////////??????
//                                        if (!body.get(i).getMenuNo().equals("") && Integer.parseInt(body.get(i).getMenuNo()) > 6043) {
//                                            items1.add(item1);
//                                            break;
//                                        }
//                                        if (!body.get(i).getMenuNo().equals("") && Integer.parseInt(body.get(i).getMenuNo()) == 6028) {
//                                            items1.add(item1);
//                                            break;
//                                        }
//                                        if (!body.get(i).getMenuNo().equals("") && Integer.parseInt(body.get(i).getMenuNo()) > 6043) {
//
//                                        }
//                                        if (body.get(i).getName().equals("??? ??????") || body.get(i).getName().equals("?????? ?????? ??????") || body.get(i).getName().equals("?????? ???????????? ??????")
//                                                || body.get(i).getName().equals("????????? ??????") || body.get(i).getName().equals("????????? ??????") || body.get(i).getName().equals("?????? ??????")
//                                                || body.get(i).getName().equals("????????????") || body.get(i).getName().equals("????????????")) {
//                                            items1.add(item1);
//                                            break;
//                                        }
                                    }
//                        }

                                    // ?????? ????????? ????????? ??????
//                        ArrayList<OrderModel> body = new ArrayList<>();
//                        body = printOrderModel.getOrder();
//                        OrderRecyclerItem item1 = new OrderRecyclerItem(
//                                printOrderModel.getTable(),
//                                printOrderModel.getOrder(),
//                                item.getKey(),
//                                printOrderModel.getPrice(),
//                                printOrderModel.getOrderCheck(),
//                                printOrderModel.getTime(),
//                                printOrderModel.getMenuNo());
//                        for (int i = 0; i < body.size(); i++) {
//                            if (!body.get(i).getMenuNo().equals("") && 6042 > Integer.parseInt(body.get(i).getMenuNo()) && Integer.parseInt(body.get(i).getMenuNo()) > 6020) {
//                                items1.add(item1);
//                                break;
//                            }
////                            if (body.get(i).getName().equals("????????????")) {
////                                items1.add(item1);
////                                break;
////                            }
//                        }
//                        print(printOrderModel);
//                        printOrderModel.setPrintStatus("o");
//                        FirebaseDatabase.getInstance().getReference().child("order").child(pref.getString("storename", "")).child(time2).child(item.getKey()).setValue(printOrderModel);
                                }
                            }
//                        }
                    }
                }
                Log.d("daon_test", "itemz = "+items1.size());
                if (count < items1.size()){
                    count = items1.size();
                    adapter = new OrderRecyclerAdapter(context, items1);
                    recyclerView.setAdapter(adapter);
                    if (count > 0) {
                        startBell();
                    }
                }else{
                    count = items1.size();
                    adapter = new OrderRecyclerAdapter(context, items1);
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void startBell() {
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.bell2);
        mediaPlayer.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }

    public void test() {
        Intent intent = new Intent(context, testActivity.class);
        activity.startActivity(intent);

    }
    public void setPayment(String amount, String type, String vanTr, String cardNo, String prevAuthNum, String prevAuthDate) {
        HashMap<String, byte[]> paymentHash = new HashMap<String, byte[]>();
        Log.d("daon_test", "amount = "+amount);
        // ?????? ????????????
        paymentHash.put("TelegramType", "0200".getBytes());                                 // ?????? ?????? ,  ??????(0200) ??????(0420)
        paymentHash.put("DPTID", "AT0296742A".getBytes());                                  // ??????????????? , ????????????????????? DPT0TEST03
        paymentHash.put("PosEntry", "S".getBytes());                                        // Pos Entry Mode , ??????????????? ?????? ??? ?????????????????? 'K'??????
        paymentHash.put("PayType", "00".getBytes());                                        // [??????]???????????????(default '00') [??????]???????????????
        paymentHash.put("TotalAmount", getStrMoneyAmount(amount));                          // ?????????
        paymentHash.put("Amount", getStrMoneyAmount(amount));                               // ???????????? = ????????? - ????????? - ?????????
        paymentHash.put("ServiceAmount" ,getStrMoneyAmount(amount));                        // ?????????
        paymentHash.put("TaxAmount", getStrMoneyAmount("0"));                               // ?????????
        paymentHash.put("FreeAmount", getStrMoneyAmount("0"));                              // ?????? 0??????  / ?????? 1004?????? ?????? ????????? 1004??? ?????????(ServiceAmount),?????????(TaxAmount) 0??? ???????????? 1004???/ ??????(FreeAmount)  1004???
        paymentHash.put("AuthNum", "".getBytes());                                          // ????????? ???????????? , ??????????????? ??????
        paymentHash.put("Authdate", "".getBytes());                                         // ????????? ???????????? , ??????????????? ??????
        paymentHash.put("Filler", "".getBytes());                                           // ???????????? - ????????? ??????????????? ????????????
        paymentHash.put("SignTrans", "N".getBytes());                                       // ???????????? ??????, ?????????(N) 50000??? ????????? ?????? "N" => "S"?????? ??????
        if (Long.parseLong(amount) > 50000) {
            paymentHash.put("SignTrans", "S".getBytes());                                   // ???????????? ??????, ?????????(N) 50000??? ????????? ?????? "N" => "S"?????? ??????
        }

        paymentHash.put("PlayType", "D".getBytes());                                        // ????????????,  ??????????????? ?????????(D)
        paymentHash.put("CardType", "".getBytes());                                         // ???????????? ???????????? (?????? ????????????), "" ??????
        paymentHash.put("BranchNM", "".getBytes());                                         // ???????????? ,?????? ?????? ?????????????????? ?????? , ????????? "" ??????
        paymentHash.put("BIZNO", "135-88-01055".getBytes());                                // ??????????????? ,KSNET ?????? ????????? ????????????????????? ??????, ?????? ???"" ??????
        paymentHash.put("TransType", "".getBytes());                                        // "" ??????
        paymentHash.put("AutoClose_Time", "30".getBytes());                                 // ????????? ?????? ?????? ??? ?????? ?????? ex)30??? ??? ??????

        //?????? ????????????
//        paymentHash.put("SubBIZNO", "".getBytes());                                         // ?????? ??????????????? ,??????????????? ??????????????? ?????? ??? ????????? ??????
//        paymentHash.put("Device_PortName", "/dev/bus/usb/001/002".getBytes());              //????????? ?????? ?????? ?????? ??? UsbDevice ??????????????? getDeviceName() ??????????????? , ?????????????????? ????????????
//        paymentHash.put("EncryptSign", "A!B@C#D4".getBytes());                              // SignTrans "T"????????? KSCIC?????? ?????? ???????????? ?????? ?????????????????? ????????????, ??????????????????

        ComponentName compName = new ComponentName("ks.kscic_ksr01", " ks.kscic_ksr01.PaymentDlg");
//        Intent intent = new Intent(Intent.ACTION_MAIN);

        if (type.equals("credit")) {
            paymentHash.put("ReceiptNo", "X".getBytes());                                   // ??????????????? ????????????, ???????????? ??? "X", ??????????????? ??????????????? "", Key-In????????? "??????????????? ??? ??????" -> Pos Entry Mode 'K;
        } else if (type.equals("cancel")) {
            paymentHash.put("TelegramType", "0420".getBytes());                             // ?????? ?????? ,  ??????(0200) ??????(0420)
            paymentHash.put("ReceiptNo", "X".getBytes());                                   // ??????????????? ????????????, ???????????? ??? "X", ??????????????? ??????????????? "", Key-In????????? "??????????????? ??? ??????" -> Pos Entry Mode 'K;
            paymentHash.put("AuthNum", prevAuthDate.getBytes());
            paymentHash.put("AuthDate", prevAuthDate.getBytes());
        } else if (type.equals("cancelNoCard")) {
            paymentHash.put("TelegramType", "0420".getBytes());                             // ?????? ?????? ,  ??????(0200) ??????(0420)
            paymentHash.put("ReceiptNo", "X".getBytes());                                   // ??????????????? ????????????, ???????????? ??? "X", ??????????????? ??????????????? "", Key-In????????? "??????????????? ??? ??????" -> Pos Entry Mode 'K;
            paymentHash.put("VanTr", vanTr.getBytes());                                     // ?????????????????? , ????????? ????????? ?????? ?????? ??????
            paymentHash.put("CardBin", cardNo.getBytes());
            paymentHash.put("AuthNum", prevAuthNum.getBytes());
            paymentHash.put("AuthDate", prevAuthDate.getBytes());
            Log.d("HASH", "VanTr: " + paymentHash.get("VanTr").toString());
            Log.d("HASH", "CardBin: " + paymentHash.get("CardBin").toString());
            Log.d("HASH", "AuthNum: " + paymentHash.get("AuthNum").toString());
            Log.d("HASH", "AuthDate: " + paymentHash.get("AuthDate").toString());

        }
        isPrinter isPrinter = new isPrinter();
        Sam4sPrint sam4sPrint = new Sam4sPrint();

        sam4sPrint = isPrinter.setPrinter2();


        Sam4sBuilder builder = new Sam4sBuilder("ELLIX30", Sam4sBuilder.LANG_KO);
        try {
            // top
            builder.addTextAlign(Sam4sBuilder.ALIGN_CENTER);
            builder.addFeedLine(2);
            builder.addTextBold(true);
            builder.addTextSize(2,1);
            builder.addText("????????????");
            builder.addFeedLine(1);
            builder.addTextBold(false);
            builder.addTextSize(1,1);
            builder.addTextAlign(Sam4sBuilder.ALIGN_LEFT);
            builder.addText("[?????????]");
            builder.addFeedLine(1);
            builder.addText(prevAuthDate);
            builder.addFeedLine(1);
            builder.addText("???????????? ?????????");
            builder.addFeedLine(1);
            builder.addText(" ?????????\t");
            builder.addText("651-81-00773 \t");
            builder.addText("Tel : 064-764-2334");
            builder.addFeedLine(1);
            builder.addText("????????????????????? ???????????? ????????? ?????????????????? 191");
            builder.addFeedLine(1);
            // body
            builder.addText("------------------------------------------");
            builder.addFeedLine(1);
            builder.addText("TID:\t");
            builder.addText("AT0296742A \t");
            builder.addText("A-0000 \t");
            builder.addText("0017");
            builder.addFeedLine(1);
//            builder.addText("????????????: ");
//            builder.addTextSize(2,1);
//            builder.addTextBold(true);
//            builder.addText(prevCardNo);
            builder.addTextSize(1,1);
            builder.addTextBold(false);
            builder.addFeedLine(1);
            builder.addText("????????????: ");
            builder.addText(paymentHash.get("CardBin").toString());
            builder.addFeedLine(1);
            builder.addTextPosition(0);
            builder.addText("????????????: ");
            builder.addText(prevAuthDate);
            builder.addTextPosition(65535);
            builder.addText("(?????????)");
            builder.addFeedLine(1);
            builder.addText("------------------------------------------");
            builder.addFeedLine(2);
            //menu
            DecimalFormat myFormatter = new DecimalFormat("###,###");

            builder.addText("------------------------------------------");
            builder.addFeedLine(1);
            // footer
            builder.addTextAlign(Sam4sBuilder.ALIGN_LEFT);
            builder.addText("IC??????");
            builder.addTextPosition(120);
//                builder.addText("???  ??? : ");
//                //builder.addTextPosition(400);
//                int a = (Integer.parseInt(price))/10;
//                builder.addText(myFormatter.format(a*9)+"???");
//                builder.addFeedLine(1);
//                builder.addText("DDC?????????");
//                builder.addTextPosition(120);
//                builder.addText("????????? : ");
//                builder.addText(myFormatter.format(a*1)+"???");
//                builder.addFeedLine(1);
//                builder.addTextPosition(120);
            builder.addText("???  ??? : ");
            builder.addTextSize(2,1);
            builder.addTextBold(true);
            builder.addText(myFormatter.format(Integer.parseInt(amount))+"???");
            builder.addFeedLine(1);
            builder.addTextSize(1,1);
            builder.addTextPosition(120);
            builder.addText("??????No : ");
            builder.addTextBold(true);
            builder.addTextSize(2,1);
            builder.addText(prevAuthNum);
            builder.addFeedLine(1);
            builder.addTextBold(false);
            builder.addTextSize(1,1);
//            builder.addText("???????????? : ");
//            builder.addText(prevCardNo);
            builder.addFeedLine(1);
            builder.addText("??????????????? : ");
            builder.addText("AT0296742A");
            builder.addFeedLine(1);
            builder.addText("?????????????????? : ");
            builder.addText(vanTr);
            builder.addFeedLine(1);
            builder.addText("------------------------------------------");
            builder.addFeedLine(1);
            builder.addTextAlign(Sam4sBuilder.ALIGN_CENTER);
            builder.addText("???????????????.");
            builder.addCut(Sam4sBuilder.CUT_FEED);
            Thread.sleep(200);
            sam4sPrint.sendData(builder);
            isPrinter.closePrint1(sam4sPrint);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(compName);
        intent.putExtra("AdminHash", paymentHash);
        getActivity().startActivityForResult(intent, 0);
    }
    public byte[] getStrMoneyAmount(String money) {
        byte[] amount = null;
        if (money.length() == 0) {
            return "000000001004".getBytes();
        } else {
            Long longMoney = Long.parseLong(money.replace(",", ""));
            money = String.format("%012d", longMoney);
            amount = money.getBytes();
            return amount;
        }
    }
}
