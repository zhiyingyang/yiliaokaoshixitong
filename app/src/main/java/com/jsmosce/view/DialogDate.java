package com.jsmosce.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.jsmosce.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DialogDate extends AlertDialog implements OnMonthChangedListener {

    private Context context;
    private MaterialCalendarView materialCalendarView;
    private OnDateSelectedListener onDateSelectedListener;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private List<String> dateString = new ArrayList<String>();
    private static boolean[] PRIME_TABLE = {
            false,  // 0?
            false,
            true, // 2
            true, // 3
            false,
            true, // 5
            false,
            true, // 7
            false,
            false,
            false,
            true, // 11
            false,
            true, // 13
            false,
            false,
            false,
            true, // 17
            false,
            true, // 19
            false,
            false,
            false,
            true, // 23
            false,
            false,
            false,
            false,
            false,
            true, // 29
            false,
            true, // 31
            false,
            false,
            false, //PADDING
    };

    public DialogDate(Context context, OnDateSelectedListener onDateSelectedListener) {
        //super(context);
        super(context, R.style.Dialog);
        this.context = context;
        this.onDateSelectedListener = onDateSelectedListener;

    }

    public void upDate(List<String> dateStrings) {
        this.dateString = dateStrings;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_date, null);
        this.setContentView(layout);
        materialCalendarView = (MaterialCalendarView) layout.findViewById(R.id.calendarView);
        materialCalendarView.addDecorator(new PrimeDayDisableDecorator());
        materialCalendarView.setOnDateChangedListener(onDateSelectedListener);
        materialCalendarView.setOnMonthChangedListener(this);
    }

    public MaterialCalendarView getMaterialCalendarView() {
        return materialCalendarView;
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

        materialCalendarView.addDecorator(new PrimeDayDisableDecorator());

    }

    private class PrimeDayDisableDecorator implements DayViewDecorator {
        /**
         * 需要实现效果的天数返回true
         *
         * @param day {@linkplain CalendarDay} to possibly decorate
         * @return
         */
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            //在当前时间之前的都禁用
            if (day.getDate().before(new Date())) {
                return true;
            }
            //在判断是否禁用
            for (String date : dateString) {
                try {
                    if (day.getDate().equals(sdf.parse(date))) {
                        return true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        /**
         * 上面方法返回true的天，会设置无法选择
         *
         * @param view View to decorate
         */
        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }


    }
}
