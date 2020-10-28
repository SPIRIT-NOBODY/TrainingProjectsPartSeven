package xml;

import java.util.ArrayList;
import java.util.List;

import xmltag.Forecast;

public class XmlWriter<T> {
	private String captionName;
	private List<T> forecastList;

	public XmlWriter() {
		forecastList = new ArrayList<>();
	}

	public void setCaptionName(String cityName) {
		this.captionName = cityName;
	}

	public String getCaptionName() {
		return captionName;
	}

	public void addForecast(T forecast) {
		forecastList.add(forecast);
	}

	public List<T> getForecastList() {
		return forecastList;
	}

}
