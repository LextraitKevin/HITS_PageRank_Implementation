

/**
 * Created by kevin on 12/06/2017.
 */


public class DataFile {

	private String Type;
	private Data param;


	public DataFile(String type, Integer firstValue, String secondValue){
		this.Type= type;
		this.param= new Data(firstValue,secondValue);

	}
}
