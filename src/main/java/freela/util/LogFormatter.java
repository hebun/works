package freela.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
	private static final DateFormat df = new SimpleDateFormat(
			"hh:mm:ss dd/MM/yyyy ");
	@Override
	public String format(LogRecord record) {
	 StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	String caller=	stackTrace[9].getMethodName();
		StringBuilder builder = new StringBuilder(1000);
		builder.append("[").append(record.getLevel()).append("] ");
		builder.append(formatMessage(record));
	
		builder.append("    [" + record.getSourceMethodName()).append("]<-").
		append(caller).
		append(" - ");
		builder.append(df.format(new Date(record.getMillis()))).append(" - ");
		builder.append("[").append(record.getSourceClassName()).append(".")
				.append(record.getSourceMethodName()).append("]");

		builder.append("\n");

	
		return builder.toString();
	}

}