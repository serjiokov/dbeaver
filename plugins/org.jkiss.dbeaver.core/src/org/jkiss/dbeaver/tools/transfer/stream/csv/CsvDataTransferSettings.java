package org.jkiss.dbeaver.tools.transfer.stream.csv;

import java.io.File;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableContext;
import org.jkiss.dbeaver.tools.transfer.IDataTransferSettings;
import org.jkiss.dbeaver.tools.transfer.wizard.DataTransferSettings;

public class CsvDataTransferSettings implements IDataTransferSettings {

	private static final String DELIMITER = "delimiter";
	private String delimiter;
	private File fileImport = null;

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

	public char getDelimetr() {
		if(delimiter==null) {
			return ',';
		}
		return delimiter.toCharArray()[0];
	}

}
