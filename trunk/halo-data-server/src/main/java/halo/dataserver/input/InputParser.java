package halo.dataserver.input;


public interface InputParser {

	/**
	 * 对输入字符串进行解析,转换为可识别的信息.当解析失败时抛出 InputParseException
	 * 
	 * @param inputString
	 * @return
	 * @throws InputParseException
	 */
	InputSqlInfo parse(String inputString) throws InputParseException;
}
