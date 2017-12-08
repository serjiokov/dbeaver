package org.jkiss.dbeaver.tools.transfer.stream.csv;

import java.io.BufferedReader;
import java.io.File;
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
import org.jkiss.dbeaver.Log;

public class CsvDataParser {

	enum STATE {
		COLUMNS_READER, DATA_READER,
	}

	private static String BUNDLE_ID = "org.jkiss.dbeaver.tools.transfer.stream.csv";
	private BufferedReader input = null;

	List<String> dataColumns = new ArrayList<>();
	Map<String, String[]> dataMap = new HashMap<>();

	String delimiter;
	String encType;
	File inputFile;
	
	public CsvDataParser() {
	}
	
	public void init(File inputFile, String delimiter, String encType) {
		this.inputFile = inputFile;
		if (delimiter != null && !delimiter.isEmpty()) {
			this.delimiter = delimiter;
		} else {
			this.delimiter = ",";
		}
		this.encType = encType;
	}

	public IStatus parseCsvFile() {
		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), encType));
			String iterLine;
			STATE state = STATE.COLUMNS_READER;

			String strPtrnClm = "\\\"" + "(\\w+)" + "\\\"";
			Pattern ptrnClm = Pattern.compile(strPtrnClm);

			while ((iterLine = input.readLine()) != null) {

				System.out.println(iterLine);

				switch (state) {
				case COLUMNS_READER:
					Matcher mtchColumn = ptrnClm.matcher(iterLine);
					while (mtchColumn.find()) {
						dataColumns.add(mtchColumn.group());
					}
					state = STATE.DATA_READER;
					break;
				case DATA_READER:
					String[] data = iterLine.split(delimiter);
					if (data != null && data.length > 0) {
						dataMap.put(data[0], data);
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

		return Status.OK_STATUS;
	}

	public List<String> getColumns() {
		return dataColumns;
	}

	public Map<String, String[]> getMapData() {
		return dataMap;
	}

	
	
}
