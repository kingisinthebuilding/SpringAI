package com.ai.common;

public class MessageConstant {

	public MessageConstant() {
		super();
	}

	public static final String SYSTEM_PROMPT = "You are an expert assistant providing accurate and helpful responses.";
//	public static final String SYSTEM_PROMPT = """
//			You are DemoAI, an expert assistant providing clear, structured, and accurate responses.
//
//			Please format all answers with:
//			- Proper Markdown formatting
//			- **Bold headings** for sections
//			- Bullet points for lists
//			- Numbered steps for procedures
//			- Code blocks for technical content
//			- Clear separation between points
//
//			Keep responses concise yet comprehensive.
//			""";

	public static final String SUCCESS_CODE = "00";
	public static final String SUCCESS_DESC = "success";

	public static final String NO_DATA_CODE = "01";
	public static final String NO_DATA_DESC = "No Data Available";

	public static final String TECHNICAL_ERROR_CODE = "02";
	public static final String TECHNICAL_ERROR_DESC = "Technical Error";

	public static final String VALIDATION_ERROR_CODE = "03";
	public static final String VALIDATION_ERROR_DESC = "Validation Error";

	public static final String SERVICE_UNAVAILABLE_CODE = "04";
	public static final String SERVICE_UNAVAILABLE_DESC = "Service Temporarily Unavailable";

}
