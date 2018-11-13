package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormat {
   private String data;

    public DataFormat(){

    }
    public static String formatarSimpleDate(Date data){
        SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormatada = formata.format(data);
        return dataFormatada;
    }

    public static Date stringDate(String data){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date date = df.parse(data);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
