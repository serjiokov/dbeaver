package org.jkiss.dbeaver.tools.transfer.stream.csv;

import java.io.File;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableContext;
import org.jkiss.dbeaver.tools.transfer.IDataTransferSettings;
import org.jkiss.dbeaver.tools.transfer.wizard.DataTransferSettings;

public class CsvDataTransferSettings implements IDataTransferSettings {

	private static final String DELIMITER = "delimiter";
	private File fileImport = null;

	@Override
	public void loadSettings(IRunnableContext runnableContext, DataTransferSettings dataTransferSettings,
			IDialogSettings dialogSettings) {

		String delimiter = dialogSettings.get(DELIMITER);
		if (delimiter != null) {
		}
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

}
