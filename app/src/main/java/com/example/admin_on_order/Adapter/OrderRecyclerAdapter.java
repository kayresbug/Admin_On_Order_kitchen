package com.example.admin_on_order.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_on_order.OrderDialog;
import com.example.admin_on_order.R;
import com.example.admin_on_order.model.OrderModel;
import com.example.admin_on_order.model.OrderRecyclerItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.OrderViewHolder> {

    private final ArrayList<OrderRecyclerItem> items;
    Context context;
    View view;
    OrderDialog orderDialog;
    String fullMenuName;

    public OrderRecyclerAdapter(Context context, ArrayList<OrderRecyclerItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_order_item, parent, false);

        OrderViewHolder viewHolder = new OrderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.tableNo.setText(items.get(position).getTableNo());
        ArrayList<OrderModel> body = new ArrayList<>();
        body = items.get(position).getMenu();
//        holder.menu.setText(body.get(0).getName());

        String test = "";

        for (int i = 0; i < body.size(); i++) {
            Log.d("daon_test", "adf = " + body.get(i).getMenuNo());
            if (!body.get(i).getMenuNo().equals("")) {
                if (Integer.parseInt(body.get(i).getMenuNo()) > 6000 && Integer.parseInt(body.get(i).getMenuNo()) < 6021) {
                    if (i == body.size() - 1) {
                        test += body.get(i).getName() + " X " + body.get(i).getCount();
                    } else {
                        test += body.get(i).getName() + " X " + body.get(i).getCount() + "\n";
                    }
                }
                if (Integer.parseInt(body.get(i).getMenuNo()) == 6031 || Integer.parseInt(body.get(i).getMenuNo()) == 6032
                        || Integer.parseInt(body.get(i).getMenuNo()) == 6033 || Integer.parseInt(body.get(i).getMenuNo()) == 6034
                        || Integer.parseInt(body.get(i).getMenuNo()) == 6036) {
                    if (i == body.size() - 1) {
                        test += body.get(i).getName() + " X " + body.get(i).getCount();
                    } else {
                        test += body.get(i).getName() + " X " + body.get(i).getCount() + "\n";
                    }
                }
//
//                ///////////사이드
//                if (Integer.parseInt(body.get(i).getMenuNo()) > 6020 && Integer.parseInt(body.get(i).getMenuNo()) < 6046) {
//                    if (Integer.parseInt(body.get(i).getMenuNo()) != 6044 || Integer.parseInt(body.get(i).getMenuNo()) != 6028
//                            || Integer.parseInt(body.get(i).getMenuNo()) != 6030 || Integer.parseInt(body.get(i).getMenuNo()) != 6031) {
//                        if (i == body.size() - 1) {
//                            test += body.get(i).getName() + " X " + body.get(i).getCount();
//                        } else {
//                            test += body.get(i).getName() + " X " + body.get(i).getCount() + "\n";
//                        }
//                    }
//                }
            }

            // 고기 사이드 제외한 모든 메뉴

//            if (!body.get(i).getMenuNo().equals("") && Integer.parseInt(body.get(i).getMenuNo()) > 6043) {
//                if (i == body.size() - 1) {
//                    test += body.get(i).getName() + " X " + body.get(i).getCount();
//                } else {
//                    test += body.get(i).getName() + " X " + body.get(i).getCount() + "\n";
//                }
//            }
//            if (!body.get(i).getMenuNo().equals("") && Integer.parseInt(body.get(i).getMenuNo()) == 6028){
//                if (i == body.size() - 1) {
//                    test += body.get(i).getName() + " X " + body.get(i).getCount();
//                } else {
//                    test += body.get(i).getName() + " X " + body.get(i).getCount() + "\n";
//                }
//            }
//            if (body.get(i).getName().equals("물 요청") || body.get(i).getName().equals("불판 교체 요청") || body.get(i).getName().equals("아이 수저포크 요청")
//                    || body.get(i).getName().equals("앞접시 요청") || body.get(i).getName().equals("국그릇 요청") || body.get(i).getName().equals("국자 요청")
//                    || body.get(i).getName().equals("직원호출") || body.get(i).getName().equals("시간초과")) {
//                test += body.get(i).getName();
//                break;
//            }


        }


        holder.menu.setText(test);
        Log.d("TTT", "onBindViewHolder: " + holder.menu.getText());
//        fullMenuName = items.get(position).getMenu();
//
//        Log.d("menuText", "onBindViewHolder: " + items.get(position).getMenu().indexOf("n"));
//        if (items.get(position).getMenu().contains("n")) {
//            Log.d("error point", "point");
//            Log.d("authnum", "onBindViewHolder: " + items.get(position).getAuthNum());
//            Log.d("MenuName", "onBindViewHolder: " + items.get(position).getMenu());
//            holder.menu.setText(items.get(position).getMenu().substring(0, items.get(position).getMenu().indexOf("\\")).replace("n", "") + " ...");
//        } else if (items.get(position).getMenu().contains("n  ")) {
//            // error point2
//            Log.d("error point2", "point 2 ");
//            holder.menu.setText(items.get(position).getMenu().substring(0, items.get(position).getMenu().indexOf("\\")).replace("n", "") + " ...");
//        }
////      error point
//        Log.d("OVERLENGTH", "onBindViewHolder: " + items.get(position).getMenu().length());

//        holder.paymentStatus.setText(items.get(position).getPaymentStatus());
//        if (items.get(position).getPaymentStatus().equals("결제완료")) {
//
//        }

        holder.checkStatus.setText(items.get(position).getCheckStatus());

        // total price calculator
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
//        int intTotalPrice = Integer.parseInt(items.get(position).getPrice());
//        holder.totalPrice.setText(decimalFormat.format(intTotalPrice) + "원");
//        Log.d("intTotalPrice", "onBindViewHolder: " + intTotalPrice);

        // Order Dialog
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderDialog = new OrderDialog(view.getContext(), position,
                        items.get(position).getMenu(),
                        items.get(position).getCount(),
                        items.get(position).getPrice(),
                        items.get(position).getTableNo(),
                        items.get(position).getOrderTime(),
                        items.get(position).getCount(), "");
                orderDialog.show();
            }
        });

        holder.orderTime.setText(items.get(position).getOrderTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tableNo;
        TextView menu;
        TextView paymentStatus;
        TextView paymentType;
        TextView totalPrice;
        TextView orderTime;
        LinearLayout layout;
        TextView checkStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            this.checkStatus = itemView.findViewById(R.id.order_check_status);
            this.tableNo = itemView.findViewById(R.id.order_table_no);
            this.menu = itemView.findViewById(R.id.order_menu);
            this.paymentStatus = itemView.findViewById(R.id.order_payment_status);
            this.paymentType = itemView.findViewById(R.id.order_payment_type);
            this.totalPrice = itemView.findViewById(R.id.order_total_price);
            this.orderTime = itemView.findViewById(R.id.order_time);
            this.layout = itemView.findViewById(R.id.order_recycler_layout);
        }
    }
}
