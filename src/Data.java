/**
 * Created by kevin on 12/06/2017.
 */
public class Data {
	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public Data(Integer param1, String param2){
		this.first = param1;
		this.second = param2;
	}

	public Integer first;
	public String second;

}
