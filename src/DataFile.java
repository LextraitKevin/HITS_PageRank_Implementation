

/**
 * Created by kevin on 12/06/2017.
 */


public class DataFile {

	private String Type;

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public Data getParam() {
		return param;
	}

	public void setParam(Data param) {
		this.param = param;
	}

	private Data param;


	@Override
	public String toString() {
		return "DataFile{" +
				"Type='" + Type + '\'' +
				", param=" + param.first + " " + param.second+
				'}';
	}

	public DataFile(String type, Integer firstValue, String secondValue){
		this.Type= type;
		this.param= new Data(firstValue,secondValue);

	}
}
