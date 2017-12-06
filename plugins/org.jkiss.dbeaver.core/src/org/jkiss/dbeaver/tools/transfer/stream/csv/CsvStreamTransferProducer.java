package org.jkiss.dbeaver.tools.transfer.stream.csv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jkiss.code.NotNull;
import org.jkiss.dbeaver.DBException;
import org.jkiss.dbeaver.Log;
import org.jkiss.dbeaver.model.runtime.DBRProgressMonitor;
import org.jkiss.dbeaver.model.struct.DBSObject;
import org.jkiss.dbeaver.tools.transfer.IDataTransferConsumer;
import org.jkiss.dbeaver.tools.transfer.IDataTransferProducer;

public class CsvStreamTransferProducer implements IDataTransferProducer<CsvDataTransferSettings> {

	private static final String BUNDLE_ID = "org.jkiss.dbeaver.tools.transfer.stream.csv";
	@NotNull
	private CsvInputDataSource dataContainer;

	private char quoteChar = '"';
	private char delimiter;

	enum STATE {
		COLUMNS_READER, DATA_READER,
	}

	public CsvStreamTransferProducer() {
	}

	public CsvStreamTransferProducer(@NotNull CsvInputDataSource dataContainer) {
		this.dataContainer = dataContainer;
	}

	@Override
	public DBSObject getSourceObject() {
		return dataContainer;
	}

	@Override
	public void transferData(DBRProgressMonitor monitor, IDataTransferConsumer consumer,
			CsvDataTransferSettings settings) throws DBException {
		System.out.println("-= CSV TRANSFER DATA =-");
		BufferedReader input = null;
		delimiter = settings.getDelimetr();

		try {
			input = new BufferedReader(
					new InputStreamReader(new FileInputStream(settings.getFile()), settings.getEncoding()));
			String iterLine;
			STATE state = STATE.COLUMNS_READER;
			String columnName = "";
			String START_CLM = "\\\""; // A literal "(" character in regex
			String END_CLM = "\\\""; // A literal ")" character in regex
			String DLM = ",";

			// Captures the word(s) between the above two character(s)
			String strPtrnClm = START_CLM + "(\\w+)" + END_CLM;
			String strPtrData = "(\\w+)" + DLM;
			
			Pattern ptrnClm = Pattern.compile(strPtrnClm);
			Pattern ptrnData = Pattern.compile(strPtrData);
			
			// Pattern p = Pattern.compile("\\\".*?\\\"");
			List<String> columns = new ArrayList<>();
			Map<Integer, List<String>> mapData = new HashMap<>();
			
			while ((iterLine = input.readLine()) != null) {

				
				System.out.println(iterLine);
				
				switch (state) {
				case COLUMNS_READER:
					Matcher mtchColumn = ptrnClm.matcher(iterLine);
					while (mtchColumn.find()) {
						columns.add(mtchColumn.group());
					}
					state = STATE.DATA_READER;
					break;
				case DATA_READER:
					Matcher mtchData = ptrnData.matcher(iterLine);
					while (mtchData.find()) {
						System.out.println(mtchData.group());
					}
					break;
				 }
			}

		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, BUNDLE_ID, e.getMessage(), e);
			Log.getEclipseLog().log(status);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				IStatus status = new Status(IStatus.ERROR, BUNDLE_ID, e.getMessage(), e);
				Log.getEclipseLog().log(status);
			}
		}

	}

	public String toString() {
		return String.format("%s-%s ", CsvStreamTransferProducer.class.getName());
	}

}
