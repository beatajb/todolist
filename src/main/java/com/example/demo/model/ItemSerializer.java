package com.example.demo.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ItemSerializer extends StdSerializer<ListItem> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7229475179644035385L;

	public ItemSerializer() {
        this(null);
    }
  
    public ItemSerializer(Class<ListItem> t) {
        super(t);
    }

    @Override
    public void serialize(
      ListItem value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {

    	jgen.writeFieldName("item");
        jgen.writeStartObject();
        jgen.writeNumberField("itemId", value.getItemId());
        jgen.writeStringField("taskName", value.getTaskName());
        jgen.writeStringField("createdAt", value.getCreatedAt().toString());
        jgen.writeFieldName("categories"); 
        jgen.writeStartArray();
        for (ItemCategory item:value.getCategories()){
            jgen.writeStartObject();
            jgen.writeNumberField("categoryId", item.getCategoryId());
            jgen.writeStringField("categoryName", item.getCategoryName());         
            jgen.writeEndObject();
           }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}