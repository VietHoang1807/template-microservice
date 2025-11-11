package com.kk.common_lib.util;

import com.kk.common_lib.constant.Constant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    public static String getString(Object o) {
        try {
            return o.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public static java.sql.Date getSqlDate(Date date) {
        try {
            return new java.sql.Date(date.getTime());
        } catch (Exception ex) {
            return null;
        }
    }

    public static Date getUtilDate(Timestamp date) {
        try {
            return new Date(date.getTime());
        } catch (Exception ex) {
            return null;
        }
    }
    public static String convertDateToString(Date _date) {
        if (_date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(_date);
    }

    public static String convertDateToString(Date _date, String pattern) {
        if (_date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(_date);
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    private static Map sortByComparator(Map unsortMap) {

        List list = new LinkedList(unsortMap.entrySet());

        // sort list based on comparator
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable)((Map.Entry)(o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });

        // put sorted list into map again
        //LinkedHashMap make sure order in which keys were inserted
        Map sortedMap = new LinkedHashMap();
        for (Object o : list) {
            Map.Entry entry = (Map.Entry) o;
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static boolean copyProperties(Object _obj1, Object _obj2, boolean _ignoreCase) {
        if (_obj2 == null || _obj1 == null)
            return true;
        try {
            List<Field> fields1 = Utils.getAllFields(_obj1.getClass());
            List<Field> fields2 = Utils.getAllFields(_obj2.getClass());
            String[] _exceptFields =
                    { "delBtnDisabled", "updBtnDisabled", "dataTable", "selectedItems", "data", "rnum", "rowcount",
                            "detailInfo", "prefix", "bundle", "dynamicColumns" };
            for (Field field1 : fields1) {
                if (!Modifier.isStatic(field1.getModifiers())) { // 25/04/2013
                    for (Field field2 : fields2) {
                        field1.setAccessible(true);
                        field2.setAccessible(true);
                        if (!StringInArray(_exceptFields, field1.getName())) {
                            if ((field1.getName().equals(field2.getName()) && !_ignoreCase) ||
                                    (_ignoreCase && field1.getName().equalsIgnoreCase(field2.getName()))) {
                                field1.set(_obj1, field2.get(_obj2));
                                break;
                            }
                        }
                    }
                }
            }
            //BeanUtils.copyProperties(_obj1, _obj2);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean copyProperties(Object _obj1, Object _obj2, boolean _ignoreCase,
                                               boolean _whenProperObj1Null) {
        try {
            List<Field> fields1 = Utils.getAllFields(_obj1.getClass());
            List<Field> fields2 = Utils.getAllFields(_obj2.getClass());
            String[] _exceptFields =
                    { "delBtnDisabled", "updBtnDisabled", "dataTable", "selectedItems", "data", "rnum", "rowcount",
                            "detailInfo", "prefix", "bundle", "dynamicColumns" };
            for (Field field1 : fields1) {
                for (Field field2 : fields2) {
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                    if (!StringInArray(_exceptFields, field1.getName())) {
                        if ((field1.getName().equals(field2.getName()) && !_ignoreCase) ||
                                (_ignoreCase && field1.getName().equalsIgnoreCase(field2.getName()))) {
                            if (_whenProperObj1Null && field1.get(_obj1) != null)
                                continue;
                            field1.set(_obj1, field2.get(_obj2));
                            break;
                        }
                    }
                }
            }
            //BeanUtils.copyProperties(_obj1, _obj2);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean StringInArray(String[] arr, String name) {
        for (String item : arr) {
            if ((item == null && name != null) || (item != null && name == null))
                return false;
            else if ((item == null && name == null) || item.equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public static boolean StringIsNullOrEmpty(String str) {
        if (str == null)
            return true;
        str = str.trim();
        return str.equalsIgnoreCase("");
    }

    public static boolean StringEquals(String str1, String str2) {
        if (StringIsNullOrEmpty(str1)) str1 = "";
        if (StringIsNullOrEmpty(str2)) str2 = "";
        return str1.equals(str2);
    }

    public static List<Field> getAllFields(Class _class) {
        Field[] fields_child = _class.getDeclaredFields();
        Class _supperClass = _class.getSuperclass();
        List<Field> fields = new ArrayList<>(Arrays.asList(fields_child));

        while (_supperClass != null) {
            Field[] superFields = _supperClass.getDeclaredFields();
            fields.addAll(Arrays.asList(superFields));
            _supperClass = _supperClass.getSuperclass();
        }
        return fields;
    }

    public static int diffDay(Date a, Date b) {
        int tempDifference = 0;
        int difference = 0;
        Calendar earlier = Calendar.getInstance();
        Calendar later = Calendar.getInstance();

        if (a.compareTo(b) < 0) {
            earlier.setTime(a);
            later.setTime(b);
        } else {
            earlier.setTime(b);
            later.setTime(a);
        }

        while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR)) {
            tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
            difference += tempDifference;

            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR)) {
            tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
            difference += tempDifference;

            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        return difference;
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);

        return c.getTime();
    }

    public static Date getDayOfPreviousYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
        return c.getTime();
    }

    public static long dateDiff(Date a, Date b) {
        return (b.getTime() - a.getTime()) / (1000 * 60 * 60 * 24);
    }

    public static String trimString(String str) {
        if (Utils.StringIsNullOrEmpty(str)) return null;
        return str.trim();
    }

    public static int soNgay(Date a, Date b) {
        float tong = (float) (b.getTime() - a.getTime()) / (1000 * 60 * 60 * 24);
        return ((int)tong) % 30;
    }

    public static int soThang(Date a, Date b) {
        float tong = (float) (b.getTime() - a.getTime()) / (1000 * 60 * 60 * 24);
        return (int) tong / 30;
    }

    public static Date dateAdd(Date value, int month, int day, int year) {
        if (value == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(value);
        cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + month));
        cal.set(Calendar.DATE, (cal.get(Calendar.DATE) + day));
        cal.set(Calendar.YEAR, (cal.get(Calendar.YEAR) + year));
        return cal.getTime();
    }

    public static int getDateDifferenceInDD(Date from, Date to) {
        return getDateDifference(from, to, "dd");
    }

    public static int getDateDifferenceInMM(Date from, Date to) {
        return getDateDifference(from, to, "mm");
    }

    public static int getDateDifferenceInYY(Date from, Date to) {
        return getDateDifference(from, to, "yy");
    }

    public static int getDateDifference(Date from, Date to, String pattern) {
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        fromDate.setTime(from);
        toDate.setTime(to);
        int increment = 0;
        int year, month, day;
        if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH)) {
            increment = fromDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        // DAY CALCULATION
        if (increment != 0) {
            day = (toDate.get(Calendar.DAY_OF_MONTH) + increment) - fromDate.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        } else {
            day = toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
        }

        // MONTH CALCULATION
        if ((fromDate.get(Calendar.MONTH) + increment) > toDate.get(Calendar.MONTH)) {
            month = (toDate.get(Calendar.MONTH) + 12) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 1;
        } else {
            month = (toDate.get(Calendar.MONTH)) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 0;
        }

        // YEAR CALCULATION
        year = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);
        return switch (pattern) {
            case "dd": yield day;
            case "mm": yield month;
            default: yield year;
        };
    }


    public static Map<String, String> removeMapItemByValue(Map<String, String> hm, String value) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (Map.Entry<String, String> stringStringEntry : hm.entrySet()) {
            if (!((Map.Entry<?, ?>) stringStringEntry).getValue().equals(value))
                map.put((String) ((Map.Entry<?, ?>) stringStringEntry).getKey(), (String) ((Map.Entry<?, ?>) stringStringEntry).getValue());
        }
        return map;
    }

    public static String implodeString(List<String> input, String glue) {
        String output = "";

        if (input != null && !input.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(input.getFirst());

            for (int i = 1; i < input.size(); i++) {
                sb.append(glue);
                sb.append(input.get(i));
            }

            output = sb.toString();
        }

        return output;
    }

    public static String implodeString(String[] input, String glue) {
        String output = "";

        if (input != null && input.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(input[0]);

            for (int i = 1; i < input.length; i++) {
                sb.append(glue);
                sb.append(input[i]);
            }

            output = sb.toString();
        }

        return output;
    }
    public static Boolean toBoolean(String input){
        if (input == null) return false;
        input = input.trim().toLowerCase();
        return Arrays.asList("t", "T", "true", "y", "Y", "yes", "YES", "c", "C", "co", "có").contains(input);
    }

    public static String booleanToStr(Boolean input) {
        return input ? Constant.STATUS_OBJECT.ACTIVE_Y
                : Constant.STATUS_OBJECT.INACTIVE_Y;
    }

    /**
     *
     * @param input kiểu dữ liệu dang "Y" hoặc "false" | "true"
     * @return
     */
    public static String strToStr(String input) {
        return booleanToStr(toBoolean(input));
    }
}
