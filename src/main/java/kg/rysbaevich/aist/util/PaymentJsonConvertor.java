package kg.rysbaevich.aist.util;

import com.google.gson.Gson;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kg.rysbaevich.aist.model.response.billing.Payment;

@Converter
public class PaymentJsonConvertor implements AttributeConverter<Payment, String> {

    Gson gson = new Gson();

    @Override
    public String convertToDatabaseColumn(Payment payment) {
        return gson.toJson(payment);
    }

    @Override
    public Payment convertToEntityAttribute(String json) {
        return gson.fromJson(json, Payment.class);
    }

}



