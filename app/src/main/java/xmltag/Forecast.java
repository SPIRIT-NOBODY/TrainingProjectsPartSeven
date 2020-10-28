package xmltag;

import java.util.HashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.xml.sax.Attributes;

public class Forecast {

	private String info;
	private String temperature;
	private String pressure;
	private String cloudiness;
	private String precipitation;
	private String wind;
	private static HashMap<String, String> dayMap = (HashMap<String, String>) Stream
			.of(new String[][] { { "1", "воскресенье" }, { "2", "понедельник" }, { "3", "вторник" }, { "4", "среда" },
					{ "5", "четверг" }, { "6", "пятница" }, { "7", "суббота" }, })
			.collect(Collectors.toMap(data -> data[0], data -> data[1]));

	private static HashMap<String, String> cloudinessMap = (HashMap<String, String>) Stream
			.of(new String[][] { { "0", "ясно" }, { "1", "малооблачно" }, { "2", "облачно" }, { "3", "пасмурно" }, })
			.collect(Collectors.toMap(data -> data[0], data -> data[1]));

	private static HashMap<String, String> precipitationMap = (HashMap<String, String>) Stream
			.of(new String[][] { { "4", "дождь" }, { "5", "ливень" }, { "6", "снег" }, { "7", "снег" },
					{ "8", "гроза" }, { "9", "нет данных" }, { "10", "без осадков" },

			}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	
	private static HashMap<String,String> monthMap = (HashMap<String, String>) Stream.of(
			new String[][] {
				{"1","января"},
				{"2","февраля"},
				{"3","марта"},
				{"4","апреля"},
				{"5","мая"},
				{"6","июня"},
				{"7","июля"},
				{"8","августа"},
				{"9","сентября"},
				{"10","октября"},
				{"11","ноября"},
				{"12","декабря"},
			}
			).collect(Collectors.toMap(data->data[0], data->data[1]));
			

	public String getDayValue(String key) {
		return dayMap.get(key);
	}
	
	public String getCloudinessValue(String key) {
		return cloudinessMap.get(key);
	}
	
	public String getPrecipitationValue(String key) {
		return precipitationMap.get(key);
	}
	
	public String getMonthValue(String key) {
		return monthMap.get(key);
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTemperature() {

		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getCloudiness() {
		return cloudiness;
	}

	public void setCloudiness(String cloudiness) {
		this.cloudiness = cloudiness;
	}

	public String getPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(String precipitation) {
		this.precipitation = precipitation;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	@Override
	public String toString() {
		return "Forecast [info=" + info + ", temperature=" + temperature + ", pressure=" + pressure + ", cloudiness="
				+ cloudiness + ", precipitation=" + precipitation + ", wind=" + wind + "]";
	}

}
