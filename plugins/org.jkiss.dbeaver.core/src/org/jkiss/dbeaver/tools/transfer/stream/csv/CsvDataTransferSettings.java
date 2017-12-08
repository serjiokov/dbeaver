package org.jkiss.dbeaver.tools.transfer.stream.csv;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableContext;
import org.jkiss.dbeaver.tools.transfer.IDataTransferSettings;
import org.jkiss.dbeaver.tools.transfer.wizard.DataTransferSettings;

public class CsvDataTransferSettings implements IDataTransferSettings {

	private static final String DELIMITER = "delimiter";
	private String delimiter = ",";
	private File fileImport = null;

	private CsvDataParser parser;

	public CsvDataTransferSettings() {
	}

	@Override
	public void loadSettings(IRunnableContext runnableContext, DataTransferSettings dataTransferSettings,
			IDialogSettings dialogSettings) {

		delimiter = dialogSettings.get(DELIMITER);

	}

	@Override
	public void saveSettings(IDialogSettings dialogSettings) {
		dialogSettings.put(DELIMITER, "|");
	}

	public File getFile() {
		return fileImport;
	}

	public void setFileLocation(File file) {
		this.fileImport = file;
	}

	public String getEncoding() {
		return "UTF8";
	}

	public String getDelimetr() {
		if (delimiter == null) {
			return ",";
		}
		return delimiter;
	}

	public IStatus parseCsvFile() {
		parser = new CsvDataParser();
		parser.init(getFile(), getDelimetr(), getEncoding());
		return parser.parseCsvFile();
	}

	public Collection<String> getInputColumns() {
		return Collections.unmodifiableList(parser.getColumns());
	}

}
