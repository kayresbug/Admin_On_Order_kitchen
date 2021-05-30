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

                                    //  테이블 번호에 따라서 구분
//                        if (printOrderModel.getTable().equals("17") || printOrderModel.getTable().equals("18") || printOrderModel.getTable().equals("19") || printOrderModel.getTable().equals("20")
//                                || printOrderModel.getTable().equals("21") || printOrderModel.getTable().equals("22") || printOrderModel.getTable().equals("23")
//                                || printOrderModel.getTable().equals("룸1") || printOrderModel.getTable().equals("룸2") || printOrderModel.getTable().equals("룸3")) {
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
                                        ///////////육류
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
//                                    ///////////사이드
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
                                        ////////그외
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
//                                        if (body.get(i).getName().equals("물 요청") || body.get(i).getName().equals("불판 교체 요청") || body.get(i).getName().equals("아이 수저포크 요청")
//                                                || body.get(i).getName().equals("앞접시 요청") || body.get(i).getName().equals("국그릇 요청") || body.get(i).getName().equals("국자 요청")
//                                                || body.get(i).getName().equals("직원호출") || body.get(i).getName().equals("시간초과")) {
//                                            items1.add(item1);
//                                            break;
//                                        }
                                    }
//                        }

                                    // 메뉴 번호에 따라서 구분
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
////                            if (body.get(i).getName().equals("직원호출")) {
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
        // 고정 사용필드
        paymentHash.put("TelegramType", "0200".getBytes());                                 // 전문 구분 ,  승인(0200) 취소(0420)
        paymentHash.put("DPTID", "AT0296742A".getBytes());                                  // 단말기번호 , 테스트단말번호 DPT0TEST03
        paymentHash.put("PosEntry", "S".getBytes());                                        // Pos Entry Mode , 현금영수증 거래 시 키인거래에만 'K'사용
        paymentHash.put("PayType", "00".getBytes());                                        // [신용]할부개월수(default '00') [현금]거래자구분
        paymentHash.put("TotalAmount", getStrMoneyAmount(amount));                          // 총금액
        paymentHash.put("Amount", getStrMoneyAmount(amount));                               // 공급금액 = 총금액 - 부가세 - 봉사료
        paymentHash.put("ServiceAmount" ,getStrMoneyAmount(amount));                        // 부가세
        paymentHash.put("TaxAmount", getStrMoneyAmount("0"));                               // 봉사료
        paymentHash.put("FreeAmount", getStrMoneyAmount("0"));                              // 면세 0처리  / 면세 1004원일 경우 총금액 1004원 봉사료(ServiceAmount),부가세(TaxAmount) 0원 공급금액 1004원/ 면세(FreeAmount)  1004원
        paymentHash.put("AuthNum", "".getBytes());                                          // 원거래 승인번호 , 취소시에만 사용
        paymentHash.put("Authdate", "".getBytes());                                         // 원거래 승인일자 , 취소시에만 사용
        paymentHash.put("Filler", "".getBytes());                                           // 여유필드 - 판매차 필요시에만 입력처리
        paymentHash.put("SignTrans", "N".getBytes());                                       // 서명거래 필드, 무서명(N) 50000원 초과시 서명 "N" => "S"변경 필수
        if (Long.parseLong(amount) > 50000) {
            paymentHash.put("SignTrans", "S".getBytes());                                   // 서명거래 필드, 무서명(N) 50000원 초과시 서명 "N" => "S"변경 필수
        }

        paymentHash.put("PlayType", "D".getBytes());                                        // 실행구분,  데몬사용시 고정값(D)
        paymentHash.put("CardType", "".getBytes());                                         // 은련선택 여부필드 (현재 사용안함), "" 고정
        paymentHash.put("BranchNM", "".getBytes());                                         // 가맹점명 ,관련 개발 필요가맹점만 입력 , 없을시 "" 고정
        paymentHash.put("BIZNO", "135-88-01055".getBytes());                                // 사업자번호 ,KSNET 서버 정의된 가맹정일경우만 사용, 없을 시"" 고정
        paymentHash.put("TransType", "".getBytes());                                        // "" 고정
        paymentHash.put("AutoClose_Time", "30".getBytes());                                 // 사용자 동작 없을 시 자동 종료 ex)30초 후 종료

        //선택 사용필드
//        paymentHash.put("SubBIZNO", "".getBytes());                                         // 하위 사업자번호 ,하위사업자 현금영수증 승인 및 취소시 적용
//        paymentHash.put("Device_PortName", "/dev/bus/usb/001/002".getBytes());              //리더기 포트 설정 필요 시 UsbDevice 인스턴스의 getDeviceName() 리턴값입력 , 필요없을경우 생략가능
//        paymentHash.put("EncryptSign", "A!B@C#D4".getBytes());                              // SignTrans "T"일경우 KSCIC에서 서명 받지않고 해당 사인데이터로 승인진행, 특정업체사용

        ComponentName compName = new ComponentName("ks.kscic_ksr01", " ks.kscic_ksr01.PaymentDlg");
//        Intent intent = new Intent(Intent.ACTION_MAIN);

        if (type.equals("credit")) {
            paymentHash.put("ReceiptNo", "X".getBytes());                                   // 현금영수증 거래필드, 신용결제 시 "X", 현금영수증 카드거래시 "", Key-In거래시 "휴대폰번호 등 입력" -> Pos Entry Mode 'K;
        } else if (type.equals("cancel")) {
            paymentHash.put("TelegramType", "0420".getBytes());                             // 전문 구분 ,  승인(0200) 취소(0420)
            paymentHash.put("ReceiptNo", "X".getBytes());                                   // 현금영수증 거래필드, 신용결제 시 "X", 현금영수증 카드거래시 "", Key-In거래시 "휴대폰번호 등 입력" -> Pos Entry Mode 'K;
            paymentHash.put("AuthNum", prevAuthDate.getBytes());
            paymentHash.put("AuthDate", prevAuthDate.getBytes());
        } else if (type.equals("cancelNoCard")) {
            paymentHash.put("TelegramType", "0420".getBytes());                             // 전문 구분 ,  승인(0200) 취소(0420)
            paymentHash.put("ReceiptNo", "X".getBytes());                                   // 현금영수증 거래필드, 신용결제 시 "X", 현금영수증 카드거래시 "", Key-In거래시 "휴대폰번호 등 입력" -> Pos Entry Mode 'K;
            paymentHash.put("VanTr", vanTr.getBytes());                                     // 거래고유번호 , 무카드 취소일 경우 필수 필드
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
            builder.addText("신용취소");
            builder.addFeedLine(1);
            builder.addTextBold(false);
            builder.addTextSize(1,1);
            builder.addTextAlign(Sam4sBuilder.ALIGN_LEFT);
            builder.addText("[고객용]");
            builder.addFeedLine(1);
            builder.addText(prevAuthDate);
            builder.addFeedLine(1);
            builder.addText("주식회사 다모앙");
            builder.addFeedLine(1);
            builder.addText(" 안영찬\t");
            builder.addText("651-81-00773 \t");
            builder.addText("Tel : 064-764-2334");
            builder.addFeedLine(1);
            builder.addText("제주특별자치도 서귀포시 남원읍 남원체육관로 191");
            builder.addFeedLine(1);
            // body
            builder.addText("------------------------------------------");
            builder.addFeedLine(1);
            builder.addText("TID:\t");
            builder.addText("AT0296742A \t");
            builder.addText("A-0000 \t");
            builder.addText("0017");
            builder.addFeedLine(1);
//            builder.addText("카드종류: ");
//            builder.addTextSize(2,1);
//            builder.addTextBold(true);
//            builder.addText(prevCardNo);
            builder.addTextSize(1,1);
            builder.addTextBold(false);
            builder.addFeedLine(1);
            builder.addText("카드번호: ");
            builder.addText(paymentHash.get("CardBin").toString());
            builder.addFeedLine(1);
            builder.addTextPosition(0);
            builder.addText("거래일시: ");
            builder.addText(prevAuthDate);
            builder.addTextPosition(65535);
            builder.addText("(일시불)");
            builder.addFeedLine(1);
            builder.addText("------------------------------------------");
            builder.addFeedLine(2);
            //menu
            DecimalFormat myFormatter = new DecimalFormat("###,###");

            builder.addText("------------------------------------------");
            builder.addFeedLine(1);
            // footer
            builder.addTextAlign(Sam4sBuilder.ALIGN_LEFT);
            builder.addText("IC승인");
            builder.addTextPosition(120);
//                builder.addText("금  액 : ");
//                //builder.addTextPosition(400);
//                int a = (Integer.parseInt(price))/10;
//                builder.addText(myFormatter.format(a*9)+"원");
//                builder.addFeedLine(1);
//                builder.addText("DDC매출표");
//                builder.addTextPosition(120);
//                builder.addText("부가세 : ");
//                builder.addText(myFormatter.format(a*1)+"원");
//                builder.addFeedLine(1);
//                builder.addTextPosition(120);
            builder.addText("합  계 : ");
            builder.addTextSize(2,1);
            builder.addTextBold(true);
            builder.addText(myFormatter.format(Integer.parseInt(amount))+"원");
            builder.addFeedLine(1);
            builder.addTextSize(1,1);
            builder.addTextPosition(120);
            builder.addText("승인No : ");
            builder.addTextBold(true);
            builder.addTextSize(2,1);
            builder.addText(prevAuthNum);
            builder.addFeedLine(1);
            builder.addTextBold(false);
            builder.addTextSize(1,1);
//            builder.addText("매입사명 : ");
//            builder.addText(prevCardNo);
            builder.addFeedLine(1);
            builder.addText("가맹점번호 : ");
            builder.addText("AT0296742A");
            builder.addFeedLine(1);
            builder.addText("거래일련번호 : ");
            builder.addText(vanTr);
            builder.addFeedLine(1);
            builder.addText("------------------------------------------");
            builder.addFeedLine(1);
            builder.addTextAlign(Sam4sBuilder.ALIGN_CENTER);
            builder.addText("감사합니다.");
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
