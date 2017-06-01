package ru.a89uchet.roznica89.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 04.12.12
 * Time: 12:44
 * To change this template use File | Settings | File Templates.
 */
public class OtborFields {
    private String name="";
    private String rus_name="";
    private String data_type="";
    private String value_str="";
    private String value_code="";
    private byte  type_byte=0;

    public byte getType_byte() {
        return type_byte;
    }

    public void setType_byte(byte type_byte) {
        this.type_byte = type_byte;
    }

    public String getRus_name() {
        return rus_name;
    }

    public void setRus_name(String rus_name) {
        this.rus_name = rus_name;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getValue_str() {
        return value_str;
    }

    public void setValue_str(String value_str) {
        this.value_str = value_str;
    }

    public String getValue_code() {
        return value_code;
    }

    public void setValue_code(String value_code) {
        this.value_code = value_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
