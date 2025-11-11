package com.kk.common_lib.constant;

/*
 * Declare constants used in the project
 */
public interface Constant {
    //file config in lib_common
    String FILE_CONFIG = "config_common.properties";
    String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    String CONTENT_TYPE_APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    String UTF_8 = "UTF-8";
    String EMPTY = "";
    String BLANK = " ";
    String DASH = "-";
    String DOT = "\\.";
    int MAX_LENGTH_RESPONSE_CODE = 4;
    int ZERO = 0;
    int FIRST = 1;
    int SECOND = 2;
    String ID_VKSNDTC = "01";
    String REPONSE_CODE_MISS_REQUIRED = "0404";
    String PRIMARY_KEY_REGEX = "\\d{9}";
    String KHONG_THAY_DOI_TRANG_THAI = "KHONG_THAY_DOI_TRANG_THAI";
    String[] SPPID_DEPARTMENT = {"010232", "010250", "010254", "010216","010220","010204","010234"};

    interface PROCEDURE {

        String WRITE_LOGS_HISTORIES = "WRITE_LOGS_HISTORIES";
    }

    interface ENVIRONMENT {
        String PRODUCTION = "production";
        String TEST = "test";
        String DEV = "dev";
    }

    interface EMAIL {
        interface TYPE {
            int NOTIFICATION = 1;
            int CONFIRM = 2;
        }

        interface STATUS {
            int NEW = 0;
            int SUCCESS = 1;
            int ERROR = 2;
            int TIME_OUT = 4;
        }
    }

    interface USER {
        interface STATUS {
            int ACTIVE = 1;
            int INACTIVE = 0;
            int WAIT_FOR_CONFIRM_EMAIL = 2;
        }
    }

    interface ROLE {
        interface TYPE {
            int MENU = 1;
            int METHOD = 2;
        }
    }

    interface GROUP_ROLE {
        interface TYPE {
            int MENU = 1;
        }

        interface APP_CODE {
            String VKS_QLA = "VKS_QLA";
            String VKS_REPORT = "VKS_REPORT";
        }
    }

    interface OTP {
        long EXPIRY_DEFAULT = 300000;//5 phut
        int TYPE_DEFAULT = 1;

        interface RESET {
            long EXPIRY = 300000;//5 phut
            int TYPE = 1;
        }
    }

    interface PARAMS {
        String ALPHA_NUMERIC_STRING = "0123456789abcdefghijklmnopqrstuvxyz";

        interface GROUP {
            String CHECK_STOCK = "CHECK_STOCK";
            String EVN_CUSTOMER_BILL = "EVN_CUSTOMER_BILL";
        }

        interface TYPE {
            String NPP = "NPP";
        }

    }


    interface CRYPT {
        interface MD5 {
            String ALGORITHM = "MD5";
            byte[] HEX_ARRAY = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        }
    }

    interface STATUS_OBJECT {
        int ACTIVE = 1;
        int INACTIVE = 0;
        String ACTIVE_Y = "Y";
        String INACTIVE_Y = "N";
        int ALL = -1;
        Boolean ACTIVE_B = true;
        Boolean INACTIVE_B = false;
    }

    interface APPROVE_OBJECT {
        String ACTIVE = "Y";
        String INACTIVE = "N";
        String WAITING = "W";

    }

    interface DATE {
        interface FORMAT {
            String DATE_TIME_STAMP = "HH:mm:ss.SSS dd/MM/yyyy";
            String DATE_TIME = "HH:mm:ss dd/MM/yyyy";
            String DATE = "dd/MM/yyyy";
            String TIME = "HH:mm:ss";
            String TIME_2 = "HH:mm";
        }
    }


    interface LOG_APPENDER {
        String CONTROLLER = "controller";
        String CONSOLE = "ConsoleAppender";
        String COMMON = "common";
        String DB = "db";
        String CONNECTION = "connection";
        String APPLICATION = "application";
        String PRE_FILTER = "pre_filter";
        String JOB = "job";
        String SERVICE = "service";
        String SUPPLIER = "supplier";
        String THREAD = "thread";
    }

    interface PAGE {
        interface DEFAULT_VALUE {
            String ASC_ORDER = "ASC";
            String DESC_ORDER = "DESC";
            int PAGE_DEFAULT = 1;
            int SIZE_FIFTY = 50;
            int SIZE_ALL = 0;
            int PAGE_ALL = 0;
        }
    }

    interface KEY {
        String USERNAME = "username";
        String USER_TYPE = "user_type";
    }

    //Declare TABLE used in the DATABASE
    interface TABLE {

    }

    //Declare SEQUENCE used in the DATABASE
    interface SEQUENCE {

    }

    interface METER_INDEX {
        interface STATUS {
            int NEW = 0;
            int UPDATE = 1;
        }

        interface TYPE {
            int INDEX_PERIOD = 1;
            int REPAIR = 2;
        }
    }

    interface REST_FULL {
        String HTTP_STATUS = "HTTP_STATUS";
        String RESPONSE_BODY = "RESPONSE_BODY";
        String RESPONSE = "RESPONSE";
    }

    interface BILL {
        interface STATUS {
            int UNPAID = 0;
            int PAID = 1;
        }
    }

    interface TRANSACTION {
        interface STATUS {
            int NEW = 0;
            int SUCCESS = 1;
            int PENDING = 2;
            int ERROR = 3;
            int TIME_OUT = 4;
            int REVERT = 5;
        }

        interface TYPE {
            int BILLING = 1;
            int REVERT = 2;
        }
    }

    interface PACKAGE {

    }

    interface FUNCTION {

    }
}
