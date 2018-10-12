package com.bingor.picker.datetimepickerdialog.util;

import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

public class DateTimePickerUtil {

    public static void setNumberPickerEditable(ViewGroup np, boolean flag) {
        if (np == null) {
            return;
        }
        for (int i = 0; i < np.getChildCount(); i++) {
            View child = np.getChildAt(i);
            if (child instanceof EditText) {
                EditText et = (EditText) np.getChildAt(i);
//                et.setEnabled(false);
                et.setFocusable(flag);
                break;
            } else if (child instanceof ViewGroup) {
                setNumberPickerEditable((ViewGroup) child, flag);
            }
        }
    }

    public static void setTimePickerDividerColor(TimePicker timePicker,
                                                 int color) {
        // Divider changing:
        // boolean flag = false;

        for (int i = 0; i < timePicker.getChildCount(); i++) {
            // 获取 mSpinners
            if (timePicker.getChildAt(i) instanceof LinearLayout) {
                LinearLayout llFirst = (LinearLayout) timePicker.getChildAt(i);
                for (int j = 0; j < llFirst.getChildCount(); j++) {
                    if (llFirst.getChildAt(j) instanceof LinearLayout) {
                        // 获取 NumberPicker
                        LinearLayout mSpinners = (LinearLayout) llFirst
                                .getChildAt(j);
                        for (int k = 0; k < mSpinners.getChildCount(); k++) {
                            if (mSpinners.getChildAt(k) instanceof NumberPicker) {
                                NumberPicker np = (NumberPicker) mSpinners
                                        .getChildAt(k);
                                setNumberPickerDividerColor(np, color);
                            }
                        }
                        // flag = true;
                        // break;
                    } else if (llFirst.getChildAt(j) instanceof NumberPicker) {
                        NumberPicker np = (NumberPicker) llFirst.getChildAt(j);
                        setNumberPickerDividerColor(np, color);
                    }
                }
            } else if (timePicker.getChildAt(i) instanceof NumberPicker) {
                NumberPicker np = (NumberPicker) timePicker.getChildAt(i);
                setNumberPickerDividerColor(np, color);
            }
            // if (flag) {
            // break;
            // }
        }

    }

    public static void setNumberPickerDividerColor(NumberPicker np, int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        boolean flag = false;
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(np, new ColorDrawable(color));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (flag) {
                    break;
                }
                flag = true;
            }
            if (pf.getName().equals("mSelectionDividerHeight")) {
                pf.setAccessible(true);
                try {
                    pf.set(np, 3);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (flag) {
                    break;
                }
                flag = true;
            }
        }
    }

}
