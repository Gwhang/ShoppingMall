package com.gwh.sell.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * 日期转换long
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(o.getTime() / 1000);
    }
}
