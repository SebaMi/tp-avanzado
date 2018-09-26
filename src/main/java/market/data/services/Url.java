package market.data.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import market.data.model.Valores;
import market.data.model.*;



public class Url {
	
	
	public ArrayList<Valores> traerJson() throws IOException {


		URL url = new URL("https://raw.githubusercontent.com/mlennard-utn/tp_avanzado/master/mercado.json");
		File file = new File("D:\\Sebas\\Mercado.json");

		ObjectMapper mapper = new ObjectMapper();

		ArrayList<Valores> valores = mapper.readValue(file,
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Valores.class));

		return valores;
	}   
	
}
