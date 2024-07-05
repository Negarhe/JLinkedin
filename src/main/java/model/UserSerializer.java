package model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("name", user.getName());
        jsonObject.addProperty("lastName", user.getLastName());
        jsonObject.addProperty("password", user.getPassword());
        jsonObject.addProperty("title", user.getTitle());
        jsonObject.addProperty("additionalName", user.getAdditionalName());
        jsonObject.addProperty("city", user.getCity());
        jsonObject.addProperty("country", user.getCountry());
//        jsonObject.addProperty("contactInfo", user.getContactInfo().toString());
//        jsonObject.addProperty("profilePhoto", user.getProfilePhoto());
//        jsonObject.addProperty("backgroundPhoto", user.getBackgroundPhoto());
//        jsonObject.addProperty("profession", user.getProfession());
//        jsonObject.addProperty("status", user.getStatus().toString());



        return jsonObject;
    }
}
