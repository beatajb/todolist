package com.example.demo.model;

import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CategorySerializer extends StdSerializer<ItemCategory> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7229475179644035385L;

	public CategorySerializer() {
        this(null);
    }
  
    public CategorySerializer(Class<ItemCategory> t) {
        super(t);
    }

    @Override
    public void serialize(
      ItemCategory value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {

    	jgen.writeFieldName("category");
        jgen.writeStartObject();
        jgen.writeNumberField("categoryId", value.getCategoryId());
        jgen.writeStringField("categoryName", value.getCategoryName());
        jgen.writeStringField("createdAt", value.getCreatedAt().toString());
        jgen.writeFieldName("items"); 
        jgen.writeStartArray();
        for (ListItem item:value.getItems()){
            jgen.writeStartObject();
            jgen.writeNumberField("itemId", item.getItemId());
            jgen.writeStringField("itemName", item.getTaskName());         
            jgen.writeEndObject();
           }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}