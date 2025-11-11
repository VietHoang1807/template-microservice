package com.kk.common_lib.util;

import com.kk.common_lib.constant.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NumberCommon {

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.COMMON);

    public static Long convertToLong(Object value) {
        if (value == null || StringCommon.isNullOrBlank((String) value)) {
            return null;
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            LOGGER.error("Exception when converting ({}) to Long value: ", value.toString(), e);
            return null;
        }
    }

    public static Integer convertToInteger(Object value) {
        if (value == null || StringCommon.isNullOrBlank((String) value)) {
            return null;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (NumberFormatException e) {
            LOGGER.error("Exception when converting ({}) to Integer value. Set value default zero: ", value.toString(), e);
            return 0;
        }
    }

    public static Float convertToFloat(Object value) {
        if (value == null || StringCommon.isNullOrBlank((String) value)) {
            return null;
        }
        try {
            return Float.parseFloat(value.toString());
        } catch (NumberFormatException e) {
            LOGGER.error("Exception when converting ({}) to Float value: ", value.toString(), e);
            return null;
        }
    }

    public static Double convertToDouble(Object value) {
        if (value == null || StringCommon.isNullOrBlank((String) value)) {
            return null;
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            LOGGER.error("Exception when converting ({}) to Double value: ", value.toString(), e);
            return null;
        }
    }

    /**
     * convert object to long
     *
     * @param o            object
     * @param defaultValue if  o  null or empty function return defaultValue
     * @return long
     */
    static public long toLong(Object o, long defaultValue) {
        try {
            return switch(o) {
                case Long l -> l;
                case Integer i -> i.longValue();
                case Float f -> f.longValue();
                case Double d -> d.longValue();
                case String s -> Long.parseLong(s);
                default -> defaultValue;
            };
        } catch (Exception e) {
            LOGGER.error("Exception: ObjectConvertor ->   toLong {}", e.getMessage());
        }
        return defaultValue;
    }

    /**
     * convert object to int
     *
     * @param o            object
     * @param defaultValue if  o  null or empty function return defaultValue
     * @return int
     */
    static public int toInt(Object o, int defaultValue) {
        try {
            return switch(o) {
                case Long l -> l.intValue();
                case Integer i -> i;
                case Float f -> f.intValue();
                case Double d -> d.intValue();
                case String s -> Integer.parseInt(s);
                default -> defaultValue;
            };
        } catch (Exception e) {
            LOGGER.error("Exception: ObjectConvertor ->  toInt {}", e.getMessage());
        }
        return defaultValue;
    }

    /**
     * @param number
     * @param min
     * @param max
     * @return
     * @throws IllegalArgumentException if (number == null || (min == null && max == null) || (max !=
     *                                  null && min != null && max.doubleValue() <
     *                                  min.doubleValue()))
     */
    public static boolean isBetweenRange(Number number, Number min, Number max) {

        if (number == null || (min == null && max == null)
                || (max != null && min != null && max.doubleValue() < min.doubleValue())) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        return !((min == null && number.doubleValue() > max.doubleValue())
                || (max == null && number.doubleValue() < min.doubleValue()) || (min != null && max != null
                && (number.doubleValue() < min.doubleValue() || number.doubleValue() > max.doubleValue())));
    }

    public static String formatCurrencyNumber(double amount) {
        try {
            return String.format("%,.0f", amount);
        } catch (Exception e) {
            LOGGER.error("Exception when formatCurrencyNumber({}) ", amount, e);
        }
        return null;
    }

    public static int getRandomBetweenRange(int min, int max) {
        return min + new Random().nextInt(max);
    }

    public static long getRandomBetweenRange(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    public static long getAuditNumber() {
        return convertToLong(DateCommon.convertDateToStringByPattern(new Date(), "yyMMdd") + getRandomBetweenRange(100000000000L, 999999999999L));
    }

    /**
     * Converts a specific long number into the vietnamese words
     *
     * @param number
     * @return the vietnamese words represents for the given long number
     */
    public static String convertLongToVietnameseWords(final long number) {
        if (number == 0l) {
            return "Không đồng chẵn";
        }
        final String[] so = new String[]
                {"không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
        final String[] hang = new String[]
                {"", "nghìn", "triệu", "tỷ"};
        final String s = String.valueOf(number < 0 ? number * (-1) : number);
        int i = s.length(), j, donvi, chuc, tram;
        StringBuilder str = new StringBuilder();
        j = 0;
        while (i > 0) {
            donvi = Integer.parseInt(s.substring(i - 1, i));
            i--;
            if (i > 0) {
                chuc = Integer.parseInt(s.substring(i - 1, i));
            } else {
                chuc = -1;
            }
            i--;
            if (i > 0) {
                tram = Integer.parseInt(s.substring(i - 1, i));
            } else {
                tram = -1;
            }
            i--;
            if ((donvi > 0) || (chuc > 0) || (tram > 0) || (j == 3)) {
                str.insert(0, hang[j]);
            }
            j++;
            if (j > 3) {
                j = 1;
            }
            if ((donvi == 1) && (chuc > 1)) {
                str.insert(0, "một ");
            } else {
                if ((donvi == 5) && (chuc > 0)) {
                    str.insert(0, "lăm ");
                } else if (donvi > 0) {
                    str.insert(0, so[donvi] + " ");
                }
            }
            if (chuc < 0) {
                break;
            } else {
                if ((chuc == 0) && (donvi > 0)) {
                    str.insert(0, "lẻ ");
                }
                if (chuc == 1) {
                    str.insert(0, "mười ");
                }
                if (chuc > 1) {
                    str.insert(0, so[chuc] + " mươi ");
                }
            }
            if (tram < 0) {
                break;
            } else {
                if ((tram > 0) || (chuc > 0) || (donvi > 0)) {
                    str.insert(0, so[tram] + " trăm ");
                }
            }
            str.insert(0, " ");
        }
        if (number < 0) {
            str.insert(0, "Âm ");
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    public static boolean isNullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }
    public static boolean isNullOrZero(BigDecimal value) {
        return (value == null || value.compareTo(BigDecimal.ZERO) == 0);
    }
}
