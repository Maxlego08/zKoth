package fr.maxlego08.zkoth.zcore.utils.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import fr.maxlego08.zkoth.zcore.ZPlugin;
import fr.maxlego08.zkoth.zcore.utils.ItemDecoder;

public class ItemStackerAdapter extends TypeAdapter<ItemStack> {

	private static Type seriType = new TypeToken<Map<String, Object>>() {
	}.getType();

	private static String ITEMSTACK = "effect";

	@Override
	public void write(JsonWriter jsonWriter, ItemStack potionEffect) throws IOException {
		if (potionEffect == null) {
			jsonWriter.nullValue();
			return;
		}
		jsonWriter.value(getRaw(potionEffect));
	}

	@Override
	public ItemStack read(JsonReader jsonReader) throws IOException {
		if (jsonReader.peek() == JsonToken.NULL) {
			jsonReader.nextNull();
			return null;
		}
		return fromRaw(jsonReader.nextString());
	}

	private String getRaw(ItemStack itemStack) {
		Map<String, Object> serial = new HashMap<String, Object>();
		serial.put(ITEMSTACK, ItemDecoder.serializeItemStack(itemStack));
		return ZPlugin.z().getGson().toJson(itemStack);
	}

	private ItemStack fromRaw(String raw) {
		Map<String, Object> keys = ZPlugin.z().getGson().fromJson(raw, seriType);
		return ItemDecoder.deserializeItemStack((String) keys.get(ITEMSTACK));
	}

}
